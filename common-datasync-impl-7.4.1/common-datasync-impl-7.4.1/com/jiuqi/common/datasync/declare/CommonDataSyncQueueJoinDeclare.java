/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  com.jiuqi.va.join.api.domain.JoinMode
 */
package com.jiuqi.common.datasync.declare;

import com.jiuqi.common.datasync.autoconfigure.CommonDataSyncAutoConfiguration;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDataSyncQueueJoinDeclare
implements JoinDeclare {
    @Autowired
    private CommonDataSyncAutoConfiguration dataSyncAutoConfiguration;

    public String getName() {
        return this.getName(this.dataSyncAutoConfiguration.getDataSyncProperties().getServiceName());
    }

    public String getName(String serviceName) {
        return "GC_ENT_COMMON_DATASYNC_QUEUE_" + serviceName;
    }

    public String getTitle() {
        return "\u901a\u7528\u6570\u636e\u540c\u6b65\u4efb\u52a1\u9879\u6d88\u8d39\u961f\u5217\u8fde\u63a5\u70b9[" + this.dataSyncAutoConfiguration.getDataSyncProperties().getServiceName() + "]";
    }

    public JoinMode getJoinMode() {
        return JoinMode.QUEUE;
    }
}

