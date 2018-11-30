package v3.pointcut;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * @version 3.0
 * @description: AspectJ的连接点验证器 获取切入点
 * @author: 侯春兵
 * @Date: 10:42 2018/11/30
 */
public class AspectJExpressionPointCut implements PointCut {

	private static PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

	private PointcutExpression pointcutExpression;

	private String expression;

	public AspectJExpressionPointCut(String ex) {
		this.expression = ex;
		pointcutExpression = pointcutParser.parsePointcutExpression(expression);
	}

	@Override
	public boolean matchClass(Class<?> clz) {
		return pointcutExpression.couldMatchJoinPointsInType(clz);
	}

	@Override
	public boolean matchMethod(Method method) {
		ShadowMatch shadowMatch = pointcutExpression.matchesAdviceExecution(method);
		return shadowMatch.alwaysMatches();
	}
}
