package v3.context.application;

import v3.beans.BeanFactory;
import v3.beans.BeanPostProcessor;
import v3.beans.PreBuildBeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext {
    /**
     * 子类可以使用的beanFactory
     */
    protected BeanFactory beanFactory;

    public AbstractApplicationContext() {
        this.beanFactory = new PreBuildBeanFactory();
    }

    //未重写getResource方法 子类实现

    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }

    @Override
    public void registeBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanFactory.registeBeanPostProcessor(beanPostProcessor);
    }

}
