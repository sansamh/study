package v2.beans;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @version 2.0
 * @description: 自定义beanDefinition 可通过Class生成对象 也可以通过工厂方法 获取对象
 * @author: 侯春兵
 * @Date: 14:09 2018/11/27
 */
public interface BeanDefinition {

    String SCOPE_SINGLETION = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 获取beanClass 可以为对象的class 也可以为静态工厂bean的class
     */
    Class<?> getBeanClass();

    /**
     * 获取bean的作用范围
     */
    String getScope();

    /**
     * 是否单例模式
     */
    boolean isSingleton();

    /**
     * 是否原型模式
     */
    boolean isPrototyoe();

    /**
     * 获取工厂方法bean的名字
     */
    String getFactoryBeanName();

    /**
     * 获取工厂方法 方法名
     */
    String getFactoryMethodName();

    /**
     * 获取初始化方法
     */
    String getInitMethodName();

    /**
     * 获取销毁方法
     */
    String getDestoryMethodName();

    /**
     * 验证beanDefinition是否合法
     */
    default boolean validate() {
        //beanClass 不存在的时候 工厂方法bean名和工厂方法名必须存在
        if (this.getBeanClass() == null) {
            if (StringUtils.isBlank(getFactoryBeanName()) || StringUtils.isBlank(getFactoryMethodName())) {
                return false;
            }
        }
        //beanClass和工厂方法bean名不能同时存在
        if (this.getBeanClass() != null && StringUtils.isNotBlank(getFactoryBeanName())) {
            return false;
        }
        return true;
    }

    /* v2新增 */

    /**
     * 获取构造器
     */
    Constructor<?> getConstructor();

    /**
     * 设置构造器
     */
    void setConstructor(Constructor constructor);

    /**
     * 获取工厂方法
     */
    Method getFactoryMethod();

    /**
     * 设置工厂方法
     */
    void setFactoryMethod(Method factoryMethod);

    /**
     * 获取构造器参数
     */
    List<?> getConstructorArgumentValues();

    /**
     * 属性依赖
     */
    List<PropertyValue> getPropertyValues();
}
