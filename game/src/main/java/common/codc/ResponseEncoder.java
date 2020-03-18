package common.codc;

import common.constant.ConstantValue;
import common.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;


/**
 * Created by lili on 2017/4/23.
 */

/**
 * /**
 * 请求解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * | 包头          | 模块号        | 命令号      |状态吗|  长度        |   数据
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节short
 * 命令号2字节short
 * 长度4字节(描述数据部分字节长度)
 *
 * @author -琴兽-
 *
 */

public class ResponseEncoder extends OneToOneEncoder{

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object rs) throws Exception {

        Response response = (Response)rs;
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        //包头使得数据更加稳定
        buffer.writeInt(ConstantValue.FLAG);
        buffer.writeShort(response.getMoudle());
        buffer.writeShort(response.getCmd());
        buffer.writeInt(response.getStatusCode());
        buffer.writeInt(response.getDataLength());
        if(response.getData() != null){
            buffer.writeBytes(response.getData());
        }
        return buffer;
    }
}
