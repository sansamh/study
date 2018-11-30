package v3.advice;

import java.lang.reflect.Method;

/**
 * @version 3.0 方法后置增强
 * @description:
 * @author: 侯春兵
 * @Date: 10:36 2018/11/30
 */
public interface MethodAfterAdvice extends Advice {

	/**
	 *
	 * @param returnValue  方法返回值value
	 * @param method 增强的方法
	 * @param args  增强方法的参数
	 * @param target  增强方法所在的对象
	 * @return
	 */
	void invoke(Object returnValue, Method method, Object [] args, Object target);
}
