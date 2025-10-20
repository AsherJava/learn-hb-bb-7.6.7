/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.np.core.application.NpApplication
 */
package com.jiuqi.dc.taskscheduling.log.impl.util;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.np.core.application.NpApplication;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskSchedulingServerUtil
implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulingServerUtil.class);
    @Autowired
    private NpApplication npApplication;
    @Value(value="${`jiuqi.np.asynctask.executor-serverid`:NULL}")
    private String serveId;
    private String serverIp;

    @Override
    public void afterPropertiesSet() {
        this.init();
    }

    private void init() {
        if (this.serveId == null || this.serveId.length() == 0 || "NULL".equals(this.serveId)) {
            this.serveId = UUID.randomUUID().toString();
        }
        try {
            InetAddress address = this.getLocalHostExactAddress();
            if (Objects.nonNull(address)) {
                this.serverIp = address.getHostAddress();
            }
            if (Objects.isNull(this.serverIp)) {
                this.serverIp = DistributionManager.getInstance().self().getIp().replace("-", "@");
            }
        }
        catch (Exception e) {
            logger.error("\u672a\u80fd\u83b7\u53d6\u670d\u52a1IP\u5730\u5740\u3002", e);
            this.serverIp = UUID.randomUUID().toString();
        }
    }

    private InetAddress getLocalHostExactAddress() {
        InetAddress candidateAddress = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();
                while (inetAddrs.hasMoreElements()) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (inetAddr.isLoopbackAddress()) continue;
                    if (inetAddr.isSiteLocalAddress()) {
                        return inetAddr;
                    }
                    if (candidateAddress != null) continue;
                    candidateAddress = inetAddr;
                }
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u673a\u5668ip\u5f02\u5e38\uff1a", e);
        }
        return candidateAddress;
    }

    public String getServeId() {
        return this.serveId;
    }

    public String getServerIp() {
        return this.serverIp;
    }
}

