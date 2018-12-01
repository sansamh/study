package v3.aop;

import v3.advisor.Advisor;
import v3.beans.BeanFactory;

import java.util.List;

public interface AopProxyFactory {

    AopProxy createAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Throwable;

    static AopProxyFactory getDefaultAopProxyFactory() {
        return new DefaultAopProxyFactory();
    }
}
