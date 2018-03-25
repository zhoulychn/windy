package com.zhoulychn.Cluster;

import com.zhoulychn.ProviderService;

import java.util.List;

/**
 * Created by Lewis on 2018/3/24
 */
public interface Cluster {

    ProviderService select(List<ProviderService> list);
}
