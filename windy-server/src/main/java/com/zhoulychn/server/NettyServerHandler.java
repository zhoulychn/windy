package com.zhoulychn.server;

import com.zhoulychn.WindyRequest;
import com.zhoulychn.WindyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理客户端请求
 * Created by Lewis on 2018/3/26
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<WindyRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WindyRequest request) throws Exception {
        WindyResponse response = ServiceHandler.invoke(request);
        System.out.println("receive data from client:" + request);
        ctx.write(response);
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
