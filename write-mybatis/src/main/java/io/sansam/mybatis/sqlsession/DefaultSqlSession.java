package io.sansam.mybatis.sqlsession;

import io.sansam.mybatis.config.Configuration;
import io.sansam.mybatis.exception.MybatisException;
import lombok.Data;

/**
 * @version 1.0
 * @description: 默认的sqlSession
 * @author: sansam
 * @Date: 11:16 2019/1/29
 */
@Data
public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 用户给入一个接口类，DefaultSqlSession中就为它生成一个对象？
	 * 万一给入的不是一个Mapper接口呢？
	 * 也为其生成一个对象就不合理了？
	 * 那怎么判断给入的接口类是否是一个Mapper接口呢？
	 * 那就只有在配置阶段扫描、解析Mapper接口时做个存储了。
	 * 存哪，用什么存？
	 * 这也是配置信息，还是存在Configuration 中，就用个Set来存吧。
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T getMapper(Class<T> tClass) throws MybatisException{
		if (!configuration.getMappers().contains(tClass)) {
			throw new MybatisException("不存在这样的mapper类！");
		}
		return null;
	}
}
