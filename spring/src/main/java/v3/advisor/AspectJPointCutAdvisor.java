package v3.advisor;


import v3.pointcut.AspectJExpressionPointCut;
import v3.pointcut.PointCut;

/**
 * @version 3.0
 * @description:  包括Pointcut（通过expression获取AspectJ的连接点验证器）和Advice（通过beanName获取）
 * @author: 侯春兵
 * @Date: 11:28 2018/11/30
 */
public class AspectJPointCutAdvisor implements PointCutAdvisor {

	private String adviceBeanName;

	private String expression;

	private AspectJExpressionPointCut pointCut;

	public AspectJPointCutAdvisor(String beanName, String exp) {
		this.adviceBeanName = beanName;
		this.expression = exp;
		pointCut = new AspectJExpressionPointCut(expression);
	}

	@Override
	public String getAdviceBeanName() {
		return adviceBeanName;
	}

	@Override
	public String getExpression() {
		return expression;
	}

	@Override
	public PointCut getPointCut() {
		return pointCut;
	}
}
