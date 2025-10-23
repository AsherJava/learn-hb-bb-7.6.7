/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo
 *  com.jiuqi.nr.workflow2.todo.entity.TodoInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoDeleteInfoImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoInfoImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.nr.workflow2.todo.event.BeforeTodoSendEvent
 *  com.jiuqi.nr.workflow2.todo.event.TodoConsumeEvent
 *  com.jiuqi.nr.workflow2.todo.event.TodoConsumeItem
 *  com.jiuqi.nr.workflow2.todo.event.TodoDeleteEvent
 *  com.jiuqi.nr.workflow2.todo.event.TodoItem
 *  com.jiuqi.nr.workflow2.todo.event.TodoSendEvent
 *  com.jiuqi.nr.workflow2.todo.event.TodoSendItem
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.SendTodoJobParameter;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoDeleteInfoImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoInfoImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.nr.workflow2.todo.event.BeforeTodoSendEvent;
import com.jiuqi.nr.workflow2.todo.event.TodoConsumeEvent;
import com.jiuqi.nr.workflow2.todo.event.TodoConsumeItem;
import com.jiuqi.nr.workflow2.todo.event.TodoDeleteEvent;
import com.jiuqi.nr.workflow2.todo.event.TodoItem;
import com.jiuqi.nr.workflow2.todo.event.TodoSendEvent;
import com.jiuqi.nr.workflow2.todo.event.TodoSendItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public abstract class SendTodoJob {
    protected SendTodoJobParameter todoSendParam;
    private WorkflowSettingsDO workflowSettings;
    private TaskDefine task;
    private FormSchemeDefine formScheme;
    protected IProgressMonitor monitor;
    protected Logger logger;

    public SendTodoJob(JobContext context) {
        this.parseParameter(context);
        this.monitor = context.getMonitor();
        this.logger = context.getDefaultLogger();
    }

    protected void parseParameter(JobContext context) {
        String paramJson = context.getParameterValue("OPT_INFO");
        try {
            this.todoSendParam = (SendTodoJobParameter)TodoJob.OBJECTMAPPER.readValue(paramJson, SendTodoJobParameter.class);
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u5f85\u529e\u4e8b\u9879\u5bf9\u8c61\u53cd\u5e8f\u5217\u5316\u9519\u8bef\u3002");
        }
        this.workflowSettings = TodoBeanUtils.getWorkflowSettingsService().queryWorkflowSettings(this.todoSendParam.getTaskKey());
        if (this.workflowSettings == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u4efb\u52a1\u6d41\u7a0b\u8bbe\u7f6e\u4e0d\u5b58\u5728\u3002\u4efb\u52a1\uff1a" + this.todoSendParam.getTaskKey());
        }
        this.task = TodoBeanUtils.getViewController().getTask(this.todoSendParam.getTaskKey());
        if (this.task == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u4efb\u52a1\u4e0d\u5b58\u5728\u3002\u4efb\u52a1\uff1a" + this.todoSendParam.getTaskKey());
        }
        this.formScheme = TodoBeanUtils.getViewController().getFormScheme(this.todoSendParam.getFormschemeKey());
        if (this.formScheme == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\u3002\u62a5\u8868\u65b9\u6848\uff1a" + this.todoSendParam.getFormschemeKey());
        }
    }

    protected void removeOrignalTodoByProcessInstance() {
        String taskName = "nr_wf2_todo_removetodo";
        this.monitor.startTask("nr_wf2_todo_removetodo", new int[]{50, 50});
        ArrayList<String> todoRemoveInfos = new ArrayList<String>();
        for (SendTodoJobParameter.InstanceInfo instance : this.todoSendParam.getInstanceInfos()) {
            todoRemoveInfos.add(instance.getId());
        }
        if (todoRemoveInfos.isEmpty()) {
            this.monitor.finishTask("nr_wf2_todo_removetodo");
            return;
        }
        this.logger.info("\u5f00\u59cb\u5220\u9664\u5f85\u529e");
        TodoBeanUtils.getTodoManipulationService().batchClearTodo(todoRemoveInfos);
        this.monitor.stepIn();
        this.logger.info("\u89e6\u53d1\u5f85\u529e\u5220\u9664\u540e\u4e8b\u4ef6");
        TodoDeleteEvent consumeEvent = new TodoDeleteEvent(todoRemoveInfos, todoRemoveInfos);
        TodoBeanUtils.getApplicationEventPublisher().publishEvent((ApplicationEvent)consumeEvent);
        this.monitor.stepIn();
        this.monitor.finishTask("nr_wf2_todo_removetodo");
    }

    protected void removeOrignalTodoByProcessTask() {
        String taskName = "nr_wf2_todo_removetodo";
        this.monitor.startTask("nr_wf2_todo_removetodo", new int[]{50, 50});
        ArrayList<TodoDeleteInfoImpl> todoRemoveInfos = new ArrayList<TodoDeleteInfoImpl>();
        for (SendTodoJobParameter.InstanceInfo instance : this.todoSendParam.getInstanceInfos()) {
            if (StringUtils.isEmpty(instance.getOringnalTaskId())) continue;
            TodoDeleteInfoImpl todoInfo = new TodoDeleteInfoImpl();
            todoInfo.setWorkflowInstance(instance.getId());
            todoInfo.setWorkflowNodeTask(instance.getOringnalTaskId());
            todoRemoveInfos.add(todoInfo);
        }
        if (todoRemoveInfos.isEmpty()) {
            this.monitor.finishTask("nr_wf2_todo_removetodo");
            return;
        }
        this.logger.info("\u5f00\u59cb\u5220\u9664\u5f85\u529e");
        TodoBeanUtils.getTodoManipulationService().batchDeleteTodo(todoRemoveInfos);
        this.monitor.stepIn();
        this.monitor.stepIn();
        this.monitor.finishTask("nr_wf2_todo_removetodo");
    }

    protected void consumeOrignalTodo() {
        String taskName = "nr_wf2_todo_consumetodo";
        this.monitor.startTask("nr_wf2_todo_consumetodo", new int[]{50, 50});
        ArrayList<TodoConsumeInfoImpl> todoConsumeInfos = new ArrayList<TodoConsumeInfoImpl>();
        ArrayList<TodoConsumeItem> todoConsumeItems = new ArrayList<TodoConsumeItem>();
        for (SendTodoJobParameter.InstanceInfo instance : this.todoSendParam.getInstanceInfos()) {
            if (StringUtils.isEmpty(instance.getOringnalTaskId())) continue;
            TodoConsumeInfoImpl consumeInfo = new TodoConsumeInfoImpl();
            consumeInfo.setWorkflowInstance(instance.getId());
            consumeInfo.setWorkflowNodeTask(instance.getOringnalTaskId());
            todoConsumeInfos.add(consumeInfo);
            todoConsumeItems.add(new TodoConsumeItem(instance.getBusinessObject(), (TodoConsumeInfo)consumeInfo));
            if (!"tsk_audit".equals(instance.getOringnalUserTask())) continue;
            consumeInfo = new TodoConsumeInfoImpl();
            consumeInfo.setWorkflowInstance(instance.getId());
            consumeInfo.setWorkflowNodeTask(TodoNodeType.REQUEST_REJECT.name());
            todoConsumeInfos.add(consumeInfo);
        }
        if (todoConsumeInfos.isEmpty()) {
            this.monitor.finishTask("nr_wf2_todo_consumetodo");
            return;
        }
        this.logger.info("\u5f00\u59cb\u6d88\u8d39\u5f85\u529e");
        TodoBeanUtils.getTodoManipulationService().batchConsumeTodo(todoConsumeInfos);
        this.monitor.stepIn();
        this.logger.info("\u89e6\u53d1\u5f85\u529e\u6d88\u8d39\u540e\u4e8b\u4ef6");
        TodoConsumeEvent consumeEvent = new TodoConsumeEvent(todoConsumeItems, this.task, this.formScheme, todoConsumeItems);
        TodoBeanUtils.getApplicationEventPublisher().publishEvent((ApplicationEvent)consumeEvent);
        this.monitor.stepIn();
        this.monitor.finishTask("nr_wf2_todo_consumetodo");
    }

    protected void consumeOrignalTodoByDelete() {
        String taskName = "nr_wf2_todo_removetodo";
        this.monitor.startTask("nr_wf2_todo_removetodo", new int[]{50, 50});
        ArrayList<TodoDeleteInfoImpl> todoRemoveInfos = new ArrayList<TodoDeleteInfoImpl>();
        for (SendTodoJobParameter.InstanceInfo instance : this.todoSendParam.getInstanceInfos()) {
            if (StringUtils.isEmpty(instance.getOringnalTaskId())) continue;
            TodoDeleteInfoImpl todoInfo = new TodoDeleteInfoImpl();
            todoInfo.setWorkflowInstance(instance.getId());
            todoInfo.setWorkflowNodeTask(instance.getOringnalTaskId());
            todoRemoveInfos.add(todoInfo);
            if (!"tsk_audit".equals(instance.getOringnalUserTask())) continue;
            todoInfo = new TodoDeleteInfoImpl();
            todoInfo.setWorkflowInstance(instance.getId());
            todoInfo.setWorkflowNodeTask(TodoNodeType.REQUEST_REJECT.name());
            todoRemoveInfos.add(todoInfo);
        }
        if (todoRemoveInfos.isEmpty()) {
            this.monitor.finishTask("nr_wf2_todo_removetodo");
            return;
        }
        this.logger.info("\u5f00\u59cb\u5220\u9664\u5f85\u529e");
        TodoBeanUtils.getTodoManipulationService().batchDeleteTodo(todoRemoveInfos);
        this.monitor.stepIn();
        this.monitor.stepIn();
        this.monitor.finishTask("nr_wf2_todo_removetodo");
    }

    protected void sendNewTodo() {
        String taskName = "nr_wf2_todo_sendtodo";
        String calculateTaskName = "calculate_participant";
        this.monitor.startTask("nr_wf2_todo_sendtodo", new int[]{50, 10, 30, 10});
        this.monitor.startTask("calculate_participant", this.todoSendParam.getInstanceInfos().size());
        this.logger.info("\u5f00\u59cb\u8ba1\u7b97\u53c2\u4e0e\u8005");
        ArrayList<TodoSendItem> todoSendItems = new ArrayList<TodoSendItem>();
        for (SendTodoJobParameter.InstanceInfo instance : this.todoSendParam.getInstanceInfos()) {
            if (this.monitor.isCanceled()) {
                return;
            }
            if (!StringUtils.isEmpty(instance.getCurTaskId())) {
                TodoInfoImpl todoInfo = new TodoInfoImpl();
                todoInfo.setWorkflowInstance(instance.getId());
                todoInfo.setWorkflowNode(instance.getCurNode());
                todoInfo.setWorkflowNodeTask(instance.getCurTaskId());
                todoInfo.setTaskId(this.todoSendParam.getTaskKey());
                todoInfo.setFormSchemeKey(this.todoSendParam.getFormschemeKey());
                todoInfo.setPeriod((String)instance.getBusinessObject().getDimensions().getPeriodDimensionValue().getValue());
                BusinessKey businessKey = new BusinessKey(this.todoSendParam.getTaskKey(), instance.getBusinessObject());
                RuntimeBusinessKey rtBusinessKey = new RuntimeBusinessKey((IBusinessKey)businessKey, this.task, this.formScheme, this.workflowSettings);
                UserTask userTask = (UserTask)TodoBeanUtils.getProcessDefinitionService().getUserTask(instance.getProcessDefinitionId(), instance.getCurNode());
                if (userTask.enableSendTodo()) {
                    List<String> participants = ActorStrategyUtil.getInstance().getActors(userTask.getTodoReceivers(), rtBusinessKey);
                    todoInfo.setParticipants(participants);
                    todoInfo.setRemark(this.todoSendParam.getRemark());
                    todoSendItems.add(new TodoSendItem(instance.getBusinessObject(), (TodoInfo)todoInfo));
                }
            }
            this.monitor.stepIn();
        }
        this.monitor.finishTask("calculate_participant");
        this.monitor.stepIn();
        if (this.monitor.isCanceled()) {
            return;
        }
        if (todoSendItems.isEmpty()) {
            this.monitor.finishTask("nr_wf2_todo_sendtodo");
            return;
        }
        this.logger.info("\u89e6\u53d1\u5f85\u529e\u53d1\u9001\u524d\u4e8b\u4ef6");
        BeforeTodoSendEvent beforeSendEvent = new BeforeTodoSendEvent(todoSendItems, this.task, this.formScheme, todoSendItems);
        TodoBeanUtils.getApplicationEventPublisher().publishEvent((ApplicationEvent)beforeSendEvent);
        ArrayList<TodoInfo> todoInfos = new ArrayList<TodoInfo>();
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>(todoSendItems.size());
        for (TodoSendItem todoSendItem : beforeSendEvent.todoItems()) {
            if (todoSendItem.isCanceled()) continue;
            todoInfos.add(todoSendItem.getTodoInfo());
            todoItems.add(new TodoItem(todoSendItem.getBusinessObject(), todoSendItem.getTodoInfo()));
        }
        this.monitor.stepIn();
        if (todoInfos.isEmpty()) {
            this.monitor.finishTask("nr_wf2_todo_sendtodo");
            return;
        }
        this.logger.info("\u5f00\u59cb\u53d1\u9001\u5f85\u529e");
        TodoBeanUtils.getTodoManipulationService().batchCreateTodo(todoInfos);
        this.monitor.stepIn();
        this.logger.info("\u89e6\u53d1\u5f85\u529e\u53d1\u9001\u540e\u4e8b\u4ef6");
        TodoSendEvent todoSendEvent = new TodoSendEvent(todoItems, this.task, this.formScheme, todoItems);
        TodoBeanUtils.getApplicationEventPublisher().publishEvent((ApplicationEvent)todoSendEvent);
        this.monitor.stepIn();
        this.monitor.finishTask("nr_wf2_todo_sendtodo");
    }

    public static class RefreshInstanceSendTodoJob
    extends SendTodoJob {
        public RefreshInstanceSendTodoJob(JobContext context) {
            super(context);
        }

        public static TodoJob createJob(SendTodoJobParameter param) {
            return TodoJob.createTodoJob(TodoJob.OperationType.REFREASH_INSTANCE, param);
        }

        public void execute() throws JobExecutionException {
            this.logger.info("\u63a5\u6536\u53c2\u6570\u5b8c\u6210\uff0c\u64cd\u4f5c\u7c7b\u578b\uff1a" + TodoJob.OperationType.REFREASH_INSTANCE.name() + "\uff0c\u6d41\u7a0b\u5b9e\u4f8b\u6570\uff1a" + this.todoSendParam.getInstanceInfos().size());
            if (this.todoSendParam.getInstanceInfos().isEmpty()) {
                return;
            }
            String taskName = "nr_wf2_todo";
            this.monitor.startTask("nr_wf2_todo", new int[]{20, 80});
            super.removeOrignalTodoByProcessInstance();
            this.monitor.stepIn();
            super.sendNewTodo();
            this.monitor.stepIn();
            this.monitor.finishTask("nr_wf2_todo");
        }
    }

    public static class RetriveTaskSendTodoJob
    extends SendTodoJob {
        public static TodoJob createJob(SendTodoJobParameter param) {
            return TodoJob.createTodoJob(TodoJob.OperationType.RETRIVE_TASK, param);
        }

        public RetriveTaskSendTodoJob(JobContext context) {
            super(context);
        }

        public void execute() throws JobExecutionException {
            this.logger.info("\u63a5\u6536\u53c2\u6570\u5b8c\u6210\uff0c\u64cd\u4f5c\u7c7b\u578b\uff1a" + TodoJob.OperationType.RETRIVE_TASK.name() + "\uff0c\u6d41\u7a0b\u5b9e\u4f8b\u6570\uff1a" + this.todoSendParam.getInstanceInfos().size());
            if (this.todoSendParam.getInstanceInfos().isEmpty()) {
                return;
            }
            String taskName = "nr_wf2_todo";
            this.monitor.startTask("nr_wf2_todo", new int[]{20, 80});
            super.removeOrignalTodoByProcessTask();
            this.monitor.stepIn();
            super.sendNewTodo();
            this.monitor.stepIn();
            this.monitor.finishTask("nr_wf2_todo");
        }
    }

    public static class CompleteTaskSendTodoJob
    extends SendTodoJob {
        public static TodoJob createJob(SendTodoJobParameter param) {
            return TodoJob.createTodoJob(TodoJob.OperationType.COMPLETE_TASK, param);
        }

        public CompleteTaskSendTodoJob(JobContext context) {
            super(context);
        }

        public void execute() throws JobExecutionException {
            this.logger.info("\u63a5\u6536\u53c2\u6570\u5b8c\u6210\uff0c\u64cd\u4f5c\u7c7b\u578b\uff1a" + TodoJob.OperationType.COMPLETE_TASK.name() + "\uff0c\u6d41\u7a0b\u5b9e\u4f8b\u6570\uff1a" + this.todoSendParam.getInstanceInfos().size());
            if (this.todoSendParam.getInstanceInfos().isEmpty()) {
                return;
            }
            String taskName = "nr_wf2_todo";
            this.monitor.startTask("nr_wf2_todo", new int[]{20, 80});
            super.consumeOrignalTodoByDelete();
            this.monitor.stepIn();
            super.sendNewTodo();
            this.monitor.stepIn();
            this.monitor.finishTask("nr_wf2_todo");
        }
    }

    public static class StartInstanceSendTodoJob
    extends SendTodoJob {
        public static TodoJob createJob(SendTodoJobParameter param) {
            return TodoJob.createTodoJob(TodoJob.OperationType.START_INSTANCE, param);
        }

        public StartInstanceSendTodoJob(JobContext context) {
            super(context);
        }

        public void execute() throws JobExecutionException {
            this.logger.info("\u63a5\u6536\u53c2\u6570\u5b8c\u6210\uff0c\u64cd\u4f5c\u7c7b\u578b\uff1a" + TodoJob.OperationType.START_INSTANCE.name() + "\uff0c\u6d41\u7a0b\u5b9e\u4f8b\u6570\uff1a" + this.todoSendParam.getInstanceInfos().size());
            if (this.todoSendParam.getInstanceInfos().isEmpty()) {
                return;
            }
            super.sendNewTodo();
        }
    }
}

