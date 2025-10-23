/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 */
package com.jiuqi.nr.workflow2.schedule.bean.utils;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.schedule.dao.IWFSTriggerPlanDao;
import com.jiuqi.nr.workflow2.schedule.service.IProcessPeriodTriggerService;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupInstanceService;
import com.jiuqi.nr.workflow2.schedule.service.IProcessStartupScheduleManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class ProcessScheduleBeanUtils
implements InitializingBean {
    @Autowired
    private IWFSTriggerPlanDao wfsTriggerPlanDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessStartupInstanceService startupInstanceService;
    @Autowired
    private IProcessStartupScheduleManager processStartupScheduleManager;
    @Autowired
    protected IProcessPeriodTriggerService periodTriggerService;
    public static ProcessScheduleBeanUtils INSTANCE;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    public static IWFSTriggerPlanDao getWfsTriggerPlanDao() {
        return ProcessScheduleBeanUtils.INSTANCE.wfsTriggerPlanDao;
    }

    public static IRunTimeViewController getRunTimeViewController() {
        return ProcessScheduleBeanUtils.INSTANCE.runTimeViewController;
    }

    public static PeriodEngineService getPeriodEngineService() {
        return ProcessScheduleBeanUtils.INSTANCE.periodEngineService;
    }

    public static WorkflowSettingsService getWorkflowSettingsService() {
        return ProcessScheduleBeanUtils.INSTANCE.workflowSettingsService;
    }

    public static IProcessStartupInstanceService getStartupInstanceService() {
        return ProcessScheduleBeanUtils.INSTANCE.startupInstanceService;
    }

    public static IProcessStartupScheduleManager getProcessStartupScheduleManager() {
        return ProcessScheduleBeanUtils.INSTANCE.processStartupScheduleManager;
    }

    public static IProcessPeriodTriggerService getPeriodTriggerService() {
        return ProcessScheduleBeanUtils.INSTANCE.periodTriggerService;
    }
}

