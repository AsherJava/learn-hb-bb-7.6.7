/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  com.jiuqi.va.join.api.domain.JoinMode
 */
package com.jiuqi.gcreport.aidocaudit.config;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinMode;
import org.springframework.stereotype.Component;

@Component
public class AidocauditRuleResultDeclare
implements JoinDeclare {
    public String getName() {
        return "AIDOCAUDIT_RULE_RESULT";
    }

    public String getTitle() {
        return "\u6a21\u677f\u5ba1\u6838\u7ed3\u679c\u961f\u5217";
    }

    public JoinMode getJoinMode() {
        return JoinMode.QUEUE;
    }

    public boolean isDurable() {
        return true;
    }

    public boolean isAutoDelete() {
        return false;
    }

    public long getMessageTTL() {
        return -1L;
    }
}

