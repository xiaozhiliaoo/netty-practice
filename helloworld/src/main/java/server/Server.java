package server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lili on 2017/4/29.
 */
public class Server {



    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                //转化为String  管道分为上行和下行
                pipeline.addLast("decoder",new StringDecoder()); //ChannelUpstreamHandler
                pipeline.addLast("encoder", new StringEncoder()); // ChannelDownstreamHandler
                pipeline.addLast("helloHander", new HelloHandler());
                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(10101));
        System.out.println("Start!!!");
    }
}
