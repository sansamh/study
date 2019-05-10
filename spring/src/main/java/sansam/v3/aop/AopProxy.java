package sansam.v3.aop;

/**
 * @version 3.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:46 2018/11/30
 */
public interface AopProxy {

	Object getProxy();

	Object getProxy(ClassLoader classLoader);
}
