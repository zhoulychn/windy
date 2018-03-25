package com.zhoulychn.Client;

import com.zhoulychn.WindyRequest;
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



    public static void main(String[] args) throws InterruptedException {
        int port = 8880;
        new NettyClient().connect(port, "127.0.0.1");
    }


    private void connect(int port, String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host,port).sync();
            byte[] req = "hello,netty".getBytes();
            ByteBuf messageBuffer = Unpooled.buffer(req.length);
            messageBuffer.writeBytes(req);
            ChannelFuture channelFuture = f.channel().writeAndFlush(messageBuffer);
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    @Override
    public Object call(Object proxy, Method method, Object[] args) {

        WindyRequest request = new WindyRequest(UUID.randomUUID().toString(),proxy.getClass(),method,args,1000L,"lewis");


        return null;
    }


    class EchoClientHandler extends SimpleChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);

            String body = new String(req, "UTF-8");
            System.out.println("receive data from server :" + body);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }
}
