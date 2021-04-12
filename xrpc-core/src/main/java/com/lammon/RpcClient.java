package com.lammon;

import com.lammon.entity.RpcRequest;

/**
 * 客户端抽象接口
 *
 * @author lammon
 * @date 2021/4/12
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
