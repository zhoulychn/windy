package com.zhoulychn;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Lewis on 2018/3/24
 */


public class ServiceHandler {

    private static RegisterCenter center = RegisterCenter.getSingleton();

    public static WindyResponse invoke(WindyRequest request) {
        Method method = center.getProvider(request.getClazz().getName(), request.getMethod().toString());
        return null;
    }

}
