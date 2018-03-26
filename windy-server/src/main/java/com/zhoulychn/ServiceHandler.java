package com.zhoulychn;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Lewis on 2018/3/24
 */


public class ServiceHandler {

    private static RegisterCenter center = RegisterCenter.getSingleton();

    public static WindyResponse invoke(WindyRequest request) {
        long start = System.currentTimeMillis();
        ServiceData service = center.getProvider(request.getAppName(), request.getClazz());
        Method method = service.getMap().get(request.getMethod());
        WindyResponse response = new WindyResponse();
        response.setUUID(request.getUUID());
        try {
            Object result = method.invoke(service.getObj(), request.getArgs());
            response.setResult(result);
        } catch (Exception e) {
            response.setException(e);
        }
        long end = System.currentTimeMillis();
        response.setTime(end - start);
        return response;
    }

    public static void ServiceRegister(List<Class> list) {
        center.RegisterCenterInit();
        center.registerProvider(list);
    }
}
