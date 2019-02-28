package netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        ServerSocket serverSocket = new ServerSocket(port);

        new Thread(() -> {
            while (true) {
                try {
                    Socket accept = serverSocket.accept();

                    new Thread(() -> {
                        try {
                            byte [] data = new byte[1024];
                            InputStream is = accept.getInputStream();
                            while (true) {
                                int len;
                                while (-1 != (len = is.read(data))) {
                                    System.out.println(new String(data, 0 , len));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
