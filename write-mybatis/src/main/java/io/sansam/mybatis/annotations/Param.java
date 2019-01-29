package io.sansam.mybatis.annotations;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @description: 指定参数名的注解
 * @author: sansam
 * @Date: 11:11 2019/1/29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
	/**
	 * 2. 规则： ？ 用 #{属性名} 代替，我们来解析SQL语句中的 #{属性名} 来决定参数对应
	 * 3. 规则： ${属性名} 表示这里是字符串替换
	 */
	String value();
}
