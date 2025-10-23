/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 */
package com.jiuqi.nr.workflow2.service.impl;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.result.UnitStateQueryResult;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessQueryService
implements IProcessQueryService {
    @Autowired
    protected WorkflowSettingsService settingService;
    @Autowired
    protected IProcessEngineFactory processEngineFactory;

    @Override
    public IProcessInstance queryInstances(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.queryProcessInstance(businessKey);
    }

    @Override
    public IBizObjectOperateResult<IProcessInstance> queryInstances(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKeyCollection.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.batchQueryProcessInstances(businessKeyCollection);
    }

    @Override
    public List<IProcessTask> queryCurrentTask(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.queryCurrentTask(businessKey, IActor.fromContext());
    }

    @Override
    public List<IProcessOperation> queryProcessOperations(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.queryInstanceHistory(businessKey);
    }

    @Override
    public List<String> queryMatchingActors(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.getMatchingActors(businessKey);
    }

    @Override
    public IProcessStatus queryInstanceState(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.queryStatus(businessKey);
    }

    @Override
    public IProcessStatusWithOperation queryInstanceStateWithOperation(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.queryStatusWithOperation(businessKey);
    }

    @Override
    public IBizObjectOperateResult<IProcessStatus> queryInstanceState(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKeyCollection.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.batchQueryStatus(businessKeyCollection);
    }

    @Override
    public IBizObjectOperateResult<IProcessStatusWithOperation> queryInstanceStateWithOperation(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKeyCollection.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.batchQueryStatusWithOperation(businessKeyCollection);
    }

    @Override
    public IProcessStatus queryUnitState(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        String period = runEnvPara.getPeriod();
        DimensionCombination combination = businessKey.getBusinessObject().getDimensions();
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKey.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(combination);
        Map unitStateMap = processRuntimeService.queryEntityStatus(businessKey.getTask(), period, (DimensionCollection)dimensionCollection);
        return (IProcessStatus)unitStateMap.get(combination);
    }

    @Override
    public IBizObjectOperateResult<IProcessStatus> queryUnitState(IProcessRunPara runEnvPara, IBusinessKeyCollection businessKeyCollection) {
        String period = runEnvPara.getPeriod();
        DimensionCollection dimensionCollection = businessKeyCollection.getBusinessObjects().getDimensions();
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(businessKeyCollection.getTask());
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        Map unitStateMap = processRuntimeService.queryEntityStatus(businessKeyCollection.getTask(), period, dimensionCollection);
        return new UnitStateQueryResult(businessKeyCollection, unitStateMap);
    }
}

