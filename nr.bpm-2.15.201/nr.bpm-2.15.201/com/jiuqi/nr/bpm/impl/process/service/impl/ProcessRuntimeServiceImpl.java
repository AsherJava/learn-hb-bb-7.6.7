/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.xlib.runtime.Assert
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.impl.process.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
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
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.SimpleMessageThreadLocalStrategy;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.exception.TaskNotFound;
import com.jiuqi.nr.bpm.exception.TaskSuspendedError;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchPrepareEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionPrepareEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionProgressEventImpl;
import com.jiuqi.nr.bpm.impl.process.ProcessInstanceImpl;
import com.jiuqi.nr.bpm.impl.process.ProcessTask;
import com.jiuqi.nr.bpm.impl.process.ProcessTaskCreateBatchEvent;
import com.jiuqi.nr.bpm.impl.process.ProcessTaskCreateEvent;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.consts.TaskEnum;
import com.jiuqi.nr.bpm.impl.process.dao.BatchCompleteParam;
import com.jiuqi.nr.bpm.impl.process.dao.UploadProcessInstanceDto;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.service.AbstractRuntimeService;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.xlib.runtime.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

public class ProcessRuntimeServiceImpl
extends AbstractRuntimeService {
    public ProcessRuntimeServiceImpl(DeployService deployService) {
        this.deployService = deployService;
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        BusinessKey businessKeyinfo = BusinessKeyFormatter.parsingFromString(businessKey);
        return processTaskBuilder.get().queryTaskByBusinessKey(businessKeyinfo);
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, Actor candicateActor) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        BusinessKey businessKeyinfo = BusinessKeyFormatter.parsingFromString(businessKey);
        List<Task> tasks = processTaskBuilder.get().queryTaskByBusinessKey(businessKeyinfo);
        return this.filterTaskByActor(tasks, candicateActor, businessKeyinfo);
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, boolean startIfInstanceNotExist) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        return this.queryTaskByBusinessKey(businessKey, null, startIfInstanceNotExist);
    }

    @Override
    public Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        BusinessKey businessKeyinfo = BusinessKeyFormatter.parsingFromString(businessKey);
        DimensionValueSet dimension = this.processUtil.buildUploadMasterKey(businessKeyinfo);
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKeyinfo.getFormSchemeKey());
        UploadStateNew uploadState = this.processStateHistoryDao.queryUploadState(dimension, businessKeyinfo.getFormKey(), formScheme);
        if (uploadState != null) {
            return Optional.of(new ProcessInstanceImpl(BusinessKeyFormatter.parsingFromString(businessKey)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey, boolean startIfNotExist) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Optional<ProcessInstance> instance = this.queryInstanceByBusinessKey(businessKey);
        if (instance.isPresent() || !startIfNotExist) {
            return instance;
        }
        return Optional.ofNullable(this.startProcessByBusinessKey(BusinessKeyFormatter.parsingFromString(businessKey)));
    }

    @Override
    public Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey, boolean startIfNotExist, String processKey) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        return this.queryInstanceByBusinessKey(businessKey, startIfNotExist);
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, Actor candicateActor, boolean startIfInstanceNotExist, IConditionCache conditionCache) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        if (processTaskBuilder.isPresent()) {
            BusinessKey businessKeyinfo = BusinessKeyFormatter.parsingFromString(businessKey);
            Optional<ProcessInstance> instance = this.queryInstanceByBusinessKey(businessKey);
            if (!instance.isPresent() && startIfInstanceNotExist) {
                this.startProcessByBusinessKey(businessKeyinfo, conditionCache);
            }
            if (candicateActor != null) {
                return this.filterTaskByActor(processTaskBuilder.get().queryTaskByBusinessKey(businessKeyinfo), candicateActor, businessKeyinfo);
            }
            return processTaskBuilder.get().queryTaskByBusinessKey(businessKeyinfo);
        }
        return Collections.emptyList();
    }

    private Optional<ProcessInstance> queryInstanceByBusinessKey(String businessKey, UploadStateNew uploadState) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        if (uploadState != null) {
            return Optional.of(new ProcessInstanceImpl(BusinessKeyFormatter.parsingFromString(businessKey)));
        }
        return Optional.empty();
    }

    @Override
    public List<ProcessInstance> queryInstanceByFormSchemeKey(String formSchemeKey, String period) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must  be not null!");
        List<UploadProcessInstanceDto> instacne = this.processStateHistoryDao.queryUploadInstance(formSchemeKey, period);
        return instacne.stream().map(e -> new ProcessInstanceImpl(e.getBusinessKey())).collect(Collectors.toList());
    }

    @Override
    public Optional<ProcessInstance> getInstanceById(String instanceId) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        return this.queryInstanceByBusinessKey(instanceId);
    }

    @Override
    public Optional<String> getBusinessKey(String instanceId) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        return Optional.of(instanceId);
    }

    @Override
    public List<Task> queryTasks(String instanceId) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        return this.queryTaskByBusinessKey(instanceId);
    }

    @Override
    public List<Task> queryTaskByProcessInstance(String instanceId, Actor candicateActor) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        return this.queryTaskByBusinessKey(instanceId, candicateActor);
    }

    @Override
    public List<Task> queryTaskByBusinessKey(String businessKey, Actor candicateActor, boolean startIfInstanceNotExist) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        if (processTaskBuilder.isPresent()) {
            BusinessKey businessKeyinfo = BusinessKeyFormatter.parsingFromString(businessKey);
            FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKeyinfo.getFormSchemeKey());
            List<Task> taskByBusinessKey = processTaskBuilder.get().queryTaskByBusinessKey(businessKeyinfo);
            if (taskByBusinessKey.size() == 0) {
                taskByBusinessKey = this.startProcessByBusinessKey(formScheme, businessKeyinfo);
            }
            if (candicateActor != null) {
                return this.filterTaskByActor(taskByBusinessKey, candicateActor, businessKeyinfo);
            }
            return taskByBusinessKey;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Task> queryTasks(String businessKey, boolean defaultWorkflow, Actor candicateActor, boolean startIfInstanceNotExist) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        if (defaultWorkflow) {
            return this.queryTaskByBusinessKey(businessKey, candicateActor, startIfInstanceNotExist);
        }
        return this.queryTaskByBusinessKey(businessKey, candicateActor);
    }

    @Override
    public List<Task> queryTasks(String businessKey, boolean defaultWorkflow, Actor candicateActor, boolean startIfInstanceNotExist, String processKey) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        if (defaultWorkflow) {
            return this.queryTaskByBusinessKey(businessKey, candicateActor, startIfInstanceNotExist);
        }
        return this.queryTaskByBusinessKey(businessKey, candicateActor);
    }

    @Override
    public Optional<Task> getTaskById(String taskId, BusinessKey businessKey) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        return processTaskBuilder.get().queryTaskById(taskId, businessKey);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public ProcessInstance startProcessByProcessDefinitionKey(String businessKey, String processDefinitionKey, String startUserId) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        return this.startProcessByBusinessKey(BusinessKeyFormatter.parsingFromString(businessKey));
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public ProcessInstance startProcessByProcessDefinitionId(String businessKey, String processDefinitionId, String startUserId) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        return this.startProcessByBusinessKey(BusinessKeyFormatter.parsingFromString(businessKey));
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public ProcessInstance startProcessByProcessDefinitionId(String businessKey, String processDefinitionId, String startUserId, Map<String, Object> variables) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        return this.startProcessByBusinessKey(BusinessKeyFormatter.parsingFromString(businessKey));
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public ProcessInstance startProcessByProcessDefinitionKey(String businessKey, String processDefinitionKey, String startUserId, Map<String, Object> variables) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        return this.startProcessByBusinessKey(BusinessKeyFormatter.parsingFromString(businessKey));
    }

    @Override
    public void deleteProcessInstanceById(String instanceId) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        this.processStateHistoryDao.deleteUploadStateAndRecord(BusinessKeyFormatter.parsingFromString(instanceId));
    }

    @Override
    public UserTask getRetrievableTask(String instanceId, Actor actor, String userTaskId) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        Assert.notNull((Object)userTaskId, (String)"userTaskId is must  be not null!");
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(instanceId);
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        Optional<UserTask> userTask = builder.get().queryUserTask(userTaskId, businessKey.getFormSchemeKey());
        Optional<Task> task = builder.get().queryTaskById(userTaskId, businessKey);
        if (userTask.isPresent() && task.isPresent() && this.isTaskActor(userTask.get(), businessKey, task.get())) {
            return userTask.get();
        }
        return null;
    }

    @Override
    public Task retrieveTask(String instanceId, String userTaskId, Actor actor) {
        Assert.notNull((Object)instanceId, (String)"instanceId is must  be not null!");
        Assert.notNull((Object)userTaskId, (String)"userTaskId is must  be not null!");
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(instanceId);
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        Optional<Task> task = builder.get().queryTaskById(userTaskId, businessKey);
        if (task.isPresent()) {
            return null;
        }
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(task.get());
        List<Task> filterTasks = this.filterTaskByActor(tasks, actor, businessKey);
        if (filterTasks.isEmpty()) {
            return null;
        }
        return (Task)filterTasks.stream().findFirst().get();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public ProcessInstance startProcessByBusinessKey(BusinessKey businessKey) {
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        String businessKeyStr = BusinessKeyFormatter.formatToString(businessKey);
        Optional<ProcessInstance> instance = this.queryInstanceByBusinessKey(businessKeyStr);
        if (instance.isPresent()) {
            return instance.get();
        }
        boolean canStart = this.canStartFlow(businessKeyStr);
        if (!instance.isPresent() && !canStart) {
            return null;
        }
        String taskId = builder.get().nextUserTaskId("start", null, businessKeyStr);
        if (this.actionEventHandler.isPresent()) {
            ProcessTaskCreateEvent createEvent = new ProcessTaskCreateEvent();
            createEvent.setActionId("start");
            createEvent.setBusinessKey(businessKey);
            createEvent.setTaskId(taskId);
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onCreateTask(createEvent);
            }
            catch (Exception e) {
                throw new BpmException(e);
            }
        }
        return new ProcessInstanceImpl(businessKey);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public ProcessInstance startProcessByBusinessKey(BusinessKey businessKey, IConditionCache conditionCache) {
        SimpleMessageThreadLocalStrategy.setMessageEventListener(this.nrParameterUtils.getMessageEventListener());
        SimpleMessageThreadLocalStrategy.setSumTotal(1);
        SimpleMessageThreadLocalStrategy.setUpperLimitValue(this.nrParameterUtils.getMaxValue());
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        String businessKeyStr = BusinessKeyFormatter.formatToString(businessKey);
        Optional<ProcessInstance> instance = this.queryInstanceByBusinessKey(businessKeyStr);
        if (instance.isPresent()) {
            return instance.get();
        }
        boolean canStart = this.canStartFlow(businessKeyStr);
        if (!instance.isPresent() && !canStart) {
            return null;
        }
        String taskId = builder.get().nextUserTaskId("start", null, businessKeyStr);
        if (this.actionEventHandler.isPresent()) {
            ProcessTaskCreateEvent createEvent = new ProcessTaskCreateEvent();
            createEvent.setActionId("start");
            createEvent.setBusinessKey(businessKey);
            createEvent.setTaskId(taskId);
            createEvent.setConditionCache(conditionCache);
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onCreateTask(createEvent);
            }
            catch (Exception e) {
                throw new BpmException(e);
            }
        }
        SimpleMessageThreadLocalStrategy.clear();
        HashSet<BusinessKey> success = new HashSet<BusinessKey>();
        success.add(businessKey);
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(businessKey.getFormSchemeKey());
        if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(success, workflowStartType, taskId);
        }
        return new ProcessInstanceImpl(businessKey);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Task> startProcessByBusinessKey(FormSchemeDefine formScheme, BusinessKey businessKey) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        String businessKeyStr = BusinessKeyFormatter.formatToString(businessKey);
        DimensionValueSet dimensionValueSet = this.processUtil.buildUploadMasterKey(businessKey);
        String taskId = builder.get().nextUserTaskId("start", null, businessKeyStr);
        List<TaskEnum> taskEnums = Arrays.asList(TaskEnum.values());
        List tasks = taskEnums.stream().filter(e -> e.getTaskId().equals(taskId)).collect(Collectors.toList());
        TaskEnum task = (TaskEnum)((Object)tasks.stream().findFirst().get());
        BusinessGenerator businessGenerator = (BusinessGenerator)BeanUtil.getBean(BusinessGenerator.class);
        userTasks.add(new ProcessTask(task, businessGenerator.buildBusinessKey(formScheme.getKey(), dimensionValueSet, businessKey.getFormKey(), businessKey.getFormKey())));
        if (this.actionEventHandler.isPresent()) {
            ProcessTaskCreateEvent createEvent = new ProcessTaskCreateEvent();
            createEvent.setActionId("start");
            createEvent.setBusinessKey(businessKey);
            createEvent.setTaskId(taskId);
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onCreateTask(createEvent);
            }
            catch (Exception e2) {
                throw new BpmException(e2);
            }
        }
        return userTasks;
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public Set<BusinessKey> batchStartProcessByBusinessKey(Map<BusinessKey, String> startParam, Map<String, Object> variables) {
        WorkFlowType workflowStartType;
        if (startParam.isEmpty()) {
            return new HashSet<BusinessKey>();
        }
        String formSchemeKey = startParam.keySet().iterator().next().getFormSchemeKey();
        HashSet<BusinessKey> success = new HashSet<BusinessKey>();
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        BatchCompleteParam batchCompleteParam = new BatchCompleteParam();
        ArrayList<DimensionValueSet> masterKeys = new ArrayList<DimensionValueSet>();
        Set<BusinessKey> businessKeys = startParam.keySet();
        String taskId = null;
        SimpleMessageThreadLocalStrategy.setSumTotal(startParam.size());
        SimpleMessageThreadLocalStrategy.setMessageEventListener(this.nrParameterUtils.getMessageEventListener());
        SimpleMessageThreadLocalStrategy.setUpperLimitValue(this.nrParameterUtils.getMaxValue());
        for (BusinessKey businessKey : businessKeys) {
            String nextTaskId;
            boolean canExe;
            String businessKeyStr = BusinessKeyFormatter.formatToString(businessKey);
            Optional<ProcessInstance> instance = this.queryInstanceByBusinessKey(businessKeyStr);
            if (instance.isPresent() || !(canExe = this.canStartFlow(businessKeyStr)) || (nextTaskId = builder.get().nextUserTaskId("start", null, businessKeyStr)) == null) continue;
            masterKeys.add(this.processUtil.buildUploadMasterKey(businessKey));
            taskId = nextTaskId;
            success.add(businessKey);
        }
        if (businessKeys != null && businessKeys.size() > 0) {
            batchCompleteParam.setFormSchemeKey(((BusinessKey)businessKeys.stream().findAny().get()).getFormSchemeKey());
            batchCompleteParam.setMasterKey(masterKeys.stream().findAny().orElse(null));
            batchCompleteParam.setMasterKeysList(masterKeys);
            batchCompleteParam.setTaskId(taskId);
            batchCompleteParam.setForceUpload(false);
            batchCompleteParam.setActionId("start");
            this.processStateHistoryDao.batchUpdateState(batchCompleteParam);
            ProcessTaskCreateBatchEvent batchEvent = new ProcessTaskCreateBatchEvent();
            batchEvent.setActionId("start");
            batchEvent.setBusinessKey(success.stream().collect(Collectors.toList()));
            if (this.actionEventHandler.isPresent()) {
                try {
                    ((EventDispatcher)this.actionEventHandler.get()).onBatchCreateTask(batchEvent);
                }
                catch (Exception e) {
                    throw new BpmException(e);
                }
            }
        }
        if (WorkFlowType.FORM.equals((Object)(workflowStartType = this.nrParameterUtils.getWorkflowStartType(formSchemeKey))) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(success, workflowStartType, taskId);
        }
        SimpleMessageThreadLocalStrategy.clear();
        return success;
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeProcessTask(BusinessKey businessKey, String taskId, String userId, String actionId, String comment, TaskContext taskContext) {
        Optional<Task> taskFound;
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        Assert.notNull((Object)taskId, (String)"taskId is must  be not null!");
        Assert.notNull((Object)actionId, (String)"actionId is must  be not null!");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        if (taskContext == null) {
            taskContext = new ConcurrentTaskContext();
        }
        if (!(taskFound = this.getTaskById(taskId, businessKey)).isPresent()) {
            throw new TaskNotFound(taskId);
        }
        Task task = taskFound.get();
        if (task.isSuspended()) {
            throw new TaskSuspendedError(taskId);
        }
        String operationId = UUID.randomUUID().toString();
        if (this.actionEventHandler.isPresent()) {
            UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
            prepareEvent.setProcessDefinitionId(task.getProcessDefinitionId());
            prepareEvent.setUserTaskId(task.getUserTaskId());
            prepareEvent.setActionId(actionId);
            prepareEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            prepareEvent.setActorId(userId);
            prepareEvent.setComment(comment);
            prepareEvent.setContext(taskContext);
            prepareEvent.setOperationId(operationId);
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
        if (this.actionEventHandler.isPresent()) {
            Object force = taskContext.get(this.nrParameterUtils.getForceMapKey());
            ProcessTaskCreateEvent taskCreateEvent = new ProcessTaskCreateEvent();
            taskCreateEvent.setTaskId(processTaskBuilder.get().nextUserTaskId(taskId, actionId, BusinessKeyFormatter.formatToString(businessKey)));
            taskCreateEvent.setBusinessKey(businessKey);
            if (force != null) {
                taskCreateEvent.setForceUpload((boolean)((Boolean)force));
            }
            taskCreateEvent.setActionId(actionId);
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onCreateTask(taskCreateEvent);
                UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
                completeEvent.setUserTaskId(taskId);
                completeEvent.setActionId(actionId);
                completeEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
                completeEvent.setActorId(userId);
                completeEvent.setComment(comment);
                completeEvent.setContext(taskContext);
                completeEvent.setOperationId(UUID.randomUUID().toString());
                completeEvent.setTaskId(taskId);
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
            }
            catch (Exception e) {
                throw new BpmException(e);
            }
        }
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(businessKey.getFormSchemeKey());
        HashSet<BusinessKey> businessKeys = new HashSet<BusinessKey>();
        businessKeys.add(businessKey);
        if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(businessKeys, workflowStartType, taskId);
        }
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void completeProcessTask(BusinessKey businessKey, String taskId, String userId, String actionId, String comment, TaskContext taskContext, Map<String, Object> variables) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
        this.completeProcessTask(businessKey, taskId, userId, actionId, comment, taskContext);
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void batchCompleteTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String comment, TaskContext context, boolean continueIfFailed, Map<String, Object> variables) {
        Assert.notNull((Object)businessKeySet, (String)"businessKeySet is must  be not null!");
        Assert.notNull((Object)actionId, (String)"actionId is must  be not null!");
        String taskId = (String)context.get(this.nrParameterUtils.getTaskIdMapKey());
        this.batchCompleteProcessTasks(businessKeySet, actor, actionId, taskId, comment, context);
    }

    @Override
    public boolean isTaskActorAllTrue(Task task, BusinessKey businessKey, Actor actor) {
        Assert.notNull((Object)businessKey, (String)"businessKey is must  be not null!");
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
    @Transactional(rollbackFor={BpmException.class})
    public void batchCompleteProcessTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String taskId, String comment, TaskContext context) {
        Assert.notNull((Object)businessKeySet, (String)"parameter 'businessKeySet' must not be null.");
        Assert.notNull((Object)actor, (String)"parameter 'actor' must not be null.");
        Assert.notNull((Object)actionId, (String)"parameter 'actionId' must not be null.");
        Assert.notNull((Object)taskId, (String)"parameter 'taskId' must not be null.");
        String operationId = UUID.randomUUID().toString();
        if (context == null) {
            context = new ConcurrentTaskContext();
        }
        boolean isForceUpload = false;
        if (this.actionEventHandler.isPresent()) {
            UserActionBatchPrepareEventImpl prepareEvent = new UserActionBatchPrepareEventImpl();
            prepareEvent.setActionId(actionId);
            prepareEvent.setBusinessKeySet(businessKeySet);
            prepareEvent.setActor(actor);
            prepareEvent.setComment(comment);
            prepareEvent.setContext(context);
            prepareEvent.setOperationId(operationId);
            prepareEvent.setCorporateValue(this.nrParameterUtils.getCorporateValue(businessKeySet.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionBatchPrepare(prepareEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
            if (prepareEvent.isSetBreak()) {
                throw new UserActionException(actionId, prepareEvent.getBreakMessage());
            }
        }
        List<MasterEntityInfo> completedEntity = null;
        Exception exception = null;
        try {
            if (context.get(this.nrParameterUtils.getForceMapKey()) != null) {
                isForceUpload = (Boolean)context.get(this.nrParameterUtils.getForceMapKey());
            }
            completedEntity = this.internalBatchCompleteTasks(businessKeySet, actionId, taskId, comment, context, operationId, actor, isForceUpload);
        }
        catch (Exception e) {
            exception = e;
        }
        if (this.actionEventHandler.isPresent()) {
            UserActionBatchCompleteEventImpl batchCompleteEvent = new UserActionBatchCompleteEventImpl();
            batchCompleteEvent.setActionId(actionId);
            batchCompleteEvent.setBusinessKeySet(businessKeySet);
            batchCompleteEvent.setActor(actor);
            batchCompleteEvent.setComment(comment);
            batchCompleteEvent.setContext(context);
            batchCompleteEvent.setCompletedMasterEntities(completedEntity);
            batchCompleteEvent.setException(exception);
            batchCompleteEvent.setOperationId(operationId);
            batchCompleteEvent.setUserTaskId(taskId);
            batchCompleteEvent.setTaskId(taskId);
            batchCompleteEvent.setCorporateValue(this.nrParameterUtils.getCorporateValue(businessKeySet.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionBatchComplete(batchCompleteEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void batchCompleteProcessTasks(BusinessKeySet businessKeySet, Actor actor, String actionId, String taskId, String comment, TaskContext context, Map<String, Object> variables) {
        Assert.notNull((Object)businessKeySet, (String)"parameter 'businessKeySet' must not be null.");
        Assert.notNull((Object)actor, (String)"parameter 'actor' must not be null.");
        Assert.notNull((Object)actionId, (String)"parameter 'actionId' must not be null.");
        Assert.notNull((Object)taskId, (String)"parameter 'taskId' must not be null.");
        String operationId = UUID.randomUUID().toString();
        if (context == null) {
            context = new ConcurrentTaskContext();
        }
        boolean isForceUpload = false;
        if (this.actionEventHandler.isPresent()) {
            UserActionBatchPrepareEventImpl prepareEvent = new UserActionBatchPrepareEventImpl();
            prepareEvent.setActionId(actionId);
            prepareEvent.setBusinessKeySet(businessKeySet);
            prepareEvent.setActor(actor);
            prepareEvent.setComment(comment);
            prepareEvent.setContext(context);
            prepareEvent.setOperationId(operationId);
            prepareEvent.setCorporateValue(this.nrParameterUtils.getCorporateValue(businessKeySet.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionBatchPrepare(prepareEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
            if (prepareEvent.isSetBreak()) {
                throw new UserActionException(actionId, prepareEvent.getBreakMessage());
            }
        }
        List<MasterEntityInfo> completedEntity = null;
        Exception exception = null;
        try {
            if (context.get(this.nrParameterUtils.getForceMapKey()) != null) {
                isForceUpload = (Boolean)context.get(this.nrParameterUtils.getForceMapKey());
            }
            completedEntity = this.internalBatchCompleteTasks(businessKeySet, actionId, taskId, comment, context, operationId, actor, isForceUpload);
        }
        catch (Exception e) {
            exception = e;
        }
        if (this.actionEventHandler.isPresent()) {
            UserActionBatchCompleteEventImpl batchCompleteEvent = new UserActionBatchCompleteEventImpl();
            batchCompleteEvent.setActionId(actionId);
            batchCompleteEvent.setBusinessKeySet(businessKeySet);
            batchCompleteEvent.setActor(actor);
            batchCompleteEvent.setComment(comment);
            batchCompleteEvent.setContext(context);
            batchCompleteEvent.setCompletedMasterEntities(completedEntity);
            batchCompleteEvent.setException(exception);
            batchCompleteEvent.setOperationId(operationId);
            batchCompleteEvent.setUserTaskId(taskId);
            batchCompleteEvent.setTaskId(taskId);
            batchCompleteEvent.setCorporateValue(this.nrParameterUtils.getCorporateValue(businessKeySet.getFormSchemeKey()));
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionBatchComplete(batchCompleteEvent);
            }
            catch (Exception e) {
                throw new UserActionException(actionId, e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void retrieveTask(Task currentTask, UserTask targetTask, String preEvent, BusinessKey businessKey) {
        Assert.notNull((Object)currentTask, (String)"currentTask is must not be null!");
        Assert.notNull((Object)targetTask, (String)"targetTask is must not be null!");
        Assert.notNull((Object)businessKey, (String)"businessKey is must not be null!");
        Actor candicateActor = Actor.fromNpContext();
        String operationId = UUID.randomUUID().toString();
        ConcurrentTaskContext context = new ConcurrentTaskContext();
        if (this.actionEventHandler.isPresent()) {
            UserActionPrepareEventImpl prepareEvent = new UserActionPrepareEventImpl();
            prepareEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            prepareEvent.setUserTaskId(currentTask.getUserTaskId());
            prepareEvent.setActionId("act_retrieve");
            prepareEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            prepareEvent.setActorId(candicateActor.getIdentityId());
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
        this.revertProcessTask(targetTask.getId(), preEvent, businessKey);
        if (this.actionEventHandler.isPresent()) {
            UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
            completeEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            completeEvent.setUserTaskId(currentTask.getUserTaskId());
            completeEvent.setActionId("act_retrieve");
            completeEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            completeEvent.setActorId(candicateActor.getIdentityId());
            completeEvent.setComment(null);
            completeEvent.setContext(context);
            completeEvent.setOperationId(operationId);
            completeEvent.setTaskId(currentTask.getUserTaskId());
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionComplete(completeEvent);
            }
            catch (Exception e) {
                throw new UserActionException("act_retrieve", e);
            }
        }
    }

    private void revertProcessTask(String targetTaskId, String preEvent, BusinessKey businessKey) {
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        if (processTaskBuilder.isPresent()) {
            ProcessTaskCreateEvent taskCreateEvent = new ProcessTaskCreateEvent();
            taskCreateEvent.setTaskId(targetTaskId);
            taskCreateEvent.setBusinessKey(businessKey);
            taskCreateEvent.setForceUpload(false);
            taskCreateEvent.setActionId(preEvent);
            if (this.actionEventHandler.isPresent()) {
                try {
                    ((EventDispatcher)this.actionEventHandler.get()).onCreateTask(taskCreateEvent);
                }
                catch (Exception e) {
                    throw new BpmException(e);
                }
            }
        }
    }

    @Override
    public boolean isTaskActor(UserTask currentTask, BusinessKey businessKey, Task task) {
        Assert.notNull((Object)businessKey, (String)"parameter 'businessKey' must not be null.");
        Actor candicateActor = Actor.fromNpContext();
        return candicateActor != null && ActorUtils.isTaskActor(currentTask, businessKey, candicateActor, this.actorStrategyProvider, task);
    }

    @Override
    public Optional<UserTask> getTargetTaskById(Task currTask, String taskId, BusinessKey businessKey) {
        Assert.notNull((Object)businessKey, (String)"parameter 'businessKey' must not be null.");
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        return processTaskBuilder.get().queryUserTask(taskId, businessKey.getFormSchemeKey());
    }

    private List<MasterEntityInfo> internalBatchCompleteTasks(BusinessKeySet businessKeySet, String actionId, String taskId, String comment, TaskContext context, String operationId, Actor actor, boolean isForceUpload) {
        List<Object> completeEntity = new ArrayList();
        MasterEntitySet masterEntitySet = businessKeySet.getMasterEntitySet();
        BusinessKeyImpl businessKey = new BusinessKeyImpl();
        if (masterEntitySet.next()) {
            businessKey.setMasterEntity(masterEntitySet.getCurrent());
        }
        masterEntitySet.reset();
        businessKey.setFormSchemeKey(businessKeySet.getFormSchemeKey());
        businessKey.setPeriod(businessKeySet.getPeriod());
        if (businessKeySet.getFormKey() != null) {
            businessKey.setFormKey(businessKeySet.getFormKey().stream().findAny().orElse(null));
        }
        UserActionProgressEventImpl progressEvent = new UserActionProgressEventImpl();
        progressEvent.setActionId(actionId);
        progressEvent.setBusinessKeySet(businessKeySet);
        progressEvent.setActor(actor);
        progressEvent.setComment(comment);
        progressEvent.setContext(context);
        progressEvent.setOperationId(operationId);
        progressEvent.setCorporateValue(this.nrParameterUtils.getCorporateValue(businessKeySet.getFormSchemeKey()));
        Optional<ProcessTaskBuilder> processTaskBuilder = this.getProcessTaskBuilderByType(this.processType);
        BatchCompleteParam batchCompleteParam = new BatchCompleteParam();
        batchCompleteParam.setActionId(actionId);
        batchCompleteParam.setForceUpload(isForceUpload);
        batchCompleteParam.setTaskId(processTaskBuilder.get().nextUserTaskId(taskId, actionId, BusinessKeyFormatter.formatToString(businessKey)));
        Set<String> formKeys = businessKeySet.getFormKey();
        if (!CollectionUtils.isEmpty(formKeys) && !formKeys.contains("00000000-0000-0000-0000-000000000000")) {
            progressEvent.setTotalSteps(formKeys.size() * masterEntitySet.size());
            completeEntity = this.batchCompleteFormTask(masterEntitySet, formKeys, businessKeySet, batchCompleteParam, progressEvent);
        } else {
            progressEvent.setTotalSteps(masterEntitySet.size());
            completeEntity = this.batchCompleteEntityTask(masterEntitySet, businessKeySet, batchCompleteParam, progressEvent);
        }
        return completeEntity;
    }

    private List<MasterEntityInfo> batchCompleteFormTask(MasterEntitySet masterEntitySet, Set<String> Keys, BusinessKeySet businessKeySet, BatchCompleteParam batchCompleteParam, UserActionProgressEventImpl progressEvent) {
        ArrayList<MasterEntityInfo> completeEntityInfos = new ArrayList<MasterEntityInfo>();
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        HashSet<BusinessKey> businessKeys = new HashSet<BusinessKey>();
        while (masterEntitySet.next()) {
            MasterEntityInfo masterEntity = masterEntitySet.getCurrent();
            for (String key : Keys) {
                BusinessKeyImpl businessKeyInfo = new BusinessKeyImpl();
                businessKeyInfo.setFormKey(key);
                businessKeyInfo.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                businessKeyInfo.setMasterEntity(masterEntity);
                businessKeyInfo.setPeriod(businessKeySet.getPeriod());
                DimensionValueSet masterKey = this.processUtil.buildUploadMasterKey(businessKeyInfo);
                dimensionValueSets.add(masterKey);
                businessKeys.add(businessKeyInfo);
                progressEvent.doStep();
                if (!this.actionEventHandler.isPresent()) continue;
                try {
                    ((EventDispatcher)this.actionEventHandler.get()).onUserActionProgressChanged(progressEvent);
                }
                catch (Exception e) {
                    throw new UserActionException(batchCompleteParam.getActionId(), e);
                }
            }
            completeEntityInfos.add(masterEntity);
        }
        batchCompleteParam.setMasterKeysList(dimensionValueSets);
        batchCompleteParam.setMasterKey(this.processUtil.buildBatchMasterKey(masterEntitySet, businessKeySet));
        batchCompleteParam.setFormSchemeKey(businessKeySet.getFormSchemeKey());
        this.processStateHistoryDao.batchUpdateState(batchCompleteParam);
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(businessKeySet.getFormSchemeKey());
        if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
            this.nrParameterUtils.updateUnitState(businessKeys, workflowStartType, batchCompleteParam.getTaskId());
        }
        return completeEntityInfos;
    }

    private List<MasterEntityInfo> batchCompleteEntityTask(MasterEntitySet masterEntitySet, BusinessKeySet businessKeySet, BatchCompleteParam batchCompleteParam, UserActionProgressEventImpl progressEvent) {
        ArrayList<MasterEntityInfo> completeEntityInfos = new ArrayList<MasterEntityInfo>();
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        while (masterEntitySet.next()) {
            MasterEntityInfo masterEntity = masterEntitySet.getCurrent();
            BusinessKeyImpl businessKeyInfo = new BusinessKeyImpl();
            Set<String> formKeys = businessKeySet.getFormKey();
            if (formKeys != null && formKeys.size() > 0) {
                for (String formKey : formKeys) {
                    businessKeyInfo.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                    businessKeyInfo.setMasterEntity(masterEntity);
                    businessKeyInfo.setPeriod(businessKeySet.getPeriod());
                    businessKeyInfo.setFormKey(formKey);
                    DimensionValueSet masterKey = this.processUtil.buildUploadMasterKey(businessKeyInfo);
                    dimensionValueSets.add(masterKey);
                    completeEntityInfos.add(masterEntity);
                    progressEvent.doStep();
                    if (!this.actionEventHandler.isPresent()) continue;
                    try {
                        ((EventDispatcher)this.actionEventHandler.get()).onUserActionProgressChanged(progressEvent);
                    }
                    catch (Exception e) {
                        throw new UserActionException(batchCompleteParam.getActionId(), e);
                    }
                }
                continue;
            }
            businessKeyInfo.setFormSchemeKey(businessKeySet.getFormSchemeKey());
            businessKeyInfo.setMasterEntity(masterEntity);
            businessKeyInfo.setPeriod(businessKeySet.getPeriod());
            DimensionValueSet masterKey = this.processUtil.buildUploadMasterKey(businessKeyInfo);
            dimensionValueSets.add(masterKey);
            completeEntityInfos.add(masterEntity);
            progressEvent.doStep();
            if (!this.actionEventHandler.isPresent()) continue;
            try {
                ((EventDispatcher)this.actionEventHandler.get()).onUserActionProgressChanged(progressEvent);
            }
            catch (Exception e) {
                throw new UserActionException(batchCompleteParam.getActionId(), e);
            }
        }
        batchCompleteParam.setMasterKeysList(dimensionValueSets);
        batchCompleteParam.setMasterKey(this.processUtil.buildBatchMasterKey(masterEntitySet, businessKeySet));
        batchCompleteParam.setFormSchemeKey(businessKeySet.getFormSchemeKey());
        this.processStateHistoryDao.batchUpdateState(batchCompleteParam);
        return completeEntityInfos;
    }

    private boolean canStartFlow(String businessKey) {
        Optional<ProcessTaskBuilder> builder = this.getProcessTaskBuilderByType(this.processType);
        if (builder.isPresent()) {
            return builder.get().canStartProcess(businessKey);
        }
        return false;
    }

    private Optional<ProcessTaskBuilder> getProcessTaskBuilderByType(ProcessType processType) {
        return this.processTaskBuilders.stream().filter(e -> e.getProcessType() == processType).findFirst();
    }

    private List<Task> filterTaskByActor(List<Task> tasks, Actor candicateActor, BusinessKey businessKey) {
        ArrayList<Task> newTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            Optional<UserTask> userTask = this.deployService.getUserTask(null, task.getUserTaskId(), businessKey.getFormSchemeKey());
            if (!this.isTaskActor(userTask.get(), businessKey, task)) continue;
            newTasks.add(task);
        }
        return newTasks;
    }

    @Override
    public boolean hasVariable(String executionId, String variableName) {
        return false;
    }

    @Override
    public void removeVariable(String executionId, String variableName) {
    }

    @Override
    @Transactional(rollbackFor={BpmException.class})
    public void retrieveTask(Task currentTask, UserTask targetTask, String preEvent, BusinessKey businessKey, TaskContext context) {
        Assert.notNull((Object)currentTask, (String)"currentTask is must not be null!");
        Assert.notNull((Object)targetTask, (String)"targetTask is must not be null!");
        Assert.notNull((Object)businessKey, (String)"businessKey is must not be null!");
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
            prepareEvent.setActorId(candicateActor.getIdentityId());
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
        this.revertProcessTask(targetTask.getId(), preEvent, businessKey);
        if (this.actionEventHandler.isPresent()) {
            UserActionCompleteEventImpl completeEvent = new UserActionCompleteEventImpl();
            completeEvent.setProcessDefinitionId(currentTask.getProcessDefinitionId());
            completeEvent.setUserTaskId(currentTask.getUserTaskId());
            completeEvent.setActionId("act_retrieve");
            completeEvent.setBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            completeEvent.setActorId(candicateActor.getIdentityId());
            completeEvent.setComment(null);
            completeEvent.setContext(context);
            completeEvent.setOperationId(operationId);
            completeEvent.setTaskId(currentTask.getUserTaskId());
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
        return new HashMap<Task, String>();
    }

    @Override
    public void jumpToTargetNode(String processDefinitionId, String currentTaskId, String jumpToTaskDefinitionId) {
    }
}

