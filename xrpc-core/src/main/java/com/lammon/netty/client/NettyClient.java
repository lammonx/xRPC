package com.lammon.netty.client;

import com.lammon.RpcClient;
import com.lammon.codec.CommonDecoder;
import com.lammon.codec.CommonEncoder;
import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.serializer.JsonSerializer;
import com.lammon.serializer.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 说明
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyClient implements RpcClient {

    private String host;
    private int port;
    private static final Bootstrap BOOTSTRAP;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        BOOTSTRAP = new Bootstrap();
        BOOTSTRAP.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder())
//                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new CommonEncoder(new KryoSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture channelFuture = BOOTSTRAP.connect(host, port).sync();
            log.info("客户端连接到服务器 {}:{}", host, port);
            Channel channel = channelFuture.channel();
            if(channel != null){
                channel.writeAndFlush(rpcRequest).addListener(future -> {
                    if(future.isSuccess()) {
                        log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        log.error("发送消息时有错误发生: ", future.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse<?>> key = AttributeKey.valueOf("RpcResponse");
                RpcResponse<?> rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        }catch (InterruptedException e) {
            log.error("发送消息时有错误发生：", e);
        }
        return null;
    }
}
