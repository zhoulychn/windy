package com.zhoulychn;


import com.zhoulychn.Client.Client;
import com.zhoulychn.Client.ClientFactory;
import com.zhoulychn.Client.ClientType;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Lewis on 2018/3/24
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> clazz = method.getDeclaringClass();
        Client client = ClientFactory.get(ClientType.Netty);
        return client.call(clazz, method, args);
    }

}
