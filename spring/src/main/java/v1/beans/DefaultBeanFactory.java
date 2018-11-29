package v1.beans;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @description:  默认工厂
 * @author: 侯春兵
 * @Date: 14:26 2018/11/27
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

    private Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap(256);
    private ConcurrentHashMap<String, BeanDefinition> beanDefintionMap = new ConcurrentHashMap(256);

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistryException {
        Objects.requireNonNull(beanName, "注册bean需要给入beanName");
        Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");

        if (!beanDefinition.validate()) {
            throw new BeanDefinitionRegistryException(String.format("名为：[%s]的beanDefinition校验不通过：[%s]", beanName, beanDefinition));
        }
        //可以设置重名beanName的出库方式
        if (beanMap.contains(beanName)) {
            throw new BeanDefinitionRegistryException(String.format("名为：[%s]的beanDefinition已存在：[%s]", beanName, this.getBeanDefinition(beanName)));
        }

        this.beanDefintionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefintionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefintionMap.contains(beanName);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName, "beanName不能为空");

        Object instance = beanMap.get(beanName);

        if (instance != null) {
            return instance;
        }

        BeanDefinition beanDefinition = beanDefintionMap.get(beanName);
        Objects.requireNonNull(beanDefinition, "beanDefinition不能为空");

        Class<?> beanClass = beanDefinition.getBeanClass();
        if (beanClass != null) {
            if (StringUtils.isBlank(beanDefinition.getFactoryMethodName())) {
                //直接创建对象
                instance = createBeanByConstructure(beanDefinition);
                log.info("通过构造函数创建对象：[{}]", instance);
            } else {
                //静态工厂方法创建对象 获取静态工厂class 反射找到方法 执行方法生成对象
                instance = createBeanByStaticFactoryMethod(beanDefinition);
                log.info("通过静态工厂方法创建对象创建对象：[{}]", instance);
            }
        } else {
            //通过普通工厂方法创建对象
            instance = createBeanByFactoryMethod(beanDefinition);
            log.info("通过普通工厂方法创建对象：[{}]", instance);
        }

        doInit(instance, beanDefinition);

        if (beanDefinition.isSingleton()) {
            this.beanMap.put(beanName, instance);
        }
        return instance;
    }

    /**
     *  执行初始化方法
     * @param instance
     * @param beanDefinition
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void doInit(Object instance, BeanDefinition beanDefinition) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (StringUtils.isNotBlank(beanDefinition.getInitMethodName())) {
            Class<?> clz = null;
            Method method = instance.getClass().getDeclaredMethod(beanDefinition.getInitMethodName(), clz);
            Object [] objects = null;
            method.invoke(instance, objects);
            log.info("执行init方法：[{}]", beanDefinition.getInitMethodName());
        }
    }

    /**
     *  通过构造函数创建对象
     * @param beanDefinition
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object createBeanByConstructure(BeanDefinition beanDefinition)
            throws IllegalAccessException, InstantiationException {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 通过静态工厂方法获取对象
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object createBeanByStaticFactoryMethod(BeanDefinition beanDefinition) throws Exception {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Class<?> clz = null;
        Method method = beanClass.getDeclaredMethod(beanDefinition.getFactoryMethodName(), clz);
        Object [] objects = null;
        return method.invoke(beanClass, objects);
    }

    /**
     * 通过普通工厂方法获取对象
     * @param beanDefinition
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object createBeanByFactoryMethod(BeanDefinition beanDefinition) throws Exception {
        Object o = doGetBean(beanDefinition.getFactoryBeanName());
        Class<?> clz = null;
        Method method = o.getClass().getDeclaredMethod(beanDefinition.getFactoryMethodName(), clz);
        Object [] objects = null;
        return method.invoke(o, objects);
    }

    @Override
    public void close() {
        Iterator<Map.Entry<String, Object>> iterator = this.beanMap.entrySet().iterator();
        BeanDefinition beanDefinition;
        Method method;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String beanName = next.getKey();
            beanDefinition = this.beanDefintionMap.get(beanName);
            if (beanDefinition.isSingleton() && StringUtils.isNotBlank(beanDefinition.getDestoryMethodName())) {
                try {
                    Class<?> clz = null;
                    method = next.getValue().getClass().getDeclaredMethod(beanDefinition.getDestoryMethodName(), clz);
                    Object [] objects = null;
                    method.invoke(next.getValue(), objects);
                    log.info("执行bean[" + beanName + "] " + beanDefinition + " 的 销毁方法！");
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e1) {
                    e1.printStackTrace();
                    log.info("执行bean[" + beanName + "] " + beanDefinition + " 的 销毁方法异常！" + e1);
                }

            }
        }
    }
}
