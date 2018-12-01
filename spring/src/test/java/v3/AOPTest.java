package v3;


import org.junit.Test;
import v3.advisor.AspectJPointCutAdvisor;
import v3.aop.AdvisorAotuProxyCreater;
import v3.beans.BeanReference;
import v3.beans.GenericBeanDefinition;
import v3.beans.PreBuildBeanFactory;
import v3.samples.ABean;
import v3.samples.CBean;
import v3.samples.IABean;
import v3.samples.aop.MyAfterReturningAdvice;
import v3.samples.aop.MyBeforeAdvice;
import v3.samples.aop.MyMethodInterceptor;

import java.util.ArrayList;
import java.util.List;

public class AOPTest {

    static PreBuildBeanFactory bf = new PreBuildBeanFactory();

    @Test
    public void testCirculationDI() throws Throwable {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABean.class);
        List<Object> args = new ArrayList<>();
        args.add("abean01");
        args.add(new BeanReference("cbean"));
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("abean", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(CBean.class);
        args = new ArrayList<>();
        args.add("cbean01");
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("cbean", bd);

        // 前置增强advice bean注册
        bd = new GenericBeanDefinition();
        bd.setBeanClass(MyBeforeAdvice.class);
        bf.registryBeanDefinition("myBeforeAdvice", bd);

        // 环绕增强advice bean注册
        bd = new GenericBeanDefinition();
        bd.setBeanClass(MyMethodInterceptor.class);
        bf.registryBeanDefinition("myMethodInterceptor", bd);

        // 后置增强advice bean注册
        bd = new GenericBeanDefinition();
        bd.setBeanClass(MyAfterReturningAdvice.class);
        bf.registryBeanDefinition("myAfterReturningAdvice", bd);

        // 往BeanFactory中注册AOP的BeanPostProcessor
        AdvisorAotuProxyCreater aapc = new AdvisorAotuProxyCreater();
        bf.registeBeanPostProcessor(aapc);
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registAdvisor(
                new AspectJPointCutAdvisor("myBeforeAdvice", "execution(* v3.samples.ABean.*(..))"));
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registAdvisor(
                new AspectJPointCutAdvisor("myMethodInterceptor", "execution(* v3.samples.ABean.do*(..))"));
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registAdvisor(new AspectJPointCutAdvisor("myAfterReturningAdvice",
                "execution(* v3.samples.ABean.do*(..))"));
        //使用jdk动态代理
        /*aapc.registAdvisor(
                new AspectJPointCutAdvisor("myBeforeAdvice", "execution(* v3.samples.ABean.*(..))"));
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registAdvisor(
                new AspectJPointCutAdvisor("myMethodInterceptor", "execution(* v3.samples.ABean.do*(..))"));
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.registAdvisor(new AspectJPointCutAdvisor("myAfterReturningAdvice",
                "execution(* v3.samples.ABean.do*(..))"));*/

        bf.preInstantiateSingletons();
        //使用jdk动态代理
//        IABean abean = (IABean) bf.getBean("abean");
        ABean abean = (ABean) bf.getBean("abean");

        abean.doSomthing();
        System.out.println("--------------------------------");
        abean.sayHello();
    }
}
