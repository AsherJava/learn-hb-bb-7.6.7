/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.ITaskFlowExtendService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.converter;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.ITaskFlowExtendService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskFlowsDefineConverter
implements ITaskFlowExtendService {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public TaskFlowsDefine getFlowsSetting(String taskId, TaskFlowsDefine taskFlowsDefine) {
        if (this.defaultEngineVersionJudge.isTaskVersion_1_0(taskId)) {
            return taskFlowsDefine;
        }
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(taskId);
        DesignTaskFlowsDefine designTaskFlowsDefine = (DesignTaskFlowsDefine)taskFlowsDefine;
        DesignFlowSettingDefine flowSettingDefine = taskFlowsDefine.getDesignFlowSettingDefine();
        WorkflowObjectType workflowObjectType = workflowSettingsDO.getWorkflowObjectType();
        switch (workflowObjectType) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                flowSettingDefine.setWordFlowType(WorkFlowType.ENTITY);
                break;
            }
            case FORM: {
                flowSettingDefine.setWordFlowType(WorkFlowType.FORM);
                break;
            }
            case FORM_GROUP: {
                flowSettingDefine.setWordFlowType(WorkFlowType.GROUP);
            }
        }
        flowSettingDefine.setErroStatus(taskFlowsDefine.getErroStatus());
        flowSettingDefine.setPromptStatus(taskFlowsDefine.getPromptStatus());
        designTaskFlowsDefine.setDesignFlowSettingDefine(flowSettingDefine);
        designTaskFlowsDefine.setFlowsType(workflowSettingsDO.isWorkflowEnable() ? FlowsType.DEFAULT : FlowsType.NOSTARTUP);
        return designTaskFlowsDefine;
    }

    public FillInAutomaticallyDue getFillInAutomaticallyDue(String taskId, FillInAutomaticallyDue fillInAutomaticallyDue) {
        if (this.defaultEngineVersionJudge.isTaskVersion_1_0(taskId)) {
            return fillInAutomaticallyDue;
        }
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskId);
        FillInEndTimeConfig fillInEndTimeConfig = workflowOtherSettings.getFillInEndTimeConfig();
        FillInAutomaticallyDue convertDue = new FillInAutomaticallyDue();
        convertDue.setAutomaticTermination(true);
        if (fillInEndTimeConfig.isEnable()) {
            FillInDaysConfig fillInDaysConfig = fillInEndTimeConfig.getFillInDaysConfig();
            int fillInDueType = fillInDaysConfig.getType().equals((Object)TimeControlType.NATURAL_DAY) ? FillInAutomaticallyDue.Type.NATURAL.getValue() : FillInAutomaticallyDue.Type.WORKING.getValue();
            convertDue.setType(fillInDueType);
            convertDue.setDays(fillInDaysConfig.getDayNum());
        } else {
            convertDue.setType(FillInAutomaticallyDue.Type.CLOSE.getValue());
            convertDue.setDays(0);
        }
        return convertDue;
    }

    public FillDateType getFillingDateType(String taskId, FillDateType fillDateType) {
        if (this.defaultEngineVersionJudge.isTaskVersion_1_0(taskId)) {
            return fillDateType;
        }
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskId);
        FillInStartTimeConfig fillInStartTimeConfig = workflowOtherSettings.getFillInStartTimeConfig();
        if (fillInStartTimeConfig.isEnable()) {
            StartTimeStrategy strategy = fillInStartTimeConfig.getType();
            if (strategy.equals((Object)StartTimeStrategy.IDENTICAL_TO_TASK)) {
                return FillDateType.NATURAL_DAY;
            }
            FillInDaysConfig fillInDaysConfig = fillInStartTimeConfig.getFillInDaysConfig();
            return fillInDaysConfig.getType().equals((Object)TimeControlType.NATURAL_DAY) ? FillDateType.NATURAL_DAY : FillDateType.WORK_DAY;
        }
        return FillDateType.NONE;
    }

    public int getFillingDateDays(String taskId, int fillingDateDays) {
        if (this.defaultEngineVersionJudge.isTaskVersion_1_0(taskId)) {
            return fillingDateDays;
        }
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskId);
        FillInStartTimeConfig fillInStartTimeConfig = workflowOtherSettings.getFillInStartTimeConfig();
        if (fillInStartTimeConfig.isEnable()) {
            StartTimeStrategy strategy = fillInStartTimeConfig.getType();
            if (strategy.equals((Object)StartTimeStrategy.IDENTICAL_TO_TASK)) {
                return 0;
            }
            return fillInStartTimeConfig.getFillInDaysConfig().getDayNum();
        }
        return 0;
    }
}

