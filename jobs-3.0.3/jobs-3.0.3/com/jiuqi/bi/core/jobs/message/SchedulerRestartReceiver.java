/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerRestartReceiver
implements IMessageReceiver {
    public static final String ID = "com.jiuqi.bi.jobs.schedulerRestart";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getGroupId() {
        return ID;
    }

    public void receive(Message msg) {
        this.logger.info("\u91cd\u542f\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler:\u5f00\u59cb...");
        ConfigManager.getInstance().clearConfigCache();
        this.logger.info("\u91cd\u542f\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler:\u5237\u65b0\u7f13\u5b58\u5b8c\u6210...");
        try {
            AbstractJobBridge defaultBridge = JobBridgeFactory.getInstance().getDefaultBridge();
            if (defaultBridge.isReady()) {
                defaultBridge.restartAll();
            } else {
                this.logger.info("{}:\u8df3\u8fc7\u5173\u95ed\u72b6\u6001\u7684\u8c03\u5ea6\u5668\uff0c\u4e0d\u505a\u542f\u52a8\u64cd\u4f5c", (Object)defaultBridge.getBridgeType());
            }
            RealTimeJobRunnerFactory.getInstance().getDefaultRunner().restartAll();
        }
        catch (JobsException e) {
            this.logger.error("\u91cd\u542f\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler\u5931\u8d25", e);
        }
        this.logger.info("\u91cd\u542f\u4efb\u52a1\u7ba1\u7406\u6240\u6709scheduler:\u5b8c\u6210");
    }
}

