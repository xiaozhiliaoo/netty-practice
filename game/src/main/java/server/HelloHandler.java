package server;

import common.model.Request;
import common.model.Response;
import common.model.StateCode;
import common.model.fuben.request.FightRequest;
import common.model.fuben.response.FightResponse;
import org.jboss.netty.channel.*;


/**
 * Created by lili on 2017/4/23.
 */
public class HelloHandler extends SimpleChannelHandler{

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request message = (Request) e.getMessage();

        if(message.getMoudle() == 1){
            if(message.getCmd() == 1){
                FightRequest fightRequest = new FightRequest();
                fightRequest.readFromBytes(message.getData());
                System.out.println("你需要的攻击的地方是：fubenID" + fightRequest.getFubenId()+";次数  "+ fightRequest.getCount());
                FightResponse fightResponse = new FightResponse();
                fightResponse.setGold(9999);
                Response response = new Response();
                response.setMoudle((short) 1);
                response.setCmd((short) 1);
                response.setStatusCode(StateCode.SUCCESS);
                response.setData(fightResponse.getBytes());
                //Channel往回写数据
                ctx.getChannel().write(response);
            }else if(message.getCmd() == 2) {

            }
        }else if(message.getMoudle() == 2) {

        }

    }

    /**
     * Invoked when an exception was raised by an I/O thread or a
     * {@link ChannelHandler}.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} is open, but not bound nor connected.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelOpen");
        super.channelOpen(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} is open, bound to a local address, and
     * connected to a remote address.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} was disconnected from its remote peer.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * Invoked when a {@link Channel} was closed and all its related resources
     * were released.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }
}
