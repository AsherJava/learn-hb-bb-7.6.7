/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb;

import com.jiuqi.common.taskschedule.streamdb.condition.EntMqStreamDbEnableCondition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.common.taskschedule.streamdb"})
@EnableScheduling
@Conditional(value={EntMqStreamDbEnableCondition.class})
public class EntTaskScheduleAutoDbConfiguration {
}

