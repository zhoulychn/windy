package com.zhoulychn.server;

import com.zhoulychn.common.Constants;
import com.zhoulychn.registry.RegisterCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Lewis on 2018/3/25
 */
public class NettyServer {

    public NettyServer() {
        RegisterCenter.RegisterCenterInit();
    }

    public void start() throws Exception {

        //创建两个线程组，boss 接受连接， worker处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBoot = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)  //nio的channel
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.ERROR))
                .childHandler(new ServerChannelInitializer());

        serverBoot.bind(Constants.NETTY_PORT).sync();
    }
}
