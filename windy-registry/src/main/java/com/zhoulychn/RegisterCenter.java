package com.zhoulychn;

import com.google.common.collect.Maps;
import com.zhoulychn.serializer.ZkSerializerAdapter;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Lewis on 2018/3/24
 */
public class RegisterCenter {

    private static Logger logger = Logger.getLogger(RegisterCenter.class);

    private static int ZK_SESSION_TIMEOUT;

    private static int ZK_CONNECTION_TIME_OUT;

    private static String ZK_REGISTRY_PATH;

    private static final Map<String, List<ProviderService>> services = Maps.newConcurrentMap();

    private static ExecutorService executor = Executors.newFixedThreadPool(15);

    private static ZkClient client = new ZkClient(ZK_REGISTRY_PATH, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIME_OUT, new ZkSerializerAdapter());
    ;

    static {
        Properties properties = new Properties();
        InputStream input = RegisterCenter.class.getResourceAsStream("zookeeper.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZK_SESSION_TIMEOUT = (int) properties.get("ZK_SESSION_TIMEOUT");
        ZK_CONNECTION_TIME_OUT = (int) properties.get("ZK_CONNECTION_TIME_OUT");
        ZK_REGISTRY_PATH = properties.getProperty("ZK_REGISTRY_PATH");
    }

    public void registerProvider(ConcurrentLinkedQueue<ProviderService> queue) {
        while (queue.size() != 0) {
            Future<?> future = executor.submit(() -> {
                ProviderService each = queue.poll();
                client.writeData(ZK_REGISTRY_PATH + "/" + each.getName(), each.getClazz());
            });
        }
    }

    public void getProvider(String name) {
        List<ProviderService> services = RegisterCenter.services.get(ZK_REGISTRY_PATH + name);
    }

    private void RegisterCenterInit() {
        if (!client.exists(ZK_REGISTRY_PATH)) {
            client.createPersistent(ZK_REGISTRY_PATH, true);
        }

        client.subscribeChildChanges(ZK_REGISTRY_PATH, (parentPath, currentChilds) -> {
            if (currentChilds == null || currentChilds.size() == 0) return;
            for (String each : currentChilds) {
                Object obj = client.readData(ZK_REGISTRY_PATH + "/" + each, true);

                if (obj instanceof ProviderService) {
                    services.computeIfAbsent(ZK_REGISTRY_PATH, k -> new ArrayList<>());
                    services.get(ZK_REGISTRY_PATH).add((ProviderService) obj);
                }
            }
        });
    }


}
