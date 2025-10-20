/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.join.api.domain;

import com.jiuqi.va.join.api.domain.JoinMode;

public interface JoinDeclare {
    public String getName();

    public String getTitle();

    default public String[] getFormerNames() {
        return null;
    }

    default public String getBizType() {
        return null;
    }

    default public String getGroupName() {
        return null;
    }

    default public JoinMode getJoinMode() {
        return JoinMode.QUEUE;
    }

    default public boolean isDurable() {
        return true;
    }

    default public boolean isAutoDelete() {
        return false;
    }

    default public long getMessageTTL() {
        return -1L;
    }
}

