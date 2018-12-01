package v3.beans;

import com.google.common.base.Objects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @version 2.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:10 2018/11/27
 */
public class GenericBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;

    private String scope = BeanDefinition.SCOPE_SINGLETION;

    private String factoryBeanName;

    private String factoryMethodName;

    private String initMethodName;

    private String destroyMethodName;
    //v2 新增
    private Constructor constructor;

    private Method factoryMethod;

    private List<?> constructorArgumentValues;

    private List<PropertyValue> propertyValues;
    //v2 新增

    //v3 add
    private Object [] constructorRealValues;

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETION.equals(this.scope);
    }

    @Override
    public boolean isPrototyoe() {
        return SCOPE_PROTOTYPE.equals(this.scope);
    }

    @Override
    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    @Override
    public String getFactoryMethodName() {
        return factoryMethodName;
    }

    @Override
    public String getInitMethodName() {
        return initMethodName;
    }

    @Override
    public String getDestoryMethodName() {
        return destroyMethodName;
    }

    @Override
    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public Method getFactoryMethod() {
        return factoryMethod;
    }

    @Override
    public void setFactoryMethod(Method factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    @Override
    public List<?> getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    @Override
    public Object[] getConstructorArgumentRealValues() {
        return constructorRealValues;
    }

    @Override
    public void setConstructorArgumentRealValues(Object[] values) {
        this.constructorRealValues = values;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public void setConstructorArgumentValues(List<?> constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    @Override
    public String toString() {
        return "GenericBeanDefinition{" +
                "beanClass=" + beanClass +
                ", scope='" + scope + '\'' +
                ", factoryBeanName='" + factoryBeanName + '\'' +
                ", factoryMethodName='" + factoryMethodName + '\'' +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericBeanDefinition that = (GenericBeanDefinition) o;
        return Objects.equal(beanClass, that.beanClass) &&
                Objects.equal(scope, that.scope) &&
                Objects.equal(factoryBeanName, that.factoryBeanName) &&
                Objects.equal(factoryMethodName, that.factoryMethodName) &&
                Objects.equal(initMethodName, that.initMethodName) &&
                Objects.equal(destroyMethodName, that.destroyMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(beanClass, scope, factoryBeanName, factoryMethodName, initMethodName, destroyMethodName);
    }
}
