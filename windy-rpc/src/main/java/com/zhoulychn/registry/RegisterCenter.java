package com.zhoulychn.registry;

import com.google.common.collect.Maps;
import com.zhoulychn.BookServiceImpl;
import com.zhoulychn.UserServiceImpl;
import com.zhoulychn.common.Constants;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lewis on 2018/3/24
 */

public class RegisterCenter {

    private static final Map<String, ServiceData> services = Maps.newConcurrentMap();

    private static ZkClient client;

    //初始化
    public static void RegisterCenterInit() {

        client = new ZkClient(Constants.ZK_HOST, Constants.ZK_SESSION_TIMEOUT, Constants.ZK_CONNECTION_TIME_OUT, new SerializableSerializer());

        //初始化路径
        if (!client.exists(Constants.ZK_APP_PATH)) {
            client.createPersistent(Constants.ZK_APP_PATH);
        }


        //监听结点改变，将新增的服务加入缓存
        client.subscribeChildChanges(Constants.ZK_APP_PATH, (parentPath, currentChilds) -> {
            if (currentChilds == null || currentChilds.size() == 0) return;
            for (String child : currentChilds) {
                Class obj = client.readData(parentPath + "/" + child, true);
                addCache(obj);
            }
        });
        List<Class> service = new ArrayList<>();
        service.add(UserServiceImpl.class);
        service.add(BookServiceImpl.class);
        registerProvider(service);
    }

    //添加服务结点
    private static void registerProvider(List<Class> list) {
        for (Class item : list) {
            for (Class face : item.getInterfaces()) {
                String path = Constants.ZK_APP_PATH + "/" + face.getName();
                if (!client.exists(path)) client.createPersistent(path, item);
            }
        }
    }

    //添加缓存
    private static void addCache(Class clazz) throws Exception {
        Class[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0) return;
        ServiceData service = new ServiceData();
        service.setClazz(clazz);
        service.setObj(clazz.newInstance());
        for (Method method : clazz.getDeclaredMethods()) {
            service.getMap().put(method.getName(), method);
        }
        for (Class item : interfaces) {
            services.put(item.getName(), service);
        }
    }

    //获取服务
    public static ServiceData getProvider(String appName, String serviceName) {
        if (!Constants.ZK_APP_PATH.equals("/" + appName)) return null;
        ServiceData service = services.get(serviceName);
        if (service == null) {
            Class clazz = client.readData(Constants.ZK_APP_PATH + "/" + serviceName, true);
            try {
                addCache(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return services.get(serviceName);
    }
}
