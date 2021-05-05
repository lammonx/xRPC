package com.lammon.server;

import com.lammon.annotation.ServiceScan;
import com.lammon.enumeration.RpcConfigEnum;
import com.lammon.remoting.transport.RpcServer;
import com.lammon.remoting.transport.netty.server.NettyServer;
import com.lammon.utils.PropertiesFileUtil;

import java.util.Properties;

/**
 * @author Lammon
 * 不指定扫描的包默认扫描启动类所在的包
 */

@ServiceScan(servicePackage = "com.lammon.service")
public class NettyTestServer {

    public static void main(String[] args) {
        //获取本机地址
        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue());
        String serverAddress = properties != null && properties.getProperty("xrpc.localhost.address") != null ?
                properties.getProperty("xrpc.localhost.address") : "127.0.0.1";
        //服务地址，配置服务端口为6666
        RpcServer server = new NettyServer(serverAddress, 6666);
        server.start();
    }

}