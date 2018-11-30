package v3.advisor;

/**
 * @version 3.0
 * @description:  Advisor是Pointcut和Advice的配置器，它包括Pointcut和Advice，是将Advice注入程序中Pointcut位置的代码
 * @author: 侯春兵
 * @Date: 11:13 2018/11/30
 */
public interface Advisor {

	/**
	 * 增强bean的name
	 * @return
	 */
	String getAdviceBeanName();

	/**
	 * 表达式
	 * @return
	 */
	String getExpression();
}
