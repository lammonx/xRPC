package com.lammon.remoting.transport;

import com.lammon.serializer.CommonSerializer;

/**
 * 服务端抽象接口
 *
 * @author lammon
 * @date 2021/4/12
 */
public interface RpcServer {

    /**
     * 启动服务器
     *
     */
    void start();

    /**
     *  注册发布服务
     *
     * @param service 服务
     * @param serviceClass 服务的类型
     * @param <T> 某
     */
    <T> void publishService(Object service, Class<T> serviceClass);
}
