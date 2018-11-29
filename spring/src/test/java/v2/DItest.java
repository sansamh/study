package v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import v2.beans.*;
import v2.samples.ABean;
import v2.samples.ABeanFactory;
import v2.samples.CBean;
import v2.samples.CCBean;


public class DItest {
	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testConstructorDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABean.class);
		List<Object> args = new ArrayList<>();
		args.add("abean01");
		args.add(new BeanReference("cbean"));
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("abean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(CBean.class);
		args = new ArrayList<>();
		args.add("cbean01");
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("cbean", bd);

		bf.preInstantiateSingletons();

		ABean abean = (ABean) bf.getBean("abean");

		abean.doSomthing();
	}

	@Test
	public void testStaticFactoryMethodDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABeanFactory.class);
		bd.setFactoryMethodName("getABean");
		List<Object> args = new ArrayList<>();
		args.add("abean02");
		args.add(new BeanReference("cbean02"));
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("abean02", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(CBean.class);
		args = new ArrayList<>();
		args.add("cbean02");
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("cbean02", bd);

		bf.preInstantiateSingletons();

		ABean abean = (ABean) bf.getBean("abean02");

		abean.doSomthing();
	}

	@Test
	public void testFactoryMethodDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setFactoryBeanName("abeanFactory");
		bd.setFactoryMethodName("getABean2");
		List<Object> args = new ArrayList<>();
		args.add("abean03");
		args.add(new BeanReference("cbean02"));
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("abean03", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(ABeanFactory.class);
		bf.registryBeanDefinition("abeanFactory", bd);

		bf.preInstantiateSingletons();

		ABean abean = (ABean) bf.getBean("abean03");

		abean.doSomthing();
	}

	@Test
	public void testChildTypeDI() throws Exception {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABean.class);
		List<Object> args = new ArrayList<>();
		args.add("abean04");
		args.add(new BeanReference("ccbean01"));
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("abean04", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(CCBean.class);
		args = new ArrayList<>();
		args.add("Ccbean01");
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("ccbean01", bd);

		bf.preInstantiateSingletons();

		ABean abean = (ABean) bf.getBean("abean04");

		abean.doSomthing();
	}
}
