/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.condition.rabbitmq;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RabbitMqDirectListenerEnableCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("spring.rabbitmq.listener.direct.auto-startup", Boolean.class, true);
    }
}

