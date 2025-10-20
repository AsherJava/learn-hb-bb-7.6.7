/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.NodeInfo
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
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
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DistributeInterfaceSchemaStrategy
implements DistributeStrategy {
    private static final Logger log = LoggerFactory.getLogger(DistributeInterfaceSchemaStrategy.class);
    @Autowired
    private WorkflowBusinessSaveService workflowBusinessSaveService;

    @Override
    public int getDistributeType() {
        return DistributionTypeEnum.INTERFACE_SCHEMA.getValue();
    }

    @Override
    public void executeDistribute(WorkflowBusinessDistributeDTO distributionDTO) {
        BusinessWorkflowNodesInfoSaveProcess processInfo = DistributeUtil.setProcessInfo(distributionDTO);
        String billSchemeCode = distributionDTO.getBillSchemeCode();
        String billReadSchemeCode = distributionDTO.getBillReadSchemeCode();
        String approveSchemeCode = distributionDTO.getApproveSchemeCode();
        if (!(StringUtils.hasText(billSchemeCode) || StringUtils.hasText(billReadSchemeCode) || StringUtils.hasText(approveSchemeCode))) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String currentUserId = ShiroUtil.getUser().getId();
        List targetObjectList = distributionDTO.getTargetObjectList();
        List<WorkflowBusinessDO> workflowBusinessDOList = DistributeUtil.getTargetBindingRelation(targetObjectList);
        Map<String, List<TargetObject>> nodeGroupMap = DistributeUtil.getNodeGroupMap(targetObjectList);
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOList) {
            List<TargetObject> nodeGroup;
            if (DistributeUtil.shouldSkip(workflowBusinessDO, currentUserId, nodeGroup = nodeGroupMap.get(workflowBusinessDO.getBusinesscode() + "_" + workflowBusinessDO.getWorkflowdefinekey()), processInfo)) continue;
            try {
                Map<String, Object> designDataMap = DistributeUtil.getDesignDataMap(workflowBusinessDO);
                this.updateDesignDataMap(designDataMap, nodeGroup, distributionDTO);
                workflowBusinessDO.setDesigndata(JSONUtil.toJSONString(designDataMap));
                this.workflowBusinessSaveService.save(workflowBusinessDO);
                nodeGroup.forEach(node -> {
                    node.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.SUCCESS.getValue());
                    node.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.success"));
                });
            }
            catch (Exception e) {
                log.error("\u4e0b\u53d1\u754c\u9762\u65b9\u6848\u65f6\u5f02\u5e38", e);
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

    private void updateDesignDataMap(Map<String, Object> designDataMap, List<TargetObject> nodeGroup, WorkflowBusinessDistributeDTO distributionDTO) {
        String billSchemeCode = distributionDTO.getBillSchemeCode();
        String billReadSchemeCode = distributionDTO.getBillReadSchemeCode();
        String approveSchemeCode = distributionDTO.getApproveSchemeCode();
        List<Map<String, Object>> multiSchemes = VaWorkflowUtils.getMapList("multiSchemes");
        if (designDataMap.containsKey("multiSchemes") && !CollectionUtils.isEmpty(multiSchemes)) {
            List oldNodeInfoList = JSONUtil.parseArray((String)JSONUtil.toJSONString(multiSchemes), NodeInfo.class);
            this.updateExistingMultiSchemes(oldNodeInfoList, nodeGroup, billSchemeCode, billReadSchemeCode, approveSchemeCode);
            designDataMap.put("multiSchemes", oldNodeInfoList);
        } else {
            ArrayList<NodeInfo> newNodeInfoList = new ArrayList<NodeInfo>();
            for (TargetObject nodeInfo : nodeGroup) {
                newNodeInfoList.add(this.createNodeItemMap(nodeInfo, billSchemeCode, billReadSchemeCode, approveSchemeCode));
            }
            designDataMap.put("multiSchemes", newNodeInfoList);
        }
    }

    private void updateExistingMultiSchemes(List<NodeInfo> oldNodeInfoList, List<TargetObject> nodeGroup, String billSchemeCode, String billReadSchemeCode, String approveSchemeCode) {
        Map<String, NodeInfo> nodeInfoMap = oldNodeInfoList.stream().collect(Collectors.toMap(NodeInfo::getNodeId, o -> o));
        for (TargetObject nodeInfo : nodeGroup) {
            String nodeId = nodeInfo.getNodeId();
            NodeInfo existingNode = nodeInfoMap.get(nodeId);
            if (existingNode != null) {
                this.updateSchemeCodes(existingNode, billSchemeCode, billReadSchemeCode, approveSchemeCode);
                continue;
            }
            oldNodeInfoList.add(this.createNodeItemMap(nodeInfo, billSchemeCode, billReadSchemeCode, approveSchemeCode));
        }
    }

    private NodeInfo createNodeItemMap(TargetObject nodeInfo, String billSchemeCode, String billReadSchemeCode, String approveSchemeCode) {
        NodeInfo newNodeInfo = new NodeInfo();
        newNodeInfo.setNodeId(nodeInfo.getNodeId());
        newNodeInfo.setNodeName(nodeInfo.getNodeName());
        newNodeInfo.setSchemes(billSchemeCode);
        newNodeInfo.setBillReadScheme(billReadSchemeCode);
        newNodeInfo.setApproveScheme(approveSchemeCode);
        return newNodeInfo;
    }

    private void updateSchemeCodes(NodeInfo nodeInfo, String billSchemeCode, String billReadSchemeCode, String approveSchemeCode) {
        if (StringUtils.hasText(billSchemeCode)) {
            nodeInfo.setSchemes(billSchemeCode);
        }
        if (StringUtils.hasText(billReadSchemeCode)) {
            nodeInfo.setBillReadScheme(billReadSchemeCode);
        }
        if (StringUtils.hasText(approveSchemeCode)) {
            nodeInfo.setApproveScheme(approveSchemeCode);
        }
    }
}

