package com.zhoulychn;

import com.google.common.collect.Maps;
import com.zhoulychn.serializer.ZkSerializerAdapter;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Lewis on 2018/3/24
 */

public class RegisterCenter {

    private static Logger logger = Logger.getLogger(RegisterCenter.class);

    private static final int ZK_SESSION_TIMEOUT = 30000;

    private static final int ZK_CONNECTION_TIME_OUT = 1000;

    private static final String ZK_APP_PATH = "/lewis";

    private static final String ZK_HOST = "127.0.0.1:2181";

    private static final Map<String, ServiceData> services = Maps.newConcurrentMap();

    private static ZkClient client;

    private static RegisterCenter singleton = new RegisterCenter();

    public static RegisterCenter getSingleton() {
        return singleton;
    }


    //添加服务结点
    public void registerProvider(List<Class> list) {
        for (Class each : list) {
            for (Class face : each.getInterfaces()) {
                client.createEphemeral(ZK_APP_PATH + "/" + face.getName(),each);
            }
        }
    }

    //获取服务
    public ServiceData getProvider(String appName, String serviceName) {
        if (!ZK_APP_PATH.equals(appName)) return null;
        ServiceData service = services.get(serviceName);
        if (service == null) {
            Object obj = client.readData(ZK_APP_PATH + "/" + serviceName, true);
            if (obj != null && obj instanceof Class) {
                try {
                    addCache((Class) obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return service;
    }

    //初始化
    public void RegisterCenterInit() {

        client = new ZkClient(ZK_HOST, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIME_OUT, new ZkSerializerAdapter());

        //初始化路径
        if (!client.exists(ZK_APP_PATH)) {
            client.createPersistent(ZK_APP_PATH);
        }

        //监听结点改变，将新增的服务加入缓存
        client.subscribeChildChanges(ZK_APP_PATH, (parentPath, currentChilds) -> {
            if (currentChilds == null || currentChilds.size() == 0) return;
            for (String each : currentChilds) {
                Object obj = client.readData(parentPath + "/" + each, true);
                if (obj != null && obj instanceof Class) {
                    addCache((Class) obj);
                }
            }
        });
    }

    //添加缓存
    private void addCache(Class clazz) throws Exception {
        Class[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0) return;
        ServiceData service = new ServiceData();
        service.setClazz(clazz);
        service.setObj(clazz.newInstance());
        for (Method method : clazz.getDeclaredMethods()) {
            service.getMap().put(method.toString(), method);
        }
        for (Class item : interfaces) {
            services.put(item.getName(), service);
        }
    }
}
