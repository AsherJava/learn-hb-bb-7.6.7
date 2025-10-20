/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.join.api.domain;

import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;

public interface JoinListener {
    public String getJoinName();

    public ReplyTo onMessage(String var1);

    default public ReplyTo onException() {
        return new ReplyTo(ReplyStatus.FAIL_REJECT);
    }

    default public int getMaxConsumers() {
        return 1;
    }

    @Deprecated
    default public int getBatchSize() {
        return 1;
    }

    default public int getPrefetchCount() {
        return -1;
    }

    default public boolean isAutoStart() {
        return true;
    }

    default public int getRequeueCount() {
        return 3;
    }
}

