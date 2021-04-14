package com.lammon.remoting.transport.netty.server;

import com.lammon.hook.ShutdownHook;
import com.lammon.remoting.transport.RpcServer;
import com.lammon.remoting.codec.CommonDecoder;
import com.lammon.remoting.codec.CommonEncoder;
import com.lammon.provider.DefaultServiceProvider;
import com.lammon.registry.NacosServiceRegistry;
import com.lammon.provider.ServiceProvider;
import com.lammon.registry.ServiceRegistry;
import com.lammon.serializer.CommonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Netty方式服务端的实现
 *
 * @author lammon
 * @date 2021/4/12
 */
@Slf4j
public class NettyServer implements RpcServer {
    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;
    private final CommonSerializer serializer;

    public NettyServer(String host, int port) {
        this(host, port, CommonSerializer.DEFALUT_SERIALIZER);
    }

    public NettyServer(String host, int port, int serializerCode) {
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new DefaultServiceProvider();
        this.serializer = CommonSerializer.getByCode(serializerCode);
    }

    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //设定IdleStateHandler心跳检测每30秒进行一次读检测，如果30秒内ChannelRead()方法未被调用则触发一次userEventTrigger()方法
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            //添加注销服务的钩子，服务端关闭时才会执行
            ShutdownHook.getShutdownHook().addClearAllHook();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("启动服务器时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }
}
