/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 */
package com.jiuqi.nr.workflow2.service.impl;

import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessMetaDataService
implements IProcessMetaDataService {
    @Autowired
    private WorkflowSettingsService settingService;
    @Autowired
    private IProcessEngineFactory processEngineFactory;

    @Override
    public IProcessDefinition getProcessDefinition(String taskKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        return definitionService.queryProcess(flowSettings.getWorkflowDefine());
    }

    @Override
    public IUserTask getUserTask(String taskKey, String userTaskCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        String workflowDefineId = flowSettings.getWorkflowDefine();
        return definitionService.queryUserTask(workflowDefineId, userTaskCode);
    }

    @Override
    public IUserTask getUserTaskOrDefault(String taskKey, String userTaskCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        String workflowDefineId = flowSettings.getWorkflowDefine();
        return definitionService.getUserTaskOrDefault(workflowDefineId, userTaskCode);
    }

    @Override
    public List<IUserTask> queryAllUserTasks(String taskKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        return definitionService.queryAllUserTasks(flowSettings.getWorkflowDefine());
    }

    @Override
    public IUserAction queryAction(String taskKey, String userTaskCode, String actionCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        String workflowDefineId = flowSettings.getWorkflowDefine();
        return definitionService.queryAction(workflowDefineId, userTaskCode, actionCode);
    }

    @Override
    public IUserAction getActionOrDefault(String taskKey, String userTaskCode, String actionCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        String workflowDefineId = flowSettings.getWorkflowDefine();
        return definitionService.getActionOrDefault(workflowDefineId, userTaskCode, actionCode);
    }

    @Override
    public List<IUserAction> queryAllActions(String taskKey, String userTaskCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        return definitionService.queryAllActions(flowSettings.getWorkflowDefine(), userTaskCode);
    }

    @Override
    public List<IProcessStatus> queryAllStatus(String taskKey) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        return definitionService.queryAllStatus(flowSettings.getWorkflowDefine());
    }

    @Override
    public List<IProcessStatus> queryUserTaskStatus(String taskKey, String userTaskCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        return definitionService.queryUserTaskStatus(flowSettings.getWorkflowDefine(), userTaskCode);
    }

    @Override
    public IProcessStatus queryStatus(String taskKey, String statusCode) {
        WorkflowSettingsDO flowSettings = this.settingService.queryWorkflowSettings(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(flowSettings.getWorkflowEngine());
        IProcessDefinitionService definitionService = processEngine.getProcessDefinitionService();
        return definitionService.queryStatus(flowSettings.getWorkflowDefine(), statusCode);
    }
}

