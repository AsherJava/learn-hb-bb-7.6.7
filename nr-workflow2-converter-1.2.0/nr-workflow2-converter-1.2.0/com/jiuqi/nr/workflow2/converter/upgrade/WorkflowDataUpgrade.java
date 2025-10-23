/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nr.workflow2.events.enumeration.CurrencyType
 *  com.jiuqi.nr.workflow2.events.enumeration.ReviewType
 *  com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService
 *  com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil
 *  com.jiuqi.nr.workflow2.todo.utils.TodoUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.events.enumeration.ReviewType;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.json.JSONObject;

public class WorkflowDataUpgrade
implements CustomClassExecutor {
    private JSONObject submitNodeJSON;
    private JSONObject uploadNodeJSON;
    private JSONObject auditNodeJSON;
    private JSONObject submitButtonJSON;
    private JSONObject backButtonJSON;
    private JSONObject uploadButtonJSON;
    private JSONObject rejectButtonJSON;
    private JSONObject confirmButtonJSON;
    private JSONObject cancelConfirmButtonJSON;
    private JSONObject retrieveButtonJSON;

    public void execute(DataSource dataSource) throws Exception {
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringBeanUtils.getBean(IDesignTimeViewController.class);
        WorkflowSettingsService workflowSettingsService_2_0 = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        List allDesignTaskDefines = designTimeViewController.listAllTask();
        for (DesignTaskDefine designTaskDefine : allDesignTaskDefines) {
            WorkflowSettingsDO workflowSettingsDO = workflowSettingsService_2_0.queryWorkflowSettings(designTaskDefine.getKey());
            if (workflowSettingsDO != null) continue;
            this.flowSettingUpgrade(designTaskDefine);
        }
    }

    public void flowSettingUpgrade(DesignTaskDefine designTaskDefine) {
        WorkflowSettingsManipulationService workflowSettingsManipulationService = (WorkflowSettingsManipulationService)SpringBeanUtils.getBean(WorkflowSettingsManipulationService.class);
        DefaultProcessDesignService defaultProcessDesignService = (DefaultProcessDesignService)SpringBeanUtils.getBean(DefaultProcessDesignService.class);
        TaskFlowsDefine flowsSetting = designTaskDefine.getFlowsSetting();
        FlowsType flowsType = flowsSetting.getFlowsType();
        WorkFlowType workFlowType = flowsSetting.getWordFlowType();
        boolean allowFormBack = flowsSetting.isAllowFormBack();
        WorkflowObjectType workflowObjectType = null;
        switch (workFlowType) {
            case ENTITY: {
                workflowObjectType = allowFormBack ? WorkflowObjectType.MD_WITH_SFR : WorkflowObjectType.MAIN_DIMENSION;
                break;
            }
            case FORM: {
                workflowObjectType = WorkflowObjectType.FORM;
                break;
            }
            case GROUP: {
                workflowObjectType = WorkflowObjectType.FORM_GROUP;
            }
        }
        if (flowsType.equals((Object)FlowsType.NOSTARTUP) || flowsType.equals((Object)FlowsType.EXTEND)) {
            WorkflowSettingsUtil.initTaskWorkflowSetting((String)designTaskDefine.getKey(), (boolean)false);
        } else {
            UUID workflowDefineUUID = UUID.randomUUID();
            WorkflowSettingsDTO settingsDTO = WorkflowSettingsUtil.generateDefaultWorkflowSettingsDTO((String)designTaskDefine.getKey(), (String)workflowDefineUUID.toString());
            settingsDTO.setTodoEnable(SendMessageTaskConfig.canSendMessage());
            settingsDTO.setWorkflowObjectType(workflowObjectType);
            settingsDTO.setOtherConfig(this.generateOtherConfig((TaskDefine)designTaskDefine).toString());
            settingsDTO.setWorkflowEngine("jiuqi.nr.default-1.0");
            JSONObject workflow = WorkflowSettingsUtil.generateStandardWorkflow();
            WorkflowSettingDefine targetSettingDefine = this.queryWorkflowSettingDefine((TaskDefine)designTaskDefine);
            if (targetSettingDefine != null) {
                settingsDTO.setWorkflowEngine("jiuqi.nr.customprocessengine");
                settingsDTO.setWorkflowDefine(targetSettingDefine.getWorkflowId());
            }
            workflowSettingsManipulationService.addWorkflowSettings(settingsDTO);
            defaultProcessDesignService.addDefaultProcessConfig(workflowDefineUUID.toString(), workflow.toString());
        }
    }

    private List<String> transferMultiCheckStrToList(String multiCheckStr) {
        return Arrays.asList(multiCheckStr.split(";"));
    }

    public WorkflowSettingDefine queryWorkflowSettingDefine(TaskDefine taskDefine) {
        WorkflowSettingDefine targetSettingDefine;
        block1: {
            FormSchemeDefine formSchemeDefine;
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
            WorkflowSettingService workflowSettingService_1_0 = (WorkflowSettingService)SpringBeanUtils.getBean(WorkflowSettingService.class);
            PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
            TodoUtil todoUtil = (TodoUtil)SpringBeanUtils.getBean(TodoUtil.class);
            String curPeriod = periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
            FormSchemeDefine curFormScheme = todoUtil.getFormSchemeDefine(taskDefine.getKey(), curPeriod);
            WorkflowSettingDefine workflowSettingDefine = targetSettingDefine = curFormScheme == null ? null : workflowSettingService_1_0.getWorkflowDefineByFormSchemeKey(curFormScheme.getKey());
            if (targetSettingDefine != null) break block1;
            List formSchemeDefines = runTimeViewController.listFormSchemeByTask(taskDefine.getKey()).stream().sorted(Comparator.comparing(FormSchemeDefine::getFromPeriod, Comparator.reverseOrder())).collect(Collectors.toList());
            Iterator iterator = formSchemeDefines.iterator();
            while (iterator.hasNext() && (targetSettingDefine = workflowSettingService_1_0.getWorkflowDefineByFormSchemeKey((formSchemeDefine = (FormSchemeDefine)iterator.next()).getKey())) == null) {
            }
        }
        return targetSettingDefine;
    }

    public JSONObject generateOtherConfig(TaskDefine taskDefine) {
        String isEnable;
        int dueType;
        ITaskOptionController taskOption = (ITaskOptionController)SpringBeanUtils.getBean(ITaskOptionController.class);
        FillDateType fillingDateType = taskDefine.getFillingDateType();
        int fillingDateDays = taskDefine.getFillingDateDays();
        FillInAutomaticallyDue fillInAutomaticallyDue = taskDefine.getFillInAutomaticallyDue();
        JSONObject otherSettings = new JSONObject();
        JSONObject timeControl = new JSONObject();
        if (!fillingDateType.equals((Object)FillDateType.NONE)) {
            JSONObject startTimeConfig = new JSONObject();
            startTimeConfig.put("type", (Object)StartTimeStrategy.CUSTOM);
            JSONObject customValue = new JSONObject();
            customValue.put("type", (Object)(fillingDateType.equals((Object)FillDateType.WORK_DAY) ? TimeControlType.WEEKDAY : TimeControlType.NATURAL_DAY));
            customValue.put("dayNum", fillingDateDays);
            startTimeConfig.put("customValue", (Object)customValue);
            timeControl.put("startTimeConfig", (Object)startTimeConfig);
        }
        if ((dueType = fillInAutomaticallyDue.getType()) != FillInAutomaticallyDue.Type.CLOSE.getValue()) {
            JSONObject endTimeConfig = new JSONObject();
            endTimeConfig.put("type", (Object)(dueType == FillInAutomaticallyDue.Type.WORKING.getValue() ? TimeControlType.WEEKDAY : TimeControlType.NATURAL_DAY));
            endTimeConfig.put("dayNum", fillingDateDays);
            endTimeConfig.put("hierarchicalControl", true);
            timeControl.put("endTimeConfig", (Object)endTimeConfig);
        }
        if ((isEnable = taskOption.getValue(taskDefine.getKey(), "ALLOW_STOP_FILING")) != null && "1".equals(isEnable.toString())) {
            String role = taskOption.getValue(taskDefine.getKey(), "ALLOW_STOP_FILING_ROLE");
            JSONObject manualTermination = new JSONObject();
            manualTermination.put("role", (Object)role);
            timeControl.put("manualTermination", (Object)manualTermination);
        }
        otherSettings.put("timeControl", (Object)timeControl);
        return otherSettings;
    }

    private JSONObject generateDefaultWorkflow(TaskFlowsDefine flowsSetting) {
        boolean isSubmitEnable;
        String defaultButtonNameConfig;
        JSONObject buttonNameJson;
        JSONObject nodeNameJson;
        JSONObject workflow = new JSONObject();
        String defaultNodeNameConfig = flowsSetting.getDefaultNodeNameConfig();
        JSONObject jSONObject = nodeNameJson = WorkflowSettingsUtil.isJSONStr((String)defaultNodeNameConfig) ? new JSONObject(defaultNodeNameConfig) : new JSONObject();
        if (!nodeNameJson.isEmpty()) {
            this.submitNodeJSON = nodeNameJson.isNull("tsk_submit") ? new JSONObject() : nodeNameJson.getJSONObject("tsk_submit");
            this.uploadNodeJSON = nodeNameJson.isNull("tsk_upload") ? new JSONObject() : nodeNameJson.getJSONObject("tsk_upload");
            this.auditNodeJSON = nodeNameJson.isNull("tsk_audit") ? new JSONObject() : nodeNameJson.getJSONObject("tsk_audit");
        }
        JSONObject jSONObject2 = buttonNameJson = WorkflowSettingsUtil.isJSONStr((String)(defaultButtonNameConfig = flowsSetting.getDefaultButtonNameConfig())) ? new JSONObject(defaultButtonNameConfig) : new JSONObject();
        if (!buttonNameJson.isEmpty()) {
            this.submitButtonJSON = nodeNameJson.isNull("act_submit") ? new JSONObject() : nodeNameJson.getJSONObject("act_submit");
            this.backButtonJSON = nodeNameJson.isNull("act_return") ? new JSONObject() : nodeNameJson.getJSONObject("act_return");
            this.uploadButtonJSON = nodeNameJson.isNull("act_upload") ? new JSONObject() : nodeNameJson.getJSONObject("act_upload");
            this.rejectButtonJSON = nodeNameJson.isNull("act_reject") ? new JSONObject() : nodeNameJson.getJSONObject("act_reject");
            this.confirmButtonJSON = nodeNameJson.isNull("act_confirm") ? new JSONObject() : nodeNameJson.getJSONObject("act_confirm");
            this.cancelConfirmButtonJSON = nodeNameJson.isNull("act_cancel_confirm") ? new JSONObject() : nodeNameJson.getJSONObject("act_cancel_confirm");
            this.retrieveButtonJSON = nodeNameJson.isNull("act_retrieve") ? new JSONObject() : nodeNameJson.getJSONObject("act_retrieve");
        }
        workflow.put("workflowDefineTemplate", (Object)((isSubmitEnable = flowsSetting.isUnitSubmitForCensorship()) ? WorkflowDefineTemplate.SUBMIT_WORKFLOW : WorkflowDefineTemplate.STANDARD_WORKFLOW));
        JSONObject workflowNodes = new JSONObject();
        if (isSubmitEnable) {
            workflowNodes.put(UserTaskTemplate.SUBMIT.getCode(), (Object)this.generateSubmitNodeConfig(flowsSetting));
        }
        workflowNodes.put(UserTaskTemplate.REPORT.getCode(), (Object)this.generateReportNodeConfig(flowsSetting));
        workflowNodes.put(UserTaskTemplate.AUDIT.getCode(), (Object)this.generateAuditNodeConfig(flowsSetting));
        workflow.put("workflowNodes", (Object)workflowNodes);
        return workflow;
    }

    private JSONObject generateSubmitNodeConfig(TaskFlowsDefine flowsSetting) {
        JSONObject submitNodeConfig = new JSONObject();
        JSONObject submitProperty = new JSONObject();
        submitProperty.put("rename", (Object)(this.submitNodeJSON.isNull("rename") ? UserTaskTemplate.SUBMIT.getTitle() : this.submitNodeJSON.getString("rename")));
        submitNodeConfig.put("property", (Object)submitProperty);
        JSONObject submitAction = new JSONObject();
        JSONObject retrieve = new JSONObject();
        retrieve.put("buttonName", (Object)(this.retrieveButtonJSON.isNull("rename") ? UserActionTemplate.RETRIEVE.getTitle() : this.retrieveButtonJSON.getString("rename")));
        retrieve.put("stateName", (Object)(this.retrieveButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.RETRIEVE.getTitle() : this.retrieveButtonJSON.getString("statename")));
        submitAction.put(UserActionTemplate.RETRIEVE.getCode(), (Object)retrieve);
        JSONObject submit = new JSONObject();
        submit.put("buttonName", (Object)(this.submitButtonJSON.isNull("rename") ? UserActionTemplate.SUBMIT.getTitle() : this.submitButtonJSON.getString("rename")));
        submit.put("stateName", (Object)(this.submitButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.SUBMIT.getTitle() : this.submitButtonJSON.getString("statename")));
        submitAction.put(UserActionTemplate.SUBMIT.getCode(), (Object)submit);
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

    private JSONObject generateReportNodeConfig(TaskFlowsDefine flowsSetting) {
        boolean isSubmitEnable = flowsSetting.isUnitSubmitForCensorship();
        JSONObject reportNodeConfig = new JSONObject();
        JSONObject reportProperty = new JSONObject();
        reportProperty.put("rename", (Object)(this.uploadNodeJSON.isNull("rename") ? UserTaskTemplate.REPORT.getTitle() : this.uploadNodeJSON.getString("rename")));
        if (flowsSetting.getStepByStepReport()) {
            reportProperty.put("uploadLayerByLayer", (Object)UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_UPLOADED);
        }
        if (flowsSetting.isUnitSubmitForForce()) {
            reportProperty.put("forceUpload", (Object)flowsSetting.getSelectedRoleForceKey());
        }
        if (flowsSetting.isSubmitExplain()) {
            reportProperty.put("uploadDesc", (Object)(flowsSetting.isForceSubmitExplain() ? FillInDescStrategy.REQUIRED : FillInDescStrategy.NON_REQUIRED));
        }
        if (flowsSetting.isAllowTakeBack()) {
            reportProperty.put("retrieveOrReturn", (Object)RetrieveStrategy.RETRIEVE_SELF);
        }
        if (flowsSetting.isApplyReturn()) {
            reportProperty.put("retrieveOrReturn", (Object)(flowsSetting.isAllowTakeBack() ? RetrieveStrategy.RETRIEVE_SELF : RetrieveStrategy.APPLY_RETURN));
        }
        reportNodeConfig.put("property", (Object)reportProperty);
        JSONObject reportAction = new JSONObject();
        JSONObject report = new JSONObject();
        report.put("buttonName", (Object)(this.uploadButtonJSON.isNull("rename") ? UserActionTemplate.REPORT.getTitle() : this.uploadButtonJSON.getString("rename")));
        report.put("stateName", (Object)(this.uploadButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.REPORT.getTitle() : this.uploadButtonJSON.getString("statename")));
        reportAction.put(UserActionTemplate.REPORT.getCode(), (Object)report);
        JSONObject retrieve = new JSONObject();
        retrieve.put("buttonName", (Object)(this.retrieveButtonJSON.isNull("rename") ? UserActionTemplate.RETRIEVE.getTitle() : this.retrieveButtonJSON.getString("rename")));
        retrieve.put("stateName", (Object)(this.retrieveButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.RETRIEVE.getTitle() : this.retrieveButtonJSON.getString("statename")));
        reportAction.put(UserActionTemplate.RETRIEVE.getCode(), (Object)retrieve);
        reportNodeConfig.put("actions", (Object)reportAction);
        if (isSubmitEnable) {
            JSONObject back = new JSONObject();
            back.put("buttonName", (Object)(this.backButtonJSON.isNull("rename") ? UserActionTemplate.BACK.getTitle() : this.backButtonJSON.getString("rename")));
            back.put("stateName", (Object)(this.backButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.BACK.getTitle() : this.backButtonJSON.getString("statename")));
            reportAction.put(UserActionTemplate.BACK.getCode(), (Object)back);
        }
        JSONObject reportEvent = new JSONObject();
        JSONObject reportActionEvent = new JSONObject();
        if (flowsSetting.getReportBeforeAudit()) {
            JSONObject review = new JSONObject();
            review.put("reviewType", (Object)ReviewType.FORMULA_REVIEW.name());
            review.put("formulaScheme", this.transferMultiCheckStrToList(flowsSetting.getReportBeforeAuditValue()));
            if (!flowsSetting.getWordFlowType().equals((Object)WorkFlowType.FORM) && flowsSetting.getSpecialAudit()) {
                review.put("isReviewAllBeforeUpload", flowsSetting.getSpecialAudit());
            }
            JSONObject auditCurrency = new JSONObject();
            CurrencyType resultType = null;
            ReportAuditType type = flowsSetting.getReportBeforeAuditType();
            switch (type) {
                case NONE: {
                    resultType = CurrencyType.ALL;
                    break;
                }
                case CONVERSION: {
                    resultType = CurrencyType.SUPERIOR;
                    break;
                }
                case ESCALATION: {
                    resultType = CurrencyType.SELF;
                    break;
                }
                case CUSTOM: {
                    resultType = CurrencyType.CUSTOM;
                }
            }
            auditCurrency.put("type", (Object)resultType.name());
            auditCurrency.put("currency", resultType.equals((Object)CurrencyType.CUSTOM) ? this.transferMultiCheckStrToList(flowsSetting.getReportBeforeAuditCustom()) : new ArrayList());
            review.put("auditCurrency", (Object)auditCurrency);
            review.put("errorHandle", (Object)"");
            reportActionEvent.put("complete-review-event", (Object)review);
        }
        if (flowsSetting.getMulCheckBeforeCheck()) {
            // empty if block
        }
        reportEvent.put(UserActionTemplate.REPORT.getCode(), (Object)reportActionEvent);
        reportEvent.put(UserActionTemplate.RETRIEVE.getCode(), new HashMap());
        if (isSubmitEnable) {
            reportEvent.put(UserActionTemplate.BACK.getCode(), new HashMap());
        }
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
        return reportNodeConfig;
    }

    private JSONObject generateAuditNodeConfig(TaskFlowsDefine flowsSetting) {
        JSONObject auditNodeConfig = new JSONObject();
        JSONObject auditProperty = new JSONObject();
        auditProperty.put("rename", (Object)(this.auditNodeJSON.isNull("rename") ? UserTaskTemplate.AUDIT.getTitle() : this.auditNodeJSON.getString("rename")));
        if (flowsSetting.isDataConfirm()) {
            auditNodeConfig.put("isConfirmEnable", flowsSetting.isDataConfirm());
        }
        if (flowsSetting.getStepByStepBack()) {
            auditNodeConfig.put("isReturnLayerByLayer", flowsSetting.getStepByStepBack());
        }
        if (flowsSetting.getGoBackAllSup()) {
            auditNodeConfig.put("isReturnAllSuperior", flowsSetting.getGoBackAllSup());
        }
        if (flowsSetting.isBackDescriptionNeedWrite()) {
            auditNodeConfig.put("returnDesc", (Object)FillInDescStrategy.REQUIRED);
        }
        auditNodeConfig.put("property", (Object)auditProperty);
        JSONObject auditAction = new JSONObject();
        JSONObject confirm = new JSONObject();
        confirm.put("buttonName", (Object)(this.confirmButtonJSON.isNull("rename") ? UserActionTemplate.CONFIRM.getTitle() : this.confirmButtonJSON.getString("rename")));
        confirm.put("stateName", (Object)(this.confirmButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.CONFIRM.getTitle() : this.confirmButtonJSON.getString("statename")));
        auditAction.put(UserActionTemplate.CONFIRM.getCode(), (Object)confirm);
        JSONObject cancel_confirm = new JSONObject();
        cancel_confirm.put("buttonName", (Object)(this.cancelConfirmButtonJSON.isNull("rename") ? UserActionTemplate.CANCEL_CONFIRM.getTitle() : this.cancelConfirmButtonJSON.getString("rename")));
        cancel_confirm.put("stateName", (Object)(this.cancelConfirmButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.CANCEL_CONFIRM.getTitle() : this.cancelConfirmButtonJSON.getString("statename")));
        auditAction.put(UserActionTemplate.CANCEL_CONFIRM.getCode(), (Object)cancel_confirm);
        JSONObject reject = new JSONObject();
        reject.put("buttonName", (Object)(this.rejectButtonJSON.isNull("rename") ? UserActionTemplate.REJECT.getTitle() : this.rejectButtonJSON.getString("rename")));
        reject.put("stateName", (Object)(this.rejectButtonJSON.isNull("statename") ? "\u5df2" + UserActionTemplate.REJECT.getTitle() : this.rejectButtonJSON.getString("statename")));
        auditAction.put(UserActionTemplate.REJECT.getCode(), (Object)reject);
        auditNodeConfig.put("actions", (Object)auditAction);
        JSONObject auditEvent = new JSONObject();
        JSONObject confirmActionEvent = new JSONObject();
        JSONObject notification = new JSONObject();
        if (flowsSetting.getSendMessageMail() != null && !flowsSetting.getSendMessageMail().isEmpty()) {
            notification.put("email", (Object)flowsSetting.getSendMessageMail());
        }
        if (flowsSetting.getMessageTemplate() != null && !flowsSetting.getMessageTemplate().isEmpty()) {
            notification.put("message", (Object)flowsSetting.getMessageTemplate());
        }
        if (!notification.isEmpty()) {
            confirmActionEvent.put("send-message-notice-event", (Object)notification);
        }
        auditEvent.put(UserActionTemplate.CONFIRM.getCode(), (Object)confirmActionEvent);
        auditEvent.put(UserActionTemplate.CANCEL_CONFIRM.getCode(), new HashMap());
        JSONObject rejectActionEvent = new JSONObject();
        if (flowsSetting.isReturnVersion()) {
            rejectActionEvent.put("made-data-snapshot-version-event", true);
        }
        if (!notification.isEmpty()) {
            rejectActionEvent.put("send-message-notice-event", (Object)notification);
        }
        auditEvent.put(UserActionTemplate.REJECT.getCode(), (Object)rejectActionEvent);
        auditNodeConfig.put("events", (Object)auditEvent);
        JSONObject auditParticipant = new JSONObject();
        JSONObject auditActionExecuter = new JSONObject();
        auditActionExecuter.put("strategy", (Object)"jiuqi.nr.canaudit");
        JSONObject participantParam = new JSONObject();
        participantParam.put("userRoleList", new ArrayList());
        auditActionExecuter.put("param", (Object)participantParam);
        auditParticipant.put("actionExecuter", (Object)auditActionExecuter);
        JSONObject todoReceiver = new JSONObject();
        todoReceiver.put("type", (Object)TodoReceiverStrategy.IDENTICAL_TO_EXECUTOR.toString());
        todoReceiver.put("customParticipant", new ArrayList());
        auditParticipant.put("todoReceiver", (Object)todoReceiver);
        auditNodeConfig.put("participant", (Object)auditParticipant);
        return auditNodeConfig;
    }
}

