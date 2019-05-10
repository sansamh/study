package sansam.v3.aop;

import sansam.v3.advisor.Advisor;
import sansam.v3.beans.BeanFactory;

import java.util.List;

public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Throwable {
        //根据什么来区分使用JDK动态代理还是CGLIB呢
        //可以根据类有没有接口
        if (shouldUseJDKDynamicProxy(target)) {
            return new JDKDynamicAopProxy(beanName, target, matchAdvisors, beanFactory);
        }

        return new CglibDynamicAopProxy(beanName, target, matchAdvisors, beanFactory);
    }

    private boolean shouldUseJDKDynamicProxy(Object target) {
        /**
         *     @Override
         *     public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
         *         if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
         *             Class<?> targetClass = config.getTargetClass();
         *             if (targetClass == null) {
         *                 throw new AopConfigException("TargetSource cannot determine target class: " +
         *                         "Either an interface or a target is required for proxy creation.");
         *             }
         *             if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
         *                 return new JdkDynamicAopProxy(config);
         *             }
         *             return new ObjenesisCglibAopProxy(config);
         *         }
         *         else {
         *             return new JdkDynamicAopProxy(config);
         *         }
         *     }
         * config.isOptimize()：是否优化，看到否的逻辑是JDK，就可以知道Spring认为CGLIB动态代理的性能更高点。。。
         * config.isProxyTargetClass()：是否直接代理目标类以及任何接口
         * hasNoUserSuppliedProxyInterfaces(config)：是否没有指定代理接口
         * targetClass.isInterface()：确定指定的对象是否表示接口类型
         * Proxy.isProxyClass(targetClass)：是否是代理类
         * ---------------------
         * 在代理对象不是借口类型或不是代理类时，指定proxyTargetClass=true后，执行CGLIB代理
         * 代理对象是接口类型或是代理类，使用JDK代理
         */

        return false;
    }
}
