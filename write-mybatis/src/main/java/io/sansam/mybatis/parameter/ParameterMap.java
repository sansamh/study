package io.sansam.mybatis.parameter;

import lombok.Data;

/**
 * @version 1.0
 * @description: 按顺序解析的sql语句 ? 对应的是哪个参数 的那个属性
 * @author: sansam
 * @Date: 16:45 2019/1/29
 */
@Data
public class ParameterMap {
	/**
	 * ?对应的参数索引号
	 */
	private int index;

	/**
	 * 对应参数的哪个属性
	 */
	private String property;
}
