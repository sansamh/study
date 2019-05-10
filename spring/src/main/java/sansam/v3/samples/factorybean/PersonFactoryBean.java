package sansam.v3.samples.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 15:21 2018/12/7
 */
public class PersonFactoryBean  implements FactoryBean {
	@Override
	public Object getObject() throws Exception {
		Person bean = new Person();
		bean.setName("PersonFactoryBean#getObject");
		bean.setAge(18);
		bean.setAddress("earth");
		return bean;
	}

	@Override
	public Class<?> getObjectType() {
		return PersonFactoryBean.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
