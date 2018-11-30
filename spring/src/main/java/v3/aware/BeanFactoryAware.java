package v3.aware;

import v3.beans.BeanFactory;

/**
 * @version 3.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:43 2018/11/30
 */
public interface BeanFactoryAware extends Aware {

	void setBeanFactory(BeanFactory beanFactory);
}
