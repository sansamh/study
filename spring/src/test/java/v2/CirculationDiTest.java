package v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import v2.beans.BeanReference;
import v2.beans.GenericBeanDefinition;
import v2.beans.PreBuildBeanFactory;
import v2.samples.DBean;
import v2.samples.EBean;


public class CirculationDiTest {

	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testCirculationDI() throws Exception {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(DBean.class);
		List<Object> args = new ArrayList<>();
		args.add(new BeanReference("ebean"));
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("dbean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(EBean.class);
		args = new ArrayList<>();
		args.add(new BeanReference("dbean"));
		bd.setConstructorArgumentValues(args);
		bf.registryBeanDefinition("ebean", bd);

		bf.preInstantiateSingletons();
	}
}
