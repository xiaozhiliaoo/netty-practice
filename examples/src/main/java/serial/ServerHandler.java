package serial;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import utils.GzipUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;


public class ServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//直接可以转化为requst对象了
		Req req = (Req)msg;
		System.out.println("Server : " + req.getId() + ", " + req.getName() + ", " + req.getRequestMessage());
		byte[] attachment = GzipUtils.ungzip(req.getAttachment());

		String path = System.getProperty("user.dir") +
				File.separatorChar + "socket"+
				File.separatorChar + "receive"+
				File.separatorChar + "001"+UUID.randomUUID()+".jpg";
		FileOutputStream fos = new FileOutputStream(path);
        fos.write(attachment);
        fos.close();
		
		Resp resp = new Resp();
		resp.setId(req.getId());
		resp.setName("resp" + req.getId());
		resp.setResponseMessage("响应内容" + req.getId());
		ctx.writeAndFlush(resp);//.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	
	
}
