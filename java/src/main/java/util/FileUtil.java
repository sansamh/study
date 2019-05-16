package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * <p>
 * FileUtil
 * </p>
 *
 * @author houcb
 * @since 2019-05-15 15:28
 */
public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName) throws FileNotFoundException {
        // 路径：file:/D:/tomcat/apache-tomcat-7.0.70/webapps/jdbc_study/WEB-INF/classes/
        //return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
        return new FileInputStream(new File(fileName));

    }
}
