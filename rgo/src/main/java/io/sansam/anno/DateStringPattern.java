package io.sansam.anno;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Pattern
public @interface DateStringPattern {

    String pattern() default "yyyyMMdd";
}
