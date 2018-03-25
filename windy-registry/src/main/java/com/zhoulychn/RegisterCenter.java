package com.zhoulychn;

import com.google.common.collect.Maps;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Op;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Lewis on 2018/3/24
 */
public class RegisterCenter {

    private static Logger logger = Logger.getLogger(RegisterCenter.class);

    private static int ZK_SESSION_TIMEOUT;

    private static int ZK_CONNECTION_TIME_OUT;

    private static String ZK_REGISTRY_PATH;

    private static final Map<String, List<ProviderService>> services = Maps.newConcurrentMap();


    private static ZkClient client;

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

    public void registerProvider(final List<ProviderService> list) {


        for (ProviderService item : list) {
            ZkClient client = new ZkClient(ZK_REGISTRY_PATH, ZK_SESSION_TIMEOUT);

        }
    }

    private void RegisterCenterInit() {
        ZkClient client = new ZkClient(ZK_REGISTRY_PATH, ZK_SESSION_TIMEOUT);
        if (client.exists(ZK_REGISTRY_PATH)) return;
        client.createPersistent(ZK_REGISTRY_PATH, true);


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
