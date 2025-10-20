/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.taskschedule.stream.util.EntMqEnvironmentMatchUtil
 */
package com.jiuqi.common.taskschedule.stream.rabbit.condition;

import com.jiuqi.common.taskschedule.stream.util.EntMqEnvironmentMatchUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EntMqStreamRabbitEnableCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (!EntMqEnvironmentMatchUtil.matchType((ConditionContext)context, (String)"rabbit")) {
            return false;
        }
        Boolean directListenerEnable = context.getEnvironment().getProperty("spring.rabbitmq.listener.direct.auto-startup", Boolean.class);
        Boolean simpleListenerEnable = context.getEnvironment().getProperty("spring.rabbitmq.listener.simple.auto-startup", Boolean.class);
        return Boolean.TRUE.equals(directListenerEnable) || Boolean.TRUE.equals(simpleListenerEnable);
    }
}

