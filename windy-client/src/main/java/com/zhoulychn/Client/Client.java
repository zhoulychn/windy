package com.zhoulychn.Client;

import java.lang.reflect.Method;

/**
 * Created by Lewis on 2018/3/25
 */
public interface Client {

   Object call(Object proxy, Method method, Object[] args);
}
