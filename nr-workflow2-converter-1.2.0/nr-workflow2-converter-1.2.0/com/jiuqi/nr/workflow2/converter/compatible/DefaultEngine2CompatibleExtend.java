/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.ReportProperty
 *  com.jiuqi.nr.workflow2.events.enumeration.CurrencyType
 *  com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.compatible;

import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.ReportProperty;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DefaultEngine2CompatibleExtend
implements Workflow2EngineCompatibleExtend {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    public String getWorkflowEngine() {
        return "jiuqi.nr.default";
    }

    public boolean isSubmitEnabled(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        WorkflowDefineTemplate workflowDefineTemplate = config.getWorkflowDefineTemplate();
        return workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW);
    }

    public String getUploadLayerByLayer(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        ReportProperty reportProperty = config.getReportNodeConfig().getProperty();
        UploadLayerByLayerStrategy uploadLayerByLayer = reportProperty.getUploadLayerByLayer();
        if (uploadLayerByLayer == null) {
            return null;
        }
        return uploadLayerByLayer.toString();
    }

    public String getForceUpload(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        ReportProperty reportProperty = config.getReportNodeConfig().getProperty();
        return reportProperty.getForceUpload();
    }

    public String getBackDesc(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        ReportNodeConfig reportNodeConfig = defaultProcessConfig.getReportNodeConfig();
        FillInDescStrategy backDesc = reportNodeConfig.getProperty().getBackDesc();
        if (backDesc == null) {
            return null;
        }
        return backDesc.toString();
    }

    public String getUploadDesc(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        ReportNodeConfig reportNodeConfig = defaultProcessConfig.getReportNodeConfig();
        FillInDescStrategy uploadDesc = reportNodeConfig.getProperty().getUploadDesc();
        if (uploadDesc == null) {
            return null;
        }
        return uploadDesc.toString();
    }

    public String getReportNodeRetrieveOrReturn(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        ReportNodeConfig reportNodeConfig = defaultProcessConfig.getReportNodeConfig();
        RetrieveStrategy retrieveOrReturn = reportNodeConfig.getProperty().getRetrieveOrReturn();
        if (retrieveOrReturn == null) {
            return null;
        }
        return retrieveOrReturn.toString();
    }

    public boolean isReturnLayerByLayer(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        AuditNodeConfig auditNodeConfig = defaultProcessConfig.getAuditNodeConfig();
        return auditNodeConfig.getProperty().isReturnLayerByLayer();
    }

    public boolean isReturnAllSuperior(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        AuditNodeConfig auditNodeConfig = defaultProcessConfig.getAuditNodeConfig();
        return auditNodeConfig.getProperty().isReturnAllSuperior();
    }

    public String getReturnDesc(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        AuditNodeConfig auditNodeConfig = defaultProcessConfig.getAuditNodeConfig();
        FillInDescStrategy returnDesc = auditNodeConfig.getProperty().getReturnDesc();
        if (returnDesc == null) {
            return null;
        }
        return returnDesc.toString();
    }

    public String getReturnType(String taskKey) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        AuditNodeConfig auditNodeConfig = config.getAuditNodeConfig();
        AuditProperty property = auditNodeConfig.getProperty();
        return property.getReturnType();
    }

    public List<String> getCalculateFormulaSchemeList(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return Collections.emptyList();
        }
        List<String> formulaSchemeList = new ArrayList<String>();
        for (ActionEvent event : actionEvents) {
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("complete-calculation-event")) continue;
            formulaSchemeList = eventParam.getJSONArray("formulaScheme").toList().stream().map(Object::toString).collect(Collectors.toList());
            break;
        }
        return formulaSchemeList;
    }

    public List<String> getReviewFormulaSchemeList(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return Collections.emptyList();
        }
        List<String> formulaSchemeList = new ArrayList<String>();
        for (ActionEvent event : actionEvents) {
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("complete-review-event")) continue;
            formulaSchemeList = eventParam.getJSONArray("formulaScheme").toList().stream().map(Object::toString).collect(Collectors.toList());
            break;
        }
        return formulaSchemeList;
    }

    public ReportAuditType getReviewCurrencyType(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return ReportAuditType.NONE;
        }
        ReportAuditType checkCurrencyType = ReportAuditType.NONE;
        for (ActionEvent event : actionEvents) {
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("complete-review-event")) continue;
            JSONObject auditCurrency = eventParam.getJSONObject("auditCurrency");
            CurrencyType currencyType = CurrencyType.valueOf((String)auditCurrency.getString("type"));
            String currencyStr = null;
            switch (currencyType) {
                case SUPERIOR: {
                    checkCurrencyType = ReportAuditType.CONVERSION;
                    break;
                }
                case SELF: {
                    checkCurrencyType = ReportAuditType.ESCALATION;
                    break;
                }
                case CUSTOM: {
                    checkCurrencyType = ReportAuditType.CUSTOM;
                    List currencyList = auditCurrency.getJSONArray("currency").toList().stream().map(Object::toString).collect(Collectors.toList());
                    currencyStr = String.join((CharSequence)";", currencyList);
                    break;
                }
                default: {
                    checkCurrencyType = ReportAuditType.NONE;
                    break;
                }
            }
            break;
        }
        return checkCurrencyType;
    }

    public List<String> getReviewCustomCurrencyValue(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return new ArrayList<String>();
        }
        List<String> currencyList = new ArrayList<String>();
        for (ActionEvent event : actionEvents) {
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("complete-review-event")) continue;
            JSONObject auditCurrency = eventParam.getJSONObject("auditCurrency");
            CurrencyType currencyType = CurrencyType.valueOf((String)auditCurrency.getString("type"));
            if (!currencyType.equals((Object)CurrencyType.CUSTOM)) break;
            currencyList = auditCurrency.getJSONArray("currency").toList().stream().map(Object::toString).collect(Collectors.toList());
            break;
        }
        return currencyList;
    }

    public List<Integer> getIgnoreErrorStatus(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> ignoreErrorStatus = new ArrayList<Integer>();
        for (ActionEvent event : actionEvents) {
            List auditTypes;
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("complete-review-event")) continue;
            try {
                auditTypes = this.auditTypeDefineService.queryAllAuditType();
            }
            catch (Exception exception) {
                LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
                throw new RuntimeException(exception);
            }
            try {
                Map<String, String> errorTypeValueMap = eventParam.getJSONArray("errorHandle").toList().stream().map(e -> (HashMap)e).collect(Collectors.toMap(e -> (String)e.get("code"), e -> (String)e.get("value"), (v1, v2) -> v1));
                for (AuditType auditType : auditTypes) {
                    Integer auditTypeCode = auditType.getCode();
                    String value = errorTypeValueMap.get(String.valueOf(auditTypeCode));
                    if (value == null || !value.equals("0")) continue;
                    ignoreErrorStatus.add(auditTypeCode);
                }
                break;
            }
            catch (JSONException e2) {
                String error;
                String warning;
                JSONObject errorHandle = eventParam.getJSONObject("errorHandle");
                String hint = errorHandle.getString("hint");
                if (hint.equals("0")) {
                    ignoreErrorStatus.add(1);
                }
                if ((warning = errorHandle.getString("warning")).equals("0")) {
                    ignoreErrorStatus.add(2);
                }
                if (!(error = errorHandle.getString("error")).equals("0")) break;
                ignoreErrorStatus.add(4);
                break;
            }
        }
        return ignoreErrorStatus;
    }

    public List<Integer> getNeedCommentErrorStatus(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> needCommentErrorStatus = new ArrayList<Integer>();
        for (ActionEvent event : actionEvents) {
            List auditTypes;
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("complete-review-event")) continue;
            try {
                auditTypes = this.auditTypeDefineService.queryAllAuditType();
            }
            catch (Exception exception) {
                LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
                throw new RuntimeException(exception);
            }
            try {
                Map<String, String> errorTypeValueMap = eventParam.getJSONArray("errorHandle").toList().stream().map(e -> (HashMap)e).collect(Collectors.toMap(e -> (String)e.get("code"), e -> (String)e.get("value"), (v1, v2) -> v1));
                for (AuditType auditType : auditTypes) {
                    Integer auditTypeCode = auditType.getCode();
                    String value = errorTypeValueMap.get(String.valueOf(auditTypeCode));
                    if (value == null || !value.equals("1")) continue;
                    needCommentErrorStatus.add(auditTypeCode);
                }
                break;
            }
            catch (JSONException e2) {
                String error;
                String warning;
                JSONObject errorHandle = eventParam.getJSONObject("errorHandle");
                String hint = errorHandle.getString("hint");
                if (hint.equals("1")) {
                    needCommentErrorStatus.add(1);
                }
                if ((warning = errorHandle.getString("warning")).equals("1")) {
                    needCommentErrorStatus.add(2);
                }
                if (!(error = errorHandle.getString("error")).equals("1")) break;
                needCommentErrorStatus.add(4);
                break;
            }
        }
        return needCommentErrorStatus;
    }

    public boolean getSysMsgShow(String taskKey, @Nullable String workflowNode, String actionCode) {
        return false;
    }

    public boolean getMailShow(String taskKey, @Nullable String workflowNode, String actionCode) {
        List<ActionEvent> actionEvents = this.getActionEvents(taskKey, workflowNode, actionCode);
        if (actionEvents == null) {
            return false;
        }
        boolean mailShow = false;
        block0: for (ActionEvent event : actionEvents) {
            String eventId = event.getEventId();
            JSONObject eventParam = new JSONObject(event.getEventParam());
            if (!eventId.equals("send-message-notice-event")) continue;
            for (String messageType : eventParam.keySet()) {
                if (!messageType.equals(MessageType.EMAIL.code)) continue;
                JSONObject emailConfig = eventParam.getJSONObject(messageType);
                mailShow = emailConfig.getBoolean("userSelectable");
                break block0;
            }
        }
        return mailShow;
    }

    private List<ActionEvent> getActionEvents(String taskKey, @Nullable String workflowNode, String actionCode) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        Map nodeEvents = Collections.emptyMap();
        if (workflowNode == null) {
            workflowNode = this.computeActionNode(actionCode);
        }
        switch (workflowNode) {
            case "tsk_submit": {
                SubmitNodeConfig submitNodeConfig = config.getSubmitNodeConfig();
                nodeEvents = submitNodeConfig == null ? new HashMap() : submitNodeConfig.getEvents();
                break;
            }
            case "tsk_upload": {
                ReportNodeConfig reportNodeConfig = config.getReportNodeConfig();
                nodeEvents = reportNodeConfig.getEvents();
                break;
            }
            case "tsk_audit": {
                AuditNodeConfig auditNodeConfig = config.getAuditNodeConfig();
                nodeEvents = auditNodeConfig.getEvents();
            }
        }
        if (actionCode.equals("act_apply_reject")) {
            nodeEvents = config.getReportNodeConfig().getEvents();
        }
        return (List)nodeEvents.get(actionCode);
    }

    private String computeActionNode(String actionCode) {
        switch (actionCode) {
            case "act_submit": {
                return "tsk_submit";
            }
            case "act_return": 
            case "act_upload": 
            case "act_apply_reject": {
                return "tsk_upload";
            }
            case "act_reject": 
            case "act_confirm": 
            case "act_cancel_confirm": {
                return "tsk_audit";
            }
        }
        return "";
    }
}

