package com.lammon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 传输的数据包对应类型
 *
 * @author lammon
 * @date 2021/4/12
 */
@AllArgsConstructor
@Getter
public enum PackageType {
    /**
     * 前者为请求包
     * 后者为响应包
     */
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
