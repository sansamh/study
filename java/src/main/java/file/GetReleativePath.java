package file;

import java.io.File;
import java.net.URL;

/**
 * @version 1.0
 * @description: 获取相对路径 绝对路径
 * @author: sansam
 * @Date: 15:46 2019/1/29
 */
public class GetReleativePath {

	public static void main(String[] args) {
		URL resource = GetReleativePath.class.getResource("");
		//绝对路径 /E:/study/out/production/java/file/ 包括自己
		System.out.println(resource.getPath());

		URL resource1 = GetReleativePath.class.getResource("/");
		//绝对路径 /E:/study/out/production/java/ 不包括自己的根路径
		System.out.println(resource1.getPath());

		// 创建失败
		File file = new File("src/file/test.txt");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		if (!file.exists()) {
			boolean mkdir = file.mkdir();
			System.out.println("test.txt创建 " + mkdir);
		}

	}
}
