package io.sansam.mybatis.parse.xml;

import io.sansam.mybatis.config.Configuration;
import io.sansam.mybatis.xml.Node;
import io.sansam.mybatis.xml.SqlAndParamValues;
import lombok.Data;

import java.io.InputStream;

/**
 * @version 1.0
 * @description: xml文件解析类
 * @author: sansam
 * @Date: 10:26 2019/1/29
 */
@Data
public class XMLMapperBuilder {
	/**
	 * 配置信息中心
	 */
	private Configuration configuration;
	/**
	 * 单个语句解析器
	 */
	private XMLStatementBuilder xmlStatementBuilder;

	/**
	 *
	 * @param is xml文件流
	 * @param resource 文件来源
	 */
	public void parse(InputStream is, String resource){
		// 通过is 获取namespace
		String nameSpace = "";
		// 解析出单个node
		Node node = new Node() {
			@Override
			public SqlAndParamValues getRealSqlAndParamValues(Object[] args) {
				return null;
			}
		};
		xmlStatementBuilder.parse(configuration, nameSpace, node);
	}

}
