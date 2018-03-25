package com.zhoulychn;

import java.lang.reflect.Proxy;

/**
 * Created by Lewis on 2018/3/25
 */
public class Application {

    public static void main(String[] args) {

        Class[] list = new Class[]{UserService.class};

        UserService result = (UserService) Proxy.newProxyInstance(Application.class.getClassLoader(), list, new ServiceProxy());

        String name = result.getName("hello,lewis");

        System.out.println(name);
    }
}
