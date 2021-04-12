package com.lammon.exception;

/**
 * 序列化异常
 *
 * @author lammon
 * @date 2021/4/12
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}
