package v3.aop;

import v3.advice.MethodAfterAdvice;
import v3.advice.MethodBeforeAdvice;
import v3.advice.MethodIntercepterAdvice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @version 3.0
 * @description: 责任链式调用增强
 * @author: 侯春兵
 * @Date: 14:50 2018/11/30
 */
public class AopAdviceChainInvocation {

	private List<Object> advisors;
	private Object target;
	private Method method;
	private Object[] args;

	public AopAdviceChainInvocation(List<Object> advisors, Object target, Method method, Object[] args) {
		this.advisors = advisors;
		this.target = target;
		this.method = method;
		this.args = args;
	}

	private int i = 0;
	public Object invoke() throws InvocationTargetException, IllegalAccessException {
		if (i < advisors.size()) {
			Object advice = advisors.get(i++);
			if (advice instanceof MethodBeforeAdvice) {
				// 执行前置增强
				((MethodBeforeAdvice) advice).invoke(method, args, target);
			} else if (advice instanceof MethodIntercepterAdvice) {
				// 执行环绕增强和异常处理增强。注意这里给入的method 和 对象 是invoke方法和链对象
				return ((MethodIntercepterAdvice) advice).invoke(method, null, this);
			} else if (advice instanceof MethodAfterAdvice) {
				// 执行后置增强 先得得到结果，再执行后置增强逻辑
				Object invoke = this.invoke();
				((MethodAfterAdvice) advice).invoke(invoke, method, args, target);
				return invoke;
			}

			return this.invoke();
		} else {
			return method.invoke(target, args);
		}
	}
}
