package v3.samples.aop;

import v3.advice.MethodIntercepterAdvice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodIntercepterAdvice {

	@Override
	public Object invoke(Method method, Object[] args, Object target) throws InvocationTargetException, IllegalAccessException {
		System.out.println(this + "对 " + target + "进行了环绕--前增强");
		Object ret = method.invoke(target, args);
		System.out.println(this + "对 " + target + "进行了环绕 --后增强。方法的返回值为：" + ret);
		return ret;
	}

}
