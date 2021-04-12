package com.lammon.registry;

/**
 * 服务的注册接口
 *
 * @author lammon
 * @date 2021/3/23
 */
public interface ServiceRegistry {
    /**
     * 服务的注册
     *
     * @param service 服务
     */
    <T> void register(T service);

    /**
     * 获取对应服务名的服务
     *
     * @param serviceName 服务名
     * @return 对应服务
     */
    Object getService(String serviceName);
}
