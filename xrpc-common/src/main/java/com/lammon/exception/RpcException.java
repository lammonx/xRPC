package com.lammon.exception;

import com.lammon.enumeration.RpcError;

/**
 * rpc调用的异常
 *
 * @author lammon
 * @date 2021/3/23
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }
    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}