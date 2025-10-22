/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.RepositoryService
 */
package com.jiuqi.nr.bpm.impl.process.service.impl;

import com.jiuqi.nr.bpm.common.DeploymentBuilder;
import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.exception.NotSupportExeception;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ExtensionService;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.service.DeployService;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.activiti.engine.RepositoryService;

public class ProcessDeployServiceImpl
implements DeployService {
    private List<ProcessTaskBuilder> processTaskBuilders;
    private ProcessType processType = ProcessType.DEFAULT;

    public ProcessDeployServiceImpl setProcessTaskBuilders(List<ProcessTaskBuilder> processTaskBuilders) {
        this.processTaskBuilders = processTaskBuilders;
        return this;
    }

    @Override
    public DeploymentBuilder createDeploymentBuilder() {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public InputStream getDeploymentResourceByProcessDefinitionId(String processDefinitionId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<ProcessDefinition> getAllProcessDefinition() {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public Optional<ProcessDefinition> getProcessDefinitionById(String processDefinitionId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionByKey(String processDefinitionKey) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public Optional<UserTask> getUserTask(String processDefinitionId, String userTaskId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public Optional<UserTask> getUserTask(String processDefinitionId, String userTaskId, String formSchemeKey) {
        Optional<ProcessTaskBuilder> taskBuilder = this.processTaskBuilders.stream().filter(e -> e.getProcessType() == this.processType).findFirst();
        if (taskBuilder.isPresent()) {
            return taskBuilder.get().queryUserTask(userTaskId, formSchemeKey);
        }
        return Optional.empty();
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void deleteDeploymentByProcessDefinitionKey(String processDefinitionKey) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public Optional<EventDispatcher> getActionEventHandler() {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public ExtensionService getActivitExtensionService() {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public RepositoryService getRepositoryService() {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void deployToRunTime(WorkFlowInfoObj desWorkFlowInfoObj) throws Exception {
        throw new NotSupportExeception(this.getClass());
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }
}

