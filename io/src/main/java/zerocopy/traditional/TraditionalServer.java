package zerocopy.traditional;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lili on 2017/6/24.
 */
public class TraditionalServer {
    public static void main(String[] args) {
        int port = 2000;
        ServerSocket serverSocket ;
        DataInputStream input;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server waiting for client on port " + serverSocket.getLocalPort());
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                input = new DataInputStream(socket.getInputStream());
//                System.out.println(input);
                try{
                    byte[] byteArray = new byte[4096];
                    while (true){
                        int nread = input.read(byteArray,0,4096);
                        if(0==nread){
                            break;
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    socket.close();
                    System.out.println("Connection closed by client");
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
