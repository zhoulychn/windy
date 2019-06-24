package com.zhoulychn;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Lewis on 2018/3/24
 */

@Data
public class WindyRequest implements Serializable {

    private String appName;

    private String UUID;

    private String clazz;

    private String method;

    private Object[] args;

    private long maxTime;
}
