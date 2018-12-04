package v3.context.application;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import v3.beans.BeanDefinitionRegistry;
import v3.context.reader.BeanDefinitonReader;
import v3.context.reader.XmlBeanDefinitonReader;
import v3.context.resource.ClassPathResource;
import v3.context.resource.FileSystemResource;
import v3.context.resource.Resource;
import v3.context.resource.UrlPathResource;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class XmlApplicationContext extends AbstractApplicationContext {

    private List<Resource> resources;

    private BeanDefinitonReader reader;

    public XmlApplicationContext(String ... location) throws MalformedURLException {
        //加载resource资源
        load(location);
        this.reader = new XmlBeanDefinitonReader((BeanDefinitionRegistry) this.beanFactory);
        reader.loadBeanDefinition((Resource[]) resources.toArray());
    }

    private void load(String ... location) throws MalformedURLException {
        if (CollectionUtils.isEmpty(resources)) {
            resources = new ArrayList<>(16);
        }
        if (location != null && location.length > 0) {
            for (String s : location) {
                Resource resource = this.getResource(s);
                if (resource != null) {
                    resources.add(resource);
                }
            }
        }

    }

    @Override
    public Resource getResource(String location) throws MalformedURLException {
        if (!StringUtils.isEmpty(location)) {
            if (location.startsWith(Resource.CLASS_PATH_PREFIX)) {
                //classpath: 开头
                return new ClassPathResource(location.substring(Resource.CLASS_PATH_PREFIX.length()));
            }

            if (location.startsWith(Resource.File_SYSTEM_PREFIX)) {
                //file: 开头
                return new FileSystemResource(location.substring(Resource.CLASS_PATH_PREFIX.length()));
            } else {
                //url地址
                return new UrlPathResource(location);
            }
        }
        return null;
    }
}
