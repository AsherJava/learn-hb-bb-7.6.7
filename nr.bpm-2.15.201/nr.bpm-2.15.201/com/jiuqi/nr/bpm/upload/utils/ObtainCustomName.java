/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.upload.utils;

import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
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
import org.springframework.stereotype.Component;

@Component
public class ObtainCustomName {
    @Autowired
    private IActionAlias actionAlias;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;

    public String getActionNameByActionCode(String formSchemeKey, String actionCode, String taskCode) {
        String actionName = null;
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Map<String, String> actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
        if (!defaultWorkflow) {
            WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            WorkFlowDefine runWorkFlowDefineByID = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
            WorkFlowAction actionInfo = this.customWorkFolwService.getWorkflowActionByCode(taskCode, actionCode, runWorkFlowDefineByID.getLinkid());
            if (null != actionInfo) {
                actionName = actionInfo.getActionTitle();
            }
            return actionName;
        }
        if (actionCodeAndActionName == null) {
            DefaultButton formType = DefaultButton.formByActionCode(actionCode);
            return formType.getName();
        }
        actionName = actionCodeAndActionName.get(actionCode);
        return actionName;
    }

    public String getStateNameByActionCode(String formSchemeKey, String actionCode, String taskCode) {
        String actionName = null;
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        if (!defaultWorkflow) {
            WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            WorkFlowDefine workFlowDefineByID = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
            WorkFlowAction actionInfo = this.customWorkFolwService.getWorkflowActionByCode(taskCode, actionCode, workFlowDefineByID.getLinkid());
            if (null != actionInfo) {
                actionName = actionInfo.getStateName();
            }
            return actionName;
        }
        if (actionCodeAndStateName == null) {
            DefaultButton formType = DefaultButton.formByActionCode(actionCode);
            return formType.getName();
        }
        actionName = actionCodeAndStateName.get(actionCode);
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

    public String getStateNameByStateCode(String formSchemeKey, String stateCode, String taskCode) {
        String actionName = null;
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Map<String, String> actionStateCodeAndStateName = this.actionAlias.actionStateCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        if (!defaultWorkflow) {
            if (UploadState.PART_SUBMITED.toString().equals(stateCode) || UploadState.PART_UPLOADED.toString().equals(stateCode) || UploadState.PART_CONFIRMED.toString().equals(stateCode)) {
                actionName = null;
            } else if (UploadState.PART_START.toString().equals(stateCode) || UploadState.ORIGINAL_UPLOAD.toString().equals(stateCode)) {
                actionName = "\u672a\u4e0a\u62a5";
            } else if (UploadState.ORIGINAL_SUBMIT.toString().equals(stateCode)) {
                actionName = "\u672a\u9001\u5ba1";
            } else {
                WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                if (workflowDefine != null && workflowDefine.getId() != null) {
                    WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1);
                    List<WorkFlowLine> workflowLines = this.customWorkFolwService.getWorkflowLinesByLinkid(workFlowDefine.getLinkid());
                    for (WorkFlowLine line : workflowLines) {
                        String actionid;
                        WorkFlowAction workflowAction;
                        if (!line.getAfterNodeID().equals(taskCode) && !line.getAfterNodeID().startsWith("End") || (workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid = line.getActionid(), workFlowDefine.getLinkid())) == null || stateCode == null || !stateCode.equals(workflowAction.getStateCode())) continue;
                        actionName = workflowAction.getStateName();
                    }
                }
            }
        } else {
            String name;
            if (actionStateCodeAndStateName == null) {
                ActionStateEnum formType = ActionStateEnum.formType(stateCode);
                return formType.getStateName(workflowType);
            }
            actionName = actionStateCodeAndStateName.get(stateCode);
            if (UploadState.PART_SUBMITED.name().equals(stateCode)) {
                name = actionStateCodeAndStateName.get(UploadState.SUBMITED.toString());
                actionName = name.startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name;
            } else if (UploadState.PART_UPLOADED.name().equals(stateCode)) {
                name = actionStateCodeAndStateName.get(UploadState.UPLOADED.name());
                actionName = name.startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name;
            } else if (UploadState.PART_CONFIRMED.name().equals(stateCode)) {
                name = actionStateCodeAndStateName.get(UploadState.CONFIRMED.name());
                actionName = name.startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name;
            } else if (UploadState.PART_START.name().equals(stateCode)) {
                actionName = actionStateCodeAndStateName.get(UploadState.ORIGINAL_UPLOAD.name());
            }
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
        }
        return actionName;
    }

    public ActionStateBean getActionStateByActionCode(String formSchemeKey, String actionCode, String taskCode) {
        ActionStateBean actionStateBean = new ActionStateBean();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        if (!defaultWorkflow) {
            WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            WorkFlowDefine runWorkFlowDefineByID = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
            WorkFlowAction actionInfo = this.customWorkFolwService.getWorkflowActionByCode(taskCode, actionCode, runWorkFlowDefineByID.getLinkid());
            if (null != actionInfo) {
                String stateName = actionInfo.getStateName();
                actionStateBean.setTitile(stateName);
                actionStateBean.setCode(actionInfo.getStateCode());
            }
        } else if (actionCodeAndStateName == null) {
            DefaultButton formType = DefaultButton.formByActionCode(actionCode);
            actionStateBean.setTitile(formType.getName());
            actionStateBean.setCode(formType.getStateCode());
        } else {
            String stateName = actionCodeAndStateName.get(actionCode);
            if ("act_submit".equals(actionCode)) {
                String stateCode = ActionStateEnum.PART_SUBMITED.getStateCode();
                actionStateBean.setTitile(this.getStateName(workflowType, stateName));
                actionStateBean.setCode(stateCode);
            } else if ("act_upload".equals(actionCode)) {
                String stateCode = ActionStateEnum.PART_UPLOADED.getStateCode();
                actionStateBean.setTitile(this.getStateName(workflowType, stateName));
                actionStateBean.setCode(stateCode);
            } else if ("act_confirm".equals(actionCode)) {
                String stateCode = ActionStateEnum.PART_CONFIRMED.getStateCode();
                actionStateBean.setTitile(this.getStateName(workflowType, stateName));
                actionStateBean.setCode(stateCode);
            }
        }
        return actionStateBean;
    }

    private String getStateName(WorkFlowType workflowType, String name) {
        if (WorkFlowType.FORM.equals((Object)workflowType)) {
            String tempName = "\u62a5\u8868" + name;
            return tempName;
        }
        if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            String tempName = "\u5206\u7ec4" + name;
            return tempName;
        }
        return name;
    }

    public String getActionNameByActionCode(String formSchemeKey, String actionCode) {
        Map<String, String> actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
        if (actionCodeAndActionName == null) {
            DefaultButton formType = DefaultButton.formByActionCode(actionCode);
            return formType.getName();
        }
        String actionName = actionCodeAndActionName.get(actionCode);
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
        String name;
        Map<String, String> actionStateCodeAndStateName = this.actionAlias.actionStateCodeAndStateName(formSchemeKey);
        WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
        if (actionStateCodeAndStateName == null) {
            if (stateCode == null) {
                return ActionStateEnum.ORIGINAL_UPLOAD.getStateName();
            }
            ActionStateEnum formType = ActionStateEnum.formType(stateCode);
            return formType.getStateName(workflowType);
        }
        String actionName = actionStateCodeAndStateName.get(stateCode);
        if (UploadState.PART_SUBMITED.name().equals(stateCode)) {
            name = actionStateCodeAndStateName.get(UploadState.SUBMITED.toString());
            actionName = name.startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name;
        } else if (UploadState.PART_UPLOADED.name().equals(stateCode)) {
            name = actionStateCodeAndStateName.get(UploadState.UPLOADED.name());
            actionName = name.startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name;
        } else if (UploadState.PART_CONFIRMED.name().equals(stateCode)) {
            name = actionStateCodeAndStateName.get(UploadState.CONFIRMED.name());
            actionName = name.startsWith("\u5df2") || name.startsWith("\u672a") ? name.substring(1, name.length()) : name;
        } else if (UploadState.PART_START.name().equals(stateCode)) {
            actionName = actionStateCodeAndStateName.get(UploadState.ORIGINAL_UPLOAD.name());
        }
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
        return actionName;
    }
}

