package com.zhoulychn;

import java.io.Serializable;

/**
 * Created by Lewis on 2018/3/24
 */
public class WindyResponse implements Serializable {

    private String UUID;

    private Object result;

    private Long time;

    private Exception exception;

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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
