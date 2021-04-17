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
     * 请求包
     */
    REQUEST_PACK(0),
    /**
     *
     * 响应包
     */
    RESPONSE_PACK(1);

    private final int code;
}
