package com.lammon.remoting.transport.netty.server;

import com.lammon.remoting.handler.RequestHandler;
import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Handler;

/**
 * 服务端处理传输的数据
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final RequestHandler REQUEST_HANDLER;

    static {
        REQUEST_HANDLER = new RequestHandler();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent) evt).state();
            if(state == IdleState.READER_IDLE){
                log.info("长时间未收到心跳包，断开连接……");
                ctx.close();
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            if(msg.getHeartBeat()){
                log.info("接收到客户端心跳包……");
                return;
            }
            log.info("服务器接收到请求：{}", msg);
            Object result = REQUEST_HANDLER.handle(msg);
            //注意这里的通道是workGroup中的，而NettyServer中创建的是bossGroup的，不要混淆
            ChannelFuture channelFuture = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
            //当操作失败或者被取消了就关闭通道
            channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程调用时有错误发生：");
        cause.printStackTrace();
        ctx.close();
    }
}
