package io.sansam.mybatis.sqlsession;

import io.sansam.mybatis.exception.MybatisException;

/**
 * @version 1.0
 * @description: 执行sql过程的接口
 * @author: sansam
 * @Date: 11:15 2019/1/29
 */
public interface SqlSession {
	/**
	 * UserDao userDao = sqlSession.getMapper(UserDao.class);
	 * userDao.addUser(user);
	 *
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	<T> T getMapper(Class<T> tClass) throws MybatisException;
}
