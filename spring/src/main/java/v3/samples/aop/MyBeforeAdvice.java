package v3.samples.aop;

import v3.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MyBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void invoke(Method method, Object[] args, Object target) {
        System.out.println(this + " 对 " + target + " 进行了前置增强！");
    }
}
