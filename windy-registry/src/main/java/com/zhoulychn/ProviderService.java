package com.zhoulychn;

import java.io.Serializable;

/**
 * Created by Lewis on 2018/3/24
 */
public class ProviderService implements Serializable {

    private Class<?> clazz;

    private String name;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
