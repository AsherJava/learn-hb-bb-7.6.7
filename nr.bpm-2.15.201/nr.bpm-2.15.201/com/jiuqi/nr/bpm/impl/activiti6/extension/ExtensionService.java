/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.bpmn.model.FlowNode
 *  org.activiti.bpmn.model.Process
 *  org.activiti.bpmn.model.SequenceFlow
 *  org.activiti.engine.ManagementService
 *  org.activiti.engine.ProcessEngine
 *  org.activiti.engine.RepositoryService
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.repository.ProcessDefinition
 *  org.activiti.engine.task.Task
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.activiti6.extension.DeleteTaskCmd;
import com.jiuqi.nr.bpm.impl.activiti6.extension.SetFLowNodeAndGoCmd;
import com.jiuqi.nr.bpm.impl.activiti6.extension.UpdateResourceCmd;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class ExtensionService {
    private final RepositoryService repositoryService;
    private final ManagementService managementService;

    public ExtensionService(ProcessEngine activitiProcessEngine) {
        Assert.notNull((Object)activitiProcessEngine, "parameter 'activitiProcessEngine' must not be null.");
        this.repositoryService = activitiProcessEngine.getRepositoryService();
        this.managementService = activitiProcessEngine.getManagementService();
    }

    public ManagementService getManagementService() {
        return this.managementService;
    }

    public boolean getRetrievableUserTask(Task currentTask, String userTaskId) {
        Process process = this.repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();
        FlowNode currentNode = (FlowNode)process.getFlowElement(currentTask.getTaskDefinitionKey());
        List incoming = currentNode.getIncomingFlows();
        for (SequenceFlow sequenceFlow : incoming) {
            FlowNode targetNode = (FlowNode)sequenceFlow.getSourceFlowElement();
            if (!targetNode.getId().equals(userTaskId)) continue;
            return true;
        }
        return false;
    }

    public String getRetrievableUserTask(Task currentTask, Integer level) {
        Process process = this.repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();
        FlowNode currentNode = (FlowNode)process.getFlowElement(currentTask.getTaskDefinitionKey());
        return this.getTargetNode(currentNode, level);
    }

    private String getTargetNode(FlowNode currentNode, Integer level) {
        List incoming = currentNode.getIncomingFlows();
        if (!incoming.isEmpty()) {
            FlowNode targetNode = (FlowNode)((SequenceFlow)incoming.get(0)).getSourceFlowElement();
            if (level == 0) {
                return targetNode.getId();
            }
            Integer n = level;
            Integer n2 = level = Integer.valueOf(level - 1);
            return this.getTargetNode(targetNode, level);
        }
        return null;
    }

    public String getRetrievableUserTask(Task currentTask) {
        ArrayList<SequenceFlow> incomFlowLines = new ArrayList<SequenceFlow>();
        Process process = this.repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();
        FlowNode currentNode = (FlowNode)process.getFlowElement(currentTask.getTaskDefinitionKey());
        FlowNode targetNode = (FlowNode)((SequenceFlow)currentNode.getIncomingFlows().get(0)).getSourceFlowElement();
        if (currentNode.getIncomingFlows().size() > 1) {
            for (SequenceFlow sequenceFlow : currentNode.getIncomingFlows()) {
                String sourceRef = sequenceFlow.getSourceRef();
                if (!sourceRef.equals(targetNode.getId())) continue;
                incomFlowLines.add(sequenceFlow);
            }
            return incomFlowLines.size() > 1 ? null : targetNode.getId();
        }
        if (targetNode.getIncomingFlows().size() > 1) {
            return targetNode.getId();
        }
        if (targetNode.getOutgoingFlows().size() > 1) {
            incomFlowLines.clear();
            for (SequenceFlow sequenceFlow : targetNode.getOutgoingFlows()) {
                String targetRef = sequenceFlow.getTargetRef();
                if (targetRef.equals(currentNode.getId())) continue;
                incomFlowLines.add(sequenceFlow);
            }
            return incomFlowLines.size() > 1 ? null : targetNode.getId();
        }
        return targetNode.getId();
    }

    @Transactional
    public void jumpTo(String processDefinitionId, String currentTaskId, String jumpToTaskDefinitionId) {
        Process process = this.repositoryService.getBpmnModel(processDefinitionId).getMainProcess();
        FlowNode targetNode = (FlowNode)process.getFlowElement(jumpToTaskDefinitionId);
        String executionEntityId = (String)this.managementService.executeCommand((Command)new DeleteTaskCmd(currentTaskId));
        this.managementService.executeCommand((Command)new SetFLowNodeAndGoCmd(targetNode, executionEntityId));
    }

    @Transactional
    public void updateProcessResourceByKey(String processDefinitionKey, InputStream resource, String resourceName, String oldResourceName) {
        byte[] resourceByteArray;
        List processDefinitions = this.repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list();
        try {
            resourceByteArray = this.readFromStream(resource);
        }
        catch (IOException e) {
            throw new BpmException("update process resource error.", e);
        }
        for (ProcessDefinition processDefinition : processDefinitions) {
            UpdateResourceCmd updateResourceCmd = new UpdateResourceCmd(processDefinition, resourceByteArray, resourceName, oldResourceName);
            this.managementService.executeCommand((Command)updateResourceCmd);
        }
    }

    private byte[] readFromStream(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = inputStream.read(buffer, 0, buf_size))) {
                outputStream.write(buffer, 0, len);
            }
            byte[] byArray = outputStream.toByteArray();
            return byArray;
        }
    }
}

