/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RunTimeService {
    public List<Task> queryTasks(Actor var1);

    public List<Task> queryTasks(Actor var1, int var2, int var3);

    public List<Task> queryTasks(String var1);

    public List<Task> queryTaskByProcessInstance(String var1, Actor var2);

    public List<Task> queryTaskByBusinessKey(String var1);

    default public List<Task> queryTaskByBusinessKey(String businessKey, Actor candicateActor, boolean startIfInstanceNotExist, IConditionCache conditionCache) {
        return null;
    }

    public List<Task> queryTasks(String var1, boolean var2, Actor var3, boolean var4);

    public List<Task> queryTasks(String var1, boolean var2, Actor var3, boolean var4, String var5);

    public List<Task> queryTaskByBusinessKey(String var1, boolean var2);

    public List<Task> queryTaskByBusinessKey(String var1, Actor var2);

    public List<Task> queryTaskByBusinessKey(String var1, Actor var2, boolean var3);

    @Deprecated
    public Optional<Task> getTaskById(String var1);

    public Optional<Task> getTaskById(String var1, BusinessKey var2);

    public List<ProcessInstance> queryInstanceByProcessDefinitionKey(String var1);

    public List<ProcessInstance> queryInstanceByProcessDefinitionId(String var1, int var2, int var3);

    public Optional<ProcessInstance> queryInstanceByBusinessKey(String var1);

    public Optional<ProcessInstance> queryInstanceByBusinessKey(String var1, boolean var2);

    public Optional<ProcessInstance> queryInstanceByBusinessKey(String var1, boolean var2, String var3);

    public Optional<ProcessInstance> getInstanceById(String var1);

    public Optional<String> getBusinessKey(String var1);

    public long startProcess(String var1, String var2);

    public ProcessInstance startProcessByProcessDefinitionId(String var1, String var2, String var3);

    public ProcessInstance startProcessByProcessDefinitionId(String var1, String var2, String var3, Map<String, Object> var4);

    public ProcessInstance startProcessByProcessDefinitionKey(String var1, String var2, String var3);

    public ProcessInstance startProcessByProcessDefinitionKey(String var1, String var2, String var3, Map<String, Object> var4);

    public long suspendProcessInstanceByRunningConfig(UUID var1);

    public void suspendProcessInstanceById(String var1);

    public long activateProcessInstanceByRunningConfig(UUID var1);

    public void activateProcessInstanceById(String var1);

    public long deleteProcessInstanceByRunningConfig(UUID var1);

    public void deleteProcessInstanceById(String var1);

    @Deprecated
    public void completeTask(String var1, String var2, String var3, String var4);

    @Deprecated
    public void completeTask(String var1, String var2, String var3, String var4, TaskContext var5);

    @Deprecated
    public void completeTask(String var1, String var2, String var3, String var4, TaskContext var5, Map<String, Object> var6);

    @Deprecated
    public List<UserTask> getRetrievableTask(String var1, Actor var2);

    public UserTask getRetrievableTask(String var1, Actor var2, String var3);

    @Deprecated
    public Task retrieveTask(String var1, String var2, Actor var3);

    public void retrieveTask(Task var1, UserTask var2, String var3, BusinessKey var4);

    public void retrieveTask(Task var1, UserTask var2, String var3, BusinessKey var4, TaskContext var5);

    public void batchCompleteTasks(BusinessKeySet var1, Actor var2, String var3, String var4, TaskContext var5, boolean var6, Map<String, Object> var7);

    public boolean isTaskActorByIdentityLink(Task var1, Actor var2);

    public boolean isTaskActorAllTrue(Task var1, BusinessKey var2, Actor var3);

    public String getAutoStartProcessKey(String var1);

    public boolean isTaskActor(UserTask var1, BusinessKey var2, Task var3);

    @Deprecated
    public Optional<UserTask> getTargetTaskById(Task var1, String var2);

    public Optional<UserTask> getTargetTaskById(Task var1, String var2, BusinessKey var3);

    public Set<BusinessKey> batchStartProcessByBusinessKey(Map<BusinessKey, String> var1, Map<String, Object> var2);

    public void completeProcessTask(BusinessKey var1, String var2, String var3, String var4, String var5, TaskContext var6);

    public void completeProcessTask(BusinessKey var1, String var2, String var3, String var4, String var5, TaskContext var6, Map<String, Object> var7);

    public void batchCompleteProcessTasks(BusinessKeySet var1, Actor var2, String var3, String var4, String var5, TaskContext var6);

    public void batchCompleteProcessTasks(BusinessKeySet var1, Actor var2, String var3, String var4, String var5, TaskContext var6, Map<String, Object> var7);

    public List<ProcessInstance> queryInstanceByFormSchemeKey(String var1, String var2);

    public boolean hasVariable(String var1, String var2);

    public void removeVariable(String var1, String var2);

    public Map<Task, String> querySignTaskByProcessInstance(String var1, Actor var2);

    public void jumpToTargetNode(String var1, String var2, String var3);
}

