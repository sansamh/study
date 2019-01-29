package io.sansam.mybatis.parameter;

import lombok.Data;

/**
 * @version 1.0
 * @description: 解析出来的真正的sql语句 参数信息
 * @author: sansam
 * @Date: 16:49 2019/1/29
 */
@Data
public class RealSqlAndParamValues {

	private String sql;

	/**
	 * 从入参解析出的? 填充参数
	 */
	private Object[] args;

}
