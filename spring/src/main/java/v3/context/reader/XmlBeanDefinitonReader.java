package v3.context.reader;

import v3.beans.BeanDefinitionRegistry;
import v3.context.resource.Resource;

public class XmlBeanDefinitonReader extends AbstractBeanDefinitonReader {

    public XmlBeanDefinitonReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        parseXml(resource);
    }

    @Override
    public void loadBeanDefinition(Resource... resources) {
        if (resources != null && resources.length > 0) {
            for (Resource r : resources) {
                loadBeanDefinition(r);
            }
        }
    }

    private void parseXml(Resource resource) {
        // TODO 解析xml文档，获取bean定义 ，创建bean定义对象，注册到BeanDefinitionRegistry中。
    }
}
