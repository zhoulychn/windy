package com.zhoulychn;

import com.zhoulychn.proxy.RpcProxy;

public class Application {
    public static void main(String[] args) {
        UserService service = RpcProxy.buildProxy(UserService.class);
        String lewis = service.getName("-----------------------------lewis-----------------------------");
        System.out.println(lewis);
        String dubbo = service.getName("-----------------------------james-----------------------------");
        System.out.println(dubbo);
    }
}
