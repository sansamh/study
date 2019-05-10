package sansam.v3.context.reader;


import org.apache.commons.lang3.StringUtils;
import sansam.v3.beans.BeanDefinitionRegistry;
import sansam.v3.beans.BeanDefinitionRegistryException;
import sansam.v3.beans.GenericBeanDefinition;
import sansam.v3.context.config.annotation.Autowired;
import sansam.v3.context.config.annotation.Component;
import sansam.v3.context.resource.Resource;

import java.io.File;
import java.lang.reflect.Constructor;

public class AnnotationBeanDefinitonReader extends AbstractBeanDefinitonReader{


    public AnnotationBeanDefinitonReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        if (resource != null) {
            this.loadBeanDefinition(new Resource[]{resource});
        }
    }

    @Override
    public void loadBeanDefinition(Resource... resources) {
        if (resources != null && resources.length > 0) {
            for (Resource resource : resources) {
                parseFileAndRegistBeanDefinition(resource);
            }
        }
    }

    private void parseFileAndRegistBeanDefinition(Resource resource) {
        if (resource != null && resource.getFile() != null) {
            String className = getClassNameFromFile(resource.getFile());
            try {
                Class<?> clz = Class.forName(className);
                Component component = clz.getAnnotation(Component.class);
                if (null != component) {
                    //标注了@Component注解 需要注册为beanDefinition
                    GenericBeanDefinition bd = new GenericBeanDefinition();
                    bd.setBeanClass(clz);
                    bd.setScope(component.scope());
                    bd.setFactoryMethodName(component.factoryMethodName());
                    bd.setFactoryBeanName(component.factoryBeanName());
                    bd.setInitMethodName(component.initMethodName());
                    bd.setDestroyMethodName(component.destroyMethodName());

                    // 获得所有构造方法，在构造方法上找@Autowired注解，如有，将这个构造方法set到bd;
                    this.handleConstructor(clz, bd);

                    String beanName = "".equals(component.value()) ? component.name() : null;
                    if (StringUtils.isBlank(beanName)) {
                        // 应用名称生成规则生成beanName 第一个字母小写;
                        beanName = beanName.substring(0,1).toLowerCase() + beanName.substring(1);
                    }
                    // 注册bean定义
                    this.beanDefinitionRegistry.registryBeanDefinition(beanName, bd);

                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (BeanDefinitionRegistryException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConstructor(Class<?> clz, GenericBeanDefinition bd) {
        Constructor<?>[] constructors = clz.getConstructors();
        if (constructors != null && constructors.length > 0) {
            for (Constructor<?> c : constructors) {
                if (c.getDeclaredAnnotation(Autowired.class) != null) {
                    bd.setConstructor(c);
                    // 遍历获取参数上的注解，及创建参数依赖
                    this.handleConstructorParameters(c, bd);
                    break;
                }
            }
        }
    }

    private void handleConstructorParameters(Constructor<?> c, GenericBeanDefinition bd) {
        Class<?>[] parameterTypes = c.getParameterTypes();
        if (parameterTypes != null && parameterTypes.length > 0) {
            for (Class<?> type : parameterTypes) {

            }
        }
    }

    /**
     * 根目录路径的长度
     */
    private int rootPathLength = AnnotationBeanDefinitonReader.class.getClassLoader().getResource("/").toString().length();

    /**
     * 获取文件名 /com/xxx/abc.class
     * @param file
     * @return abc
     */
    private String getClassNameFromFile(File file) {
        String absolutePath = file.getAbsolutePath();
        String name = absolutePath.substring(rootPathLength + 1, absolutePath.indexOf("."));
        return name;
    }
}
