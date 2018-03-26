package com.zhoulychn.Server;

import com.zhoulychn.NettyDecoderHandler;
import com.zhoulychn.NettyEncoderHandler;
import com.zhoulychn.SerialFactory;
import com.zhoulychn.WindyRequest;
import com.zhoulychn.serializer.SerializerType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Lewis on 2018/3/25
 */
public class NettyServer {


    private static final int port = 8880;


    public static void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyServerInvoker())
                            .addLast(new NettyEncoderHandler(SerialFactory.get(SerializerType.kryo)))
                            .addLast(new NettyDecoderHandler(SerialFactory.get(SerializerType.kryo), WindyRequest.class));
                }
            });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
