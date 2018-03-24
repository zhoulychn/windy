package com.zhoulychn;

import com.esotericsoftware.kryo.Kryo;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by Lewis on 2018/3/22
 */
public class Manager implements Watcher {

    private final static String host = "127.0.0.1:2181";

    private static volatile boolean isConnected = false;


    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper(host, 5000, new Manager());

        ZooKeeper.States state = zk.getState();

       UserService userService = new UserService();
        Kryo kryo = new Kryo();




    }

    public void process(WatchedEvent event) {
        isConnected = true;

        System.out.println(1);
        System.out.println(event);
    }
}
