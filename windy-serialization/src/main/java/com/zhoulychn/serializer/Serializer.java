package com.zhoulychn.serializer;

import java.io.IOException;

/**
 * Created by Lewis on 2018/3/24
 */
public interface Serializer {

    //序列化
    byte[] serialize(Object obj) throws IOException;

    //反序列化
    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException;
}
