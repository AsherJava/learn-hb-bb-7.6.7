/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.schedule.spring.config;

import com.jiuqi.nr.workflow2.schedule.bean.utils.ProcessScheduleBeanUtils;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.ProcessBiScheduleJobManager;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.ProcessCheckStartupJobFactory;
import com.jiuqi.nr.workflow2.schedule.bi.jobs.ProcessStartupInstancesJobFactory;
import com.jiuqi.nr.workflow2.schedule.dao.IWFSTriggerPlanDao;
import com.jiuqi.nr.workflow2.schedule.dao.impl.WFSTriggerPlanDao;
import com.jiuqi.nr.workflow2.schedule.listener.IWFStartupScheduleListener;
import com.jiuqi.nr.workflow2.schedule.service.IProcessPeriodTriggerService;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupInstanceService;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupScheduleManager;
import com.jiuqi.nr.workflow2.schedule.service.impl.ProcessPeriodTriggerService;
import com.jiuqi.nr.workflow2.schedule.service.impl.ProcessStartupInstanceService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.jiuqi.nr.workflow2.schedule.bean.utils"})
@AutoConfiguration
public class NrWorkflow2ScheduleSpringConfig {
    @Bean
    public ProcessScheduleBeanUtils getProcessScheduleBeanUtils() {
        return new ProcessScheduleBeanUtils();
    }

    @Bean
    public IWFStartupScheduleListener getWorkflowScheduleListener() {
        return new IWFStartupScheduleListener();
    }

    @Bean
    public ProcessStartupInstancesJobFactory getProcessStartInstancesJob() {
        return new ProcessStartupInstancesJobFactory();
    }

    @Bean
    public ProcessCheckStartupJobFactory getProcessCheckStartupJobFactory() {
        return new ProcessCheckStartupJobFactory();
    }

    @Bean
    public IProcessStartupScheduleManager getProcessStartupScheduleManager() {
        return new ProcessBiScheduleJobManager();
    }

    @Bean
    public IWFSTriggerPlanDao getWorkflowStartupPlanDao() {
        return new WFSTriggerPlanDao();
    }

    @Bean
    public IProcessPeriodTriggerService getProcessPeriodTriggerBuilder() {
        return new ProcessPeriodTriggerService();
    }

    @Bean
    public IProcessStartupInstanceService getStartupInstanceService() {
        return new ProcessStartupInstanceService();
    }
}

