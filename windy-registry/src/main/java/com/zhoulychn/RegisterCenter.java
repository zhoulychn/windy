package com.zhoulychn;

import com.google.common.collect.Maps;
import com.zhoulychn.serializer.ZkSerializerAdapter;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;

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

    private static int ZK_SESSION_TIMEOUT;

    private static int ZK_CONNECTION_TIME_OUT;

    private static String ZK_APP_PATH;

    private static final Map<String, Map<String, Method>> services = Maps.newConcurrentMap();

    private static ZkClient client;

    private static RegisterCenter singleton = new RegisterCenter();

    static {
        Properties properties = new Properties();
        InputStream input = RegisterCenter.class.getResourceAsStream("zookeeper.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZK_SESSION_TIMEOUT = (int) properties.get("ZK_SESSION_TIMEOUT");
        ZK_CONNECTION_TIME_OUT = (int) properties.get("ZK_CONNECTION_TIME_OUT");
        ZK_APP_PATH = properties.getProperty("ZK_APP_PATH");
        client = new ZkClient(ZK_APP_PATH, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIME_OUT, new ZkSerializerAdapter());
    }

    private RegisterCenter() {

    }

    public static RegisterCenter getSingleton() {
        return singleton;
    }


    public void registerProvider(List<Class> list) {
        for (Class each : list) {
            client.createPersistent(ZK_APP_PATH + "/" + each.getName(), true);
            for (Method method : each.getDeclaredMethods()) {
                client.createPersistent(ZK_APP_PATH + "/" + each.getName() + "/" + method.toString(), method);
            }
        }
    }

    public Method getProvider(String serviceName, String methodName) {
        Method method = services.get(serviceName).get(methodName);
        if (method == null) {
            method = client.readData(ZK_APP_PATH + "/" + serviceName + "/" + methodName, true);
        }
        return method;
    }

    private void RegisterCenterInit() {

        //初始化路径
        if (!client.exists(ZK_APP_PATH)) {
            client.createPersistent(ZK_APP_PATH, true);
        }

        //监听结点改变，将新增的服务加入缓存
        client.subscribeChildChanges(ZK_APP_PATH, (parentPath, currentChilds) -> {
            if (currentChilds == null || currentChilds.size() == 0) return;
            for (String each : currentChilds) {
                Object obj = client.readData(parentPath + "/" + each, true);

                if (obj instanceof Method) {
                    services.computeIfAbsent(ZK_APP_PATH, k -> Maps.newConcurrentMap());
                    services.get(((Method) obj).getDeclaringClass().getName()).put(obj.toString(), (Method) obj);
                }
            }
        });
    }
}
