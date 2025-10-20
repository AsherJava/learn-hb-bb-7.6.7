/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.cloud.nacos.NacosDiscoveryProperties
 *  com.alibaba.cloud.nacos.event.NacosDiscoveryInfoChangedEvent
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.event.NacosDiscoveryInfoChangedEvent;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.nvwa.sf.adapter.spring.naming.IMetaModuleStatePublisher;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class NacosMetaModuleStatePublisher
implements IMetaModuleStatePublisher {
    private ApplicationEventPublisher applicationEventPublisher;
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    public NacosMetaModuleStatePublisher(ApplicationEventPublisher applicationEventPublisher, NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    @Override
    public void publish() {
        this.nacosDiscoveryProperties.getMetadata().put("module-state", ServiceNodeStateHolder.getState().name());
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new NacosDiscoveryInfoChangedEvent(this.nacosDiscoveryProperties));
    }
}

