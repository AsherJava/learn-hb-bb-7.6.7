/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.common.CustomDesignWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.tree.NodeInfo;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.NodeColorEnum;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeNodeIconColorServiceImpl
implements ITreeNodeIconColorService {
    private static final String TREENODEID = "nr-tree-node-id";
    private static final String TREENODEICONCOLOR = "TREE-NODE-ICON-COLOR";
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired(required=false)
    Map<String, CustomDesignWorkflow> customDesignProvider;
    @Autowired
    private WorkflowSettingService flowService;
    @Autowired
    private CustomWorkFolwService workflowCustomService;
    @Autowired
    private IWorkflow workflow;
    private String systemOptions;
    private NodeInfo nodeData;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<TreeNodeColorInfo> getStateNodeIconColor(String formSchemeKey) {
        List<Object> allNodeIconColor = new ArrayList();
        WorkflowSettingDefine workflowSetting = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowSetting != null) {
            CustomDesignWorkflow map = this.map(workflowSetting.getWorkflowId());
            if (map != null) {
                List<WorkFlowTreeState> workflowTreeStates = map.queryWorkflowTreeState(workflowSetting.getWorkflowId());
                if (workflowTreeStates != null && workflowTreeStates.size() > 0) {
                    for (WorkFlowTreeState workFlowTreeState : workflowTreeStates) {
                        TreeNodeColorInfo treeNodeColorInfo = new TreeNodeColorInfo();
                        treeNodeColorInfo.setId(workFlowTreeState.getCode());
                        treeNodeColorInfo.setStateName(workFlowTreeState.getTitle());
                        treeNodeColorInfo.setState(workFlowTreeState.getCode());
                        allNodeIconColor.add(treeNodeColorInfo);
                    }
                }
            } else {
                allNodeIconColor = this.getStateCode(formSchemeKey);
            }
        } else {
            allNodeIconColor = this.getStateCode(formSchemeKey);
        }
        return allNodeIconColor;
    }

    private List<TreeNodeColorInfo> getStateCode(String formSchemeKey) {
        ArrayList<TreeNodeColorInfo> newTreeNodeColors = new ArrayList<TreeNodeColorInfo>();
        ArrayList<String> treeNodeCodes = new ArrayList<String>();
        List<TreeNodeColorInfo> allNodeIconColor = this.getAllNodeIconColor();
        List<TreeNodeColorInfo> stateAndColor = this.getStateAndColor(formSchemeKey);
        if (this.workflow.hasStatisticalNode(formSchemeKey)) {
            if (stateAndColor != null && stateAndColor.size() > 0) {
                for (TreeNodeColorInfo nodeColorInfo : stateAndColor) {
                    if (treeNodeCodes.contains(nodeColorInfo.getState())) continue;
                    treeNodeCodes.add(nodeColorInfo.getState());
                    newTreeNodeColors.add(nodeColorInfo);
                }
            }
            TreeNodeColorInfo treeNodeColorInfo = new TreeNodeColorInfo();
            treeNodeColorInfo.setState(UploadState.ORIGINAL_UPLOAD.name());
            treeNodeColorInfo.setStateName("\u672a\u4e0a\u62a5");
            newTreeNodeColors.add(treeNodeColorInfo);
        }
        for (TreeNodeColorInfo treeNodeColorInfo : allNodeIconColor) {
            if (treeNodeCodes.contains(treeNodeColorInfo.getState())) continue;
            treeNodeCodes.add(treeNodeColorInfo.getState());
            newTreeNodeColors.add(treeNodeColorInfo);
        }
        return newTreeNodeColors;
    }

    @Override
    public List<TreeNodeColorInfo> getAllNodeIconColor() {
        NodeInfo node = this.getNodeData();
        if (node != null) {
            return node.getInfoItems();
        }
        return new ArrayList<TreeNodeColorInfo>();
    }

    public NodeInfo getNodeData() {
        String systemStr = this.nvwaSystemOptionService.get(TREENODEID, TREENODEICONCOLOR);
        if (StringUtils.isNotEmpty((String)systemStr) && !systemStr.equals(this.systemOptions)) {
            try {
                this.nodeData = (NodeInfo)this.objectMapper.readValue(systemStr, NodeInfo.class);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
            this.systemOptions = systemStr;
        }
        return this.nodeData;
    }

    @Override
    public boolean isNodeIconType() {
        boolean icon = false;
        NodeInfo nodeInfo = this.getNodeData();
        String type = nodeInfo.getType();
        if (NodeColorEnum.Type.ICON.name().equals(type)) {
            icon = true;
        }
        return icon;
    }

    @Override
    public TreeNodeColorInfo getNodeIconColorByUploadState(UploadState uploadState) {
        List<TreeNodeColorInfo> allNodeIconColor = this.getAllNodeIconColor();
        if (allNodeIconColor.size() > 0) {
            for (TreeNodeColorInfo treeNodeColorInfo : allNodeIconColor) {
                if (UploadState.PART_START.name().equals(uploadState.name())) {
                    Optional<TreeNodeColorInfo> findFirst = allNodeIconColor.stream().filter(e -> e.getState().equals(UploadState.ORIGINAL_UPLOAD.name())).findFirst();
                    return findFirst.get();
                }
                if (!treeNodeColorInfo.getState().equals(uploadState.name())) continue;
                return treeNodeColorInfo;
            }
        }
        return null;
    }

    @Override
    public TreeNodeColorInfo getNodeIconColorByUploadState(String uploadState) {
        List<TreeNodeColorInfo> allNodeIconColor = this.getAllNodeIconColor();
        if (allNodeIconColor.size() > 0) {
            for (TreeNodeColorInfo treeNodeColorInfo : allNodeIconColor) {
                if (UploadState.PART_START.name().equals(uploadState)) {
                    Optional<TreeNodeColorInfo> findFirst = allNodeIconColor.stream().filter(e -> e.getState().equals(UploadState.ORIGINAL_UPLOAD.name())).findFirst();
                    return findFirst.get();
                }
                if (!treeNodeColorInfo.getState().equals(uploadState)) continue;
                return treeNodeColorInfo;
            }
        }
        return null;
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

    private List<TreeNodeColorInfo> getStateAndColor(String formSchemeKey) {
        WorkFlowDefine workFlowDefine;
        ArrayList<TreeNodeColorInfo> treeNodeColorInfos = new ArrayList<TreeNodeColorInfo>();
        TreeNodeColorInfo treeNodeColorInfo = null;
        WorkflowSettingDefine workflowSettingDefine = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowSettingDefine != null && (workFlowDefine = this.workflowCustomService.getWorkFlowDefineByID(workflowSettingDefine.getWorkflowId(), 1)) != null && workFlowDefine.getLinkid() != null) {
            List<WorkFlowNodeSet> statisticalNode = this.getStatisticalNode(workFlowDefine.getLinkid());
            for (WorkFlowNodeSet workFlowNodeSet : statisticalNode) {
                String[] actions;
                String nodeId = workFlowNodeSet.getId();
                for (String actionId : actions = workFlowNodeSet.getActions()) {
                    JSONObject actionConfig;
                    treeNodeColorInfo = new TreeNodeColorInfo();
                    WorkFlowAction workflowAction = this.workflowCustomService.getWorkflowActionById(actionId, workFlowDefine.getLinkid());
                    String actionCode = workflowAction.getActionCode();
                    String exset = workflowAction.getExset();
                    if (exset != null && exset.length() > 0 && (actionConfig = new JSONObject(exset)).has("stateColor")) {
                        String stateColor = actionConfig.getString("stateColor");
                        treeNodeColorInfo.setColor(stateColor);
                    }
                    treeNodeColorInfo.setState(nodeId + "@" + workflowAction.getStateCode());
                    treeNodeColorInfo.setStateName(workflowAction.getStateName());
                    if (treeNodeColorInfo == null) continue;
                    treeNodeColorInfos.add(treeNodeColorInfo);
                }
            }
        }
        return treeNodeColorInfos;
    }

    private List<WorkFlowNodeSet> getStatisticalNode(String linkId) {
        ArrayList<WorkFlowNodeSet> nodeSets = new ArrayList<WorkFlowNodeSet>();
        List<WorkFlowNodeSet> workFlowNodeSets = this.workflowCustomService.getWorkFlowNodeSets(linkId);
        for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
            boolean statisticalNode = workFlowNodeSet.isStatisticalNode();
            if (!statisticalNode) continue;
            nodeSets.add(workFlowNodeSet);
        }
        return nodeSets;
    }
}

