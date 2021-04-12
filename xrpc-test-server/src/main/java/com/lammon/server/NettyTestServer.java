package com.lammon.server;

import com.lammon.netty.server.NettyServer;
import com.lammon.registry.DefaultServiceRegistry;
import com.lammon.registry.ServiceRegistry;
import com.lammon.rpcapi.IHelloService;
import com.lammon.serive.HelloServiceImpl;

public class NettyTestServer {

    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}