package server;

import org.jboss.netty.channel.*;

/**
 * Created by lili on 2017/4/29.
 */
public class HelloHandler extends SimpleChannelHandler{

    /**
     * 接收消息
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("Message Recieve!!!");
        //int i=1/0;
//        int i = 1/0  //异常
        //前面加了StringDecoder处理器，就不用转了
       /* ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        String s = new String(buffer.array());*/

        String s = (String) e.getMessage();
        System.out.println(s);

        //回写数据  必须会写ChannerBuffer,可以通过管道处理
//        ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer("hi".getBytes());
//        ctx.getChannel().write(copiedBuffer);

        ctx.getChannel().write("hi~~~~~");
        super.messageReceived(ctx, e);
    }

    /**
     * 捕获异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是链接已经建立，关闭通道的时候才会触发
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭的时候触发
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }
}


