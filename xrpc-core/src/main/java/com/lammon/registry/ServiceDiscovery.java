package com.lammon.registry;

import java.net.InetSocketAddress;

/**
 * 服务的发现
 *
 * @author Lammon
 * @date 2021/4/14
 */
public interface ServiceDiscovery {
    /**
     * 根据服务名查找服务提供者地址
     *
     * @param serviceName 服务名
     * @return 服务地址
     */
    InetSocketAddress lookupService(String serviceName);
}