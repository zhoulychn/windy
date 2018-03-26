import com.zhoulychn.SerialFactory;
import com.zhoulychn.serializer.SerializerType;
import com.zhoulychn.serializer.Serializer;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Lewis on 2018/3/24
 */
public class SerialFactoryTest {

    public static void test(SerializerType type) {
        Serializer serializer = SerialFactory.get(SerializerType.kryo);

        TestClass testClass = new TestClass();
        testClass.setName("lewis");
        testClass.setDesc("student");
        try {
            byte[] bys = serializer.serialize(testClass);

            TestClass newTestClass = serializer.deserialize(bys, TestClass.class);
            System.out.println(newTestClass);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void KryoTest() {
        test(SerializerType.kryo);
        test(SerializerType.defaultJava);
    }
}
