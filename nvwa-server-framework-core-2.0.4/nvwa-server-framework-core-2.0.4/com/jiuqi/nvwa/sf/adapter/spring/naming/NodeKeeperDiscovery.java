/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.core.nodekeeper.IConnectionProvider
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  org.springframework.cloud.client.DefaultServiceInstance
 *  org.springframework.cloud.client.ServiceInstance
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming;

import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.IConnectionProvider;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

public class NodeKeeperDiscovery {
    Logger logger = LoggerFactory.getLogger(NodeKeeperDiscovery.class);
    private static final long OVERTIME = 30000L;
    private DataSource dataSource;
    private NedisCache serviceInstanceCache;

    public NodeKeeperDiscovery(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public NodeKeeperDiscovery(DataSource dataSource, NedisCacheProvider cacheProvider) {
        this.dataSource = dataSource;
        NedisCacheManager cacheManager = cacheProvider.getCacheManager("nodeKeepCacheManager");
        this.serviceInstanceCache = cacheManager.getCache("services");
    }

    public List<ServiceInstance> getInstances(String serviceId) {
        ArrayList<Node> nodesByService;
        Cache.ValueWrapper valueWrapper;
        if (null != this.serviceInstanceCache && null != (valueWrapper = this.serviceInstanceCache.get(serviceId)) && valueWrapper.get() != null) {
            return (List)valueWrapper.get();
        }
        this.checkConnection();
        DistributionManager distributionManager = DistributionManager.getInstance();
        try {
            nodesByService = distributionManager.findNodesByApplicationName(serviceId);
            long currentTime = System.currentTimeMillis();
            ArrayList<Node> nodes = new ArrayList<Node>();
            for (Node node : nodesByService) {
                if (currentTime - node.getLastTimeStamp() < 30000L) {
                    nodes.add(node);
                    continue;
                }
                this.logger.warn(String.format("nodekeeperDiscovey\uff1a\u8282\u70b9\u5931\u6d3b\uff0c\u8282\u70b9\u4fe1\u606f\uff1amachineName\uff1a%s\uff0cip\uff1a%s\uff0cport\uff1a%s\u3002\u5f53\u524d\u670d\u52a1\u5668\u65f6\u95f4\u6233\uff1a%s,\u8282\u70b9\u6700\u540e\u6d3b\u8dc3\u65f6\u95f4\u6233\uff1a%s\uff0c\u76f8\u5dee\u5927\u4e8e\uff1a%s\u6beb\u79d2", node.getMachineName(), node.getIp(), node.getPort(), currentTime, node.getLastTimeStamp(), 30000L));
            }
            nodesByService = nodes;
            nodesByService.sort((o1, o2) -> o2.getLastTimeStamp() - o1.getLastTimeStamp() > 0L ? 1 : -1);
        }
        catch (Exception e) {
            if (DistributionManager.getInstance().self() != null) {
                this.logger.error(e.getMessage(), e);
            }
            return Collections.emptyList();
        }
        ArrayList<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>();
        for (Node node : nodesByService) {
            serviceInstances.add((ServiceInstance)this.transNodeToService(node, serviceId));
        }
        if (null != this.serviceInstanceCache) {
            this.serviceInstanceCache.put(serviceId, serviceInstances);
        }
        return serviceInstances;
    }

    private DefaultServiceInstance transNodeToService(Node node, String serviceId) {
        if (StringUtils.isEmpty((String)node.getPort())) {
            this.logger.error("\u7aef\u53e3\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5{}\u670d\u52a1\u7aef\u53e3", (Object)(node.getApplicationName() + "[" + node.getName() + "]"));
        }
        int port = Integer.parseInt(node.getPort());
        HashMap<String, String> metadata = new HashMap<String, String>();
        metadata.put("group", node.getGroup());
        metadata.put("context-path", node.getContextPath());
        metadata.put("machine-name", node.getMachineName());
        metadata.put("machine-code", node.getMachineCode());
        metadata.put("module-state", node.getApplicationState().name());
        return new DefaultServiceInstance(node.getName(), serviceId, node.getIp(), port, node.isHttps(), metadata);
    }

    public List<String> getServices() {
        this.checkConnection();
        DistributionManager distributionManager = DistributionManager.getInstance();
        try {
            return distributionManager.findAvaliableServices();
        }
        catch (DistributionException e) {
            this.logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void checkConnection() {
        if (GlobalConnectionProviderManager.getConnectionProvider() == null) {
            GlobalConnectionProviderManager.setConnectionProvider((IConnectionProvider)new IConnectionProvider(){

                public Connection openConnection() throws SQLException {
                    return NodeKeeperDiscovery.this.dataSource.getConnection();
                }

                public Connection openHostedConnection() throws SQLException {
                    return NodeKeeperDiscovery.this.dataSource.getConnection();
                }
            });
        }
    }
}

