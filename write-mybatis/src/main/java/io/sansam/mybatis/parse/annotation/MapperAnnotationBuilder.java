package io.sansam.mybatis.parse.annotation;

import io.sansam.mybatis.config.Configuration;
import lombok.Data;

/**
 * @version 1.0
 * @description: 解析注解
 * @author: sansam
 * @Date: 10:47 2019/1/29
 */
@Data
public class MapperAnnotationBuilder {
	private Configuration configuration;

	public void parse(Class<?> clz) {

	}
}
