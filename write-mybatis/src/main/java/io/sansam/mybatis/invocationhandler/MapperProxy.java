package io.sansam.mybatis.invocationhandler;

import io.sansam.mybatis.config.Configuration;
import io.sansam.mybatis.parameter.RealSqlAndParamValues;
import io.sansam.mybatis.xml.MappedStatement;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @version 1.0
 * @description: 动态代理 invocationhandler
 * @author: sansam
 * @Date: 11:47 2019/1/29
 */
@Data
public class MapperProxy implements InvocationHandler {

	private Configuration configuration;

	/**
	 * 需要增强的类 通过类名+.+方法名 获取mappedStatement
	 */
	private Class<?> mapper;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 1、获得方法对应的SQL语句
		String id = mapper.getName() + "." + method.getName();
		MappedStatement mappedStatement = configuration.getMappedStatement(id);
		// 2、解析SQL参数与方法参数的对应关系，得到真正的SQL与语句参数值
		RealSqlAndParamValues realSqlAndParamValue = mappedStatement.getRealSqlAndParamValue(args);
		// 3、获得数据库连接
		Connection connection = this.configuration.getDataSource().getConnection();
		// 4、执行语句
		connection.prepareStatement(realSqlAndParamValue.getSql());
		// 5、处理结果
		return null;
	}
}
