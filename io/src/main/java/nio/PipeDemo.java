package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author lili
 * @date 2020/3/29 11:43
 * @description
 * @notes
 */
public class PipeDemo {
    public static void main(String[] args) {
        try {
            Pipe pipe = Pipe.open();
            Pipe.SinkChannel sinkChannel = pipe.sink();
            String data = "Test Data to Check java NIO Channels Pipe.";
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            byteBuffer.clear();
            byteBuffer.put(data.getBytes());
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                sinkChannel.write(byteBuffer);
            }
            Pipe.SourceChannel sourceChannel = pipe.source();
            byteBuffer = ByteBuffer.allocate(512);
            while (sourceChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    char ch = (char) byteBuffer.get();
                    System.out.print(ch);
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
