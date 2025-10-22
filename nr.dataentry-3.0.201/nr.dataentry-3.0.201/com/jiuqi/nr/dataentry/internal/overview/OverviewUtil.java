/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowLine
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OverviewUtil {
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;

    public boolean confirmBrforeUpload(String formSchemeKey) {
        String workflowId;
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine settingDefine;
        LinkedHashMap<String, List<String>> nodeIdToActions = new LinkedHashMap<String, List<String>>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        if (!defaultWorkflow && (settingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey)) != null && settingDefine.getId() != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowId = settingDefine.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null) {
            List workflowLines = this.customWorkFolwService.getWorkflowLinesByLinkid(workFlowDefine.getLinkid());
            if (workflowLines != null && workflowLines.size() > 0) {
                WorkFlowLine startEvent = workflowLines.stream().filter(e -> e.getBeforeNodeID().contains("StartEvent")).findAny().get();
                String afterNodeID = startEvent.getAfterNodeID();
                int i = 0;
                this.buildNodeToActions(workflowLines, afterNodeID, workFlowDefine.getLinkid(), nodeIdToActions, i);
            }
            return this.calculateOrder(nodeIdToActions);
        }
        return false;
    }

    public void buildNodeToActions(List<WorkFlowLine> workflowLines, String afterNodeID, String linkId, Map<String, List<String>> nodeIdToActions, int i) {
        if (afterNodeID.contains("EndEvent")) {
            return;
        }
        if (i > 25) {
            return;
        }
        List workFlowNodeAction = this.customWorkFolwService.getWorkFlowNodeAction(afterNodeID, linkId);
        if (workFlowNodeAction != null && workFlowNodeAction.size() > 0) {
            List actionCodes = workFlowNodeAction.stream().map(e -> e.getActionCode()).collect(Collectors.toList());
            nodeIdToActions.put(afterNodeID, actionCodes);
        }
        String finalBeforeNodeID = afterNodeID;
        WorkFlowLine startEvent = workflowLines.stream().filter(e -> e.getBeforeNodeID().equals(finalBeforeNodeID)).findAny().get();
        afterNodeID = startEvent.getAfterNodeID();
        this.buildNodeToActions(workflowLines, afterNodeID, linkId, nodeIdToActions, ++i);
    }

    public boolean calculateOrder(Map<String, List<String>> nodeIdToActions) {
        if (nodeIdToActions == null || nodeIdToActions.size() == 0) {
            return false;
        }
        boolean containConfirm = false;
        boolean containUpload = false;
        for (Map.Entry<String, List<String>> entry : nodeIdToActions.entrySet()) {
            List<String> actionCodes = entry.getValue();
            if (actionCodes == null || actionCodes.size() == 0) continue;
            if (containUpload) {
                return false;
            }
            if (containConfirm) {
                return true;
            }
            if (actionCodes.contains("cus_confirm")) {
                containConfirm = true;
            }
            if (!actionCodes.contains("cus_upload")) continue;
            containUpload = true;
        }
        return false;
    }
}

