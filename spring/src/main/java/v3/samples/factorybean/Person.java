package v3.samples.factorybean;

import lombok.Data;

/**
 * @version 1.0
 * @description: 普通bean
 * @author: 侯春兵
 * @Date: 15:19 2018/12/7
 */
@Data
public class Person {

	private String name;
	private int age;
	private String address;

	public Person() {
	}

	public Person(String name, int age, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}
}
