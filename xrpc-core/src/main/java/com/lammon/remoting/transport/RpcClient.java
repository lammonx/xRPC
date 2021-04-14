package com.lammon.remoting.transport;

import com.lammon.entity.RpcRequest;
import com.lammon.serializer.CommonSerializer;

/**
 * 客户端抽象接口
 *
 * @author lammon
 * @date 2021/4/12
 */
public interface RpcClient {
    /**
     * 发送请求
     *
     * @param rpcRequest 请求主体
     * @return
     */
    Object sendRequest(RpcRequest rpcRequest);
}
