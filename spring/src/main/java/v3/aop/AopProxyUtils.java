package v3.aop;

import org.springframework.util.CollectionUtils;
import v3.advisor.Advisor;
import v3.advisor.PointCutAdvisor;
import v3.beans.BeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 3.0
 * @description:
 * @author: 侯春兵
 * @Date: 14:35 2018/11/30
 */
public class AopProxyUtils {

	public static Object applyAdvice(Object target, Method method, Object [] args, List<Advisor> matchAdvisor,
									 Object proxy, BeanFactory beanFactory) throws Exception {
		//1.获取与方法匹配的advisor里的advice对象
		List<Object> shouldApplyAdvice = getShouldApplyAdvice(target.getClass(), method, matchAdvisor, beanFactory);
		//2.增强 如果没有advice bean 直接调用 返回
		if (CollectionUtils.isEmpty(shouldApplyAdvice)) {
			return method.invoke(target, args);
		} else {
			//责任链调用 增强

		}

		return null;
	}

	private static List<Object> getShouldApplyAdvice(Class<?> aClass, Method method, List<Advisor> matchAdvisor, BeanFactory beanFactory) throws Exception {
		if (CollectionUtils.isEmpty(matchAdvisor)) {
			return null;
		}
		List<Object> list = new ArrayList<>();
		//匹配方法 找到advice bean
		for (Advisor advisor : matchAdvisor) {
			if (advisor instanceof PointCutAdvisor) {
				if ( ((PointCutAdvisor) advisor).getPointCut().matchMethod(method) ) {
					list.add(beanFactory.getBean(advisor.getAdviceBeanName()));
				}
			}
		}
		return list;
	}
}
