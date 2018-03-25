package com.zhoulychn;

/**
 * Created by Lewis on 2018/3/24
 */
public class WindyResponse {

    private String UUID;

    private Object result;

    private long maxTime;

    private Exception exception;

    public WindyResponse(String UUID, Object result, long maxTime) {
        this.UUID = UUID;
        this.result = result;
        this.maxTime = maxTime;
    }

    public WindyResponse() {

    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public String toString() {
        return "WindyResponse{" +
                "UUID='" + UUID + '\'' +
                ", result=" + result +
                ", maxTime=" + maxTime +
                '}';
    }
}
