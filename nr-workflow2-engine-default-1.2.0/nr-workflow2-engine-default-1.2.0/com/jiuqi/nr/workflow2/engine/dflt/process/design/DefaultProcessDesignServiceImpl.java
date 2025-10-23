/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfigImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignDao;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfoImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEventImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfigImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfigImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfigImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipantImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItemImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiverImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.AuditPropertyImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.ReportPropertyImpl;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.SubmitPropertyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessDesignServiceImpl
implements DefaultProcessDesignService {
    @Autowired
    private DefaultProcessDesignDao defaultProcessDesignDao;

    @Override
    public boolean addDefaultProcessConfig(String processId, String config) {
        DefaultProcessDO defaultProcessDO = this.defaultProcessDesignDao.queryDefaultProcessConfig(processId);
        if (defaultProcessDO != null) {
            return false;
        }
        return this.defaultProcessDesignDao.addDefaultProcessConfig(processId, config);
    }

    @Override
    public boolean deleteDefaultProcessConfig(String processId) {
        return this.defaultProcessDesignDao.deleteDefaultProcessConfig(processId);
    }

    @Override
    public boolean updateDefaultProcessConfig(String processId, String config) {
        DefaultProcessDO defaultProcessDO = this.defaultProcessDesignDao.queryDefaultProcessConfig(processId);
        if (defaultProcessDO == null) {
            return false;
        }
        return this.defaultProcessDesignDao.updateDefaultProcessConfig(processId, config);
    }

    @Override
    public DefaultProcessConfig queryDefaultProcessConfig(String processId) {
        DefaultProcessDO defaultProcessDO = this.defaultProcessDesignDao.queryDefaultProcessConfig(processId);
        if (defaultProcessDO == null) {
            return null;
        }
        return this.parseConfig(defaultProcessDO.getConfig());
    }

    private DefaultProcessConfig parseConfig(String config) {
        DefaultProcessConfigImpl defaultProcessConfig = new DefaultProcessConfigImpl();
        JSONObject workflow = new JSONObject(config);
        WorkflowDefineTemplate workflowDefineTemplate = WorkflowDefineTemplate.valueOf(workflow.getString("workflowDefineTemplate"));
        JSONObject workflowNodes = workflow.getJSONObject("workflowNodes");
        SubmitNodeConfig submitNodeConfig = null;
        ReportNodeConfig reportNodeConfig = null;
        AuditNodeConfig auditNodeConfig = null;
        Iterator iterator = workflowNodes.keySet().iterator();
        while (iterator.hasNext()) {
            String workflowNode;
            switch (workflowNode = (String)iterator.next()) {
                case "tsk_submit": {
                    JSONObject submitNodeConfigJson = workflowNodes.getJSONObject(workflowNode);
                    submitNodeConfig = this.parseSubmitNodeConfig(submitNodeConfigJson);
                    break;
                }
                case "tsk_upload": {
                    JSONObject reportNodeConfigJson = workflowNodes.getJSONObject(workflowNode);
                    reportNodeConfig = this.parseReportNodeConfig(reportNodeConfigJson);
                    break;
                }
                case "tsk_audit": {
                    JSONObject auditNodeConfigJson = workflowNodes.getJSONObject(workflowNode);
                    auditNodeConfig = this.parseAuditNodeConfig(auditNodeConfigJson);
                }
            }
        }
        defaultProcessConfig.setWorkflowDefineTemplate(workflowDefineTemplate);
        defaultProcessConfig.setSubmitNodeConfig(submitNodeConfig);
        defaultProcessConfig.setReportNodeConfig(reportNodeConfig);
        defaultProcessConfig.setAuditNodeConfig(auditNodeConfig);
        return defaultProcessConfig;
    }

    private SubmitNodeConfig parseSubmitNodeConfig(JSONObject submitNodeConfigJson) {
        JSONObject submitPropertyJson = submitNodeConfigJson.getJSONObject("property");
        String submitRename = submitPropertyJson.getString("rename");
        String retrieveOrReturn = submitPropertyJson.isNull("retrieveOrReturn") ? null : submitPropertyJson.getString("retrieveOrReturn");
        RetrieveStrategy retrieveStrategy = null;
        if (retrieveOrReturn != null) {
            if (retrieveOrReturn.equals(RetrieveStrategy.RETRIEVE_SELF.code)) {
                retrieveStrategy = RetrieveStrategy.RETRIEVE_SELF;
            } else if (retrieveOrReturn.equals(RetrieveStrategy.APPLY_RETURN.code)) {
                retrieveStrategy = RetrieveStrategy.APPLY_RETURN;
            }
        }
        SubmitPropertyImpl submitPropertyImpl = new SubmitPropertyImpl();
        submitPropertyImpl.setRename(submitRename);
        submitPropertyImpl.setRetrieveOrReturn(retrieveStrategy);
        SubmitNodeConfigImpl submitNodeConfig = new SubmitNodeConfigImpl();
        submitNodeConfig.setProperty(submitPropertyImpl);
        JSONObject submitActionsJson = submitNodeConfigJson.getJSONObject("actions");
        submitNodeConfig.setActions(this.parseActionConfig(submitActionsJson));
        JSONObject submitEventsJson = submitNodeConfigJson.getJSONObject("events");
        submitNodeConfig.setEvents(this.parseEventConfig(submitEventsJson));
        JSONObject submitParticipantJson = submitNodeConfigJson.getJSONObject("participant");
        submitNodeConfig.setParticipant(this.parseNodeParticipant(submitParticipantJson));
        return submitNodeConfig;
    }

    private ReportNodeConfig parseReportNodeConfig(JSONObject reportNodeConfigJson) {
        JSONObject reportPropertyJson = reportNodeConfigJson.getJSONObject("property");
        String reportRename = reportPropertyJson.getString("rename");
        String uploadLayerByLayer = reportPropertyJson.isNull("uploadLayerByLayer") ? null : reportPropertyJson.getString("uploadLayerByLayer");
        String forceUpload = reportPropertyJson.isNull("forceUpload") ? null : reportPropertyJson.getString("forceUpload");
        FillInDescStrategy backDesc = reportPropertyJson.isNull("backDesc") ? null : FillInDescStrategy.valueOf(reportPropertyJson.getString("backDesc"));
        FillInDescStrategy uploadDesc = reportPropertyJson.isNull("uploadDesc") ? null : FillInDescStrategy.valueOf(reportPropertyJson.getString("uploadDesc"));
        String retrieveOrReturn = reportPropertyJson.isNull("retrieveOrReturn") ? null : reportPropertyJson.getString("retrieveOrReturn");
        RetrieveStrategy retrieveStrategy = null;
        if (retrieveOrReturn != null) {
            if (retrieveOrReturn.equals(RetrieveStrategy.RETRIEVE_SELF.code)) {
                retrieveStrategy = RetrieveStrategy.RETRIEVE_SELF;
            } else if (retrieveOrReturn.equals(RetrieveStrategy.APPLY_RETURN.code)) {
                retrieveStrategy = RetrieveStrategy.APPLY_RETURN;
            }
        }
        ReportPropertyImpl reportProperty = new ReportPropertyImpl();
        reportProperty.setRename(reportRename);
        reportProperty.setUploadLayerByLayer(uploadLayerByLayer == null ? null : UploadLayerByLayerStrategy.valueOf(uploadLayerByLayer));
        reportProperty.setForceUpload(forceUpload);
        reportProperty.setBackDesc(backDesc);
        reportProperty.setUploadDesc(uploadDesc);
        reportProperty.setRetrieveOrReturn(retrieveStrategy);
        ReportNodeConfigImpl reportNodeConfig = new ReportNodeConfigImpl();
        reportNodeConfig.setProperty(reportProperty);
        JSONObject reportActionsJson = reportNodeConfigJson.getJSONObject("actions");
        reportNodeConfig.setActions(this.parseActionConfig(reportActionsJson));
        JSONObject reportEventsJson = reportNodeConfigJson.getJSONObject("events");
        reportNodeConfig.setEvents(this.parseEventConfig(reportEventsJson));
        JSONObject reportParticipantJson = reportNodeConfigJson.getJSONObject("participant");
        reportNodeConfig.setParticipant(this.parseNodeParticipant(reportParticipantJson));
        return reportNodeConfig;
    }

    private AuditNodeConfig parseAuditNodeConfig(JSONObject auditNodeConfigJson) {
        JSONObject auditPropertyJson = auditNodeConfigJson.getJSONObject("property");
        String auditRename = auditPropertyJson.getString("rename");
        boolean isConfirmEnable = !auditPropertyJson.isNull("isConfirmEnable");
        boolean isReturnLayerByLayer = !auditPropertyJson.isNull("isReturnLayerByLayer");
        boolean isReturnAllSuperior = !auditPropertyJson.isNull("isReturnAllSuperior");
        boolean isForceControl = !auditPropertyJson.isNull("isForceControl");
        FillInDescStrategy returnDesc = auditPropertyJson.isNull("returnDesc") ? null : FillInDescStrategy.valueOf(auditPropertyJson.getString("returnDesc"));
        String returnType = auditPropertyJson.isNull("returnType") ? null : auditPropertyJson.getString("returnType");
        AuditPropertyImpl auditProperty = new AuditPropertyImpl();
        auditProperty.setRename(auditRename);
        auditProperty.setConfirmEnable(isConfirmEnable);
        auditProperty.setReturnLayerByLayer(isReturnLayerByLayer);
        auditProperty.setReturnAllSuperior(isReturnAllSuperior);
        auditProperty.setForceControl(isForceControl);
        auditProperty.setReturnDesc(returnDesc);
        auditProperty.setReturnType(returnType);
        AuditNodeConfigImpl auditNodeConfig = new AuditNodeConfigImpl();
        auditNodeConfig.setProperty(auditProperty);
        JSONObject auditActionsJson = auditNodeConfigJson.getJSONObject("actions");
        auditNodeConfig.setActions(this.parseActionConfig(auditActionsJson));
        JSONObject auditEventsJson = auditNodeConfigJson.getJSONObject("events");
        auditNodeConfig.setEvents(this.parseEventConfig(auditEventsJson));
        JSONObject auditParticipantJson = auditNodeConfigJson.getJSONObject("participant");
        auditNodeConfig.setParticipant(this.parseNodeParticipant(auditParticipantJson));
        return auditNodeConfig;
    }

    private Map<String, ActionInfo> parseActionConfig(JSONObject actionsJson) {
        HashMap<String, ActionInfo> actions = new HashMap<String, ActionInfo>();
        for (String actionCode : actionsJson.keySet()) {
            JSONObject actionInfoJson = actionsJson.getJSONObject(actionCode);
            ActionInfoImpl actionInfo = new ActionInfoImpl();
            actionInfo.setButtonName(actionInfoJson.getString("buttonName"));
            actionInfo.setStateName(actionInfoJson.has("stateName") ? actionInfoJson.getString("stateName") : null);
            actions.put(actionCode, actionInfo);
        }
        return actions;
    }

    private Map<String, List<ActionEvent>> parseEventConfig(JSONObject eventsJson) {
        HashMap<String, List<ActionEvent>> events = new HashMap<String, List<ActionEvent>>();
        for (String actionCode : eventsJson.keySet()) {
            ArrayList<ActionEventImpl> actionEvents = new ArrayList<ActionEventImpl>();
            JSONObject actionEventJson = eventsJson.getJSONObject(actionCode);
            for (String eventId : actionEventJson.keySet()) {
                JSONObject eventParam = actionEventJson.getJSONObject(eventId);
                ActionEventImpl actionEvent = new ActionEventImpl();
                actionEvent.setEventId(eventId);
                actionEvent.setEventParam(eventParam.toString());
                actionEvents.add(actionEvent);
            }
            events.put(actionCode, actionEvents);
        }
        return events;
    }

    private NodeParticipant parseNodeParticipant(JSONObject participantJson) {
        NodeParticipantImpl participant = new NodeParticipantImpl();
        JSONObject actionExecuterJson = participantJson.getJSONObject("actionExecuter");
        JSONObject todoReceiverJson = participantJson.getJSONObject("todoReceiver");
        String strategy = actionExecuterJson.getString("strategy");
        JSONObject param = actionExecuterJson.getJSONObject("param");
        ParticipantItemImpl actionExecuter = new ParticipantItemImpl();
        actionExecuter.setStrategy(strategy);
        actionExecuter.setParam(param.toString());
        participant.setActionExecuter(actionExecuter);
        TodoReceiverStrategy type = TodoReceiverStrategy.valueOf(todoReceiverJson.getString("type"));
        List<ParticipantItem> customStrategy = new ArrayList<ParticipantItem>();
        JSONArray customParticipant = todoReceiverJson.getJSONArray("customParticipant");
        if (customParticipant != null && !customParticipant.isEmpty()) {
            customStrategy = customParticipant.toList().stream().map(e -> {
                HashMap itemMap = (HashMap)e;
                String itemStrategy = itemMap.get("strategy").toString();
                HashMap itemParam = (HashMap)itemMap.get("param");
                JSONObject itemParamJson = new JSONObject();
                itemParam.forEach((arg_0, arg_1) -> ((JSONObject)itemParamJson).put(arg_0, arg_1));
                ParticipantItemImpl customItem = new ParticipantItemImpl();
                customItem.setStrategy(itemStrategy);
                customItem.setParam(itemParamJson.toString());
                return customItem;
            }).collect(Collectors.toList());
        }
        TodoReceiverImpl todoReceiver = new TodoReceiverImpl();
        todoReceiver.setType(type);
        todoReceiver.setCustomParticipant(customStrategy);
        participant.setTodoReceiver(todoReceiver);
        return participant;
    }
}

