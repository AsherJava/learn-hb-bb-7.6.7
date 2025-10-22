/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.tree.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.common.CustomDesignWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TreeState {
    private static final Logger logger = LoggerFactory.getLogger(TreeState.class);
    @Autowired
    private CustomWorkFolwService cusFlowService;
    @Autowired
    private WorkflowSettingService flowService;
    @Autowired(required=false)
    Map<String, CustomDesignWorkflow> customDesignProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    ActionAndStateUtil actionAndStateUtil;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private IWorkflow workflow;

    public Map<DimensionValueSet, ActionStateBean> getWorkflowUploadState(DimensionValueSet dimSet, String formKey, String formSchemeKey) {
        WorkflowSettingDefine workflowSetting;
        WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
        if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            formKey = "11111111-1111-1111-1111-111111111111";
        }
        if ((workflowSetting = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey)) != null) {
            CustomDesignWorkflow map = this.map(workflowSetting.getWorkflowId());
            if (map != null) {
                return map.queryWorkflowTreeUploadState(dimSet, formKey, formSchemeKey, formSchemeKey);
            }
            return this.queryUploadStateService.queryUploadStateMap(formSchemeKey, dimSet, formKey, formKey);
        }
        return this.queryUploadStateService.queryUploadStateMap(formSchemeKey, dimSet, formKey, formKey);
    }

    public List<WorkFlowTreeState> getWorkFlowActions(String formSchemeKey) {
        List<WorkFlowTreeState> actions = new ArrayList<WorkFlowTreeState>();
        try {
            CustomDesignWorkflow map;
            WorkflowSettingDefine workflowSetting = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            actions = workflowSetting != null ? ((map = this.map(workflowSetting.getWorkflowId())) != null ? map.queryWorkflowTreeState(workflowSetting.getWorkflowId()) : this.getActions(formSchemeKey)) : this.getActions(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actions;
    }

    private List<WorkFlowTreeState> getActions(String formSchemeKey) {
        List<WorkFlowTreeState> actions = new ArrayList<WorkFlowTreeState>();
        try {
            TaskFlowsDefine flowsSetting;
            FlowsType flowsType;
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null && FlowsType.DEFAULT.equals((Object)(flowsType = (flowsSetting = formScheme.getFlowsSetting()).getFlowsType()))) {
                actions = defaultWorkflow ? this.getDefaultWorkFlowAction(formSchemeKey) : (this.workflow.hasStatisticalNode(formSchemeKey) ? this.getWorkflowStates(formSchemeKey) : this.getCustomWorkFlowAction(formSchemeKey));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actions;
    }

    private List<WorkFlowTreeState> getDefaultWorkFlowAction(String formSchemeKey) {
        ArrayList<WorkFlowTreeState> states = new ArrayList<WorkFlowTreeState>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            if (flowsSetting.isUnitSubmitForCensorship()) {
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.ORIGINAL_SUBMIT.name()));
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.UPLOADED.name()));
                if (flowsSetting.isDataConfirm()) {
                    states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.CONFIRMED.name()));
                }
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.SUBMITED.name()));
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.REJECTED.name()));
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.RETURNED.name()));
            } else {
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.ORIGINAL_UPLOAD.name()));
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.UPLOADED.name()));
                if (flowsSetting.isDataConfirm()) {
                    states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.CONFIRMED.name()));
                }
                states.add(this.WorkFlowTreeState(formSchemeKey, UploadState.REJECTED.name()));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return states;
    }

    private List<WorkFlowTreeState> getCustomWorkFlowAction(String formSchemeKey) {
        ArrayList<WorkFlowTreeState> states = new ArrayList<WorkFlowTreeState>();
        try {
            ArrayList<String> actionCodeList = new ArrayList<String>();
            WorkflowSettingDefine flowSettings = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            if (flowSettings != null && flowSettings.getId() != null) {
                WorkFlowDefine flowDefine = this.cusFlowService.getRunWorkFlowDefineByID(flowSettings.getWorkflowId(), 1);
                List<WorkFlowAction> actions = this.cusFlowService.getRunWorkflowActionsByLinkid(flowDefine.getLinkid());
                for (WorkFlowAction action : actions) {
                    if (!actionCodeList.contains(action.getStateCode())) {
                        WorkFlowTreeState s = new WorkFlowTreeState();
                        s.setTitle(action.getStateName());
                        s.setCode(action.getStateCode());
                        states.add(s);
                    }
                    actionCodeList.add(action.getStateCode());
                }
                List collect = states.stream().map((? super T e) -> e.getCode()).collect(Collectors.toList());
                if (collect != null && collect.size() > 0) {
                    if (collect.contains(UploadState.SUBMITED.toString())) {
                        states.add(0, this.WorkFlowTreeState(formSchemeKey, UploadState.ORIGINAL_SUBMIT.name()));
                    } else if (collect.contains(UploadState.UPLOADED.toString())) {
                        states.add(0, this.WorkFlowTreeState(formSchemeKey, UploadState.ORIGINAL_UPLOAD.name()));
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return states;
    }

    public boolean hasOpenCensorship(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                return flowsSetting.isUnitSubmitForCensorship();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean hasDataConfirm(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                return flowsSetting.isDataConfirm();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private CustomDesignWorkflow map(String processDefineKey) {
        if (this.customDesignProvider != null && this.customDesignProvider.size() > 0) {
            for (Map.Entry<String, CustomDesignWorkflow> customDesign : this.customDesignProvider.entrySet()) {
                CustomDesignWorkflow value = customDesign.getValue();
                if (!value.isApply(processDefineKey)) continue;
                return value;
            }
        }
        return null;
    }

    public WorkFlowTreeState WorkFlowTreeState(String formSchemeKey, String stateCode) {
        WorkFlowTreeState workFlowTreeState = new WorkFlowTreeState();
        workFlowTreeState = new WorkFlowTreeState();
        workFlowTreeState.setCode(stateCode);
        workFlowTreeState.setTitle(this.actionAndStateUtil.getStateNameByStateCode(formSchemeKey, stateCode));
        if (stateCode.equals(UploadState.ORIGINAL_UPLOAD.name()) || stateCode.equals(UploadState.ORIGINAL_SUBMIT.name())) {
            workFlowTreeState.setCode(stateCode + "_" + UploadState.PART_START.name());
        }
        return workFlowTreeState;
    }

    private List<WorkFlowTreeState> getWorkflowStates(String formSchemeKey) {
        ArrayList<WorkFlowTreeState> states = new ArrayList<WorkFlowTreeState>();
        try {
            WorkFlowDefine workFlowDefine;
            ArrayList<String> statisticalNodeKeys = new ArrayList<String>();
            ArrayList<String> actionCodes = new ArrayList<String>();
            WorkflowSettingDefine flowSettings = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            if (flowSettings != null && flowSettings.getId() != null && (workFlowDefine = this.cusFlowService.getWorkFlowDefineByID(flowSettings.getWorkflowId(), 1)) != null && workFlowDefine.getLinkid() != null) {
                List collect;
                List<WorkFlowNodeSet> statisticalNode = this.getStatisticalNode(workFlowDefine.getLinkid());
                for (WorkFlowNodeSet workFlowNodeSet : statisticalNode) {
                    String[] actions;
                    for (String action : actions = workFlowNodeSet.getActions()) {
                        WorkFlowTreeState workFlowTreeState = new WorkFlowTreeState();
                        WorkFlowAction workflowAction = this.cusFlowService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                        String stateCode = workFlowNodeSet.getId() + "@" + workflowAction.getStateCode();
                        if (!actionCodes.contains(stateCode)) {
                            workFlowTreeState.setCode(stateCode);
                            workFlowTreeState.setTitle(workflowAction.getStateName());
                            states.add(workFlowTreeState);
                        }
                        actionCodes.add(stateCode);
                    }
                    statisticalNodeKeys.add(workFlowNodeSet.getId());
                }
                List<WorkFlowNodeSet> workFlowNodeSets = this.cusFlowService.getWorkFlowNodeSets(workFlowDefine.getLinkid());
                if (workFlowNodeSets != null && workFlowNodeSets.size() > 0) {
                    collect = workFlowNodeSets.stream().filter(e -> !statisticalNodeKeys.contains(e.getId())).collect(Collectors.toList());
                    for (WorkFlowNodeSet workFlowNodeSet : collect) {
                        String[] actions = workFlowNodeSet.getActions();
                        if (actions == null || actions.length <= 0) continue;
                        for (String action : actions) {
                            WorkFlowAction workflowAction = this.cusFlowService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                            if (!actionCodes.contains(workflowAction.getStateCode())) {
                                WorkFlowTreeState s = new WorkFlowTreeState();
                                s.setTitle(workflowAction.getStateName());
                                s.setCode(workflowAction.getStateCode());
                                states.add(s);
                            }
                            actionCodes.add(workflowAction.getStateCode());
                        }
                    }
                }
                if ((collect = states.stream().map((? super T e) -> e.getCode()).collect(Collectors.toList())) != null && collect.size() > 0) {
                    if (collect.contains(UploadState.SUBMITED.toString())) {
                        states.add(0, this.WorkFlowTreeState(formSchemeKey, UploadState.ORIGINAL_SUBMIT.name()));
                    } else if (collect.contains(UploadState.UPLOADED.toString())) {
                        states.add(0, this.WorkFlowTreeState(formSchemeKey, UploadState.ORIGINAL_UPLOAD.name()));
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return states;
    }

    private List<WorkFlowNodeSet> getStatisticalNode(String linkId) {
        ArrayList<WorkFlowNodeSet> nodeSets = new ArrayList<WorkFlowNodeSet>();
        List<WorkFlowNodeSet> workFlowNodeSets = this.cusFlowService.getWorkFlowNodeSets(linkId);
        for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
            boolean statisticalNode = workFlowNodeSet.isStatisticalNode();
            if (!statisticalNode) continue;
            nodeSets.add(workFlowNodeSet);
        }
        return nodeSets;
    }
}

