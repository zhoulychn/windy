package com.zhoulychn.serializer;

import com.zhoulychn.SerialEngine;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.IOException;

/**
 * Created by Lewis on 2018/3/25
 */
public class ZkSerializerAdapter implements ZkSerializer {

    private final Serializer serializer = SerialEngine.get(SerializerType.kryo);

    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
        try {
            return serializer.serialize(data);
        } catch (IOException e) {
            throw new ZkMarshallingError(e.getMessage());
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            return serializer.deserialize(bytes, Object.class);
        } catch (IOException | ClassNotFoundException e) {
            throw new ZkMarshallingError(e.getMessage());
        }
    }
}
