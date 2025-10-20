/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.taskschedule.stream.util.EntMqEnvironmentMatchUtil
 */
package com.jiuqi.common.taskschedule.streamdb.condition;

import com.jiuqi.common.taskschedule.stream.util.EntMqEnvironmentMatchUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EntMqStreamDbEnableCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return EntMqEnvironmentMatchUtil.matchType((ConditionContext)context, (String)"entdb");
    }
}

