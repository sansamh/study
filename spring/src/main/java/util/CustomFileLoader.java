package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

public class CustomFileLoader {

    /**
     * 获取类似spring加载spring.handlers那种文件properties啊
     * 会去搜索所有jar包 包括自己的目录下这个文件夹下的文件
     * eg. fileName = "META-INF/spring.handlers"
     */
    public static Properties loadCustomerFileLileSpring(String fileName) throws IOException {
        Properties properties = new Properties();
        Enumeration<URL> resources = CustomFileLoader.class.getClassLoader().getResources(fileName);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            properties.load(is);
        }
        return properties;
    }

}
