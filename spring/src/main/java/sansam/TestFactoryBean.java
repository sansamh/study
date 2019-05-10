package sansam;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import sansam.v3.samples.factorybean.Person;
import sansam.v3.samples.factorybean.PersonFactoryBean;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 15:26 2018/12/7
 */
public class TestFactoryBean {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Person person = (Person) context.getBean("person");
		System.out.println(person.getClass().getName());
		//v3.samples.factorybean.Person

		//对于实现了FactoryBean接口的类，获取实例时，获取的是FactoryBean.getObject方法产生的对象 而不是对象本身
		Person factoryBean1 = (Person) context.getBean("personFactoryBean");
		System.out.println(factoryBean1.getClass().getName());
		//v3.samples.factorybean.Person

		//对于实现了FactoryBean接口的类，获取实例时，获取的是FactoryBean.getObject方法产生的对象 而不是对象本身
		//想要获取这个类本身 需要使用 &personFactoryBean &符号在前
		PersonFactoryBean factoryBean = (PersonFactoryBean) context.getBean("&personFactoryBean");
		System.out.println(factoryBean.getClass().getName());
		//v3.samples.factorybean.PersonFactoryBean
	}
}
