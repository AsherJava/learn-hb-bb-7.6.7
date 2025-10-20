/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.DistributeItem
 *  com.jiuqi.va.domain.workflow.business.NodeInfo
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.feign.client.BillClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.business.DistributeItem;
import com.jiuqi.va.domain.workflow.business.NodeInfo;
import com.jiuqi.va.domain.workflow.business.TargetObject;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.feign.client.BillClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
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

@Component
public class DistributeActionStrategy
implements DistributeStrategy {
    private static final Logger log = LoggerFactory.getLogger(DistributeActionStrategy.class);
    @Autowired
    private WorkflowBusinessSaveService workflowBusinessSaveService;
    @Autowired
    private BillClient billClient;
    @Autowired
    private MetaDataClient metaDataClient;

    @Override
    public int getDistributeType() {
        return DistributionTypeEnum.ACTION.getValue();
    }

    @Override
    public void executeDistribute(WorkflowBusinessDistributeDTO distributionDTO) {
        BusinessWorkflowNodesInfoSaveProcess processInfo = DistributeUtil.setProcessInfo(distributionDTO);
        List actionList = distributionDTO.getActionList();
        if (CollectionUtils.isEmpty(actionList)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String currentUserId = ShiroUtil.getUser().getId();
        List targetObjectList = distributionDTO.getTargetObjectList();
        List<WorkflowBusinessDO> workflowBusinessDOList = DistributeUtil.getTargetBindingRelation(targetObjectList);
        Map<String, List<TargetObject>> nodeGroupMap = DistributeUtil.getNodeGroupMap(targetObjectList);
        List<DistributeItem> filteredList = DistributeUtil.getFilteredList(actionList, "action");
        List<Object> addItemList = DistributeUtil.getAddItemList(filteredList);
        List<DistributeItem> deleteItemList = DistributeUtil.getDeleteItemList(filteredList);
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOList) {
            String businessCode;
            List<TargetObject> nodeGroup;
            if (DistributeUtil.shouldSkip(workflowBusinessDO, currentUserId, nodeGroup = nodeGroupMap.get((businessCode = workflowBusinessDO.getBusinesscode()) + "_" + workflowBusinessDO.getWorkflowdefinekey()), processInfo)) continue;
            try {
                List<Object> actionNameList = this.getActionNameList(businessCode);
                addItemList = addItemList.stream().filter(item -> actionNameList.contains(item.getName())).collect(Collectors.toList());
                Map<String, Object> designDataMap = DistributeUtil.getDesignDataMap(workflowBusinessDO);
                this.updateDesignDataMap(designDataMap, nodeGroup, addItemList, deleteItemList);
                workflowBusinessDO.setDesigndata(JSONUtil.toJSONString(designDataMap));
                this.workflowBusinessSaveService.save(workflowBusinessDO);
                nodeGroup.forEach(node -> {
                    node.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.SUCCESS.getValue());
                    node.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.success"));
                });
            }
            catch (Exception e) {
                log.error("\u4e0b\u53d1\u52a8\u4f5c\u65f6\u5f02\u5e38", e);
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

    private List<Object> getActionNameList(String billDefine) {
        String modelName;
        R r;
        List<Object> actionNameList = new ArrayList<Object>();
        TenantDO param = new TenantDO();
        param.setTraceId(Utils.getTraceId());
        param.addExtInfo("defineCode", (Object)billDefine);
        R metaData = this.metaDataClient.findMetaInfoByDefineCode(param);
        if (metaData.getCode() == 0 && (r = this.billClient.getBillActionsByModelName(modelName = (String)metaData.get((Object)"modelName"))).getCode() == 0) {
            List<Map<String, Object>> list = VaWorkflowUtils.getMapList(r.get((Object)"data"));
            actionNameList = list.stream().map(item -> item.get("name")).collect(Collectors.toList());
        }
        return actionNameList;
    }

    private void updateDesignDataMap(Map<String, Object> designDataMap, List<TargetObject> nodeGroup, List<DistributeItem> addItemList, List<DistributeItem> deleteItemList) {
        if (designDataMap.containsKey("action")) {
            List nodeInfoList = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)designDataMap.get("action")), NodeInfo.class);
            this.updateExistingData(nodeInfoList, nodeGroup, addItemList, deleteItemList);
            designDataMap.put("action", nodeInfoList);
        } else {
            ArrayList<NodeInfo> newNodeInfoList = new ArrayList<NodeInfo>();
            for (TargetObject nodeInfo : nodeGroup) {
                newNodeInfoList.add(this.createNodeItem(addItemList, nodeInfo));
            }
            designDataMap.put("action", newNodeInfoList);
        }
    }

    private void updateExistingData(List<NodeInfo> nodeInfoList, List<TargetObject> nodeGroup, List<DistributeItem> addItemList, List<DistributeItem> deleteItemList) {
        Map<String, NodeInfo> existNodeMap = nodeInfoList.stream().collect(Collectors.toMap(NodeInfo::getNodeId, o -> o));
        for (TargetObject nodeInfo : nodeGroup) {
            String nodeId = nodeInfo.getNodeId();
            NodeInfo existNode = existNodeMap.get(nodeId);
            if (existNode == null) {
                nodeInfoList.add(this.createNodeItem(addItemList, nodeInfo));
                continue;
            }
            List newActionList = existNode.getActionList();
            List deleteItemNameList = deleteItemList.stream().map(DistributeItem::getName).collect(Collectors.toList());
            List addItemNameList = addItemList.stream().map(DistributeItem::getName).collect(Collectors.toList());
            newActionList.removeIf(item -> deleteItemNameList.contains(item.getName()) || addItemNameList.contains(item.getName()));
            newActionList.addAll(addItemList);
            existNode.setActionList(newActionList);
        }
    }

    private NodeInfo createNodeItem(List<DistributeItem> addItemList, TargetObject nodeInfo) {
        NodeInfo newNodeInfo = new NodeInfo();
        newNodeInfo.setNodeId(nodeInfo.getNodeId());
        newNodeInfo.setNodeName(nodeInfo.getNodeName());
        newNodeInfo.setActionList(addItemList);
        return newNodeInfo;
    }
}

