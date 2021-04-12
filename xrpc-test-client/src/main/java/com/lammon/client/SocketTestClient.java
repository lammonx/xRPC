package com.lammon.client;

import com.lammon.RpcClient;
import com.lammon.rpcapi.HelloObject;
import com.lammon.RpcClientProxy;
import com.lammon.rpcapi.IAddService;
import com.lammon.rpcapi.IHelloService;
import com.lammon.socket.client.SocketClient;

/**
 * 测试用客户端
 *
 * @author lammon
 * @date 2021/3/9
 */
public class SocketTestClient {
    public static void main(String[] args) {
        RpcClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        IHelloService helloService = rpcClientProxy.getProxy(IHelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println("-----------" + res + "-------------");
        IAddService addService = rpcClientProxy.getProxy(IAddService.class);
        int res2 = addService.add(665);
        System.out.println("-----------" + res2 + "-------------");
    }
}
