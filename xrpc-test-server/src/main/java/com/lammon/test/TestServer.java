package com.lammon.test;

import com.lammon.rpcapi.IHelloService;
import com.lammon.server.RpcServer;

/**
 * 测试用服务端
 *
 * @author lammon
 * @date 2021/3/9
 */
public class TestServer {
    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}