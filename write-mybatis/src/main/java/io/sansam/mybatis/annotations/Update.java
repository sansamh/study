package io.sansam.mybatis.annotations;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @description:
 * @author: sansam
 * @Date: 10:06 2019/1/29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Update {
	String value();
}
