package io.sansam.mybatis.parameter;

import io.sansam.mybatis.xml.MappedStatement;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @description: 解析sql 成 #{p1} 根据参数 构造parameterMap
 * @author: sansam
 * @Date: 16:58 2019/1/29
 */
@Data
public class SqlAndParameterParser {

	/**
	 * 解析sql 成 #{1} ${2} 根据参数 构造parameterMap
	 *
	 * @param ms
	 * @param sql
	 * @param method
	 * @return
	 */
	public static List<ParameterMap> parse(MappedStatement ms, String sql, Method method) {
		String placeHolderSql = "";
		List<ParameterMap> list = new ArrayList<>();
		ms.setPlaceholderSql(placeHolderSql);
		return list;
	}
}

