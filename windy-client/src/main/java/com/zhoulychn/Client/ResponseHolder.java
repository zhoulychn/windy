package com.zhoulychn.Client;

import com.google.common.collect.Maps;
import com.zhoulychn.WindyResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lewis on 2018/3/26
 */
public class ResponseHolder {

    private static Map<String, WindyResponse> resultMap = new HashMap<>();

    public static void put(String uuid, WindyResponse response) {
        resultMap.put(uuid, response);
    }

    public static WindyResponse get(String uuid) {
        return resultMap.get(uuid);
    }
}
