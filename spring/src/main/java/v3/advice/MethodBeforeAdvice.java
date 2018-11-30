package v3.advice;

import java.lang.reflect.Method;

/**
 * @version 3.0
 * @description:  方法前置增强
 * @author: 侯春兵
 * @Date: 9:53 2018/11/30
 */
public interface MethodBeforeAdvice extends Advice{

    /**
     *
     * @param method 增强的方法
     * @param args  增强方法的参数
     * @param target  增强方法所在的对象
     * @return
     */
    void invoke(Method method, Object[] args, Object target);
}
