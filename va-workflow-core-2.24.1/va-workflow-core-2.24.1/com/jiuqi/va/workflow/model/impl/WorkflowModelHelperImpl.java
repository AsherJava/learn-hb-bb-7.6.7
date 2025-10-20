/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.constants.BizState
 *  com.jiuqi.va.domain.todo.VaTodoTransferDTO
 *  com.jiuqi.va.domain.todo.VaTodoTransferVO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.retract.RetractRejectInfo
 *  com.jiuqi.va.domain.workflow.retract.RetractRejectTypeEnum
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDO
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice
 *  com.jiuqi.va.extend.WorkflowNodeBehavior
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BillVerifyClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.client.VaSystemOptionClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.message.feign.client.VaMessageTemplateClient
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.model.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.constants.BizState;
import com.jiuqi.va.domain.todo.VaTodoTransferDTO;
import com.jiuqi.va.domain.todo.VaTodoTransferVO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.retract.RetractRejectInfo;
import com.jiuqi.va.domain.workflow.retract.RetractRejectTypeEnum;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDO;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice;
import com.jiuqi.va.extend.WorkflowNodeBehavior;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BillVerifyClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.client.VaSystemOptionClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.message.feign.client.VaMessageTemplateClient;
import com.jiuqi.va.message.template.domain.VaMessageTemplateDTO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.dao.PlusApprovalInfoDao;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.model.WorkflowModelHelper;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowModelHelperImpl
implements WorkflowModelHelper {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowModelHelperImpl.class);
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private PlusApprovalInfoDao plusApprovalInfoDao;
    @Autowired
    private WorkflowSevice workflowSevice;
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private VaMessageTemplateClient vaMessageTemplateClient;
    @Autowired
    protected VaSystemOptionClient vaSystemOptionClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BillVerifyClient billVerifyClient;
    @Autowired
    private WorkflowStrategySevice workflowStrategySevice;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowRetractLockService retractLockService;
    @Autowired
    private VaWorkflowProcessService processService;
    @Autowired
    private WorkflowOptionService workflowOptionService;

    @Override
    public String getBizDefineTitle(String bizType, String bizCode, String bizDefine) {
        String title = bizDefine;
        TenantDO tenantDO = new TenantDO();
        tenantDO.setTraceId(Utils.getTraceId());
        if ("bill".equalsIgnoreCase(bizType)) {
            tenantDO.addExtInfo("defineCode", (Object)bizDefine);
            R r = this.metaDataClient.findMetaInfoByDefineCode(tenantDO);
            title = (String)r.get((Object)"title");
        } else {
            Object bizDefineTitleObject;
            tenantDO.addExtInfo("bizCode", (Object)bizCode);
            tenantDO.addExtInfo("bizDefine", (Object)bizDefine);
            BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
            R r = bussinessClient.getBizTitle(tenantDO);
            if (r.getCode() == 0 && !ObjectUtils.isEmpty(bizDefineTitleObject = r.get((Object)"bizDefineTitle"))) {
                title = String.valueOf(bizDefineTitleObject);
            }
        }
        return title;
    }

    @Override
    public PlusApprovalInfoDO getPlusApprovalInfoDo(String nodeCode, String processInstanceId, String id) {
        List<ProcessNodeDO> latestProcessNodeDOS = this.getLatestProcessNodeDOS(processInstanceId, nodeCode);
        List nodeIds = latestProcessNodeDOS.stream().map(ProcessNodeDO::getNodeid).distinct().collect(Collectors.toList());
        PlusApprovalInfoDTO plusApprovalInfoDTO = new PlusApprovalInfoDTO();
        plusApprovalInfoDTO.setNodecode(nodeCode);
        plusApprovalInfoDTO.setProcessid(processInstanceId);
        plusApprovalInfoDTO.setNodeIds(nodeIds);
        plusApprovalInfoDTO.setApprovaluser(id);
        List plusApprovalInfoDoList = this.workflowPlusApprovalService.selectByConditionAndNodeIds(plusApprovalInfoDTO);
        if (!CollectionUtils.isEmpty(plusApprovalInfoDoList)) {
            return (PlusApprovalInfoDO)plusApprovalInfoDoList.get(0);
        }
        return null;
    }

    public List<ProcessNodeDO> getLatestProcessNodeDOS(String processId, String nodeCode) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processId);
        List listProcessNode = Optional.ofNullable(this.workflowProcessNodeService.listProcessNode(processNodeDTO)).orElse(Collections.emptyList());
        return this.workflowProcessNodeService.getLatestProcessNodes(nodeCode, listProcessNode, null);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void packageParamPlusApproval(Map<String, Object> customParam, WorkflowDTO workflowDTO) {
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String taskId = workflowDTO.getTaskId();
        List plusApprovalInfoDoList = VaWorkflowUtils.getList(customParam.get("PlusInfos"));
        List notApprovalList = plusApprovalInfoDoList.stream().filter(x -> Objects.nonNull(x.getPlusSignApprovalFlag()) && 1 == x.getPlusSignApprovalFlag()).collect(Collectors.toList());
        List approvalList = plusApprovalInfoDoList.stream().filter(x -> Objects.nonNull(x.getPlusSignApprovalFlag()) && 0 == x.getPlusSignApprovalFlag()).collect(Collectors.toList());
        List allTempPlusApprovalList = plusApprovalInfoDoList.stream().filter(x -> Objects.isNull(x.getPlusSignApprovalFlag())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(allTempPlusApprovalList)) {
            WorkflowOptionDTO workFlowOptionDto = this.workflowHelperService.getWorkFlowOptionDto();
            boolean approvalFlag = String.valueOf(1).equals(workFlowOptionDto.getPlusApprovalFlag());
            if (approvalFlag) {
                customParam.put("PlusInfos", allTempPlusApprovalList);
                customParam.put("approvalFlag", true);
                this.workflowSevice.plusApproval(processInstanceId, null);
            } else {
                Map<String, String> userToIdMap = allTempPlusApprovalList.stream().collect(Collectors.toMap(PlusApprovalInfoDO::getApprovaluser, p -> taskId));
                customParam.put("PlusUserToId", userToIdMap);
                customParam.put("PlusInfos", allTempPlusApprovalList);
                customParam.put("approvalFlag", false);
                this.workflowPlusApprovalService.afterPlusApprovalInfo(customParam);
            }
        }
        if (!CollectionUtils.isEmpty(notApprovalList)) {
            Map<String, String> userTaskIdMap = notApprovalList.stream().collect(Collectors.toMap(PlusApprovalInfoDO::getApprovaluser, p -> taskId));
            customParam.put("PlusUserToId", userTaskIdMap);
            customParam.put("PlusInfos", notApprovalList);
            customParam.put("approvalFlag", false);
            this.workflowPlusApprovalService.afterPlusApprovalInfo(customParam);
        }
        if (!CollectionUtils.isEmpty(approvalList)) {
            customParam.put("PlusInfos", approvalList);
            customParam.put("approvalFlag", true);
            this.workflowSevice.plusApproval(processInstanceId, null);
        }
    }

    @Override
    public List<Map<String, Object>> getTodoTransferInfo(String retractTaskDefineKey, String processInstanceId, String bizCode, String taskId) {
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (!StringUtils.hasText(retractTaskDefineKey) || !StringUtils.hasText(processInstanceId)) {
            return resultList;
        }
        VaTodoTransferDTO vaTodoTransferDTO = new VaTodoTransferDTO();
        vaTodoTransferDTO.setNodecode(retractTaskDefineKey);
        vaTodoTransferDTO.setProcessid(processInstanceId);
        vaTodoTransferDTO.setBizCode(bizCode);
        vaTodoTransferDTO.setNodeid(taskId);
        vaTodoTransferDTO.setIgnoreuser(Boolean.valueOf(true));
        R r = Optional.ofNullable(this.todoClient.listTransferInfo(vaTodoTransferDTO)).orElse(new R());
        Object dataObject = r.get((Object)"data");
        List<Object> list = VaWorkflowUtils.getList(dataObject, VaTodoTransferVO.class);
        if (list.isEmpty()) {
            return resultList;
        }
        if (list.size() > 1) {
            list = list.stream().sorted(Comparator.comparing(VaTodoTransferVO::getCreateTime)).collect(Collectors.toList());
        }
        for (VaTodoTransferVO transferVO : list) {
            HashMap<String, Object> map = new HashMap<String, Object>(8);
            map.put("id", transferVO.getId());
            map.put("createTime", transferVO.getCreateTime());
            map.put("todoTaskId", transferVO.getTodoTaskId());
            map.put("transferTo", transferVO.getTransferTo());
            map.put("transferFrom", transferVO.getTransferFrom());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public Map<String, String> getBillVerifyCodeMap(List<String> userIds, String bizCode) {
        BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
        billVerifyDTO.setAuth(1);
        billVerifyDTO.setBillCode(bizCode);
        billVerifyDTO.setUserIds(userIds);
        R re = this.billVerifyClient.encodeBillCode(billVerifyDTO);
        Map verifyCodes = (Map)re.get((Object)"data");
        return Optional.ofNullable(verifyCodes).orElse(Collections.emptyMap());
    }

    @Override
    public void calculateAutoTaskUser(WorkflowContext workflowContext, JsonNode autoTaskParamNode, WorkflowDTO workflowDTO) throws JsonProcessingException {
        if (Objects.isNull(autoTaskParamNode)) {
            return;
        }
        JsonNode receiveUserNode = autoTaskParamNode.path("receiveUser");
        String strategyModuleName = receiveUserNode.path("strategyModuleName").asText();
        String strategyName = receiveUserNode.path("strategyName").asText();
        if (!StringUtils.hasText(strategyModuleName) || !StringUtils.hasText(strategyName)) {
            logger.error("[\u5de5\u4f5c\u6d41]>>>\u81ea\u52a8\u4efb\u52a1\u6d88\u606f\u6a21\u677f\u53c2\u6570\u6267\u884c\u516c\u5f0f\u53c2\u6570\u7f3a\u5931");
            return;
        }
        JsonNode itemNode = receiveUserNode.path("items");
        Map variablesMap = Optional.ofNullable(workflowDTO.getWorkflowVariables()).orElse(Collections.emptyMap());
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assignParam", itemNode);
        paramMap.put("variables", variablesMap);
        paramMap.put("currentNodeCode", workflowDTO.getExtInfo("processNodeId"));
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        if (vaWorkflowContext != null) {
            ProcessDO processDO = vaWorkflowContext.getProcessDO();
            paramMap.put("processDO", processDO);
            WorkflowDTO dto = new WorkflowDTO();
            dto.setBizType(processDO.getBizmodule());
            dto.setBizDefine(processDO.getBiztype());
            dto.setBizCode(processDO.getBizcode());
            paramMap.put("workflowDTO", dto);
            paramMap.put("subProcessInfoDTO", vaWorkflowContext.getSubProcessInfoDTO());
        }
        Set userIdSet = this.workflowStrategySevice.execute(strategyModuleName, strategyName, paramMap);
        ArrayList userIdList = new ArrayList(userIdSet);
        ObjectNode objectNode = (ObjectNode)receiveUserNode;
        if (userIdSet.size() == 1) {
            objectNode.put("realValue", (String)userIdList.get(0));
        } else if (!CollectionUtils.isEmpty(userIdList)) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(userIdList);
            objectNode.set("realValue", mapper.readTree(json));
        } else {
            objectNode.set("realValue", null);
        }
    }

    private Map<String, Object> getCarbonCopyNoticeTemplateConfig() {
        VaMessageTemplateDTO templateParam = new VaMessageTemplateDTO();
        templateParam.setName("CARBONCOPYMESSAGE");
        templateParam.setFunctionmodule("VAWORKFLOW");
        R r = this.vaMessageTemplateClient.getTemplateConfig(templateParam);
        if (r.getCode() != 0 || !r.containsKey((Object)"templateConfig")) {
            return Collections.emptyMap();
        }
        VaMessageTemplateDTO templateConfig = (VaMessageTemplateDTO)r.get((Object)"templateConfig");
        return Optional.ofNullable(templateConfig.getConfigJson()).orElse(Collections.emptyMap());
    }

    @Override
    public boolean isRetractReject(WorkflowDTO workflowDTO) {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(workflowDTO.getBizCode());
        ProcessDO processDO = this.processService.get(processDTO);
        workflowDTO.addExtInfo("processDO", (Object)processDO);
        if (processDO == null) {
            ProcessDTO dto = new ProcessDTO();
            dto.setBizcode(workflowDTO.getBizCode());
            List histories = this.processService.listHistory(dto);
            if (CollectionUtils.isEmpty(histories)) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processhistoryinfonotfound"));
            }
            ProcessHistoryDO historyDO = (ProcessHistoryDO)histories.get(histories.size() - 1);
            if (new BigDecimal("2").equals(historyDO.getEndstatus())) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
            }
            workflowDTO.addExtInfo("processHistoryDO", (Object)historyDO);
            String processInstanceId = historyDO.getId();
            workflowDTO.setProcessInstanceId(processInstanceId);
            return true;
        }
        String processInstanceId = processDO.getId();
        workflowDTO.setProcessInstanceId(processInstanceId);
        String taskId = workflowDTO.getTaskId();
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        String currUserId = ShiroUtil.getUser().getId();
        List<ProcessNodeDO> processNodes = this.listProcessNode(processInstanceId);
        workflowDTO.addExtInfo("processNodes", processNodes);
        ProcessNodeDO retractNode = processNodes.stream().filter(node -> Objects.equals(taskId, node.getNodeid())).filter(node -> Objects.equals(currUserId, node.getCompleteuserid())).findFirst().orElse(null);
        return retractNode != null && "\u5ba1\u6279\u9a73\u56de".equals(retractNode.getCompleteresult());
    }

    private List<ProcessNodeDO> listProcessNode(String processInstanceId) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processInstanceId);
        return this.workflowProcessNodeService.listProcessNode(processNodeDTO);
    }

    @Override
    public void checkRetractReject(WorkflowDTO workflowDTO, ArrayNode nodes) {
        WorkflowRetractLockDO retractLockDO;
        boolean oldDataFlag;
        String currUserId = ShiroUtil.getUser().getId();
        logger.info("\u7528\u6237{}\uff0c\u4e1a\u52a1{}\u8fdb\u5165\u6d41\u7a0b\u9a73\u56de\u540e\u53d6\u56de\u6821\u9a8c\u903b\u8f91", (Object)currUserId, (Object)workflowDTO.getBizCode());
        if (!BigDecimal.ONE.equals(workflowDTO.getRejectAfterRetract())) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notallowedrejecttodoretract"));
        }
        String taskId = workflowDTO.getTaskId();
        if (!StringUtils.hasText(taskId)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "taskId");
        }
        List<ProcessNodeDO> processNodes = (List<ProcessNodeDO>)workflowDTO.getExtInfo().remove("processNodes");
        if (processNodes == null) {
            processNodes = this.listProcessNode(workflowDTO.getProcessInstanceId());
        }
        if (oldDataFlag = processNodes.stream().anyMatch(node -> node.getIgnoreflag() == null)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.historyprocessnotallowretract"));
        }
        ProcessNodeDO retractNode = processNodes.stream().filter(node -> Objects.equals(taskId, node.getNodeid())).filter(node -> Objects.equals(currUserId, node.getCompleteuserid())).findFirst().orElse(null);
        if (retractNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundretractnode") + VaWorkFlowI18nUtils.getInfo("va.workflow.pleaserefreshpage"));
        }
        ArrayList<ProcessNodeDO> currBranchCompleteNodes = new ArrayList<ProcessNodeDO>();
        ArrayList<ProcessNodeDO> currBranchNodes = new ArrayList<ProcessNodeDO>();
        this.categorizeNodes(processNodes, retractNode, currBranchNodes, currBranchCompleteNodes);
        if (currBranchCompleteNodes.isEmpty()) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
        }
        ProcessNodeDO lastNode = (ProcessNodeDO)currBranchCompleteNodes.get(currBranchCompleteNodes.size() - 1);
        if ("\u6d41\u7a0b\u7ed3\u675f".equals(lastNode.getNodecode())) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processhasendnotallowretract"));
        }
        this.checkPlusApproval(currBranchNodes, retractNode);
        String retractNodeCode = retractNode.getNodecode();
        boolean retractFlag = false;
        String lastNodeCode = null;
        for (int i = currBranchCompleteNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO completeNode = (ProcessNodeDO)currBranchCompleteNodes.get(i);
            String nodecode = completeNode.getNodecode();
            if (lastNodeCode == null) {
                lastNodeCode = nodecode;
            }
            if (!Objects.equals(lastNodeCode, retractNodeCode)) break;
            if (!Objects.equals(taskId, completeNode.getNodeid())) continue;
            retractFlag = true;
            break;
        }
        if (!retractFlag) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
        }
        ProcessDO processDO = (ProcessDO)workflowDTO.getExtInfo("processDO");
        if (processDO != null) {
            List currNodeProperties = this.workflowSevice.currNodeProperties(workflowDTO.getProcessInstanceId());
            for (Map currNodeProperty : currNodeProperties) {
                WorkflowNodeBehavior workflowNodeBehavior;
                String nodeType = (String)currNodeProperty.get("nodeType");
                if ("AutoManualTask".equals(nodeType)) {
                    throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextautomanualtasknotretract"));
                }
                Map workflowNodeBehaviorMap = this.workflowSevice.getWorkflowNodeBehaviorMap();
                if (!"ManualTask".equals(nodeType) || (workflowNodeBehavior = (WorkflowNodeBehavior)workflowNodeBehaviorMap.get((String)currNodeProperty.get("stencilId"))).isCheckRetractMq(workflowDTO)) continue;
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
            }
        }
        ProcessNodeDO targetNode = (ProcessNodeDO)currBranchNodes.get(currBranchNodes.size() - 1);
        String subProcessBranch = retractNode.getSubprocessbranch();
        WorkflowRetractLockDTO retractLockDTO = new WorkflowRetractLockDTO();
        retractLockDTO.setBizcode(workflowDTO.getBizCode());
        retractLockDTO.setLocknode(targetNode.getNodecode());
        if (StringUtils.hasText(subProcessBranch)) {
            retractLockDTO.setSubprocessbranch(subProcessBranch);
        }
        if ((retractLockDO = this.retractLockService.selectOne(retractLockDTO)) != null && retractLockDO.getLockcount() > 0) {
            String lockdescribe = retractLockDO.getLockdescribe();
            String msg = VaWorkFlowI18nUtils.getInfo("va.workflow.currbizhasbeenlocked");
            if (StringUtils.hasText(lockdescribe)) {
                msg = msg + lockdescribe;
            }
            throw new WorkflowException(msg);
        }
    }

    @Override
    public void handlePgwRetractRejectSubmit(List<ProcessNodeDO> processNodes, ProcessNodeDO retractNode, RetractRejectInfo retractRejectInfo, ArrayNode nodes) {
        String pgwNodeId = retractNode.getPgwnodeid();
        ProcessNodeDO pgwNode = null;
        ArrayList<ProcessNodeDO> pgwNodes = new ArrayList<ProcessNodeDO>();
        for (ProcessNodeDO processNode : processNodes) {
            if (Objects.equals(pgwNodeId, processNode.getNodeid())) {
                pgwNode = processNode;
                continue;
            }
            if (!Objects.equals(processNode.getPgwnodeid(), pgwNodeId)) continue;
            pgwNodes.add(processNode);
        }
        if (pgwNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.divergentgatewaynotfound"));
        }
        HashMap pgwBranchNodesMap = new HashMap();
        for (int i = pgwNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = (ProcessNodeDO)pgwNodes.get(i);
            String string = processNodeDO.getPgwbranch();
            if (pgwBranchNodesMap.containsKey(string)) {
                ((List)pgwBranchNodesMap.get(string)).add(processNodeDO);
                continue;
            }
            ArrayList<Object> processNodeDOList = new ArrayList<Object>();
            processNodeDOList.add(processNodeDO);
            pgwBranchNodesMap.put(string, processNodeDOList);
        }
        HashMap branchRetractNodesMap = new HashMap();
        block2: for (Map.Entry entry : pgwBranchNodesMap.entrySet()) {
            String branch = (String)entry.getKey();
            List nodeList = (List)entry.getValue();
            String lastNodeCode = null;
            for (ProcessNodeDO processNodeDO : nodeList) {
                if (lastNodeCode == null) {
                    lastNodeCode = processNodeDO.getNodecode();
                }
                if (!Objects.equals(lastNodeCode, processNodeDO.getNodecode())) continue block2;
                if (branchRetractNodesMap.containsKey(branch)) {
                    ((List)branchRetractNodesMap.get(branch)).add(processNodeDO);
                    continue;
                }
                ArrayList<ProcessNodeDO> processNodeDOList = new ArrayList<ProcessNodeDO>();
                processNodeDOList.add(processNodeDO);
                branchRetractNodesMap.put(branch, processNodeDOList);
            }
        }
        String pgwNodeCode = pgwNode.getNodecode();
        String string = retractNode.getPgwbranch();
        String submitNodeCode = retractRejectInfo.getSubmitNodeCode();
        BizState changedBizState = BizState.AUDITING;
        List currBranchNodes = processNodes.stream().filter(node -> Objects.equals(node.getPgwbranch(), pgwBranch) || node.getPgwnodeid() == null).filter(node -> !Objects.equals(node.getNodecode(), pgwNodeCode)).filter(node -> !Objects.equals(node.getNodecode(), retractNode.getNodecode())).filter(node -> !Objects.equals(node.getCompletecomment(), "\u81ea\u52a8\u540c\u610f")).collect(Collectors.toList());
        if (!currBranchNodes.isEmpty() && Objects.equals(submitNodeCode, ((ProcessNodeDO)currBranchNodes.get(currBranchNodes.size() - 1)).getNodecode())) {
            changedBizState = BizState.COMMITTED;
        }
        retractRejectInfo.setChangedBizState(changedBizState);
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.PGW_RETRACT_REJECT_SUBMIT);
        retractRejectInfo.setPgwNode(pgwNode);
        String pgwJoinNodeCode = VaWorkflowNodeUtils.getPgwJoinNodeCode(nodes, pgwNodeCode);
        if (pgwJoinNodeCode == null) {
            logger.warn("\u672a\u83b7\u53d6\u5230\u6c47\u805a\u7f51\u5173");
        }
        retractRejectInfo.setPgwJoinNodeCode(pgwJoinNodeCode);
        retractRejectInfo.setPgwBranch(string);
        retractRejectInfo.setRetractPgwProcessNodes(branchRetractNodesMap);
    }

    @Override
    public void handleCommonRetractRejectSubmit(ProcessNodeDO retractNode, List<ProcessNodeDO> processNodes, RetractRejectInfo retractRejectInfo) {
        ProcessNodeDO processNodeDO;
        String nodecode;
        ArrayList<ProcessNodeDO> retractNodes = new ArrayList<ProcessNodeDO>();
        String retractNodeCode = retractNode.getNodecode();
        for (int i = processNodes.size() - 1; i >= 0 && Objects.equals(retractNodeCode, nodecode = (processNodeDO = processNodes.get(i)).getNodecode()); --i) {
            retractNodes.add(processNodeDO);
        }
        String submitNodeCode = retractRejectInfo.getSubmitNodeCode();
        BizState changedBizState = BizState.AUDITING;
        for (int i = processNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO2 = processNodes.get(i);
            String nodecode2 = processNodeDO2.getNodecode();
            if (Objects.equals(retractNodeCode, nodecode2) || "\u81ea\u52a8\u540c\u610f".equals(processNodeDO2.getCompletecomment())) continue;
            if (!Objects.equals(submitNodeCode, nodecode2)) break;
            changedBizState = BizState.COMMITTED;
        }
        retractRejectInfo.setChangedBizState(changedBizState);
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.COMMON_RETRACT_REJECT_SUBMIT);
        retractRejectInfo.setRetractProcessNodes(retractNodes);
        retractRejectInfo.setDelUnCompleteTodoFlag(true);
    }

    @Override
    public void handleSubRetractRejectSub(ProcessNodeDO retractNode, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes, RetractRejectInfo retractRejectInfo) {
        String subProcessNodeId = retractNode.getSubprocessnodeid();
        String retractNodeCode = retractNode.getNodecode();
        String subProcessBranch = retractNode.getSubprocessbranch();
        ProcessNodeDO subNode = null;
        ArrayList<ProcessNodeDO> currBranchNodes = new ArrayList<ProcessNodeDO>();
        ArrayList<ProcessNodeDO> targetNodes = new ArrayList<ProcessNodeDO>();
        for (ProcessNodeDO processNode : processNodes) {
            if (Objects.equals(processNode.getNodeid(), subProcessNodeId)) {
                subNode = processNode;
                continue;
            }
            if (!Objects.equals(subProcessBranch, processNode.getSubprocessbranch())) continue;
            currBranchNodes.add(processNode);
            if (processNode.getCompletetime() != null || BigDecimal.ONE.equals(processNode.getIgnoreflag())) continue;
            targetNodes.add(processNode);
        }
        if (subNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.subprocessnotfound"));
        }
        ArrayList<ProcessNodeDO> retractNodes = new ArrayList<ProcessNodeDO>();
        String lastNodeCode = null;
        for (int i = currBranchNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = (ProcessNodeDO)currBranchNodes.get(i);
            String nodecode = processNodeDO.getNodecode();
            if (lastNodeCode == null) {
                lastNodeCode = nodecode;
                continue;
            }
            if (Objects.equals(nodecode, lastNodeCode)) continue;
            if (!Objects.equals(retractNodeCode, nodecode)) break;
            retractNodes.add(processNodeDO);
        }
        String targetNodeCode = ((ProcessNodeDO)targetNodes.get(0)).getNodecode();
        ArrayList<ProcessNodeDO> needUpdateRejectStatusNodes = new ArrayList<ProcessNodeDO>();
        boolean flag = true;
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            String nodecode;
            ProcessNodeDO completeNode = completeNodes.get(i);
            if (!Objects.equals(completeNode.getSubprocessbranch(), subProcessBranch) || Objects.equals(nodecode = completeNode.getNodecode(), retractNodeCode)) continue;
            if (Objects.equals(nodecode, targetNodeCode)) {
                flag = false;
                needUpdateRejectStatusNodes.add(completeNode);
                continue;
            }
            if (!flag) break;
            needUpdateRejectStatusNodes.add(completeNode);
        }
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.SUB_RETRACT_REJECT_SUB);
        retractRejectInfo.setSubProcessNodeCode(subNode.getNodecode());
        retractRejectInfo.setSubProcessBranch(subProcessBranch);
        retractRejectInfo.setRetractTargetNodes(targetNodes);
        retractRejectInfo.setRetractProcessNodes(retractNodes);
        retractRejectInfo.setNeedUpdateRejectStatusNodes(needUpdateRejectStatusNodes);
    }

    @Override
    public void handlePgwRetractRejectPgw(ProcessNodeDO retractNode, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes, RetractRejectInfo retractRejectInfo) {
        String pgwNodeId = retractNode.getPgwnodeid();
        String retractNodeCode = retractNode.getNodecode();
        String pgwBranch = retractNode.getPgwbranch();
        ProcessNodeDO pgwNode = null;
        ArrayList<ProcessNodeDO> currBranchNodes = new ArrayList<ProcessNodeDO>();
        ArrayList<ProcessNodeDO> targetNodes = new ArrayList<ProcessNodeDO>();
        for (ProcessNodeDO node : processNodes) {
            if (node.getCompletetime() == null && Objects.equals(node.getNodeid(), pgwNodeId)) {
                pgwNode = node;
                continue;
            }
            if (!Objects.equals(node.getPgwbranch(), pgwBranch)) continue;
            currBranchNodes.add(node);
            if (node.getCompletetime() != null || BigDecimal.ONE.equals(node.getIgnoreflag())) continue;
            targetNodes.add(node);
        }
        if (pgwNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.divergentgatewaynotfound"));
        }
        ArrayList<ProcessNodeDO> retractNodes = new ArrayList<ProcessNodeDO>();
        String lastNodeCode = null;
        for (int i = currBranchNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = (ProcessNodeDO)currBranchNodes.get(i);
            String nodecode = processNodeDO.getNodecode();
            if (lastNodeCode == null) {
                lastNodeCode = nodecode;
                continue;
            }
            if (Objects.equals(nodecode, lastNodeCode)) continue;
            if (!Objects.equals(retractNodeCode, nodecode)) break;
            retractNodes.add(processNodeDO);
        }
        String targetNodeCode = ((ProcessNodeDO)targetNodes.get(0)).getNodecode();
        ArrayList<ProcessNodeDO> needUpdateRejectStatusNodes = new ArrayList<ProcessNodeDO>();
        boolean flag = true;
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO completeNode = completeNodes.get(i);
            String nodecode = completeNode.getNodecode();
            if (!Objects.equals(completeNode.getPgwbranch(), pgwBranch) || Objects.equals(nodecode, retractNodeCode)) continue;
            if (Objects.equals(nodecode, targetNodeCode)) {
                flag = false;
                needUpdateRejectStatusNodes.add(completeNode);
                continue;
            }
            if (!flag) break;
            needUpdateRejectStatusNodes.add(completeNode);
        }
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.PGW_RETRACT_REJECT_PGW);
        retractRejectInfo.setPgwNode(pgwNode);
        retractRejectInfo.setPgwBranch(pgwBranch);
        retractRejectInfo.setRetractTargetNodes(targetNodes);
        retractRejectInfo.setRetractProcessNodes(retractNodes);
        retractRejectInfo.setNeedUpdateRejectStatusNodes(needUpdateRejectStatusNodes);
    }

    @Override
    public void handleCommonRetractRejectPgw(ProcessNodeDO retractNode, List<ProcessNodeDO> unCompleteNodes, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes, RetractRejectInfo retractRejectInfo) {
        String retractNodeCode = retractNode.getNodecode();
        ArrayList<ProcessNodeDO> targetNodes = new ArrayList<ProcessNodeDO>();
        HashSet<String> targetNodeCodes = new HashSet<String>();
        ProcessNodeDO pgwNode = null;
        for (ProcessNodeDO unCompleteNode : unCompleteNodes) {
            if (unCompleteNode.getPgwnodeid() == null) continue;
            if (Objects.equals(unCompleteNode.getNodeid(), unCompleteNode.getPgwnodeid())) {
                pgwNode = unCompleteNode;
                continue;
            }
            targetNodes.add(unCompleteNode);
            targetNodeCodes.add(unCompleteNode.getNodecode());
        }
        if (pgwNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.divergentgatewaynotfound"));
        }
        ArrayList<ProcessNodeDO> retractNodes = new ArrayList<ProcessNodeDO>();
        String lastNodeCode = null;
        for (int i = processNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = processNodes.get(i);
            if (Objects.equals(processNodeDO.getNodeid(), pgwNode.getNodeid())) continue;
            String nodecode = processNodeDO.getNodecode();
            if (lastNodeCode == null) {
                lastNodeCode = nodecode;
                continue;
            }
            if (targetNodeCodes.contains(nodecode)) continue;
            if (!Objects.equals(retractNodeCode, nodecode)) break;
            retractNodes.add(processNodeDO);
        }
        targetNodes.add(pgwNode);
        ProcessNodeDO targetNode = (ProcessNodeDO)targetNodes.get(0);
        String targetNodeCode = targetNode.getNodecode();
        if (targetNodeCodes.size() > 1) {
            targetNodeCode = pgwNode.getNodecode();
        }
        String targetPgwBranch = targetNode.getPgwbranch();
        String oldTargetPgwNodeId = null;
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = completeNodes.get(i);
            if (!Objects.equals(targetNode.getNodecode(), processNodeDO.getNodecode())) continue;
            oldTargetPgwNodeId = processNodeDO.getPgwnodeid();
            break;
        }
        ArrayList<ProcessNodeDO> needUpdateRejectStatusNodes = new ArrayList<ProcessNodeDO>();
        boolean flag = true;
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO completeNode = completeNodes.get(i);
            String nodecode = completeNode.getNodecode();
            if (completeNode.getPgwnodeid() != null && !Objects.equals(completeNode.getPgwnodeid(), oldTargetPgwNodeId) || Objects.equals(nodecode, retractNodeCode)) continue;
            if (Objects.equals(nodecode, targetNodeCode)) {
                flag = false;
                needUpdateRejectStatusNodes.add(completeNode);
                continue;
            }
            if (!flag) break;
            needUpdateRejectStatusNodes.add(completeNode);
        }
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.COMMON_RETRACT_REJECT_PGW);
        retractRejectInfo.setPgwNode(pgwNode);
        retractRejectInfo.setPgwBranch(targetPgwBranch);
        retractRejectInfo.setRetractTargetNodes(targetNodes);
        retractRejectInfo.setRetractProcessNodes(retractNodes);
        retractRejectInfo.setNeedUpdateRejectStatusNodes(needUpdateRejectStatusNodes);
    }

    @Override
    public void handlePgwRetractRejectCommon(List<ProcessNodeDO> processNodes, ProcessNodeDO retractNode, List<ProcessNodeDO> completeNodes, RetractRejectInfo retractRejectInfo, ArrayNode nodes) {
        String pgwNodeId = retractNode.getPgwnodeid();
        ProcessNodeDO pgwNode = null;
        ArrayList<ProcessNodeDO> pgwNodes = new ArrayList<ProcessNodeDO>();
        ArrayList<ProcessNodeDO> targetNodes = new ArrayList<ProcessNodeDO>();
        for (ProcessNodeDO processNode : processNodes) {
            if (Objects.equals(pgwNodeId, processNode.getNodeid())) {
                pgwNode = processNode;
                continue;
            }
            if (Objects.equals(processNode.getPgwnodeid(), pgwNodeId)) {
                pgwNodes.add(processNode);
            }
            if (processNode.getCompletetime() != null || processNode.getPgwnodeid() != null) continue;
            targetNodes.add(processNode);
        }
        if (pgwNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.divergentgatewaynotfound"));
        }
        String pgwBranch = retractNode.getPgwbranch();
        HashMap pgwBranchNodesMap = new HashMap();
        for (int i = pgwNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = (ProcessNodeDO)pgwNodes.get(i);
            String string = processNodeDO.getPgwbranch();
            if (pgwBranchNodesMap.containsKey(string)) {
                ((List)pgwBranchNodesMap.get(string)).add(processNodeDO);
                continue;
            }
            ArrayList<Object> processNodeDOList = new ArrayList<Object>();
            processNodeDOList.add(processNodeDO);
            pgwBranchNodesMap.put(string, processNodeDOList);
        }
        HashMap branchRetractNodesMap = new HashMap();
        block2: for (Map.Entry entry : pgwBranchNodesMap.entrySet()) {
            String branch = (String)entry.getKey();
            List nodeList = (List)entry.getValue();
            String lastNodeCode = null;
            for (ProcessNodeDO processNodeDO : nodeList) {
                if (lastNodeCode == null) {
                    lastNodeCode = processNodeDO.getNodecode();
                }
                if (!Objects.equals(lastNodeCode, processNodeDO.getNodecode())) continue block2;
                if (branchRetractNodesMap.containsKey(branch)) {
                    ((List)branchRetractNodesMap.get(branch)).add(processNodeDO);
                    continue;
                }
                ArrayList<ProcessNodeDO> processNodeDOList = new ArrayList<ProcessNodeDO>();
                processNodeDOList.add(processNodeDO);
                branchRetractNodesMap.put(branch, processNodeDOList);
            }
        }
        String retractNodeCode = retractNode.getNodecode();
        String string = ((ProcessNodeDO)targetNodes.get(0)).getNodecode();
        boolean flag = true;
        ArrayList<ProcessNodeDO> needUpdateRejectStatusNodes = new ArrayList<ProcessNodeDO>();
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO completeNode = completeNodes.get(i);
            String nodecode = completeNode.getNodecode();
            if (completeNode.getPgwnodeid() != null && !Objects.equals(completeNode.getPgwbranch(), pgwBranch) || Objects.equals(nodecode, retractNodeCode)) continue;
            if (Objects.equals(nodecode, string)) {
                flag = false;
                needUpdateRejectStatusNodes.add(completeNode);
                continue;
            }
            if (!flag) break;
            needUpdateRejectStatusNodes.add(completeNode);
        }
        needUpdateRejectStatusNodes.add(pgwNode);
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.PGW_RETRACT_REJECT_COMMON);
        String pgwNodeCode = pgwNode.getNodecode();
        retractRejectInfo.setPgwNode(pgwNode);
        String pgwJoinNodeCode = VaWorkflowNodeUtils.getPgwJoinNodeCode(nodes, pgwNodeCode);
        if (pgwJoinNodeCode == null) {
            logger.warn("\u672a\u83b7\u53d6\u5230\u6c47\u805a\u7f51\u5173");
        }
        retractRejectInfo.setPgwJoinNodeCode(pgwJoinNodeCode);
        retractRejectInfo.setPgwBranch(pgwBranch);
        retractRejectInfo.setRetractTargetNodes(targetNodes);
        retractRejectInfo.setRetractPgwProcessNodes(branchRetractNodesMap);
        retractRejectInfo.setNeedUpdateRejectStatusNodes(needUpdateRejectStatusNodes);
    }

    @Override
    public void handleCommonRetractRejectCommon(List<ProcessNodeDO> unCompleteNodes, ProcessNodeDO retractNode, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes, RetractRejectInfo retractRejectInfo) {
        ProcessNodeDO targetNode = unCompleteNodes.get(0);
        ArrayList<ProcessNodeDO> retractNodes = new ArrayList<ProcessNodeDO>();
        String retractNodeCode = retractNode.getNodecode();
        String lastNodeCode = null;
        for (int i = processNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO processNodeDO = processNodes.get(i);
            String nodecode = processNodeDO.getNodecode();
            if (lastNodeCode == null) {
                lastNodeCode = nodecode;
                continue;
            }
            if (Objects.equals(nodecode, lastNodeCode)) continue;
            if (!Objects.equals(retractNodeCode, nodecode)) break;
            retractNodes.add(processNodeDO);
        }
        String targetNodeCode = targetNode.getNodecode();
        ArrayList<ProcessNodeDO> needUpdateRejectStatusNodes = new ArrayList<ProcessNodeDO>();
        boolean flag = true;
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO completeNode = completeNodes.get(i);
            String nodecode = completeNode.getNodecode();
            if (Objects.equals(nodecode, retractNodeCode)) continue;
            if (Objects.equals(nodecode, targetNodeCode)) {
                flag = false;
                needUpdateRejectStatusNodes.add(completeNode);
                continue;
            }
            if (!flag) break;
            needUpdateRejectStatusNodes.add(completeNode);
        }
        retractRejectInfo.setRetractRejectType(RetractRejectTypeEnum.COMMON_RETRACT_REJECT_COMMON);
        retractRejectInfo.setRetractTargetNodes(unCompleteNodes);
        retractRejectInfo.setRetractProcessNodes(retractNodes);
        retractRejectInfo.setNeedUpdateRejectStatusNodes(needUpdateRejectStatusNodes);
    }

    @Override
    public void checkRetract(WorkflowDTO workflowDTO, List<ProcessNodeDO> processNodeDOList, String submitNodeCode) {
        String taskId = workflowDTO.getTaskId();
        ProcessNodeDO retractNode = this.findRetractNode(processNodeDOList, taskId, submitNodeCode);
        if (retractNode == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundretractnode"));
        }
        String retractNodeCode = retractNode.getNodecode();
        String retractTaskId = retractNode.getNodeid();
        ArrayList<ProcessNodeDO> completeNodes = new ArrayList<ProcessNodeDO>();
        ArrayList<ProcessNodeDO> processNodes = new ArrayList<ProcessNodeDO>();
        this.categorizeNodes(processNodeDOList, retractNode, processNodes, completeNodes);
        if (!this.isRetractValid(completeNodes, retractNodeCode, retractTaskId)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
        }
        processNodes.stream().filter(node -> node.getCompletetime() == null).filter(node -> Objects.equals(node.getNodecode(), retractNodeCode)).filter(node -> Objects.equals(node.getCompleteuserid(), retractNode.getCompleteuserid())).findAny().ifPresent(node -> {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.existssametasknotallowretract"));
        });
        this.checkPlusApproval(processNodes, retractNode);
    }

    private ProcessNodeDO findRetractNode(List<ProcessNodeDO> processNodeDOList, String taskId, String submitNodeCode) {
        if (StringUtils.hasText(taskId)) {
            return processNodeDOList.stream().filter(node -> node.getCompletetime() != null).filter(node -> Objects.equals(taskId, node.getNodeid())).findFirst().orElse(null);
        }
        String retractLastNodeCode = null;
        boolean submitFlag = false;
        boolean findLastCompleteUserFlag = false;
        ProcessNodeDO submitNode = null;
        ProcessNodeDO retractNode = null;
        for (ProcessNodeDO nodeDO : processNodeDOList) {
            if (Objects.equals(nodeDO.getNodeid(), nodeDO.getPgwnodeid()) || BigDecimal.ONE.equals(nodeDO.getHiddenflag()) || "\u81ea\u52a8\u540c\u610f".equals(nodeDO.getCompletecomment())) continue;
            if (Objects.equals(submitNodeCode, nodeDO.getNodecode())) {
                submitNode = nodeDO;
                submitFlag = true;
                continue;
            }
            if (!submitFlag) continue;
            if (retractLastNodeCode == null) {
                retractLastNodeCode = nodeDO.getNodecode();
            }
            if (Objects.equals(retractLastNodeCode, nodeDO.getNodecode())) {
                if (nodeDO.getCompletetime() != null) continue;
                retractNode = submitNode;
                continue;
            }
            findLastCompleteUserFlag = true;
            break;
        }
        if (findLastCompleteUserFlag) {
            String userId = ShiroUtil.getUser().getId();
            for (int i = processNodeDOList.size() - 1; i >= 0; --i) {
                ProcessNodeDO nodeDO = processNodeDOList.get(i);
                if (nodeDO.getCompletetime() == null || !Objects.equals(nodeDO.getCompleteuserid(), userId)) continue;
                retractNode = nodeDO;
                break;
            }
        }
        return retractNode;
    }

    private void categorizeNodes(List<ProcessNodeDO> processNodeDOList, ProcessNodeDO retractNode, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes) {
        String retractNodeSubBranch = retractNode.getSubprocessbranch();
        String retractNodeSubNodeId = retractNode.getSubprocessnodeid();
        String retractNodePgwBranch = retractNode.getPgwbranch();
        String retractNodePgwNodeId = retractNode.getPgwnodeid();
        if (StringUtils.hasText(retractNodeSubBranch)) {
            this.categorizeBySubBranch(processNodeDOList, retractNodeSubBranch, retractNodeSubNodeId, processNodes, completeNodes);
        } else if (StringUtils.hasText(retractNodePgwBranch)) {
            this.categorizeByPgwBranch(processNodeDOList, retractNodePgwBranch, retractNodePgwNodeId, processNodes, completeNodes);
        } else {
            this.addAllNodes(processNodeDOList, processNodes, completeNodes);
        }
    }

    private void categorizeBySubBranch(List<ProcessNodeDO> processNodeDOList, String retractNodeSubBranch, String retractNodeSubNodeId, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes) {
        for (ProcessNodeDO node : processNodeDOList) {
            if (BigDecimal.ONE.equals(node.getHiddenflag()) || !Objects.equals(node.getSubprocessbranch(), retractNodeSubBranch) && node.getSubprocessnodeid() != null && (node.getSubprocessnodeid() == null || Objects.equals(node.getSubprocessnodeid(), retractNodeSubNodeId))) continue;
            processNodes.add(node);
            if (node.getCompletetime() == null || Objects.equals(node.getNodeid(), node.getSubprocessnodeid())) continue;
            completeNodes.add(node);
        }
    }

    private void categorizeByPgwBranch(List<ProcessNodeDO> processNodeDOList, String retractNodePgwBranch, String retractNodePgwNodeId, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes) {
        for (ProcessNodeDO node : processNodeDOList) {
            if (BigDecimal.ONE.equals(node.getHiddenflag()) || !Objects.equals(node.getPgwbranch(), retractNodePgwBranch) && node.getPgwnodeid() != null && (node.getPgwnodeid() == null || Objects.equals(node.getPgwnodeid(), retractNodePgwNodeId))) continue;
            processNodes.add(node);
            if (node.getCompletetime() == null || Objects.equals(node.getNodeid(), node.getPgwnodeid())) continue;
            completeNodes.add(node);
        }
    }

    private void addAllNodes(List<ProcessNodeDO> processNodeDOList, List<ProcessNodeDO> processNodes, List<ProcessNodeDO> completeNodes) {
        for (ProcessNodeDO node : processNodeDOList) {
            processNodes.add(node);
            if (node.getCompletetime() == null) continue;
            completeNodes.add(node);
        }
    }

    private boolean isRetractValid(List<ProcessNodeDO> completeNodes, String retractNodeCode, String retractTaskId) {
        for (int i = completeNodes.size() - 1; i >= 0; --i) {
            ProcessNodeDO nodeDO = completeNodes.get(i);
            if ("\u81ea\u52a8\u540c\u610f".equals(nodeDO.getCompletecomment()) && !Objects.equals(retractTaskId, nodeDO.getNodeid())) continue;
            if (!Objects.equals(nodeDO.getNodecode(), retractNodeCode)) break;
            if (!Objects.equals(retractTaskId, nodeDO.getNodeid())) continue;
            return true;
        }
        return false;
    }

    private void checkPlusApproval(List<ProcessNodeDO> processNodeDOList, ProcessNodeDO retractNode) {
        Map<String, List<ProcessNodeDO>> unCompleteNodesMap = processNodeDOList.stream().filter(node -> node.getCompletetime() == null && !Objects.equals(node.getNodecode(), retractNode.getNodecode())).filter(node -> !Objects.equals(node.getNodeid(), node.getPgwnodeid())).filter(node -> !Objects.equals(node.getNodeid(), node.getSubprocessnodeid())).filter(node -> node.getNodecode() != null && node.getNodeid() != null).collect(Collectors.groupingBy(ProcessNodeDO::getNodecode));
        if (unCompleteNodesMap.isEmpty()) {
            return;
        }
        String processInstanceId = processNodeDOList.get(0).getProcessid();
        for (Map.Entry<String, List<ProcessNodeDO>> entry : unCompleteNodesMap.entrySet()) {
            List nodeIds;
            List<ProcessNodeDO> nodes = entry.getValue();
            if (CollectionUtils.isEmpty(nodes) || (nodeIds = nodes.stream().map(ProcessNodeDO::getNodeid).distinct().collect(Collectors.toList())).isEmpty()) continue;
            String nodeCode = entry.getKey();
            PlusApprovalInfoDTO plusApprovalInfoDTO = new PlusApprovalInfoDTO();
            plusApprovalInfoDTO.setProcessid(processInstanceId);
            plusApprovalInfoDTO.setNodeIds(nodeIds);
            plusApprovalInfoDTO.setNodecode(nodeCode);
            List plusInfos = this.workflowPlusApprovalService.selectByConditionAndNodeIds(plusApprovalInfoDTO);
            for (PlusApprovalInfoDO plusInfo : plusInfos) {
                Integer plusSignApprovalFlag = plusInfo.getPlusSignApprovalFlag();
                if (plusSignApprovalFlag == null || plusSignApprovalFlag != 1) continue;
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.currnodehasplusnotallowretract"));
            }
        }
    }
}

