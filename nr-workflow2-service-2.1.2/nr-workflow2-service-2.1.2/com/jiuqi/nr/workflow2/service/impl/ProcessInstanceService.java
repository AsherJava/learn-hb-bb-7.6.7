/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 */
package com.jiuqi.nr.workflow2.service.impl;

import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.service.IProcessInstanceService;
import com.jiuqi.nr.workflow2.service.exception.OperateStateCode;
import com.jiuqi.nr.workflow2.service.execute.events.BatchStartProcessInstancePosEvent;
import com.jiuqi.nr.workflow2.service.execute.events.BatchStartProcessInstancePreEvent;
import com.jiuqi.nr.workflow2.service.execute.events.ClearProcessInstancePosEvent;
import com.jiuqi.nr.workflow2.service.execute.events.ClearProcessInstancePreEvent;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class ProcessInstanceService
implements IProcessInstanceService {
    @Autowired
    protected WorkflowSettingsService settingService;
    @Autowired
    protected IProcessEngineFactory processEngineFactory;
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void startInstances(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(runEnvPara.getTaskKey());
        monitor.info("\u6b63\u5728\u53d1\u5e03\u6d41\u7a0b\u5b9e\u4f8b\u542f\u52a8\u300c\u524d\u7f6e\u4e8b\u4ef6\u300d\uff01\uff01", 10.0);
        this.applicationEventPublisher.publishEvent(new BatchStartProcessInstancePreEvent(runEnvPara));
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        monitor.info("\u6b63\u5728\u542f\u52a8\u6d41\u7a0b\u5b9e\u4f8b...");
        IBizObjectOperateResult operateResult = processRuntimeService.batchStartProcessInstance(flowSettings.getWorkflowDefine(), businessKeyCollection);
        this.setOperateResult((IBizObjectOperateResult<Void>)operateResult, businessKeyCollection, operateResultSet);
        monitor.info("\u6b63\u5728\u53d1\u5e03\u6d41\u7a0b\u5b9e\u4f8b\u542f\u52a8\u300c\u540e\u7f6e\u4e8b\u4ef6\u300d\uff01\uff01", 70.0);
        this.applicationEventPublisher.publishEvent(new BatchStartProcessInstancePosEvent(runEnvPara));
        monitor.setJobResult(AsyncJobResult.SUCCESS, OperateStateCode.OPT_SUCCESS.toString());
    }

    @Override
    public void clearInstances(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(runEnvPara.getTaskKey());
        this.applicationEventPublisher.publishEvent(new ClearProcessInstancePreEvent(runEnvPara));
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        IBizObjectOperateResult operateResult = processRuntimeService.batchDeleteProcessInstance(businessKeyCollection);
        this.setOperateResult((IBizObjectOperateResult<Void>)operateResult, businessKeyCollection, operateResultSet);
        this.applicationEventPublisher.publishEvent(new ClearProcessInstancePosEvent(runEnvPara));
        monitor.setJobResult(AsyncJobResult.SUCCESS, OperateStateCode.OPT_SUCCESS.toString());
    }

    @Override
    public void refreshActors(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection, IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(runEnvPara.getTaskKey());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        processRuntimeService.batchRefreshProcessInstance(businessKeyCollection);
    }

    private void setOperateResult(IBizObjectOperateResult<Void> operateResult, IBusinessKeyCollection businessKeyCollection, IOperateResultSet operateResultSet) {
        for (IBusinessObject businessObject : businessKeyCollection.getBusinessObjects()) {
            IOperateResult result = operateResult.getResult((Object)businessObject);
            if (!result.isSuccessful()) {
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, result.getErrorCode().name()));
                continue;
            }
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, ""));
        }
    }
}

