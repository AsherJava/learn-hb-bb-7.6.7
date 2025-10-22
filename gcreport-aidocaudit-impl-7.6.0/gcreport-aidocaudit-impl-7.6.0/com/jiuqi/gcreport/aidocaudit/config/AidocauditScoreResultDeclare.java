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
public class AidocauditScoreResultDeclare
implements JoinDeclare {
    public String getName() {
        return "AIDOCAUDIT_SCORE_RESULT";
    }

    public String getTitle() {
        return "\u8bc4\u5206\u7ed3\u679c\u961f\u5217";
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

