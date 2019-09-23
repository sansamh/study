package io.sansam.anno;

import java.lang.annotation.*;
import java.util.HashMap;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Pattern
public @interface MapPattern {

    Class clz() default HashMap.class;

    String length() default "4";
}
