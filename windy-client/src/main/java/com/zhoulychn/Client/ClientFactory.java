package com.zhoulychn.Client;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lewis on 2018/3/25
 */
public class ClientFactory {

    private static Map<ClientType, Client> factory = new HashMap<>();

    private static void initFactory() {
        if (factory.size() == 0)
            factory.put(ClientType.Netty, new NettyClient());
    }

    public static Client get(ClientType type) {
        if (factory.size() == 0) {
            initFactory();
        }
        return factory.get(type);
    }

    public static void main(String[] args) {
        Method[] methods = Client.class.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            System.out.println(method);
            System.out.println(method.toGenericString());
        }
    }
}
