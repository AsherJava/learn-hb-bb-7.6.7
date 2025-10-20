/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.workflow.NodeView
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.ClientAnchor
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.streaming.SXSSFDrawing
 *  org.apache.poi.xssf.streaming.SXSSFPicture
 *  org.apache.poi.xssf.streaming.SXSSFRow
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFClientAnchor
 *  org.apache.poi.xssf.usermodel.XSSFPicture
 *  org.apache.poi.xssf.usermodel.XSSFShape
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.workflow.NodeView;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.constants.VaWorkflowConstants;
import com.jiuqi.va.workflow.dao.WorkflowMetaDao;
import com.jiuqi.va.workflow.dao.WorkflowProcessNodeDao;
import com.jiuqi.va.workflow.domain.FlowTransferInfo;
import com.jiuqi.va.workflow.domain.WorkflowConst;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowBusinessService;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFPicture;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowHelperServiceImpl
implements WorkflowHelperService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowHelperServiceImpl.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowProcessNodeDao workflowProcessNodeDao;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private VaWorkflowProcessService workflowProcessService;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    @Autowired
    private WorkflowMetaDao workflowMetaDao;
    private WorkflowBusinessService workflowBusinessService;
    private WorkflowSevice workflowSevice;

    public WorkflowBusinessService getWorkflowBusinessService() {
        if (this.workflowBusinessService == null) {
            this.workflowBusinessService = (WorkflowBusinessService)ApplicationContextRegister.getBean(WorkflowBusinessService.class);
        }
        return this.workflowBusinessService;
    }

    public WorkflowSevice getWorkflowSevice() {
        if (this.workflowSevice == null) {
            this.workflowSevice = (WorkflowSevice)ApplicationContextRegister.getBean(WorkflowSevice.class);
        }
        return this.workflowSevice;
    }

    @Override
    public List<Map<String, Object>> listNodesByVersionUniqueCode(String uniqueCode, Long version) {
        ArrayList<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        WorkflowModel workflowModel = null;
        try {
            workflowModel = this.getModel(uniqueCode, version);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (Objects.isNull(workflowModel)) {
            return nodes;
        }
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        ArrayNode arrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        for (JsonNode jsonNode : arrayNode) {
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            ArrayNode outGoingArrayNode = (ArrayNode)jsonNode.get("outgoing");
            this.getWorkflowNodes(outGoingArrayNode, arrayNode, nodes);
            break;
        }
        return nodes;
    }

    @Override
    public Map<String, Map<String, String>> listNextApproveInfo(TenantDO tenantDO) {
        Object bizCodeObject = tenantDO.getExtInfo("bizCode");
        List<String> bizCodeList = VaWorkflowUtils.getList(bizCodeObject);
        HashMap<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>(bizCodeList.size());
        if (bizCodeList.isEmpty()) {
            return resultMap;
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizCodes(bizCodeList);
        List<ProcessNodeDO> allProcessNodeDOs = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
        if (allProcessNodeDOs.isEmpty()) {
            return resultMap;
        }
        HashMap bizCodeNodeListMap = new HashMap();
        for (ProcessNodeDO processNodeDO : allProcessNodeDOs) {
            if (Objects.equals(BigDecimal.ONE, processNodeDO.getHiddenflag())) continue;
            String bizcode = processNodeDO.getBizcode();
            if (bizCodeNodeListMap.containsKey(bizcode)) {
                ((List)bizCodeNodeListMap.get(bizcode)).add(processNodeDO);
                continue;
            }
            ArrayList<ProcessNodeDO> processNodeDOList = new ArrayList<ProcessNodeDO>();
            processNodeDOList.add(processNodeDO);
            bizCodeNodeListMap.put(bizcode, processNodeDOList);
        }
        boolean i18nEnable = Boolean.TRUE.equals(VaI18nParamUtil.getTranslationEnabled());
        HashMap<String, String> userMap = new HashMap<String, String>();
        HashMap<String, String> nodeLanguageMap = new HashMap<String, String>();
        HashMap<String, Object> bizCacheMap = new HashMap<String, Object>();
        String tenantName = ShiroUtil.getTenantName();
        for (String bizCode : bizCodeList) {
            if (!bizCodeNodeListMap.containsKey(bizCode)) continue;
            HashMap<String, String> approvalMap = new HashMap<String, String>(4);
            HashSet<String> nodeNameSet = new HashSet<String>();
            HashSet<String> userNameSet = new HashSet<String>();
            List<Object> processNodes = (List<ProcessNodeDO>)bizCodeNodeListMap.get(bizCode);
            ProcessNodeDO lastProcessNode = processNodes.stream().filter(node -> node.getOrdernum() != null).max(Comparator.comparing(ProcessNodeDO::getOrdernum)).orElse(null);
            if (lastProcessNode == null) continue;
            if (i18nEnable) {
                processNodes = VaWorkFlowDataUtils.handleBizProcessNodes(processNodes, lastProcessNode.getBizdefine());
            }
            String lastProcessId = lastProcessNode.getProcessid();
            processNodes = processNodes.stream().filter(node -> Objects.equals(node.getProcessid(), lastProcessId)).collect(Collectors.toList());
            List<Object> resultProcessNodeList = new ArrayList();
            String subProcessNodeId = lastProcessNode.getSubprocessnodeid();
            String pgwNodeId = lastProcessNode.getPgwnodeid();
            if (StringUtils.hasText(subProcessNodeId)) {
                resultProcessNodeList = this.handleSubProcessNode(processNodes, subProcessNodeId);
            }
            if (StringUtils.hasText(pgwNodeId)) {
                resultProcessNodeList = this.handlePgwProcessNode(processNodes, pgwNodeId);
            }
            if (!StringUtils.hasText(subProcessNodeId) && !StringUtils.hasText(pgwNodeId)) {
                resultProcessNodeList = this.getApprovalInfo(processNodes);
            }
            if (i18nEnable) {
                VaWorkFlowI18nUtils.convertProcessNodeListI18n(resultProcessNodeList, nodeLanguageMap, bizCacheMap);
            }
            for (ProcessNodeDO processNodeDO : resultProcessNodeList) {
                String completeusername;
                String completeuserid = processNodeDO.getCompleteuserid();
                if (userMap.containsKey(completeuserid)) {
                    completeusername = (String)userMap.get(completeuserid);
                } else {
                    UserDO userDO = VaWorkFlowDataUtils.getOneUserData(tenantName, completeuserid);
                    completeusername = userDO == null ? "" : userDO.getName();
                    userMap.put(completeuserid, completeusername);
                }
                userNameSet.add(completeusername);
                nodeNameSet.add(processNodeDO.getProcessnodename());
            }
            approvalMap.put("APPROVER", String.join((CharSequence)",", userNameSet));
            approvalMap.put("NEXT_APPROVE_NODE", String.join((CharSequence)",", nodeNameSet));
            resultMap.put(bizCode, approvalMap);
        }
        return resultMap;
    }

    private List<ProcessNodeDO> handlePgwProcessNode(List<ProcessNodeDO> processNodes, String pgwNodeId) {
        List pgwNodes = processNodes.stream().filter(node -> !Objects.equals(node.getPgwnodeid(), node.getNodeid()) || !Objects.equals(node.getSyscode(), "WORKFLOW")).filter(node -> Objects.equals(node.getPgwnodeid(), pgwNodeId)).collect(Collectors.toList());
        HashMap pgwBranchNodesMap = new HashMap();
        for (ProcessNodeDO pgwNode : pgwNodes) {
            String pgwbranch = pgwNode.getPgwbranch();
            if (pgwBranchNodesMap.containsKey(pgwbranch)) {
                ((List)pgwBranchNodesMap.get(pgwbranch)).add(pgwNode);
                continue;
            }
            ArrayList<ProcessNodeDO> subBranch = new ArrayList<ProcessNodeDO>();
            subBranch.add(pgwNode);
            pgwBranchNodesMap.put(pgwbranch, subBranch);
        }
        ArrayList<ProcessNodeDO> pgwProcessNodeList = new ArrayList<ProcessNodeDO>();
        for (Map.Entry entry : pgwBranchNodesMap.entrySet()) {
            List processNodeList = (List)entry.getValue();
            if (processNodeList.isEmpty()) continue;
            List<ProcessNodeDO> approvalInfo = this.getApprovalInfo(processNodeList);
            pgwProcessNodeList.addAll(approvalInfo);
        }
        return pgwProcessNodeList;
    }

    private List<ProcessNodeDO> handleSubProcessNode(List<ProcessNodeDO> processNodes, String subProcessNodeId) {
        List subProcessNodes = processNodes.stream().filter(node -> Objects.equals(node.getSubprocessnodeid(), subProcessNodeId)).filter(node -> !Objects.equals(node.getNodeid(), subProcessNodeId)).collect(Collectors.toList());
        HashMap subBranchNodesMap = new HashMap();
        for (ProcessNodeDO subProcessNode : subProcessNodes) {
            String subprocessbranch = subProcessNode.getSubprocessbranch();
            if (!StringUtils.hasText(subprocessbranch)) continue;
            if (subBranchNodesMap.containsKey(subprocessbranch)) {
                ((List)subBranchNodesMap.get(subprocessbranch)).add(subProcessNode);
                continue;
            }
            ArrayList<ProcessNodeDO> subBranch = new ArrayList<ProcessNodeDO>();
            subBranch.add(subProcessNode);
            subBranchNodesMap.put(subprocessbranch, subBranch);
        }
        ArrayList<ProcessNodeDO> subProcessNodeList = new ArrayList<ProcessNodeDO>();
        for (Map.Entry entry : subBranchNodesMap.entrySet()) {
            List processNodeList = (List)entry.getValue();
            if (processNodeList.isEmpty()) continue;
            List<ProcessNodeDO> approvalInfo = this.getApprovalInfo(processNodeList);
            subProcessNodeList.addAll(approvalInfo);
        }
        return subProcessNodeList;
    }

    private List<ProcessNodeDO> getApprovalInfo(List<ProcessNodeDO> processNodeList) {
        ProcessNodeDO processNodeDO;
        ArrayList<ProcessNodeDO> resultProcessNodes = new ArrayList<ProcessNodeDO>();
        ProcessNodeDO lastProcessNode = processNodeList.get(processNodeList.size() - 1);
        String nodecode = lastProcessNode.getNodecode();
        if ("\u6d41\u7a0b\u7ed3\u675f".equals(nodecode)) {
            resultProcessNodes.add(lastProcessNode);
            return resultProcessNodes;
        }
        for (int i = processNodeList.size() - 1; i >= 0 && Objects.equals((processNodeDO = processNodeList.get(i)).getNodecode(), nodecode); --i) {
            if (processNodeDO.getCompletetime() != null) continue;
            resultProcessNodes.add(processNodeDO);
        }
        return resultProcessNodes;
    }

    @Override
    public void dealAutoTaskParamBeforeExecute(WorkflowDTO workflowDTO, String nodeId) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        WorkflowModel workflowModel = (WorkflowModel)customParam.get("workflowModel");
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().find(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        JsonNode process = processDesignPluginDefine.getData();
        ArrayNode nodes = (ArrayNode)process.get("childShapes");
        String nodeName = null;
        for (JsonNode node : nodes) {
            String workflowNodeId = node.get("resourceId").asText();
            if (!nodeId.equals(workflowNodeId)) continue;
            JsonNode propertyNode = Optional.ofNullable(node.get("properties")).orElse(VaWorkflowConstants.emptyJsonNode());
            nodeName = propertyNode.get("name").asText();
            break;
        }
        if (ObjectUtils.isEmpty(workflowDTO.getExtInfo())) {
            workflowDTO.setExtInfo(new HashMap(16));
        }
        workflowDTO.getExtInfo().put("processNodeId", nodeId);
        workflowDTO.getExtInfo().put("processNodeName", nodeName);
    }

    @Override
    public String getWorkFlowLanguageFlag(Map<String, Object> bizCodeCacheMap, NodeView nodeView, ProcessDO tempProcessDO) {
        String workFlowDefine = tempProcessDO.getDefinekey();
        BigDecimal workFlowDefineVersion = tempProcessDO.getDefineversion();
        String nodeCode = nodeView.getNodeCode();
        return "VA#workflow#" + workFlowDefine + "&define#" + workFlowDefineVersion + "&workflowversion#processDesignPlugin&plugin#" + nodeCode;
    }

    @Override
    public ProcessDO getProcessDoByBizCode(String bizCode) {
        List processHistoryList;
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.workflowProcessService.get(processDTO);
        if (Objects.isNull(processDO) && !ObjectUtils.isEmpty(processHistoryList = Optional.ofNullable(this.workflowProcessService.listHistory(processDTO)).orElse(Collections.emptyList()))) {
            processDO = new ProcessDO();
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryList.get(0);
            processDO.setDefinekey(processHistoryDO.getDefinekey());
            processDO.setDefineversion(processHistoryDO.getDefineversion());
        }
        return processDO;
    }

    @Override
    public void getWorkflowNodes(ArrayNode outGoingArrayNode, ArrayNode arrayNode, List<Map<String, Object>> nodes) {
        block0: for (JsonNode outgoing : outGoingArrayNode) {
            for (JsonNode jsonNode : arrayNode) {
                String nodeType;
                String nodeId = jsonNode.get("resourceId").asText();
                if (!outgoing.get("resourceId").asText().equals(nodeId)) continue;
                String id = jsonNode.get("stencil").get("id").asText();
                String string = nodeType = jsonNode.get("stencil").get("nodeType") == null ? "" : jsonNode.get("stencil").get("nodeType").asText();
                if ("UserTask".equals(id) || "Manual".equals(nodeType)) {
                    Map properties = JSONUtil.parseMap((String)jsonNode.get("properties").toString());
                    Map<String, String> node = new HashMap<String, String>(16);
                    if (properties instanceof Map) {
                        node = properties;
                    }
                    node.put("nodeId", nodeId);
                    for (Map map : nodes) {
                        if (!nodeId.equals(map.get("nodeId"))) continue;
                        continue block0;
                    }
                    nodes.add(node);
                }
                if ("SubProcess".equals(id)) {
                    ArrayNode subArrayNode = (ArrayNode)jsonNode.get("childShapes");
                    ArrayNode jsonNodes = arrayNode.deepCopy();
                    jsonNodes.addAll(subArrayNode);
                    for (JsonNode jsonNode2 : subArrayNode) {
                        String subNodeType = jsonNode2.get("stencil").get("id").asText();
                        if (!"StartNoneEvent".equals(subNodeType)) continue;
                        ArrayNode subOutGoingArrayNode = (ArrayNode)jsonNode2.get("outgoing");
                        this.getWorkflowNodes(subOutGoingArrayNode, jsonNodes, nodes);
                        break;
                    }
                }
                this.getWorkflowNodes((ArrayNode)jsonNode.get("outgoing"), arrayNode, nodes);
            }
        }
    }

    @Override
    public WorkflowOptionDTO getWorkFlowOptionDto() {
        WorkflowOptionDTO workflowOptionDTO = new WorkflowOptionDTO();
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        List list = Optional.ofNullable(this.workflowOptionService.list(optionItemDTO)).orElse(Collections.emptyList());
        for (OptionItemVO optionItemVO : list) {
            String optionName = optionItemVO.getName();
            String optionVal = optionItemVO.getVal();
            if ("WF1004".equals(optionName)) {
                workflowOptionDTO.setPlusApprovalFlag(optionVal);
            }
            if ("WF1003".equals(optionName)) {
                workflowOptionDTO.setWf1003(optionVal);
            }
            if ("WF1013".equals(optionName)) {
                workflowOptionDTO.setWf1013(optionVal);
            }
            if (!"WF1016".equals(optionName)) continue;
            workflowOptionDTO.setWf1016(optionVal);
        }
        return workflowOptionDTO;
    }

    @Override
    public void workflowBatchExport(List<MetaInfoDim> workflowMetaInfos, SXSSFWorkbook workbook) {
        for (MetaInfoDim workflowMetaInfo : workflowMetaInfos) {
            try {
                this.exportOneSheet(workbook, workflowMetaInfo);
            }
            catch (Exception e) {
                logger.error(workflowMetaInfo.getTitle() + "\u5de5\u4f5c\u6d41\u914d\u7f6e\u5bfc\u51fa\u5931\u8d25", e);
            }
        }
    }

    public void exportOneSheet(SXSSFWorkbook workbook, MetaInfoDim workflowMetaInfo) {
        String uniqueCode = workflowMetaInfo.getUniqueCode();
        Long workflowMetaInfoVersion = workflowMetaInfo.getVersion();
        String sheetname = workflowMetaInfo.getTitle();
        sheetname = sheetname.replace("/", "-");
        SXSSFSheet sheet = workbook.createSheet(sheetname);
        int rowSpan = 0;
        SXSSFPicture picture = null;
        SXSSFDrawing drawing = sheet.createDrawingPatriarch();
        byte[] imageInfo = this.getImgData(uniqueCode, workflowMetaInfoVersion);
        if (imageInfo != null) {
            byte[] processedImageData = this.addBackground(imageInfo, Color.white);
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 1, 1, 2);
            int pictureIndex = workbook.addPicture(processedImageData, 6);
            picture = drawing.createPicture(anchor, pictureIndex);
            picture.resize();
            rowSpan = this.getRowSpan(drawing);
        }
        int lastRowIndex = rowSpan + 3;
        lastRowIndex = this.handleBusinessInfo(uniqueCode, workflowMetaInfoVersion, (Workbook)workbook, (Sheet)sheet, lastRowIndex);
        ++lastRowIndex;
        List<Map<String, Object>> nodeList = this.getWorkFlowElement(uniqueCode, workflowMetaInfoVersion);
        List<FlowTransferInfo> flowTransferInfos = this.packagingFlowInfo(nodeList);
        lastRowIndex = this.handleTransferInfo((Workbook)workbook, (Sheet)sheet, lastRowIndex, flowTransferInfos);
        ++lastRowIndex;
        lastRowIndex = this.handleParticipantInfo((Workbook)workbook, (Sheet)sheet, lastRowIndex, nodeList);
        sheet.trackAllColumnsForAutoSizing();
        for (int i = 0; i < 4; ++i) {
            sheet.autoSizeColumn(i);
            int maxWidth = 15000;
            sheet.setColumnWidth(i, Math.min(maxWidth, sheet.getColumnWidth(i)));
        }
        int colSpan = 0;
        if (picture != null) {
            picture.resize();
            colSpan = this.getColSpan(drawing);
        }
        CellRangeAddress MergeRegion = new CellRangeAddress(0, 0, 0, colSpan < 2 ? 1 : colSpan - 1);
        sheet.addMergedRegion(MergeRegion);
        SXSSFRow mergedRow = sheet.createRow(0);
        Cell mergedCell = mergedRow.createCell(0);
        mergedCell.setCellValue(workflowMetaInfo.getTitle());
        CellStyle titleStyle = this.getTitleStyle((Workbook)workbook);
        this.cellMergeBord((Sheet)sheet, MergeRegion, titleStyle);
    }

    public byte[] getImgData(String uniqueCode, Long workflowMetaInfoVersion) {
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("workflowDefineKey", (Object)uniqueCode);
        tenantDO.addExtInfo("metaVersion", (Object)workflowMetaInfoVersion);
        Integer version = this.workflowMetaDao.getworkflowDefineVersion(tenantDO);
        if (version == null) {
            return null;
        }
        TenantDO tenantDO1 = new TenantDO();
        tenantDO1.addExtInfo("version", (Object)version);
        tenantDO1.addExtInfo("definename", (Object)uniqueCode);
        R r = this.getWorkflowSevice().getWorkflowDesignImage(tenantDO1);
        if (r.getCode() == 1) {
            return null;
        }
        return (byte[])r.get((Object)"imageInfo");
    }

    private int getColSpan(SXSSFDrawing drawing) {
        int colSpan = 0;
        for (XSSFShape shape : drawing) {
            if (!(shape instanceof XSSFPicture)) continue;
            XSSFPicture picture0 = (XSSFPicture)shape;
            XSSFClientAnchor anchor0 = picture0.getClientAnchor();
            colSpan = anchor0.getCol2() - anchor0.getCol1() + 1;
            break;
        }
        return colSpan;
    }

    private int getRowSpan(SXSSFDrawing drawing) {
        int rowSpan = 0;
        for (XSSFShape shape : drawing) {
            if (!(shape instanceof XSSFPicture)) continue;
            XSSFPicture picture0 = (XSSFPicture)shape;
            XSSFClientAnchor anchor0 = picture0.getClientAnchor();
            rowSpan = anchor0.getRow2() - anchor0.getRow1() + 1;
            break;
        }
        return rowSpan;
    }

    private void cellMergeBord(Sheet sheet, CellRangeAddress mergeRegion, CellStyle style) {
        for (int i = mergeRegion.getFirstRow(); i <= mergeRegion.getLastRow(); ++i) {
            Row mergeRow = sheet.getRow(i);
            if (mergeRow == null) {
                mergeRow = sheet.createRow(i);
            }
            for (int j = mergeRegion.getFirstColumn(); j <= mergeRegion.getLastColumn(); ++j) {
                Cell mergeCell = mergeRow.getCell(j);
                if (mergeCell == null) {
                    mergeCell = mergeRow.createCell(j);
                }
                mergeCell.setCellStyle(style);
            }
        }
    }

    private CellStyle getTitleStyle(Workbook workbook) {
        Font boldFont = workbook.createFont();
        boldFont.setFontName("\u5b8b\u4f53");
        boldFont.setFontHeightInPoints((short)20);
        CellStyle boldCellStyle = workbook.createCellStyle();
        boldCellStyle.setFont(boldFont);
        boldCellStyle.setAlignment(HorizontalAlignment.CENTER);
        boldCellStyle.setBorderTop(BorderStyle.THIN);
        boldCellStyle.setBorderBottom(BorderStyle.THIN);
        boldCellStyle.setBorderLeft(BorderStyle.THIN);
        boldCellStyle.setBorderRight(BorderStyle.THIN);
        return boldCellStyle;
    }

    private int handleParticipantInfo(Workbook workbook, Sheet sheet, int lastRowIndex, List<Map<String, Object>> nodeList) {
        List<String> participantsTitles = Arrays.asList("\u8282\u70b9\u540d\u79f0", "\u8282\u70b9\u7c7b\u578b", "\u53c2\u4e0e\u8005\u7c7b\u578b", "\u53c2\u4e0e\u8005\u914d\u7f6e");
        CellRangeAddress mergeRegion = new CellRangeAddress(lastRowIndex, lastRowIndex, 0, participantsTitles.size() - 1);
        sheet.addMergedRegion(mergeRegion);
        Row participantsRow = sheet.createRow(lastRowIndex++);
        Cell participantsCell = participantsRow.createCell(0);
        this.cellMergeBord(sheet, mergeRegion, this.getTitleStyle(workbook));
        participantsCell.setCellValue("\u8282\u70b9\u7684\u53c2\u4e0e\u8005\u5217\u8868");
        CellStyle cellStyle = this.getHeaderCellStyle(workbook);
        CellStyle cellContentStyle = this.getCellStyle(workbook);
        Row row1 = sheet.createRow(lastRowIndex++);
        int size = participantsTitles.size();
        for (int i = 0; i < size; ++i) {
            Cell cell = row1.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(participantsTitles.get(i));
        }
        for (Map<String, Object> node : nodeList) {
            List participantStrategies;
            String elementType = (String)node.get("elementType");
            if (Objects.equals(elementType, "SequenceFlow")) continue;
            int rowSpan = 0;
            Row row2 = null;
            Row firstRowSpan = null;
            if ("UserTask".equals(elementType) && !CollectionUtils.isEmpty(participantStrategies = (List)node.get("participantStrategies"))) {
                rowSpan = participantStrategies.size();
                int size2 = participantStrategies.size();
                for (int i = 0; i < size2; ++i) {
                    Map strategy = (Map)participantStrategies.get(i);
                    row2 = sheet.createRow(lastRowIndex++);
                    if (size2 > 1 && i == 0) {
                        firstRowSpan = row2;
                    }
                    Cell cell = row2.createCell(2);
                    cell.setCellStyle(cellContentStyle);
                    cell.setCellValue((String)strategy.get("strategyName"));
                    Cell cell1 = row2.createCell(3);
                    cell1.setCellStyle(cellContentStyle);
                    cell1.setCellValue((String)strategy.get("strategyCfg"));
                }
            }
            if (rowSpan == 0) {
                row2 = sheet.createRow(lastRowIndex++);
                Cell cell2 = row2.createCell(2);
                String strategyName = "";
                Cell cell3 = row2.createCell(3);
                String strategyCfg = "";
                cell2.setCellStyle(cellContentStyle);
                cell2.setCellValue(strategyName);
                cell3.setCellStyle(cellContentStyle);
                cell3.setCellValue(strategyCfg);
            }
            if (rowSpan < 2) {
                Cell cell0 = row2.createCell(0);
                cell0.setCellStyle(cellContentStyle);
                cell0.setCellValue((String)node.get("elementName"));
                Cell cell1 = row2.createCell(1);
                cell1.setCellStyle(cellContentStyle);
                cell1.setCellValue(elementType);
                continue;
            }
            CellRangeAddress elementNameMergeRegion = new CellRangeAddress(lastRowIndex - rowSpan, lastRowIndex - 1, 0, 0);
            sheet.addMergedRegion(elementNameMergeRegion);
            Cell cell0 = firstRowSpan.createCell(0);
            cell0.setCellValue((String)node.get("elementName"));
            this.cellMergeBord(sheet, elementNameMergeRegion, cellContentStyle);
            CellRangeAddress elementTypeMergeRegion = new CellRangeAddress(lastRowIndex - rowSpan, lastRowIndex - 1, 1, 1);
            sheet.addMergedRegion(elementTypeMergeRegion);
            Cell cell1 = firstRowSpan.createCell(1);
            cell1.setCellValue(elementType);
            this.cellMergeBord(sheet, elementTypeMergeRegion, cellContentStyle);
        }
        return lastRowIndex;
    }

    private int handleTransferInfo(Workbook workbook, Sheet sheet, int lastRowIndex, List<FlowTransferInfo> flowTransferInfos) {
        List<String> workflowInfoTitles = Arrays.asList("\u8282\u70b9\u540d\u79f0", "\u8f6c\u79fb\u7684\u6807\u9898", "\u540e\u4e00\u8282\u70b9\u540d\u79f0", "\u8f6c\u79fb\u7684\u6761\u4ef6");
        CellRangeAddress mergeRegion = new CellRangeAddress(lastRowIndex, lastRowIndex, 0, workflowInfoTitles.size() - 1);
        sheet.addMergedRegion(mergeRegion);
        Row workflowInfoRow = sheet.createRow(lastRowIndex++);
        Cell workflowInfoCell = workflowInfoRow.createCell(0);
        this.cellMergeBord(sheet, mergeRegion, this.getTitleStyle(workbook));
        workflowInfoCell.setCellValue("\u5de5\u4f5c\u6d41\u7684\u6d41\u8f6c\u4fe1\u606f");
        Row row = sheet.createRow(lastRowIndex++);
        CellStyle cellStyle = this.getHeaderCellStyle(workbook);
        CellStyle cellContentStyle = this.getCellStyle(workbook);
        for (int i = 0; i < workflowInfoTitles.size(); ++i) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(workflowInfoTitles.get(i));
        }
        for (FlowTransferInfo transferInfo : flowTransferInfos) {
            Row row1 = sheet.createRow(lastRowIndex++);
            Cell cell0 = row1.createCell(0);
            cell0.setCellStyle(cellContentStyle);
            cell0.setCellValue(transferInfo.getNodeName());
            Cell cell1 = row1.createCell(1);
            cell1.setCellStyle(cellContentStyle);
            cell1.setCellValue(transferInfo.getTransferTitle());
            Cell cell2 = row1.createCell(2);
            cell2.setCellStyle(cellContentStyle);
            cell2.setCellValue(transferInfo.getNextNodeName());
            Cell cell3 = row1.createCell(3);
            cell3.setCellStyle(cellContentStyle);
            cell3.setCellValue(transferInfo.getTransferCondition());
        }
        return lastRowIndex;
    }

    private int handleBusinessInfo(String uniqueCode, Long workflowMetaInfoVersion, Workbook workbook, Sheet sheet, int lastRowIndex) {
        WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
        workflowBusinessDTO.setWorkflowdefinekey(uniqueCode);
        workflowBusinessDTO.setWorkflowdefineversion(workflowMetaInfoVersion);
        WorkflowBusinessDTO businessInfo = this.getWorkflowBusinessService().getBusiness(workflowBusinessDTO, true);
        if (businessInfo == null) {
            return lastRowIndex;
        }
        List businesses = businessInfo.getBusinesses();
        if (CollectionUtils.isEmpty(businesses)) {
            return lastRowIndex;
        }
        CellRangeAddress mergeRegion = new CellRangeAddress(lastRowIndex, lastRowIndex, 0, businesses.size());
        sheet.addMergedRegion(mergeRegion);
        Row businessTitleRow = sheet.createRow(lastRowIndex++);
        Cell businessTitleCell = businessTitleRow.createCell(0);
        this.cellMergeBord(sheet, mergeRegion, this.getTitleStyle(workbook));
        businessTitleCell.setCellValue("\u7ed1\u5b9a\u7684\u4e1a\u52a1\u6a21\u578b\u4fe1\u606f");
        List<String> bindingRowTitles = Arrays.asList("\u5173\u8054\u7684\u4e1a\u52a1\u6a21\u578b\uff1a", "\u9002\u5e94\u6761\u4ef6\uff1a", "\u7ec4\u7ec7\u673a\u6784\uff1a");
        int bindingsSize = businesses.size();
        CellStyle cellStyle = this.getHeaderCellStyle(workbook);
        CellStyle cellContentStyle = this.getCellStyle(workbook);
        for (int i = 0; i < bindingRowTitles.size(); ++i) {
            Row row1 = sheet.createRow(lastRowIndex++);
            Cell cell0 = row1.createCell(0);
            cell0.setCellStyle(cellStyle);
            cell0.setCellValue(bindingRowTitles.get(i));
            block6: for (int j = 0; j < bindingsSize; ++j) {
                Cell cell1 = row1.createCell(j + 1);
                cell1.setCellStyle(cellContentStyle);
                switch (i) {
                    case 0: {
                        cell1.setCellValue(((Map)businesses.get(j)).get("businesstitle") + "(" + ((Map)businesses.get(j)).get("businesscode") + ")");
                        continue block6;
                    }
                    case 1: {
                        LinkedHashMap adaptcondition = (LinkedHashMap)((Map)businesses.get(j)).get("adaptcondition");
                        if (adaptcondition == null) continue block6;
                        cell1.setCellValue((String)adaptcondition.get("expression"));
                        continue block6;
                    }
                    case 2: {
                        ArrayList unitcode = (ArrayList)((Map)businesses.get(j)).get("unitcode");
                        if (CollectionUtils.isEmpty(unitcode)) continue block6;
                        ArrayList<String> unitList = new ArrayList<String>();
                        for (Map unit : unitcode) {
                            unitList.add((String)unit.get("unittitle"));
                        }
                        cell1.setCellValue(String.join((CharSequence)"\uff1b\n", unitList));
                    }
                }
            }
        }
        return lastRowIndex;
    }

    private CellStyle getCellStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)11);
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(font);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = this.getCellStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    /*
     * Exception decompiling
     */
    public byte[] addBackground(byte[] imageInfo, Color color) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public List<FlowTransferInfo> packagingFlowInfo(List<Map<String, Object>> nodeList) {
        ArrayList<FlowTransferInfo> flowInfoList = new ArrayList<FlowTransferInfo>();
        HashMap<String, Map<String, Object>> nodeMap = new HashMap<String, Map<String, Object>>(nodeList.size());
        nodeList.forEach(node -> nodeMap.put((String)node.get("elementId"), (Map<String, Object>)node));
        for (Map<String, Object> ele : nodeList) {
            if (!"StartNoneEvent".equals(ele.get("elementType")) || !"\u5f00\u59cb".equals(ele.get("elementName"))) continue;
            ArrayNode outgoingList = (ArrayNode)ele.get("outgoingList");
            if (outgoingList == null || outgoingList.isEmpty()) {
                FlowTransferInfo transferInfo = new FlowTransferInfo();
                transferInfo.setNodeName((String)ele.get("nodeName"));
                transferInfo.setTransferTitle("");
                transferInfo.setNextNodeName("");
                transferInfo.setTransferCondition("");
                flowInfoList.add(transferInfo);
                break;
            }
            this.getOutgoingInfoBfs(ele, nodeMap, flowInfoList);
            break;
        }
        return flowInfoList;
    }

    public void getOutgoingInfoBfs(Map<String, Object> ele, Map<String, Map<String, Object>> nodeMap, List<FlowTransferInfo> flowInfoList) {
        LinkedHashSet<String> queue = new LinkedHashSet<String>();
        String elementId = (String)ele.get("elementId");
        queue.add(elementId);
        while (!queue.isEmpty()) {
            ArrayNode outgoingList;
            Map<String, Object> element;
            String id = this.dequeue(queue);
            if (!StringUtils.hasText(id) || (element = nodeMap.get(id)) == null || (outgoingList = (ArrayNode)element.get("outgoingList")) == null || outgoingList.isEmpty()) continue;
            for (JsonNode outgoing : outgoingList) {
                long count;
                String resourceId;
                Map<String, Object> nextNode;
                ArrayNode sequenceFlowOutgoingList;
                String outgoingId = outgoing.get("resourceId").asText();
                Map<String, Object> outgoingSequenceFlow = nodeMap.get(outgoingId);
                if (CollectionUtils.isEmpty(outgoingSequenceFlow) || (sequenceFlowOutgoingList = (ArrayNode)outgoingSequenceFlow.get("outgoingList")) == null || sequenceFlowOutgoingList.isEmpty() || CollectionUtils.isEmpty(nextNode = nodeMap.get(resourceId = sequenceFlowOutgoingList.get(0).get("resourceId").asText()))) continue;
                String nextNodeType = (String)nextNode.get("elementType");
                FlowTransferInfo transferInfo = new FlowTransferInfo();
                transferInfo.setNodeId((String)element.get("elementId"));
                transferInfo.setNodeName((String)element.get("elementName"));
                String elementType = (String)element.get("elementType");
                transferInfo.setNodeType(elementType);
                transferInfo.setTransferTitle((String)outgoingSequenceFlow.get("elementName"));
                String nextNodeId = (String)nextNode.get("elementId");
                transferInfo.setNextNodeId(nextNodeId);
                transferInfo.setNextNodeName((String)nextNode.get("elementName"));
                transferInfo.setNextNodeType((String)nextNode.get("elementType"));
                transferInfo.setTransferCondition((String)outgoingSequenceFlow.get("conditionsequenceflow"));
                flowInfoList.add(transferInfo);
                if ("SubProcess".equals(nextNodeType)) {
                    String subStartId = (String)nextNode.get("subStartId");
                    HashMap<String, Object> subMap = new HashMap<String, Object>();
                    subMap.put("elementId", subStartId);
                    this.getOutgoingInfoBfs(subMap, nodeMap, flowInfoList);
                }
                if ((count = flowInfoList.stream().filter(f -> Objects.equals(nextNode.get("elementId"), f.getNodeId())).count()) > 0L) continue;
                queue.add(nextNodeId);
            }
        }
    }

    private String dequeue(LinkedHashSet<String> queue) {
        if (CollectionUtils.isEmpty(queue)) {
            return null;
        }
        String element = (String)queue.iterator().next();
        queue.remove(element);
        return element;
    }

    public void getOutgoingInfoDfs(Map<String, Object> ele, ArrayNode outgoingList, Map<String, Map<String, Object>> nodeMap, List<FlowTransferInfo> flowInfoList) {
        if (outgoingList == null || outgoingList.isEmpty()) {
            return;
        }
        for (JsonNode jsonNode : outgoingList) {
            String resourceId;
            Map<String, Object> nextNode;
            ArrayNode sequenceFlowOutgoingList;
            String outgoingId = jsonNode.get("resourceId").asText();
            Map<String, Object> outgoingSequenceFlow = nodeMap.get(outgoingId);
            if (CollectionUtils.isEmpty(outgoingSequenceFlow) || (sequenceFlowOutgoingList = (ArrayNode)outgoingSequenceFlow.get("outgoingList")) == null || sequenceFlowOutgoingList.isEmpty() || CollectionUtils.isEmpty(nextNode = nodeMap.get(resourceId = sequenceFlowOutgoingList.get(0).get("resourceId").asText()))) continue;
            FlowTransferInfo transferInfo = new FlowTransferInfo();
            transferInfo.setNodeId((String)ele.get("elementId"));
            transferInfo.setNodeName((String)ele.get("elementName"));
            transferInfo.setTransferTitle((String)outgoingSequenceFlow.get("elementName"));
            transferInfo.setNextNodeId((String)nextNode.get("elementId"));
            transferInfo.setNextNodeName((String)nextNode.get("elementName"));
            transferInfo.setNextNodeType((String)nextNode.get("elementType"));
            transferInfo.setTransferCondition((String)outgoingSequenceFlow.get("conditionsequenceflow"));
            String elementType = (String)ele.get("elementType");
            transferInfo.setNodeType(elementType);
            flowInfoList.add(transferInfo);
            long count = flowInfoList.stream().filter(f -> Objects.equals(nextNode.get("elementId"), f.getNodeId())).count();
            if (count > 0L) continue;
            ArrayNode nextNodeOutgoingList = (ArrayNode)nextNode.get("outgoingList");
            this.getOutgoingInfoDfs(nextNode, nextNodeOutgoingList, nodeMap, flowInfoList);
        }
    }

    public List<Map<String, Object>> getWorkFlowElement(String uniqueCode, Long version) {
        ArrayList<Map<String, Object>> elements = new ArrayList<Map<String, Object>>();
        WorkflowModelDefine define = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, version);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode elementsData = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        HashMap<String, JsonNode> eleMap = new HashMap<String, JsonNode>(elementsData.size());
        for (JsonNode ele : elementsData) {
            eleMap.put(ele.get("resourceId").asText(), ele);
        }
        for (JsonNode element : elementsData) {
            String nodeType = element.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            String nodeId = element.get("resourceId").asText();
            String nodeName = element.get("properties").get("name").asText();
            ArrayNode outgoings = (ArrayNode)element.get("outgoing");
            HashMap<String, String> ele = new HashMap<String, String>(8);
            ele.put("elementId", nodeId);
            ele.put("elementName", nodeName);
            ele.put("elementType", nodeType);
            ele.put("outgoingList", (String)outgoings);
            elements.add(ele);
            this.getOutgoingElement(outgoings, eleMap, elements);
            break;
        }
        return elements.stream().map(map -> map.get("elementId").toString()).distinct().map(elementId -> elements.stream().filter(map -> elementId.equals(map.get("elementId").toString())).findFirst().orElse(null)).collect(Collectors.toList());
    }

    public void getOutgoingElement(ArrayNode outgoings, Map<String, JsonNode> eleMap, List<Map<String, Object>> elements) {
        if (outgoings == null || outgoings.isEmpty()) {
            return;
        }
        for (JsonNode outgoing : outgoings) {
            String nodeType;
            long count;
            String outgoingResourceId = outgoing.get("resourceId").asText();
            JsonNode jsonNode = eleMap.get(outgoingResourceId);
            if (jsonNode == null || (count = elements.stream().filter(e -> Objects.equals(outgoingResourceId, e.get("elementId"))).count()) > 0L) continue;
            String id = jsonNode.get("stencil").get("id").asText();
            ArrayNode outgoingList = (ArrayNode)jsonNode.get("outgoing");
            String string = nodeType = jsonNode.get("stencil").get("nodeType") == null ? "" : jsonNode.get("stencil").get("nodeType").asText();
            if (WorkflowConst.ELEMENTTYPELIST.contains(id) || "Manual".equals(nodeType) || "AUTO".equals(nodeType) || "SubProcess".equals(id)) {
                String nodeName = jsonNode.get("properties").get("name").asText();
                HashMap<String, Object> node = new HashMap<String, Object>(16);
                node.put("elementId", outgoingResourceId);
                node.put("elementName", nodeName);
                node.put("elementType", id);
                node.put("outgoingList", outgoingList);
                if ("SequenceFlow".equals(id)) {
                    JsonNode conditionsequenceflow = jsonNode.get("properties").get("conditionsequenceflow");
                    node.put("conditionsequenceflow", conditionsequenceflow == null ? "" : (conditionsequenceflow.get("expression") == null ? "" : conditionsequenceflow.get("expression").asText()));
                }
                if ("UserTask".equals(id)) {
                    this.getParticipant(jsonNode, node);
                }
                if ("Manual".equals(nodeType)) {
                    node.put("elementNodeType", nodeType);
                }
                elements.add(node);
                if ("SubProcess".equals(id)) {
                    ArrayNode childShapes = (ArrayNode)jsonNode.get("childShapes");
                    HashMap<String, JsonNode> childEleMap = new HashMap<String, JsonNode>(childShapes.size() + eleMap.size());
                    childEleMap.putAll(eleMap);
                    for (JsonNode childEle : childShapes) {
                        childEleMap.put(childEle.get("resourceId").asText(), childEle);
                    }
                    for (JsonNode childEle : childShapes) {
                        String type = childEle.get("stencil").get("id").asText();
                        String subStartId = childEle.get("resourceId").asText();
                        if (!"StartNoneEvent".equals(type)) continue;
                        ArrayNode childEleOutgoings = (ArrayNode)childEle.get("outgoing");
                        String nodeId1 = childEle.get("resourceId").asText();
                        node.put("subStartId", subStartId);
                        String nodeName1 = childEle.get("properties").get("name").asText();
                        HashMap<String, String> node1 = new HashMap<String, String>(8);
                        node1.put("elementId", nodeId1);
                        node1.put("elementName", nodeName1);
                        node1.put("elementType", type);
                        node1.put("outgoingList", (String)childEleOutgoings);
                        elements.add(node1);
                        this.getOutgoingElement(childEleOutgoings, childEleMap, elements);
                        break;
                    }
                }
            }
            this.getOutgoingElement(outgoingList, eleMap, elements);
        }
    }

    private void getParticipant(JsonNode jsonNode, Map<String, Object> node) {
        JsonNode usertaskassignment;
        JsonNode properties = jsonNode.get("properties");
        String multiinstance_type = properties.get("multiinstance_type").asText();
        if (Objects.equals("Parallel", multiinstance_type)) {
            String multiinstance_collection = properties.get("multiinstance_collection").asText();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode userNode = null;
            try {
                userNode = objectMapper.readTree(multiinstance_collection);
            }
            catch (JsonProcessingException e) {
                logger.error("\u83b7\u53d6\u53c2\u4e0e\u8005\u5931\u8d25");
            }
            if (userNode != null && !userNode.isEmpty()) {
                ArrayList participantStrategies = new ArrayList();
                for (JsonNode strategy : userNode) {
                    HashMap<String, String> strategyMap = new HashMap<String, String>(4);
                    JsonNode name = strategy.get("name");
                    String strategyName = name == null ? strategy.get("strategyName").asText() : name.asText();
                    String strategyCfg = strategy.get("str").asText();
                    strategyMap.put("strategyName", strategyName);
                    strategyMap.put("strategyCfg", strategyCfg);
                    participantStrategies.add(strategyMap);
                }
                node.put("participantStrategies", participantStrategies);
            }
        } else if (Objects.equals("None", multiinstance_type) && (usertaskassignment = properties.get("usertaskassignment")) != null && !usertaskassignment.isEmpty()) {
            ArrayList participantStrategies = new ArrayList();
            for (JsonNode strategy : usertaskassignment) {
                HashMap<String, String> strategyMap = new HashMap<String, String>(4);
                JsonNode name = strategy.get("name");
                String strategyName = name == null ? strategy.get("strategyName").asText() : name.asText();
                String strategyCfg = strategy.get("str").asText();
                strategyMap.put("strategyName", strategyName);
                strategyMap.put("strategyCfg", strategyCfg);
                participantStrategies.add(strategyMap);
            }
            node.put("participantStrategies", participantStrategies);
        }
    }

    private WorkflowModel getModel(String uniqueCode, Long processDefineVersion) {
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        return (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
    }

    private void dealMetaDataParentGroup(MetaTreeInfoDTO metaTreeInfoDTO, List<? super MetaTreeInfoDTO> parentList, List<? extends MetaTreeInfoDTO> groupMetas) {
        if (MetaType.GROUP == metaTreeInfoDTO.getType()) {
            List parentGroups = groupMetas.stream().filter(x -> Objects.equals(x.getName(), metaTreeInfoDTO.getParentName()) && Objects.equals(metaTreeInfoDTO.getModuleName(), x.getModuleName())).collect(Collectors.toList());
            for (MetaTreeInfoDTO parentGroup : parentGroups) {
                parentList.add((MetaTreeInfoDTO)parentGroup);
                ArrayList tempChildren = new ArrayList();
                this.dealMetaDataParentGroup(parentGroup, tempChildren, groupMetas);
                parentList.addAll(tempChildren);
            }
        }
        if (MetaType.METADATA == metaTreeInfoDTO.getType()) {
            String groupName = metaTreeInfoDTO.getGroupName();
            String moduleName = metaTreeInfoDTO.getModuleName();
            List tempGroupList = groupMetas.stream().filter(x -> Objects.equals(x.getName(), groupName) && Objects.equals(x.getModuleName(), moduleName)).collect(Collectors.toList());
            for (MetaTreeInfoDTO treeInfoDTO : tempGroupList) {
                parentList.add((MetaTreeInfoDTO)treeInfoDTO);
                this.dealMetaDataParentGroup(treeInfoDTO, parentList, groupMetas);
            }
        }
    }
}

