package v3.pointcut;

import java.lang.reflect.Method;

/**
 * @version 3.0
 * @description:  切入点
 * 	JointPoint 连接点：程序运行中的某个阶段点，比如方法的调用、异常的抛出等。比如方法doSome();
 *	Pointcut是JoinPoint的子集合，它是程序中需要注入Advice的位置JointPoint的集合，指明Advice要在什么样的条件下才能被触发。
 *  通常我们的PointCut为某些类的某些具体方法
 * @author: 侯春兵
 * @Date: 10:39 2018/11/30
 */
public interface PointCut {

	/**
	 * 类名是否匹配表达式 表达式可为AspectJ 或者 正则表达式等
	 * @param clz
	 * @return
	 */
	boolean matchClass(Class<?> clz);

	/**
	 * 方法名是否匹配表达式
	 * @param method
	 * @return
	 */
	boolean matchMethod(Method method);
}
