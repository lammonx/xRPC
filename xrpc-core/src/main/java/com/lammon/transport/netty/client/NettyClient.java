package com.lammon.transport.netty.client;

import com.lammon.transport.RpcClient;
import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.registry.NacosServiceRegistry;
import com.lammon.registry.ServiceRegistry;
import com.lammon.serializer.CommonSerializer;
import io.netty.channel.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Netty方式客户端的实现
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyClient implements RpcClient {

    private final ServiceRegistry serviceRegistry;
    private CommonSerializer serializer = CommonSerializer.getByCode(0);

    public NettyClient() {
        this.serviceRegistry = new NacosServiceRegistry();
    }


    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ClientChannelProvider.getChannel(inetSocketAddress, serializer);
            if(channel.isActive()){
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

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
