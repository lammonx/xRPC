package com.lammon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * rpc调用的错误枚举
 *
 * @author lammon
 * @date 2021/3/23
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    /**
     * 调用过程中的错误
     */
    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;

}
