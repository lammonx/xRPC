package com.lammon.client;

import com.lammon.api.service.IAddService;
import com.lammon.remoting.transport.RpcClient;
import com.lammon.remoting.transport.netty.client.NettyClient;
import com.lammon.api.service.HelloObject;
import com.lammon.api.service.IHelloService;
import com.lammon.remoting.proxy.RpcClientProxy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lammon
 */
@Slf4j
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        //获取代理类
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        IHelloService helloService = rpcClientProxy.getProxy(IHelloService.class);
        IAddService addService = rpcClientProxy.getProxy(IAddService.class);
        String res = helloService.hello(new HelloObject(666, "Hello MY RPC!!"));
        log.info(res);
        int res2 = addService.addOne(665);
        log.info(String.valueOf(res2));
    }

}