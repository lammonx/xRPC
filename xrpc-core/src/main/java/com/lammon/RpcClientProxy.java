package com.lammon;

import com.lammon.RpcClient;
import com.lammon.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.lammon.entity.RpcRequest;
import com.lammon.socket.client.SocketClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端的代理类
 *
 * @author lammon
 * @date 2021/3/9
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private RpcClient client;

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
        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(), params, method.getParameterTypes());
        return client.sendRequest(rpcRequest);
    }
}


