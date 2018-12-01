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
    private static Method invokeMethod;

    static {
        try {
            invokeMethod = AopAdviceChainInvocation.class.getMethod("invoke");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

	private List<Object> advisors;
	private Object target;
	private Method method;
	private Object[] args;
	private Object proxy;

	public AopAdviceChainInvocation(List<Object> advisors, Object target, Object proxy, Method method, Object[] args) {
		this.advisors = advisors;
		this.target = target;
		this.method = method;
		this.args = args;
		this.proxy = proxy;
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
                // 环绕增强 相当于 环绕BEGIN -> 接下来的所有增强 -> 环绕END 所以在这里我们要传入自身invoke方法, target为AopAdviceChainInvocation 继续调用自身invoke执行接下来的增强
				return ((MethodIntercepterAdvice) advice).invoke(invokeMethod, null, this);

			} else if (advice instanceof MethodAfterAdvice) {
				// 执行后置增强 先得到被增强方法结果，再执行后置增强逻辑
                // 如果只有一个后置增强 运行到这里 继续调用本身 此时 i==advisors.size() 执行 return method.invoke(target, args); 得到方法本身的返回值 == invoke
                // 再走 ((MethodAfterAdvice) advice).invoke(invoke, method, args, target); 后置增强方法 返回invoke
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
