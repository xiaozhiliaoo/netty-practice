package common.codc;

import common.constant.ConstantValue;
import common.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created by lili on 2017/4/23.
 */
public class ResponseDecoder extends FrameDecoder{

    /**
     * 数据包基本长度  没有计算数据
     */
    public static int BASE_LENGTH = 4 + 2 + 2 + 4;
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {

        //记录包头开始index  读指针位置 >=0   写指针 >= 读指针
        //  0 =< readerIndex =< writerIndex

        //数据不确定，但是包头模块命令是确定的
        if(buffer.readableBytes() > BASE_LENGTH){
            int beginReader = buffer.readerIndex();

            while(true){
                //读到了包头
                if(buffer.readInt() == ConstantValue.FLAG){
                    break;
                }
            }

            short moudle = buffer.readShort();
            short cmd = buffer.readShort();
            int statusCode = buffer.readInt();
            int length = buffer.readInt();


            //读数据
            if(buffer.readableBytes() < length){
                //还原读指针
                buffer.readerIndex(beginReader);
                return null;
            }

            byte data[] = new byte[length];
            buffer.readBytes(data);

            Response response = new Response();

            response.setMoudle(moudle);
            response.setData(data);
            response.setStatusCode(statusCode);
            response.setCmd(cmd);
            //继续向下传递
            return response ;

        }
            //数据包没有来齐  需要等待后面的包过来
            return null;
    }
}
