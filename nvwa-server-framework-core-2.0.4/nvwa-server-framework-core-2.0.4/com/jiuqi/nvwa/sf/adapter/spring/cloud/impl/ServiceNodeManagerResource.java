/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud.impl;

import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceNodeManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.ServiceNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ServiceNodeManagerResource
implements IServiceNodeManager {
    @Override
    public List<ServiceNode> getNode() {
        List allNode;
        try {
            allNode = DistributionManager.getInstance().getAllNode();
        }
        catch (DistributionException e) {
            throw new RuntimeException(e);
        }
        String appName = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("spring.application.name");
        ArrayList<ServiceNode> nodes = new ArrayList<ServiceNode>();
        for (Node node : allNode) {
            if (!node.isAlive() || !node.getApplicationName().equalsIgnoreCase(appName)) continue;
            ServiceNode serviceNode = new ServiceNode();
            BeanUtils.copyProperties(node, serviceNode);
            nodes.add(serviceNode);
        }
        return nodes;
    }
}

