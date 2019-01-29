package io.sansam.mybatis.xml;

import lombok.Data;

/**
 * @version 1.0
 * @description: sql语句和参数值
 * @author: sansam
 * @Date: 10:33 2019/1/29
 */
@Data
public class SqlAndParamValues {
	/**
	 * 解析的sql语句
	 */
	private String sql;
	/**
	 * 参数值
	 */
	private ParamValue[] paramValues;
}
