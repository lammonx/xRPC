package com.lammon.registry;

import java.net.InetSocketAddress;

/**
 * 通用的服务注册及获取地址接口
 *
 * @author Lammon
 * @date 2021/4/13
 */
public interface ServiceRegistry {
    /**
     * 服务的注册
     *
     * @param serviceName 服务名
     * @param inetSocketAddress 服务地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

}