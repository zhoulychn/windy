package com.zhoulychn.Client;

import com.zhoulychn.*;
import com.zhoulychn.Server.NettyServerInvoker;
import com.zhoulychn.serializer.SerializerType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Lewis on 2018/3/25
 */
public class NettyClient implements Client {

    private static final String appName = "/lewis";

    private static final int port = 8880;

    private static final String host = "127.0.0.1";

    private static Channel channel;


    @Override
    public void connect(int port, String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ch.pipeline().addLast(new NettyClientInvoker()).
                                    addLast(new NettyEncoderHandler(SerialFactory.get(SerializerType.kryo)))
                                    .addLast(new NettyDecoderHandler(SerialFactory.get(SerializerType.kryo), WindyResponse.class));
                        }
                    });
            ChannelFuture channelFuture = b.connect(host, port).sync();
            channel = channelFuture.channel();
        } finally {
            group.shutdownGracefully();
        }
    }


    @Override
    public Object call(Object proxy, Method method, Object[] args) {
        try {
            connect(port,host);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WindyRequest request = new WindyRequest();
        request.setAppName(appName);
        request.setClazz(proxy.getClass().getName());
        request.setMaxTime(30000L);
        request.setMethod(method.toString());
        request.setUUID(UUID.randomUUID().toString());
        request.setArgs(args);

        NettyClientInvoker.send(request);
        return ResponseHolder.get(request.getUUID());
    }

}
