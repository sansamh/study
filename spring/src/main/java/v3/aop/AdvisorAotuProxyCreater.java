package v3.aop;

import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import v3.advisor.Advisor;
import v3.advisor.AdvisorRegistry;
import v3.advisor.PointCutAdvisor;
import v3.aware.BeanFactoryAware;
import v3.beans.BeanFactory;
import v3.beans.BeanPostProcessor;
import v3.pointcut.PointCut;

import java.lang.reflect.Method;
import java.util.*;

public class AdvisorAotuProxyCreater implements BeanPostProcessor, AdvisorRegistry, BeanFactoryAware {

    private List<Advisor> advisors;

    private BeanFactory beanFactory;

    public AdvisorAotuProxyCreater() {
        this.advisors = new ArrayList<>(16);
    }

    @Override
    public void registAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    public List<Advisor> getAdvisor() {
        return advisors;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        //判断bena是否需要增强
        List<Advisor> matchAdvisors = getMatchAdvisors(bean, beanName);

        if (CollectionUtils.isEmpty(matchAdvisors)) {
            return bean;
        }

        return createAopProxy(bean, beanName,matchAdvisors);


    }

    private Object createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors) throws Throwable{
        return AopProxyFactory.getDefaultAopProxyFactory().createAopProxy(beanName, bean, matchAdvisors, beanFactory).getProxy();
    }

    private List<Advisor> getMatchAdvisors(Object bean, String beanName) {
        if (CollectionUtils.isEmpty(advisors)) {
            return null;
        }
        List<Advisor> match = new ArrayList<>(16);
        //获取bean的所有方法
        List<Method> allMethods = getAllMethod(bean);

        for (Advisor advisor : advisors) {
            if (advisor instanceof PointCutAdvisor) {
               if (isPointCutAdvisor((PointCutAdvisor)advisor, bean.getClass(), allMethods)) {
                   match.add(advisor);
               }
            }
        }

        return match;
    }

    private boolean isPointCutAdvisor(PointCutAdvisor advisor, Class<?> aClass, List<Method> allMethods) {
        PointCut pointCut = advisor.getPointCut();

        if (!pointCut.matchClass(aClass)) {
            return false;
        }

        for (Method method : allMethods) {
            if (pointCut.matchMethod(method)) {
                return true;
            }
        }


        return false;
    }

    private List<Method> getAllMethod(Object bean) {
        List<Method> allMethods = new LinkedList<>();
        Set<Class<?>> allInterfacesForClassAsSet = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        //添加自己的class
        allInterfacesForClassAsSet.add(bean.getClass());
        for (Class<?> aClass : allInterfacesForClassAsSet) {
            allMethods.addAll(Arrays.asList(ReflectionUtils.getAllDeclaredMethods(aClass)));
        }
        return allMethods;
    }
}
