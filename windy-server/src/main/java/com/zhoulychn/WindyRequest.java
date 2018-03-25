package com.zhoulychn;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Lewis on 2018/3/24
 */
public class WindyRequest implements Serializable {

    private String UUID;

    private ProviderService service;

    private String methodName;

    private Object[] params;

    private long maxTime;

    private String appName;

    public WindyRequest(String UUID, ProviderService service, String methodName, Object[] params, long maxTime, String appName) {
        this.UUID = UUID;
        this.service = service;
        this.methodName = methodName;
        this.params = params;
        this.maxTime = maxTime;
        this.appName = appName;
    }

    public WindyRequest() {

    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public ProviderService getService() {
        return service;
    }

    public void setService(ProviderService service) {
        this.service = service;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
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
                ", service=" + service +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                ", maxTime=" + maxTime +
                ", appName='" + appName + '\'' +
                '}';
    }
}
