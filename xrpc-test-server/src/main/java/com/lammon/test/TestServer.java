package com.lammon.test;

import com.lammon.rpcapi.IHelloService;
import com.lammon.server.RpcServer;

public class TestServer {
    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}