package com.lammon.client;

import com.lammon.RpcClient;
import com.lammon.netty.client.NettyClient;
import com.lammon.rpcapi.HelloObject;
import com.lammon.rpcapi.IHelloService;
import com.lammon.RpcClientProxy;

public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        IHelloService helloService = rpcClientProxy.getProxy(IHelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}