package v3.context.reader;

import v3.beans.BeanDefinitionRegistry;
import v3.context.resource.Resource;

public class AnnotationBeanDefinitonReader extends AbstractBeanDefinitonReader {

    public AnnotationBeanDefinitonReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    @Override
    public void loadBeanDefinition(Resource resource) {

    }

    @Override
    public void loadBeanDefinition(Resource... resources) {

    }
}
