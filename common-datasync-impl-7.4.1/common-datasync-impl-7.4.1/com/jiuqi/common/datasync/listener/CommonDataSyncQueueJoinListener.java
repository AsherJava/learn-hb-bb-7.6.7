/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 */
package com.jiuqi.common.datasync.listener;

import com.jiuqi.common.datasync.consumer.CommonDataSyncMessageConsumer;
import com.jiuqi.common.datasync.declare.CommonDataSyncQueueJoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDataSyncQueueJoinListener
implements JoinListener {
    @Autowired
    private CommonDataSyncQueueJoinDeclare queueJoinDeclare;
    @Autowired
    private CommonDataSyncMessageConsumer commonDataSyncMessageConsumer;

    public String getJoinName() {
        return this.queueJoinDeclare.getName();
    }

    public ReplyTo onMessage(String message) {
        try {
            this.commonDataSyncMessageConsumer.receiveDataSyncQueueMessageHandler(message);
            return new ReplyTo(ReplyStatus.SUCESS, "sucess");
        }
        catch (Exception e) {
            return new ReplyTo(ReplyStatus.FAIL_REJECT, "\u6570\u636e\u540c\u6b65\u53d1\u751f\u5f02\u5e38," + e.getMessage());
        }
    }

    public ReplyTo onException() {
        return new ReplyTo(ReplyStatus.FAIL_REJECT);
    }

    public int getMaxConsumers() {
        return 1;
    }

    public int getBatchSize() {
        return 1;
    }

    public boolean isAutoStart() {
        return true;
    }
}

