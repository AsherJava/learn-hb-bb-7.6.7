/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  org.springframework.cloud.client.DefaultServiceInstance
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.serviceregistry.Registration
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.register;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ApplicationContext;

public class NodeKeeperRegistration
implements Registration,
ServiceInstance {
    private ApplicationContext context;

    public NodeKeeperRegistration(ApplicationContext context) {
        this.context = context;
    }

    public String getServiceId() {
        return DistributionManager.getInstance().self().getApplicationName();
    }

    public String getHost() {
        return DistributionManager.getInstance().self().getIp();
    }

    public int getPort() {
        String port = DistributionManager.getInstance().self().getPort();
        return Integer.parseInt(port);
    }

    public boolean isSecure() {
        return DistributionManager.getInstance().self().isHttps();
    }

    public URI getUri() {
        return DefaultServiceInstance.getUri((ServiceInstance)this);
    }

    public Map<String, String> getMetadata() {
        return new HashMap<String, String>();
    }
}

