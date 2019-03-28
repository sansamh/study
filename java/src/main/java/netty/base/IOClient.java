package netty.base;

import java.net.Socket;
import java.util.Date;

public class IOClient {

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    socket.getOutputStream().write(("hello world " + new Date()).getBytes());
                    socket.getOutputStream().flush();
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }).start();
    }
}
