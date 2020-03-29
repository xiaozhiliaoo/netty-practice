package douglea.classic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author lili
 * @date 2020/3/29 13:08
 * @description Reactor:单线程模式，多线程模式，线程池模式，Multiple Reactors
 * @notes Reactor is Server ，is a Runnable
 */
public class Reactor implements Runnable {

    private static final int MAXIN = 1111;
    private static final int MAXOUT = 11111;
    //final
    Selector selector;
    ServerSocketChannel serverSocket;


    //1  Setup
    Reactor(int port) {
        try {
//            SelectorProvider p = SelectorProvider.provider();
//            selector = p.openSelector();
//            serverSocket = p.openServerSocketChannel();
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            sk.attach(new Acceptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            //2 Dispatch Loop
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    dispatch((SelectionKey) (it.next()));
                }
                selected.clear();
            }
        } catch (IOException ex) {

        }
    }

    private void dispatch(SelectionKey k) {
        //attachment=Acceptor
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();
        }
    }

    //3 Acceptor
    class Acceptor implements Runnable { // inner
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    new Handler(selector, c);
                }
            } catch (IOException ex) { /* ... */ }
        }
    }

    //4 Handler setup
    private final class Handler implements Runnable {
        final SocketChannel socket;
        final SelectionKey sk;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        //read, process, send
        static final int READING = 0, SENDING = 1, PROCESSING = 3;
        int state = READING;
        Executor pool = Executors.newCachedThreadPool();

        Handler(Selector sel, SocketChannel c) throws IOException {
            socket = c;
            c.configureBlocking(false);
            // Optionally try first read now
            sk = socket.register(sel, 0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            sel.wakeup();
        }

        @Override
        public void run() {
            try {
                //5 Request handling
                if (state == READING) {
                    read();
                } else if (state == SENDING) {
                    send();
                }
            } catch (IOException ex) { /* ... */ }
        }

        void read() throws IOException {
            socket.read(input);
            if (inputIsComplete()) {
                state = PROCESSING;
                pool.execute(new Processer());
            }
        }

        synchronized void processAndHandOff() {
            process();
            state = SENDING; // or rebind attachment
            sk.interestOps(SelectionKey.OP_WRITE);
        }

        class Processer implements Runnable {
            public void run() {
                processAndHandOff();
            }
        }

        void send() throws IOException {
            socket.write(output);
            if (outputIsComplete()) {
                sk.cancel();
            }
        }

        boolean inputIsComplete() {
            return false;
        }

        boolean outputIsComplete() {
            return false;
        }

        void process() {

        }

    }


    private final class SingleThreadHandler implements Runnable {
        final SocketChannel socket;
        final SelectionKey sk;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        //read, process, send
        static final int READING = 0, SENDING = 1;
        int state = READING;
        Executor pool = Executors.newCachedThreadPool();

        SingleThreadHandler(Selector sel, SocketChannel c) throws IOException {
            socket = c;
            c.configureBlocking(false);
            // Optionally try first read now
            sk = socket.register(sel, 0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            sel.wakeup();
        }

        @Override
        public void run() {
            try {
                //5 Request handling
                if (state == READING) {
                    read();
                } else if (state == SENDING) {
                    send();
                }
            } catch (IOException ex) { /* ... */ }
        }

        void read() throws IOException {
            socket.read(input);
            if (inputIsComplete()) {
                process();
                state = SENDING;
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
            }
        }

        void send() throws IOException {
            socket.write(output);
            if (outputIsComplete()) {
                sk.cancel();
            }
        }

        boolean inputIsComplete() {
            return false;
        }

        boolean outputIsComplete() {
            return false;
        }

        void process() {

        }

    }
}
