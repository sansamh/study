package sansam.v3.context.reader;

import sansam.v3.context.resource.Resource;

public interface BeanDefinitonReader {

    void loadBeanDefinition(Resource resource);

    void loadBeanDefinition(Resource ... resources);
}
