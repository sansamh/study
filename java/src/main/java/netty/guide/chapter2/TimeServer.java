package netty.guide.chapter2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    public static void main(String[] args) {
        int port = 8888;
        if (null != args && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            System.out.println("Time server start up im port : " + port);
            Socket socket;
            while (true) {
                socket = ss.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                System.out.println("Time server close");
                try {
                    ss.close();
                    ss = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
