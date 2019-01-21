package utils;

import java.io.*;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 17:16 2018/12/21
 */
public class FileUtils {

	/**
	 * 对象序列化到磁盘
	 * @param obj
	 * @param fileName
	 */
	public static void doWriteObjectToFile(Serializable obj, String fileName) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
			oos.writeObject(obj);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载磁盘文件
	 * @param obj
	 * @param fileName
	 */
	public static Serializable doLoadFileToObject(String fileName) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
			Serializable obj = (Serializable) ois.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
