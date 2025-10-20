/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.cloud.client.serviceregistry.Registration
 *  org.springframework.cloud.client.serviceregistry.ServiceRegistry
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.register;

import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.StartUp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NodeKeeperServiceRegistry
implements ServiceRegistry<Registration> {
    private static final Logger log = LoggerFactory.getLogger(NodeKeeperServiceRegistry.class);

    public void register(Registration registration) {
        StartUp startUp = (StartUp)SpringBeanUtils.getBean(StartUp.class);
        startUp.registNode(SpringBeanUtils.getApplicationContext());
    }

    public void deregister(Registration registration) {
    }

    public void close() {
    }

    public void setStatus(Registration registration, String status) {
    }

    public Object getStatus(Registration registration) {
        try {
            List nodesByApplicationName = DistributionManager.getInstance().findNodesByApplicationName(registration.getInstanceId());
            for (Node node : nodesByApplicationName) {
                if (!node.getIp().equalsIgnoreCase(registration.getHost())) continue;
                return node.isAlive();
            }
        }
        catch (DistributionException e) {
            log.error("get all instance of {} error,", (Object)registration.getInstanceId(), (Object)e);
        }
        return null;
    }
}

