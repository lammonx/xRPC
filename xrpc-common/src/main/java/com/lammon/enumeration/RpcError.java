package com.lammon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 项目中的错误枚举
 *
 * @author lammon
 * @date 2021/3/23
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    /**
     *
     */
    UNKNOWN_ERROR("出现未知错误"),
    /**
     *
     */
    UNKNOWN_PROTOCOL("未知的协议包"),
    /**
     *
     */
    UNKNOWN_SERIALIZER("未知的(反)序列化器"),
    /**
     *
     */
    UNKNOWN_PACKAGE_TYPE("未知的数据包类型"),
    /**
     *
     */
    SERVICE_SCAN_PACKAGE_NOT_FOUND("启动类ServiceScan注解缺失，导致服务无法注册"),
    /**
     *
     */
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    /**
     *
     */
    SERVICE_NOT_FOUND("对应服务未能找到"),
    /**
     *
     */
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务无效，未实现任何接口"),
    /**
     *
     */
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接注册中心失败"),
    /**
     *
     */
    REGISTER_SERVICE_FAILED("注册服务失败"),
    /**
     *
     */
    CLIENT_CONNECT_SERVER_FAILURE("客户端连接服务端失败"),
    /**
     *
     */
    SERIALIZER_NOT_FOUND("无法找到序列化器"),
    /**
     *
     */
    RESPONSE_NOT_MATCH("响应与请求号不符");

    private final String message;

}
