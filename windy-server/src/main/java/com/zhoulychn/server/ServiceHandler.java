package com.zhoulychn.server;

import com.zhoulychn.RegisterCenter;
import com.zhoulychn.ServiceData;
import com.zhoulychn.WindyRequest;
import com.zhoulychn.WindyResponse;

import java.lang.reflect.Method;

/**
 * Created by Lewis on 2018/3/24
 */


public class ServiceHandler {

    public static WindyResponse invoke(WindyRequest request) {
        long start = System.currentTimeMillis();
        ServiceData service = RegisterCenter.getProvider(request.getAppName(), request.getClazz());
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
}
