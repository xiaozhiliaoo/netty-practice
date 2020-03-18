package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by lili on 2017/6/23.
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //((ByteBuf) msg).release();
        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] data = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(data);
            String request = new String(data,"utf-8");
            System.out.println("Server:" + request);

            String response = "服务器返回啦";
//            ctx.write(Unpooled.copiedBuffer("88888".getBytes()));
//            ctx.flush();
            ctx.writeAndFlush(Unpooled.copiedBuffer("88888".getBytes()));
//                    .addListener(ChannelFutureListener.CLOSE);

        }finally {
            //有了write就不用此方法了
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
