package v2.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 2.0
 * @description: 预加载单例模式的bean
 * @author: 侯春兵
 * @Date: 14:35 2018/11/29
 */
public class PreBuildBeanFactory extends DefaultBeanFactory {

    private Logger logger = LoggerFactory.getLogger(PreBuildBeanFactory.class);

    private List<String> beanNames = new ArrayList<>();


    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistryException {
        super.registryBeanDefinition(beanName, beanDefinition);
        //TODO 替换成lock锁方式
        synchronized (beanNames) {
            beanNames.add(beanName);
        }
    }

    public void preInstantiateSingletons() throws Exception {
        synchronized (beanNames) {
            for (String beanName : beanNames) {
                BeanDefinition beanDefinition = getBeanDefinition(beanName);
                if (beanDefinition.isSingleton()) {
                    this.doGetBean(beanName);
                    if (logger.isDebugEnabled()) {
                        logger.debug("preInstantiate: name=" + beanName + " " + beanDefinition);
                    }
                }
            }
        }
    }
}
