package com.lammon.transport.netty.server;

import com.lammon.transport.RequestHandler;
import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.provider.DefaultServiceProvider;
import com.lammon.provider.ServiceProvider;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端处理传输的数据
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static RequestHandler requestHandler;
    private static ServiceProvider serviceProvider;

    static {
        requestHandler = new RequestHandler();
        serviceProvider = new DefaultServiceProvider();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            log.info("服务器接收到请求：{}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceProvider.getServiceProvider(interfaceName);
            Object result = requestHandler.handle(msg, service);
            ChannelFuture channelFuture = ctx.writeAndFlush(RpcResponse.success(result));
            channelFuture.addListener(ChannelFutureListener.CLOSE);
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
