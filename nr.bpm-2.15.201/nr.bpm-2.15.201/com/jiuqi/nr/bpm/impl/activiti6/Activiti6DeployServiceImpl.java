/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.ProcessEngine
 *  org.activiti.engine.RepositoryService
 *  org.activiti.engine.repository.Deployment
 *  org.activiti.engine.repository.DeploymentBuilder
 *  org.activiti.engine.repository.ProcessDefinition
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.impl.activiti6.DeploymentBuilderImpl;
import com.jiuqi.nr.bpm.impl.activiti6.common.ActivitiObjectWrapperUtils;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ExtensionService;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.upload.modeling.CustomProcessBuilder;
import com.jiuqi.nr.bpm.modeling.ProcessBuilder;
import com.jiuqi.nr.bpm.service.DeployService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.util.Assert;

class Activiti6DeployServiceImpl
implements DeployService {
    private final RepositoryService repositoryService;
    private final ExtensionService activitExtensionService;
    private Optional<EventDispatcher> actionEventHandler;

    public Activiti6DeployServiceImpl(ProcessEngine activitiProcessEngine, ExtensionService activitExtensionService) {
        Assert.notNull((Object)activitiProcessEngine, "parameter 'activitiProcessEngine' must not be null.");
        Assert.notNull((Object)activitExtensionService, "parameter 'activitExtensionService' must not be null.");
        this.repositoryService = activitiProcessEngine.getRepositoryService();
        this.activitExtensionService = activitExtensionService;
    }

    @Override
    public RepositoryService getRepositoryService() {
        return this.repositoryService;
    }

    @Override
    public ExtensionService getActivitExtensionService() {
        return this.activitExtensionService;
    }

    @Override
    public com.jiuqi.nr.bpm.common.DeploymentBuilder createDeploymentBuilder() {
        return new DeploymentBuilderImpl(this);
    }

    @Override
    public Optional<EventDispatcher> getActionEventHandler() {
        return this.actionEventHandler;
    }

    public Activiti6DeployServiceImpl setActionEventHandler(EventDispatcher actionEventHandler) {
        this.actionEventHandler = Optional.ofNullable(actionEventHandler);
        return this;
    }

    ProcessDefinition deployProcess(String deploymentName, Map<String, InputStream> resources) {
        DeploymentBuilder activitiBuilder = this.repositoryService.createDeployment();
        activitiBuilder.name(deploymentName);
        for (Map.Entry<String, InputStream> resource : resources.entrySet()) {
            activitiBuilder.addInputStream(resource.getKey(), resource.getValue());
        }
        Deployment deployment = activitiBuilder.deploy();
        return ActivitiObjectWrapperUtils.wrappingProcessDefinition((org.activiti.engine.repository.ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult());
    }

    ProcessDefinition coverProcess(String processDefinitionKey, Map<String, InputStream> resources) {
        return this.coverProcess(processDefinitionKey, resources, null);
    }

    ProcessDefinition coverProcess(String processDefinitionKey, Map<String, InputStream> resources, String oldResourceName) {
        boolean processIsExist = this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).count() > 0L;
        return processIsExist ? this.updateProcess(processDefinitionKey, resources, oldResourceName) : this.deployProcess(null, resources);
    }

    ProcessDefinition updateProcess(String processDefinitionKey, Map<String, InputStream> resources, String oldResourceName) {
        for (Map.Entry<String, InputStream> resource : resources.entrySet()) {
            this.activitExtensionService.updateProcessResourceByKey(processDefinitionKey, resource.getValue(), resource.getKey(), oldResourceName);
        }
        return ActivitiObjectWrapperUtils.wrappingProcessDefinition((org.activiti.engine.repository.ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult());
    }

    @Override
    public InputStream getDeploymentResourceByProcessDefinitionId(String processDefinitionId) {
        return this.repositoryService.getProcessModel(processDefinitionId);
    }

    @Override
    public List<ProcessDefinition> getAllProcessDefinition() {
        return ActivitiObjectWrapperUtils.wrappingProcessDefinitions(this.repositoryService.createProcessDefinitionQuery().list());
    }

    @Override
    public Optional<ProcessDefinition> getProcessDefinitionById(String processDefinitionId) {
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingProcessDefinition((org.activiti.engine.repository.ProcessDefinition)this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult()));
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionByKey(String processDefinitionKey) {
        return ActivitiObjectWrapperUtils.wrappingProcessDefinitions(this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list());
    }

    @Override
    public Optional<UserTask> getUserTask(String processDefinitionId, String userTaskId) {
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingUserTask(this.repositoryService.getBpmnModel(processDefinitionId).getFlowElement(userTaskId)));
    }

    @Override
    public Optional<UserTask> getUserTask(String processDefinitionId, String userTaskId, String formSchemeKey) {
        return this.getUserTask(processDefinitionId, userTaskId);
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId) {
        this.repositoryService.suspendProcessDefinitionById(processDefinitionId);
    }

    @Override
    public void suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances) {
        this.repositoryService.suspendProcessDefinitionById(processDefinitionId, suspendProcessInstances, null);
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId) {
        this.repositoryService.activateProcessDefinitionById(processDefinitionId);
    }

    @Override
    public void activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances) {
        this.repositoryService.activateProcessDefinitionById(processDefinitionId, activateProcessInstances, null);
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        Assert.notNull((Object)deploymentId, "parameter 'deploymentId' must not be null.");
        this.repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public void deleteDeploymentByProcessDefinitionKey(String processDefinitionKey) {
        Assert.notNull((Object)processDefinitionKey, "parameter 'processDefinitionKey' must not be null.");
        this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list().forEach(p -> this.repositoryService.deleteDeployment(p.getDeploymentId(), true));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deployToRunTime(WorkFlowInfoObj desWorkFlowInfoObj) throws Exception {
        WorkFlowDefine workFlowDefine = desWorkFlowInfoObj.getDefine();
        if (workFlowDefine == null) {
            return;
        }
        CustomProcessBuilder processBuilder = new CustomProcessBuilder(workFlowDefine.getCode(), desWorkFlowInfoObj);
        ((ProcessBuilder)processBuilder).build();
        String processDefinitionKey = workFlowDefine.getCode();
        com.jiuqi.nr.bpm.common.DeploymentBuilder deploymentBuilder = this.createDeploymentBuilder();
        String resourceName = String.format("%s.bpmn20.xml", processDefinitionKey);
        String bpmnDocument = processBuilder.getBpmnDocument();
        try (ByteArrayInputStream resource = new ByteArrayInputStream(bpmnDocument.getBytes("UTF-8"));){
            deploymentBuilder.addStreamResource(resourceName, resource, null).coverMode(processDefinitionKey).deploy();
        }
    }
}

