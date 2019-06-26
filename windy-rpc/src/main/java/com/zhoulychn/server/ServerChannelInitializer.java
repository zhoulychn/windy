package com.zhoulychn.server;

import com.zhoulychn.serialize.NettyDecoderHandler;
import com.zhoulychn.serialize.NettyEncoderHandler;
import com.zhoulychn.transport.WindyRequest;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel ch) {
        ch.pipeline().addLast(new NettyDecoderHandler(WindyRequest.class));
        ch.pipeline().addLast(new NettyEncoderHandler());
        ch.pipeline().addLast(new NettyServerHandler());
    }
}
