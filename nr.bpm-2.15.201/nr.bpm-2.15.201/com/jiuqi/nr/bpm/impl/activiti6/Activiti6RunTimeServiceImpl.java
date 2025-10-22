/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.sql.exception.NotImplementedException
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  org.activiti.engine.ProcessEngine
 *  org.activiti.engine.RuntimeService
 *  org.activiti.engine.TaskService
 *  org.activiti.engine.impl.identity.Authentication
 *  org.activiti.engine.runtime.ProcessInstance
 *  org.activiti.engine.task.IdentityLink
 *  org.activiti.engine.task.Task
 *  org.activiti.engine.task.TaskQuery
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.sql.exception.NotImplementedException;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.ProcessProvider;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.SimpleMessageThreadLocalStrategy;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.exception.ProcessInstanceNotFound;
import com.jiuqi.nr.bpm.exception.ProcessInstanceStateError;
import com.jiuqi.nr.bpm.exception.TaskNotFound;
import com.jiuqi.nr.bpm.exception.TaskSuspendedError;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.exception.UserTaskNotFound;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.activiti6.common.ActivitiObjectWrapperUtils;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ExtensionService;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import com.jiuqi.nr.bpm.impl.countersign.group.IQueryGroupCount;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.event.UserActionCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionPrepareEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionProgressEventImpl;
import com.jiuqi.nr.bpm.impl.process.ProcessTask;
import com.jiuqi.nr.bpm.impl.process.consts.TaskEnum;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.service.AbstractRuntimeService;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.TaskQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class Activiti6RunTimeServiceImpl
extends AbstractRuntimeService {
    private final RuntimeService activitiRuntimeService;
    private final TaskService activitiTaskService;
    private final ExtensionService activitExtensionService;

    public Activiti6RunTimeServiceImpl(ProcessEngine activitiProcessEngine, DeployService deployService, ExtensionService activitExtensionService) {
        Assert.notNull((Object)activitiProcessEngine, "parameter 'activitiProcessEngine' must not be null.");
        this.deployService = deployService;
        this.activitiRuntimeService = activitiProcessEngine.getRuntimeService();
        this.activitiTaskService = activitiProcessEngine.getTaskService();
        this.activitExtensionService = activitExtensionService;
    }

    @Override
    public List<Task> queryTasks(Actor candicateActor) {
        Assert.notNull((Object)candicateActor, "parameter 'candicateActor' must not be null.");
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().active().taskCandidateUser("8a80cb816d6bcf57016d6bfa1bcf0001")).list();
        if (candicateActor.getIdentityId() != null) {
            tasks.addAll(((TaskQuery)this.activitiTaskService.createTaskQuery().active().taskCandidateUser(candicateActor.getIdentityId().toString())).list());
        }
        tasks.sort((t1, t2) -> t1.getCreateTime().compareTo(t2.getCreateTime()));
        return this.distinctTask(ActivitiObjectWrapperUtils.wrappingTasks(tasks, this.activitiTaskService));
    }

    @Override
    public List<Task> queryTasks(Actor candicateActor, int pageIndex, int pageSize) {
        Assert.notNull((Object)candicateActor, "parameter 'candicateActor' must not be null.");
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().active().taskCandidateUser(candicateActor.getUserId().toString())).list();
        if (candicateActor.getIdentityId() != null) {
            tasks.addAll(((TaskQuery)this.activitiTaskService.createTaskQuery().active().taskCandidateUser(candicateActor.getIdentityId().toString())).list());
        }
        tasks.sort((t1, t2) -> t1.getCreateTime().compareTo(t2.getCreateTime()));
        return this.distinctTask(ActivitiObjectWrapperUtils.wrappingTasks(tasks.subList((pageIndex - 1) * pageSize, pageIndex * pageSize)));
    }

    @Override
    public List<Task> queryTasks(String instanceId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().active().processInstanceId(instanceId)).list();
        return this.distinctTask(ActivitiObjectWrapperUtils.wrappingTasks(tasks, this.activitiTaskService));
    }

    @Override
    public List<Task> queryTaskByProcessInstance(String instanceId, Actor candicateActor) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        ArrayList<Task> result = new ArrayList<Task>();
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().active().processInstanceId(instanceId)).list();
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        org.activiti.engine.runtime.ProcessInstance instance = (org.activiti.engine.runtime.ProcessInstance)this.activitiRuntimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (instance == null) {
            throw new ProcessInstanceNotFound(instanceId);
        }
        HashMap<String, String> taskToMap = new HashMap<String, String>();
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(instance.getBusinessKey());
        for (org.activiti.engine.task.Task task : tasks) {
            Optional<UserTask> userTask = this.deployService.getUserTask(instance.getProcessDefinitionId(), task.getTaskDefinitionKey(), businessKey.getFormSchemeKey());
            if (!userTask.isPresent()) continue;
            boolean isCounterTask = this.nrParameterUtils.isMulitiInstanceTask(task.getTaskDefinitionKey(), businessKey.getFormSchemeKey());
            boolean signNodeByRoleType = this.nrParameterUtils.isSignNodeByRoleType(task.getTaskDefinitionKey(), businessKey.getFormSchemeKey());
            boolean signStartMode = this.nrParameterUtils.isSignStartMode(userTask.get().getId(), businessKey.getFormSchemeKey());
            if (candicateActor != null) {
                if (isCounterTask) {
                    String userId;
                    Object variable;
                    if (signNodeByRoleType) {
                        String roleKey = null;
                        variable = this.activitiRuntimeService.getVariable(task.getExecutionId(), task.getId());
                        if (variable == null) continue;
                        roleKey = variable.toString();
                        IQueryGroupCount queryGroupCount = this.nrParameterUtils.getQueryGroupCount(signNodeByRoleType);
                        boolean groupActor = queryGroupCount.isGroupActor(userTask.get(), businessKey, candicateActor, this.actorStrategyProvider, ActivitiObjectWrapperUtils.wrappingTask(task), roleKey);
                        if (groupActor) {
                            result.add(ActivitiObjectWrapperUtils.wrappingTask(task, this.activitiTaskService));
                        }
                        if (!signStartMode) continue;
                        taskToMap.put(task.getId(), roleKey);
                        continue;
                    }
                    String userKey = null;
                    variable = this.activitiRuntimeService.getVariable(task.getExecutionId(), task.getId());
                    if (variable == null) continue;
                    userKey = variable.toString();
                    if (userKey.equals(userId = candicateActor.getIdentityId())) {
                        result.add(ActivitiObjectWrapperUtils.wrappingTask(task, this.activitiTaskService));
                    }
                    taskToMap.put(task.getId(), userKey);
                    continue;
                }
                if (!ActorUtils.isTaskActor(userTask.get(), businessKey, candicateActor, this.actorStrategyProvider, ActivitiObjectWrapperUtils.wrappingTask(task))) continue;
                result.add(ActivitiObjectWrapperUtils.wrappingTask(task, this.activitiTaskService));
                continue;
            }
            result.add(ActivitiObjectWrapperUtils.wrappingTask(task, this.activitiTaskService));
        }
        return this.distinctTask(result, taskToMap);
    }

    private List<Task> distinctTask(List<Task> tasks) {
        return tasks.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<Task>(Comparator.comparing(e -> e.getUserTaskId()))), ArrayList::new));
    }

    private List<Task> distinctTask(List<Task> tasks, Map<String, String> taskToMap) {
        List taskTemp = tasks.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<Task>(Comparator.comparing(e -> e.getUserTaskId()))), ArrayList::new));
        if (taskTemp != null && taskTemp.size() > 0) {
            for (Task task : taskTemp) {
                if (taskToMap == null || taskToMap.size() <= 0) continue;
                String roleKey = taskToMap.get(task.getId());
                NpContext context = NpContextHolder.getContext();
                ContextExtension extension = context.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_NODE_TO_ROLE);
                extension.put(CounterSignConst.NR_WORKFLOW_SIGN_NODE_TO_ROLE_VALUE, (Serializable)((Object)roleKey));
            }
        }
        return taskTemp;
    }

    @Override
    public boolean isTaskActorAllTrue(Task task, BusinessKey businessKey, Actor actor) {
        Optional<UserTask> userTask = this.deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), businessKey.getFormSchemeKey());
        if (!userTask.isPresent()) {
            return false;
        }
        for (ActorStrategyInstance actorStrategyInstance : userTask.get().getActorStrategies()) {
            ActorStrategy<?> actorStrategy = this.actorStrategyProvider.getActorStrategyByType(actorStrategyInstance.getType());
            if (actorStrategy.isUserMatch((BusinessKeyInfo)businessKey, actorStrategyInstance.getParameterJson(), actor, task)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isTaskActorByIdentityLink(Task task, Actor candicateActor) {
        List links = this.activitiTaskService.getIdentityLinksForTask(task.getId());
        for (IdentityLink identityLink : links) {
            if (identityLink.getUserId() == null || !(candicateActor.getIdentityId() != null ? identityLink.getUserId().equals(candicateActor.getUserId().toString()) || identityLink.getUserId().equals(candicateActor.getIdentityId().toString()) : identityLink.getUserId().equals(candicateActor.getUserId().toString()))) continue;
            return true;
        }
        return false;
    }

    @Override
    public Optional<Task> getTaskById(String taskId) {
        Assert.notNull((Object)taskId, "parameter 'taskId' must not be null.");
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingTask((org.activiti.engine.task.Task)((TaskQuery)this.activitiTaskService.createTaskQuery().taskId(taskId)).singleResult()));
    }

    @Override
    public Optional<Task> getTaskById(String taskId, BusinessKey businessKey) {
        Assert.notNull((Object)taskId, "parameter 'taskId' must not be null.");
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingTask((org.activiti.engine.task.Task)((TaskQuery)this.activitiTaskService.createTaskQuery().taskId(taskId)).singleResult()));
    }

    @Override
    public List<ProcessInstance> queryInstanceByProcessDefinitionKey(String processDefinitionKey) {
        Assert.notNull((Object)processDefinitionKey, "parameter 'processDefinitionKey' must not be null.");
        return ActivitiObjectWrapperUtils.wrappingProcessInstances(this.activitiRuntimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list());
    }

    @Override
    public List<ProcessInstance> queryInstanceByProcessDefinitionId(String processDefintionId, int pageIndex, int pageSize) {
        Assert.notNull((Object)processDefintionId, "parameter 'processDefintionId' must not be null.");
        return ActivitiObjectWrapperUtils.wrappingProcessInstances(this.activitiRuntimeService.createProcessInstanceQuery().processDefinitionId(processDefintionId).listPage((pageIndex - 1) * pageSize, pageSize));
    }

    @Override
    public Optional<ProcessInstance> getInstanceById(String instanceId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingProcessInstance((org.activiti.engine.runtime.ProcessInstance)this.activitiRuntimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult()));
    }

    @Override
    public Optional<String> getBusinessKey(String instanceId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        org.activiti.engine.runtime.ProcessInstance instance = (org.activiti.engine.runtime.ProcessInstance)this.activitiRuntimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (instance == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(instance.getBusinessKey().toString());
    }

    @Override
    @Transactional
    public long startProcess(String processRunningConfigId, String period) {
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public ProcessInstance startProcessByProcessDefinitionId(String businessKey, String processDefinitionId, String startUserId) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        Assert.notNull((Object)processDefinitionId, "parameter 'processDefinitionId' must not be null.");
        HashMap<String, Object> variables = new HashMap<String, Object>();
        return ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceById(processDefinitionId, businessKey, this.generateInstanceVariables(businessKey, variables)));
    }

    @Override
    public ProcessInstance startProcessByProcessDefinitionKey(String businessKey, String processDefinitionKey, String startUserId) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        Assert.notNull((Object)processDefinitionKey, "parameter 'processDefinitionKey' must not be null.");
        try {
            HashMap<String, Object> variables = new HashMap<String, Object>();
            return ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, this.generateInstanceVariables(businessKey, variables)));
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Set<BusinessKey> batchStartProcessByBusinessKey(Map<BusinessKey, String> startParam, Map<String, Object> variables) {
        HashSet<BusinessKey> success = new HashSet<BusinessKey>();
        if (startParam.isEmpty()) {
            return new HashSet<BusinessKey>();
        }
        BusinessKey businessKey = startParam.keySet().iterator().next();
        String formSchemeKey = businessKey.getFormSchemeKey();
        SimpleMessageThreadLocalStrategy.setSumTotal(startParam.size());
        SimpleMessageThreadLocalStrategy.setMessageEventListener(this.nrParameterUtils.getMessageEventListener());
        SimpleMessageThreadLocalStrategy.setUpperLimitValue(this.nrParameterUtils.getMaxValue());
        boolean counterSign = false;
        if (startParam != null && startParam.size() > 0) {
            BusinessKey next = startParam.keySet().iterator().next();
            counterSign = this.isCounterSign(next);
        }
        for (Map.Entry<BusinessKey, String> entry : startParam.entrySet()) {
            if (counterSign) {
                ProcessTask processTask = new ProcessTask(TaskEnum.TASK_START, entry.getKey());
                Map<String, Object> counterParam = this.nrParameterUtils.buildCounterParam(entry.getKey(), processTask, "start");
                variables.putAll(counterParam);
            }
            try {
                String businessKeyStr = BusinessKeyFormatter.formatToString(entry.getKey());
                ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceByKey(entry.getValue(), businessKeyStr, this.generateInstanceVariables(businessKeyStr, variables)));
            }
            catch (Exception e) {
                continue;
            }
            success.add(entry.getKey());
        }
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(formSchemeKey);
        if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(success, workflowStartType, null);
        }
        SimpleMessageThreadLocalStrategy.clear();
        return success;
    }

    Map<String, Object> generateInstanceVariables(String businessKey, Map<String, Object> variables) {
        variables.put("businessKey", businessKey);
        List<WorkFlowLine> workLine = this.nrParameterUtils.queryWorkFlowLines(businessKey);
        if (workLine.size() < 2) {
            this.generateCounterVariables((WorkFlowLine)workLine.stream().findFirst().get(), variables);
            return variables;
        }
        for (WorkFlowLine workFlowLine : workLine) {
            String selectedExecute = workFlowLine.getConditionExecute();
            String result = this.executeResult(businessKey, selectedExecute, workFlowLine.getId());
            variables.put(ProcessBuilderUtils.MD5(workFlowLine.getId()), result);
        }
        return variables;
    }

    void generateCounterVariables(WorkFlowLine workFlowLine, Map<String, Object> variables) {
        Set actors;
        String actId = workFlowLine.getId();
        Map actorsMap = (Map)variables.get(this.nrParameterUtils.getCountersignParamMapKey());
        if (!CollectionUtils.isEmpty(actorsMap) && (actors = (Set)actorsMap.get(actId)) != null && !actors.isEmpty()) {
            variables.put("assigneeList", actors);
        }
    }

    private boolean isCounterSign(BusinessKey businessKey) {
        List<WorkFlowLine> lines = this.nrParameterUtils.queryWorkFlowLines(BusinessKeyFormatter.formatToString(businessKey));
        if (CollectionUtils.isEmpty(lines)) {
            return false;
        }
        for (WorkFlowLine line : lines) {
            WorkFlowNodeSet workflowNode = this.nrParameterUtils.queryWorkflowNode(line.getAfterNodeID(), businessKey.getFormSchemeKey());
            boolean countersign = workflowNode.isSignNode();
            if (!countersign) continue;
            return true;
        }
        return false;
    }

    @Override
    public long suspendProcessInstanceByRunningConfig(UUID runningConfigId) {
        throw new NotImplementedException();
    }

    @Override
    public void suspendProcessInstanceById(String instanceId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        this.activitiRuntimeService.suspendProcessInstanceById(instanceId);
    }

    @Override
    public long activateProcessInstanceByRunningConfig(UUID runningConfigId) {
        throw new NotImplementedException();
    }

    @Override
    public void activateProcessInstanceById(String instanceId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        this.activitiRuntimeService.activateProcessInstanceById(instanceId);
    }

    @Override
    public long deleteProcessInstanceByRunningConfig(UUID runningConfigId) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteProcessInstanceById(String instanceId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        this.activitiRuntimeService.deleteProcessInstance(instanceId, "");
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeTask(String taskId, String userId, String actionId, String comment) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        this.completeTask(taskId, userId, actionId, comment, null, variables);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeTask(String taskId, String userId, String actionId, String comment, TaskContext taskContext) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        this.completeTask(taskId, userId, actionId, comment, taskContext, variables);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeTask(String taskId, String userId, String actionId, String comment, TaskContext context, Map<String, Object> variables) {
        Optional<Task> taskFound;
        Assert.notNull((Object)taskId, "parameter 'taskId' must not be null.");
        Assert.notNull((Object)userId, "parameter 'userId' must not be null.");
        Assert.notNull((Object)actionId, "parameter 'actionId' must not be null.");
        if (context == null) {
            context = new ConcurrentTaskContext();
        }
        if (!(taskFound = this.getTaskById(taskId)).isPresent()) {
            throw new TaskNotFound(taskId);
        }
        Task task = taskFound.get();
        if (task.isSuspended()) {
            throw new TaskSuspendedError(taskId);
        }
        String businessKey = this.getBusinessKey(task.getProcessInstanceId()).get();
        BusinessKey businessKeyObj = BusinessKeyFormatter.parsingFromString(businessKey);
        try {
            Map<String, Object> counterParam = this.nrParameterUtils.buildCounterParam(businessKeyObj, task, actionId);
            variables.putAll(counterParam);
        }
        catch (Exception e) {
            throw new UserActionException(actionId, e.getMessage());
        }
        String operationId = UUID.randomUUID().toString();
        if (this.actionEventHandler.isPresent()) {
            UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
            prepareEvent.setProcessDefinitionId(task.getProcessDefinitionId());
            prepareEvent.setUserTaskId(task.getUserTaskId());
            prepareEvent.setActionId(actionId);
            prepareEvent.setBusinessKey(businessKey);
            prepareEvent.setActorId(userId);
            prepareEvent.setComment(comment);
            prepareEvent.setContext(context);
            prepareEvent.setOperationId(operationId);
            prepareEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKeyObj.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionPrepare(prepareEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
            if (prepareEvent.isSetBreak()) {
                throw new UserActionException(actionId, prepareEvent.getBreakMessage());
            }
            comment = prepareEvent.getComment();
        }
        this.evalFlowLineCondition(variables, task, actionId, userId, businessKey);
        Object force = context.get(this.nrParameterUtils.getForceMapKey());
        this.internalCompleteTask(task, userId, actionId, comment, force, variables);
        if (this.actionEventHandler.isPresent()) {
            UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
            completeEvent.setProcessDefinitionId(task.getProcessDefinitionId());
            completeEvent.setUserTaskId(task.getUserTaskId());
            completeEvent.setActionId(actionId);
            completeEvent.setBusinessKey(businessKey);
            completeEvent.setActorId(userId);
            completeEvent.setComment(comment);
            completeEvent.setContext(context);
            completeEvent.setOperationId(operationId);
            completeEvent.setTaskId(task.getId());
            completeEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKeyObj.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
        }
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(businessKeyObj.getFormSchemeKey());
        HashSet<BusinessKey> businessKeys = new HashSet<BusinessKey>();
        businessKeys.add(businessKeyObj);
        if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(businessKeys, workflowStartType, taskId);
        }
        this.clearSignActionCode();
    }

    void evalFlowLineCondition(Map<String, Object> variables, Task task, String actionId, String userId, String businessKey) {
        String temp;
        BusinessKey businessKeyInfo = BusinessKeyFormatter.parsingFromString(businessKey);
        List<WorkFlowLine> workFlowLines = this.nrParameterUtils.getWorkFlowLine(task.getUserTaskId(), businessKeyInfo.getFormSchemeKey());
        if (CollectionUtils.isEmpty(workFlowLines)) {
            return;
        }
        ArrayList<WorkFlowLine> otherWorkFlowLines = new ArrayList<WorkFlowLine>(workFlowLines);
        ArrayList<String> lineExecuteResult = new ArrayList<String>();
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        boolean signNode = this.nrParameterUtils.isMulitiInstanceTask(task.getUserTaskId(), businessKeyInfo.getFormSchemeKey());
        boolean signStartMode = this.nrParameterUtils.isSignStartMode(task.getUserTaskId(), businessKeyInfo.getFormSchemeKey());
        if (signNode && signStartMode && !"act_reject".equals(actionId) && !"cus_reject".equals(actionId) && !"act_return".equals(actionId) && !"cus_return".equals(actionId) && (temp = this.actionId(businessKey, task, actionId)) != null) {
            actionId = temp;
            NpContext context = NpContextHolder.getContext();
            ContextExtension extension = context.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_ACTION_CODE);
            extension.put(CounterSignConst.NR_WORKFLOW_SIGN_ACTION_CODE_VALUE, (Serializable)((Object)actionId));
        }
        for (WorkFlowLine workFlowLine : workFlowLines) {
            String selectedExecute = workFlowLine.getConditionExecute();
            if (selectedExecute == null) {
                selectedExecute = "DefaultConditionalExecute";
            }
            String input = actionId + task.getUserTaskId() + workFlowLine.getId();
            String result = this.executeResult(task, userId, actionId, businessKey, selectedExecute, workFlowLine.getId());
            if (result.equals("true")) {
                lineExecuteResult.add(result);
                this.generateCounterVariables(workFlowLine, variables);
                otherWorkFlowLines.remove(workFlowLine);
            }
            variables.put(ProcessBuilderUtils.MD5(input), result);
        }
        for (WorkFlowLine otherWorkFlowLine : otherWorkFlowLines) {
            WorkFlowAction workflowAction = this.nrParameterUtils.getWorkflowActionById(otherWorkFlowLine.getActionid(), businessKeyInfo.getFormSchemeKey());
            String otherInput = workflowAction.getActionCode() + task.getUserTaskId() + otherWorkFlowLine.getId();
            variables.put(ProcessBuilderUtils.MD5(otherInput), "false");
        }
        if (lineExecuteResult.size() != 1) {
            throw new BpmException("\u6d41\u7a0b\u8bbe\u8ba1\u9519\u8bef\u6216\u8005\u6d41\u7a0b\u8f6c\u79fb\u7ebf\u4e0a\u7684\u5339\u914d\u6761\u4ef6\u4e0d\u6ee1\u8db3\uff0c\u8bf7\u68c0\u67e5\u6d41\u7a0b\u8bbe\u8ba1");
        }
    }

    void evalFlowLineConditionss(Map<String, Object> variables, Task task, String actionId, String userId, String businessKey) {
        BusinessKey businessKeyInfo = BusinessKeyFormatter.parsingFromString(businessKey);
        List<WorkFlowLine> workFlowLines = this.nrParameterUtils.getWorkFlowLine(task.getUserTaskId(), businessKeyInfo.getFormSchemeKey());
        if (CollectionUtils.isEmpty(workFlowLines)) {
            return;
        }
        ArrayList<WorkFlowLine> otherWorkFlowLines = new ArrayList<WorkFlowLine>(workFlowLines);
        ArrayList<String> lineExecuteResult = new ArrayList<String>();
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        for (WorkFlowLine workFlowLine : workFlowLines) {
            String selectedExecute = workFlowLine.getConditionExecute();
            if (selectedExecute == null) {
                selectedExecute = "DefaultConditionalExecute";
            }
            String input = actionId + task.getUserTaskId() + workFlowLine.getId();
            String result = this.executeResult(task, userId, actionId, businessKey, selectedExecute, workFlowLine.getId());
            if (result.equals("true")) {
                lineExecuteResult.add(result);
                this.generateCounterVariables(workFlowLine, variables);
                otherWorkFlowLines.remove(workFlowLine);
            }
            variables.put(ProcessBuilderUtils.MD5(input), result);
        }
        for (WorkFlowLine otherWorkFlowLine : otherWorkFlowLines) {
            WorkFlowAction workflowAction = this.nrParameterUtils.getWorkflowActionById(otherWorkFlowLine.getActionid(), businessKeyInfo.getFormSchemeKey());
            String otherInput = workflowAction.getActionCode() + task.getUserTaskId() + otherWorkFlowLine.getId();
            variables.put(ProcessBuilderUtils.MD5(otherInput), "false");
        }
        if (lineExecuteResult.size() != 1) {
            throw new BpmException("\u6d41\u7a0b\u8bbe\u8ba1\u9519\u8bef\u6216\u8005\u6d41\u7a0b\u8f6c\u79fb\u7ebf\u4e0a\u7684\u5339\u914d\u6761\u4ef6\u4e0d\u6ee1\u8db3\uff0c\u8bf7\u68c0\u67e5\u6d41\u7a0b\u8bbe\u8ba1");
        }
    }

    private String executeResult(Task task, String userId, String actionId, String businessKey, String selectedExecute, String workFlowLineId) {
        String result = "false";
        for (IConditionalExecute conditionalExecute : this.conditionalExecutes) {
            if (!selectedExecute.equals(conditionalExecute.getClass().getSimpleName()) || !conditionalExecute.execute(task, userId, actionId, businessKey, workFlowLineId)) continue;
            result = "true";
            return result;
        }
        return result;
    }

    private String executeResult(String businessKey, String selectedExecute, String workFlowLineId) {
        String result = "false";
        for (IConditionalExecute conditionalExecute : this.conditionalExecutes) {
            if (!selectedExecute.equals(conditionalExecute.getClass().getSimpleName()) || !conditionalExecute.execute(businessKey, workFlowLineId)) continue;
            result = "true";
            return result;
        }
        return result;
    }

    void internalCompleteTask(Task task, String userId, String actionId, String comment, Object force, Map<String, Object> variables) {
        this.activitiTaskService.claim(task.getId(), userId.toString());
        if (!StringUtils.isEmpty((String)comment)) {
            Authentication.setAuthenticatedUserId((String)userId.toString());
            this.activitiTaskService.addComment(task.getId(), task.getProcessInstanceId(), comment);
        }
        try {
            this.activitiTaskService.complete(task.getId(), this.generateTaskVariables(task, userId, actionId, comment, force, variables));
        }
        catch (Exception e) {
            throw new UserActionException(actionId, e);
        }
    }

    Map<String, Object> generateTaskVariables(Task task, String userId, String actionId, String comment, Object force, Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        variables.put(String.format("action_%s", task.getUserTaskId()), actionId);
        variables.put(this.nrParameterUtils.getForceMapKey(), force);
        String signActionCode = this.getSignActionCode();
        if (signActionCode != null) {
            actionId = signActionCode;
        }
        variables.put(this.nrParameterUtils.getMapKey(), actionId);
        variables.put(this.nrParameterUtils.getUserMapKey(), userId);
        Object object = variables.get("assigneeList");
        if (object != null) {
            variables.put("assigneeList", variables.get("assigneeList"));
        }
        return variables;
    }

    @Override
    public List<UserTask> getRetrievableTask(String instanceId, Actor actor) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        Assert.notNull((Object)actor, "parameter 'actor' must not be null.");
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().processInstanceId(instanceId)).active().list();
        if (tasks.isEmpty()) {
            return null;
        }
        org.activiti.engine.runtime.ProcessInstance instance = (org.activiti.engine.runtime.ProcessInstance)this.activitiRuntimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (instance == null) {
            throw new ProcessInstanceNotFound(instanceId);
        }
        ArrayList<UserTask> retrievableUserTasks = new ArrayList<UserTask>();
        for (org.activiti.engine.task.Task task : tasks) {
            String retrievableUserTaskId = this.activitExtensionService.getRetrievableUserTask(task);
            Optional<UserTask> userTask = this.deployService.getUserTask(task.getProcessDefinitionId(), retrievableUserTaskId);
            if (!userTask.isPresent() || !userTask.get().isRetrivable() || !this.isTaskActorByIdentityLink(ActivitiObjectWrapperUtils.wrappingTask(task), actor)) continue;
            retrievableUserTasks.add(userTask.get());
        }
        return retrievableUserTasks;
    }

    @Override
    public UserTask getRetrievableTask(String instanceId, Actor actor, String userTaskId) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        Assert.notNull((Object)userTaskId, "parameter 'userTaskId' must not be null.");
        Assert.notNull((Object)actor, "parameter 'actor' must not be null.");
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().processInstanceId(instanceId)).active().list();
        if (tasks.isEmpty()) {
            return null;
        }
        UserTask targetTask = null;
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(this.getBusinessKey(instanceId).get());
        for (org.activiti.engine.task.Task task : tasks) {
            Optional<UserTask> userTask = this.deployService.getUserTask(task.getProcessDefinitionId(), userTaskId, businessKey.getFormSchemeKey());
            if (userTask == null || !userTask.isPresent()) continue;
            targetTask = userTask.get();
        }
        return targetTask;
    }

    @Override
    @Transactional
    public Task retrieveTask(String instanceId, String userTaskId, Actor actor) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        Assert.notNull((Object)userTaskId, "parameter 'userTaskId' must not be null.");
        Assert.notNull((Object)actor, "parameter 'actor' must not be null.");
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().processInstanceId(instanceId)).active().list();
        if (tasks.isEmpty()) {
            return null;
        }
        boolean retrievable = false;
        org.activiti.engine.task.Task currentTask = null;
        for (org.activiti.engine.task.Task task : tasks) {
            String retrievableUserTaskId = this.activitExtensionService.getRetrievableUserTask(task);
            Optional<UserTask> userTask = this.deployService.getUserTask(task.getProcessDefinitionId(), retrievableUserTaskId);
            if (userTask == null || !userTask.isPresent() || !userTask.get().getId().equals(userTaskId)) continue;
            retrievable = true;
            currentTask = task;
            break;
        }
        if (!retrievable) {
            throw new ProcessInstanceStateError();
        }
        this.activitExtensionService.jumpTo(currentTask.getProcessDefinitionId(), currentTask.getId(), userTaskId);
        org.activiti.engine.task.Task newTask = (org.activiti.engine.task.Task)((TaskQuery)((TaskQuery)this.activitiTaskService.createTaskQuery().processInstanceId(instanceId)).taskDefinitionKey(userTaskId)).singleResult();
        return ActivitiObjectWrapperUtils.wrappingTask(newTask);
    }

    @Override
    @Transactional
    public void retrieveTask(Task currentTask, UserTask targetTask, String preEvent, BusinessKey businessKey) {
        String signActionCode;
        Assert.notNull((Object)currentTask, "currentTask is must not be null!");
        Assert.notNull((Object)targetTask, "targetTask is must not be null!");
        Assert.notNull((Object)businessKey, "businessKey is must not be null!");
        Actor candicateActor = Actor.fromNpContext();
        String operationId = UUID.randomUUID().toString();
        ConcurrentTaskContext context = new ConcurrentTaskContext();
        if (this.actionEventHandler.isPresent()) {
            UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
            prepareEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            prepareEvent.setUserTaskId(currentTask.getUserTaskId());
            prepareEvent.setActionId("act_retrieve");
            prepareEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            prepareEvent.setActorId(candicateActor.getUserId());
            prepareEvent.setComment(null);
            prepareEvent.setContext(context);
            prepareEvent.setOperationId(operationId);
            prepareEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKey.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionPrepare(prepareEvent);
            }
            catch (Exception e) {
                throw new UserActionException("act_retrieve", e);
            }
            if (prepareEvent.isSetBreak()) {
                throw new UserActionException("act_retrieve", prepareEvent.getBreakMessage());
            }
        }
        if ((signActionCode = this.getSignActionCode()) != null) {
            preEvent = signActionCode;
        }
        this.activitiRuntimeService.setVariable(currentTask.getExecutionId(), this.nrParameterUtils.getMapKey(), (Object)preEvent);
        this.activitExtensionService.jumpTo(currentTask.getProcessDefinitionId(), currentTask.getId(), targetTask.getId());
        if (this.actionEventHandler.isPresent()) {
            UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
            completeEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            completeEvent.setUserTaskId(currentTask.getUserTaskId());
            completeEvent.setActionId("act_retrieve");
            completeEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            completeEvent.setActorId(candicateActor.getUserId());
            completeEvent.setComment(null);
            completeEvent.setContext(context);
            completeEvent.setOperationId(operationId);
            completeEvent.setTaskId(currentTask.getId());
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
            }
            catch (Exception e) {
                throw new UserActionException("act_retrieve", e);
            }
        }
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey) {
        return this.queryTaskByBusinessKey(businessKey, false);
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, boolean startIfInstanceNotExist) {
        Optional<ProcessInstance> instanceFound = this.queryInstanceByBusinessKey(businessKey, startIfInstanceNotExist);
        if (!instanceFound.isPresent()) {
            return Collections.emptyList();
        }
        return this.queryTasks(instanceFound.get().getId());
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, Actor candicateActor) {
        return this.queryTaskByBusinessKey(businessKey, candicateActor, false);
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, Actor candicateActor, boolean startIfInstanceNotExist) {
        Optional<ProcessInstance> instanceFound = this.queryInstanceByBusinessKey(businessKey, startIfInstanceNotExist);
        if (!instanceFound.isPresent()) {
            return Collections.emptyList();
        }
        return this.queryTaskByProcessInstance(instanceFound.get().getId(), candicateActor);
    }

    @Override
    public Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        return this.queryInstanceByBusinessKey(businessKey, false);
    }

    @Override
    public String getAutoStartProcessKey(String businessKey) {
        String processDefintionKey = null;
        for (ProcessProvider processProvider : this.processProviders) {
            processDefintionKey = processProvider.getProcessDefinitionKey(businessKey);
            if (StringUtils.isEmpty((String)processDefintionKey)) continue;
            return processDefintionKey;
        }
        return processDefintionKey;
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void batchCompleteTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String comment, TaskContext context, boolean continueIfFailed, Map<String, Object> variables) {
        Assert.notNull((Object)businessKeySet, "parameter 'businessKeySet' must not be null.");
        Assert.notNull((Object)actor, "parameter 'actor' must not be null.");
        Assert.notNull((Object)actionId, "parameter 'actionId' must not be null.");
        if (context == null) {
            context = new ConcurrentTaskContext();
        }
        String operationId = UUID.randomUUID().toString();
        this.internalBatchCompleteTasks(businessKeySet, actor, actionId, comment, context, continueIfFailed, operationId, variables);
    }

    List<MasterEntityInfo> internalBatchCompleteTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String comment, TaskContext context, boolean continueIfFailed, String operationId, Map<String, Object> variables) {
        MasterEntitySet masterEntitySet = businessKeySet.getMasterEntitySet();
        UserActionProgressEventImpl progressEvent = new UserActionProgressEventImpl();
        progressEvent.setActionId(actionId);
        progressEvent.setBusinessKeySet(businessKeySet);
        progressEvent.setActor(actor);
        progressEvent.setComment(comment);
        progressEvent.setContext(context);
        progressEvent.setOperationId(operationId);
        progressEvent.setCorporateValue(this.nrParameterUtils.getCorporateValue(businessKeySet.getFormSchemeKey()));
        Set<String> formKeys = businessKeySet.getFormKey();
        ArrayList<MasterEntityInfo> completed = new ArrayList<MasterEntityInfo>();
        if (!CollectionUtils.isEmpty(formKeys) && !formKeys.contains("00000000-0000-0000-0000-000000000000")) {
            progressEvent.setTotalSteps(formKeys.size() * masterEntitySet.size());
            this.batchCompleteFormTask(masterEntitySet, formKeys, businessKeySet, actor, continueIfFailed, actionId, comment, operationId, context, completed, progressEvent, variables);
        } else {
            progressEvent.setTotalSteps(masterEntitySet.size());
            this.batchCompleteEntityTask(masterEntitySet, businessKeySet, actor, continueIfFailed, actionId, comment, operationId, context, completed, progressEvent, variables);
        }
        return completed;
    }

    void batchCompleteFormTask(MasterEntitySet masterEntitySet, Set<String> formKeys, BusinessKeySet businessKeySet, Actor actor, boolean continueIfFailed, String actionId, String comment, String operationId, TaskContext context, List<MasterEntityInfo> completed, UserActionProgressEventImpl progressEvent, Map<String, Object> variables) {
        HashSet<BusinessKey> businessKeys = new HashSet<BusinessKey>();
        while (masterEntitySet.next()) {
            for (String key : formKeys) {
                block17: {
                    BusinessKeyImpl businessKey = new BusinessKeyImpl();
                    businessKey.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                    businessKey.setPeriod(businessKeySet.getPeriod());
                    businessKey.setFormKey(key);
                    businessKey.setMasterEntity(masterEntitySet.getCurrent());
                    String businessKeyStr = ((Object)businessKey).toString();
                    List<Task> tasks = this.queryTaskByBusinessKey(businessKeyStr, actor, true);
                    Task taskToComplete = null;
                    for (Task task : tasks) {
                        Optional<UserTask> userTask = this.deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), businessKey.getFormSchemeKey());
                        if (!userTask.isPresent() && !continueIfFailed) {
                            throw new UserTaskNotFound(task.getUserTaskId());
                        }
                        if (!userTask.isPresent() || !userTask.get().getActions().stream().anyMatch(o -> o.getId().equals(actionId))) continue;
                        taskToComplete = task;
                        break;
                    }
                    if (taskToComplete != null) {
                        try {
                            Map<String, Object> counterParam = this.nrParameterUtils.buildCounterParam(businessKey, taskToComplete, actionId);
                            variables.putAll(counterParam);
                            if (this.actionEventHandler.isPresent()) {
                                UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
                                prepareEvent.setActionId(actionId);
                                prepareEvent.setBusinessKey(businessKeyStr);
                                prepareEvent.setActorId(actor.getUserId());
                                prepareEvent.setComment(comment);
                                prepareEvent.setContext(context);
                                prepareEvent.setOperationId(operationId);
                                prepareEvent.setProcessDefinitionId(taskToComplete.getProcessDefinitionId());
                                prepareEvent.setUserTaskId(taskToComplete.getUserTaskId());
                                prepareEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKey.getFormSchemeKey()));
                                try {
                                    ((EventDispatcher)this.actionEventHandler.get()).onUserActionPrepare(prepareEvent);
                                }
                                catch (Exception e) {
                                    throw new UserActionException(actionId, e);
                                }
                                if (prepareEvent.isSetBreak()) {
                                    throw new UserActionException(actionId, prepareEvent.getBreakMessage());
                                }
                                comment = prepareEvent.getComment();
                            }
                            this.evalFlowLineCondition(variables, taskToComplete, actionId, actor.getUserId(), businessKeyStr);
                            try {
                                this.internalCompleteTask(taskToComplete, actor.getUserId(), actionId, comment, context.get(this.nrParameterUtils.getForceMapKey()), variables);
                                businessKeys.add(businessKey);
                            }
                            catch (UserActionException prepareEvent) {
                                // empty catch block
                            }
                            UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
                            completeEvent.setActionId(actionId);
                            completeEvent.setBusinessKey(businessKeyStr);
                            completeEvent.setActorId(actor.getUserId());
                            completeEvent.setComment(comment);
                            completeEvent.setContext(context);
                            completeEvent.setOperationId(operationId);
                            completeEvent.setProcessDefinitionId(taskToComplete.getProcessDefinitionId());
                            completeEvent.setUserTaskId(taskToComplete.getUserTaskId());
                            completeEvent.setTaskId(taskToComplete.getId());
                            completeEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKey.getFormSchemeKey()));
                            if (this.actionEventHandler.isPresent()) {
                                ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
                            }
                            completed.add(masterEntitySet.getCurrent());
                        }
                        catch (Exception e) {
                            if (continueIfFailed) break block17;
                            throw new UserActionException(actionId, e);
                        }
                    }
                }
                progressEvent.doStep();
                if (!this.actionEventHandler.isPresent()) continue;
                try {
                    ((EventDispatcher)this.actionEventHandler.get()).onUserActionProgressChanged(progressEvent);
                }
                catch (Exception e) {
                    throw new UserActionException(actionId, e);
                }
            }
        }
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(businessKeySet.getFormSchemeKey());
        if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(businessKeys, workflowStartType, null);
        }
        this.clearSignActionCode();
    }

    void batchCompleteEntityTask(MasterEntitySet masterEntitySet, BusinessKeySet businessKeySet, Actor actor, boolean continueIfFailed, String actionId, String comment, String operationId, TaskContext context, List<MasterEntityInfo> completed, UserActionProgressEventImpl progressEvent, Map<String, Object> variables) {
        while (masterEntitySet.next()) {
            block13: {
                BusinessKeyImpl businessKey = new BusinessKeyImpl();
                businessKey.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                businessKey.setPeriod(businessKeySet.getPeriod());
                businessKey.setMasterEntity(masterEntitySet.getCurrent());
                String businessKeyStr = ((Object)businessKey).toString();
                List<Task> tasks = this.queryTaskByBusinessKey(businessKeyStr, actor, true);
                Task taskToComplete = null;
                for (Task task : tasks) {
                    Optional<UserTask> userTask = this.deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), businessKey.getFormSchemeKey());
                    if (!userTask.isPresent() && !continueIfFailed) {
                        throw new UserTaskNotFound(task.getUserTaskId());
                    }
                    if (!userTask.isPresent() || !userTask.get().getActions().stream().anyMatch(o -> o.getId().equals(actionId))) continue;
                    taskToComplete = task;
                    break;
                }
                if (taskToComplete != null) {
                    try {
                        Map<String, Object> counterParam = this.nrParameterUtils.buildCounterParam(businessKey, taskToComplete, actionId);
                        variables.putAll(counterParam);
                        if (this.actionEventHandler.isPresent()) {
                            UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
                            prepareEvent.setActionId(actionId);
                            prepareEvent.setBusinessKey(businessKeyStr);
                            prepareEvent.setActorId(actor.getUserId());
                            prepareEvent.setComment(comment);
                            prepareEvent.setContext(context);
                            prepareEvent.setOperationId(operationId);
                            prepareEvent.setProcessDefinitionId(taskToComplete.getProcessDefinitionId());
                            prepareEvent.setUserTaskId(taskToComplete.getUserTaskId());
                            prepareEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKey.getFormSchemeKey()));
                            try {
                                ((EventDispatcher)this.actionEventHandler.get()).onUserActionPrepare(prepareEvent);
                            }
                            catch (Exception e) {
                                throw new UserActionException(actionId, e);
                            }
                            if (prepareEvent.isSetBreak()) {
                                throw new UserActionException(actionId, prepareEvent.getBreakMessage());
                            }
                            comment = prepareEvent.getComment();
                        }
                        this.evalFlowLineCondition(variables, taskToComplete, actionId, actor.getUserId(), businessKeyStr);
                        this.internalCompleteTask(taskToComplete, actor.getUserId(), actionId, comment, context.get(this.nrParameterUtils.getForceMapKey()), variables);
                        UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
                        completeEvent.setActionId(actionId);
                        completeEvent.setBusinessKey(businessKeyStr);
                        completeEvent.setActorId(actor.getUserId());
                        completeEvent.setComment(comment);
                        completeEvent.setContext(context);
                        completeEvent.setOperationId(operationId);
                        completeEvent.setProcessDefinitionId(taskToComplete.getProcessDefinitionId());
                        completeEvent.setUserTaskId(taskToComplete.getUserTaskId());
                        completeEvent.setTaskId(taskToComplete.getId());
                        if (this.actionEventHandler.isPresent()) {
                            ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
                        }
                        completed.add(masterEntitySet.getCurrent());
                    }
                    catch (Exception e) {
                        if (continueIfFailed) break block13;
                        throw new UserActionException(actionId, e);
                    }
                }
            }
            progressEvent.doStep();
            if (!this.actionEventHandler.isPresent()) continue;
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionProgressChanged(progressEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
        }
        this.clearSignActionCode();
    }

    @Override
    public ProcessInstance startProcessByProcessDefinitionId(String businessKey, String processDefinitionId, String startUserId, Map<String, Object> variables) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        Assert.notNull((Object)processDefinitionId, "parameter 'processDefinitionId' must not be null.");
        variables.put("businessKey", businessKey);
        return ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceById(processDefinitionId, businessKey, variables));
    }

    @Override
    public ProcessInstance startProcessByProcessDefinitionKey(String businessKey, String processDefinitionKey, String startUserId, Map<String, Object> variables) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        Assert.notNull((Object)processDefinitionKey, "parameter 'processDefinitionKey' must not be null.");
        variables.put("businessKey", businessKey);
        return ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables));
    }

    @Override
    public List<Task> queryTasks(String businessKey, boolean defaultWorkflow, Actor candicateActor, boolean startIfInstanceNotExist) {
        return this.queryTasks(businessKey, defaultWorkflow, candicateActor, startIfInstanceNotExist, null);
    }

    @Override
    public List<Task> queryTasks(String businessKey, boolean defaultWorkflow, Actor candicateActor, boolean startIfInstanceNotExist, String processKey) {
        if (defaultWorkflow) {
            return this.queryTaskByBusinessKey(businessKey, candicateActor, startIfInstanceNotExist);
        }
        Optional<ProcessInstance> processInstance = this.queryInstanceByBusinessKey(businessKey, startIfInstanceNotExist, processKey);
        return this.queryTaskByProcessInstance(processInstance.get().getId(), candicateActor);
    }

    @Override
    public Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey, boolean startIfNotExist, String processDefinitionKey) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        Optional<ProcessInstance> instanceFound = this.queryInstanceBase(businessKey, startIfNotExist, processDefinitionKey);
        if (instanceFound.isPresent() || !startIfNotExist) {
            return instanceFound;
        }
        HashMap<String, Object> variables = new HashMap<String, Object>();
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, this.generateInstanceVariables(businessKey, variables))));
    }

    @Override
    public Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey, boolean startIfNotExist) {
        Assert.notNull((Object)businessKey, "parameter 'businessKey' must not be null.");
        Optional<ProcessInstance> instanceFound = this.queryInstanceBase(businessKey, startIfNotExist, null);
        if (instanceFound.isPresent() || !startIfNotExist) {
            return instanceFound;
        }
        String processDefinitionKey = this.getAutoStartProcessKey(businessKey);
        if (StringUtils.isEmpty((String)processDefinitionKey)) {
            return instanceFound;
        }
        HashMap<String, Object> variables = new HashMap<String, Object>();
        return Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingProcessInstance(this.activitiRuntimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, this.generateInstanceVariables(businessKey, variables))));
    }

    private Optional<ProcessInstance> queryInstanceBase(String businessKey, boolean startIfNotExist, String processDefinitionKey) {
        Optional<ProcessInstance> instanceFound = null;
        List activitiInstances = new ArrayList();
        activitiInstances = processDefinitionKey == null ? this.activitiRuntimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list() : this.activitiRuntimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).processDefinitionKey(processDefinitionKey).list();
        if (activitiInstances.isEmpty()) {
            instanceFound = Optional.empty();
        } else {
            instanceFound = Optional.ofNullable(ActivitiObjectWrapperUtils.wrappingProcessInstance((org.activiti.engine.runtime.ProcessInstance)activitiInstances.get(0)));
            if (activitiInstances.size() > 1) {
                for (int i = 1; i < activitiInstances.size(); ++i) {
                    this.activitiRuntimeService.deleteProcessInstance(((org.activiti.engine.runtime.ProcessInstance)activitiInstances.get(i)).getProcessInstanceId(), "delete repeat.");
                }
            }
        }
        return instanceFound;
    }

    @Override
    public boolean isTaskActor(UserTask usertTask, BusinessKey businessKey, Task task) {
        Actor candicateActor = Actor.fromNpContext();
        return candicateActor != null && ActorUtils.isTaskActor(usertTask, businessKey, candicateActor, this.actorStrategyProvider, task);
    }

    @Override
    public Optional<UserTask> getTargetTaskById(Task currTask, String taskId) {
        Optional<UserTask> userTask = this.deployService.getUserTask(currTask.getProcessDefinitionId(), taskId);
        if (!userTask.isPresent()) {
            return Optional.empty();
        }
        return userTask;
    }

    @Override
    public Optional<UserTask> getTargetTaskById(Task currTask, String taskId, BusinessKey businessKey) {
        return this.getTargetTaskById(currTask, taskId);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeProcessTask(BusinessKey businessKey, String taskId, String userId, String actionId, String comment, TaskContext taskContext) {
        this.completeTask(taskId, userId, actionId, comment, taskContext);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeProcessTask(BusinessKey businessKey, String taskId, String userId, String actionId, String comment, TaskContext taskContext, Map<String, Object> variables) {
        this.completeTask(taskId, userId, actionId, comment, taskContext, variables);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void batchCompleteProcessTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String taskId, String comment, TaskContext taskContext) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        this.batchCompleteTasks(businessKeySet, actor, actionId, comment, taskContext, false, variables);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void batchCompleteProcessTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String taskId, String comment, TaskContext taskContext, Map<String, Object> variables) {
        this.batchCompleteTasks(businessKeySet, actor, actionId, comment, taskContext, false, variables);
    }

    @Override
    public List<ProcessInstance> queryInstanceByFormSchemeKey(String formSchemeKey, String period) {
        List activitiInstances = this.activitiRuntimeService.createProcessInstanceQuery().variableValueLike("businessKey", "%".concat(String.format("%s%s%s", "FS{", formSchemeKey, "}"))).active().list();
        if (activitiInstances.isEmpty()) {
            return Collections.emptyList();
        }
        return activitiInstances.stream().map(e -> ActivitiObjectWrapperUtils.wrappingProcessInstance(e)).collect(Collectors.toList()).stream().filter(e -> e.getBusinessKey().getPeriod().equals(period)).collect(Collectors.toList());
    }

    @Override
    public boolean hasVariable(String executionId, String variableName) {
        return this.activitiRuntimeService.hasVariable(executionId, variableName);
    }

    @Override
    public void removeVariable(String executionId, String variableName) {
        this.activitiRuntimeService.removeVariable(executionId, variableName);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void retrieveTask(Task currentTask, UserTask targetTask, String preEvent, BusinessKey businessKey, TaskContext context) {
        String signActionCode;
        Assert.notNull((Object)currentTask, "currentTask is must not be null!");
        Assert.notNull((Object)targetTask, "targetTask is must not be null!");
        Assert.notNull((Object)businessKey, "businessKey is must not be null!");
        Actor candicateActor = Actor.fromNpContext();
        String operationId = UUID.randomUUID().toString();
        if (context == null) {
            context = new ConcurrentTaskContext();
        }
        if (this.actionEventHandler.isPresent()) {
            UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
            prepareEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            prepareEvent.setUserTaskId(currentTask.getUserTaskId());
            prepareEvent.setActionId("act_retrieve");
            prepareEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            prepareEvent.setActorId(candicateActor.getUserId());
            prepareEvent.setComment(null);
            prepareEvent.setContext(context);
            prepareEvent.setOperationId(operationId);
            prepareEvent.setCorporateVaule(this.nrParameterUtils.getCorporateValue(businessKey.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionPrepare(prepareEvent);
            }
            catch (Exception e) {
                throw new UserActionException("act_retrieve", e);
            }
            if (prepareEvent.isSetBreak()) {
                throw new UserActionException("act_retrieve", prepareEvent.getBreakMessage());
            }
        }
        if ((signActionCode = this.getSignActionCode()) != null) {
            preEvent = signActionCode;
        }
        this.activitiRuntimeService.setVariable(currentTask.getExecutionId(), this.nrParameterUtils.getMapKey(), (Object)preEvent);
        this.activitExtensionService.jumpTo(currentTask.getProcessDefinitionId(), currentTask.getId(), targetTask.getId());
        if (this.actionEventHandler.isPresent()) {
            UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
            completeEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            completeEvent.setUserTaskId(currentTask.getUserTaskId());
            completeEvent.setActionId("act_retrieve");
            completeEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            completeEvent.setActorId(candicateActor.getUserId());
            completeEvent.setComment(null);
            completeEvent.setContext(context);
            completeEvent.setOperationId(operationId);
            completeEvent.setTaskId(currentTask.getId());
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
            }
            catch (Exception e) {
                throw new UserActionException("act_retrieve", e);
            }
        }
    }

    @Override
    public Map<Task, String> querySignTaskByProcessInstance(String instanceId, Actor candicateActor) {
        Assert.notNull((Object)instanceId, "parameter 'instanceId' must not be null.");
        HashMap<Task, String> map = new HashMap<Task, String>();
        List tasks = ((TaskQuery)this.activitiTaskService.createTaskQuery().active().processInstanceId(instanceId)).list();
        if (tasks.isEmpty()) {
            return map;
        }
        org.activiti.engine.runtime.ProcessInstance instance = (org.activiti.engine.runtime.ProcessInstance)this.activitiRuntimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (instance == null) {
            throw new ProcessInstanceNotFound(instanceId);
        }
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(instance.getBusinessKey());
        for (org.activiti.engine.task.Task task : tasks) {
            Optional<UserTask> userTask = this.deployService.getUserTask(instance.getProcessDefinitionId(), task.getTaskDefinitionKey(), businessKey.getFormSchemeKey());
            if (!userTask.isPresent()) continue;
            boolean isCounterTask = this.nrParameterUtils.isMulitiInstanceTask(task.getTaskDefinitionKey(), businessKey.getFormSchemeKey());
            boolean signNodeByRoleType = this.nrParameterUtils.isSignNodeByRoleType(task.getTaskDefinitionKey(), businessKey.getFormSchemeKey());
            if (candicateActor == null || !isCounterTask || !signNodeByRoleType) continue;
            String roleKey = null;
            Object variable = this.activitiRuntimeService.getVariable(task.getExecutionId(), task.getId());
            if (variable == null) continue;
            roleKey = variable.toString();
            map.put(ActivitiObjectWrapperUtils.wrappingTask(task, this.activitiTaskService), roleKey);
        }
        return map;
    }

    private String actionId(String businessKeyStr, Task task, String actionId) {
        String tempActionId = null;
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(businessKeyStr);
        ArrayList<String> actions = new ArrayList<String>();
        List<String> roleKeys = this.getRoleKeys(businessKey.getFormSchemeKey(), task.getUserTaskId());
        List<UploadRecordNew> historyState = this.nrParameterUtils.queryHistoryState(businessKey, task.getUserTaskId(), roleKeys);
        List<Object> temp = new ArrayList();
        if (historyState != null && historyState.size() > 0) {
            if (historyState.size() <= roleKeys.size()) {
                temp = historyState;
            } else if (historyState.size() > roleKeys.size()) {
                temp = historyState.subList(0, roleKeys.size());
            }
        }
        for (UploadRecordNew uploadRecordNew : temp) {
            actions.add(uploadRecordNew.getAction());
        }
        if (actions.contains("act_reject")) {
            tempActionId = "act_reject";
        } else if (actions.contains("cus_reject")) {
            tempActionId = "cus_reject";
        } else if (actions.contains("act_return")) {
            tempActionId = "act_return";
        } else if (actions.contains("cus_return")) {
            tempActionId = "cus_return";
        }
        return tempActionId;
    }

    private String getSignActionCode() {
        Object object;
        NpContext npContext = NpContextHolder.getContext();
        ContextExtension extension = npContext.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_ACTION_CODE);
        if (extension != null && (object = extension.get(CounterSignConst.NR_WORKFLOW_SIGN_ACTION_CODE_VALUE)) != null) {
            return (String)object;
        }
        return null;
    }

    private void clearSignActionCode() {
        NpContext npContext = NpContextHolder.getContext();
        ContextExtension extension = npContext.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_ACTION_CODE);
        extension.put(CounterSignConst.NR_WORKFLOW_SIGN_ACTION_CODE_VALUE, null);
    }

    private List<String> getRoleKeys(String formSchemeKey, String userTaskId) {
        WorkFlowNodeSet workFlowNodeSet = this.nrParameterUtils.queryNodeNode(formSchemeKey, userTaskId);
        List<String> roleKeys = this.nrParameterUtils.queryNodeActors(formSchemeKey, workFlowNodeSet);
        String identityId = NpContextHolder.getContext().getIdentityId();
        Set<String> roleIdsByUserId = this.nrParameterUtils.getRoleIdsByUserId(identityId);
        if (roleKeys != null && roleKeys.size() > 0) {
            roleKeys.removeAll(roleIdsByUserId);
        }
        return roleKeys;
    }

    @Override
    public void jumpToTargetNode(String processDefinitionId, String currentTaskId, String jumpToTaskDefinitionId) {
        this.activitExtensionService.jumpTo(processDefinitionId, currentTaskId, jumpToTaskDefinitionId);
    }
}

