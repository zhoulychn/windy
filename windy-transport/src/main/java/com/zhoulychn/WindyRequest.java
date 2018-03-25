package com.zhoulychn;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Lewis on 2018/3/24
 */
public class WindyRequest implements Serializable {

    private String UUID;

    private Class clazz;

    private Method method;

    private Object[] args;

    private long maxTime;

    private String appName;


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public WindyRequest() {

    }

    public WindyRequest(String UUID, Class clazz, Method method, Object[] args, long maxTime, String appName) {
        this.UUID = UUID;
        this.clazz = clazz;
        this.method = method;
        this.args = args;
        this.maxTime = maxTime;
        this.appName = appName;
    }
}
