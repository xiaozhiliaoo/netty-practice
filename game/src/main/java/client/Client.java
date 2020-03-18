package client;

import common.codc.RequestEncoder;
import common.codc.ResponseDecoder;
import common.model.Request;
import common.model.fuben.request.FightRequest;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by lili on 2017/4/23.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {

        ClientBootstrap bootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new ResponseDecoder());
                pipeline.addLast("encoder", new RequestEncoder());
                pipeline.addLast("hiHandler", new HiHandler());
                return pipeline;
            }
        });

        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1",9999));

        Channel channel = connect.sync().getChannel();

        System.out.println("client start");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("请输入");
            int fubenId = Integer.parseInt(scanner.nextLine());
            int count = Integer.parseInt(scanner.nextLine());
            FightRequest fightRequest = new FightRequest();
            fightRequest.setFubenId(fubenId);
            fightRequest.setCount(count);
            Request request = new Request();
            request.setMoudle((short)1);
            request.setCmd((short) 1);
            request.setData(fightRequest.getBytes());
            //发送请求
            channel.write(request);
        }
    }
}
