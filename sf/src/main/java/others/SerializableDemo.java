package others;

import java.io.*;

import static utils.FileUtils.doLoadFileToObject;
import static utils.FileUtils.doWriteObjectToFile;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 17:06 2018/12/21
 */
public class SerializableDemo {

	/**
	 * 1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
	 *
	 * 2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
	 *
	 * 3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化 (反序列化后类中static型变量的值为当前JVM中对应static变量的值，这个值是JVM中的不是反序列化得出的)。
	 */

	public static void main(String[] args) {
		User user = new User();
		user.setName("张三");
		user.setAge(18);
		System.out.println(user.toString());

		doWriteObjectToFile(user, "test.txt");
		Serializable serializable = doLoadFileToObject("test.txt");
		User user1 = (User) serializable;
		System.out.println(user1.toString());
	}

}

class User implements Serializable {
	private static final long serialVersionUID = 8294180014912103005L;

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