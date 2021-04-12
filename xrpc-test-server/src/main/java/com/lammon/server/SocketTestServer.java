package com.lammon.server;

import com.lammon.registry.DefaultServiceRegistry;
import com.lammon.registry.ServiceRegistry;
import com.lammon.rpcapi.IAddService;
import com.lammon.rpcapi.IHelloService;
import com.lammon.serive.AddServiceImpl;
import com.lammon.serive.HelloServiceImpl;
import com.lammon.socket.server.SocketServer;

/**
 * 测试用服务端
 *
 * @author lammon
 * @date 2021/3/9
 */
public class SocketTestServer {
    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        IAddService addService = new AddServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //同时注册两个服务
        serviceRegistry.register(helloService);
        serviceRegistry.register(addService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}