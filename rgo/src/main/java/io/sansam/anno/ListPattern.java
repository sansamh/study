package io.sansam.anno;

import java.lang.annotation.*;
import java.util.ArrayList;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Pattern
public @interface ListPattern {

    Class clz() default ArrayList.class;

    String length() default "4";

}
