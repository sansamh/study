package io.sansam.mybatis.config;

import io.sansam.mybatis.parse.annotation.MapperAnnotationBuilder;
import io.sansam.mybatis.parse.xml.XMLMapperBuilder;
import io.sansam.mybatis.parse.xml.XMLStatementBuilder;
import io.sansam.mybatis.xml.MappedStatement;
import lombok.Data;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @description: 统一配置信息存放类
 * @author: sansam
 * @Date: 10:21 2019/1/29
 */
@Data
public class Configuration {
	/**
	 * sql语句集合
	 */
	private ConcurrentHashMap<String, MappedStatement> mappedStatements;
	/**
	 * xml解析器
	 */
	private XMLMapperBuilder xmlMapperBuilder;
	/**
	 * 注解解析器
	 */
	private MapperAnnotationBuilder mapperAnnotationBuilder;
	/**
	 * 指定扫描的root包
	 */
	private String name;
	/**
	 * 指定扫描的root包 下的 接口类型
	 */
	private Class type;
	/**
	 * 指定扫描的root包 下的 接口类型 包含此注解的
	 * 1. 约定俗成的规则：指定包下扫到的@Mapper接口，例如UserDao，还可以在包下定义 UserDao.xml，会被加载解析
	 */
	private Annotation annotation;
	/**
	 * 数据源
	 */
	private DataSource dataSource;
	/**
	 * 所有的接口类
	 */
	private Set<Class> mappers;

	//
	public void addMappedStatement(MappedStatement ms) {
		this.mappedStatements.put(ms.getId(), ms);
	}
	public MappedStatement getMappedStatement(String id) {
		return this.mappedStatements.get(id);
	}
	public boolean hasMappedStatement(String id) {
		return this.mappedStatements.contains(id);
	}
	//
	public void addXmlMapper(InputStream is, String resource) {
		xmlMapperBuilder.parse(is, resource);
		// 解析出nameSpace 对应一个mapper类
		Class mapperClass = Class.class;
		addMapperClass(mapperClass);
	}
	//
	public void addAnnotationMapper(Class<?> clz) {
		mapperAnnotationBuilder.parse(clz);
		addMapperClass(clz);
	}
	//
	public void addMappers(String basePackage) {
		// 解析出nameSpace 对应一个mapper类
		Class mapperClass = Class.class;
		addMapperClass(mapperClass);
	}
	public void addMappers(String basePackage, Class<?> superType) {
		// 解析出nameSpace 对应一个mapper类
		Class mapperClass = Class.class;
		addMapperClass(mapperClass);
	}
	public void addMappers(String basePackage, Class<?> superType, Annotation annotation) {
		// 解析出nameSpace 对应一个mapper类
		Class mapperClass = Class.class;
		addMapperClass(mapperClass);
	}
	//

	/**
	 * 添加mapper class
	 * @param clz
	 */
	public void addMapperClass(Class<?> clz) {
		mappers.add(clz);
	}
}
