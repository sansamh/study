package v3.context.reader;

import v3.beans.BeanDefinitionRegistry;

public abstract class AbstractBeanDefinitonReader implements BeanDefinitonReader {

    protected BeanDefinitionRegistry beanDefinitionRegistry;

    public AbstractBeanDefinitonReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }
}
