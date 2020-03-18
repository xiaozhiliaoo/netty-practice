package httpfile;


import utils.HttpCallerUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


public class Test {

	
	public static void main(String[] args) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		//发出http请求
		byte[] ret = HttpCallerUtils.getStream("http://192.168.255.1:8765/socket/sources/a.doc", params);
		
//		byte[] ret = HttpProxy.get("http://192.168.1.111:8765/images/006.jpg");
        //写出文件
        String writePath = System.getProperty("user.dir") + File.separatorChar + "socket"+File.separatorChar+"receive" +  File.separatorChar + "b.doc";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(ret);
        fos.close();

	}
}
