package com.lammon.provider;

/**
 * 添加和提供服务实例对象
 *
 * @author lammon
 * @date 2021/3/23
 */
public interface ServiceProvider {
    /**
     * 服务的添加
     *
     * @param service 某服务
     */
    <T> void addServiceProvider(T service);

    /**
     * 根据对应服务名获取某服务
     *
     * @param serviceName 服务名
     * @return 对应服务
     */
    Object getServiceProvider(String serviceName);
}
