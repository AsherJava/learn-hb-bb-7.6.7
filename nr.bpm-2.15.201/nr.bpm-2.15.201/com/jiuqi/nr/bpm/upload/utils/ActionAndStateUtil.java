/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.upload.utils;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.ActionStateEnum;
import com.jiuqi.nr.bpm.upload.utils.DefaultButton;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class ActionAndStateUtil {
    @Autowired
    private IActionAlias actionAlias;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    @Qualifier(value="process_btn")
    private I18nHelper i18nHelper;
    private final String CHINESE = "zh";
    private final String EMPTY_STR = "";

    private String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (language == null || language.equals("zh")) {
            return "zh";
        }
        return language;
    }

    public String getLanguageName(String actionCode) {
        String language = this.getLanguage();
        if (language.equals("") || language.equals("zh")) {
            return "";
        }
        if (actionCode == null || this.i18nHelper.getMessage(actionCode).equals("")) {
            return "";
        }
        return this.i18nHelper.getMessage(actionCode);
    }

    public String getActionNameByActionCode(String formSchemeKey, String actionCode) {
        Map<String, String> actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
        String actionName = this.getLanguageName(actionCode);
        if (actionCodeAndActionName == null) {
            if (actionName.equals("")) {
                return DefaultButton.formByActionCode(actionCode).getName();
            }
        } else if (actionName.equals("")) {
            return actionCodeAndActionName.get(actionCode);
        }
        return actionName;
    }

    public String getStateNameByActionCode(String formSchemeKey, String actionCode) {
        Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        if (actionCodeAndStateName == null) {
            DefaultButton formType = DefaultButton.formByActionCode(actionCode);
            return formType.getName();
        }
        String actionName = actionCodeAndStateName.get(actionCode);
        if ("act_submit".equals(actionCode) || "act_upload".equals(actionCode) || "act_confirm".equals(actionCode)) {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                String tempName = "\u62a5\u8868" + actionName;
                return tempName;
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                String tempName = "\u5206\u7ec4" + actionName;
                return tempName;
            }
        }
        return actionName;
    }

    public String getStateNameByStateCode(String formSchemeKey, String stateCode) {
        Map<String, String> actionStateCodeAndStateName = this.actionAlias.actionStateCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        String stateName = "";
        if (actionStateCodeAndStateName == null) {
            if (stateCode == null) {
                stateName = this.getLanguageName(ActionStateEnum.ORIGINAL_UPLOAD.getStateCode());
                if (stateName.equals("")) {
                    return ActionStateEnum.ORIGINAL_UPLOAD.getStateName();
                }
            } else {
                stateName = this.getLanguageName(stateCode);
                if (stateName.equals("")) {
                    return ActionStateEnum.formType(stateCode).getStateName(workflowType);
                }
            }
            return stateName;
        }
        String actionName = this.getLanguageName(stateCode);
        if (actionName.equals("")) {
            String name;
            actionName = UploadState.PART_SUBMITED.name().equals(stateCode) ? ((name = actionStateCodeAndStateName.get(UploadState.SUBMITED.toString())).startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name) : (UploadState.PART_UPLOADED.name().equals(stateCode) ? ((name = actionStateCodeAndStateName.get(UploadState.UPLOADED.name())).startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name) : (UploadState.PART_CONFIRMED.name().equals(stateCode) ? ((name = actionStateCodeAndStateName.get(UploadState.CONFIRMED.name())).startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name) : (UploadState.PART_START.name().equals(stateCode) ? actionStateCodeAndStateName.get(UploadState.ORIGINAL_UPLOAD.name()) : actionStateCodeAndStateName.get(stateCode))));
            if (UploadState.PART_SUBMITED.name().equals(stateCode) || UploadState.PART_UPLOADED.name().equals(stateCode) || UploadState.PART_CONFIRMED.name().equals(stateCode)) {
                String tempName;
                if (WorkFlowType.FORM.equals((Object)workflowType)) {
                    tempName = "\u62a5\u8868" + actionName;
                    return tempName;
                }
                if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                    tempName = "\u5206\u7ec4" + actionName;
                    return tempName;
                }
            }
        } else {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                return "report" + actionName;
            }
            if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                return "group" + actionName;
            }
        }
        return actionName;
    }

    public String getStateNameByStateCode(String formSchemeKey, String stateCode, String taskCode) {
        String actionName = null;
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Map<String, String> actionStateCodeAndStateName = this.actionAlias.actionStateCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        if (!defaultWorkflow) {
            if (UploadState.PART_SUBMITED.toString().equals(stateCode) || UploadState.PART_UPLOADED.toString().equals(stateCode) || UploadState.PART_CONFIRMED.toString().equals(stateCode)) {
                actionName = null;
            } else if (UploadState.PART_START.toString().equals(stateCode) || UploadState.ORIGINAL_SUBMIT.toString().equals(stateCode) || UploadState.ORIGINAL_UPLOAD.toString().equals(stateCode)) {
                actionName = this.getLanguage().equals("zh") ? "\u672a\u4e0a\u62a5" : "original upload";
            } else {
                WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1);
                List<WorkFlowLine> workflowLines = this.customWorkFolwService.getWorkflowLinesByLinkid(workFlowDefine.getLinkid());
                for (WorkFlowLine line : workflowLines) {
                    String actionid;
                    WorkFlowAction workflowAction;
                    if (!line.getAfterNodeID().equals(taskCode) && !line.getAfterNodeID().startsWith("End") || (workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid = line.getActionid(), workFlowDefine.getLinkid())) == null || !stateCode.equals(workflowAction.getStateCode()) || !(actionName = this.getLanguageName(workflowAction.getStateCode())).equals("")) continue;
                    actionName = workflowAction.getStateName();
                }
            }
        } else {
            if (actionStateCodeAndStateName == null) {
                String stateName = this.getLanguageName(stateCode);
                if (stateName.equals("")) {
                    return ActionStateEnum.formType(stateCode).getStateName(workflowType);
                }
                return stateName;
            }
            actionName = this.getLanguageName(stateCode);
            if (actionName.equals("")) {
                String name;
                actionName = UploadState.PART_SUBMITED.name().equals(stateCode) ? ((name = actionStateCodeAndStateName.get(UploadState.SUBMITED.toString())).startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name) : (UploadState.PART_UPLOADED.name().equals(stateCode) ? ((name = actionStateCodeAndStateName.get(UploadState.UPLOADED.name())).startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name) : (UploadState.PART_CONFIRMED.name().equals(stateCode) ? ((name = actionStateCodeAndStateName.get(UploadState.CONFIRMED.name())).startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name) : (UploadState.PART_START.name().equals(stateCode) ? actionStateCodeAndStateName.get(UploadState.ORIGINAL_UPLOAD.name()) : actionStateCodeAndStateName.get(stateCode))));
                if (UploadState.PART_SUBMITED.name().equals(stateCode) || UploadState.PART_UPLOADED.name().equals(stateCode) || UploadState.PART_CONFIRMED.name().equals(stateCode)) {
                    String tempName;
                    if (WorkFlowType.FORM.equals((Object)workflowType)) {
                        tempName = "\u62a5\u8868" + actionName;
                        return tempName;
                    }
                    if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                        tempName = "\u5206\u7ec4" + actionName;
                        return tempName;
                    }
                }
            } else if (UploadState.PART_SUBMITED.name().equals(stateCode) || UploadState.PART_UPLOADED.name().equals(stateCode) || UploadState.PART_CONFIRMED.name().equals(stateCode)) {
                if (WorkFlowType.FORM.equals((Object)workflowType)) {
                    return "report" + actionName;
                }
                if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                    return "group" + actionName;
                }
            }
        }
        return actionName;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getNodeNameByCode(String formSchemeKey, String nodeCode) {
        String nodeName = null;
        Map<String, String> nodeAndNodeName = this.actionAlias.nodeAndNodeName(formSchemeKey);
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            if (nodeAndNodeName != null) return nodeAndNodeName.get(nodeCode);
            if (nodeCode == null) return nodeName;
            if ("tsk_submit".equals(nodeCode)) {
                return "\u9001\u5ba1";
            }
            if ("tsk_start".equals(nodeCode)) {
                return "\u5f00\u59cb";
            }
            if ("tsk_upload".equals(nodeCode)) {
                return "\u4e0a\u62a5";
            }
            if ("tsk_audit".equals(nodeCode)) return "\u5ba1\u6279";
            if (!"tsk_audit_after_confirm".equals(nodeCode)) return nodeName;
            return "\u5ba1\u6279";
        }
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        WorkFlowNodeSet workFlowNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(nodeCode, workFlowDefine.getLinkid());
        if (workFlowNodeSet == null) return nodeName;
        if (workFlowNodeSet.getId() == null) return nodeName;
        return workFlowNodeSet.getTitle();
    }

    public String getStateTitleByActionCode(String formSchemeKey, String actionCode, String nodeCode) {
        String stateTitle;
        block8: {
            WorkFlowDefine workFlowDefine;
            block7: {
                stateTitle = null;
                boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
                if (defaultWorkflow) {
                    Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndStateName(formSchemeKey);
                    if (actionCodeAndStateName == null) {
                        if (actionCode.equals("start")) {
                            if ("tsk_submit".equals(nodeCode)) {
                                return "\u672a\u9001\u5ba1";
                            }
                            if ("tsk_upload".equals(nodeCode)) {
                                return "\u672a\u4e0a\u62a5";
                            }
                        }
                        DefaultButton formType = DefaultButton.formByActionCode(actionCode);
                        return formType.getStateName();
                    }
                    return actionCodeAndStateName.get(actionCode);
                }
                WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
                if (!"start".equals(actionCode)) break block7;
                List<WorkFlowAction> workFlowNodeAction = this.customWorkFolwService.getRunWorkFlowNodeAction(nodeCode, workFlowDefine.getLinkid());
                if (workFlowNodeAction == null || workFlowNodeAction.size() <= 0) break block8;
                for (WorkFlowAction workFlowAction : workFlowNodeAction) {
                    stateTitle = "\u672a" + workFlowAction.getActionTitle();
                }
                break block8;
            }
            List<WorkFlowLine> workflowLineByEndNode = this.customWorkFolwService.getWorkflowLineByEndNode(nodeCode, workFlowDefine.getLinkid());
            for (WorkFlowLine wkl : workflowLineByEndNode) {
                String actionId = wkl.getActionid();
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionId, workFlowDefine.getLinkid());
                if (workflowAction == null || !workflowAction.getActionCode().equals(actionCode)) continue;
                stateTitle = workflowAction.getStateName();
            }
        }
        return stateTitle;
    }
}

