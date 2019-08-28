package com.zhoulychn.server;

import com.zhoulychn.transport.WindyRequest;
import com.zhoulychn.transport.WindyResponse;
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
        System.out.println("请求处理完成，结果编码中。。。" + response);
        System.out.println();
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
