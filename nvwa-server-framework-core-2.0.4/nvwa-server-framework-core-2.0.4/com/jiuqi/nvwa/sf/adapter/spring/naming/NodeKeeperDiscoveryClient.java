/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.discovery.DiscoveryClient
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming;

import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscovery;
import java.util.List;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class NodeKeeperDiscoveryClient
implements DiscoveryClient {
    private NodeKeeperDiscovery nodeKeeperDiscovery;
    public static final String DESCRIPTION = "Join-Cheer NVWA Basic Discovery Client";

    public NodeKeeperDiscoveryClient(NodeKeeperDiscovery nodeKeeperDiscovery) {
        this.nodeKeeperDiscovery = nodeKeeperDiscovery;
    }

    public String description() {
        return DESCRIPTION;
    }

    public List<ServiceInstance> getInstances(String serviceId) {
        return this.nodeKeeperDiscovery.getInstances(serviceId);
    }

    public List<String> getServices() {
        return this.nodeKeeperDiscovery.getServices();
    }

    public int getOrder() {
        return 1;
    }
}

