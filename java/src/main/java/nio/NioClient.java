package nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NioClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            OutputStream outputStream = socket.getOutputStream();
            String s = "hello world " + System.currentTimeMillis();
            outputStream.write(s.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
