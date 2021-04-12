package com.lammon.registry;

import com.lammon.enumeration.RpcError;
import com.lammon.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的注册实现
 *
 * @author lammon
 * @date 2021/3/23
 */
@Slf4j
public class DefaultServiceRegistry implements ServiceRegistry {

    /**
     * 服务-服务提供
     * 接口名-实现类
     * 保证全局唯一的注册信息
     */
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    /**
     * 已注册的服务
     */
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();


    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName)){
            return;
        }
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        log.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
