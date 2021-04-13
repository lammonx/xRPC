package com.lammon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字节流中标识序列化和反序列化器
 *
 * @author lammon
 * @date 2021/4/12
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    /**
     * 序列化协议
     */
    KRYO(0),
    JSON(1);

    private final int code;
}
