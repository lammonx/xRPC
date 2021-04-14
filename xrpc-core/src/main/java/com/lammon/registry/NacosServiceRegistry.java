package com.lammon.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lammon.enumeration.RpcError;
import com.lammon.exception.RpcException;
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
public class NacosServiceRegistry implements ServiceRegistry{

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos注册服务
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生：", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
