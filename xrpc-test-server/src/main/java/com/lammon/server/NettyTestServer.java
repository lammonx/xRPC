package com.lammon.server;

import com.lammon.annotation.ServiceScan;
import com.lammon.remoting.transport.RpcServer;
import com.lammon.remoting.transport.netty.server.NettyServer;

/**
 * @author Lammon
 * 不指定扫描的包默认扫描启动类所在的包
 */

@ServiceScan(servicePackage = "com.lammon.service")
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 6666);
        server.start();
    }

}