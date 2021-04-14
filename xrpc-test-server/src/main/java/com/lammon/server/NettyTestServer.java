package com.lammon.server;

import com.lammon.remoting.transport.netty.server.NettyServer;
import com.lammon.api.service.IHelloService;
import com.lammon.serive.HelloServiceImpl;

/**
 * @author Lammon
 */
public class NettyTestServer {

    public static void main(String[] args) {
        //设置服务器端口
        NettyServer server = new NettyServer("127.0.0.1", 6666);
        //注册发布服务
        IHelloService helloService = new HelloServiceImpl();
        server.publishService(helloService, IHelloService.class);
    }

}