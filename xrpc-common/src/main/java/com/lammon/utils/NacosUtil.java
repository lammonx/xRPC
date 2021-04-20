package com.lammon.utils;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lammon.enumeration.RpcConfigEnum;
import com.lammon.enumeration.RpcError;
import com.lammon.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 管理Nacos相关的工具类
 *
 * @author lammon
 * @date 2021/4/13
 */
@Slf4j
public class NacosUtil {
    private static final NamingService namingService;
    private static final Set<String> SERVICE_NAMES = new HashSet<>();
    private static InetSocketAddress address;
    private static final String DEFAULT_NACOS_ADDR = "localhost:8848";

    static {
        namingService = getNacosNamingService();
    }

    /**
     * 连接Nacos创建命名空间
     * @return [com.alibaba.nacos.api.naming.NamingService]
     */
    public static NamingService getNacosNamingService(){
        try {
            Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue());
            String nacosAddress = properties != null && properties.getProperty(RpcConfigEnum.NACOS_ADDRESS.getPropertyValue()) != null ?
                    properties.getProperty(RpcConfigEnum.NACOS_ADDRESS.getPropertyValue()) : DEFAULT_NACOS_ADDR;
            return NamingFactory.createNamingService(nacosAddress);
        }catch (NacosException e) {
            log.error("连接到Nacos时有错误发生：", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    /**
     * 注册服务
     */
    public static void registerService(String serviceName, InetSocketAddress address) throws NacosException {
        namingService.registerInstance(serviceName, address.getHostName(), address.getPort());
        NacosUtil.address = address;
        //保存注册的服务名
        SERVICE_NAMES.add(serviceName);
    }

    /**
     * 获取提供该服务的全部服务地址
     */
    public static List<Instance> getAllInstance(String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    /**
     * 注销记录的所有服务
     */
    public static void logoutService() {
        if(!SERVICE_NAMES.isEmpty() && address != null) {
            String host = address.getHostName();
            int port = address.getPort();
            for (String serviceName : SERVICE_NAMES) {
                try {
                    //注销服务
                    namingService.deregisterInstance(serviceName, host, port);
                } catch (NacosException e) {
                    log.error("注销服务{}失败", serviceName, e);
                }
            }
        }
    }

}
