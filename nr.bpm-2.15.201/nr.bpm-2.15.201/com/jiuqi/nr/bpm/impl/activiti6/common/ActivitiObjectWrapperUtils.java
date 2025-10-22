/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.bpmn.model.FlowElement
 *  org.activiti.engine.TaskService
 *  org.activiti.engine.history.HistoricTaskInstance
 *  org.activiti.engine.repository.ProcessDefinition
 *  org.activiti.engine.runtime.ProcessInstance
 *  org.activiti.engine.task.Comment
 *  org.activiti.engine.task.Task
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.common.ProcessActivity;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskComment;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.activiti6.common.ProcessActivityWrappers;
import com.jiuqi.nr.bpm.impl.activiti6.common.ProcessDefinitionWrapper;
import com.jiuqi.nr.bpm.impl.activiti6.common.ProcessInstanceWrapper;
import com.jiuqi.nr.bpm.impl.activiti6.common.TaskCommentWrapper;
import com.jiuqi.nr.bpm.impl.activiti6.common.TaskWrapper;
import com.jiuqi.nr.bpm.impl.activiti6.common.UserTaskWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;

public class ActivitiObjectWrapperUtils {
    public static com.jiuqi.nr.bpm.common.ProcessDefinition wrappingProcessDefinition(ProcessDefinition innerObject) {
        return innerObject == null ? null : new ProcessDefinitionWrapper(innerObject);
    }

    public static List<com.jiuqi.nr.bpm.common.ProcessDefinition> wrappingProcessDefinitions(List<ProcessDefinition> innerObjects) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingProcessDefinition(o)).collect(Collectors.toList());
    }

    public static UserTask wrappingUserTask(FlowElement userTaskElement) {
        return userTaskElement == null ? null : new UserTaskWrapper(userTaskElement);
    }

    public static Task wrappingTask(org.activiti.engine.task.Task innerObject) {
        return innerObject == null ? null : new TaskWrapper(innerObject);
    }

    public static List<Task> wrappingTasks(List<org.activiti.engine.task.Task> innerObjects) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingTask(o)).collect(Collectors.toList());
    }

    public static ProcessInstance wrappingProcessInstance(org.activiti.engine.runtime.ProcessInstance innerObject) {
        return innerObject == null ? null : new ProcessInstanceWrapper(innerObject);
    }

    public static List<ProcessInstance> wrappingProcessInstances(List<org.activiti.engine.runtime.ProcessInstance> innerObjects) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingProcessInstance(o)).collect(Collectors.toList());
    }

    public static ProcessActivity wrappingProcessActivity(HistoricTaskInstance innerObject) {
        return innerObject == null ? null : new ProcessActivityWrappers.ProcessActivityWrapperByHistoricTask(innerObject);
    }

    public static List<ProcessActivity> wrappingProcessActivitysByHistoricTask(List<HistoricTaskInstance> innerObjects) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingProcessActivity(o)).collect(Collectors.toList());
    }

    public static ProcessActivity wrappingProcessActivity(org.activiti.engine.task.Task innerObject) {
        return innerObject == null ? null : new ProcessActivityWrappers.ProcessActivityWrapperByTask(innerObject);
    }

    public static List<ProcessActivity> wrappingProcessActivitysByTask(List<org.activiti.engine.task.Task> innerObjects) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingProcessActivity(o)).collect(Collectors.toList());
    }

    public static TaskComment wrappingTaskComment(Comment innerObject) {
        return innerObject == null ? null : new TaskCommentWrapper(innerObject);
    }

    public static List<TaskComment> wrappingTaskComments(List<Comment> innerObjects) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingTaskComment(o)).collect(Collectors.toList());
    }

    public static Task wrappingTask(org.activiti.engine.task.Task innerObject, TaskService activitiTaskService) {
        return innerObject == null ? null : new TaskWrapper(innerObject, activitiTaskService);
    }

    public static List<Task> wrappingTasks(List<org.activiti.engine.task.Task> innerObjects, TaskService activitiTaskService) {
        return innerObjects.stream().map(o -> ActivitiObjectWrapperUtils.wrappingTask(o, activitiTaskService)).collect(Collectors.toList());
    }
}

