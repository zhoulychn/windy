package com.zhoulychn;

import com.zhoulychn.proxy.RpcProxy;

/**
 * Created by Lewis on 2018/3/25
 */
public class Application {

    public static void main(String[] args) {
        UserService service = RpcProxy.buildProxy(UserService.class);
        String lewis = service.getName("lewis");
        System.out.println(lewis);
    }
}
