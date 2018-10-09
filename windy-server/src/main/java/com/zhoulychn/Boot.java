package com.zhoulychn;

import com.zhoulychn.server.NettyServer;
import com.zhoulychn.impl.BookServiceImpl;
import com.zhoulychn.impl.UserServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lewis on 2018/3/26
 */
public class Boot {

    public static void main(String[] args) throws InterruptedException, IOException {

        ArrayList<Class> list = new ArrayList<>();
        list.add(BookServiceImpl.class);
        list.add(UserServiceImpl.class);

        ServiceHandler.ServiceRegister(list);
        NettyServer.bind();
        ServerSocket serverSocket = new ServerSocket(8880);
        Socket accept = serverSocket.accept();
    }
}
