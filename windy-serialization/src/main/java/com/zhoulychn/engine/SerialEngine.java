package com.zhoulychn.engine;

import com.zhoulychn.serializer.KryoSerializer;
import com.zhoulychn.serializer.SerializerType;
import com.zhoulychn.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lewis on 2018/3/24
 */
public class SerialEngine {

    private static final Map<SerializerType, Serializer> container = new HashMap<>();


    private synchronized static void init() {
        if (container.size() != 0) return;
        container.put(SerializerType.kryo, new KryoSerializer());
    }

    public static Serializer get(SerializerType type) {
        if (container.size() == 0) init();
        return container.get(type);
    }
}
