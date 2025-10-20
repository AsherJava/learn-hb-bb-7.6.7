/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.taskschedule.stream.rabbit.condition.EntMqStreamRabbitEnableCondition
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import com.jiuqi.common.taskschedule.stream.rabbit.condition.EntMqStreamRabbitEnableCondition;
import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMQStateCheckEvent;
import com.jiuqi.dc.taskscheduling.core.monitor.RabbitMqQueueMonitor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Conditional(value={EntMqStreamRabbitEnableCondition.class})
public class RabbitMQStateCheckEventListener {
    @EventListener
    @Async
    public void onApplicationEvent(RabbitMQStateCheckEvent event) {
        RabbitMqQueueMonitor.getInstance().recordModuleQueueInfo(event.getModuleName());
    }
}

