package com.lammon.servers;

import com.lammon.registry.DefaultServiceRegistry;
import com.lammon.registry.ServiceRegistry;
import com.lammon.rpcapi.IAddService;
import com.lammon.rpcapi.IHelloService;
import com.lammon.serive.AddServiceImpl;
import com.lammon.serive.HelloServiceImpl;
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
        IAddService addService = new AddServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //同时注册两个服务
        serviceRegistry.registry(helloService);
        serviceRegistry.registry(addService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}