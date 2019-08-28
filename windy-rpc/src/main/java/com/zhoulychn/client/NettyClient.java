package com.zhoulychn.client;

import com.zhoulychn.common.Constants;
import com.zhoulychn.transport.WindyRequest;
import com.zhoulychn.transport.WindyResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.*;

public class NettyClient {

    static Map<String, BlockingQueue<WindyResponse>> responseMap = new ConcurrentHashMap<>();


    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     * 创建一个netty通道，用于数据交互
     *
     * @return 通道
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

        // 注册一个channel
        Channel channel = registerChannel();

        // 检测channel是否可用
        if (!channel.isOpen() || !channel.isActive() || !channel.isWritable()) {
            throw new Exception("channel create failed.");
        }

        // 开启线程池
        Future<WindyResponse> future = threadPool.submit(() -> {
            NettyClient.responseMap.put(request.getUUID(), new ArrayBlockingQueue<>(1));
            ChannelFuture channelFuture = channel.writeAndFlush(request);
            channelFuture.syncUninterruptibly();
            try {
                return NettyClient.responseMap.get(request.getUUID()).poll(Constants.NETTY_TIMEOUT, TimeUnit.MILLISECONDS);
            } finally {
                channel.close();
            }
        });
        return future.get();
    }
}
