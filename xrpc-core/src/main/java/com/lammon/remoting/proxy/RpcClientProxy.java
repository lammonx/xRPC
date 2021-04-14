package com.lammon.remoting.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.remoting.transport.RpcClient;
import com.lammon.remoting.transport.netty.client.NettyClient;
import com.lammon.utils.RpcMessageChecker;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端的代理类,使用的是JDK动态代理
 *
 * @author lammon
 * @date 2021/3/9
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) {
        log.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(),method.getDeclaringClass().getName(),
                method.getName(), params, method.getParameterTypes(),false);
        RpcResponse rpcResponse = null;
        if(client instanceof NettyClient){
            //异步获取调用结果
            CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>)client.sendRequest(rpcRequest);
            try {
                rpcResponse = completableFuture.get();
            }catch (InterruptedException | ExecutionException e){
                log.error("方法调用请求发送失败", e);
                return null;
            }
        }
        RpcMessageChecker.check(rpcRequest, rpcResponse);
        return rpcResponse.getData();
    }
}


