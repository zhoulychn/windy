package com.zhoulychn.client;

import com.zhoulychn.NettyDecoderHandler;
import com.zhoulychn.NettyEncoderHandler;
import com.zhoulychn.WindyResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel ch) {
        ch.pipeline().addLast(new NettyEncoderHandler());
        ch.pipeline().addLast(new NettyDecoderHandler(WindyResponse.class));
        ch.pipeline().addLast(new NettyClientHandler());
    }
}
