/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.spring.config;

import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.util.NotificationSendUtil;
import com.jiuqi.nr.workflow2.events.register.WorkflowSettingsActionEventRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@ComponentScan(basePackages={"com.jiuqi.nr.workflow2.events.helper"})
@Configuration
public class NrWorkflow2SpringConfig {
    @Bean(value={"com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper"})
    public EventDependentServiceHelper getEventDependentServiceHelper() {
        return new EventDependentServiceHelper();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.register.WorkflowSettingsActionEventRegister"})
    @Lazy(value=false)
    public WorkflowSettingsActionEventRegister getWorkflowSettingsActionEventRegister() {
        return new WorkflowSettingsActionEventRegister();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.events.executor.util.NotificationSendUtil"})
    public NotificationSendUtil getNotificationSendUtil() {
        return new NotificationSendUtil();
    }
}

