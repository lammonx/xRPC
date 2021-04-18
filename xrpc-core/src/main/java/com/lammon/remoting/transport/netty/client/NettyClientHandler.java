package com.lammon.remoting.transport.netty.client;

import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.factory.SingletonFactory;
import com.lammon.serializer.CommonSerializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 客户端处理传输的数据
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse>  {

    private final UnprocessedRequests unprocessedRequests;

    public NettyClientHandler(){
        unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            if(state == IdleState.WRITER_IDLE){
                log.info("发送心跳包[{}]", ctx.channel().remoteAddress());
                Channel channel = ClientChannelProvider.getChannel((InetSocketAddress) ctx.channel().remoteAddress(),
                        CommonSerializer.getByCode(CommonSerializer.DEFALUT_SERIALIZER));
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setHeartBeat(true);
                //设置一个Listener监测服务端是否接收到心跳包，如果接收到就代表对方在线，不用关闭Channel
                channel.writeAndFlush(rpcRequest).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        try {
            log.info(String.format("客户端接收到消息：%s", msg));
            //将响应数据取出
            unprocessedRequests.complete(msg);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
