package v3.advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @version 3.0
 * @description:  方法环绕增强
 * @author: 侯春兵
 * @Date: 10:35 2018/11/30
 */
public interface MethodIntercepterAdvice {

	/**
	 *
	 * @param method 增强的方法
	 * @param args  增强方法的参数
	 * @param target  增强方法所在的对象
	 * @return
	 */
	Object invoke(Method method, Object [] args, Object target) throws InvocationTargetException, IllegalAccessException;
}
