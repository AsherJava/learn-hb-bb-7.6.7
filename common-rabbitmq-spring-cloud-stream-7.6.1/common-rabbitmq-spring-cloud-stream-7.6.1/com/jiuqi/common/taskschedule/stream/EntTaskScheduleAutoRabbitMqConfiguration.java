/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.stream;

import com.jiuqi.common.taskschedule.stream.rabbit.condition.EntMqStreamRabbitEnableCondition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.common.taskschedule.stream.rabbit"})
@Conditional(value={EntMqStreamRabbitEnableCondition.class})
public class EntTaskScheduleAutoRabbitMqConfiguration {
}

