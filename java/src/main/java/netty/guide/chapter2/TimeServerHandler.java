package netty.guide.chapter2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream());
            StringBuffer receive = new StringBuffer();
            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    System.out.println("Time server receive msg : " + receive.toString());
                    writer.println(new Date().toString());
                    break;
                }
                receive.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                    writer = null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                    this.socket = null;
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }
}
