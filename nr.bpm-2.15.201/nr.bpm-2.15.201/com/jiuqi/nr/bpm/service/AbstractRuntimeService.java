/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.ProcessProvider;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.exception.NotSupportExeception;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.impl.process.util.ProcessUtil;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractRuntimeService
implements RunTimeService {
    public ProcessType processType = ProcessType.DEFAULT;
    protected ActorStrategyProvider actorStrategyProvider;
    protected Optional<EventDispatcher> actionEventHandler;
    protected NrParameterUtils nrParameterUtils;
    protected ProcessUtil processUtil;
    protected List<ProcessTaskBuilder> processTaskBuilders;
    protected ProcessStateHistoryDao processStateHistoryDao;
    public DeployService deployService;
    protected List<IConditionalExecute> conditionalExecutes;
    protected List<ProcessProvider> processProviders;

    public AbstractRuntimeService setActorStrategyProvider(ActorStrategyProvider actorStrategyProvider) {
        this.actorStrategyProvider = actorStrategyProvider;
        return this;
    }

    public AbstractRuntimeService setUserActionEventHandler(Optional<EventDispatcher> actionEventHandler) {
        this.actionEventHandler = actionEventHandler;
        return this;
    }

    public AbstractRuntimeService setProcessProviders(List<ProcessProvider> processProviders) {
        this.processProviders = processProviders;
        if (this.processProviders == null) {
            this.processProviders = Collections.emptyList();
        }
        return this;
    }

    public AbstractRuntimeService setNrParameterUtils(NrParameterUtils nrParameterUtils) {
        this.nrParameterUtils = nrParameterUtils;
        return this;
    }

    public AbstractRuntimeService setConditionalExecute(List<IConditionalExecute> conditionalExecutes) {
        this.conditionalExecutes = conditionalExecutes;
        return this;
    }

    public AbstractRuntimeService setProcessUtil(ProcessUtil processUtil) {
        this.processUtil = processUtil;
        return this;
    }

    public AbstractRuntimeService setProcessTaskBuilder(List<ProcessTaskBuilder> processTaskBuilder) {
        if (processTaskBuilder == null) {
            this.processTaskBuilders = Collections.emptyList();
        }
        this.processTaskBuilders = processTaskBuilder;
        return this;
    }

    public AbstractRuntimeService setProcessStateHistoryDao(ProcessStateHistoryDao processStateHistoryDao) {
        this.processStateHistoryDao = processStateHistoryDao;
        return this;
    }

    @Override
    public List<Task> queryTasks(Actor candicateActor) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<Task> queryTasks(Actor candicateActor, int pageIndex, int pageSize) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<Task> queryTasks(String instanceId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<Task> queryTaskByProcessInstance(String instanceId, Actor candicateActor) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public Optional<Task> getTaskById(String taskId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<ProcessInstance> queryInstanceByProcessDefinitionKey(String processDefintionKey) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<ProcessInstance> queryInstanceByProcessDefinitionId(String processDefintionId, int pageIndex, int pageSize) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public long startProcess(String processRunningConfigId, String period) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void suspendProcessInstanceById(String instanceId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void activateProcessInstanceById(String instanceId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public long suspendProcessInstanceByRunningConfig(UUID runningConfigId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public long activateProcessInstanceByRunningConfig(UUID runningConfigId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public long deleteProcessInstanceByRunningConfig(UUID runningConfigId) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void completeTask(String taskId, String userId, String actionId, String comment) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void completeTask(String taskId, String userId, String actionId, String comment, TaskContext taskContext) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public void completeTask(String taskId, String userId, String actionId, String comment, TaskContext context, Map<String, Object> variables) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public List<UserTask> getRetrievableTask(String instanceId, Actor actor) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public boolean isTaskActorByIdentityLink(Task task, Actor candicateActor) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public String getAutoStartProcessKey(String businessKey) {
        throw new NotSupportExeception(this.getClass());
    }

    @Override
    public Optional<UserTask> getTargetTaskById(Task currTask, String taskId) {
        throw new NotSupportExeception(this.getClass());
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }
}

