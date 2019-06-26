package com.zhoulychn.transport;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Lewis on 2018/3/24
 */

@Data
public class WindyResponse implements Serializable {

    private String UUID;

    private Object result;

    private Long time;

    private Exception exception;
}
