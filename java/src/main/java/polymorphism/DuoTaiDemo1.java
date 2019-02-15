package polymorphism;

/**
 * @version 1.0
 * @description: 多态demo1
 * @author: sansam
 * @Date: 10:02 2019/2/14
 */
public class DuoTaiDemo1 {

	public static void main(String[] args) {
		// child.name = child;
		// child继承于parent 因此实例化时 先实例化Parent.name = parent;
		Child child = new Child();
		System.out.println("child name: " + child.name);
		// child本身就是一个parent 因此直接调用parent.name输出为parent 相当于调用child.Parent.name
		Parent parent = child;
		System.out.println("parent name: " + parent.name);
	}
}

class Parent {
	public String name = "parent";
}

class Child extends Parent {
	public String name = "child";
}