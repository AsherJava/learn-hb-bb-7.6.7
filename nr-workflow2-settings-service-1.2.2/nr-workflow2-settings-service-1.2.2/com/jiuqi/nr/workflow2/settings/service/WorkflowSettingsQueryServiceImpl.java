/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityGroup
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.registry.ActionEventRegistry
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.ReportProperty
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.SubmitProperty
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.settings.service;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.registry.ActionEventRegistry;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditProperty;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.ReportProperty;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.SubmitProperty;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsQueryContext;
import com.jiuqi.nr.workflow2.settings.event.source.WorkflowEventSource;
import com.jiuqi.nr.workflow2.settings.extend.WorkflowSettingsTransmit;
import com.jiuqi.nr.workflow2.settings.filter.WorkflowEngineFilter;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsQueryService;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsSourceUtil;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkflowSettingsQueryServiceImpl
implements WorkflowSettingsQueryService {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_TITLE = "title";
    @Autowired
    private List<WorkflowEventSource> workflowEventSources;
    @Resource
    private WorkflowSettingsService workflowSettingsService;
    @Resource
    private DefaultProcessDesignService defaultProcessDesignService;
    @Resource
    private IActionEventFactory actionEventFactory;
    @Resource
    private IDesignTimeViewController designTimeViewController;
    @Resource
    private PeriodEngineService periodEngineService;
    @Resource
    private IActorStrategyFactory actorStrategyFactory;
    @Resource
    private WorkflowSettingsTransmit workflowSettingsTransmit;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IProcessEngineFactory processEngineFactory;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private WorkflowSettingsSourceUtil sourceUtil;
    @Autowired
    private WorkflowEngineFilter workflowEngineFilter;

    @Override
    public Map<String, Object> getWorkflowSettings(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(taskId);
        JSONObject returnJson = new JSONObject();
        JSONObject workflowSettingsJson = new JSONObject();
        workflowSettingsJson.put("isWorkFlowEnabled", workflowSettingsDO.isWorkflowEnable());
        JSONObject todoSettings = new JSONObject();
        todoSettings.put("isTodoEnabled", workflowSettingsDO.isTodoEnable());
        workflowSettingsJson.put("todoSetting", (Object)todoSettings);
        JSONObject basicSettings = new JSONObject();
        basicSettings.put("submitMode", (Object)workflowSettingsDO.getWorkflowObjectType());
        basicSettings.put("workflowType", (Object)workflowSettingsDO.getWorkflowEngine());
        basicSettings.put("workflowDefine", (Object)workflowSettingsDO.getWorkflowDefine());
        basicSettings.put("workflow", new HashMap());
        workflowSettingsJson.put("basicSettings", (Object)basicSettings);
        returnJson.put("workflowSettings", (Object)workflowSettingsJson);
        JSONObject workflowSettingsSource = new JSONObject();
        workflowSettingsSource.put("submitModeSource", this.buildSubmitModeSource());
        workflowSettingsSource.put("workflowTypeSource", this.buildWorkflowTypeSource());
        String optionValue = this.nvwaSystemOptionService.get("nr-flow-todo-id", "PROCESS_UPLOAD_CAN_SEND_MSG");
        boolean isSystemTodoEnabled = optionValue.contains("0");
        workflowSettingsSource.put("isSystemTodoEnabled", isSystemTodoEnabled);
        returnJson.put("workflowSettingsSource", (Object)workflowSettingsSource);
        return returnJson.toMap();
    }

    @Override
    public Map<String, Object> getOtherSettings(String taskId) {
        JSONObject customValue;
        FillInDaysConfig fillInDaysConfig;
        WorkflowOtherSettings workflowOtherSettings = this.workflowSettingsService.queryTaskWorkflowOtherSettings(taskId);
        FillInStartTimeConfig fillInStartTimeConfig = workflowOtherSettings.getFillInStartTimeConfig();
        FillInEndTimeConfig fillInEndTimeConfig = workflowOtherSettings.getFillInEndTimeConfig();
        ManualTerminationConfig manualTerminationConfig = workflowOtherSettings.getManualTerminationConfig();
        WorkflowSelfControl workflowSelfControlConfig = workflowOtherSettings.getWorkflowSelfControl();
        JSONObject returnJson = new JSONObject();
        JSONObject otherSettings = new JSONObject();
        JSONObject timeControl = new JSONObject();
        if (fillInStartTimeConfig.isEnable()) {
            JSONObject startTimeConfig = new JSONObject();
            startTimeConfig.put("type", (Object)fillInStartTimeConfig.getType());
            fillInDaysConfig = fillInStartTimeConfig.getFillInDaysConfig();
            customValue = new JSONObject();
            customValue.put("type", (Object)fillInDaysConfig.getType());
            customValue.put("dayNum", fillInDaysConfig.getDayNum());
            startTimeConfig.put("customValue", (Object)customValue);
            timeControl.put("startTimeConfig", (Object)startTimeConfig);
        }
        if (fillInEndTimeConfig.isEnable()) {
            FillInDaysConfig fillInDaysConfig2 = fillInEndTimeConfig.getFillInDaysConfig();
            JSONObject endTimeConfig = new JSONObject();
            endTimeConfig.put("type", (Object)fillInDaysConfig2.getType());
            endTimeConfig.put("dayNum", fillInDaysConfig2.getDayNum());
            endTimeConfig.put("hierarchicalControl", fillInEndTimeConfig.isHierarchicalControl());
            timeControl.put("endTimeConfig", (Object)endTimeConfig);
        }
        if (manualTerminationConfig.isEnable()) {
            JSONObject manualTermination = new JSONObject();
            manualTermination.put("role", (Object)manualTerminationConfig.getRole());
            timeControl.put("manualTermination", (Object)manualTermination);
        }
        if (workflowSelfControlConfig.isEnable()) {
            JSONObject workflowSelfControl = new JSONObject();
            workflowSelfControl.put("type", (Object)workflowSelfControlConfig.getType());
            fillInDaysConfig = workflowSelfControlConfig.getFillInDaysConfig();
            customValue = new JSONObject();
            customValue.put("type", (Object)fillInDaysConfig.getType());
            customValue.put("dayNum", fillInDaysConfig.getDayNum());
            workflowSelfControl.put("customValue", (Object)customValue);
            workflowSelfControl.put("bootTime", (Object)workflowSelfControlConfig.getBootTime());
            timeControl.put("workflowSelfControl", (Object)workflowSelfControl);
        }
        otherSettings.put("timeControl", (Object)timeControl);
        returnJson.put("otherSettings", (Object)otherSettings);
        JSONObject timeControlSource = new JSONObject();
        timeControlSource.put("periodType", this.buildPeriodType(taskId));
        timeControlSource.put("roleSource", this.buildRoleSource());
        returnJson.put("otherSettingsSource", (Object)timeControlSource);
        return returnJson.toMap();
    }

    @Override
    public Map<String, Object> getConfigWithSource(WorkflowSettingsQueryContext context) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(context.getTaskId());
        if (workflowDefine.equals("default-1.0")) {
            workflowDefine = UUID.randomUUID().toString();
        }
        WorkflowDefineTemplate viewDefineTemplate = context.getWorkflowDefineTemplate() == null || context.getWorkflowDefineTemplate().isEmpty() ? null : WorkflowDefineTemplate.valueOf((String)context.getWorkflowDefineTemplate());
        JSONObject configWithSource = new JSONObject();
        configWithSource.put("workflowDefineSource", this.buildWorkflowDefineSource(workflowDefine));
        DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        if (config == null) {
            if (viewDefineTemplate == null || viewDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) {
                configWithSource.put("workflow", (Object)WorkflowSettingsUtil.generateStandardWorkflow());
                JSONObject configSource = new JSONObject();
                configSource.put("workflowNodesSource", this.buildWorkflowNodeSource(WorkflowDefineTemplate.STANDARD_WORKFLOW));
                configWithSource.put("workflowSource", (Object)configSource);
            } else if (viewDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                configWithSource.put("workflow", (Object)WorkflowSettingsUtil.generateSubmitWorkflow());
                JSONObject configSource = new JSONObject();
                configSource.put("workflowNodesSource", this.buildWorkflowNodeSource(WorkflowDefineTemplate.SUBMIT_WORKFLOW));
                configWithSource.put("workflowSource", (Object)configSource);
            }
        } else {
            WorkflowDefineTemplate defineTemplate = config.getWorkflowDefineTemplate();
            Map<String, Object> configMap = this.buildConfig(config);
            if (viewDefineTemplate == null || viewDefineTemplate.equals((Object)defineTemplate)) {
                configWithSource.put("workflow", configMap);
                JSONObject configSource = new JSONObject();
                configSource.put("workflowNodesSource", this.buildWorkflowNodeSource(defineTemplate));
                configWithSource.put("workflowSource", (Object)configSource);
            } else if (viewDefineTemplate.equals((Object)WorkflowDefineTemplate.STANDARD_WORKFLOW)) {
                configMap.put("workflowDefineTemplate", WorkflowDefineTemplate.STANDARD_WORKFLOW);
                Map workflowNodes = (Map)configMap.get("workflowNodes");
                workflowNodes.remove("tsk_submit");
                configWithSource.put("workflow", configMap);
                JSONObject configSource = new JSONObject();
                configSource.put("workflowNodesSource", this.buildWorkflowNodeSource(viewDefineTemplate));
                configWithSource.put("workflowSource", (Object)configSource);
            } else if (viewDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
                configMap.put("workflowDefineTemplate", WorkflowDefineTemplate.SUBMIT_WORKFLOW);
                Map workflowNodes = (Map)configMap.get("workflowNodes");
                Map submitNodeConfigMap = WorkflowSettingsUtil.generateSubmitNodeConfig().toMap();
                workflowNodes.put("tsk_submit", submitNodeConfigMap);
                Map reportNodeConfigMap = (Map)workflowNodes.get(UserTaskTemplate.REPORT.getCode());
                Map reportActionMap = (Map)reportNodeConfigMap.get("actions");
                HashMap<String, String> back = new HashMap<String, String>();
                back.put("buttonName", UserActionTemplate.BACK.getTitle());
                back.put("stateName", "\u5df2" + UserActionTemplate.BACK.getTitle());
                reportActionMap.put(UserActionTemplate.BACK.getCode(), back);
                Map reportEventMap = (Map)reportNodeConfigMap.get("events");
                reportEventMap.put(UserActionTemplate.BACK.getCode(), new HashMap());
                configWithSource.put("workflow", configMap);
                HashMap<String, List<JSONObject>> configSource = new HashMap<String, List<JSONObject>>();
                configSource.put("workflowNodesSource", this.buildWorkflowNodeSource(viewDefineTemplate));
                configWithSource.put("workflowSource", configSource);
            }
        }
        return configWithSource.toMap();
    }

    @Override
    public Map<String, Object> getCustomConfigSource(String taskId) {
        JSONObject customConfigSource = new JSONObject();
        customConfigSource.put("workflowDefineSource", this.workflowSettingsTransmit.getCustomConfigSource(taskId));
        return customConfigSource.toMap();
    }

    @Override
    public Map<String, Object> getNodePropertySource(WorkflowSettingsQueryContext context) {
        String workflowNode = context.getWorkflowNode();
        if (workflowNode == null || workflowNode.isEmpty()) {
            return new HashMap<String, Object>();
        }
        if (workflowNode.equals("tsk_upload")) {
            JSONObject reportNodePropertySource = new JSONObject();
            ArrayList<JSONObject> uploadLayerByLayer = new ArrayList<JSONObject>();
            for (UploadLayerByLayerStrategy strategy : UploadLayerByLayerStrategy.values()) {
                JSONObject strategyJson = new JSONObject();
                strategyJson.put(KEY_OF_CODE, (Object)strategy.name());
                strategyJson.put(KEY_OF_TITLE, (Object)strategy.title);
                uploadLayerByLayer.add(strategyJson);
            }
            reportNodePropertySource.put("uploadLayerByLayer", uploadLayerByLayer);
            reportNodePropertySource.put("forceUpload", this.buildRoleSource());
            return reportNodePropertySource.toMap();
        }
        if (workflowNode.equals("tsk_audit")) {
            JSONObject auditNodePropertySource = new JSONObject();
            auditNodePropertySource.put("returnType", (Object)this.getReturnTypeDefaultValue());
            auditNodePropertySource.put("isMultiEntityCaliber", this.sourceUtil.isMultiEntityCaliber(context.getTaskId()));
            return auditNodePropertySource.toMap();
        }
        return new HashMap<String, Object>();
    }

    @Override
    public List<Map<String, Object>> getNodeActionSource(WorkflowSettingsQueryContext context) {
        String workflowNode;
        ArrayList<Map<String, Object>> nodeActionSource = new ArrayList<Map<String, Object>>();
        switch (workflowNode = context.getWorkflowNode()) {
            case "tsk_submit": {
                JSONObject submitAction = new JSONObject();
                submitAction.put(KEY_OF_CODE, (Object)UserActionTemplate.SUBMIT.getCode());
                submitAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.SUBMIT.getTitle());
                nodeActionSource.add(submitAction.toMap());
                JSONObject retrieveAction = new JSONObject();
                retrieveAction.put(KEY_OF_CODE, (Object)UserActionTemplate.RETRIEVE.getCode());
                retrieveAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.RETRIEVE.getTitle());
                nodeActionSource.add(retrieveAction.toMap());
                break;
            }
            case "tsk_upload": {
                JSONObject reportAction = new JSONObject();
                reportAction.put(KEY_OF_CODE, (Object)UserActionTemplate.REPORT.getCode());
                reportAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.REPORT.getTitle());
                nodeActionSource.add(reportAction.toMap());
                if (context.getWorkflowDefineTemplate().equals(WorkflowDefineTemplate.SUBMIT_WORKFLOW.name())) {
                    JSONObject backAction = new JSONObject();
                    backAction.put(KEY_OF_CODE, (Object)UserActionTemplate.BACK.getCode());
                    backAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.BACK.getTitle());
                    nodeActionSource.add(backAction.toMap());
                }
                JSONObject retrieveAction = new JSONObject();
                retrieveAction.put(KEY_OF_CODE, (Object)UserActionTemplate.RETRIEVE.getCode());
                retrieveAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.RETRIEVE.getTitle());
                nodeActionSource.add(retrieveAction.toMap());
                JSONObject applyRejectAction = new JSONObject();
                applyRejectAction.put(KEY_OF_CODE, (Object)UserActionTemplate.APPLY_FOR_REJECT.getCode());
                applyRejectAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.APPLY_FOR_REJECT.getTitle());
                nodeActionSource.add(applyRejectAction.toMap());
                break;
            }
            case "tsk_audit": {
                JSONObject confirmAction = new JSONObject();
                confirmAction.put(KEY_OF_CODE, (Object)UserActionTemplate.CONFIRM.getCode());
                confirmAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.CONFIRM.getTitle());
                nodeActionSource.add(confirmAction.toMap());
                JSONObject cancelConfirmAction = new JSONObject();
                cancelConfirmAction.put(KEY_OF_CODE, (Object)UserActionTemplate.CANCEL_CONFIRM.getCode());
                cancelConfirmAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.CANCEL_CONFIRM.getTitle());
                nodeActionSource.add(cancelConfirmAction.toMap());
                JSONObject rejectAction = new JSONObject();
                rejectAction.put(KEY_OF_CODE, (Object)UserActionTemplate.REJECT.getCode());
                rejectAction.put(KEY_OF_TITLE, (Object)UserActionTemplate.REJECT.getTitle());
                nodeActionSource.add(rejectAction.toMap());
            }
        }
        return nodeActionSource;
    }

    @Override
    public List<Map<String, Object>> getNodeEventSource(WorkflowSettingsQueryContext context) {
        String workflowNode;
        ArrayList<Map<String, Object>> nodeEventSource = new ArrayList<Map<String, Object>>();
        switch (workflowNode = context.getWorkflowNode()) {
            case "tsk_submit": {
                nodeEventSource.add(this.buildActionEventSource(UserActionTemplate.SUBMIT));
                break;
            }
            case "tsk_upload": {
                nodeEventSource.add(this.buildActionEventSource(UserActionTemplate.REPORT));
                if (context.getWorkflowDefineTemplate().equals(WorkflowDefineTemplate.SUBMIT_WORKFLOW.name())) {
                    nodeEventSource.add(this.buildActionEventSource(UserActionTemplate.BACK));
                }
                nodeEventSource.add(this.buildActionEventSource(UserActionTemplate.APPLY_FOR_REJECT));
                break;
            }
            case "tsk_audit": {
                nodeEventSource.add(this.buildActionEventSource(UserActionTemplate.CONFIRM));
                nodeEventSource.add(this.buildActionEventSource(UserActionTemplate.REJECT));
            }
        }
        return nodeEventSource;
    }

    @Override
    public Map<String, Object> getEventSource(String taskId, String eventId) {
        if (this.workflowEventSources == null || this.workflowEventSources.isEmpty()) {
            return Collections.emptyMap();
        }
        for (WorkflowEventSource eventSource : this.workflowEventSources) {
            if (!eventSource.getEventId().equals(eventId)) continue;
            return eventSource.getSource(taskId);
        }
        return Collections.emptyMap();
    }

    @Override
    public List<Map<String, Object>> getNodeParticipantSource(WorkflowSettingsQueryContext context) {
        ArrayList<Map<String, Object>> nodeParticipantSource = new ArrayList<Map<String, Object>>();
        List strategies = this.actorStrategyFactory.queryAllActorStrategyDefinition();
        for (IActorStrategyDefinition strategy : strategies) {
            JSONObject strategyJson = new JSONObject();
            strategyJson.put(KEY_OF_CODE, (Object)strategy.getId());
            strategyJson.put(KEY_OF_TITLE, (Object)strategy.getTitle());
            nodeParticipantSource.add(strategyJson.toMap());
        }
        return nodeParticipantSource;
    }

    @Override
    public boolean isExistWorkflowInstance(String taskKey) {
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
        IProcessEngine processEngine = this.processEngineFactory.getProcessEngine(workflowEngine);
        IProcessRuntimeService processRuntimeService = processEngine.getProcessRuntimeService();
        return processRuntimeService.existsInstance(taskKey);
    }

    @Override
    public Map<String, Object> getSaveTips(String taskId, String workflowType, String submitMode) {
        String tipType;
        String operation;
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(taskId);
        String originalWorkflowEngine = workflowSettingsDO.getWorkflowEngine();
        WorkflowObjectType orginalWorkflowObjectType = workflowSettingsDO.getWorkflowObjectType();
        WorkflowObjectType curWorkflowObjectType = WorkflowObjectType.valueOf((String)submitMode);
        if (!originalWorkflowEngine.equals(workflowType)) {
            operation = "workflowType";
            tipType = "jiuqi.nr.default-1.0".equals(originalWorkflowEngine) && "jiuqi.nr.default".equals(workflowType) ? "transfer" : "clear";
        } else {
            operation = "submitMode";
            tipType = "jiuqi.nr.default".equals(originalWorkflowEngine) ? "transfer" : "clear";
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("operation", operation);
        result.put("tipType", tipType);
        return result;
    }

    private Map<String, Object> buildConfig(DefaultProcessConfig config) {
        WorkflowDefineTemplate defineTemplate = config.getWorkflowDefineTemplate();
        JSONObject workflow = new JSONObject();
        workflow.put("workflowDefineTemplate", (Object)defineTemplate);
        JSONObject workflowNodes = new JSONObject();
        if (defineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
            SubmitNodeConfig submitNodeConfig = config.getSubmitNodeConfig();
            JSONObject submitNodeConfigJson = new JSONObject();
            SubmitProperty submitProperty = submitNodeConfig.getProperty();
            JSONObject submitPropertyJson = new JSONObject();
            submitPropertyJson.put("rename", (Object)submitProperty.getRename());
            submitPropertyJson.put("retrieveOrReturn", submitProperty.getRetrieveOrReturn() == null ? null : submitProperty.getRetrieveOrReturn().code);
            submitNodeConfigJson.put("property", (Object)submitPropertyJson);
            submitNodeConfigJson.put("actions", submitNodeConfig.getActions());
            submitNodeConfigJson.put("events", (Object)this.buildNodeEvents(submitNodeConfig.getEvents()));
            submitNodeConfigJson.put("participant", (Object)this.buildNodeParticipant(submitNodeConfig.getParticipant()));
            workflowNodes.put(UserTaskTemplate.SUBMIT.getCode(), (Object)submitNodeConfigJson);
        }
        ReportNodeConfig reportNodeConfig = config.getReportNodeConfig();
        JSONObject reportNodeConfigJson = new JSONObject();
        ReportProperty reportProperty = reportNodeConfig.getProperty();
        JSONObject reportPropertyJson = new JSONObject();
        reportPropertyJson.put("rename", (Object)reportProperty.getRename());
        reportPropertyJson.put("uploadLayerByLayer", (Object)(reportProperty.getUploadLayerByLayer() == null ? null : reportProperty.getUploadLayerByLayer()));
        reportPropertyJson.put("forceUpload", (Object)(reportProperty.getForceUpload() == null ? null : reportProperty.getForceUpload()));
        reportPropertyJson.put("backDesc", (Object)(reportProperty.getBackDesc() == null ? null : reportProperty.getBackDesc()));
        reportPropertyJson.put("uploadDesc", (Object)(reportProperty.getUploadDesc() == null ? null : reportProperty.getUploadDesc()));
        reportPropertyJson.put("retrieveOrReturn", reportProperty.getRetrieveOrReturn() == null ? null : reportProperty.getRetrieveOrReturn().code);
        reportNodeConfigJson.put("property", (Object)reportPropertyJson);
        reportNodeConfigJson.put("actions", reportNodeConfig.getActions());
        reportNodeConfigJson.put("events", (Object)this.buildNodeEvents(reportNodeConfig.getEvents()));
        reportNodeConfigJson.put("participant", (Object)this.buildNodeParticipant(reportNodeConfig.getParticipant()));
        workflowNodes.put(UserTaskTemplate.REPORT.getCode(), (Object)reportNodeConfigJson);
        AuditNodeConfig auditNodeConfig = config.getAuditNodeConfig();
        JSONObject auditNodeConfigJson = new JSONObject();
        AuditProperty auditProperty = auditNodeConfig.getProperty();
        JSONObject auditPropertyJson = new JSONObject();
        auditPropertyJson.put("rename", (Object)auditProperty.getRename());
        auditPropertyJson.put("isConfirmEnable", (Object)(auditProperty.isConfirmEnable() ? Boolean.valueOf(true) : null));
        auditPropertyJson.put("isReturnLayerByLayer", (Object)(auditProperty.isReturnLayerByLayer() ? Boolean.valueOf(true) : null));
        auditPropertyJson.put("isReturnAllSuperior", (Object)(auditProperty.isReturnAllSuperior() ? Boolean.valueOf(true) : null));
        auditPropertyJson.put("isForceControl", (Object)(auditProperty.isForceControl() ? Boolean.valueOf(true) : null));
        auditPropertyJson.put("returnDesc", (Object)auditProperty.getReturnDesc());
        auditPropertyJson.put("returnType", (Object)auditProperty.getReturnType());
        auditNodeConfigJson.put("property", (Object)auditPropertyJson);
        auditNodeConfigJson.put("actions", auditNodeConfig.getActions());
        auditNodeConfigJson.put("events", (Object)this.buildNodeEvents(auditNodeConfig.getEvents()));
        auditNodeConfigJson.put("participant", (Object)this.buildNodeParticipant(auditNodeConfig.getParticipant()));
        workflowNodes.put(UserTaskTemplate.AUDIT.getCode(), (Object)auditNodeConfigJson);
        workflow.put("workflowNodes", (Object)workflowNodes);
        return workflow.toMap();
    }

    private JSONObject buildNodeEvents(Map<String, List<ActionEvent>> nodeEvents) {
        JSONObject nodeEventsJson = new JSONObject();
        for (String action : nodeEvents.keySet()) {
            JSONObject eventJson = new JSONObject();
            for (ActionEvent actionEvent : nodeEvents.get(action)) {
                if (actionEvent.getEventId().equals("send-message-notice-event")) {
                    JSONObject messageEventParam = this.buildMessageEventParam(new JSONObject(actionEvent.getEventParam()));
                    eventJson.put(actionEvent.getEventId(), (Object)messageEventParam);
                    continue;
                }
                if (actionEvent.getEventId().equals("complete-review-event")) {
                    JSONObject reviewEventJson = this.buildReviewEventParam(new JSONObject(actionEvent.getEventParam()));
                    eventJson.put(actionEvent.getEventId(), (Object)reviewEventJson);
                    continue;
                }
                eventJson.put(actionEvent.getEventId(), (Object)new JSONObject(actionEvent.getEventParam()));
            }
            nodeEventsJson.put(action, (Object)eventJson);
        }
        return nodeEventsJson;
    }

    private JSONObject buildMessageEventParam(JSONObject messageEventJson) {
        for (String type : messageEventJson.keySet()) {
            JSONObject messageJson = messageEventJson.getJSONObject(type);
            ArrayList<JSONObject> transferResult = new ArrayList<JSONObject>();
            List receiver = messageJson.getJSONArray("receiver").toList().stream().map(item -> (HashMap)item).collect(Collectors.toList());
            for (HashMap receiverMap : receiver) {
                JSONObject itemJson = new JSONObject();
                itemJson.put("strategy", receiverMap.get("strategy"));
                itemJson.put("param", (Object)this.transferToUserRoleList(new JSONObject((Map)((HashMap)receiverMap.get("param")))));
                transferResult.add(itemJson);
            }
            messageJson.put("receiver", transferResult);
        }
        return messageEventJson;
    }

    private JSONObject buildReviewEventParam(JSONObject reviewEventJson) {
        List auditTypes;
        ArrayList newErrorHandle = new ArrayList();
        try {
            auditTypes = this.auditTypeDefineService.queryAllAuditType();
        }
        catch (Exception exception) {
            LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
        try {
            Map<String, String> errorTypeValueMap = reviewEventJson.getJSONArray("errorHandle").toList().stream().map(e -> (HashMap)e).collect(Collectors.toMap(e -> (String)e.get(KEY_OF_CODE), e -> (String)e.get("value"), (v1, v2) -> v1));
            for (AuditType auditType : auditTypes) {
                String auditTypeCode = String.valueOf(auditType.getCode());
                HashMap<String, String> errorTypeItem = new HashMap<String, String>();
                errorTypeItem.put(KEY_OF_CODE, auditTypeCode);
                errorTypeItem.put(KEY_OF_TITLE, auditType.getTitle());
                errorTypeItem.put("value", "2");
                if (errorTypeValueMap.containsKey(auditTypeCode)) {
                    errorTypeItem.put("value", errorTypeValueMap.get(auditTypeCode));
                }
                newErrorHandle.add(errorTypeItem);
            }
        }
        catch (JSONException e2) {
            JSONObject errorHandle = reviewEventJson.getJSONObject("errorHandle");
            String hint = errorHandle.getString("hint");
            String warning = errorHandle.getString("warning");
            String error = errorHandle.getString("error");
            try {
                for (AuditType auditType : auditTypes) {
                    String auditTypeCode = String.valueOf(auditType.getCode());
                    HashMap<String, String> errorTypeItem = new HashMap<String, String>();
                    errorTypeItem.put(KEY_OF_CODE, auditTypeCode);
                    errorTypeItem.put(KEY_OF_TITLE, auditType.getTitle());
                    errorTypeItem.put("value", "2");
                    if (auditTypeCode.equals("1")) {
                        errorTypeItem.put("value", hint);
                    } else if (auditTypeCode.equals("2")) {
                        errorTypeItem.put("value", warning);
                    } else if (auditTypeCode.equals("4")) {
                        errorTypeItem.put("value", error);
                    }
                    newErrorHandle.add(errorTypeItem);
                }
            }
            catch (Exception exception) {
                LoggerFactory.getLogger(this.getClass()).error(exception.getMessage(), exception);
                throw new RuntimeException(exception);
            }
        }
        reviewEventJson.put("errorHandle", newErrorHandle);
        return reviewEventJson;
    }

    private JSONObject buildNodeParticipant(NodeParticipant nodeParticipant) {
        JSONObject nodeParticipantJson = new JSONObject();
        JSONObject actionExecuterJson = new JSONObject();
        ParticipantItem actionExecuter = nodeParticipant.getActionExecuter();
        actionExecuterJson.put("strategy", (Object)actionExecuter.getStrategy());
        actionExecuterJson.put("param", (Object)this.transferToUserRoleList(new JSONObject(actionExecuter.getParam())));
        nodeParticipantJson.put("actionExecuter", (Object)actionExecuterJson);
        JSONObject todoReceiverJson = new JSONObject();
        TodoReceiver todoReceiver = nodeParticipant.getTodoReceiver();
        todoReceiverJson.put("type", (Object)todoReceiver.getType());
        ArrayList<JSONObject> transferResult = new ArrayList<JSONObject>();
        List customParticipant = todoReceiver.getCustomParticipant();
        for (ParticipantItem item : customParticipant) {
            JSONObject itemJson = new JSONObject();
            itemJson.put("strategy", (Object)item.getStrategy());
            itemJson.put("param", (Object)this.transferToUserRoleList(new JSONObject(item.getParam())));
            transferResult.add(itemJson);
        }
        todoReceiverJson.put("customParticipant", transferResult);
        nodeParticipantJson.put("todoReceiver", (Object)todoReceiverJson);
        return nodeParticipantJson;
    }

    private JSONObject transferToUserRoleList(JSONObject param) {
        JSONObject resultJson = new JSONObject();
        ArrayList<String> userList = param.isNull("user") ? new ArrayList<String>() : param.getJSONArray("user").toList().stream().map(Object::toString).collect(Collectors.toList());
        ArrayList<String> roleList = param.isNull("role") ? new ArrayList<String>() : param.getJSONArray("role").toList().stream().map(Object::toString).collect(Collectors.toList());
        resultJson.put("userRoleList", this.workflowSettingsTransmit.convertUserRoleList(userList, roleList));
        return resultJson;
    }

    private List<JSONObject> buildSubmitModeSource() {
        ArrayList<JSONObject> submitModeSource = new ArrayList<JSONObject>();
        for (WorkflowObjectType mode : WorkflowObjectType.values()) {
            JSONObject dataMap = new JSONObject();
            dataMap.put(KEY_OF_CODE, (Object)mode.name());
            dataMap.put(KEY_OF_TITLE, (Object)mode.title);
            submitModeSource.add(dataMap);
        }
        return submitModeSource;
    }

    private List<JSONObject> buildWorkflowTypeSource() {
        ArrayList<JSONObject> workflowTypeSource = new ArrayList<JSONObject>();
        List allProcessEngines = this.processEngineFactory.getAllProcessEngines();
        for (IProcessEngine engine : allProcessEngines) {
            if (!this.workflowEngineFilter.isEnabled(engine.getName())) continue;
            JSONObject engineSource = new JSONObject();
            engineSource.put(KEY_OF_CODE, (Object)engine.getName());
            engineSource.put(KEY_OF_TITLE, (Object)engine.getTitle());
            workflowTypeSource.add(engineSource);
        }
        return workflowTypeSource;
    }

    Map<String, Object> buildPeriodType(String taskId) {
        HashMap<String, Object> periodType = new HashMap<String, Object>();
        DesignTaskDefine designTaskDefine = this.designTimeViewController.getTask(taskId);
        String code = designTaskDefine.getDateTime();
        String title = PeriodUtils.isPeriod13((String)code, (PeriodType)designTaskDefine.getPeriodType()) ? "\u6708" : (!PeriodUtils.isDefault((PeriodType)designTaskDefine.getPeriodType()) ? "\u671f" : this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime()).getTitle());
        periodType.put(KEY_OF_CODE, code);
        periodType.put(KEY_OF_TITLE, title);
        return periodType;
    }

    public List<Map<String, Object>> buildRoleSource() {
        ArrayList<Map<String, Object>> roleSource = new ArrayList<Map<String, Object>>();
        List<Role> allRoles = this.workflowSettingsTransmit.getRoleSource();
        for (Role role : allRoles) {
            HashMap<String, String> roleJson = new HashMap<String, String>();
            roleJson.put(KEY_OF_CODE, role.getId());
            roleJson.put(KEY_OF_TITLE, role.getTitle());
            roleSource.add(roleJson);
        }
        return roleSource;
    }

    public String getReturnTypeDefaultValue() {
        List rootEntityGroups = this.entityMetaService.getRootEntityGroups();
        return this.getFirstBaseData(rootEntityGroups);
    }

    private String getFirstBaseData(List<IEntityGroup> rootEntityGroups) {
        if (rootEntityGroups == null || rootEntityGroups.isEmpty()) {
            return null;
        }
        for (IEntityGroup entityGroup : rootEntityGroups) {
            List entityDefines = this.entityMetaService.getEntitiesInGroup(entityGroup.getId());
            if (entityDefines != null && !entityDefines.isEmpty()) {
                return ((IEntityDefine)entityDefines.get(0)).getId();
            }
            String result = this.getFirstBaseData(this.entityMetaService.getChildrenGroup(entityGroup.getId()));
            if (result == null) continue;
            return result;
        }
        return null;
    }

    private Map<String, Object> buildWorkflowDefineSource(String workflowDefine) {
        JSONObject workflowDefineSource = new JSONObject();
        workflowDefineSource.put("key", (Object)workflowDefine);
        ArrayList<JSONObject> templateSource = new ArrayList<JSONObject>();
        for (WorkflowDefineTemplate defineTemplate : WorkflowDefineTemplate.values()) {
            JSONObject defineTemplateJson = new JSONObject();
            defineTemplateJson.put(KEY_OF_CODE, (Object)defineTemplate.name());
            defineTemplateJson.put(KEY_OF_TITLE, (Object)defineTemplate.title);
            templateSource.add(defineTemplateJson);
        }
        workflowDefineSource.put("templateSource", templateSource);
        return workflowDefineSource.toMap();
    }

    private List<JSONObject> buildWorkflowNodeSource(WorkflowDefineTemplate defineTemplate) {
        ArrayList<JSONObject> workflowNodesSource = new ArrayList<JSONObject>();
        if (defineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW)) {
            JSONObject submitNode = new JSONObject();
            submitNode.put(KEY_OF_CODE, (Object)UserTaskTemplate.SUBMIT.getCode());
            submitNode.put(KEY_OF_TITLE, (Object)(UserTaskTemplate.SUBMIT.getTitle() + "\u8282\u70b9"));
            workflowNodesSource.add(submitNode);
        }
        JSONObject reportNode = new JSONObject();
        reportNode.put(KEY_OF_CODE, (Object)UserTaskTemplate.REPORT.getCode());
        reportNode.put(KEY_OF_TITLE, (Object)(UserTaskTemplate.REPORT.getTitle() + "\u8282\u70b9"));
        workflowNodesSource.add(reportNode);
        JSONObject auditNode = new JSONObject();
        auditNode.put(KEY_OF_CODE, (Object)UserTaskTemplate.AUDIT.getCode());
        auditNode.put(KEY_OF_TITLE, (Object)(UserTaskTemplate.AUDIT.getTitle() + "\u8282\u70b9"));
        workflowNodesSource.add(auditNode);
        return workflowNodesSource;
    }

    private Map<String, Object> buildActionEventSource(UserActionTemplate action) {
        JSONObject actionEventSource = new JSONObject();
        actionEventSource.put("actionCode", (Object)action.getCode());
        actionEventSource.put("actionTitle", (Object)action.getTitle());
        ArrayList<JSONObject> events = new ArrayList<JSONObject>();
        List eventKeys = ActionEventRegistry.getEvents((String)action.getCode());
        for (String eventId : eventKeys) {
            IActionEventDefinition actionEventDefinition = this.actionEventFactory.queryActionEventDefinition(eventId);
            JSONObject eventInfo = new JSONObject();
            eventInfo.put(KEY_OF_CODE, (Object)actionEventDefinition.getId());
            eventInfo.put(KEY_OF_TITLE, (Object)actionEventDefinition.getTitle());
            events.add(eventInfo);
        }
        actionEventSource.put("events", events);
        return actionEventSource.toMap();
    }
}

