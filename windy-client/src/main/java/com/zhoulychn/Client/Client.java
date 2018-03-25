package com.zhoulychn.Client;

import java.lang.reflect.Method;

/**
 * Created by Lewis on 2018/3/25
 */
public interface Client {

    Object call(final Class<?> clazz, Method method, Object[] args);
}
