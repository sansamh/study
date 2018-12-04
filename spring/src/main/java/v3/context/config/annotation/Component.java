package v3.context.config.annotation;

import v3.beans.BeanDefinition;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

	String value() default "";

	String name() default "";

	String scope() default BeanDefinition.SCOPE_SINGLETION;

	String factoryMethodName() default "";

	String factoryBeanName() default "";

	String initMethodName() default "";

	String destroyMethodName() default "";
}
