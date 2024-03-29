
package heartBeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class ServerHeartBeatHandler extends ChannelHandlerAdapter {
    
	/** key:ip value:auth */
	private static HashMap<String, String> AUTH_IP_MAP = new HashMap<String, String>();
	private static final String SUCCESS_KEY = "auth_success_key";
	
	static {
		//应该从数据库取得
		AUTH_IP_MAP.put("192.168.1.200", "1234");
	}
	
	private boolean auth(ChannelHandlerContext ctx, Object msg){

			//System.out.println(msg);   ret[0]  ip   ret[1]  auth
			String [] ret = ((String) msg).split(",");
			String auth = AUTH_IP_MAP.get(ret[0]);
			// 有ip并且
			if(auth != null && auth.equals(ret[1])){
				ctx.writeAndFlush(SUCCESS_KEY);
				return true;
			} else {
				//认证失败进行断连
				ctx.writeAndFlush("auth failure !").addListener(ChannelFutureListener.CLOSE);
				return false;
			}
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//字符串的话走认证方法  解密等过程 实际开发较复杂
		if(msg instanceof String){
			auth(ctx, msg);
		} else if (msg instanceof RequestInfo) {
			
			RequestInfo info = (RequestInfo) msg;
			System.out.println("--------------------------------------------");
			System.out.println("当前主机ip为: " + info.getIp());
			System.out.println("当前主机cpu情况: ");
			HashMap<String, Object> cpu = info.getCpuPercMap();
			System.out.println("总使用率: " + cpu.get("combined"));
			System.out.println("用户使用率: " + cpu.get("user"));
			System.out.println("系统使用率: " + cpu.get("sys"));
			System.out.println("等待率: " + cpu.get("wait"));
			System.out.println("空闲率: " + cpu.get("idle"));
			
			System.out.println("当前主机memory情况: ");
			HashMap<String, Object> memory = info.getMemoryMap();
			System.out.println("内存总量: " + memory.get("total"));
			System.out.println("当前内存使用量: " + memory.get("used"));
			System.out.println("当前内存剩余量: " + memory.get("free"));
			System.out.println("--------------------------------------------");
			
			ctx.writeAndFlush("info received!");
		} else {
			//过了一会 没发心跳包  就会把网络断了
			ctx.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
		}
    }


}
