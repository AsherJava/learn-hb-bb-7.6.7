/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 *  com.jiuqi.bi.core.messagequeue.MessageItem
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import com.jiuqi.bi.core.messagequeue.MessageItem;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerStartReceiver
implements IMessageReceiver {
    public static final String ID = "com.jiuqi.bi.jobs.schedulerStart";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getGroupId() {
        return ID;
    }

    public void receive(Message msg) {
        if (msg.getItems().isEmpty()) {
            this.logger.warn("\u3010com.jiuqi.bi.jobs.schedulerStart\u3011\u6d88\u606f\u5185\u5bb9\u5f02\u5e38\uff0c\u672a\u627e\u5230\u8282\u70b9\u540d");
            return;
        }
        try {
            Node curNode = DistributionManager.getInstance().getSelfNode();
            String nodeName = ((MessageItem)msg.getItems().get(0)).getResourceId();
            if (nodeName != null && !nodeName.equals(curNode.getName())) {
                return;
            }
        }
        catch (DistributionException e) {
            this.logger.error("\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u5931\u8d25,\u62d2\u7edd\u3010com.jiuqi.bi.jobs.schedulerStart\u3011\u6d88\u606f" + e.getMessage(), e);
            return;
        }
        this.logger.info("\u542f\u52a8\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler:\u5f00\u59cb...");
        ConfigManager.getInstance().clearConfigCache();
        this.logger.info("\u542f\u52a8\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler:\u5237\u65b0\u7f13\u5b58\u5b8c\u6210...");
        try {
            JobBridgeFactory.getInstance().getDefaultBridge().restartAll();
            RealTimeJobRunnerFactory.getInstance().getDefaultRunner().restartAll();
        }
        catch (JobsException e) {
            this.logger.error("\u542f\u52a8\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler\u5931\u8d25", e);
        }
        this.logger.info("\u542f\u52a8\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler:\u5b8c\u6210");
    }
}

