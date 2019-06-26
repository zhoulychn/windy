package com.zhoulychn.serialize.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Lewis on 2018/3/24
 */
public class KryoSerializer {

    public byte[] serialize(Object obj) throws IOException {
        Kryo kryo = new Kryo();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); Output output = new Output(byteArrayOutputStream);) {
            kryo.writeObject(output, obj);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException {
        Kryo kryo = new Kryo();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data); Input input = new Input(byteArrayInputStream)) {
            T t = kryo.readObject(input, clazz);
            return t;
        }
    }
}
