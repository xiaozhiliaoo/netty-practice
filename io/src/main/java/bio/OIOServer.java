package bio;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class OIOServer {

	
	public static void main(String[] args) throws Exception {
		// 线程池
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		//创建socket服务,监听10102端口
		ServerSocket server=new ServerSocket(10102);
		System.out.println("服务器启动！");
		// 创建线程任务  创建线程池
		while(true) {
			//获取一个套接字（阻塞）
			final Socket socket = server.accept();
			System.out.println("来个一个新客户端！");
			//扔到线程池里面  不用重复创建线程
			newCachedThreadPool.execute(new Runnable() {
				public void run() {
					//业务处理
					handler(socket);
				}
			});
		}
	}
	
	/**
	 * 读取数据
	 * @param socket
	 * @throws Exception
	 */
	public static void handler(Socket socket) {
			try {
				byte[] bytes = new byte[1024];
				InputStream inputStream = socket.getInputStream();
				
				while(true){
					//读取数据（阻塞）
					int read = inputStream.read(bytes);
					if(read != -1){
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println(new String(bytes, 0, read));
					}else{
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					System.out.println("socket关闭");
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
}
