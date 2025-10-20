/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeState
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.nvwa.remote.annotation.RemoteService
 *  com.jiuqi.nvwa.remote.service.IRemoteServiceHelper
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.discovery.DiscoveryClient
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud.impl;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.nvwa.remote.annotation.RemoteService;
import com.jiuqi.nvwa.remote.service.IRemoteServiceHelper;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceNodeManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.SFService;
import com.jiuqi.nvwa.sf.adapter.spring.util.StackTraceUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class SFRemoteResourceManage {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    @RemoteService
    private List<IServiceManager> serviceManagerResources;
    @Autowired
    @RemoteService
    private List<IServiceNodeManager> serviceNodeManagerResources;
    @Autowired
    private IRemoteServiceHelper remoteServiceHelper;
    @Autowired
    private DiscoveryClient discoveryClient;

    public List<IServiceManager> getServiceManagerResources() {
        return Collections.unmodifiableList(this.serviceManagerResources);
    }

    public List<IServiceNodeManager> getServiceNodeManagerResources() {
        return Collections.unmodifiableList(this.serviceNodeManagerResources);
    }

    public Map<SFService, IServiceManager> loadProductInfo() {
        HashMap<SFService, IServiceManager> map = new HashMap<SFService, IServiceManager>();
        for (IServiceManager serviceManagerResource : this.getServiceManagerResources()) {
            try {
                map.put(serviceManagerResource.getProductInfo(), serviceManagerResource);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return map;
    }

    public IServiceManager getServiceManagerResource(String serviceName) {
        for (IServiceManager serviceManagerResource : this.getServiceManagerResources()) {
            try {
                if (!serviceManagerResource.getServiceName().equalsIgnoreCase(serviceName)) continue;
                return serviceManagerResource;
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public boolean isAllServiceManagerResourceReady() {
        AtomicBoolean ready = new AtomicBoolean(true);
        block0: for (IServiceManager iServiceManager : this.getServiceManagerResources()) {
            if (!this.remoteServiceHelper.isRemote((Object)iServiceManager)) {
                if (ServiceNodeStateHolder.getState() == ServiceNodeState.RUNNING) continue;
                this.logger.warn("\u670d\u52a1\u8282\u70b9\u72b6\u6001\uff1a\u672c\u5730\u670d\u52a1\u8282\u70b9\u72b6\u6001\u4e0d\u5728\u8fd0\u884c\u4e2d:{}", (Object)ServiceNodeStateHolder.getState());
                StackTraceUtil.printStackTrace("SFRemoteResourceManage:isAllServiceManagerResourceReady:local_false");
                ready.set(false);
                break;
            }
            String serviceId = this.remoteServiceHelper.getServiceId((Object)iServiceManager);
            for (ServiceInstance serviceInstance : this.discoveryClient.getInstances(serviceId)) {
                Map metadata = serviceInstance.getMetadata();
                String moduleState = metadata.getOrDefault("module-state", ServiceNodeState.RUNNING.name());
                if (ServiceNodeState.valueOf((String)moduleState) != ServiceNodeState.LICENCE_UNHANDLED && ServiceNodeState.valueOf((String)moduleState) != ServiceNodeState.MODULE_UNHANDLED) continue;
                String uri = serviceInstance.getUri().toString();
                this.logger.warn("\u670d\u52a1\u8282\u70b9\u72b6\u6001\uff1a\u8fdc\u7aef\u670d\u52a1\u5b58\u5728\u6388\u6743\u95ee\u9898\u6216\u8005\u811a\u672c\u95ee\u9898:{}", (Object)uri);
                StackTraceUtil.printStackTrace("SFRemoteResourceManage:isAllServiceManagerResourceReady:remote_false:serviceId:" + serviceId + ":uri:" + uri + ":moduleState:" + moduleState);
                ready.set(false);
                continue block0;
            }
        }
        return ready.get();
    }
}

