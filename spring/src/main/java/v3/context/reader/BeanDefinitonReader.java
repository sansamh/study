package v3.context.reader;

import v3.context.resource.Resource;

public interface BeanDefinitonReader {

    void loadBeanDefinition(Resource resource);

    void loadBeanDefinition(Resource ... resources);
}
