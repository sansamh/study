package javac;

import org.apache.commons.logging.LogFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import sun.rmi.runtime.Log;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @version 1.0
 * @description:
 * @author:
 * @Date: 10:02 2019/1/28
 */
public class ReflectGetParameterName {
	public static void main(String[] args) {
//		getParametersNameByJavac();
		getParametersNameBySpring();
	}

	/**
	 * 通过spring LocalVariableTableParameterNameDiscoverer 获取
	 *
	 */
	private static void getParametersNameBySpring() {
		LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		Method[] methods = UserService.class.getDeclaredMethods();
		for (Method method : methods) {
			String[] parameterNames = nameDiscoverer.getParameterNames(method);
			for (String name : parameterNames) {
				System.out.print(name + " ");
			}
			System.out.println();
		}

	}


	/**
	 * 通过指定javac参数 -parameters 获取
	 *
	 * 在 preferences-》Java Compiler->设置模块字节码版本1.8，Javac Options中的 Additional command line parameters: -parameters
	 */
	private static void getParametersNameByJavac() {
		try {
			Class<?> clz = Class.forName(UserService.class.getName());
			Class [] p = {User.class, int.class};
			Method method = clz.getMethod("method", p);
			Class<?> returnType = method.getReturnType();
			System.out.println(returnType.toString());
			AnnotatedType annotatedReturnType = method.getAnnotatedReturnType();
			System.out.println(annotatedReturnType.toString());

			Parameter[] parameters = method.getParameters();
			for (Parameter parameter : parameters) {
				System.out.println(parameter.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class User {
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}

class UserService {

	public void method(User user1, int index){
		System.out.println(index + " " + user1.toString());
	}
}