package nio2;

public class TimeServer {
    public static void main(String[] args) throws Exception {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(8080);
        new Thread(timeServer, "001").start();
    }
}
