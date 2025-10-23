/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.workflow2.events.enumeration.CurrencyType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.workflow2.settings.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsQueryServiceImpl;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsSourceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping(value={"/nr/workflow2/settings"})
@Api(tags={"\u4efb\u52a1\u6d41\u7a0b2.0-\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u6d41\u7a0b1.0\u5f15\u64ce\u67e5\u8be2"})
public class WorkflowSettingsConvertController {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private WorkflowSettingsQueryServiceImpl workflowSettingsQueryService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private WorkflowSettingsSourceUtil workflowSettingsSourceUtil;
    @Value(value="${jiuqi.nr.task.openMulCheck:false}")
    private boolean openMulCheck;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u586b\u62a5\u8ba1\u5212 - \u6d41\u7a0b1.0\u5f15\u64ce\u8bbe\u8ba1\u5668\u53c2\u6570\u67e5\u8be2")
    @GetMapping(value={"/config/default_1_0"})
    public Map<String, Object> getDefault_1_0_ConfigWithSource(@RequestParam(value="taskId") String taskId) {
        TaskFlowsDefine flowsSetting = this.designTimeViewController.getTask(taskId).getFlowsSetting();
        HashMap<String, Object> result = new HashMap<String, Object>();
        HashMap workflow = new HashMap();
        HashMap<String, Object> flowSettingMap = new HashMap<String, Object>();
        flowSettingMap.put("submitExplain", flowsSetting.isSubmitExplain());
        flowSettingMap.put("forceSubmitExplain", flowsSetting.isForceSubmitExplain());
        flowSettingMap.put("stepByStepReport", flowsSetting.getStepByStepReport());
        flowSettingMap.put("stepReportType", flowsSetting.getStepReportType());
        flowSettingMap.put("checkBeforeReporting", flowsSetting.isCheckBeforeReporting());
        flowSettingMap.put("checkBeforeReportingType", this.transferToCurrencyType(flowsSetting.getCheckBeforeReportingType()));
        flowSettingMap.put("checkBeforeReportingCustom", this.transferMultiCheckValueStrToList(flowsSetting.getCheckBeforeReportingCustom()));
        flowSettingMap.put("unitSubmitForForce", flowsSetting.isUnitSubmitForForce());
        flowSettingMap.put("selectedRoleForceKey", flowsSetting.getSelectedRoleForceKey());
        flowSettingMap.put("allowTakeBack", flowsSetting.isAllowTakeBack());
        flowSettingMap.put("applyReturn", flowsSetting.isApplyReturn());
        flowSettingMap.put("unitSubmitForCensorship", flowsSetting.isUnitSubmitForCensorship());
        flowSettingMap.put("returnExplain", flowsSetting.isReturnExplain());
        flowSettingMap.put("allowTakeBackForSubmit", flowsSetting.isAllowTakeBackForSubmit());
        flowSettingMap.put("dataConfirm", flowsSetting.isDataConfirm());
        flowSettingMap.put("backDescriptionNeedWrite", flowsSetting.isBackDescriptionNeedWrite());
        flowSettingMap.put("openBackType", flowsSetting.isOpenBackType());
        flowSettingMap.put("backTypeEntity", this.processBackTypeEntity(flowsSetting.getBackTypeEntity()));
        flowSettingMap.put("returnVersion", flowsSetting.isReturnVersion());
        flowSettingMap.put("stepByStepBack", flowsSetting.getStepByStepBack());
        flowSettingMap.put("goBackAllSup", flowsSetting.getGoBackAllSup());
        flowSettingMap.put("isOpenForceControl", flowsSetting.isOpenForceControl());
        flowSettingMap.put("defaultButtonName", flowsSetting.getDefaultButtonName());
        flowSettingMap.put("defaultButtonNameConfig", this.transferJsonStrToMap(flowsSetting.getDefaultButtonNameConfig()));
        flowSettingMap.put("defaultNodeName", flowsSetting.getDefaultNodeName());
        flowSettingMap.put("defaultNodeNameConfig", this.transferJsonStrToMap(flowsSetting.getDefaultNodeNameConfig()));
        boolean isSendMessageMailEnable = flowsSetting.getSendMessageMail() != null && !flowsSetting.getSendMessageMail().isEmpty();
        flowSettingMap.put("sendMessageMail", isSendMessageMailEnable);
        flowSettingMap.put("messageTemplate", flowsSetting.getMessageTemplate());
        flowSettingMap.put("mulCheckBeforeCheck", flowsSetting.getMulCheckBeforeCheck());
        flowSettingMap.put("reportBeforeAudit", flowsSetting.getReportBeforeAudit());
        flowSettingMap.put("specialAudit", flowsSetting.getSpecialAudit());
        flowSettingMap.put("reportBeforeAuditType", this.transferToCurrencyType(flowsSetting.getReportBeforeAuditType()));
        flowSettingMap.put("reportBeforeAuditCustom", this.transferMultiCheckValueStrToList(flowsSetting.getReportBeforeAuditCustom()));
        flowSettingMap.put("reportBeforeAuditValue", this.transferMultiCheckValueStrToList(flowsSetting.getReportBeforeAuditValue()));
        flowSettingMap.put("errorHandle", this.transferErrorStatusAndPromptStatusToErrorHandle(flowsSetting.getErroStatus(), flowsSetting.getPromptStatus()));
        flowSettingMap.put("reportBeforeOperation", flowsSetting.getReportBeforeOperation());
        flowSettingMap.put("reportBeforeOperationValue", this.transferMultiCheckValueStrToList(flowsSetting.getReportBeforeOperationValue()));
        flowSettingMap.put("submitAfterFormula", flowsSetting.getSubmitAfterFormula());
        flowSettingMap.put("submitAfterFormulaValue", this.transferMultiCheckValueStrToList(flowsSetting.getSubmitAfterFormulaValue()));
        flowSettingMap.put("filterCondition", flowsSetting.getFilterCondition());
        workflow.put("flowSetting", flowSettingMap);
        result.put("workflow", workflow);
        HashMap<String, Object> workflowSource = new HashMap<String, Object>();
        workflowSource.put("openMulCheck", this.openMulCheck);
        workflowSource.put("currencyEnable", this.workflowSettingsSourceUtil.isContainCurrency(taskId));
        workflowSource.put("actions", this.getDefaultActions());
        workflowSource.put("workflowNodes", this.getDefaultNodes());
        workflowSource.put("formulaSchemeSource", this.workflowSettingsSourceUtil.buildFormulaSchemeSource(taskId));
        workflowSource.put("currencySource", this.workflowSettingsSourceUtil.buildCurrencySource(taskId));
        workflowSource.put("stepReportType", this.getStepReportType());
        workflowSource.put("forceUpload", this.workflowSettingsQueryService.buildRoleSource());
        workflowSource.put("returnType", this.workflowSettingsQueryService.getReturnTypeDefaultValue());
        workflowSource.put("isMultiEntityCaliber", this.workflowSettingsSourceUtil.isMultiEntityCaliber(taskId));
        result.put("workflowSource", workflowSource);
        return result;
    }

    private CurrencyType transferToCurrencyType(ReportAuditType reportAuditType) {
        switch (reportAuditType) {
            case NONE: {
                return CurrencyType.ALL;
            }
            case CONVERSION: {
                return CurrencyType.SUPERIOR;
            }
            case ESCALATION: {
                return CurrencyType.SELF;
            }
            case CUSTOM: {
                return CurrencyType.CUSTOM;
            }
        }
        return CurrencyType.ALL;
    }

    private List<String> transferMultiCheckValueStrToList(String multiCheckValueStr) {
        if (multiCheckValueStr == null || multiCheckValueStr.isEmpty()) {
            return Collections.emptyList();
        }
        String[] values = multiCheckValueStr.trim().split(";");
        return Arrays.asList(values);
    }

    private Map<String, Object> transferJsonStrToMap(String jsonStr) {
        if (jsonStr == null || jsonStr.isEmpty() || !jsonStr.startsWith("{")) {
            return new HashMap<String, Object>();
        }
        return new JSONObject(jsonStr).toMap();
    }

    private List<Map<String, String>> transferErrorStatusAndPromptStatusToErrorHandle(String errorStatus, String promptStatus) {
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
            for (AuditType auditType : auditTypes) {
                String auditTypeCode = String.valueOf(auditType.getCode());
                String auditChooseValue = "2";
                if (errorStatus.contains(auditTypeCode)) {
                    auditChooseValue = "0";
                } else if (promptStatus.contains(auditTypeCode)) {
                    auditChooseValue = "1";
                }
                HashMap<String, String> auditItem = new HashMap<String, String>();
                auditItem.put("code", auditTypeCode);
                auditItem.put("title", auditType.getTitle());
                auditItem.put("value", auditChooseValue);
                result.add(auditItem);
            }
            return result;
        }
        catch (Exception e) {
            LoggerFactory.getLogger(WorkflowSettingsConvertController.class).error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private List<Map<String, Object>> getDefaultActions() {
        ArrayList<Map<String, Object>> actions = new ArrayList<Map<String, Object>>();
        LinkedHashMap<String, String> act_submit = new LinkedHashMap<String, String>();
        act_submit.put("name", "\u9001\u5ba1");
        act_submit.put("rename", "\u9001\u5ba1");
        act_submit.put("statename", "\u5df2\u9001\u5ba1");
        act_submit.put("value", "act_submit");
        actions.add(act_submit);
        LinkedHashMap<String, String> act_return = new LinkedHashMap<String, String>();
        act_return.put("name", "\u9000\u5ba1");
        act_return.put("rename", "\u9000\u5ba1");
        act_return.put("statename", "\u5df2\u9000\u5ba1");
        act_return.put("value", "act_return");
        actions.add(act_return);
        LinkedHashMap<String, String> act_upload = new LinkedHashMap<String, String>();
        act_upload.put("name", "\u4e0a\u62a5");
        act_upload.put("rename", "\u4e0a\u62a5");
        act_upload.put("statename", "\u5df2\u4e0a\u62a5");
        act_upload.put("value", "act_upload");
        actions.add(act_upload);
        LinkedHashMap<String, String> act_reject = new LinkedHashMap<String, String>();
        act_reject.put("name", "\u9000\u56de");
        act_reject.put("rename", "\u9000\u56de");
        act_reject.put("statename", "\u5df2\u9000\u56de");
        act_reject.put("value", "act_reject");
        actions.add(act_reject);
        LinkedHashMap<String, String> act_confirm = new LinkedHashMap<String, String>();
        act_confirm.put("name", "\u786e\u8ba4");
        act_confirm.put("rename", "\u786e\u8ba4");
        act_confirm.put("statename", "\u5df2\u786e\u8ba4");
        act_confirm.put("value", "act_confirm");
        actions.add(act_confirm);
        LinkedHashMap<String, String> act_cancel_confirm = new LinkedHashMap<String, String>();
        act_cancel_confirm.put("name", "\u53d6\u6d88\u786e\u8ba4");
        act_cancel_confirm.put("rename", "\u53d6\u6d88\u786e\u8ba4");
        act_cancel_confirm.put("statename", "\u5df2\u53d6\u6d88\u786e\u8ba4");
        act_cancel_confirm.put("value", "act_cancel_confirm");
        actions.add(act_cancel_confirm);
        LinkedHashMap<String, String> act_retrieve = new LinkedHashMap<String, String>();
        act_retrieve.put("name", "\u53d6\u56de");
        act_retrieve.put("rename", "\u53d6\u56de");
        act_retrieve.put("value", "act_retrieve");
        actions.add(act_retrieve);
        return actions;
    }

    private List<Map<String, Object>> getDefaultNodes() {
        ArrayList<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        LinkedHashMap<String, String> tsk_submit = new LinkedHashMap<String, String>();
        tsk_submit.put("name", "\u9001\u5ba1");
        tsk_submit.put("rename", "\u9001\u5ba1");
        tsk_submit.put("value", "tsk_submit");
        nodes.add(tsk_submit);
        LinkedHashMap<String, String> tsk_upload = new LinkedHashMap<String, String>();
        tsk_upload.put("name", "\u4e0a\u62a5");
        tsk_upload.put("rename", "\u4e0a\u62a5");
        tsk_upload.put("value", "tsk_upload");
        nodes.add(tsk_upload);
        LinkedHashMap<String, String> tsk_audit = new LinkedHashMap<String, String>();
        tsk_audit.put("name", "\u5ba1\u6279");
        tsk_audit.put("rename", "\u5ba1\u6279");
        tsk_audit.put("value", "tsk_audit");
        nodes.add(tsk_audit);
        return nodes;
    }

    private List<Map<String, String>> getStepReportType() {
        ArrayList<Map<String, String>> stepReportTypes = new ArrayList<Map<String, String>>();
        HashMap<String, String> stepReport = new HashMap<String, String>();
        stepReport.put("code", "1");
        stepReport.put("title", "\u4e0b\u7ea7\u5df2\u4e0a\u62a5\uff0c\u4e0a\u7ea7\u53ef\u4e0a\u62a5");
        stepReportTypes.add(stepReport);
        HashMap<String, String> stepConfirm = new HashMap<String, String>();
        stepConfirm.put("code", "2");
        stepConfirm.put("title", "\u4e0b\u7ea7\u5df2\u786e\u8ba4\uff0c\u4e0a\u7ea7\u53ef\u4e0a\u62a5");
        stepReportTypes.add(stepConfirm);
        return stepReportTypes;
    }

    private String processBackTypeEntity(String strange_backTypeEntity) {
        if (strange_backTypeEntity == null || strange_backTypeEntity.isEmpty() || strange_backTypeEntity.equals("null")) {
            return "";
        }
        return strange_backTypeEntity.trim();
    }
}

