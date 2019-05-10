package sansam.v3.beans;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import sansam.v3.aware.BeanFactoryAware;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 2.0
 * @description: 默认工厂
 * @author: 侯春兵
 * @Date: 14:26 2018/11/27
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

    private Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>(256);
    private ConcurrentHashMap<String, BeanDefinition> beanDefintionMap = new ConcurrentHashMap<>(256);
    /**
     * 记录正在创建的beanName到threadLocal里 解决循环依赖 a->b->c->a 会造成死循环
     */
    private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();

    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>(16);

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistryException {
        Objects.requireNonNull(beanName, "注册bean需要给入beanName");
        Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");

        if (!beanDefinition.validate()) {
            throw new BeanDefinitionRegistryException(String.format("名为：[%s]的beanDefinition校验不通过：[%s]", beanName, beanDefinition));
        }
        //可以设置重名beanName的出库方式
        if (beanDefintionMap.contains(beanName)) {
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

    @Override
    public void registeBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessorList.add(beanPostProcessor);
        if (beanPostProcessor instanceof BeanFactoryAware) {
            ((BeanFactoryAware) beanPostProcessor).setBeanFactory(this);
        }
    }

    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName, "beanName不能为空");

        Object instance = beanMap.get(beanName);

        if (instance != null) {
            return instance;
        }

        BeanDefinition beanDefinition = beanDefintionMap.get(beanName);
        Objects.requireNonNull(beanDefinition, "beanDefinition不能为空");

        //记录正在创建的beanName 检测循环依赖
        Set<String> beans = buildingBeans.get();
        if (beans == null) {
            beans = new HashSet<>(16);
        }
        if (!beans.contains(beanName)) {
            beans.add(beanName);
            buildingBeans.set(beans);
        } else {
            String[] strings = new String[beans.size()];
            beans.toArray(strings);
            buildingBeans.remove();
            throw new Exception("创建[" + beanName + "]时检测到循环依赖，正在创建的bean有：[" + Arrays.toString(strings) + "]");
        }

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

        //实例创建好之后，移除记录
        beans.remove(beanName);
        //初始化
        doInit(instance, beanDefinition);
        //属性依赖 初始化
        doSetPropertyValues(instance, beanDefinition);
        //aop
        try {
            instance = applyPostProcessAfterInitialization(instance, beanName);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (beanDefinition.isSingleton()) {
            this.beanMap.put(beanName, instance);
        }
        return instance;
    }

    // 应用bean初始化前的处理
    private Object applyPostProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
        for (BeanPostProcessor bpp : this.beanPostProcessorList) {
            bean = bpp.postProcessBeforeInitialization(bean, beanName);
        }
        return bean;
    }

    // 应用bean初始化后的处理
    private Object applyPostProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        for (BeanPostProcessor bpp : this.beanPostProcessorList) {
            bean = bpp.postProcessAfterInitialization(bean, beanName);
        }
        return bean;
    }

    private void doSetPropertyValues(Object o, BeanDefinition beanDefinition) throws Exception {
        if (CollectionUtils.isEmpty(beanDefinition.getPropertyValues())) {
            return;
        }
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();

        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            if (StringUtils.isBlank(name)) {
                continue;
            }
            Object value = propertyValue.getValue();

            Field f = o.getClass().getDeclaredField(name);
            f.setAccessible(true);

            Object v = null;
            if (value instanceof BeanReference) {
                v = this.getBean(((BeanReference) value).getBeanName());
            } else if (value instanceof Object[]) {
                // TODO 处理集合中的bean引用
            } else if (value instanceof Collection) {
                // TODO 处理集合中的bean引用
            } else if (value instanceof Properties) {
                // TODO 处理properties中的bean引用
            } else if (value instanceof Map) {
                // TODO 处理Map中的bean引用
            } else {
                v = value;
            }

            f.set(o, v);
            log.info("bean：[{}]给属性[{}]设置value[{}]", o, f.getName(), v);
        }
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
                //缓存真正的构造函数的参数 v3
                beanDefinition.setConstructorArgumentRealValues(values);
                // 缓存构造函数由determineConstructor 中移到了这里，无论原型否都缓存，因为后面AOP需要用
                Constructor realConstructor = getRealConstructor(beanDefinition, values);
                beanDefinition.setConstructor(realConstructor);
                return realConstructor.newInstance(values);
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
        outer:
        for (Constructor<?> c : declaredConstructors) {
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

        //对应原型bean 缓存构造函数 -> v3 上层缓存
        if (constructor != null) {
            /*if (beanDefinition.isPrototyoe()) {
                beanDefinition.setConstructor(constructor);
            }*/
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
        Object[] realValues = getRealValues(beanDefinition.getConstructorArgumentValues());
        Method method = getRealFactoryMethod(beanDefinition, realValues, null);
        return method.invoke(beanClass, realValues);
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
        Object[] realValues = getRealValues(beanDefinition.getConstructorArgumentValues());
        Method method = getRealFactoryMethod(beanDefinition, realValues, o.getClass());
        return method.invoke(o, realValues);
    }

    /**
     * 获取工厂方法
     *
     * @param beanDefinition
     * @param realValues
     * @param objectClass    工厂产生的实例对象class 静态工厂为null 不需要通过IOC容器产生对象 直接为beanCalss
     * @return
     */
    private Method getRealFactoryMethod(BeanDefinition beanDefinition, Object[] realValues, Class objectClass) throws Exception {
        if (Objects.isNull(objectClass)) {
            objectClass = beanDefinition.getBeanClass();
        }
        if (Objects.isNull(realValues)) {
            return objectClass.getDeclaredMethod(beanDefinition.getFactoryMethodName(), null);
        }
        //原型bean 缓存factoryMethod
        if (!Objects.isNull(beanDefinition.getFactoryMethod())) {
            return beanDefinition.getFactoryMethod();
        }
        Method m = null;
        Class[] paramTypes = new Class[realValues.length];
        int index = 0;
        for (Object realValue : realValues) {
            paramTypes[index++] = realValue.getClass();
        }

        outer:
        for (Method method : objectClass.getDeclaredMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == paramTypes.length) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!paramTypes[i].isAssignableFrom(parameterTypes[i])) {
                        continue outer;
                    }
                }
                m = method;
                break outer;
            }
        }

        if (m != null) {
            //原型bean 缓存工厂方法
            if (beanDefinition.isPrototyoe()) {
                beanDefinition.setFactoryMethod(m);
            }
            return m;
        } else {
            throw new Exception("未找到对应的工厂方法：[" + beanDefinition + "]");
        }
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
