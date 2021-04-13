package com.lammon.server;

import com.lammon.provider.DefaultServiceProvider;
import com.lammon.provider.ServiceProvider;
import com.lammon.rpcapi.IAddService;
import com.lammon.rpcapi.IHelloService;
import com.lammon.serive.AddServiceImpl;
import com.lammon.serive.HelloServiceImpl;
import com.lammon.transport.socket.server.SocketServer;

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
        ServiceProvider serviceProvider = new DefaultServiceProvider();
        //同时注册两个服务
        serviceProvider.addServiceProvider(helloService);
        serviceProvider.addServiceProvider(addService);
        SocketServer socketServer = new SocketServer(serviceProvider);
        socketServer.start();
    }
}