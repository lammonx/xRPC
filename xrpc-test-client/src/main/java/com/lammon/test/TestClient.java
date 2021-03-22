package com.lammon.test;

import com.lammon.rpcapi.HelloObject;
import com.lammon.transport.RpcClientProxy;
import com.lammon.rpcapi.IHelloService;

/**
 * 测试用客户端
 *
 * @author lammon
 * @date 2021/3/9
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        IHelloService helloService = proxy.getProxy(IHelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}

