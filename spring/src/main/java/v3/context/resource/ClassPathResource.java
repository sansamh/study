package v3.context.resource;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String path;
    private Class<?> clz;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this.path = path;
    }

    public ClassPathResource(String path, Class<?> clz) {
        this.path = path;
        this.clz = clz;
    }

    public ClassPathResource(String path, Class<?> clz, ClassLoader classLoader) {
        this.path = path;
        this.clz = clz;
        this.classLoader = classLoader;
    }

    @Override
    public boolean exists() {
        if (StringUtils.isNotBlank(path)) {
            if (this.getClz() != null) {
                return this.getClz().getResource(path) != null;
            }

            if (this.getClassLoader() != null) {
                return this.getClassLoader().getResource(path.startsWith("/") ? "/" : path) != null;
            }

            return this.getClass().getResource(path) != null;
        }
        return false;
    }

    @Override
    public boolean isReadable() {
        return exists();
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (StringUtils.isNotBlank(path)) {
            if (this.clz != null) {
                return this.getClz().getResourceAsStream(path);
            }

            if (this.getClassLoader() != null) {
                return this.getClassLoader().getResourceAsStream(path.startsWith("/") ? "/" : path);
            }

            return this.getClass().getResourceAsStream(path);
        }
        return null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
