/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  com.jiuqi.va.join.api.domain.JoinMode
 */
package com.jiuqi.common.datasync.declare;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinMode;
import org.springframework.stereotype.Component;

@Component
public class CommonDataSyncTopicJoinDeclare
implements JoinDeclare {
    public String getName() {
        return "GC_ENT_COMMON_DATASYNC_TOPIC";
    }

    public String getTitle() {
        return "\u901a\u7528\u6570\u636e\u540c\u6b65\u4efb\u52a1\u9879\u81ea\u52a8\u6ce8\u518c\u961f\u5217\u8fde\u63a5\u70b9";
    }

    public JoinMode getJoinMode() {
        return JoinMode.TOPIC_SERVER;
    }
}

