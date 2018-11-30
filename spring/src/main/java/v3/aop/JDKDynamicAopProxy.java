package v3.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import v3.advisor.Advisor;
import v3.beans.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:48 2018/11/30
 */
public class JDKDynamicAopProxy implements AopProxy, InvocationHandler {

	private static Logger logger = LoggerFactory.getLogger(JDKDynamicAopProxy.class);

	private String beanName;

	private	Object target;

	private List<Advisor> matchAdvisor;

	private BeanFactory beanFactory;

	public JDKDynamicAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) {
		this.beanName = beanName;
		this.target = target;
		this.matchAdvisor = matchAdvisors;
		this.beanFactory = beanFactory;
	}

	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}

	@Override
	public Object getProxy() {
		return this.getProxy(target.getClass().getClassLoader());
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		return Proxy.newProxyInstance(classLoader, target.getClass().getInterfaces(), this);
	}
}
