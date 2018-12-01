### v3 说明
1. beanDefinition添加setConstructorArgumentRealValues方法，缓存构造函数真正的属性值。提取缓存构造方法和缓存真正属性值方法到createBeanByConstructure中，方便后续AOP使用

2. PointCut接口定义了切入点的规则，类名是否匹配和方法名是否匹配表达式。实现类AspectJExpressionPointCut，构造函数需要传入匹配的表达式，通过PointcutParser获取PointcutExpression进行前面的匹配操作

3. Advice接口是增强类的父接口，实现类有前置、环绕和后置增强

4. Advisor接口是Pointcut和Advice的配置器，它包括AdviceBeanName和表达式，是将Advice注入程序中Pointcut位置的代码
5. PointCutAdvisor接口实现了Advisor接口，同时增加了获取PointCut的方法
6. AspectJPointCutAdvisor接口实现了PointCutAdvisor接口，通过构造函数获得AdviceBeanName和表达式，表达式生成PointCut对象

7. Aware接口的实现接口BeanFactoryAware接口，每个实现这个接口的类需要设置beanFactory，方便获取beanDefinition
8. BeanPostProcessor接口是标识bean需要被处理的接口，postProcessBeforeInitialization方法标识在bean初始化之前需要做的事情（未处理，直接返回bean）。postProcessAfterInitialization方法，是bean初始化之后需要处理的方法，我们用来实现AOP增强。
9. AdvisorRegistry接口是需要注册Advisor和获取Advisor集合的接口
10. AdvisorAotuProxyCreater是继承了以上三个接口的承上启下的类，包含了bean实例化之前和之后需要处理的方法，设置beanFactory的方法、注册Advisor的方法和获取Advisor集合的方法
11. BeanFactory接口添加registeBeanPostProcessor方法，我们会在DefaultBeanFactory里添加beanPostProcessorList这个集合存放BeanPostProcessor，把我们需要bean增强处理的类放在这里面
12. AOP的实现采用观察者模式，每次新创建一个bean的时候，调用applyPostProcessAfterInitialization方法，唤醒beanPostProcessorList中的每个beanPostProcessor进行后续处理（调用postProcessAfterInitialization方法）

13. AdvisorAotuProxyCreater的postProcessAfterInitialization方法，我们用来处理AOP增强，具体逻辑为
```
// 1.1 判断bena是否需要增强 beanName在CGLIB创建代理对象时，如果没有获取到无参构造方法，需要根据beanName获取beanDefiniton里缓存的构造方法和构造方法具体参数
List<Advisor> matchAdvisors = getMatchAdvisors(bean, beanName);
    // 1.2 getMatchAdvisors逻辑为如果没有advisor返回null
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
    }
    // 1.3 判断哪些advisor是符合条件的增强
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
// 2 没有增强直接返回bean
if (CollectionUtils.isEmpty(matchAdvisors)) {
    return bean;
}
// 3 生成代理对象
return createAopProxy(bean, beanName,matchAdvisors);
    // 3.1 工厂模式来产生代理对象
    private Object createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors) throws Throwable{
        return AopProxyFactory.getDefaultAopProxyFactory().createAopProxy(beanName, bean, matchAdvisors, beanFactory).getProxy();
    }
```

14. AopProxyFactory产生代理对象的工厂父接口，包含createAopProxy方法和获取默认的工厂
15. DefaultAopProxyFactory实现AopProxyFactory接口，在createAopProxy方法里，我们需要实现判断类具体采用哪种方式来生成代理对象

16. AopProxy是获取动态代理类的父接口，包含getProxy和getProxy(ClassLoader classLoader)两个方法来获取
17. CglibDynamicAopProxy继承AopProxy和MethodInterceptor接口，使用CGLIB方式生成动态代理对象
18. JDKDynamicAopProxy继承AopProxy和InvocationHandler接口，使用JDK动态代理方式生成动态代理对象

19. AopProxyUtils是上面两种动态代理具体需要执行代理方法的通用类，applyAdvice方法将增强类的方法织入到具体方法
```
具体织入逻辑为：
// 1.获取与方法匹配的advisor里的advice对象
List<Object> shouldApplyAdvice = getShouldApplyAdvice(target.getClass(), method, matchAdvisor, beanFactory);
    // 1.1 匹配方法 找到advice对应的bean
    for (Advisor advisor : matchAdvisor) {
        if (advisor instanceof PointCutAdvisor) {
            if ( ((PointCutAdvisor) advisor).getPointCut().matchMethod(method) ) {
                list.add(beanFactory.getBean(advisor.getAdviceBeanName()));
            }
        }
    }
// 2.1 如果shouldApplyAdvice为空，说明方法没有匹配到增强方法 直接返回bean
if (CollectionUtils.isEmpty(shouldApplyAdvice)) {
    return method.invoke(target, args);
} else {
    // 2.2 责任链调用 增强
    AopAdviceChainInvocation chainInvocation = new AopAdviceChainInvocation(shouldApplyAdvice, target, proxy, method, args);
    return chainInvocation.invoke();
}
```
20. AopAdviceChainInvocation采用责任链调用增强，具体逻辑为
```
static {
    try {
        invokeMethod = AopAdviceChainInvocation.class.getMethod("invoke");
    } catch (NoSuchMethodException e) {
        e.printStackTrace();
    }
}
//标识位
private int i = 0;
public Object invoke() throws InvocationTargetException, IllegalAccessException {
    if (i < advisors.size()) {
        Object advice = advisors.get(i++);
        if (advice instanceof MethodBeforeAdvice) {
            // 执行前置增强
            ((MethodBeforeAdvice) advice).invoke(method, args, target);

        } else if (advice instanceof MethodIntercepterAdvice) {
            // 执行环绕增强和异常处理增强。注意这里给入的method 和 对象 是invoke方法和链对象
            // 环绕增强 相当于 环绕BEGIN -> 接下来的所有增强 -> 环绕END 所以在这里我们要传入自身invoke方法, target为AopAdviceChainInvocation 继续调用自身invoke执行接下来的增强
            return ((MethodIntercepterAdvice) advice).invoke(invokeMethod, null, this);

        } else if (advice instanceof MethodAfterAdvice) {
            // 执行后置增强 先得到被增强方法结果，再执行后置增强逻辑
            // 如果只有一个后置增强 运行到这里 继续调用本身 此时 i==advisors.size() 执行 return method.invoke(target, args); 得到方法本身的返回值 == invoke
            // 再走 ((MethodAfterAdvice) advice).invoke(invoke, method, args, target); 后置增强方法 返回invoke
            Object invoke = this.invoke();
            ((MethodAfterAdvice) advice).invoke(invoke, method, args, target);
            return invoke;
        }

        return this.invoke();
    } else {
        return method.invoke(target, args);
    }
}
```