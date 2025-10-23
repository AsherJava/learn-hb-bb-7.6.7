/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.spring.config;

import com.jiuqi.nr.workflow2.service.impl.ProcessExecuteService;
import com.jiuqi.nr.workflow2.service.impl.ProcessInstanceService;
import com.jiuqi.nr.workflow2.service.impl.ProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.impl.ProcessQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.workflow2.service.helper"})
@Configuration
public class Workflow2ServiceBeanConfig {
    @Bean(value={"com.jiuqi.nr.workflow2.service.impl.ProcessMetaDataService"})
    public ProcessMetaDataService getProcessMetaDataService() {
        return new ProcessMetaDataService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.service.impl.ProcessExecuteService"})
    public ProcessExecuteService getProcessExecuteService() {
        return new ProcessExecuteService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.service.impl.ProcessQueryService"})
    public ProcessQueryService getProcessQueryService() {
        return new ProcessQueryService();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.service.impl.ProcessInstanceService"})
    public ProcessInstanceService getProcessInstanceService() {
        return new ProcessInstanceService();
    }
}

