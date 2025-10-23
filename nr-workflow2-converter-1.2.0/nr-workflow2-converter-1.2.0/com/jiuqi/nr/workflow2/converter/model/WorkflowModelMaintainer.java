/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.impl.event.WorkflowDeleteEventImpl
 *  com.jiuqi.nr.bpm.impl.event.WorkflowObserver
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationService
 */
package com.jiuqi.nr.workflow2.converter.model;

import com.jiuqi.nr.bpm.impl.event.WorkflowDeleteEventImpl;
import com.jiuqi.nr.bpm.impl.event.WorkflowObserver;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowModelMaintainer
implements IParamDeployFinishListener {
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private WorkflowObserver workflowObserver;
    @Autowired
    private WorkflowDeleteEventImpl workflowDeleteEvent;
    @Autowired
    private TodoManipulationService todoManipulationService;

    private static boolean taskIsMatchVersion2_0(TaskDefine task) {
        return "2.0".equals(task.getVersion());
    }

    private boolean workflowEngineIsMatchVersion1_0(WorkflowSettingsDO workflowSettings) {
        return workflowSettings == null || workflowSettings.getWorkflowEngine().equals("jiuqi.nr.default-1.0") || workflowSettings.getWorkflowEngine().equals("jiuqi.nr.customprocessengine");
    }

    private WorkFlowType transferToWorkFlowType(WorkflowObjectType workflowObjectType) {
        switch (workflowObjectType) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                return WorkFlowType.ENTITY;
            }
            case FORM: {
                return WorkFlowType.FORM;
            }
            case FORM_GROUP: {
                return WorkFlowType.GROUP;
            }
        }
        return WorkFlowType.ENTITY;
    }

    @EventListener
    public void onWorkFlowSettingChanged(WorkflowSettingsSaveEvent event) {
        TaskDefine task = this.viewController.getTask(event.getTaskId());
        if (task == null) {
            return;
        }
        if (!WorkflowModelMaintainer.taskIsMatchVersion2_0(task)) {
            return;
        }
        WorkflowSettingsDO originalSettingsDO = event.getOriginalSettingsDO();
        WorkflowSettingsDO targetSettingsDO = event.getTargetSettingsDO();
        if (!this.workflowEngineIsMatchVersion1_0(targetSettingsDO)) {
            return;
        }
        try {
            boolean isClearWorkflowData;
            boolean bl = isClearWorkflowData = !originalSettingsDO.getWorkflowObjectType().equals((Object)targetSettingsDO.getWorkflowObjectType()) || !originalSettingsDO.getWorkflowEngine().equals(targetSettingsDO.getWorkflowEngine());
            if (isClearWorkflowData) {
                this.workflowDeleteEvent.delete(task, new StringBuffer(), this.transferToWorkFlowType(originalSettingsDO.getWorkflowObjectType()));
                this.todoManipulationService.deleteTodoMessageByTaskId(task.getKey());
            }
            this.workflowObserver.maintainTableModel(event.getTaskId());
        }
        catch (Exception e) {
            throw new ProcessRuntimeException(targetSettingsDO.getWorkflowEngine(), "\u7ef4\u62a4\u6d41\u7a0b\u5b58\u50a8\u8868\u5931\u8d25\u3002", (Throwable)e);
        }
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        TaskDefine task = this.viewController.getTask(define.getTaskKey());
        if (task == null) {
            throw new RuntimeException("\u7cfb\u7edf\u9519\u8bef\uff1a\u5728\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u540e\u4e8b\u4ef6\u4e2d\u65e0\u6cd5\u67e5\u8be2\u5230\u4efb\u52a1\u3002");
        }
        if (!WorkflowModelMaintainer.taskIsMatchVersion2_0(task)) {
            return;
        }
        WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(define.getTaskKey());
        if (!this.workflowEngineIsMatchVersion1_0(workflowSettings)) {
            return;
        }
        try {
            this.workflowObserver.maintainTableModel(define.getTaskKey(), define.getKey());
        }
        catch (Exception e) {
            throw new ProcessRuntimeException(workflowSettings.getWorkflowEngine(), "\u7ef4\u62a4\u6d41\u7a0b\u5b58\u50a8\u8868\u5931\u8d25\u3002", (Throwable)e);
        }
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(define.getTaskKey());
        if (workflowSettings == null) {
            return;
        }
        if (!this.workflowEngineIsMatchVersion1_0(workflowSettings)) {
            return;
        }
        try {
            this.workflowObserver.dropTable(define);
        }
        catch (Exception e) {
            throw new ProcessRuntimeException(workflowSettings.getWorkflowEngine(), "\u5220\u9664\u6d41\u7a0b\u5b58\u50a8\u8868\u5931\u8d25\u3002", (Throwable)e);
        }
    }
}

