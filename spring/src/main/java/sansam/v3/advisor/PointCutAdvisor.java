package sansam.v3.advisor;

import sansam.v3.pointcut.PointCut;

/**
 * @version 3.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:24 2018/11/30
 */
public interface PointCutAdvisor extends Advisor {

	/**
	 * 获取切入点
	 * @return
	 */
	PointCut getPointCut();
}
