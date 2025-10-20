/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.condition.rabbitmq;

import com.jiuqi.common.base.condition.rabbitmq.RabbitMqDirectListenerEnableCondition;
import com.jiuqi.common.base.condition.rabbitmq.RabbitMqSimpleListenerEnableCondition;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;

public class RabbitMqAnyListenerEnableCondition
extends AnyNestedCondition {
    public RabbitMqAnyListenerEnableCondition() {
        super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @Conditional(value={RabbitMqSimpleListenerEnableCondition.class})
    static class OnRabbitMqSimpleListenerEnableCondition {
        OnRabbitMqSimpleListenerEnableCondition() {
        }
    }

    @Conditional(value={RabbitMqDirectListenerEnableCondition.class})
    static class OnRabbitMqDirectListenerEnableCondition {
        OnRabbitMqDirectListenerEnableCondition() {
        }
    }
}

