package douglea.classic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lili
 * @date 2020/3/29 12:59
 * @description slide is:http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf
 * @notes
 */
public class Server implements Runnable {

    private static final int PORT = 8080;
    private static final int MAX_INPUT = 1024;


    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (!Thread.interrupted()) {
                new Thread(new Handler(ss.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Handler implements Runnable {

        final Socket socket;

        Handler(Socket s) { socket = s; }

        @Override
        public void run() {
            try {
                byte[] input = new byte[MAX_INPUT];
                //read
                socket.getInputStream().read(input);
                //process
                byte[] output = process(input);
                //send
                socket.getOutputStream().write(output);
            } catch (IOException ex) {

            }
        }

        private byte[] process(byte[] cmd) {
            return null;
        }
    }
}

