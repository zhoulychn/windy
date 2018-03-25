package com.zhoulychn.Cluster;

import com.google.common.collect.Lists;
import com.zhoulychn.ProviderService;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lewis on 2018/3/24
 */
public class WeightPollingCluster implements Cluster {

    //计数器
    private int index = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public ProviderService select(List<ProviderService> list) {

        ProviderService service = null;
        try {
            lock.tryLock(10, TimeUnit.MILLISECONDS);

            //存放加权后的服务提供者列表
            List<ProviderService> providerList = Lists.newArrayList();
            for (ProviderService provider : list) {
                int weight = provider.getWeight();
                for (int i = 0; i < weight; i++) {
                    providerList.add(provider.copy());
                }
            }

            //若计数大于服务提供者个数,将计数器归0
            if (index >= providerList.size()) {
                index = 0;
            }
            service = providerList.get(index);
            index++;
            return service;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return list.get(0);
    }
}