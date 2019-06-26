package com.zhoulychn;

import com.zhoulychn.server.NettyServer;

public class ServerBoot {

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.start();
    }
}
