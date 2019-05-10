package sansam.v3.context.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlPathResource implements Resource {

    private URL url;

    public UrlPathResource(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.getUrl().openStream();
    }

    @Override
    public boolean exists() {
        return this.url != null;
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
