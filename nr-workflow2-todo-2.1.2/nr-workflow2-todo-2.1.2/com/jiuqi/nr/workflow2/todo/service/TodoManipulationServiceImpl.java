/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.todo.service;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;
import com.jiuqi.nr.workflow2.todo.entity.TodoDeleteInfo;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoManipulationServiceImpl
implements TodoManipulationService {
    @Resource
    private TodoClient todoClient;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public boolean createTodo(TodoInfo todoInfo) {
        ArrayList<Map<String, Object>> todoTasks = new ArrayList<Map<String, Object>>();
        this.buildTodoTask(todoTasks, todoInfo);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todoTasks", todoTasks);
        R response = this.todoClient.addBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean batchCreateTodo(List<TodoInfo> todoInfos) {
        ArrayList<Map<String, Object>> todoTasks = new ArrayList<Map<String, Object>>();
        for (TodoInfo todoInfo : todoInfos) {
            this.buildTodoTask(todoTasks, todoInfo);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todoTasks", todoTasks);
        R response = this.todoClient.addBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean createUpdateTodo(TodoInfo todoInfo) {
        ArrayList deleteTodos = new ArrayList();
        HashMap<String, String> deleteParam = new HashMap<String, String>();
        deleteParam.put("TASKID", todoInfo.getWorkflowNodeTask());
        deleteParam.put("PROCESSID", todoInfo.getWorkflowInstance());
        deleteTodos.add(deleteParam);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", deleteTodos);
        R delResponse = this.todoClient.deleteBatch(tenantDO);
        if (delResponse.getCode() != 0) {
            LoggerFactory.getLogger(this.getClass()).error(delResponse.getMsg());
            return false;
        }
        return this.createTodo(todoInfo);
    }

    @Override
    public boolean batchCreateUpdateTodo(List<TodoInfo> todoInfos) {
        ArrayList deleteTodos = new ArrayList();
        ArrayList<Map<String, Object>> createTodoTasks = new ArrayList<Map<String, Object>>();
        for (TodoInfo todoInfo : todoInfos) {
            HashMap<String, String> deleteParam = new HashMap<String, String>();
            deleteParam.put("TASKID", todoInfo.getWorkflowNodeTask());
            deleteParam.put("PROCESSID", todoInfo.getWorkflowInstance());
            deleteTodos.add(deleteParam);
            this.buildTodoTask(createTodoTasks, todoInfo);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("deleteTodoTasks", deleteTodos);
        tenantDO.addExtInfo("todoTasks", createTodoTasks);
        R response = this.todoClient.completeBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean consumeTodo(TodoConsumeInfo todoConsumeInfo) {
        ArrayList completeTodoTasks = new ArrayList();
        HashMap<String, Object> todoTask = new HashMap<String, Object>();
        todoTask.put("TASKID", todoConsumeInfo.getWorkflowNodeTask());
        todoTask.put("PROCESSID", todoConsumeInfo.getWorkflowInstance());
        todoTask.put("COMPLETEUSER", NpContextHolder.getContext().getUserId());
        todoTask.put("COMPLETERESULT", 1);
        todoTask.put("PROCESSSTATUS", 1);
        completeTodoTasks.add(todoTask);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("completeTodoTasks", completeTodoTasks);
        R response = this.todoClient.completeBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean batchConsumeTodo(List<TodoConsumeInfo> todoConsumeInfos) {
        ArrayList completeTodoTasks = new ArrayList();
        for (TodoConsumeInfo todoConsumeInfo : todoConsumeInfos) {
            HashMap<String, Object> todoTask = new HashMap<String, Object>();
            todoTask.put("TASKID", todoConsumeInfo.getWorkflowNodeTask());
            todoTask.put("PROCESSID", todoConsumeInfo.getWorkflowInstance());
            todoTask.put("COMPLETEUSER", NpContextHolder.getContext().getUserId());
            todoTask.put("COMPLETERESULT", 1);
            todoTask.put("PROCESSSTATUS", 1);
            completeTodoTasks.add(todoTask);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("completeTodoTasks", completeTodoTasks);
        R response = this.todoClient.completeBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean batchClearTodo(List<String> serialNumbers) {
        ArrayList clearTodoTasks = new ArrayList();
        for (String serialNumber : serialNumbers) {
            HashMap<String, String> todoTask = new HashMap<String, String>();
            todoTask.put("PROCESSID", serialNumber);
            clearTodoTasks.add(todoTask);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", clearTodoTasks);
        R response = this.todoClient.deleteBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean deleteTodo(TodoDeleteInfo todoDeleteInfo) {
        return this.batchDeleteTodo(Collections.singletonList(todoDeleteInfo));
    }

    @Override
    public boolean batchDeleteTodo(List<TodoDeleteInfo> todoDeleteInfos) {
        if (todoDeleteInfos == null || todoDeleteInfos.isEmpty()) {
            return true;
        }
        ArrayList clearTodoTasks = new ArrayList();
        for (TodoDeleteInfo deleteInfo : todoDeleteInfos) {
            HashMap<String, String> todoTask = new HashMap<String, String>();
            todoTask.put("PROCESSID", deleteInfo.getWorkflowInstance());
            todoTask.put("TASKID", deleteInfo.getWorkflowNodeTask());
            clearTodoTasks.add(todoTask);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", clearTodoTasks);
        R response = this.todoClient.deleteBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean deleteTodoMessageByFormSchemeKey(String formSchemeKey) {
        ArrayList deleteTodoTasks = new ArrayList();
        HashMap<String, String> todoTask = new HashMap<String, String>();
        todoTask.put("PROCESSDEFINEKEY", formSchemeKey);
        deleteTodoTasks.add(todoTask);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", deleteTodoTasks);
        R response = this.todoClient.deleteBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean deleteTodoMessageByTaskId(String taskId) {
        ArrayList deleteTodoTasks = new ArrayList();
        HashMap<String, String> todoTask = new HashMap<String, String>();
        todoTask.put("BIZDEFINE", taskId);
        deleteTodoTasks.add(todoTask);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", deleteTodoTasks);
        R response = this.todoClient.deleteBatch(tenantDO);
        return response.getCode() == 0;
    }

    @Override
    public boolean deleteTodoMessageByCurrentUser(String serialNumber) {
        ArrayList clearTodoTasks = new ArrayList();
        HashMap<String, String> todoTask = new HashMap<String, String>();
        todoTask.put("PROCESSID", serialNumber);
        todoTask.put("PARTICIPANT", NpContextHolder.getContext().getUserId());
        clearTodoTasks.add(todoTask);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", clearTodoTasks);
        R response = this.todoClient.deleteBatch(tenantDO);
        return response.getCode() == 0;
    }

    private void buildTodoTask(List<Map<String, Object>> todoTasks, TodoInfo todoInfo) {
        List<String> participants = todoInfo.getParticipants();
        for (String participant : participants) {
            HashMap<String, Object> todoTask = new HashMap<String, Object>();
            todoTask.put("BIZTYPE", "NR");
            todoTask.put("BIZDEFINE", todoInfo.getTaskId());
            todoTask.put("BIZCODE", todoInfo.getWorkflowInstance());
            todoTask.put("BIZTITLE", todoInfo.getBusinessTitle() != null && todoInfo.getBusinessTitle().length() > 200 ? todoInfo.getBusinessTitle().substring(0, 200) : todoInfo.getBusinessTitle());
            todoTask.put("TASKID", todoInfo.getWorkflowNodeTask());
            todoTask.put("TASKTYPE", 1);
            todoTask.put("PRIORITY", "01");
            todoTask.put("PARTICIPANT", participant);
            todoTask.put("SYSCODE", "NR-WORKFLOW");
            todoTask.put("TASKDEFINEKEY", todoInfo.getWorkflowNode());
            todoTask.put("COUNTERSIGNFLAG", 0);
            todoTask.put("PROCESSID", todoInfo.getWorkflowInstance());
            todoTask.put("PROCESSDEFINEKEY", todoInfo.getFormSchemeKey());
            todoTask.put("BIZDATE", todoInfo.getPeriod());
            String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
            if (entityCaliber == null) {
                FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(todoInfo.getFormSchemeKey());
                entityCaliber = formSchemeDefine.getDw();
            }
            todoTask.put("UNITCODE", entityCaliber);
            todoTask.put("REMARK", todoInfo.getRemark());
            todoTasks.add(todoTask);
        }
    }
}

