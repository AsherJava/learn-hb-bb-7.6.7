/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigCacheRefreshReceiver
implements IMessageReceiver {
    public static final String ID = "com.jiuqi.bi.jobs.configCacheRefresh";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getGroupId() {
        return ID;
    }

    public void receive(Message msg) {
        ConfigManager.getInstance().clearConfigCache();
        this.logger.debug("\u5237\u65b0\u4efb\u52a1\u7ba1\u7406\u914d\u7f6e\u7f13\u5b58\u5b8c\u6210");
    }
}

