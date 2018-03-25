package com.zhoulychn;


import com.zhoulychn.Client.Client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Lewis on 2018/3/24
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /*Class<?> returnType = method.getReturnType();
        Object response = Client.call(proxy.getClass(), method, args);
        if (response != null && returnType.equals(response.getClass())) {
            return response;
        } else {
            return null;
        }*/
        return null;
    }

}
