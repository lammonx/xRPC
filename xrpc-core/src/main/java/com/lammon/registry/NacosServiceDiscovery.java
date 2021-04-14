package com.lammon.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lammon.utils.NacosUtil;
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
public class NacosServiceDiscovery implements ServiceDiscovery{

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //根据服务名获取某个服务的<所有>提供者
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
