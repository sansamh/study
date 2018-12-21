package others;

import utils.FileUtils;

import java.io.*;

/**
 * @version 1.0
 * @description: 序列化2
 * @author: 侯春兵
 * @Date: 17:07 2018/12/21
 */
public class ExternalizableDemo implements Externalizable {

	private transient String content = "是的，我将会被序列化，不管我是否被transient关键字修饰";

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(content);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		content = (String) in.readObject();
	}


	/**
	 * 们知道在Java中，对象的序列化可以通过实现两种接口来实现，若实现的是Serializable接口，则所有的序列化将会自动进行
	 * 若实现的是Externalizable接口，则没有任何东西可以自动序列化，需要在writeExternal方法中进行手工指定所要序列化的变量，这与是否被transient修饰无关。
	 */
	public static void main(String[] args) {
		FileUtils.doWriteObjectToFile(new ExternalizableDemo(), "test.txt");
		Serializable serializable = FileUtils.doLoadFileToObject("test.txt");
		ExternalizableDemo demo = (ExternalizableDemo) serializable;
		System.out.println(demo.content);
	}

}
