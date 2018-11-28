package v2.beans;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @description: 默认工厂
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
     * 执行初始化方法
     *
     * @param instance
     * @param beanDefinition
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void doInit(Object instance, BeanDefinition beanDefinition) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (StringUtils.isNotBlank(beanDefinition.getInitMethodName())) {
            Method method = instance.getClass().getDeclaredMethod(beanDefinition.getInitMethodName(), null);
            method.invoke(instance, null);
            log.info("执行init方法：[{}]", beanDefinition.getInitMethodName());
        }
    }

    /**
     * 通过构造函数创建对象
     *
     * @param beanDefinition
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object createBeanByConstructure(BeanDefinition beanDefinition)
            throws IllegalAccessException, InstantiationException {
        try {
            Object[] values = getConstructorRealArgumentValues(beanDefinition);
            if (Objects.isNull(values)) {
                //无参构造
                return beanDefinition.getBeanClass().newInstance();
            } else {
                //有参数的构造
                return getRealConstructor(beanDefinition, values).newInstance(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 匹配有参数的构造函数
     *
     * @param beanDefinition
     * @param values
     * @return
     */
    private Constructor getRealConstructor(BeanDefinition beanDefinition, Object[] values) throws Exception {
        Constructor constructor;

        /**
         * 原型类bean 缓存构造函数 第二次可以直接获取
         */
        if (!Objects.isNull(beanDefinition.getConstructor())) {
            return beanDefinition.getConstructor();
        }

        //获取参数的class
        Class<?>[] paramaTypes = new Class[values.length];
        int i = 0;
        for (Object value : values) {
            paramaTypes[i++] = value.getClass();
        }
        //试图到对应构造函数
        constructor = beanDefinition.getBeanClass().getConstructor(paramaTypes);
        if (!Objects.isNull(constructor)) {
            return constructor;
        }
        //未到找对应构造函数时 循环所有构造函数 依次匹配参数类型
        Constructor<?>[] declaredConstructors = beanDefinition.getBeanClass().getDeclaredConstructors();
        outer: for (Constructor<?> c : declaredConstructors) {
            Class<?>[] parameterTypes = c.getParameterTypes();
            if (paramaTypes.length == parameterTypes.length) {
                for (int j = 0; j < parameterTypes.length; j++) {
                    if (!paramaTypes[j].isAssignableFrom(parameterTypes[j])) {
                        continue outer;
                    }
                }
                //找到了对应的构造函数
                constructor = c;
                break outer;
            }
        }

        //对应原型bean 缓存构造函数
        if (constructor != null) {
            if (beanDefinition.isPrototyoe()) {
                beanDefinition.setConstructor(constructor);
            }
            return constructor;
        } else {
            throw new Exception("未到找对应的构造函数：[" + beanDefinition + "]");
        }

    }

    /**
     * 获取构造参数真正的value
     *
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object[] getConstructorRealArgumentValues(BeanDefinition beanDefinition) throws Exception {
        return getRealValues(beanDefinition.getConstructorArgumentValues());
    }

    /**
     * 解析参数 获取真正的参数值
     *
     * @param ref
     * @return
     * @throws Exception
     */
    private Object[] getRealValues(List<?> ref) throws Exception {
        if (CollectionUtils.isEmpty(ref)) {
            return null;
        }
        Object[] objects = new Object[ref.size()];
        int index = 0;
        Object v = null;
        for (Object o : ref) {
            if (o instanceof BeanReference) {
                v = this.doGetBean(((BeanReference) o).getBeanName());
            } else if (o instanceof Object[]) {
                // TODO 处理集合中的bean引用
            } else if (o instanceof Collection) {
                // TODO 处理集合中的bean引用
            } else if (o instanceof Properties) {
                // TODO 处理properties中的bean引用
            } else if (o instanceof Map) {
                // TODO 处理Map中的bean引用
            } else {
                v = o;
            }
            objects[index++] = v;
        }

        return objects;
    }

    /**
     * 通过静态工厂方法获取对象
     *
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object createBeanByStaticFactoryMethod(BeanDefinition beanDefinition) throws Exception {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Method method = beanClass.getDeclaredMethod(beanDefinition.getFactoryMethodName(), null);
        return method.invoke(beanClass, null);
    }

    /**
     * 通过普通工厂方法获取对象
     *
     * @param beanDefinition
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object createBeanByFactoryMethod(BeanDefinition beanDefinition) throws Exception {
        Object o = doGetBean(beanDefinition.getFactoryBeanName());
        Method method = o.getClass().getDeclaredMethod(beanDefinition.getFactoryMethodName(), null);
        return method.invoke(o, null);
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
                    method = next.getValue().getClass().getDeclaredMethod(beanDefinition.getDestoryMethodName(), null);
                    method.invoke(next.getValue(), null);
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
