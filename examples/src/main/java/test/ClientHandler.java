package test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by lili on 2017/6/23.
 */
public class ClientHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // ((ByteBuf)msg).release();
        //处理来自服务端的数据
        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] data = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(data);
            String request = new String(data,"utf-8");
            System.out.println("Client:"+request);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
