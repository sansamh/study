package sansam.v3.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sansam.v3.advisor.Advisor;
import sansam.v3.beans.BeanDefinition;
import sansam.v3.beans.BeanFactory;
import sansam.v3.beans.DefaultBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {

    private static final Log logger = LogFactory.getLog(CglibDynamicAopProxy.class);
    private static Enhancer enhancer = new Enhancer();

    private String beanName;
    private Object target;

    private List<Advisor> matchAdvisors;

    private BeanFactory beanFactory;

    public CglibDynamicAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) {
        super();
        this.beanName = beanName;
        this.target = target;
        this.matchAdvisors = matchAdvisors;
        this.beanFactory = beanFactory;
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return AopProxyUtils.applyAdvice(target, method, objects, matchAdvisors, o, beanFactory);
    }

    @Override
    public Object getProxy() {
        return this.getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("为" + target + "创建cglib代理。");
        }

        Class<?> superClass = this.target.getClass();
        enhancer.setSuperclass(superClass);
        enhancer.setInterfaces(this.getClass().getInterfaces());
        enhancer.setCallback(this);

        Constructor<?> constructor = null;

        try {
            constructor = superClass.getConstructor(new Class<?>[]{});
        } catch (NoSuchMethodException e) {

        }
        //如果有无参构造函数 直接create
        if (null != constructor) {
            return enhancer.create();
        } else {
            BeanDefinition beanDefinition = ((DefaultBeanFactory) beanFactory).getBeanDefinition(beanName);
            return enhancer.create(beanDefinition.getConstructor().getParameterTypes(), beanDefinition.getConstructorArgumentRealValues());
        }

    }
}
