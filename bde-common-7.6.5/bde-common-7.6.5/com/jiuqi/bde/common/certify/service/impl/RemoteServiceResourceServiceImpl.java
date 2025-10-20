/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.va.feign.util.FeignUtil
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.discovery.DiscoveryClient
 */
package com.jiuqi.bde.common.certify.service.impl;

import com.jiuqi.bde.common.certify.BdeRequestCertifyConfig;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.va.feign.util.FeignUtil;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class RemoteServiceResourceServiceImpl
implements RequestCertifyService {
    @Autowired
    private DiscoveryClient disconvertClient;
    private final Random random = new Random();

    @Override
    public String getNvwaUrl() {
        return this.getServiceNode(BdeRequestCertifyConfig.getAppName()).getUri().toString();
    }

    @Override
    public <T> T getFeignClient(Class<T> feignClient, String appCode) {
        ServiceInstance node = this.getServiceNode(appCode);
        String address = node.getUri().toString();
        try {
            return (T)FeignUtil.getDynamicClient(feignClient, (String)node.getUri().toString());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(String.format("\u627e\u4e0d\u5230\u670d\u52a1\uff0c\u540d\u79f0\uff1a%1$s\uff0c\u670d\u52a1\u5730\u5740\uff1a%2$s", BdeRequestCertifyConfig.getAppName(), address), (Throwable)e);
        }
    }

    @Override
    public <T> T getFeignClient(Class<T> feignClient) {
        return this.getFeignClient(feignClient, BdeRequestCertifyConfig.getAppName());
    }

    private ServiceInstance getServiceNode(String serviceName) {
        List serviceInstances = this.disconvertClient.getInstances(serviceName);
        if (CollectionUtils.isEmpty((Collection)serviceInstances)) {
            throw new BusinessRuntimeException(String.format("\u6ca1\u6709\u83b7\u53d6\u5230\u6807\u8bc6\u4e3a\u3010%1$s\u3011\u7684\u670d\u52a1\u8d44\u6e90\uff0c\u8bf7\u68c0\u67e5\u670d\u52a1\u662f\u5426\u6b63\u5e38\u542f\u52a8", serviceName));
        }
        ServiceInstance node = (ServiceInstance)serviceInstances.get(this.random.nextInt(serviceInstances.size()));
        if (node == null) {
            throw new BusinessRuntimeException(String.format("\u627e\u4e0d\u5230\u670d\u52a1\u8282\u70b9\uff0c\u540d\u79f0\uff1a%1$s\uff0c\u8bf7\u68c0\u67e5\u670d\u52a1\u662f\u5426\u6b63\u5e38\u542f\u52a8", serviceName));
        }
        return node;
    }
}

