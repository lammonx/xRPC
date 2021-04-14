package com.lammon.remoting.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.lammon.entity.RpcRequest;
import com.lammon.remoting.transport.RpcClient;
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
        return client.sendRequest(rpcRequest);
    }
}


