package com.zhoulychn.serializer;

import java.io.*;

/**
 * Created by Lewis on 2018/3/24
 */

public class JavaSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream output = new ObjectOutputStream(byteArrayOutputStream);) {
            output.writeObject(obj);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream input = new ObjectInputStream(byteArrayInputStream);) {
            return (T) input.readObject();
        }
    }
}

