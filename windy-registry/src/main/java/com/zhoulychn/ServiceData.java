package com.zhoulychn;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Lewis on 2018/3/26
 */
public class ServiceData {

    private String name;

    private Object obj;

    private Class clazz;

    private Map<String, Method> map = Maps.newConcurrentMap();

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Map<String, Method> getMap() {
        return map;
    }

    public void setMap(Map<String, Method> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "ServiceData{" +
                "name='" + name + '\'' +
                ", obj=" + obj +
                ", clazz=" + clazz +
                ", map=" + map +
                '}';
    }
}
