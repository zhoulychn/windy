package com.zhoulychn.client;

import com.zhoulychn.WindyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Lewis on 2018/3/26
 */

public class NettyClientHandler extends SimpleChannelInboundHandler<WindyResponse> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler: handlerAdded");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler: channelRegistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler: channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WindyResponse response) throws Exception {
        System.out.println("receive data from server: " + response);
        NettyClient.responseMap.get(response.getUUID()).add(response);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler: channelInactive");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler: channelUnregistered");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("NettyClientHandler: exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientHandler: channelReadComplete");
        ctx.flush();
    }
}
