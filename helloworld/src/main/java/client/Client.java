package client;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lili on 2017/4/29.
 */
public class Client {

    public static void main(String[] args) {

        ClientBootstrap bootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        bootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder",new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("hiHandler", new HiHandler());
                return pipeline;
            }
        });

        //主要处理消息 handler
//        for (int i = 0; i < 100; i++) {

            ChannelFuture connect = bootstrap.connect(new InetSocketAddress("llll", 10101));
            System.out.println(connect);
            Channel channel = connect.getChannel(); //这里的channel和HiHandler---messageReceived是一个channel，属于同一个会话
            System.out.println("client start!!!");
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("请输入：");
                channel.write(scanner.next());
            }
//        }
    }
}
