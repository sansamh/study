package v1;

import org.junit.AfterClass;
import v1.beans.BeanDefinition;
import v1.beans.BeanDefinitionRegistryException;
import v1.beans.DefaultBeanFactory;
import v1.beans.GenericBeanDefinition;
import v1.sample.TestBean;
import v1.sample.TestBeanFactory;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:38 2018/11/27
 */

public class Test {

    static DefaultBeanFactory beanFactory = new DefaultBeanFactory();

    @org.junit.Test
    public void testRegistry() throws BeanDefinitionRegistryException {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(TestBean.class);
        bd.setScope(BeanDefinition.SCOPE_SINGLETION);
        bd.setInitMethodName("init");
        bd.setDestroyMethodName("destroy");

        beanFactory.registryBeanDefinition("testBean", bd);
    }

    @org.junit.Test
    public void testRegistStaticFactoryMethod() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(TestBeanFactory.class);
        bd.setFactoryMethodName("getStaticTestBean");
        beanFactory.registryBeanDefinition("staticTestBean", bd);
    }

    @org.junit.Test
    public void testRegistFactoryMethod() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(TestBeanFactory.class);
        String fbname = "factory";
        beanFactory.registryBeanDefinition(fbname, bd);

        bd = new GenericBeanDefinition();
        bd.setFactoryBeanName(fbname);
        bd.setFactoryMethodName("getTestBean");
        bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);

        beanFactory.registryBeanDefinition("factoryTestBean", bd);
    }

    @AfterClass
    public static void testGetBean() throws Exception {
        System.out.println("构造方法方式------------");
        for (int i = 0; i < 3; i++) {
            TestBean ab = (TestBean) beanFactory.getBean("testBean");
            ab.doSomthing();
        }

        System.out.println("静态工厂方法方式------------");
        for (int i = 0; i < 3; i++) {
            TestBean ab = (TestBean) beanFactory.getBean("staticTestBean");
            ab.doSomthing();
        }

        System.out.println("工厂方法方式------------");
        for (int i = 0; i < 3; i++) {
            TestBean ab = (TestBean) beanFactory.getBean("factoryTestBean");
            ab.doSomthing();
        }
        System.out.println("beanFactory close------------");
        beanFactory.close();
    }
}
