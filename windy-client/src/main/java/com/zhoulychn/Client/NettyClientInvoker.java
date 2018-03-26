package com.zhoulychn.Client;

import com.zhoulychn.WindyRequest;
import com.zhoulychn.WindyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Lewis on 2018/3/26
 */

public class NettyClientInvoker extends SimpleChannelInboundHandler<WindyResponse> {

    private static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyClientInvoker.ctx = ctx;
    }

    public static void send(WindyRequest request){
         ctx.writeAndFlush(request);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WindyResponse response) throws Exception {
        ResponseHolder.put(response.getUUID(), response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
