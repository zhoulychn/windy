package com.zhoulychn.client;

import com.zhoulychn.Constants;
import com.zhoulychn.WindyRequest;
import com.zhoulychn.WindyResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.*;

public class NettyClient {

    static final Map<String, BlockingQueue<WindyResponse>> responseMap = new ConcurrentHashMap<>();

    /**
     * 创建一个netty通道，用于数据交互
     * @return  通道
     * @throws Exception 异常
     */
    private static Channel registerChannel() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientChannelInitializer());
        ChannelFuture channelFuture = boot.connect(Constants.NETTY_HOST, Constants.NETTY_PORT).sync();

        final CountDownLatch connectedLatch = new CountDownLatch(1);

        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                connectedLatch.countDown();
                System.out.println("channelFuture success");
            }
        });

        connectedLatch.await();
        return channelFuture.channel();
    }

    public static WindyResponse invoke(WindyRequest request) throws Exception {

        Channel channel = registerChannel();

        if (!channel.isOpen() || !channel.isActive() || !channel.isWritable()) {
            throw new Exception("channel create failed.");
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        Future<WindyResponse> future = threadPool.submit(() -> {
            NettyClient.responseMap.put(request.getUUID(), new ArrayBlockingQueue<>(1));
            ChannelFuture channelFuture = channel.writeAndFlush(request);
            channelFuture.syncUninterruptibly();
            return NettyClient.responseMap.get(request.getUUID()).poll(Constants.NETTY_TIMEOUT, TimeUnit.MILLISECONDS);
        });

        WindyResponse response = future.get();
        threadPool.shutdown();
        return response;
    }
}
