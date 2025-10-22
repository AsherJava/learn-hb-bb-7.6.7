/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.HistoryService
 *  org.activiti.engine.ManagementService
 *  org.activiti.engine.ProcessEngine
 *  org.activiti.engine.TaskService
 *  org.activiti.engine.history.HistoricActivityInstance
 *  org.activiti.engine.history.HistoricActivityInstanceQuery
 *  org.activiti.engine.history.HistoricProcessInstance
 *  org.activiti.engine.history.HistoricProcessInstanceQuery
 *  org.activiti.engine.history.HistoricTaskInstance
 *  org.activiti.engine.history.HistoricTaskInstanceQuery
 *  org.activiti.engine.history.HistoricVariableInstanceQuery
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.persistence.entity.Entity
 *  org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl
 *  org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity
 *  org.activiti.engine.task.TaskQuery
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.common.ProcessActivity;
import com.jiuqi.nr.bpm.common.TaskComment;
import com.jiuqi.nr.bpm.impl.activiti6.common.ActivitiObjectWrapperUtils;
import com.jiuqi.nr.bpm.impl.activiti6.extension.HistoricProcessInstanceCmd;
import com.jiuqi.nr.bpm.impl.activiti6.extension.HistoricVariableInstanceEntityCmd;
import com.jiuqi.nr.bpm.service.HistoryService;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.task.TaskQuery;
import org.springframework.util.Assert;

class Activiti6HistoryServiceImpl
implements HistoryService {
    private org.activiti.engine.HistoryService activitiHistoryService;
    private TaskService activitiTaskService;
    private ManagementService managementService;

    public Activiti6HistoryServiceImpl(ProcessEngine activitiProcessEngine) {
        Assert.notNull((Object)activitiProcessEngine, "parameter 'activitiProcessEngine' must not be null.");
        this.activitiHistoryService = activitiProcessEngine.getHistoryService();
        this.activitiTaskService = activitiProcessEngine.getTaskService();
        this.managementService = activitiProcessEngine.getManagementService();
    }

    @Override
    public List<TaskComment> queryCommentByInstanceId(String instanceId) {
        Assert.notNull((Object)instanceId, "'instanceId' must not be null.");
        return ActivitiObjectWrapperUtils.wrappingTaskComments(this.activitiTaskService.getProcessInstanceComments(instanceId));
    }

    @Override
    public List<TaskComment> queryCommentByTaskId(String taskId) {
        Assert.notNull((Object)taskId, "'taskId' must not be null.");
        return ActivitiObjectWrapperUtils.wrappingTaskComments(this.activitiTaskService.getTaskComments(taskId));
    }

    @Override
    public List<ProcessActivity> queryActivityByInstanceId(String instanceId, boolean finishedOnly) {
        Assert.notNull((Object)instanceId, "'instanceId' must not be null.");
        List<ProcessActivity> activities = ActivitiObjectWrapperUtils.wrappingProcessActivitysByHistoricTask(((HistoricTaskInstanceQuery)((HistoricTaskInstanceQuery)this.activitiHistoryService.createHistoricTaskInstanceQuery().processInstanceId(instanceId)).orderByHistoricTaskInstanceStartTime().asc()).list());
        if (!finishedOnly) {
            activities.addAll(ActivitiObjectWrapperUtils.wrappingProcessActivitysByTask(((TaskQuery)((TaskQuery)((TaskQuery)this.activitiTaskService.createTaskQuery().processInstanceId(instanceId)).orderByTaskCreateTime()).asc()).list()));
        }
        return activities;
    }

    @Override
    public boolean hasHistoricProcessInstance(String businessKey, String processDefKey) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = this.activitiHistoryService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(processDefKey);
        List historicProcessInstances = historicProcessInstanceQuery.list();
        ArrayList<HistoricProcessInstance> result = new ArrayList<HistoricProcessInstance>();
        for (HistoricProcessInstance hisInstance : historicProcessInstances) {
            Entity his = (Entity)hisInstance;
            if (his.isDeleted()) continue;
            result.add(hisInstance);
        }
        return result.size() > 0;
    }

    @Override
    public void deleteHistoricProcessInstance(String businessKey, String processDefKey) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = this.activitiHistoryService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(processDefKey);
        List historicProcessInstances = historicProcessInstanceQuery.list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            this.activitiHistoryService.deleteHistoricProcessInstance(historicProcessInstance.getId());
        }
    }

    @Override
    public List<HistoricProcessInstance> queryHistoricProcessInstance(String processInstanceId) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = this.activitiHistoryService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId);
        List historicProcessInstances = historicProcessInstanceQuery.list();
        return historicProcessInstances;
    }

    @Override
    public List<HistoricActivityInstance> queryHistoricActivityInstance(String processInstanceId) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = this.activitiHistoryService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId);
        List historicActivityInstances = historicActivityInstanceQuery.list();
        return historicActivityInstances;
    }

    @Override
    public List<HistoricTaskInstance> queryHistoricTaskInstance(String processInstanceId) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = (HistoricTaskInstanceQuery)this.activitiHistoryService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId);
        List historicTaskInstances = historicTaskInstanceQuery.list();
        return historicTaskInstances;
    }

    @Override
    public List<HistoricVariableInstanceEntity> queryHistoricVariableInstance(String processInstanceId) {
        HistoricVariableInstanceQuery historicVariableInstanceQuery = this.activitiHistoryService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId);
        List historicVariableInstances = historicVariableInstanceQuery.list();
        return (List)this.managementService.executeCommand((Command)new HistoricVariableInstanceEntityCmd(historicVariableInstances));
    }

    @Override
    public List<HistoricIdentityLinkEntityImpl> queryHistoricIdentityLinkInstance(String processInstanceId) {
        List historicIdentityLinks = (List)this.managementService.executeCommand((Command)new HistoricProcessInstanceCmd(processInstanceId));
        return historicIdentityLinks;
    }

    @Override
    public void deleteHistoricProcessInstance(String processInstanceId) {
        this.activitiHistoryService.deleteHistoricProcessInstance(processInstanceId);
    }

    @Override
    public List<HistoricProcessInstance> queryHistoricProcessInstanceByBusinessKey(String businessKey) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = this.activitiHistoryService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey);
        List historicProcessInstances = historicProcessInstanceQuery.list();
        return historicProcessInstances;
    }
}

