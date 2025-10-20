/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.utils.VaI18nParamUtil
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.dao.WorkflowProcessNodeDao;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class WorkflowProcessNodeExtendServiceImpl
implements WorkflowProcessNodeExtendService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowProcessNodeExtendServiceImpl.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowProcessNodeDao workflowProcessNodeDao;
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private VaI18nClient vaI18nClient;
    @Autowired
    private VaWorkflowProcessService workflowProcessService;

    public Map<String, String> getNodeCodeMapList(List<Map<String, Object>> processDefineList) {
        if (processDefineList == null || processDefineList.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        Boolean translationEnabled = VaI18nParamUtil.getTranslationEnabled();
        ArrayList<String> i18nKeys = null;
        ArrayList<String> nodeKeys = null;
        if (translationEnabled.booleanValue()) {
            i18nKeys = new ArrayList<String>();
            nodeKeys = new ArrayList<String>();
        }
        for (Map<String, Object> processDefine : processDefineList) {
            WorkflowModelDefine define;
            String processDefineKey = (String)processDefine.get("PROCESSDEFINEKEY");
            Object processDefineVersionObj = processDefine.get("PROCESSDEFINEVERSION");
            List nodeCodes = (List)processDefine.get("NODECODES");
            if (nodeCodes == null || nodeCodes.isEmpty()) continue;
            int nodeCodeSize = nodeCodes.size();
            String processDefineVerStr = String.valueOf(processDefineVersionObj);
            try {
                define = (WorkflowModelDefine)this.modelDefineService.getDefine(processDefineKey, Long.valueOf(Long.parseLong(processDefineVerStr)));
            }
            catch (Exception e) {
                logger.error("\u5f53\u524d\u5ba1\u6279\u8282\u70b9>>>\u67e5\u8be2\u6d41\u7a0b\u5b9a\u4e49\u5f02\u5e38\uff1a{}\uff0c\u6d41\u7a0b\u5b9a\u4e49\uff1a{}\uff0c\u6d41\u7a0b\u7248\u672c\uff1a{}", e.getMessage(), processDefineKey, processDefineVersionObj, e);
                continue;
            }
            ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
            ArrayNode jsonNodes = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
            HashMap<String, String> nodeCodeMap = new HashMap<String, String>();
            for (JsonNode jsonNode : jsonNodes) {
                String nodeCode = jsonNode.get("resourceId").asText();
                String nodeType = jsonNode.get("stencil").get("id").asText();
                if ("SubProcess".equals(nodeType)) {
                    JsonNode childShapes = jsonNode.get("childShapes");
                    for (JsonNode childShape : childShapes) {
                        String childNodeCode = childShape.get("resourceId").asText();
                        String childNodeCodeAndVersion = childNodeCode + "#" + processDefineVerStr;
                        if (nodeCodes.contains(childNodeCodeAndVersion)) {
                            nodeCodeMap.put(childNodeCodeAndVersion, childShape.get("properties").get("name").asText());
                            if (translationEnabled.booleanValue()) {
                                i18nKeys.add("VA#workflow#" + processDefineKey + "&define#" + processDefineVerStr + "&workflowversion#processDesignPlugin&plugin#" + childNodeCode);
                                nodeKeys.add(childNodeCodeAndVersion);
                            }
                        }
                        if (nodeCodeMap.size() != nodeCodeSize) continue;
                        break;
                    }
                } else {
                    String nodeCodeAndVersion = nodeCode + "#" + processDefineVerStr;
                    if (nodeCodes.contains(nodeCodeAndVersion)) {
                        nodeCodeMap.put(nodeCodeAndVersion, jsonNode.get("properties").get("name").asText());
                        if (translationEnabled.booleanValue()) {
                            i18nKeys.add("VA#workflow#" + processDefineKey + "&define#" + processDefineVerStr + "&workflowversion#processDesignPlugin&plugin#" + nodeCode);
                            nodeKeys.add(nodeCodeAndVersion);
                        }
                    }
                }
                if (nodeCodeMap.size() != nodeCodeSize) continue;
                break;
            }
            resultMap.putAll(nodeCodeMap);
        }
        if (translationEnabled.booleanValue() && !i18nKeys.isEmpty()) {
            VaI18nResourceDTO i18nResourceDTO = new VaI18nResourceDTO();
            i18nResourceDTO.setKey(i18nKeys);
            List i18nValues = this.vaI18nClient.queryList(i18nResourceDTO);
            HashMap i18nMap = new HashMap();
            int i18nKeysSize = i18nKeys.size();
            for (int i = 0; i < i18nKeysSize; ++i) {
                String value = (String)i18nValues.get(i);
                if (!StringUtils.hasText(value)) continue;
                i18nMap.put(nodeKeys.get(i), value);
            }
            if (!i18nMap.isEmpty()) {
                for (Map.Entry entry : i18nMap.entrySet()) {
                    String key = (String)entry.getKey();
                    if (!resultMap.containsKey(key)) continue;
                    resultMap.put(key, (String)entry.getValue());
                }
            }
        }
        return resultMap;
    }

    public void updateRejectStatus(List<ProcessNodeDO> processNodes) {
        if (processNodes == null || processNodes.isEmpty()) {
            return;
        }
        String processId = null;
        ArrayList<String> processNodeIds = new ArrayList<String>();
        for (ProcessNodeDO processNode : processNodes) {
            processId = processNode.getProcessid();
            processNodeIds.add(processNode.getId());
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processId);
        processNodeDTO.setProcessNodeIds(processNodeIds);
        processNodeDTO.setRejectstatus(null);
        this.workflowProcessNodeDao.updateRejectStatus(processNodeDTO);
    }

    public void updateRetractNode(List<ProcessNodeDO> retractNodes, ProcessNodeDO retractProcessNode, String retractTaskId) {
        if (retractNodes == null || retractNodes.isEmpty()) {
            return;
        }
        String processid = retractProcessNode.getProcessid();
        Assert.hasText(processid, VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "processId");
        ArrayList<String> processNodeIds = new ArrayList<String>();
        for (ProcessNodeDO node2 : retractNodes) {
            processNodeIds.add(node2.getId());
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processid);
        processNodeDTO.setProcessNodeIds(processNodeIds);
        processNodeDTO.setIgnoreflag(BigDecimal.ZERO);
        processNodeDTO.setRejectstatus(null);
        this.workflowProcessNodeDao.updateRetractRejectNodes(processNodeDTO);
        ProcessNodeDTO dto = new ProcessNodeDTO();
        dto.setProcessid(processid);
        dto.setNodeid(retractProcessNode.getNodeid());
        dto.setCompleteuserid(retractProcessNode.getCompleteuserid());
        dto.setCompleteresult("\u53d6\u56de");
        dto.setRejectstatus(null);
        dto.setIgnoreflag(BigDecimal.ZERO);
        this.workflowProcessNodeDao.updateRetractNode(dto);
        String nodeid = retractProcessNode.getNodeid();
        ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
        nodeDTO.setNodeid(nodeid);
        nodeDTO.setProcessid(processid);
        nodeDTO.addExtInfo("retractRejectNewTaskId", (Object)retractTaskId);
        this.workflowProcessNodeDao.updateNodeId(nodeDTO);
        boolean plusApprovalFlag = retractNodes.stream().anyMatch(node -> BigDecimal.ONE.equals(node.getCompleteusertype()));
        if (plusApprovalFlag) {
            PlusApprovalInfoDTO infoDTO = new PlusApprovalInfoDTO();
            infoDTO.setProcessid(processid);
            infoDTO.setNodeid(nodeid);
            infoDTO.addExtInfo("retractRejectNewTaskId", (Object)retractTaskId);
            this.workflowPlusApprovalService.updateNodeId(infoDTO);
        }
    }

    public void deleteRetractTargetNodes(List<ProcessNodeDO> retractTargetNodes) {
        if (retractTargetNodes == null || retractTargetNodes.isEmpty()) {
            return;
        }
        String processId = null;
        ArrayList<String> processNodeIds = new ArrayList<String>();
        for (ProcessNodeDO processNode : retractTargetNodes) {
            processId = processNode.getProcessid();
            processNodeIds.add(processNode.getId());
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processId);
        processNodeDTO.setProcessNodeIds(processNodeIds);
        this.workflowProcessNodeDao.deleteByPrimaryKeys(processNodeDTO);
    }

    public void deleteIgnoreNode(ProcessNodeDTO processNodeDTO) {
        this.workflowProcessNodeDao.deleteIgnoreNode(processNodeDTO);
    }

    public void updatePgwNodeCompleteTime(ProcessNodeDTO processNodeDTO) {
        String id = processNodeDTO.getId();
        if (!StringUtils.hasText(id)) {
            return;
        }
        this.workflowProcessNodeDao.updateCompleteTime(processNodeDTO);
    }

    public List<ProcessNodeDO> retractNodeList(WorkflowDTO workflowDTO) {
        Map<String, List<ProcessNodeDO>> branchNodesMap;
        String bizCode = workflowDTO.getBizCode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.workflowProcessService.get(processDTO);
        if (processDO == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processfinishedmsg"));
        }
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String taskId = workflowDTO.getTaskId();
        ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
        nodeDTO.setProcessid(processInstanceId);
        List<Object> processNodeDOList = this.workflowProcessNodeDao.listProcessNode(nodeDTO);
        ProcessNodeDO retractOperatorNode = processNodeDOList.stream().filter(node -> Objects.equals(node.getNodeid(), taskId)).findFirst().orElse(null);
        if (retractOperatorNode == null) {
            return Collections.emptyList();
        }
        String subProcessBranch = retractOperatorNode.getSubprocessbranch();
        String pgwBranch = retractOperatorNode.getPgwbranch();
        if (!StringUtils.hasText(subProcessBranch) && !StringUtils.hasText(pgwBranch)) {
            return Collections.emptyList();
        }
        ArrayList<ProcessNodeDO> result = new ArrayList<ProcessNodeDO>();
        BigDecimal rejectAfterRetract = workflowDTO.getRejectAfterRetract();
        if (StringUtils.hasText(subProcessBranch)) {
            String subProcessNodeId = retractOperatorNode.getSubprocessnodeid();
            processNodeDOList = processNodeDOList.stream().filter(node -> Objects.equals(subProcessNodeId, node.getSubprocessnodeid())).collect(Collectors.toList());
            branchNodesMap = VaWorkflowNodeUtils.packageSubBranchMap(processNodeDOList, subProcessNodeId);
            this.calculateParallelBranchRetractNodes(branchNodesMap, result, rejectAfterRetract);
        }
        if (StringUtils.hasText(pgwBranch)) {
            String pgwNodeId = retractOperatorNode.getPgwnodeid();
            processNodeDOList = processNodeDOList.stream().filter(node -> Objects.equals(pgwNodeId, node.getPgwnodeid())).collect(Collectors.toList());
            branchNodesMap = VaWorkflowNodeUtils.packagePgwBranchMap(processNodeDOList, pgwNodeId);
            this.calculateParallelBranchRetractNodes(branchNodesMap, result, rejectAfterRetract);
        }
        return result;
    }

    private void calculateParallelBranchRetractNodes(Map<String, List<ProcessNodeDO>> branchNodesMap, List<ProcessNodeDO> result, BigDecimal rejectAfterRetract) {
        String currUserid = ShiroUtil.getUser().getId();
        block0: for (Map.Entry<String, List<ProcessNodeDO>> entry : branchNodesMap.entrySet()) {
            List<ProcessNodeDO> branchNodes = entry.getValue();
            List branchCompleteNodes = branchNodes.stream().filter(node -> node.getCompletetime() != null).filter(node -> !"\u81ea\u52a8\u540c\u610f".equals(node.getCompletecomment())).collect(Collectors.toList());
            String lastNodeCode = null;
            for (int i = branchCompleteNodes.size() - 1; i >= 0; --i) {
                ProcessNodeDO completeNode = (ProcessNodeDO)branchCompleteNodes.get(i);
                String nodeCode = completeNode.getNodecode();
                if (lastNodeCode == null) {
                    lastNodeCode = nodeCode;
                }
                if (!Objects.equals(lastNodeCode, nodeCode)) continue block0;
                String completeUserId = completeNode.getCompleteuserid();
                if (!Objects.equals(completeUserId, currUserid)) continue;
                if ("\u5ba1\u6279\u9a73\u56de".equals(completeNode.getCompleteresult())) {
                    if (!BigDecimal.ONE.equals(rejectAfterRetract)) continue block0;
                    result.add(completeNode);
                    continue block0;
                }
                result.add(completeNode);
                continue block0;
            }
        }
    }
}

