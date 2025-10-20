/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.join.api.extend;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinMode;
import org.springframework.stereotype.Component;

@Component
public class ChangeStatusJoinDeclare
implements JoinDeclare {
    @Override
    public String getName() {
        return "CHANGE_JOIN_LISTENER_STATUS";
    }

    @Override
    public String getTitle() {
        return "\u6539\u53d8\u76d1\u542c\u72b6\u6001";
    }

    @Override
    public JoinMode getJoinMode() {
        return JoinMode.TOPIC_NODE;
    }

    @Override
    public boolean isDurable() {
        return false;
    }

    @Override
    public boolean isAutoDelete() {
        return true;
    }

    @Override
    public long getMessageTTL() {
        return 5000L;
    }
}

