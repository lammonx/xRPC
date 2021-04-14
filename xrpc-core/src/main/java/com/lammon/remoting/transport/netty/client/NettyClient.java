package com.lammon.remoting.transport.netty.client;

import com.lammon.factory.SingletonFactory;
import com.lammon.registry.NacosServiceDiscovery;
import com.lammon.registry.ServiceDiscovery;
import com.lammon.remoting.transport.RpcClient;
import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.serializer.CommonSerializer;
import io.netty.channel.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Netty方式客户端的实现
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyClient implements RpcClient {

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;
    private final UnprocessedRequests unprocessedRequests;

    public NettyClient() {
        this(CommonSerializer.DEFALUT_SERIALIZER);
    }

    public NettyClient(int serializerCode) {
        this.serializer = CommonSerializer.getByCode(serializerCode);
        this.serviceDiscovery = new NacosServiceDiscovery();
        unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }




    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        try {
            //将新请求放入未处理完的请求中
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);

            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ClientChannelProvider.getChannel(inetSocketAddress, serializer);
            if (channel.isActive()) {
                channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        future.channel().close();
                        resultFuture.completeExceptionally(future.cause());
                        log.error("发送消息时有错误发生: ", future.cause());
                    }
                });
            }
        }catch (Exception e){
                //将请求从请求集合中移除
                unprocessedRequests.remove(rpcRequest.getRequestId());
                log.error(e.getMessage(), e);
            }
            return resultFuture;
    }
}
