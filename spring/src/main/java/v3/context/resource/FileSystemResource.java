package v3.context.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource{

    private File file;

    public FileSystemResource(String fileName) {
        file = new File(fileName);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public boolean exists() {
        return this.file == null ? null : file.exists();
    }

    @Override
    public boolean isReadable() {
        return this.file == null ? null : file.canRead();
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public File getFile() {
        return file;
    }
}
