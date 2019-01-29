package io.sansam.mybatis.parse.xml;

import io.sansam.mybatis.common.SqlCommandType;
import io.sansam.mybatis.config.Configuration;
import io.sansam.mybatis.parameter.SqlAndParameterParser;
import io.sansam.mybatis.xml.MappedStatement;
import io.sansam.mybatis.xml.Node;

import java.lang.reflect.Method;

/**
 * @version 1.0
 * @description: 解析mapper里的单个sql语句
 * @author: sansam
 * @Date: 10:28 2019/1/29
 */
public class XMLStatementBuilder {

	/**
	 * 解析单个节点用
	 * @param configuration
	 * @param nameSpace
	 * @param node
	 */
	public void parse(Configuration configuration, String nameSpace, Node node){
		// 解析出node的id 通过namespace.id 作为 mappedStatement的id
		String id = "";
		String msId= nameSpace + "." + id;
		// 解析出sql语句
		String sql = "";
		// 解析出sql类型
		SqlCommandType sqlCommandType = SqlCommandType.Select;
		// 构造ms对象
		MappedStatement ms = new MappedStatement();
		ms.setId(msId);
		ms.setSql(sql);
		ms.setSqlCommandType(sqlCommandType);

		// 通过nameSpace找到mapper类， id找到方法， 解析parameterMap
		try {
			Class<?> mapper = Class.forName(nameSpace);
			Method[] methods = mapper.getMethods();
			Method parseMethod = null;
			 for (Method me : methods) {
				if (id.equalsIgnoreCase(me.getName())) {
					parseMethod = me;
					break;
				}
			 }
			ms.setParameterMaps(SqlAndParameterParser.parse(ms, sql, parseMethod));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 放入configuration
		configuration.addMappedStatement(ms);

	}
}
