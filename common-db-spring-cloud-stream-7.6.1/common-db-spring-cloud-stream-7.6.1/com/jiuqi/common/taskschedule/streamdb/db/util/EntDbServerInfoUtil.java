/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.taskschedule.streamdb.db.util;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.common.base.util.StringUtils;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EntDbServerInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(EntDbServerInfoUtil.class);
    @Value(value="${`jiuqi.np.asynctask.executor-serverid`:}")
    private String serveId;
    private String serverName;

    public String getServeId() {
        if (!StringUtils.isEmpty((String)this.serveId)) {
            return this.serveId;
        }
        this.serveId = UUID.randomUUID().toString();
        return this.serveId;
    }

    public String getServerName() {
        if (!StringUtils.isEmpty((String)this.serverName)) {
            return this.serverName;
        }
        try {
            Node node = DistributionManager.getInstance().self();
            this.serverName = node.getMachineName() + "/" + node.getIp();
        }
        catch (Exception e) {
            logger.error("\u672a\u80fd\u83b7\u53d6\u670d\u52a1IP\u5730\u5740\u3002", e);
            this.serverName = UUID.randomUUID().toString();
        }
        return this.serverName;
    }
}

