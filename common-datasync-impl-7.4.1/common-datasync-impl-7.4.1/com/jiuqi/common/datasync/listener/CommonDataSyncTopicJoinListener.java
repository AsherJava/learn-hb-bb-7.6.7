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
import com.jiuqi.common.datasync.declare.CommonDataSyncTopicJoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDataSyncTopicJoinListener
implements JoinListener {
    @Autowired
    private CommonDataSyncMessageConsumer commonDataSyncMessageConsumer;
    @Autowired
    private CommonDataSyncTopicJoinDeclare topicJoinDeclare;

    public String getJoinName() {
        return this.topicJoinDeclare.getName();
    }

    public ReplyTo onMessage(String message) {
        try {
            this.commonDataSyncMessageConsumer.receiveDataSyncTopicMessageHandler(message);
            return new ReplyTo(ReplyStatus.SUCESS, "sucess");
        }
        catch (Exception e) {
            return new ReplyTo(ReplyStatus.FAIL_REJECT, "\u8ba2\u9605\u5404\u670d\u52a1\u7684\u540c\u6b65\u6570\u636e\u4efb\u52a1\u9879\u7684\u4e0a\u62a5\u6ce8\u518c\u5217\u8868\u4fe1\u606f\u53d1\u751f\u5f02\u5e38," + e.getMessage());
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

