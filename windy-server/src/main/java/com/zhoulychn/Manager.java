package com.zhoulychn;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by Lewis on 2018/3/22
 */
public class Manager implements Watcher {

    public final static String host = "127.0.0.1:2181";

    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper(host, 5000, new Manager());

        ZooKeeper.States state = zk.getState();
        System.out.println(state);

    }

    public void process(WatchedEvent event) {
        System.out.println(1);
        System.out.println(event);
    }
}
