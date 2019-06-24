package com.zhoulychn.proxy;


import com.zhoulychn.Constants;
import com.zhoulychn.WindyRequest;
import com.zhoulychn.WindyResponse;
import com.zhoulychn.client.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by Lewis on 2018/3/24.
 * <p>
 * 接口代理对象。
 * <p>
 * Object proxy是代理对象实例。
 */


public class RpcProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> clazz = method.getDeclaringClass();

        WindyRequest request = new WindyRequest();
        request.setAppName(Constants.APP_NAME);
        request.setUUID(UUID.randomUUID().toString());
        request.setClazz(clazz.getName());
        request.setMethod(method.getName());
        request.setArgs(args);

        WindyResponse response = NettyClient.invoke(request);
        return response.getResult();
    }

    public static <T> T buildProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RpcProxy());
    }

}
