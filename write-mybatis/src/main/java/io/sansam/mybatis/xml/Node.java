package io.sansam.mybatis.xml;

/**
 * @version 1.0
 * @description: sql节点信息
 * @author: sansam
 * @Date: 10:32 2019/1/29
 */
public interface Node {
	/**
	 * 获取真正的参数和sql
	 * @param args 方法参数
	 * @return
	 */
	SqlAndParamValues getRealSqlAndParamValues(Object[] args);
}
