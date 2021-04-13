package com.lammon.server;

import com.lammon.transport.netty.server.NettyServer;
import com.lammon.rpcapi.IHelloService;
import com.lammon.serializer.KryoSerializer;
import com.lammon.serive.HelloServiceImpl;

/**
 * @author Lammon
 */
public class NettyTestServer {

    public static void main(String[] args) {
        //设置服务器端口
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        //注册发布服务
        IHelloService helloService = new HelloServiceImpl();
        server.publishService(helloService, IHelloService.class);
    }

}