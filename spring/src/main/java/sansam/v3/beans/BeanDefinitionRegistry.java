package sansam.v3.beans;

/**
 * @version 2.0
 * @description:
 * @author: 侯春兵
 * @Date: 14:20 2018/11/27
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册beanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistryException;

    /**
     * 根据beanName获取beanDefinition
     *
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 校验是否存在包含此beanName的beanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);
}
