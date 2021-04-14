package com.lammon.remoting.handler;

import com.lammon.entity.RpcRequest;
import com.lammon.entity.RpcResponse;
import com.lammon.enumeration.ResponseCode;
import com.lammon.provider.DefaultServiceProvider;
import com.lammon.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 调用执行服务
 *
 * @author lammon
 * @date 2021/3/23
 */
@Slf4j
public class RequestHandler {


    private static final ServiceProvider SERVICE_PROVIDER;

    static {
        SERVICE_PROVIDER = new DefaultServiceProvider();
    }

    public Object handle(RpcRequest rpcRequest) {
        Object service = SERVICE_PROVIDER.getServiceProvider(rpcRequest.getInterfaceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return result;
    }
}