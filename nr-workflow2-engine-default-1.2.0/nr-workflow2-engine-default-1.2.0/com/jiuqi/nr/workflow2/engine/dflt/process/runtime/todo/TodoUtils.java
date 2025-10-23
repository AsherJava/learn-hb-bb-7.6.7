/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.todo.entity.TodoInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoInfoImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.nr.workflow2.todo.event.BeforeApplyreturnTodoSendEvent
 *  com.jiuqi.nr.workflow2.todo.event.TodoSendItem
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.ClearFormSchemeWorkflowTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.ClearTaskWorkflowTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.RemoveInstanceTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.SendTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.SendTodoJobParameter;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoSystemOption;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoInfoImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.nr.workflow2.todo.event.BeforeApplyreturnTodoSendEvent;
import com.jiuqi.nr.workflow2.todo.event.TodoSendItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationEvent;

public class TodoUtils {
    public static void onInstanceStarted(RuntimeBusinessKey rtBusinessKey, ProcessInstanceDO instance, IUserTask starIUserTask) {
        if (!TodoSystemOption.isEnableTodo()) {
            return;
        }
        if (!rtBusinessKey.getWorkflowSettings().isTodoEnable()) {
            return;
        }
        if (!starIUserTask.enableSendTodo()) {
            return;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKey.getTaskKey());
        param.setFormschemeKey(rtBusinessKey.getFormSchemeKey());
        SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
        instanceInfo.setId(instance.getId());
        instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
        instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
        instanceInfo.setCurTaskId(instance.getCurTaskId());
        instanceInfo.setCurNode(instance.getCurNode());
        param.setInstanceInfos(Collections.singletonList(instanceInfo));
        TodoJob job = SendTodoJob.StartInstanceSendTodoJob.createJob(param);
        TodoUtils.commitImmediatelyTodoJob(job);
    }

    public static String onInstanceBatchStarted(RuntimeBusinessKeyCollection rtBusinessKeys, List<ProcessInstanceDO> instances) {
        if (!TodoSystemOption.isEnableTodo()) {
            return null;
        }
        if (!rtBusinessKeys.getWorkflowSettings().isTodoEnable()) {
            return null;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKeys.getTaskKey());
        param.setFormschemeKey(rtBusinessKeys.getFormSchemeKey());
        ArrayList<SendTodoJobParameter.InstanceInfo> instanceInfos = new ArrayList<SendTodoJobParameter.InstanceInfo>(instances.size());
        for (ProcessInstanceDO instance : instances) {
            SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
            instanceInfo.setId(instance.getId());
            instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
            instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
            instanceInfo.setCurTaskId(instance.getCurTaskId());
            instanceInfo.setCurNode(instance.getCurNode());
            instanceInfos.add(instanceInfo);
        }
        param.setInstanceInfos(instanceInfos);
        TodoJob job = SendTodoJob.StartInstanceSendTodoJob.createJob(param);
        return TodoUtils.commitTodoJob(job);
    }

    public static void onTaskCompleted(RuntimeBusinessKey rtBusinessKey, ProcessInstanceDO instance, String orignalTaskId, String orignalUserTask, IUserTask newUserTask, String remark) {
        if (!TodoSystemOption.isEnableTodo()) {
            return;
        }
        if (!rtBusinessKey.getWorkflowSettings().isTodoEnable()) {
            return;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKey.getTaskKey());
        param.setFormschemeKey(rtBusinessKey.getFormSchemeKey());
        SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
        instanceInfo.setId(instance.getId());
        instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
        instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
        instanceInfo.setCurTaskId(instance.getCurTaskId());
        instanceInfo.setCurNode(instance.getCurNode());
        instanceInfo.setOringnalTaskId(orignalTaskId);
        instanceInfo.setOringnalUserTask(orignalUserTask);
        param.setInstanceInfos(Collections.singletonList(instanceInfo));
        param.setRemark(remark);
        TodoJob job = SendTodoJob.CompleteTaskSendTodoJob.createJob(param);
        TodoUtils.commitImmediatelyTodoJob(job);
    }

    public static String onBatchTaskCompleted(RuntimeBusinessKeyCollection rtBusinessKeys, List<ProcessInstanceDO> instances, Map<String, ProcessInstanceDO> orignalInstances, String remark) {
        if (!TodoSystemOption.isEnableTodo()) {
            return null;
        }
        if (!rtBusinessKeys.getWorkflowSettings().isTodoEnable()) {
            return null;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKeys.getTaskKey());
        param.setFormschemeKey(rtBusinessKeys.getFormSchemeKey());
        ArrayList<SendTodoJobParameter.InstanceInfo> instanceInfos = new ArrayList<SendTodoJobParameter.InstanceInfo>(instances.size());
        for (ProcessInstanceDO instance : instances) {
            SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
            instanceInfo.setId(instance.getId());
            instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
            instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
            instanceInfo.setCurTaskId(instance.getCurTaskId());
            instanceInfo.setCurNode(instance.getCurNode());
            ProcessInstanceDO orignalInstance = orignalInstances.get(instance.getId());
            if (orignalInstance != null) {
                instanceInfo.setOringnalTaskId(orignalInstance.getCurTaskId());
                instanceInfo.setOringnalUserTask(orignalInstance.getCurNode());
            }
            instanceInfos.add(instanceInfo);
        }
        param.setInstanceInfos(instanceInfos);
        param.setRemark(remark);
        TodoJob job = SendTodoJob.CompleteTaskSendTodoJob.createJob(param);
        return TodoUtils.commitTodoJob(job);
    }

    public static void onTaskRetrived(RuntimeBusinessKey rtBusinessKey, ProcessInstanceDO instance, String orignalTaskId, String orignalUserTask, IUserTask newUserTask, String remark) {
        if (!TodoSystemOption.isEnableTodo()) {
            return;
        }
        if (!rtBusinessKey.getWorkflowSettings().isTodoEnable()) {
            return;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKey.getTaskKey());
        param.setFormschemeKey(rtBusinessKey.getFormSchemeKey());
        SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
        instanceInfo.setId(instance.getId());
        instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
        instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
        instanceInfo.setCurTaskId(instance.getCurTaskId());
        instanceInfo.setCurNode(instance.getCurNode());
        instanceInfo.setOringnalTaskId(orignalTaskId);
        instanceInfo.setOringnalUserTask(orignalUserTask);
        param.setInstanceInfos(Collections.singletonList(instanceInfo));
        param.setRemark(remark);
        TodoJob job = SendTodoJob.RetriveTaskSendTodoJob.createJob(param);
        TodoUtils.commitImmediatelyTodoJob(job);
    }

    public static String onBatchTaskRetrived(RuntimeBusinessKeyCollection rtBusinessKeys, List<ProcessInstanceDO> instances, Map<String, ProcessInstanceDO> orignalInstances, String remark) {
        if (!TodoSystemOption.isEnableTodo()) {
            return null;
        }
        if (!rtBusinessKeys.getWorkflowSettings().isTodoEnable()) {
            return null;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKeys.getTaskKey());
        param.setFormschemeKey(rtBusinessKeys.getFormSchemeKey());
        ArrayList<SendTodoJobParameter.InstanceInfo> instanceInfos = new ArrayList<SendTodoJobParameter.InstanceInfo>(instances.size());
        for (ProcessInstanceDO instance : instances) {
            SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
            instanceInfo.setId(instance.getId());
            instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
            instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
            instanceInfo.setCurTaskId(instance.getCurTaskId());
            instanceInfo.setCurNode(instance.getCurNode());
            ProcessInstanceDO orignalInstance = orignalInstances.get(instance.getId());
            if (orignalInstance != null) {
                instanceInfo.setOringnalTaskId(orignalInstance.getCurTaskId());
                instanceInfo.setOringnalUserTask(orignalInstance.getCurNode());
            }
            instanceInfos.add(instanceInfo);
        }
        param.setInstanceInfos(instanceInfos);
        param.setRemark(remark);
        TodoJob job = SendTodoJob.RetriveTaskSendTodoJob.createJob(param);
        return TodoUtils.commitTodoJob(job);
    }

    public static void onInstanceRefresh(RuntimeBusinessKey rtBusinessKey, ProcessInstanceDO instance) {
        if (!TodoSystemOption.isEnableTodo()) {
            return;
        }
        if (!rtBusinessKey.getWorkflowSettings().isTodoEnable()) {
            return;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKey.getTaskKey());
        param.setFormschemeKey(rtBusinessKey.getFormSchemeKey());
        SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
        instanceInfo.setId(instance.getId());
        instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
        instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
        instanceInfo.setCurTaskId(instance.getCurTaskId());
        instanceInfo.setCurNode(instance.getCurNode());
        instanceInfo.setOringnalTaskId(instance.getCurTaskId());
        param.setInstanceInfos(Collections.singletonList(instanceInfo));
        TodoJob job = SendTodoJob.RefreshInstanceSendTodoJob.createJob(param);
        TodoUtils.commitImmediatelyTodoJob(job);
    }

    public static String onBatchInstanceRefresh(RuntimeBusinessKeyCollection rtBusinessKeys, Collection<ProcessInstanceDO> instances) {
        if (!TodoSystemOption.isEnableTodo()) {
            return null;
        }
        if (!rtBusinessKeys.getWorkflowSettings().isTodoEnable()) {
            return null;
        }
        SendTodoJobParameter param = new SendTodoJobParameter();
        param.setTaskKey(rtBusinessKeys.getTaskKey());
        param.setFormschemeKey(rtBusinessKeys.getFormSchemeKey());
        ArrayList<SendTodoJobParameter.InstanceInfo> instanceInfos = new ArrayList<SendTodoJobParameter.InstanceInfo>(instances.size());
        for (ProcessInstanceDO instance : instances) {
            SendTodoJobParameter.InstanceInfo instanceInfo = new SendTodoJobParameter.InstanceInfo();
            instanceInfo.setId(instance.getId());
            instanceInfo.setBusinessObject(instance.getBusinessKey().getBusinessObject());
            instanceInfo.setProcessDefinitionId(instance.getProcessDefinitionId());
            instanceInfo.setCurTaskId(instance.getCurTaskId());
            instanceInfo.setCurNode(instance.getCurNode());
            instanceInfo.setOringnalTaskId(instance.getCurTaskId());
            instanceInfos.add(instanceInfo);
        }
        param.setInstanceInfos(instanceInfos);
        TodoJob job = SendTodoJob.RefreshInstanceSendTodoJob.createJob(param);
        return TodoUtils.commitTodoJob(job);
    }

    public static void onInstanceDeleted(String instanceId) {
        TodoJob job = RemoveInstanceTodoJob.createJob(Collections.singletonList(instanceId));
        TodoUtils.commitImmediatelyTodoJob(job);
    }

    public static String onBatchInstanceDeleted(List<String> instanceIds) {
        if (instanceIds.isEmpty()) {
            return null;
        }
        TodoJob job = RemoveInstanceTodoJob.createJob(instanceIds);
        return TodoUtils.commitTodoJob(job);
    }

    public static String clearTodoByTask(String taskKey) {
        TodoJob job = ClearTaskWorkflowTodoJob.createJob(taskKey);
        return TodoUtils.commitTodoJob(job);
    }

    public static String clearTodoByFormScheme(String formschemeKey) {
        TodoJob job = ClearFormSchemeWorkflowTodoJob.createJob(formschemeKey);
        return TodoUtils.commitTodoJob(job);
    }

    public static void onApplyReturn(RuntimeBusinessKey rtBusinessKey, ProcessInstanceDO instance, String remark) {
        TodoInfoImpl todo = new TodoInfoImpl();
        todo.setWorkflowInstance(instance.getId());
        todo.setWorkflowNode(TodoNodeType.REQUEST_REJECT.name());
        todo.setWorkflowNodeTask(TodoNodeType.REQUEST_REJECT.name());
        todo.setBusinessTitle("");
        todo.setTaskId(rtBusinessKey.getTaskKey());
        todo.setFormSchemeKey(rtBusinessKey.getFormSchemeKey());
        todo.setPeriod((String)instance.getBusinessKey().getBusinessObject().getDimensions().getPeriodDimensionValue().getValue());
        UserTask userTask = (UserTask)TodoBeanUtils.getProcessDefinitionService().getUserTask(instance.getProcessDefinitionId(), instance.getCurNode());
        List<IActorStrategy> reciverStrategies = rtBusinessKey.getWorkflowSettings().isTodoEnable() ? userTask.getTodoReceivers() : userTask.getActionExecutors();
        todo.setParticipants(ActorStrategyUtil.getInstance().getActors(reciverStrategies, rtBusinessKey));
        todo.setRemark(remark);
        TodoSendItem todoItem = new TodoSendItem(rtBusinessKey.getBusinessKey().getBusinessObject(), (TodoInfo)todo);
        List<TodoSendItem> todoItems = Collections.singletonList(todoItem);
        BeforeApplyreturnTodoSendEvent event = new BeforeApplyreturnTodoSendEvent(todoItems, rtBusinessKey.getTask(), rtBusinessKey.getFormScheme(), todoItems);
        TodoBeanUtils.getApplicationEventPublisher().publishEvent((ApplicationEvent)event);
        if (todoItem.isCanceled()) {
            return;
        }
        TodoBeanUtils.getTodoManipulationService().createUpdateTodo((TodoInfo)todo);
    }

    private static String commitTodoJob(TodoJob job) {
        try {
            return RealTimeJobRunnerFactory.getInstance().getDefaultRunner().commit((AbstractRealTimeJob)job);
        }
        catch (JobsException e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u63d0\u4ea4\u5f85\u529e\u4e8b\u9879\u4efb\u52a1\u9519\u8bef\u3002");
        }
    }

    private static String commitImmediatelyTodoJob(TodoJob job) {
        try {
            return ImmediatelyJobRunner.getInstance().commit((AbstractRealTimeJob)job);
        }
        catch (JobExecutionException e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u63d0\u4ea4\u5f85\u529e\u4e8b\u9879\u4efb\u52a1\u9519\u8bef\u3002");
        }
    }
}

