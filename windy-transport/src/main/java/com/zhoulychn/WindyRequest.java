package com.zhoulychn;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Lewis on 2018/3/24
 */
public class WindyRequest implements Serializable {

    private String appName;

    private String UUID;

    private String clazz;

    private String method;

    private Object[] args;

    private long maxTime;


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
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

    @Override
    public String toString() {
        return "WindyRequest{" +
                "UUID='" + UUID + '\'' +
                ", clazz='" + clazz + '\'' +
                ", method='" + method + '\'' +
                ", args=" + Arrays.toString(args) +
                ", maxTime=" + maxTime +
                ", appName='" + appName + '\'' +
                '}';
    }
}
