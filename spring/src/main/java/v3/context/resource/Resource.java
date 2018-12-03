package v3.context.resource;


import java.io.File;

/**
 * 资源类
 */
public interface Resource extends InputStreamSource {

    String CLASS_PATH_PREFIX = "classpath:";

    String File_SYSTEM_PREFIX = "file:";

    boolean exists();

    boolean isReadable();

    boolean isOpen();

    File getFile();
}
