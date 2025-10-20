/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.DistributeItem
 *  com.jiuqi.va.domain.workflow.business.NodeInfo
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.business.DistributeItem;
import com.jiuqi.va.domain.workflow.business.NodeInfo;
import com.jiuqi.va.domain.workflow.business.TargetObject;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.workflow.constants.DistributionTypeEnum;
import com.jiuqi.va.workflow.constants.WorkflowBusinessRelationConst;
import com.jiuqi.va.workflow.domain.BusinessWorkflowNodesInfoSaveProcess;
import com.jiuqi.va.workflow.service.WorkflowBusinessSaveService;
import com.jiuqi.va.workflow.strategy.distribute.DistributeStrategy;
import com.jiuqi.va.workflow.strategy.distribute.DistributeUtil;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowCacheUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DistributeEditableAttributeStrategy
implements DistributeStrategy {
    private static final Logger log = LoggerFactory.getLogger(DistributeEditableAttributeStrategy.class);
    @Autowired
    private WorkflowBusinessSaveService workflowBusinessSaveService;

    @Override
    public int getDistributeType() {
        return DistributionTypeEnum.EDITABLE_ATTRIBUTE.getValue();
    }

    @Override
    public void executeDistribute(WorkflowBusinessDistributeDTO distributionDTO) {
        BusinessWorkflowNodesInfoSaveProcess processInfo = DistributeUtil.setProcessInfo(distributionDTO);
        List editableFieldList = distributionDTO.getEditableFieldList();
        List editableTableList = distributionDTO.getEditableTableList();
        if (CollectionUtils.isEmpty(editableFieldList) && CollectionUtils.isEmpty(editableTableList)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String currentUserId = ShiroUtil.getUser().getId();
        List targetObjectList = distributionDTO.getTargetObjectList();
        List<WorkflowBusinessDO> workflowBusinessDOList = DistributeUtil.getTargetBindingRelation(targetObjectList);
        Map<String, List<TargetObject>> nodeGroupMap = DistributeUtil.getNodeGroupMap(targetObjectList);
        List<DistributeItem> filteredFieldList = DistributeUtil.getFilteredList(editableFieldList, "editablefield");
        List<DistributeItem> addFieldList = DistributeUtil.getAddItemList(filteredFieldList);
        List<DistributeItem> deleteFieldList = DistributeUtil.getDeleteItemList(filteredFieldList);
        List<DistributeItem> filteredTableList = DistributeUtil.getFilteredList(editableTableList, "editabletable");
        List<DistributeItem> addTableList = DistributeUtil.getAddItemList(filteredTableList);
        List<DistributeItem> deleteTableList = DistributeUtil.getDeleteItemList(filteredTableList);
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOList) {
            List<TargetObject> nodeGroup;
            if (DistributeUtil.shouldSkip(workflowBusinessDO, currentUserId, nodeGroup = nodeGroupMap.get(workflowBusinessDO.getBusinesscode() + "_" + workflowBusinessDO.getWorkflowdefinekey()), processInfo)) continue;
            try {
                Map<String, Object> designDataMap = DistributeUtil.getDesignDataMap(workflowBusinessDO);
                this.updateEditableField(designDataMap, nodeGroup, addFieldList, deleteFieldList);
                this.updateEditableTable(designDataMap, nodeGroup, addTableList, deleteTableList);
                workflowBusinessDO.setDesigndata(JSONUtil.toJSONString(designDataMap));
                this.workflowBusinessSaveService.save(workflowBusinessDO);
                nodeGroup.forEach(node -> {
                    node.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.SUCCESS.getValue());
                    node.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.success"));
                });
            }
            catch (Exception e) {
                log.error("\u4e0b\u53d1\u53ef\u7f16\u8f91\u5c5e\u6027\u65f6\u5f02\u5e38", e);
                nodeGroup.forEach(node -> {
                    node.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.FAILED.getValue());
                    node.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.failed") + e.getMessage());
                });
            }
            int nodeSize = nodeGroup.size();
            processInfo.setCurrIndex(processInfo.getCurrIndex() + nodeSize);
            VaWorkflowCacheUtils.setImportDataResult(processInfo.getRsKey(), JSONUtil.toJSONString((Object)processInfo));
        }
        processInfo.setNodes(targetObjectList);
        VaWorkflowCacheUtils.setImportDataResult(distributionDTO.getResultKey(), JSONUtil.toJSONString((Object)processInfo));
    }

    private void updateEditableField(Map<String, Object> designDataMap, List<TargetObject> nodeGroup, List<DistributeItem> addFieldList, List<DistributeItem> deleteFieldList) {
        if (designDataMap.containsKey("editablefield")) {
            List nodeInfoList = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)designDataMap.get("editablefield")), NodeInfo.class);
            Map<Object, NodeInfo> existNodeMap = nodeInfoList.stream().collect(Collectors.toMap(NodeInfo::getNodeId, o -> o));
            for (TargetObject nodeInfo : nodeGroup) {
                String nodeId = nodeInfo.getNodeId();
                NodeInfo existNode = existNodeMap.get(nodeId);
                if (existNode == null) {
                    NodeInfo newNodeInfo = new NodeInfo();
                    newNodeInfo.setNodeId(nodeInfo.getNodeId());
                    newNodeInfo.setNodeName(nodeInfo.getNodeName());
                    newNodeInfo.setEditableFieldList(addFieldList);
                    nodeInfoList.add(newNodeInfo);
                    continue;
                }
                List newEditableFieldList = existNode.getEditableFieldList();
                List deleteItemNameList = deleteFieldList.stream().map(DistributeItem::getId).collect(Collectors.toList());
                List addItemNameList = addFieldList.stream().map(DistributeItem::getId).collect(Collectors.toList());
                newEditableFieldList.removeIf(item -> deleteItemNameList.contains(item.getId()) || addItemNameList.contains(item.getId()));
                newEditableFieldList.addAll(addFieldList);
                existNode.setEditableFieldList(newEditableFieldList);
            }
            designDataMap.put("editablefield", nodeInfoList);
        } else {
            ArrayList<NodeInfo> newNodeInfoList = new ArrayList<NodeInfo>();
            for (TargetObject nodeInfo : nodeGroup) {
                NodeInfo newNodeInfo = new NodeInfo();
                newNodeInfo.setNodeId(nodeInfo.getNodeId());
                newNodeInfo.setNodeName(nodeInfo.getNodeName());
                newNodeInfo.setEditableFieldList(addFieldList);
                newNodeInfoList.add(newNodeInfo);
            }
            designDataMap.put("editablefield", newNodeInfoList);
        }
    }

    private void updateEditableTable(Map<String, Object> designDataMap, List<TargetObject> nodeGroup, List<DistributeItem> addTableList, List<DistributeItem> deleteTableList) {
        if (designDataMap.containsKey("editabletable")) {
            List nodeInfoList = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)designDataMap.get("editabletable")), NodeInfo.class);
            Map<Object, NodeInfo> existNodeMap = nodeInfoList.stream().collect(Collectors.toMap(NodeInfo::getNodeId, o -> o));
            for (TargetObject nodeInfo : nodeGroup) {
                String nodeId = nodeInfo.getNodeId();
                NodeInfo existNode = existNodeMap.get(nodeId);
                if (existNode == null) {
                    NodeInfo newNodeInfo = new NodeInfo();
                    newNodeInfo.setNodeId(nodeInfo.getNodeId());
                    newNodeInfo.setNodeName(nodeInfo.getNodeName());
                    newNodeInfo.setEditableTableList(addTableList);
                    nodeInfoList.add(newNodeInfo);
                    continue;
                }
                List newEditableTableList = existNode.getEditableTableList();
                List deleteItemNameList = deleteTableList.stream().map(DistributeItem::getTableName).collect(Collectors.toList());
                List addItemNameList = addTableList.stream().map(DistributeItem::getTableName).collect(Collectors.toList());
                newEditableTableList.removeIf(item -> deleteItemNameList.contains(item.getTableName()) || addItemNameList.contains(item.getTableName()));
                newEditableTableList.addAll(addTableList);
                existNode.setEditableTableList(newEditableTableList);
            }
            designDataMap.put("editabletable", nodeInfoList);
        } else {
            ArrayList<NodeInfo> newNodeInfoList = new ArrayList<NodeInfo>();
            for (TargetObject nodeInfo : nodeGroup) {
                NodeInfo newNodeInfo = new NodeInfo();
                newNodeInfo.setNodeId(nodeInfo.getNodeId());
                newNodeInfo.setNodeName(nodeInfo.getNodeName());
                newNodeInfo.setEditableTableList(addTableList);
                newNodeInfoList.add(newNodeInfo);
            }
            designDataMap.put("editabletable", newNodeInfoList);
        }
    }
}

