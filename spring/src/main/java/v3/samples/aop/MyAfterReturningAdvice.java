package v3.samples.aop;

import v3.advice.MethodAfterAdvice;

import java.lang.reflect.Method;

public class MyAfterReturningAdvice implements MethodAfterAdvice {


    @Override
    public void invoke(Object returnValue, Method method, Object[] args, Object target) {
        System.out.println(this + " 对 " + target + " 做了后置增强，得到的返回值=" + returnValue);
    }
}
