package zerocopy.traditional;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by lili on 2017/6/24.
 */
public class TraditionalClient {
    public static void main(String[] args) {
        int port = 2000;
        String server = "localhost";
        Socket socket = null;
        String lineToBeSent;

        DataOutputStream output = null;
        FileInputStream inputStream = null;
        int ERROR = 1;


        // connect to server
        try {
            socket = new Socket(server, port);
            System.out.println("Connected with server " + socket.getInetAddress() + ":" + socket.getPort());
        }
        catch (UnknownHostException e) {
            System.out.println(e);
            System.exit(ERROR);
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(ERROR);
        }

        try {
            String fname = "D:\\file1.bin";
            inputStream = new FileInputStream(fname);

            output = new DataOutputStream(socket.getOutputStream());
            long start = System.currentTimeMillis();
            byte[] b = new byte[4096];
            long read = 0, total = 0;
            while((read = inputStream.read(b))>=0) {
                total = total + read;
                output.write(b);
            }
            System.out.println("bytes send--"+total+" and totaltime--"+(System.currentTimeMillis() - start));
        }
        catch (IOException e) {
            System.out.println(e);
        }

        try {
            output.close();
            socket.close();
            inputStream.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}