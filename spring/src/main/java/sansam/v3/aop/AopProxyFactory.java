package sansam.v3.aop;

import sansam.v3.advisor.Advisor;
import sansam.v3.beans.BeanFactory;

import java.util.List;

public interface AopProxyFactory {

    AopProxy createAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Throwable;

    static AopProxyFactory getDefaultAopProxyFactory() {
        return new DefaultAopProxyFactory();
    }
}
