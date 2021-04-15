package com.lammon.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lammon.enumeration.RpcError;
import com.lammon.exception.RpcException;
import com.lammon.loadbanlance.LoadBalancer;
import com.lammon.loadbanlance.RoundRobinLoadBalancer;
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

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(){
        this(new RoundRobinLoadBalancer());
    }

    public NacosServiceDiscovery(LoadBalancer loadBalancer){
        this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //根据服务名获取某个服务的<所有>提供者
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if(instances.size() == 0){
                log.error("找不到对应服务：" + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            //负载均衡获取一个服务实体
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
