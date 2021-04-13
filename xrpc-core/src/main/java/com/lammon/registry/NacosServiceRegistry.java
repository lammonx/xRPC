package com.lammon.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lammon.enumeration.RpcError;
import com.lammon.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Nacos作为注册中心的服务注册服务
 *
 * @author lammon
 * @date 2021/4/13
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry{

    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService NAMING_SERVICE;

    static {
        try {
            //连接Nacos创建命名服务
            NAMING_SERVICE = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            log.error("连接Nacos时有错误发生：", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos注册服务
            NAMING_SERVICE.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            log.error("注册服务时有错误发生：", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //根据服务名获取某个服务的<所有>提供者
            List<Instance> instances = NAMING_SERVICE.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
