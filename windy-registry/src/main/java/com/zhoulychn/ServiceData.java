package com.zhoulychn;

import com.google.common.collect.Maps;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Lewis on 2018/3/26
 */

@Data
public class ServiceData {

    private String name;

    private Object obj;

    private Class clazz;

    private Map<String, Method> map = Maps.newConcurrentMap();
}
