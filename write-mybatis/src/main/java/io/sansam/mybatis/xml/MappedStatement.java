package io.sansam.mybatis.xml;

import io.sansam.mybatis.common.SqlCommandType;
import io.sansam.mybatis.parameter.ParameterMap;
import io.sansam.mybatis.parameter.RealSqlAndParamValues;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description: 一条sql语句 对应的实体类
 * @author: sansam
 * @Date: 10:19 2019/1/29
 */
@Data
public class MappedStatement {
	/**
	 * 使用方法名 完整类名.方法名
	 */
	private String id;
	/**
	 * 解析的sql语句 初始语句
	 */
	private String sql;
	/**
	 * 解析的sql语句 #{1} ${2} 语句
	 */
	private String placeholderSql;
	/**
	 * sql语句类型
	 */
	private SqlCommandType sqlCommandType;

	/**
	 * 按顺序解析的? 对应的参数信息 的集合
	 */
	private List<ParameterMap> parameterMaps;

	/**
	 *  查找#{属性}，根据Parameter[]中的名称（注解名、序号）匹配确定是第几个参数，
	 * 	再到 Object[] args中取到对应的值。
	 * @param args 方法的入参
	 * @return
	 */
	public RealSqlAndParamValues getRealSqlAndParamValue(Object[] args) throws Exception {
		RealSqlAndParamValues realSqlAndParamValues = new RealSqlAndParamValues();
		List<Object> realArgs = new ArrayList<>();
		for (ParameterMap p : parameterMaps) {
			Object arg = args[p.getIndex()];
			realArgs.add(parseRealParameter(p, arg));
		}
		realSqlAndParamValues.setSql(placeholderSql);
		realSqlAndParamValues.setArgs(realArgs.toArray());
		return realSqlAndParamValues;
	}

	/**
	 * 解析真正的参数
	 * @param p 参数索引和对应的property
	 * @param arg 参数
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private Object parseRealParameter(ParameterMap p, Object arg) throws NoSuchFieldException, IllegalAccessException {
		// 因为ParameterMap是按照#{1} ${2} 这样的顺序存储的 所以每次只需取sql中第一个包含#{}或者${}的部分
		String oldChar = "";
		Object value = null;

		//参数为基础数据类型
		if (arg.toString().equals(p.getProperty())) {
			//判断是否需要加引号 ognl ${}
			value = arg;
		}
		//参数为list
		 else if (arg instanceof List) {

		}
		//参数为map
		else if (arg instanceof Map){

		}
		//参数为对象
		else {
			Field field = arg.getClass().getField(p.getProperty());
			field.setAccessible(true);
			value = field.get(arg).toString();

		}
		placeholderSql = placeholderSql.replace(oldChar, "?");
		return value;
	}


}
