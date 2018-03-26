package com.zhoulychn.Server;

import com.zhoulychn.SerialFactory;
import com.zhoulychn.ServiceHandler;
import com.zhoulychn.WindyRequest;
import com.zhoulychn.WindyResponse;
import com.zhoulychn.serializer.Serializer;
import com.zhoulychn.serializer.SerializerType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Lewis on 2018/3/26
 */
public class NettyServerInvoker extends SimpleChannelInboundHandler<WindyRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WindyRequest request) throws Exception {

        Serializer serializer = SerialFactory.get(SerializerType.kryo);


        WindyResponse response = ServiceHandler.invoke(request);

        System.out.println("receive data from client:" + request);
        ByteBuf resp = Unpooled.copiedBuffer(serializer.serialize(response));
        ctx.write(resp);
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
