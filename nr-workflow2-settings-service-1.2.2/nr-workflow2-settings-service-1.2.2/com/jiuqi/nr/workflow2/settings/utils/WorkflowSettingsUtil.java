/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.settings.utils;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;

public class WorkflowSettingsUtil {
    public static void initTaskWorkflowSetting(String taskId, boolean isWorkflowEnable) {
        WorkflowSettingsManipulationService workflowSettingsManipulationService = (WorkflowSettingsManipulationService)SpringBeanUtils.getBean(WorkflowSettingsManipulationService.class);
        DefaultProcessDesignService defaultProcessDesignService = (DefaultProcessDesignService)SpringBeanUtils.getBean(DefaultProcessDesignService.class);
        WorkflowSettingsService workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        WorkflowSettingsDO workflowSettingsDO = workflowSettingsService.queryWorkflowSettings(taskId);
        if (workflowSettingsDO == null) {
            UUID workflowDefineUUID = UUID.randomUUID();
            WorkflowSettingsDTO initSettingData = WorkflowSettingsUtil.generateDefaultWorkflowSettingsDTO(taskId, workflowDefineUUID.toString());
            initSettingData.setWorkflowEnable(isWorkflowEnable);
            workflowSettingsManipulationService.addWorkflowSettings(initSettingData);
            defaultProcessDesignService.addDefaultProcessConfig(workflowDefineUUID.toString(), WorkflowSettingsUtil.generateStandardWorkflow().toString());
        } else {
            WorkflowSettingsDTO updateSettingData = new WorkflowSettingsDTO();
            updateSettingData.setTaskId(workflowSettingsDO.getTaskId());
            updateSettingData.setWorkflowEngine(workflowSettingsDO.getWorkflowEngine());
            updateSettingData.setWorkflowDefine(workflowSettingsDO.getWorkflowDefine());
            updateSettingData.setWorkflowEnable(isWorkflowEnable);
            updateSettingData.setTodoEnable(workflowSettingsDO.isTodoEnable());
            updateSettingData.setWorkflowObjectType(workflowSettingsDO.getWorkflowObjectType());
            updateSettingData.setOtherConfig(workflowSettingsDO.getOtherConfig());
            workflowSettingsManipulationService.updateWorkflowSettings(updateSettingData);
        }
    }

    public static void initTaskDefault_1_0_WorkflowSetting(String taskId, boolean isWorkflowEnable) {
        WorkflowSettingsManipulationService workflowSettingsManipulationService = (WorkflowSettingsManipulationService)SpringBeanUtils.getBean(WorkflowSettingsManipulationService.class);
        WorkflowSettingsService workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        WorkflowSettingsDO workflowSettingsDO = workflowSettingsService.queryWorkflowSettings(taskId);
        if (workflowSettingsDO == null) {
            UUID workflowDefineUUID = UUID.randomUUID();
            WorkflowSettingsDTO initSettingData = WorkflowSettingsUtil.generateDefault_1_0_WorkflowSettingsDTO(taskId, workflowDefineUUID.toString());
            initSettingData.setWorkflowEnable(isWorkflowEnable);
            workflowSettingsManipulationService.addWorkflowSettings(initSettingData);
        } else {
            WorkflowSettingsDTO updateSettingData = new WorkflowSettingsDTO();
            updateSettingData.setTaskId(workflowSettingsDO.getTaskId());
            updateSettingData.setWorkflowEngine(workflowSettingsDO.getWorkflowEngine());
            updateSettingData.setWorkflowDefine(workflowSettingsDO.getWorkflowDefine());
            updateSettingData.setWorkflowEnable(isWorkflowEnable);
            updateSettingData.setTodoEnable(workflowSettingsDO.isTodoEnable());
            updateSettingData.setWorkflowObjectType(workflowSettingsDO.getWorkflowObjectType());
            updateSettingData.setOtherConfig(workflowSettingsDO.getOtherConfig());
            workflowSettingsManipulationService.updateWorkflowSettings(updateSettingData);
        }
    }

    public static JSONObject generateStandardWorkflow() {
        JSONObject workflow = new JSONObject();
        workflow.put("workflowDefineTemplate", (Object)WorkflowDefineTemplate.STANDARD_WORKFLOW);
        JSONObject workflowNodes = new JSONObject();
        JSONObject reportNodeConfig = new JSONObject();
        JSONObject reportProperty = new JSONObject();
        reportProperty.put("rename", (Object)UserTaskTemplate.REPORT.getTitle());
        reportNodeConfig.put("property", (Object)reportProperty);
        JSONObject reportAction = new JSONObject();
        JSONObject report = new JSONObject();
        report.put("buttonName", (Object)UserActionTemplate.REPORT.getTitle());
        report.put("stateName", (Object)("\u5df2" + UserActionTemplate.REPORT.getTitle()));
        reportAction.put(UserActionTemplate.REPORT.getCode(), (Object)report);
        JSONObject retrieve = new JSONObject();
        retrieve.put("buttonName", (Object)UserActionTemplate.RETRIEVE.getTitle());
        reportAction.put(UserActionTemplate.RETRIEVE.getCode(), (Object)retrieve);
        JSONObject apply_reject = new JSONObject();
        apply_reject.put("buttonName", (Object)UserActionTemplate.APPLY_FOR_REJECT.getTitle());
        reportAction.put(UserActionTemplate.APPLY_FOR_REJECT.getCode(), (Object)apply_reject);
        reportNodeConfig.put("actions", (Object)reportAction);
        JSONObject reportEvent = new JSONObject();
        reportEvent.put(UserActionTemplate.REPORT.getCode(), new HashMap());
        reportEvent.put(UserActionTemplate.RETRIEVE.getCode(), new HashMap());
        reportEvent.put(UserActionTemplate.APPLY_FOR_REJECT.getCode(), new HashMap());
        reportNodeConfig.put("events", (Object)reportEvent);
        JSONObject reportParticipant = new JSONObject();
        JSONObject reportActionExecuter = new JSONObject();
        reportActionExecuter.put("strategy", (Object)"jiuqi.nr.canreport");
        JSONObject participantParam = new JSONObject();
        participantParam.put("userRoleList", new ArrayList());
        reportActionExecuter.put("param", (Object)participantParam);
        reportParticipant.put("actionExecuter", (Object)reportActionExecuter);
        JSONObject todoReceiver = new JSONObject();
        todoReceiver.put("type", (Object)TodoReceiverStrategy.IDENTICAL_TO_EXECUTOR.toString());
        todoReceiver.put("customParticipant", new ArrayList());
        reportParticipant.put("todoReceiver", (Object)todoReceiver);
        reportNodeConfig.put("participant", (Object)reportParticipant);
        workflowNodes.put(UserTaskTemplate.REPORT.getCode(), (Object)reportNodeConfig);
        JSONObject auditNodeConfig = new JSONObject();
        JSONObject auditProperty = new JSONObject();
        auditProperty.put("rename", (Object)UserTaskTemplate.AUDIT.getTitle());
        auditNodeConfig.put("property", (Object)auditProperty);
        JSONObject auditAction = new JSONObject();
        JSONObject confirm = new JSONObject();
        confirm.put("buttonName", (Object)UserActionTemplate.CONFIRM.getTitle());
        confirm.put("stateName", (Object)("\u5df2" + UserActionTemplate.CONFIRM.getTitle()));
        auditAction.put(UserActionTemplate.CONFIRM.getCode(), (Object)confirm);
        JSONObject cancel_confirm = new JSONObject();
        cancel_confirm.put("buttonName", (Object)UserActionTemplate.CANCEL_CONFIRM.getTitle());
        cancel_confirm.put("stateName", (Object)("\u5df2" + UserActionTemplate.CANCEL_CONFIRM.getTitle()));
        auditAction.put(UserActionTemplate.CANCEL_CONFIRM.getCode(), (Object)cancel_confirm);
        JSONObject reject = new JSONObject();
        reject.put("buttonName", (Object)UserActionTemplate.REJECT.getTitle());
        reject.put("stateName", (Object)("\u5df2" + UserActionTemplate.REJECT.getTitle()));
        auditAction.put(UserActionTemplate.REJECT.getCode(), (Object)reject);
        auditNodeConfig.put("actions", (Object)auditAction);
        JSONObject auditEvent = new JSONObject();
        auditEvent.put(UserActionTemplate.CONFIRM.getCode(), new HashMap());
        auditEvent.put(UserActionTemplate.CANCEL_CONFIRM.getCode(), new HashMap());
        auditEvent.put(UserActionTemplate.REJECT.getCode(), new HashMap());
        auditNodeConfig.put("events", (Object)auditEvent);
        JSONObject auditParticipant = new JSONObject();
        JSONObject auditActionExecuter = new JSONObject();
        auditActionExecuter.put("strategy", (Object)"jiuqi.nr.canaudit");
        auditActionExecuter.put("param", (Object)participantParam);
        auditParticipant.put("actionExecuter", (Object)auditActionExecuter);
        auditParticipant.put("todoReceiver", (Object)todoReceiver);
        auditNodeConfig.put("participant", (Object)auditParticipant);
        workflowNodes.put(UserTaskTemplate.AUDIT.getCode(), (Object)auditNodeConfig);
        workflow.put("workflowNodes", (Object)workflowNodes);
        return workflow;
    }

    public static JSONObject generateSubmitWorkflow() {
        JSONObject workflow = WorkflowSettingsUtil.generateStandardWorkflow();
        workflow.put("workflowDefineTemplate", (Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW);
        JSONObject workflowNodes = workflow.getJSONObject("workflowNodes");
        JSONObject reportNodeConfig = workflowNodes.getJSONObject(UserTaskTemplate.REPORT.getCode());
        JSONObject reportAction = reportNodeConfig.getJSONObject("actions");
        JSONObject back = new JSONObject();
        back.put("buttonName", (Object)UserActionTemplate.BACK.getTitle());
        back.put("stateName", (Object)("\u5df2" + UserActionTemplate.BACK.getTitle()));
        reportAction.put(UserActionTemplate.BACK.getCode(), (Object)back);
        JSONObject reportEvent = reportNodeConfig.getJSONObject("events");
        reportEvent.put(UserActionTemplate.BACK.getCode(), new HashMap());
        workflowNodes.put(UserTaskTemplate.SUBMIT.getCode(), (Object)WorkflowSettingsUtil.generateSubmitNodeConfig());
        return workflow;
    }

    public static JSONObject generateSubmitNodeConfig() {
        JSONObject submitNodeConfig = new JSONObject();
        JSONObject submitProperty = new JSONObject();
        submitProperty.put("rename", (Object)UserTaskTemplate.SUBMIT.getTitle());
        submitNodeConfig.put("property", (Object)submitProperty);
        JSONObject submitAction = new JSONObject();
        JSONObject submit = new JSONObject();
        submit.put("buttonName", (Object)UserActionTemplate.SUBMIT.getTitle());
        submit.put("stateName", (Object)("\u5df2" + UserActionTemplate.SUBMIT.getTitle()));
        submitAction.put(UserActionTemplate.SUBMIT.getCode(), (Object)submit);
        JSONObject retrieve = new JSONObject();
        retrieve.put("buttonName", (Object)UserActionTemplate.RETRIEVE.getTitle());
        submitAction.put(UserActionTemplate.RETRIEVE.getCode(), (Object)retrieve);
        submitNodeConfig.put("actions", (Object)submitAction);
        JSONObject submitEvent = new JSONObject();
        submitEvent.put(UserActionTemplate.SUBMIT.getCode(), new HashMap());
        submitEvent.put(UserActionTemplate.RETRIEVE.getCode(), new HashMap());
        submitNodeConfig.put("events", (Object)submitEvent);
        JSONObject submitParticipant = new JSONObject();
        JSONObject submitActionExecuter = new JSONObject();
        submitActionExecuter.put("strategy", (Object)"jiuqi.nr.cansubmit");
        JSONObject participantParam = new JSONObject();
        participantParam.put("userRoleList", new ArrayList());
        submitActionExecuter.put("param", (Object)participantParam);
        submitParticipant.put("actionExecuter", (Object)submitActionExecuter);
        JSONObject todoReceiver = new JSONObject();
        todoReceiver.put("type", (Object)TodoReceiverStrategy.IDENTICAL_TO_EXECUTOR.toString());
        todoReceiver.put("customParticipant", new ArrayList());
        submitParticipant.put("todoReceiver", (Object)todoReceiver);
        submitNodeConfig.put("participant", (Object)submitParticipant);
        return submitNodeConfig;
    }

    public static WorkflowSettingsDTO generateDefault_1_0_WorkflowSettingsDTO(String taskId, String workflowDefine) {
        WorkflowSettingsDTO settingsDTO = new WorkflowSettingsDTO();
        settingsDTO.setTaskId(taskId);
        settingsDTO.setWorkflowEngine("jiuqi.nr.default-1.0");
        settingsDTO.setWorkflowDefine(workflowDefine);
        settingsDTO.setWorkflowEnable(true);
        settingsDTO.setTodoEnable(true);
        settingsDTO.setWorkflowObjectType(WorkflowObjectType.MAIN_DIMENSION);
        settingsDTO.setOtherConfig(WorkflowSettingsUtil.generateInitOtherSettings().toString());
        return settingsDTO;
    }

    public static WorkflowSettingsDTO generateDefaultWorkflowSettingsDTO(String taskId, String workflowDefine) {
        WorkflowSettingsDTO settingsDTO = new WorkflowSettingsDTO();
        settingsDTO.setTaskId(taskId);
        settingsDTO.setWorkflowEngine("jiuqi.nr.default");
        settingsDTO.setWorkflowDefine(workflowDefine);
        settingsDTO.setWorkflowEnable(true);
        settingsDTO.setTodoEnable(true);
        settingsDTO.setWorkflowObjectType(WorkflowObjectType.MAIN_DIMENSION);
        JSONObject otherSettings = new JSONObject();
        otherSettings.put("timeControl", new HashMap());
        settingsDTO.setOtherConfig(otherSettings.toString());
        return settingsDTO;
    }

    public static WorkflowSettingsDTO generateCustomWorkflowSettingsDTO(String taskId) {
        WorkflowSettingsDTO settingsDTO = new WorkflowSettingsDTO();
        settingsDTO.setTaskId(taskId);
        settingsDTO.setWorkflowEngine("jiuqi.nr.customprocessengine");
        settingsDTO.setWorkflowDefine("");
        settingsDTO.setWorkflowEnable(true);
        settingsDTO.setTodoEnable(true);
        settingsDTO.setWorkflowObjectType(WorkflowObjectType.MAIN_DIMENSION);
        JSONObject otherSettings = new JSONObject();
        otherSettings.put("timeControl", new HashMap());
        settingsDTO.setOtherConfig(otherSettings.toString());
        return settingsDTO;
    }

    public static JSONObject generateInitOtherSettings() {
        JSONObject otherSettings = new JSONObject();
        JSONObject timeControl = new JSONObject();
        JSONObject workflowSelfControl = new JSONObject();
        workflowSelfControl.put("type", (Object)StartTimeStrategy.IDENTICAL_TO_TASK);
        JSONObject customValue = new JSONObject();
        customValue.put("type", (Object)TimeControlType.NATURAL_DAY);
        customValue.put("dayNum", 0);
        workflowSelfControl.put("customValue", (Object)customValue);
        workflowSelfControl.put("bootTime", (Object)"06:00:00");
        timeControl.put("workflowSelfControl", (Object)workflowSelfControl);
        otherSettings.put("timeControl", (Object)timeControl);
        return otherSettings;
    }

    public static <T> List<T> objectToList(Object obj, Class<T> clazz) {
        ArrayList<T> result = new ArrayList<T>();
        if (obj instanceof List) {
            for (Object o : (List)obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    public static boolean isJSONStr(String str) {
        boolean result = false;
        if (StringUtils.isNotEmpty((String)str) && (str = str.trim()).startsWith("{") && str.endsWith("}")) {
            result = true;
        }
        return result;
    }
}

