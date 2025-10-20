/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.biz.utils.VerifyUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.todo.VaTodoTransferDTO
 *  com.jiuqi.va.domain.todo.VaTodoTransferVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.NodeView
 *  com.jiuqi.va.domain.workflow.NodeViewDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.SubProcessInfoDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowOperation
 *  com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessInfo
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.domain.workflow.approval.count.ApprovalCountDTO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.retract.RetractTypeEnum
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService
 *  com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.utils.VerifyUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.todo.VaTodoTransferDTO;
import com.jiuqi.va.domain.todo.VaTodoTransferVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.NodeView;
import com.jiuqi.va.domain.workflow.NodeViewDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.SubProcessInfoDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowOperation;
import com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessInfo;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.domain.workflow.approval.count.ApprovalCountDTO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.retract.RetractTypeEnum;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService;
import com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.constants.VaWorkflowConstants;
import com.jiuqi.va.workflow.dao.WorkflowProcessNodeDao;
import com.jiuqi.va.workflow.domain.WorkflowConst;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowCommentService;
import com.jiuqi.va.workflow.service.WorkflowProcessInstanceService;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.service.impl.help.WorkflowRejectDesignateNodeService;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowThreadUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowProcessNodeServiceImpl
implements WorkflowProcessNodeService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowProcessNodeServiceImpl.class);
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowCommentService workflowCommentService;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowRejectDesignateNodeService workflowRejectDesignateNodeService;
    @Autowired
    private WorkflowProcessNodeDao workflowProcessNodeDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private VaI18nClient vaI18nClient;
    @Autowired
    private VaWorkflowNodeConfigService vaWorkflowNodeConfigService;
    private WorkflowSevice workflowSevice;
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    private WorkflowProcessInstanceService workflowProcessInstanceService;
    private WorkflowNextNodeService workflowNextNodeService;

    public WorkflowSevice getWorkflowSevice() {
        if (this.workflowSevice == null) {
            this.workflowSevice = (WorkflowSevice)ApplicationContextRegister.getBean(WorkflowSevice.class);
        }
        return this.workflowSevice;
    }

    public WorkflowPlusApprovalService getWorkflowPlusApprovalService() {
        if (this.workflowPlusApprovalService == null) {
            this.workflowPlusApprovalService = (WorkflowPlusApprovalService)ApplicationContextRegister.getBean(WorkflowPlusApprovalService.class);
        }
        return this.workflowPlusApprovalService;
    }

    public WorkflowProcessInstanceService getWorkflowProcessInstanceService() {
        if (this.workflowProcessInstanceService == null) {
            this.workflowProcessInstanceService = (WorkflowProcessInstanceService)ApplicationContextRegister.getBean(WorkflowProcessInstanceService.class);
        }
        return this.workflowProcessInstanceService;
    }

    public WorkflowNextNodeService getWorkflowNextNodeService() {
        if (this.workflowNextNodeService == null) {
            this.workflowNextNodeService = (WorkflowNextNodeService)ApplicationContextRegister.getBean(WorkflowNextNodeService.class);
        }
        return this.workflowNextNodeService;
    }

    public ProcessNodeDO get(ProcessNodeDTO processNodeDTO) {
        return (ProcessNodeDO)this.workflowProcessNodeDao.selectOne(processNodeDTO);
    }

    public List<ProcessNodeDO> listProcessNode(ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid()) && !StringUtils.hasText(processNodeDTO.getNodeid()) && CollectionUtils.isEmpty(processNodeDTO.getProcessIdList()) && CollectionUtils.isEmpty(processNodeDTO.getBizCodes())) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processIdList = processNodeDTO.getProcessIdList();
        if (CollectionUtils.isEmpty(processIdList)) {
            return this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
        }
        return this.workflowProcessNodeDao.listProcessNodeByProcessIdList(processNodeDTO);
    }

    public List<Map<String, Object>> selectProcessNode(ProcessDTO processDTO) {
        return new ArrayList<Map<String, Object>>(Optional.ofNullable(this.workflowProcessNodeDao.selectProcessNode(processDTO)).orElse(Collections.emptyList()));
    }

    @Transactional(rollbackFor={Exception.class})
    public void add(ProcessNodeDO processNode) {
        VaWorkflowContext vaWorkflowContext;
        String completecomment;
        String tenantName = processNode.getTenantName();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processNode.getProcessid());
        processNodeDTO.setNodeid(processNode.getNodeid());
        processNodeDTO.setBizcode(processNode.getBizcode());
        processNodeDTO.setCompleteuserid(processNode.getCompleteuserid());
        processNodeDTO.setTenantName(tenantName);
        List<ProcessNodeDO> processNodes = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
        if (processNodes != null && processNodes.size() > 0) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.cantinsertdouble"));
        }
        processNode.setId(UUID.randomUUID().toString());
        if (processNode.getOrdernum() == null) {
            processNode.setOrdernum(new BigDecimal(System.currentTimeMillis()));
        }
        if (processNode.getReceivetime() == null) {
            processNode.setReceivetime(new Date());
        }
        if (processNode.getCountersignflag() == null) {
            processNode.setCountersignflag(new BigDecimal(0));
        }
        if (processNode.getIgnoreflag() == null) {
            processNode.setIgnoreflag(BigDecimal.ZERO);
        }
        if (processNode.getCompleteuserid() != null && processNode.getCompleteusercode() == null) {
            UserDTO user = new UserDTO();
            user.setId(processNode.getCompleteuserid());
            user.setTenantName(tenantName);
            UserDO userDO = this.authUserClient.get(user);
            if (userDO != null) {
                processNode.setCompleteuserunitcode(userDO.getUnitcode());
                processNode.setCompleteusername(userDO.getName());
            }
        }
        if ((completecomment = processNode.getCompletecomment()) != null) {
            processNode.setCompletecomment(completecomment);
        }
        if ((vaWorkflowContext = VaContext.getVaWorkflowContext()) != null) {
            Map customParam = vaWorkflowContext.getCustomParam();
            if (customParam != null && customParam.containsKey("RetractType")) {
                RetractTypeEnum retractType = (RetractTypeEnum)customParam.get("RetractType");
                if (retractType != RetractTypeEnum.RETRACT_REJECT) {
                    this.editPgwInfo(processNode, vaWorkflowContext);
                    this.editSubProcessInfo(processNode, vaWorkflowContext);
                }
            } else {
                this.editPgwInfo(processNode, vaWorkflowContext);
                this.editSubProcessInfo(processNode, vaWorkflowContext);
            }
        }
        this.workflowProcessNodeDao.insert(processNode);
    }

    private void editSubProcessInfo(ProcessNodeDO processNode, VaWorkflowContext vaWorkflowContext) {
        SubProcessInfoDTO subProcessInfoDTO = vaWorkflowContext.getSubProcessInfoDTO();
        if (!ObjectUtils.isEmpty(subProcessInfoDTO)) {
            if (subProcessInfoDTO.isSubProcessTaskNodeCode(processNode.getNodecode()) && (!StringUtils.hasText(processNode.getSubprocessnodeid()) || vaWorkflowContext.getOperation() == WorkflowOperation.REFRESH)) {
                String nodeId = processNode.getNodeid();
                processNode.setSubprocessnodeid(subProcessInfoDTO.getSubProcessNodeId());
                processNode.setSubprocessbranch(subProcessInfoDTO.getSubProcessBranch(nodeId));
                if (StringUtils.hasText(subProcessInfoDTO.getSubProcessBranchName())) {
                    processNode.setProcessnodename(processNode.getProcessnodename() + " \u2014 " + subProcessInfoDTO.getSubProcessBranchName(nodeId));
                }
            }
            if (subProcessInfoDTO.isSubProcessFinishFlag() && processNode.getNodeid().equals(processNode.getSubprocessnodeid())) {
                processNode.setCompletetime(new Date());
                processNode.setCompleteresult(VaWorkFlowI18nUtils.getInfo("va.workflow.endsubprocess"));
            }
        }
    }

    private void editPgwInfo(ProcessNodeDO processNode, VaWorkflowContext vaWorkflowContext) {
        Map customParam = vaWorkflowContext.getCustomParam();
        if (!customParam.containsKey("pgwPlusApprovalFlag")) {
            Set pgwJoinNodeIdSet = (Set)customParam.get("pgwJoinNodeIdSet");
            if (!CollectionUtils.isEmpty(pgwJoinNodeIdSet) && pgwJoinNodeIdSet.contains(processNode.getNodeid())) {
                ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                processNodeDTO.setProcessid(processNode.getProcessid());
                processNodeDTO.setNodeid((String)customParam.get("transmitPgwNodeId"));
                processNodeDTO.setSort("receivetime");
                processNodeDTO.setOrder("desc");
                List<ProcessNodeDO> processNodeDOS = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
                if (!CollectionUtils.isEmpty(processNodeDOS)) {
                    ProcessNodeDO oldProcessNode = processNodeDOS.get(0);
                    oldProcessNode.setCompletetime(new Date());
                    oldProcessNode.setCompleteresult(VaWorkFlowI18nUtils.getInfo("va.workflow.endgatewayprocess"));
                    this.workflowProcessNodeDao.updateByPrimaryKey(oldProcessNode);
                    if (oldProcessNode.getNodeid().equals(oldProcessNode.getPgwnodeid())) {
                        customParam.put("transmitPwgBranch", null);
                        customParam.put("transmitPgwNodeId", null);
                    } else {
                        customParam.put("transmitPwgBranch", oldProcessNode.getPgwbranch());
                        customParam.put("transmitPgwNodeId", oldProcessNode.getPgwnodeid());
                    }
                }
                processNode.setPgwnodeid((String)customParam.get("transmitPgwNodeId"));
                processNode.setPgwbranch((String)customParam.get("transmitPwgBranch"));
            } else {
                String pgwNodeId = null;
                if (customParam.get("pgwNodeId") != null) {
                    pgwNodeId = (String)((Map)customParam.get("pgwNodeId")).get(processNode.getNodecode());
                }
                processNode.setPgwnodeid(StringUtils.hasText((String)customParam.get("transmitPgwNodeId")) ? (String)customParam.get("transmitPgwNodeId") : pgwNodeId);
                if (!StringUtils.hasText(processNode.getPgwnodeid()) && "\u81ea\u52a8\u540c\u610f".equals(processNode.getCompletecomment())) {
                    processNode.setPgwnodeid((String)customParam.get("transmitPgwNodeId"));
                }
                if ("\u6d41\u7a0b\u7ed3\u675f".equals(processNode.getNodecode())) {
                    processNode.setPgwnodeid(null);
                }
                customParam.put("transmitPgwNodeId", processNode.getPgwnodeid());
            }
            if (!StringUtils.hasText(processNode.getPgwnodeid())) {
                processNode.setPgwbranch(null);
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void addBatch(List<ProcessNodeDO> processNodes, String tenantName) {
        int i = 0;
        for (ProcessNodeDO processNode : processNodes) {
            processNode.setTenantName(tenantName);
            processNode.setOrdernum(new BigDecimal(System.currentTimeMillis() + (long)i));
            this.add(processNode);
            ++i;
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void update(ProcessNodeDO processNode) {
        ProcessNodeDTO processNodeDTO;
        ProcessNodeDO oldProcessNode = null;
        if (StringUtils.hasText(processNode.getNodeid())) {
            List<ProcessNodeDO> processNodeDOList;
            processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setBizcode(processNode.getBizcode());
            processNodeDTO.setNodeid(processNode.getNodeid());
            if (processNode.getCountersignflag() != null && processNode.getCountersignflag().intValue() != 0) {
                processNodeDTO.setCompleteusercode(processNode.getCompleteusercode());
                processNodeDTO.setCompleteusername(processNode.getCompleteusername());
                processNodeDTO.setCompleteuserid(processNode.getCompleteuserid());
            }
            if (!Objects.isNull(processNode.getExtInfo()) && processNode.getExtInfo().containsKey("completeUserType") && 0 == Integer.parseInt(String.valueOf(processNode.getExtInfo("completeUserType")))) {
                processNodeDOList = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
                if (CollectionUtils.isEmpty(processNodeDOList)) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt"));
                }
                for (ProcessNodeDO processNodeDO : processNodeDOList) {
                    if (processNode.getCompleteusertype() != null && !new BigDecimal(0).equals(processNodeDO.getCompleteusertype())) continue;
                    oldProcessNode = processNodeDO;
                    break;
                }
            } else if (new BigDecimal(1).equals(processNode.getApprovalflag())) {
                processNodeDOList = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
                if (CollectionUtils.isEmpty(processNodeDOList)) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt"));
                }
                oldProcessNode = processNodeDOList.get(0);
            } else {
                oldProcessNode = (ProcessNodeDO)this.workflowProcessNodeDao.selectOne(processNodeDTO);
            }
            if (oldProcessNode == null) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt"));
            }
        } else {
            processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setBizcode(processNode.getBizcode());
            processNodeDTO.setSort("receivetime");
            processNodeDTO.setOrder("desc");
            List<ProcessNodeDO> processNodeDOS = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
            if (CollectionUtils.isEmpty(processNodeDOS)) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt"));
            }
            oldProcessNode = processNodeDOS.get(0);
            if ("\u6d41\u7a0b\u7ed3\u675f".equals(oldProcessNode.getNodecode())) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
            }
            if (oldProcessNode.getCountersignflag() != null && 1 == oldProcessNode.getCountersignflag().intValue()) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
            }
        }
        oldProcessNode.setCompleteuserid(processNode.getCompleteuserid());
        oldProcessNode.setCompleteusercode(processNode.getCompleteusercode());
        oldProcessNode.setCompleteunitcode(processNode.getCompleteunitcode());
        oldProcessNode.setCompleteuserunitcode(processNode.getCompleteuserunitcode());
        oldProcessNode.setRejectstatus(processNode.getRejectstatus());
        oldProcessNode.setCompleteusername(processNode.getCompleteusername());
        oldProcessNode.setCommentcolor(processNode.getCommentcolor());
        oldProcessNode.setCompletetime(processNode.getCompletetime());
        if (processNode.getReceivetime() != null) {
            oldProcessNode.setReceivetime(processNode.getReceivetime());
        }
        oldProcessNode.setCompleteresult(processNode.getCompleteresult());
        String completecomment = processNode.getCompletecomment();
        if (completecomment != null) {
            oldProcessNode.setCompletecomment(completecomment);
        }
        this.workflowProcessNodeDao.updateByPrimaryKey(oldProcessNode);
        if (processNode.getExtInfo("addCommonComment") != null && ((Boolean)processNode.getExtInfo("addCommonComment")).booleanValue()) {
            WorkflowCommentDTO workflowCommentDTO = new WorkflowCommentDTO();
            workflowCommentDTO.setUsername(oldProcessNode.getCompleteuserid());
            workflowCommentDTO.setComment(oldProcessNode.getCompletecomment());
            this.workflowCommentService.add(workflowCommentDTO);
        }
        if (processNode.getExtInfo("delNodeUserSet") != null) {
            List delNodeUserSet = (List)processNode.getExtInfo("delNodeUserSet");
            String processid = oldProcessNode.getProcessid();
            for (String delUserId : delNodeUserSet) {
                ProcessNodeDO processNodeDO;
                processNodeDO = new ProcessNodeDO();
                processNodeDO.setProcessid(processid);
                processNodeDO.setNodeid(processNode.getNodeid());
                processNodeDO.setCompleteuserid(delUserId);
                this.workflowProcessNodeDao.deleteByNodeIdAndUserId(processNodeDO);
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void complete(ProcessNodeDO processNode) {
        WorkflowOperation operation;
        if (!StringUtils.hasText(processNode.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processNode.getProcessid());
        processNodeDTO.setNodeid(processNode.getNodeid());
        if (ObjectUtils.isEmpty(processNode.getExtInfo()) || !processNode.getExtInfo().containsKey("subProcessSubmit")) {
            if (vaWorkflowContext != null) {
                WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
                if (workflowDTO != null) {
                    if (!workflowDTO.isIgnoreCompleteUser()) {
                        processNodeDTO.setCompleteuserid(processNode.getCompleteuserid());
                    }
                } else {
                    processNodeDTO.setCompleteuserid(processNode.getCompleteuserid());
                }
            } else {
                processNodeDTO.setCompleteuserid(processNode.getCompleteuserid());
            }
        }
        processNodeDTO.setBizcode(processNode.getBizcode());
        List processNodeDOList = this.workflowProcessNodeDao.select(processNodeDTO);
        if (CollectionUtils.isEmpty(processNodeDOList)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt"));
        }
        ProcessNodeDO oldProcessNode = processNodeDOList.size() == 1 ? (ProcessNodeDO)processNodeDOList.get(0) : processNodeDOList.stream().filter(o -> !"\u53d6\u56de".equals(o.getCompleteresult())).findFirst().orElseThrow(() -> new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processnotesixt")));
        if (vaWorkflowContext != null) {
            WorkflowDTO workflowDTO;
            Map customParam = vaWorkflowContext.getCustomParam();
            operation = vaWorkflowContext.getOperation();
            if (customParam.containsKey("pgwCompleteOut") && WorkflowOperation.REJECT == operation) {
                processNodeDTO = new ProcessNodeDTO();
                processNodeDTO.setProcessid(processNode.getProcessid());
                processNodeDTO.setNodeid(oldProcessNode.getPgwnodeid());
                List<ProcessNodeDO> processNodeDOS = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
                if (!CollectionUtils.isEmpty(processNodeDOS)) {
                    ProcessNodeDO pgwNode = processNodeDOS.get(0);
                    pgwNode.setCompletetime(new Date());
                    pgwNode.setCompleteresult(VaWorkFlowI18nUtils.getInfo("va.workflow.endgatewayprocess"));
                    this.workflowProcessNodeDao.updateByPrimaryKey(pgwNode);
                }
            } else {
                customParam.put("transmitPgwNodeId", oldProcessNode.getPgwnodeid());
            }
            SubProcessInfoDTO subProcessInfoDTO = vaWorkflowContext.getSubProcessInfoDTO();
            if (!ObjectUtils.isEmpty(subProcessInfoDTO)) {
                if (StringUtils.hasText(oldProcessNode.getSubprocessnodeid())) {
                    subProcessInfoDTO.setSubProcessNodeId(oldProcessNode.getSubprocessnodeid());
                    subProcessInfoDTO.setSubProcessBranch(oldProcessNode.getSubprocessbranch());
                    String processNodeName = oldProcessNode.getProcessnodename();
                    int index = processNodeName.lastIndexOf(" \u2014 ");
                    if (index != -1) {
                        subProcessInfoDTO.setSubProcessBranchName(processNodeName.substring(index + 3));
                    }
                }
                if (subProcessInfoDTO.isSubProcessFinishFlag()) {
                    processNodeDTO = new ProcessNodeDTO();
                    processNodeDTO.setProcessid(processNode.getProcessid());
                    processNodeDTO.setNodeid(subProcessInfoDTO.getSubProcessNodeId());
                    processNodeDTO.setSort("receivetime");
                    processNodeDTO.setOrder("desc");
                    List<ProcessNodeDO> processNodeDOS = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
                    if (!CollectionUtils.isEmpty(processNodeDOS)) {
                        ProcessNodeDO subProcessProcessNode = processNodeDOS.get(0);
                        subProcessProcessNode.setCompletetime(new Date());
                        subProcessProcessNode.setCompleteresult(VaWorkFlowI18nUtils.getInfo("va.workflow.endsubprocess"));
                        this.workflowProcessNodeDao.updateByPrimaryKey(subProcessProcessNode);
                    }
                }
            }
            if ((workflowDTO = vaWorkflowContext.getWorkflowDTO()).isIgnoreCompleteUser()) {
                oldProcessNode.setCompleteuserid(processNode.getCompleteuserid());
            }
        }
        oldProcessNode.setCommentcolor(processNode.getCommentcolor());
        String completecomment = processNode.getCompletecomment();
        if (completecomment != null) {
            oldProcessNode.setCompletecomment(completecomment);
        }
        oldProcessNode.setCompleteresult(processNode.getCompleteresult());
        oldProcessNode.setCompletetime(processNode.getCompletetime() == null ? new Date() : processNode.getCompletetime());
        oldProcessNode.setCompleteunitcode(processNode.getCompleteunitcode());
        if (processNode.getCountersignflag() != null) {
            oldProcessNode.setCountersignflag(processNode.getCountersignflag());
        }
        if (processNode.getRejectstatus() != null) {
            oldProcessNode.setRejectstatus(processNode.getRejectstatus());
        }
        if (processNode.getRejectskipflag() != null) {
            oldProcessNode.setRejectskipflag(processNode.getRejectskipflag());
        }
        oldProcessNode.setReviewmode(processNode.getReviewmode());
        if (!ObjectUtils.isEmpty(processNode.getExtInfo()) && processNode.getExtInfo().containsKey("subProcessSubmit")) {
            oldProcessNode.setCompleteuserid(ShiroUtil.getUser().getId());
        }
        this.workflowProcessNodeDao.updateByPrimaryKey(oldProcessNode);
        if (!oldProcessNode.getNodeid().equals(processNodeDTO.getNodeid())) {
            processNodeDTO.setNodeid(oldProcessNode.getNodeid());
        }
        if (vaWorkflowContext != null) {
            operation = vaWorkflowContext.getOperation();
            if (WorkflowOperation.REJECT != operation) {
                this.workflowProcessNodeDao.deleteByCondition((ProcessNodeDO)processNodeDTO);
            }
        } else {
            this.workflowProcessNodeDao.deleteByCondition((ProcessNodeDO)processNodeDTO);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void completeBatch(TenantDO tenantDO) {
        String tenantName = tenantDO.getTenantName();
        if (tenantDO.getExtInfo("completeProcessNodes") != null) {
            List completeProcessNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)tenantDO.getExtInfo("completeProcessNodes")), ProcessNodeDO.class);
            for (ProcessNodeDO processNode : completeProcessNodes) {
                processNode.setTenantName(tenantName);
                if (processNode.getCompleteresult().equals("\u63d0\u4ea4") && tenantDO.getExtInfo().containsKey("subProcessSubmit")) {
                    processNode.addExtInfo("subProcessSubmit", (Object)true);
                }
                this.complete(processNode);
            }
        }
        if (tenantDO.getExtInfo("deleteUnprocessedNodes") != null && ((Boolean)tenantDO.getExtInfo("deleteUnprocessedNodes")).booleanValue() && (tenantDO.getExtInfo("PROCESSID") != null || tenantDO.getExtInfo("BIZCODE") != null)) {
            ProcessNodeDO processNodeDO = new ProcessNodeDO();
            processNodeDO.setTenantName(tenantName);
            processNodeDO.setProcessid((String)tenantDO.getExtInfo("PROCESSID"));
            processNodeDO.setBizcode((String)tenantDO.getExtInfo("BIZCODE"));
            processNodeDO.setPgwnodeid((String)tenantDO.getExtInfo("transmitRejectPwgNodeId"));
            processNodeDO.setPgwbranch((String)tenantDO.getExtInfo("transmitRejectPwgBranch"));
            VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
            if (!ObjectUtils.isEmpty(vaWorkflowContext) && !ObjectUtils.isEmpty(vaWorkflowContext.getSubProcessInfoDTO())) {
                SubProcessInfoDTO subProcessInfoDTO = vaWorkflowContext.getSubProcessInfoDTO();
                processNodeDO.setSubprocessnodeid(subProcessInfoDTO.getSubProcessNodeId());
                processNodeDO.setSubprocessbranch(subProcessInfoDTO.getSubProcessBranch());
            }
            if (vaWorkflowContext == null) {
                this.workflowProcessNodeDao.deletePendingNode(processNodeDO);
            } else {
                WorkflowOperation operation = vaWorkflowContext.getOperation();
                if (WorkflowOperation.REJECT == operation) {
                    processNodeDO.setIgnoreflag(BigDecimal.ONE);
                    this.workflowProcessNodeDao.updatePendingNode(processNodeDO);
                } else {
                    this.workflowProcessNodeDao.deletePendingNode(processNodeDO);
                }
            }
        }
        if (tenantDO.getExtInfo("processNodes") != null) {
            List processNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)tenantDO.getExtInfo("processNodes")), ProcessNodeDO.class);
            this.addBatch(processNodes, tenantName);
        }
        if (tenantDO.getExtInfo("rejectNodes") != null) {
            Set rejectNodes = (Set)tenantDO.getExtInfo("rejectNodes");
            if (tenantDO.getExtInfo("PROCESSID") == null || "".equals(tenantDO.getExtInfo("PROCESSID"))) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.dealprocessfailedinfoone"));
            }
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("PROCESSID", tenantDO.getExtInfo("PROCESSID"));
            param.put("PROCESSSTATUS", 1);
            for (String nodeId : rejectNodes) {
                ProcessNodeDO processNodeDO = new ProcessNodeDO();
                processNodeDO.setRejectstatus(new BigDecimal(1));
                processNodeDO.setNodeid(nodeId);
                processNodeDO.setProcessid((String)tenantDO.getExtInfo("PROCESSID"));
                this.workflowProcessNodeDao.update(processNodeDO);
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void retractTask(TenantDO tenantDO) {
        String tenantName = tenantDO.getTenantName();
        Map paramMap = tenantDO.getExtInfo();
        if (!paramMap.containsKey("PROCESSID") || !StringUtils.hasText((String)paramMap.get("PROCESSID"))) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.deleteprocessfailedinfoone"));
        }
        ProcessNodeDO processNodeDO = new ProcessNodeDO();
        processNodeDO.setTenantName(tenantName);
        processNodeDO.setProcessid((String)paramMap.get("PROCESSID"));
        boolean deletePendingNode = true;
        if (paramMap.get("deletePendingNode") != null) {
            deletePendingNode = (Boolean)paramMap.get("deletePendingNode");
        }
        if (deletePendingNode) {
            ProcessNodeDO delNodeDO = new ProcessNodeDO();
            delNodeDO.setTenantName(tenantName);
            delNodeDO.setProcessid((String)paramMap.get("PROCESSID"));
            if (tenantDO.getExtInfo("pgwEndRetract") == null) {
                delNodeDO.setPgwnodeid((String)tenantDO.getExtInfo("transmitPgwNodeId"));
                delNodeDO.setPgwbranch((String)tenantDO.getExtInfo("transmitRetractPwgBranch"));
                delNodeDO.addExtInfo("delPgwNodeIdFlag", (Object)true);
            }
            if (tenantDO.getExtInfo("subProcessEndRetract") == null) {
                if (tenantDO.getExtInfo("subBranchEndNodeRetract") == null) {
                    delNodeDO.setSubprocessnodeid((String)tenantDO.getExtInfo("subProcessNodeId"));
                    delNodeDO.setSubprocessbranch((String)tenantDO.getExtInfo("subProcessBranch"));
                } else {
                    delNodeDO.setSubprocessnodeid((String)tenantDO.getExtInfo("transmitSubNodeId"));
                    delNodeDO.setSubprocessbranch((String)tenantDO.getExtInfo("transmitRetractSubBranch"));
                }
                delNodeDO.addExtInfo("delSubProcessNodeIdFlag", (Object)true);
            }
            this.workflowProcessNodeDao.deletePendingNode(delNodeDO);
        }
        if (tenantDO.getExtInfo("pgwEndRetract") != null) {
            ProcessNodeDO pgwNode = new ProcessNodeDO();
            pgwNode.setNodeid((String)tenantDO.getExtInfo("transmitPgwNodeId"));
            pgwNode.setTenantName(tenantName);
            pgwNode.setProcessid((String)paramMap.get("PROCESSID"));
            pgwNode.setCompletetime(null);
            pgwNode.setCompleteresult(null);
            pgwNode.setCompletecomment(null);
            this.workflowProcessNodeDao.retractPgwNode(pgwNode);
            pgwNode.setNodeid((String)tenantDO.getExtInfo("pgwJoinNodeId"));
            pgwNode.setCompleteresult("\u53d6\u56de");
            this.workflowProcessNodeDao.update(pgwNode);
        }
        if (tenantDO.getExtInfo("retractNodes") != null) {
            List nodeIds = (List)tenantDO.getExtInfo("retractNodes");
            for (String nodeId : nodeIds) {
                processNodeDO.setNodeid(nodeId);
                processNodeDO.setCompleteuserid(ShiroUtil.getUser().getId());
                processNodeDO.setCompleteresult("\u53d6\u56de");
                this.workflowProcessNodeDao.update(processNodeDO);
            }
        } else if (tenantDO.getExtInfo("TASKDEFINEKEYS") != null) {
            List taskDefineKeys = (List)tenantDO.getExtInfo("TASKDEFINEKEYS");
            for (int i = 0; i < taskDefineKeys.size(); ++i) {
                String taskDefineKey = (String)taskDefineKeys.get(i);
                processNodeDO.setCompleteresult("\u53d6\u56de");
                processNodeDO.setCompleteuserid((String)paramMap.get("COMPLETEUSER"));
                processNodeDO.setNodecode(taskDefineKey);
                this.workflowProcessNodeDao.update(processNodeDO);
            }
        } else {
            processNodeDO.setCompleteresult("\u53d6\u56de");
            this.workflowProcessNodeDao.update(processNodeDO);
        }
        if (tenantDO.getExtInfo("processNodes") != null) {
            List processNodes = (List)paramMap.get("processNodes");
            this.addBatch(processNodes, tenantName);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void refresh(TenantDO tenantDO) {
        String tenantName = tenantDO.getTenantName();
        Map paramMap = tenantDO.getExtInfo();
        if (!paramMap.containsKey("processInstanceId") || !StringUtils.hasText((String)paramMap.get("processInstanceId"))) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.deleteprocessfailedinfotwo"));
        }
        if (!((Boolean)paramMap.get("refreshFlag")).booleanValue()) {
            ProcessNodeDO processNodeDO = new ProcessNodeDO();
            processNodeDO.setTenantName(tenantName);
            processNodeDO.setProcessid((String)paramMap.get("processInstanceId"));
            processNodeDO.setPgwnodeid((String)tenantDO.getExtInfo("transmitPgwNodeId"));
            processNodeDO.setPgwbranch((String)tenantDO.getExtInfo("transmitPwgBranch"));
            processNodeDO.addExtInfo("delPgwNodeIdFlag", (Object)true);
            processNodeDO.setSubprocessnodeid((String)tenantDO.getExtInfo("subProcessNodeId"));
            processNodeDO.setSubprocessbranch((String)tenantDO.getExtInfo("subProcessBranch"));
            this.workflowProcessNodeDao.deletePendingNode(processNodeDO);
        } else {
            List deleteUserList = (List)paramMap.get("deleteUserList");
            if (!CollectionUtils.isEmpty(deleteUserList)) {
                for (String userId : deleteUserList) {
                    ProcessNodeDO processNodeDO = new ProcessNodeDO();
                    processNodeDO.setTenantName(tenantName);
                    processNodeDO.setProcessid((String)paramMap.get("processInstanceId"));
                    processNodeDO.setNodecode((String)paramMap.get("nodeCode"));
                    processNodeDO.setCompleteuserid(userId);
                    processNodeDO.setSubprocessbranch((String)paramMap.get("subProcessBranch"));
                    this.workflowProcessNodeDao.deleteByNodeCodeAndUserId(processNodeDO);
                }
            }
        }
        if (tenantDO.getExtInfo("processNodes") != null) {
            List processNodes = (List)paramMap.get("processNodes");
            this.addBatch(processNodes, tenantName);
        }
    }

    public R deleteFinishProcessNode(ProcessNodeDO processNode) {
        if (!StringUtils.hasText(processNode.getBizcode())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.deleteprocessfailedinfothree"));
        }
        processNode.setNodecode("\u6d41\u7a0b\u7ed3\u675f");
        int i = this.workflowProcessNodeDao.deleteFinishProcessNode(processNode);
        return R.ok((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.delete") + i + VaWorkFlowI18nUtils.getInfo("va.workflow.endoftheprocess")));
    }

    public void delete(ProcessNodeDO processNodeDO) {
        this.workflowProcessNodeDao.deleteByProcessIdAndNodeId(processNodeDO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void deleteBatch(List<ProcessNodeDO> processNodes, String tenantName) {
        for (ProcessNodeDO processNode : processNodes) {
            processNode.setTenantName(tenantName);
            this.delete(processNode);
        }
    }

    public void deleteByNodeIdAndUserId(ProcessNodeDO processNodeDO) {
        this.workflowProcessNodeDao.deleteByNodeIdAndUserId(processNodeDO);
    }

    public List<ProcessNodeDO> listProcessNodeByCondition(ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getProcessid())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessNodeDao.listProcessNodeByCondition(processNodeDTO);
    }

    public ProcessNodeDO getByCondition(ProcessNodeDTO processNodeDTO) {
        List<ProcessNodeDO> list = this.workflowProcessNodeDao.getByCondition(processNodeDTO);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ProcessNodeDO> listIsExist(ProcessNodeDTO processNodeDTOTemp) {
        return this.workflowProcessNodeDao.listIsExist(processNodeDTOTemp);
    }

    public void updateByNodeCodeAndUserId(ProcessNodeDO processNodeDO) {
        this.workflowProcessNodeDao.update(processNodeDO);
    }

    public List<NodeView> processView(ProcessNodeDTO processNodeDTO) {
        int auth;
        String bizVerifyCode;
        ArrayList<NodeView> views = new ArrayList<NodeView>();
        if ("BILL".equals(processNodeDTO.getBizType()) && !"-".equals(bizVerifyCode = processNodeDTO.getBizVerifyCode()) && (auth = VerifyUtils.doVerify((String)processNodeDTO.getBizcode(), (int)1, (String)bizVerifyCode)) < 1) {
            return views;
        }
        List<ProcessNodeDO> processNodeDOs = this.listProcessNode(processNodeDTO);
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setName("WF1012");
        List itemVOS = this.workflowOptionService.list(optionItemDTO);
        if (itemVOS != null && itemVOS.size() > 0 && !"0".equals(((OptionItemVO)itemVOS.get(0)).getVal())) {
            for (ProcessNodeDO node : processNodeDOs) {
                BigDecimal completeUserType;
                if (!StringUtils.hasText(node.getCompleteresult()) || (completeUserType = node.getCompleteusertype()) == null || completeUserType.intValue() != 1) continue;
                PlusApprovalInfoDTO infoParam = new PlusApprovalInfoDTO();
                infoParam.setApprovaluser(node.getCompleteuserid());
                infoParam.setProcessid(node.getProcessid());
                infoParam.setNodecode(node.getNodecode());
                List plusApprovalList = this.getWorkflowPlusApprovalService().getPlusApprovalUser(infoParam);
                if (plusApprovalList == null || plusApprovalList.isEmpty()) continue;
                PlusApprovalInfoDTO plusApprovalInfo = plusApprovalList.stream().filter(plus -> Objects.equals(plus.getNodeid(), node.getNodeid())).filter(plus -> Objects.equals(plus.getApprovaluser(), node.getCompleteuserid())).findFirst().orElse(null);
                plusApprovalInfo = plusApprovalInfo == null ? (PlusApprovalInfoDTO)plusApprovalList.get(0) : plusApprovalInfo;
                node.addExtInfo("plusApprovaledExplain", (Object)plusApprovalInfo.getApprovalcomment());
            }
        }
        if (processNodeDOs == null || processNodeDOs.size() == 0) {
            return views;
        }
        boolean filterBizNodeFlag = processNodeDTO.isFilterBizNodeFlag();
        Set<Object> activitiNodeCodeSet = new HashSet();
        if (filterBizNodeFlag) {
            activitiNodeCodeSet = this.handleFilterBizNode(processNodeDTO);
        }
        this.processForecast(processNodeDOs, processNodeDTO);
        this.orderByProcessNode(processNodeDOs);
        Date submitTime = null;
        Date firstSubmitTime = null;
        HashMap<String, UserDO> userMap = new HashMap<String, UserDO>();
        NodeView preNode = null;
        HashMap plusApprovaledUserMap = null;
        HashMap<String, List<String>> tempMap = new HashMap<String, List<String>>();
        for (ProcessNodeDO processNode : processNodeDOs) {
            ArrayList<NodeView> thirdNodes;
            NodeView childNode;
            ArrayList<NodeView> childNodes;
            boolean inSameProcess;
            NodeView currNode;
            boolean forecastFlag;
            String nodecode;
            if (processNodeDTO.getBizdefine() == null) {
                processNodeDTO.setBizdefine(processNode.getBizdefine());
            }
            if (new BigDecimal(1).equals(processNode.getHiddenflag()) || filterBizNodeFlag && (!StringUtils.hasText(nodecode = processNode.getNodecode()) || !activitiNodeCodeSet.contains(nodecode))) continue;
            if (Objects.isNull(tempMap.get(processNode.getNodecode()))) {
                tempMap.put(processNode.getNodecode(), new ArrayList());
            }
            if (submitTime == null && "\u63d0\u4ea4".equals(processNode.getProcessnodename())) {
                submitTime = processNode.getReceivetime();
                if (firstSubmitTime == null) {
                    firstSubmitTime = processNode.getReceivetime();
                }
                HashMap<String, Object> requestParam = new HashMap<String, Object>(4);
                requestParam.put("bizcode", processNodeDTO.getBizcode());
                requestParam.put("bizType", processNodeDTO.getBizType());
                requestParam.put("bizVerifyCode", processNodeDTO.getBizVerifyCode());
                requestParam.put("subprocessbranch", processNodeDTO.getSubprocessbranch());
                requestParam.put("designateSubprocessBranch", processNodeDTO.isDesignateSubprocessBranch());
                processNode.addExtInfo("requestParam", requestParam);
            }
            if (views.size() >= 1) {
                preNode = (NodeView)views.get(views.size() - 1);
            }
            String currNodeCode = processNode.getNodecode();
            int counterSignFlag = processNode.getCountersignflag().intValue();
            boolean bl = forecastFlag = processNode.getExtInfo("forecastFlag") != null;
            if (counterSignFlag == 0) {
                boolean isNewNodeFlag;
                boolean isPlusApprovaledUser;
                boolean bl2 = isPlusApprovaledUser = processNode.getApprovalflag() != null && processNode.getApprovalflag().intValue() == 1 && processNode.getCompleteresult() != null && !processNode.getCompleteresult().equals("\u5ba1\u6279\u9a73\u56de");
                if (isPlusApprovaledUser) {
                    ArrayList<UserDO> plusApprovaledUsers;
                    UserDO plusApprovaledUser = this.generatePlusApprovaledUser(processNode, userMap);
                    String nodeid = processNode.getNodeid();
                    String plusApprovalKey = currNodeCode + nodeid;
                    if (plusApprovaledUserMap != null) {
                        if (plusApprovaledUserMap.containsKey(plusApprovalKey)) {
                            ((List)plusApprovaledUserMap.get(plusApprovalKey)).add(plusApprovaledUser);
                            continue;
                        }
                        plusApprovaledUsers = new ArrayList<UserDO>();
                        plusApprovaledUsers.add(plusApprovaledUser);
                        plusApprovaledUserMap.put(plusApprovalKey, plusApprovaledUsers);
                        continue;
                    }
                    plusApprovaledUserMap = new HashMap();
                    plusApprovaledUsers = new ArrayList();
                    plusApprovaledUsers.add(plusApprovaledUser);
                    plusApprovaledUserMap.put(plusApprovalKey, plusApprovaledUsers);
                    continue;
                }
                currNode = this.generateNode(processNode, userMap, tempMap);
                boolean bl3 = isNewNodeFlag = preNode == null || currNodeCode == null || preNode.getNodeCode() == null || !currNodeCode.equals(preNode.getNodeCode()) || !processNode.getNodeid().equals(preNode.getNodeId());
                if (isNewNodeFlag) {
                    if ("\u6d41\u7a0b\u7ed3\u675f".equals(processNode.getProcessnodename()) && !forecastFlag) {
                        Long subTime = submitTime == null ? Long.valueOf(processNode.getCompletetime().getTime() - firstSubmitTime.getTime()) : Long.valueOf(processNode.getCompletetime().getTime() - submitTime.getTime());
                        currNode.setNodeHoldingTime(subTime);
                        submitTime = null;
                        currNode.setApprovalResult(null);
                        currNode.setNodeTime(null);
                    }
                    if (processNode.getCompletetime() == null) {
                        currNode.setNodeState("\u4e0b\u4e00\u6b65");
                    }
                    currNode.setNodeModule(processNode.getNodemodule());
                    views.add(currNode);
                    continue;
                }
                List nodeUsers = preNode.getNodeUser();
                this.addNodeUser(processNode, nodeUsers, currNode, preNode, false);
                continue;
            }
            currNode = this.generateNode(processNode, userMap, tempMap);
            String childNodeName = processNode.getCompleteresult() == null ? "\u5f85\u5ba1\u6279" : "\u5df2\u5ba1\u6279";
            boolean bl4 = inSameProcess = forecastFlag == preNode.isForecastFlag();
            if (currNodeCode != null && currNodeCode.equals(preNode.getNodeCode()) && inSameProcess) {
                childNodes = preNode.getNodeViews();
                childNode = this.getChildNode(childNodeName, childNodes);
                if (childNode != null) {
                    if ("\u5f85\u5ba1\u6279".equals(childNodeName)) {
                        this.addNodeUser(processNode, childNode.getNodeUser(), currNode, childNode, true);
                    }
                    if ("\u5df2\u5ba1\u6279".equals(childNodeName)) {
                        childNode.getNodeViews().add(currNode);
                        childNode.getNodeUser().addAll(currNode.getNodeUser());
                        Collections.sort(childNode.getNodeUser(), new Comparator<UserDO>(){

                            @Override
                            public int compare(UserDO o1, UserDO o2) {
                                Date temp1 = (Date)o1.getExtInfo("completetime");
                                Date temp2 = (Date)o2.getExtInfo("completetime");
                                return temp1.compareTo(temp2);
                            }
                        });
                        childNode.getNodeViews().sort(Comparator.comparing(NodeView::getNodeTime).reversed());
                        preNode.setApprovalResult(((NodeView)childNode.getNodeViews().get(0)).getApprovalResult());
                    }
                } else {
                    thirdNodes = new ArrayList();
                    thirdNodes.add(currNode);
                    childNode = new NodeView();
                    childNode.setNodeName(childNodeName);
                    childNode.setNodeUser(currNode.getNodeUser());
                    childNode.setForecastFlag(forecastFlag);
                    childNode.setNodeViews(thirdNodes);
                    if ("\u5f85\u5ba1\u6279".equals(childNodeName)) {
                        childNodes.add(0, childNode);
                    } else {
                        childNodes.add(childNode);
                    }
                }
                if (processNode.getCompletetime() != null) continue;
                preNode.setNodeState("\u4e0b\u4e00\u6b65");
                continue;
            }
            thirdNodes = new ArrayList<NodeView>();
            thirdNodes.add(currNode);
            childNodes = new ArrayList<NodeView>();
            childNode = new NodeView();
            childNode.setNodeName(childNodeName);
            childNode.setNodeUser(currNode.getNodeUser());
            childNode.setNodeViews(thirdNodes);
            childNode.setForecastFlag(forecastFlag);
            childNodes.add(childNode);
            currNode = new NodeView();
            currNode.setNodeName(processNode.getProcessnodename());
            currNode.setNodeType(Integer.valueOf(processNode.getCountersignflag().intValue()));
            currNode.setNodeCode(processNode.getNodecode());
            currNode.setNodeId(processNode.getNodeid());
            currNode.setPGWNodeId(processNode.getPgwnodeid());
            currNode.setPGWBranch(processNode.getPgwbranch());
            currNode.setSubProcessNodeId(processNode.getSubprocessnodeid());
            currNode.setSubProcessBranch(processNode.getSubprocessbranch());
            currNode.setForecastFlag(forecastFlag);
            currNode.setNodeViews(childNodes);
            if (processNode.getCompletetime() == null) {
                currNode.setNodeState("\u4e0b\u4e00\u6b65");
            } else {
                currNode.setApprovalResult(processNode.getCompleteresult());
            }
            currNode.setNodeModule(processNode.getNodemodule());
            views.add(currNode);
        }
        if (plusApprovaledUserMap != null) {
            for (NodeView view : views) {
                String viewPlusApprovalKey = view.getNodeCode() + view.getNodeId();
                if (!plusApprovaledUserMap.containsKey(viewPlusApprovalKey)) continue;
                List userDOS = (List)plusApprovaledUserMap.get(viewPlusApprovalKey);
                Collections.sort(userDOS, (o1, o2) -> {
                    Date temp1 = (Date)o1.getExtInfo("completetime");
                    Date temp2 = (Date)o2.getExtInfo("completetime");
                    return temp2.compareTo(temp1);
                });
                view.setPlusApprovaledUsers(userDOS);
            }
        }
        Collections.reverse(views);
        VaWorkFlowI18nUtils.convertApprovalLanguage(views);
        this.convertParallelGateway(views);
        this.convertSubProcess(views);
        this.subProcessBranchAuthFilter(views, processNodeDTO);
        this.handleRejectSkip(views, processNodeDTO);
        List<NodeView> returnViews = this.handleCustomView(processNodeDTO, views);
        return CollectionUtils.isEmpty(returnViews) ? views : returnViews;
    }

    private void processForecast(List<ProcessNodeDO> processNodeDOS, ProcessNodeDTO processNodeDTO) {
        if (processNodeDTO.getExtInfo("forecast") == null) {
            return;
        }
        ProcessNodeDO lastProcessNodeDO = processNodeDOS.get(processNodeDOS.size() - 1);
        String processNodeName = lastProcessNodeDO.getProcessnodename();
        if ("\u6d41\u7a0b\u7ed3\u675f".equals(processNodeName)) {
            return;
        }
        try {
            List<Map<String, Object>> processForecastInfo = this.getProcessForecastInfo(new ArrayList<ProcessNodeDO>(processNodeDOS), processNodeDTO.getBizcode());
            for (Map<String, Object> forecastMap : processForecastInfo) {
                Integer auditState = (Integer)forecastMap.get("auditState");
                String nodeType = (String)forecastMap.get("nodeType");
                if (auditState == 1) continue;
                if ("ParallelGateway".equals(nodeType) || "SubProcess".equals(nodeType)) {
                    this.handleSpecialNode(processNodeDOS, forecastMap);
                    continue;
                }
                this.handleCommonNode(processNodeDOS, forecastMap);
            }
        }
        catch (Exception e) {
            log.error("\u67e5\u770b\u6d41\u7a0b\u6267\u884c\u6d41\u7a0b\u9884\u6d4b\u5931\u8d25", e);
        }
    }

    private List<Map<String, Object>> getProcessForecastInfo(List<ProcessNodeDO> processNodeDOS, String bizCode) {
        R r;
        String bizDefine;
        String bizType;
        WorkflowProcessForecastDTO workflowProcessForecastDTO = new WorkflowProcessForecastDTO();
        workflowProcessForecastDTO.setBizCode(bizCode);
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO != null) {
            bizType = processDO.getBizmodule();
            bizDefine = processDO.getBiztype();
            workflowProcessForecastDTO.setWorkflowDefineVersion(Long.valueOf(processDO.getDefineversion().longValue()));
        } else {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                Collections.reverse(processNodeDOS);
                ProcessNodeDO processNodeDO = processNodeDOS.stream().filter(item -> "\u63d0\u4ea4".equals(item.getProcessnodename())).findFirst().orElse(new ProcessNodeDO());
                bizType = processNodeDO.getNodemodule();
                bizDefine = processNodeDO.getBizdefine();
                workflowProcessForecastDTO.addExtInfo("needWorkflow", (Object)false);
            } else {
                ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
                bizType = processHistoryDO.getBizmodule();
                bizDefine = processHistoryDO.getBiztype();
                workflowProcessForecastDTO.setWorkflowDefineVersion(Long.valueOf(processHistoryDO.getDefineversion().longValue()));
                workflowProcessForecastDTO.setWorkflowDefineKey(processHistoryDO.getDefinekey());
                ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
                processRejectNodeDO.setBizcode(bizCode);
                List processRejectNodeDOS = this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
                Long version = CollectionUtils.isEmpty(processRejectNodeDOS) ? null : Long.valueOf(((ProcessRejectNodeDO)processRejectNodeDOS.get(0)).getProcessdefineversion().longValue());
                workflowProcessForecastDTO.setWorkflowDefineVersion(version);
            }
        }
        BussinessClient bussinessClient = this.getBussinessClient(bizType, bizDefine);
        try {
            r = bussinessClient.getProcessForecastInfo(workflowProcessForecastDTO);
        }
        catch (Exception e) {
            WorkflowDTO workflowDTO = new WorkflowDTO();
            workflowDTO.setBizCode(bizCode);
            r = this.getWorkflowProcessInstanceService().processForecast(workflowDTO);
        }
        if (r.getCode() != 0) {
            throw new RuntimeException(r.getMsg());
        }
        return (List)r.get((Object)"data");
    }

    private void handleCommonNode(List<ProcessNodeDO> processNodeDOS, Map<String, Object> forecastMap) {
        Integer auditState = (Integer)forecastMap.get("auditState");
        if (auditState == 2) {
            String auditStatus = (String)forecastMap.get("auditStatus");
            if ("\u5f85\u63d0\u4ea4".equals(auditStatus)) {
                ProcessNodeDO processNodeDO = new ProcessNodeDO();
                processNodeDO.setProcessnodename("\u63d0\u4ea4");
                processNodeDO.setCountersignflag(new BigDecimal(0));
                processNodeDO.addExtInfo("forecastFlag", (Object)true);
                processNodeDOS.add(processNodeDO);
            }
            return;
        }
        String nodeType = (String)forecastMap.get("nodeType");
        if ("EndNoneEvent".equals(nodeType)) {
            ProcessNodeDO processNodeDO = new ProcessNodeDO();
            processNodeDO.setProcessnodename("\u6d41\u7a0b\u7ed3\u675f");
            processNodeDO.setCountersignflag(new BigDecimal(0));
            processNodeDO.addExtInfo("forecastFlag", (Object)true);
            processNodeDOS.add(processNodeDO);
            return;
        }
        String commonNodeId = UUID.randomUUID().toString();
        Object auditInfo = forecastMap.get("auditInfo");
        if (auditInfo == null || CollectionUtils.isEmpty((Collection)auditInfo)) {
            processNodeDOS.add(this.createCommonNode(null, forecastMap, commonNodeId));
            return;
        }
        for (Object auditInfoObj : (Collection)auditInfo) {
            processNodeDOS.add(this.createCommonNode(auditInfoObj, forecastMap, commonNodeId));
        }
    }

    private void handleSpecialNode(List<ProcessNodeDO> processNodeDOS, Map<String, Object> forecastMap) {
        String nodeType = (String)forecastMap.get("nodeType");
        String parentNodeId = this.getParentNodeId(processNodeDOS, forecastMap);
        List branches = (List)forecastMap.get("children");
        int branchIndex = 0;
        for (List branch : branches) {
            String branchId = String.valueOf(branchIndex);
            BigDecimal orderNum = BigDecimal.ZERO;
            for (Map node : branch) {
                Integer auditState = (Integer)node.get("auditState");
                if (auditState != 3) {
                    List processNodeDOList = processNodeDOS.stream().filter(item -> item.getNodecode().equals(node.get("stencilId"))).filter(item -> item.getSubprocessbranch() == null || item.getSubprocessbranch().equals(node.get("subProcessBranch"))).collect(Collectors.toList());
                    ProcessNodeDO processNodeDO = (ProcessNodeDO)processNodeDOList.get(processNodeDOList.size() - 1);
                    if ("ParallelGateway".equals(nodeType)) {
                        branchId = processNodeDO.getPgwbranch();
                    }
                    if ("SubProcess".equals(nodeType)) {
                        branchId = processNodeDO.getSubprocessbranch();
                    }
                    orderNum = processNodeDO.getOrdernum().add(BigDecimal.ONE);
                    continue;
                }
                String commonNodeId = UUID.randomUUID().toString();
                Object auditInfo = node.get("auditInfo");
                if (auditInfo == null || CollectionUtils.isEmpty((Collection)auditInfo)) {
                    ProcessNodeDO commonNode = this.createCommonNode(null, node, commonNodeId);
                    if ("ParallelGateway".equals(nodeType)) {
                        commonNode.setPgwnodeid(parentNodeId);
                        commonNode.setPgwbranch(branchId);
                    }
                    if ("SubProcess".equals(nodeType)) {
                        commonNode.setSubprocessnodeid(parentNodeId);
                        commonNode.setSubprocessbranch(branchId);
                    }
                    commonNode.setOrdernum(orderNum);
                    processNodeDOS.add(commonNode);
                } else {
                    for (Object auditInfoMap : (Collection)auditInfo) {
                        ProcessNodeDO nodeDO = this.createCommonNode(auditInfoMap, node, commonNodeId);
                        if ("ParallelGateway".equals(nodeType)) {
                            nodeDO.setPgwnodeid(parentNodeId);
                            nodeDO.setPgwbranch(branchId);
                        }
                        if ("SubProcess".equals(nodeType)) {
                            nodeDO.setSubprocessnodeid(parentNodeId);
                            nodeDO.setSubprocessbranch(branchId);
                        }
                        nodeDO.setOrdernum(orderNum);
                        processNodeDOS.add(nodeDO);
                    }
                }
                orderNum = orderNum.add(BigDecimal.ONE);
            }
            ++branchIndex;
        }
    }

    private String getParentNodeId(List<ProcessNodeDO> processNodeDOS, Map<String, Object> forecastMap) {
        Integer auditState = (Integer)forecastMap.get("auditState");
        if (auditState == 2) {
            List processNodeDOList = processNodeDOS.stream().filter(item -> item.getNodecode().equals(forecastMap.get("stencilId"))).collect(Collectors.toList());
            ProcessNodeDO parentNodeDO = (ProcessNodeDO)processNodeDOList.get(processNodeDOList.size() - 1);
            return parentNodeDO.getNodeid();
        }
        if (auditState == 3) {
            String nodeType = (String)forecastMap.get("nodeType");
            String nodeName = this.getProcessNodeName(forecastMap.get("nodeName"));
            ProcessNodeDO processNodeDO = new ProcessNodeDO();
            processNodeDO.setProcessnodename(nodeName);
            processNodeDO.setNodecode((String)forecastMap.get("stencilId"));
            processNodeDO.setCountersignflag(this.getCounterSignFlag(forecastMap.get("counterSignFlag")));
            String parentNodeId = UUID.randomUUID().toString();
            processNodeDO.setNodeid(parentNodeId);
            if ("ParallelGateway".equals(nodeType)) {
                processNodeDO.setPgwnodeid(parentNodeId);
            }
            if ("SubProcess".equals(nodeType)) {
                processNodeDO.setSubprocessnodeid(parentNodeId);
            }
            processNodeDO.addExtInfo("forecastFlag", (Object)true);
            processNodeDOS.add(processNodeDO);
            return parentNodeId;
        }
        return UUID.randomUUID().toString();
    }

    private ProcessNodeDO createCommonNode(Object auditInfoObj, Map<String, Object> node, String commonNodeId) {
        BigDecimal counterSignFlag = this.getCounterSignFlag(node.get("counterSignFlag"));
        ProcessNodeDO nodeDO = new ProcessNodeDO();
        nodeDO.setProcessnodename(this.getProcessNodeName(node.get("nodeName")));
        nodeDO.setNodecode((String)node.get("stencilId"));
        nodeDO.setCountersignflag(counterSignFlag);
        nodeDO.setNodeid(counterSignFlag.intValue() == 1 ? UUID.randomUUID().toString() : commonNodeId);
        String userName = null;
        if (auditInfoObj instanceof Map) {
            userName = (String)((Map)auditInfoObj).get("auditUser");
        }
        if (auditInfoObj instanceof String) {
            userName = (String)auditInfoObj;
        }
        nodeDO.setCompleteusername(userName);
        nodeDO.addExtInfo("forecastFlag", (Object)true);
        return nodeDO;
    }

    private String getProcessNodeName(Object nodeName) {
        if (nodeName instanceof String) {
            return (String)nodeName;
        }
        if (nodeName instanceof TextNode) {
            return ((TextNode)nodeName).asText();
        }
        return "";
    }

    private BigDecimal getCounterSignFlag(Object counterSignFlag) {
        if (counterSignFlag == null) {
            return new BigDecimal(0);
        }
        return counterSignFlag instanceof Integer ? new BigDecimal((Integer)counterSignFlag) : (BigDecimal)counterSignFlag;
    }

    private Set<String> handleFilterBizNode(ProcessNodeDTO processNodeDTO) {
        HashSet<String> activitiNodeCodeSet = new HashSet<String>();
        String bizcode = processNodeDTO.getBizcode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizcode);
        ProcessDO process = this.vaWorkflowProcessService.get(processDTO);
        ArrayList processHistoryList = this.vaWorkflowProcessService.listHistory(processDTO);
        if (process != null) {
            ProcessHistoryDO historyDO = new ProcessHistoryDO();
            historyDO.setBizcode(process.getBizcode());
            historyDO.setDefinekey(process.getDefinekey());
            historyDO.setDefineversion(process.getDefineversion());
            processHistoryList.add(historyDO);
        }
        if (processHistoryList.isEmpty()) {
            return activitiNodeCodeSet;
        }
        if (processHistoryList.size() > 1) {
            Map distinctHisMap = processHistoryList.stream().collect(Collectors.toMap(his -> his.getDefinekey() + "#" + his.getDefineversion(), Function.identity(), (oldV, newV) -> oldV));
            processHistoryList = new ArrayList(distinctHisMap.values());
        }
        for (ProcessHistoryDO historyDO : processHistoryList) {
            BigDecimal defineversion = historyDO.getDefineversion();
            String definekey = historyDO.getDefinekey();
            if (defineversion == null || !StringUtils.hasText(definekey)) continue;
            WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(definekey, Long.valueOf(defineversion.longValue()));
            WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
            ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
            ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
            ArrayNode arrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
            this.getActivitiNodeCode(arrayNode, activitiNodeCodeSet);
        }
        activitiNodeCodeSet.add("\u6d41\u7a0b\u7ed3\u675f");
        return activitiNodeCodeSet;
    }

    private void getActivitiNodeCode(ArrayNode arrayNode, Set<String> bizNodeCodeSet) {
        for (JsonNode jsonNode : arrayNode) {
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if ("SubProcess".equals(nodeType)) {
                ArrayNode childShapes = (ArrayNode)jsonNode.get("childShapes");
                this.getActivitiNodeCode(childShapes, bizNodeCodeSet);
            }
            if (!WorkflowConst.ELEMENTTYPELIST.contains(nodeType) && !"SubProcess".equals(nodeType)) continue;
            bizNodeCodeSet.add(jsonNode.get("resourceId").asText());
        }
    }

    private void handleRejectSkip(List<NodeView> views, ProcessNodeDTO processNodeDTO) {
        ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
        processRejectNodeDO.setBizcode(processNodeDTO.getBizcode());
        List processRejectNodeDOS = this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
        HashMap<String, String> rejectSkipNodeMap = null;
        if (CollectionUtils.isEmpty(processRejectNodeDOS)) {
            return;
        }
        for (ProcessRejectNodeDO rejectNodeDO : processRejectNodeDOS) {
            rejectSkipNodeMap = new HashMap<String, String>();
            rejectSkipNodeMap.put(rejectNodeDO.getRejectnodecode(), rejectNodeDO.getSubprocessbranch());
        }
        for (NodeView view : views) {
            if (ObjectUtils.isEmpty(rejectSkipNodeMap)) break;
            if (rejectSkipNodeMap.containsKey(view.getNodeCode()) && StringUtils.hasText(view.getApprovalResult())) {
                view.setRejectSkipNode(true);
                rejectSkipNodeMap.remove(view.getNodeCode());
                continue;
            }
            if (view.ispGatewayFlag()) {
                List pgwViews = view.getPGWViews();
                for (List nodeViewList : pgwViews) {
                    for (NodeView nodeView : nodeViewList) {
                        if (!rejectSkipNodeMap.containsKey(nodeView.getNodeCode()) || !StringUtils.hasText(nodeView.getApprovalResult())) continue;
                        nodeView.setRejectSkipNode(true);
                        rejectSkipNodeMap.remove(nodeView.getNodeCode());
                    }
                }
            }
            if (!view.isSubProcessFlag()) continue;
            List subProcessViews = view.getSubProcessViews();
            for (List subProcessView : subProcessViews) {
                for (NodeView nodeView : subProcessView) {
                    if (!rejectSkipNodeMap.containsKey(nodeView.getNodeCode()) || !Objects.equals(rejectSkipNodeMap.get(nodeView.getNodeCode()), nodeView.getSubProcessBranch()) || !StringUtils.hasText(nodeView.getApprovalResult())) continue;
                    nodeView.setRejectSkipNode(true);
                    rejectSkipNodeMap.remove(nodeView.getNodeCode());
                }
            }
        }
    }

    private void convertSubProcess(List<NodeView> views) {
        Map branchMap;
        List subViews = views.stream().filter(nodeView -> StringUtils.hasText(nodeView.getSubProcessNodeId()) && !nodeView.getNodeId().equals(nodeView.getSubProcessNodeId())).collect(Collectors.toList());
        HashSet<String> subProcessNodeIdSet = new HashSet<String>();
        HashMap subProcessMap = new HashMap();
        for (NodeView subView : subViews) {
            List<NodeView> nodeViews;
            subProcessNodeIdSet.add(subView.getSubProcessNodeId());
            if (subProcessMap.containsKey(subView.getSubProcessNodeId())) {
                branchMap = (Map)subProcessMap.get(subView.getSubProcessNodeId());
                if (branchMap.containsKey(subView.getSubProcessBranch())) {
                    nodeViews = (List)branchMap.get(subView.getSubProcessBranch());
                    nodeViews.add(subView);
                    continue;
                }
                nodeViews = new ArrayList();
                nodeViews.add(subView);
                branchMap.put(subView.getSubProcessBranch(), nodeViews);
                continue;
            }
            HashMap newBranchMap = new HashMap();
            nodeViews = new ArrayList<NodeView>();
            nodeViews.add(subView);
            newBranchMap.put(subView.getSubProcessBranch(), nodeViews);
            subProcessMap.put(subView.getSubProcessNodeId(), newBranchMap);
        }
        for (NodeView view : views) {
            if (subProcessNodeIdSet.contains(view.getNodeId())) {
                view.setSubProcessFlag(true);
            }
            if (!view.isSubProcessFlag() || !subProcessMap.containsKey(view.getNodeId())) continue;
            branchMap = (Map)subProcessMap.get(view.getNodeId());
            ArrayList<List> subProcessViews = new ArrayList<List>();
            for (String branch : branchMap.keySet()) {
                List nodeViews = (List)branchMap.get(branch);
                subProcessViews.add(nodeViews);
                view.setSubProcessViews(subProcessViews);
            }
        }
        views.removeAll(subViews);
    }

    private void subProcessBranchAuthFilter(List<NodeView> views, ProcessNodeDTO processNodeDTO) {
        String startUser;
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(processNodeDTO.getBizcode());
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (ObjectUtils.isEmpty(processDO)) {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                return;
            }
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(0);
            startUser = processHistoryDO.getStartuser();
        } else {
            startUser = processDO.getStartuser();
        }
        String curUserId = ShiroUtil.getUser().getId();
        boolean designateSubprocessBranch = processNodeDTO.isDesignateSubprocessBranch();
        if (designateSubprocessBranch) {
            WorkflowProcessNodeServiceImpl.filterByDesignateSubprocessBranch(views, processNodeDTO.getSubprocessbranch());
            return;
        }
        if (startUser.equals(curUserId)) {
            return;
        }
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setName("WF1014");
        OptionItemVO optionItemVO = (OptionItemVO)this.workflowOptionService.list(optionItemDTO).get(0);
        String val = optionItemVO.getVal();
        if (val.equals("1")) {
            return;
        }
        block0: for (NodeView view : views) {
            if (view.ispGatewayFlag()) continue;
            if (view.isSubProcessFlag()) {
                List subProcessViews = view.getSubProcessViews();
                ArrayList<List> tempBranchViews = new ArrayList<List>();
                block1: for (List branchNodeViews : subProcessViews) {
                    for (NodeView branchNodeView : branchNodeViews) {
                        if (ObjectUtils.isEmpty(branchNodeView.getNodeViews())) {
                            List nodeUser = branchNodeView.getNodeUser();
                            for (UserDO userDO : nodeUser) {
                                if (!curUserId.equals(userDO.getId())) continue;
                                tempBranchViews.add(branchNodeViews);
                                continue block1;
                            }
                            continue;
                        }
                        List nodeViews = branchNodeView.getNodeViews();
                        for (NodeView nodeView : nodeViews) {
                            List nodeUser = nodeView.getNodeUser();
                            for (UserDO userDO : nodeUser) {
                                if (!curUserId.equals(userDO.getId())) continue;
                                tempBranchViews.add(branchNodeViews);
                                continue block1;
                            }
                        }
                    }
                }
                if (tempBranchViews.size() <= 0) continue;
                view.setSubProcessViews(tempBranchViews);
                continue;
            }
            if (ObjectUtils.isEmpty(view.getNodeViews())) {
                List nodeUser = view.getNodeUser();
                for (UserDO userDO : nodeUser) {
                    if (!curUserId.equals(userDO.getId())) continue;
                    break block0;
                }
                continue;
            }
            List nodeViews = view.getNodeViews();
            for (NodeView nodeView : nodeViews) {
                List nodeUser = nodeView.getNodeUser();
                for (UserDO userDO : nodeUser) {
                    if (!curUserId.equals(userDO.getId())) continue;
                    break block0;
                }
            }
        }
        if (StringUtils.hasText(processNodeDTO.getSubprocessbranch())) {
            String subProcessBranch = processNodeDTO.getSubprocessbranch();
            WorkflowProcessNodeServiceImpl.filterByDesignateSubprocessBranch(views, subProcessBranch);
        }
    }

    private static void filterByDesignateSubprocessBranch(List<NodeView> views, String subProcessBranch) {
        if (!StringUtils.hasText(subProcessBranch)) {
            return;
        }
        for (NodeView view : views) {
            if (!view.isSubProcessFlag()) continue;
            List subProcessViews = view.getSubProcessViews();
            ArrayList<List> tempBranchViews = new ArrayList<List>();
            for (List branchNodeViews : subProcessViews) {
                if (!subProcessBranch.equals(((NodeView)branchNodeViews.get(0)).getSubProcessBranch())) continue;
                tempBranchViews.add(branchNodeViews);
            }
            view.setSubProcessViews(tempBranchViews);
        }
    }

    private List<NodeView> handleCustomView(ProcessNodeDTO processNodeDTO, List<NodeView> views) {
        BussinessClient bussinessClient = null;
        String bizType = processNodeDTO.getBizType();
        if (bizType == null) {
            return null;
        }
        bussinessClient = this.getBussinessClient(bizType, processNodeDTO.getBizdefine());
        NodeViewDTO nodeViewDTO = new NodeViewDTO();
        nodeViewDTO.setTraceId(Utils.getTraceId());
        nodeViewDTO.setProcessNodeDTO(processNodeDTO);
        nodeViewDTO.setViews(views);
        return bussinessClient.bussinessProcessView(nodeViewDTO);
    }

    private BussinessClient getBussinessClient(String bizType, String bizDefine) {
        BussinessClient bussinessClient;
        if ("BILL".equals(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType("BILL");
            R r = this.metaDataClient.getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType, false);
        }
        return bussinessClient;
    }

    private void orderByProcessNode(List<ProcessNodeDO> processNodeDOs) {
        List<ProcessNodeDO> subProcessNode = processNodeDOs.stream().filter(processNode -> StringUtils.hasText(processNode.getPgwnodeid()) && !processNode.getNodeid().equals(processNode.getPgwnodeid())).collect(Collectors.toList());
        processNodeDOs.removeAll(subProcessNode);
        subProcessNode.sort(Comparator.comparing(ProcessNodeDO::getPgwnodeid).thenComparing(ProcessNodeDO::getPgwbranch).thenComparing(ProcessNodeDO::getOrdernum).reversed());
        this.orderByProcessNode(processNodeDOs, subProcessNode);
    }

    private void orderByProcessNode(List<ProcessNodeDO> processNodeDOs, List<ProcessNodeDO> subProcessNode) {
        ArrayList<ProcessNodeDO> temp = new ArrayList<ProcessNodeDO>();
        for (ProcessNodeDO processNodeDO : subProcessNode) {
            int index = IntStream.range(0, processNodeDOs.size()).filter(i -> processNodeDO.getPgwnodeid().equals(((ProcessNodeDO)processNodeDOs.get(i)).getNodeid())).findFirst().orElse(-1);
            if (index == -1) {
                temp.add(processNodeDO);
                continue;
            }
            processNodeDOs.add(index + 1, processNodeDO);
        }
        if (temp.size() > 0) {
            this.orderByProcessNode(processNodeDOs, temp);
        }
    }

    private void convertParallelGateway(List<NodeView> views) {
        Map branchMap;
        List subViews = views.stream().filter(nodeView -> StringUtils.hasText(nodeView.getPGWNodeId()) && !nodeView.getNodeId().equals(nodeView.getPGWNodeId())).collect(Collectors.toList());
        HashSet<String> pgwNodeIdSet = new HashSet<String>();
        HashMap pGWMap = new HashMap();
        for (NodeView subView : subViews) {
            List<NodeView> nodeViews;
            pgwNodeIdSet.add(subView.getPGWNodeId());
            if (pGWMap.containsKey(subView.getPGWNodeId())) {
                branchMap = (Map)pGWMap.get(subView.getPGWNodeId());
                if (branchMap.containsKey(subView.getPGWBranch())) {
                    nodeViews = (List)branchMap.get(subView.getPGWBranch());
                    nodeViews.add(subView);
                    continue;
                }
                nodeViews = new ArrayList();
                nodeViews.add(subView);
                branchMap.put(subView.getPGWBranch(), nodeViews);
                continue;
            }
            HashMap newBranchMap = new HashMap();
            nodeViews = new ArrayList<NodeView>();
            nodeViews.add(subView);
            newBranchMap.put(subView.getPGWBranch(), nodeViews);
            pGWMap.put(subView.getPGWNodeId(), newBranchMap);
        }
        for (NodeView view : views) {
            if (pgwNodeIdSet.contains(view.getNodeId())) {
                view.setPGatewayFlag(true);
            }
            if (!view.ispGatewayFlag() || !pGWMap.containsKey(view.getNodeId())) continue;
            branchMap = (Map)pGWMap.get(view.getNodeId());
            ArrayList<List> pGWViews = new ArrayList<List>();
            for (String branch : branchMap.keySet()) {
                List nodeViews = (List)branchMap.get(branch);
                pGWViews.add(nodeViews);
                view.setPGWViews(pGWViews);
            }
        }
        views.removeAll(subViews);
    }

    public R updateCompleteUserAndReceiveTime(ProcessNodeDO processNode) {
        try {
            if (!StringUtils.hasText(processNode.getBizcode())) {
                throw new IllegalArgumentException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":bizcode");
            }
            if (!StringUtils.hasText(processNode.getNodecode())) {
                throw new IllegalArgumentException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":nodecode");
            }
            if (!StringUtils.hasText(processNode.getProcessid())) {
                throw new IllegalArgumentException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":processid");
            }
            if (!StringUtils.hasText(processNode.getNodeid())) {
                throw new IllegalArgumentException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":nodeid");
            }
            processNode.setReceivetime(new Date());
            this.workflowProcessNodeDao.updateCompleteUserAndReceiveTime(processNode);
            return R.ok();
        }
        catch (Exception e) {
            log.error("Error occur while updateProcessNodeCompleteUserId:", e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.updateerror"));
        }
    }

    public List<WorkflowProcessInfo> listProcessNodeForward(ProcessNodeDTO processNodeDTO) {
        List<WorkflowProcessInfo> forwardNodes = new ArrayList<WorkflowProcessInfo>();
        List<Object> processNodeDOs = this.listProcessNode(processNodeDTO);
        List<ProcessNodeDO> unApprovalNodes = processNodeDOs.stream().filter(processNodeDO -> processNodeDO.getCompletetime() == null && !processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(processNodeDOs = processNodeDOs.stream().filter(processNodeDO -> processNodeDO.getCompletetime() != null || processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid())).collect(Collectors.toList()))) {
            return forwardNodes;
        }
        boolean filterBizNodeFlag = processNodeDTO.isFilterBizNodeFlag();
        Set<Object> activitiNodeCodeSet = new HashSet();
        if (filterBizNodeFlag) {
            activitiNodeCodeSet = this.handleFilterBizNode(processNodeDTO);
        }
        this.orderByProcessNode(processNodeDOs);
        String preNodeCode = null;
        String preNodeId = null;
        for (ProcessNodeDO processNodeDO2 : processNodeDOs) {
            boolean isPlusApproved;
            WorkflowProcessInfo workflowProcessInfo;
            boolean newNodeFlag;
            String nodecode;
            if (new BigDecimal(1).equals(processNodeDO2.getHiddenflag()) || filterBizNodeFlag && (!StringUtils.hasText(nodecode = processNodeDO2.getNodecode()) || !activitiNodeCodeSet.contains(nodecode))) continue;
            String currNodeCode = processNodeDO2.getNodecode();
            int counterSignFlag = processNodeDO2.getCountersignflag().intValue();
            if (counterSignFlag == 0) {
                newNodeFlag = !StringUtils.hasText(preNodeCode) || !StringUtils.hasText(preNodeId) || !currNodeCode.equals(preNodeCode) || !processNodeDO2.getNodeid().equals(preNodeId);
            } else {
                boolean bl = newNodeFlag = !StringUtils.hasText(preNodeCode) || !StringUtils.hasText(preNodeId) || !currNodeCode.equals(preNodeCode);
            }
            if (newNodeFlag) {
                workflowProcessInfo = new WorkflowProcessInfo();
                workflowProcessInfo.setNodeId(processNodeDO2.getNodeid());
                workflowProcessInfo.setNodeCode(processNodeDO2.getNodecode());
                workflowProcessInfo.setNodeNameArea(processNodeDO2.getProcessnodename());
                workflowProcessInfo.setPgwNodeId(processNodeDO2.getPgwnodeid());
                workflowProcessInfo.setPgwBranch(processNodeDO2.getPgwbranch());
                workflowProcessInfo.setCounterSignFlag(counterSignFlag == 1);
                workflowProcessInfo.setSubmitFlag("\u63d0\u4ea4".equals(processNodeDO2.getCompleteresult()));
                forwardNodes.add(workflowProcessInfo);
            } else {
                workflowProcessInfo = (WorkflowProcessInfo)forwardNodes.get(forwardNodes.size() - 1);
            }
            if ("\u5ba1\u6279\u7ec8\u6b62".equals(processNodeDO2.getCompleteresult())) {
                workflowProcessInfo.setRejectState(true);
            }
            String delegateUser = processNodeDO2.getDelegateuser();
            boolean bl = isPlusApproved = processNodeDO2.getCompleteusertype() != null && new BigDecimal(1).equals(processNodeDO2.getCompleteusertype());
            if (!processNodeDO2.getNodeid().equals(processNodeDO2.getPgwnodeid())) {
                ArrayList<ProcessNodeDO> processNodes;
                VaTodoTransferDTO vaTodoTransferDTO = new VaTodoTransferDTO();
                vaTodoTransferDTO.setNodecode(processNodeDO2.getNodecode());
                vaTodoTransferDTO.setProcessid(processNodeDO2.getProcessid());
                vaTodoTransferDTO.setBizCode(processNodeDO2.getBizcode());
                vaTodoTransferDTO.setNodeid(processNodeDO2.getNodeid());
                vaTodoTransferDTO.setCompleteuserid(processNodeDO2.getCompleteuserid());
                R r = this.todoClient.listTransferInfo(vaTodoTransferDTO);
                List<VaTodoTransferVO> vaTodoTransferVOList = null;
                if (r.getCode() == 0) {
                    vaTodoTransferVOList = VaWorkflowUtils.getList(r.get((Object)"data"), VaTodoTransferVO.class);
                }
                if (!CollectionUtils.isEmpty(vaTodoTransferVOList)) {
                    if (CollectionUtils.isEmpty(workflowProcessInfo.getTodoTransferInfo())) {
                        processNodes = new ArrayList<ProcessNodeDO>();
                        processNodes.add(processNodeDO2);
                        workflowProcessInfo.setTodoTransferInfo(processNodes);
                    } else {
                        workflowProcessInfo.getTodoTransferInfo().add(processNodeDO2);
                    }
                } else if (StringUtils.hasText(delegateUser)) {
                    if (CollectionUtils.isEmpty(workflowProcessInfo.getDelegateInfo())) {
                        processNodes = new ArrayList();
                        processNodes.add(processNodeDO2);
                        workflowProcessInfo.setDelegateInfo(processNodes);
                    } else {
                        workflowProcessInfo.getDelegateInfo().add(processNodeDO2);
                    }
                } else if (isPlusApproved) {
                    if (CollectionUtils.isEmpty(workflowProcessInfo.getPlusApprovaInfo())) {
                        processNodes = new ArrayList();
                        processNodes.add(processNodeDO2);
                        workflowProcessInfo.setPlusApprovaInfo(processNodes);
                    } else {
                        workflowProcessInfo.getPlusApprovaInfo().add(processNodeDO2);
                    }
                } else if (CollectionUtils.isEmpty(workflowProcessInfo.getApprovaInfo())) {
                    processNodes = new ArrayList();
                    processNodes.add(processNodeDO2);
                    workflowProcessInfo.setApprovaInfo(processNodes);
                } else {
                    workflowProcessInfo.getApprovaInfo().add(processNodeDO2);
                }
            }
            preNodeId = processNodeDO2.getNodeid();
            preNodeCode = processNodeDO2.getNodecode();
        }
        HashMap<String, String> rejectSkipNodeMap = new HashMap<String, String>();
        ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
        processRejectNodeDO.setBizcode(((ProcessNodeDO)processNodeDOs.get(0)).getBizcode());
        List processRejectNodeDOS = this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
        for (ProcessRejectNodeDO rejectNodeDO : processRejectNodeDOS) {
            rejectSkipNodeMap.put(rejectNodeDO.getBerejectednodecode(), rejectNodeDO.getRejectnodecode());
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(processNodeDTO.getBizcode());
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        ProcessHistoryDO processHistoryDO = null;
        ProcessDO newProcessDO = null;
        if (ObjectUtils.isEmpty(processDO)) {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
            newProcessDO = new ProcessDO();
            BeanUtils.copyProperties(processHistoryDO, newProcessDO);
        } else {
            newProcessDO = processDO;
        }
        WorkflowModel model = this.workflowParamService.getModel(newProcessDO.getDefinekey(), newProcessDO.getDefineversion().longValue());
        ArrayNode jsonNodes = this.workflowParamService.getWorkflowProcessNode(model);
        String startResourceId = null;
        for (JsonNode jsonNode : jsonNodes) {
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            String outgoingId = outgoings.get(0).get("resourceId").asText();
            for (JsonNode node : jsonNodes) {
                if (!outgoingId.equals(node.get("resourceId").asText())) continue;
                startResourceId = node.get("target").get("resourceId").asText();
            }
        }
        HashMap<String, Map<String, String>> nodeDependenceMap = new HashMap<String, Map<String, String>>();
        HashMap<String, Map<String, List<String>>> pgwDependenceMap = new HashMap<String, Map<String, List<String>>>();
        HashMap<String, String> currPgwInfoMap = new HashMap<String, String>();
        currPgwInfoMap.put("currPgwNodeCode", null);
        currPgwInfoMap.put("currPgwBranch", null);
        this.pgwInfoGather(jsonNodes, startResourceId, nodeDependenceMap, pgwDependenceMap, currPgwInfoMap);
        for (WorkflowProcessInfo info : forwardNodes) {
            if (!nodeDependenceMap.containsKey(info.getNodeCode()) || !StringUtils.hasText(info.getPgwNodeId()) || ObjectUtils.isEmpty(nodeDependenceMap.get(info.getNodeCode()))) continue;
            info.setPgwNodeCode((String)((Map)nodeDependenceMap.get(info.getNodeCode())).get("pgwNodeCode"));
            info.setPgwBranch((String)((Map)nodeDependenceMap.get(info.getNodeCode())).get("pgwBranch"));
        }
        forwardNodes = this.getForwardProcessNode(forwardNodes, rejectSkipNodeMap, unApprovalNodes, nodeDependenceMap, processDO, processHistoryDO);
        this.convertParallelGateway(forwardNodes, processNodeDOs, nodeDependenceMap);
        ArrayList<WorkflowProcessInfo> tempList = new ArrayList<WorkflowProcessInfo>();
        for (WorkflowProcessInfo info : forwardNodes) {
            if (!CollectionUtils.isEmpty(info.getApprovaInfo()) || !CollectionUtils.isEmpty(info.getPlusApprovaInfo()) || !CollectionUtils.isEmpty(info.getDelegateInfo()) || !CollectionUtils.isEmpty(info.getTodoTransferInfo()) || !CollectionUtils.isEmpty(info.getGatewayInfo())) continue;
            tempList.add(info);
        }
        forwardNodes.removeAll(tempList);
        return forwardNodes;
    }

    private void pgwInfoGather(ArrayNode jsonNodes, String resourceId, Map<String, Map<String, String>> nodeDependenceMap, Map<String, Map<String, List<String>>> pgwDependenceMap, Map<String, String> currPgwInfoMap) {
        for (JsonNode jsonNode : jsonNodes) {
            String nextNodeResourceId;
            ArrayNode flowOutgoings;
            String outgoingId;
            int i;
            ArrayNode outgoings;
            if (!resourceId.equals(jsonNode.get("resourceId").asText())) continue;
            if ("SequenceFlow".equals(jsonNode.get("stencil").get("id").asText()) || nodeDependenceMap.containsKey(resourceId)) {
                return;
            }
            if ("ParallelGateway".equals(jsonNode.get("stencil").get("id").asText())) {
                currPgwInfoMap.put("currPgwNodeCode", resourceId);
                outgoings = (ArrayNode)jsonNode.get("outgoing");
                for (i = 0; i < outgoings.size(); ++i) {
                    if (i == outgoings.size() - 1) {
                        currPgwInfoMap.put("currPgwFinishFlag", "currPgwFinishFlag");
                    }
                    outgoingId = outgoings.get(i).get("resourceId").asText();
                    for (JsonNode flowNode : jsonNodes) {
                        if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                        flowOutgoings = (ArrayNode)flowNode.get("outgoing");
                        nextNodeResourceId = flowOutgoings.get(0).get("resourceId").asText();
                        currPgwInfoMap.put("currPgwBranch", nextNodeResourceId);
                        this.pgwInfoGather(jsonNodes, nextNodeResourceId, nodeDependenceMap, pgwDependenceMap, currPgwInfoMap);
                    }
                }
            }
            if ("JoinParallelGateway".equals(jsonNode.get("stencil").get("id").asText())) {
                if (currPgwInfoMap.containsKey("currPgwFinishFlag")) {
                    currPgwInfoMap.put("currPgwNodeCode", null);
                    currPgwInfoMap.put("currPgwBranch", null);
                    currPgwInfoMap.remove("currPgwFinishFlag");
                } else {
                    return;
                }
            }
            if (!ObjectUtils.isEmpty(currPgwInfoMap.get("currPgwNodeCode")) && !ObjectUtils.isEmpty(currPgwInfoMap.get("currPgwBranch"))) {
                HashMap<String, String> pgwInfoMap = new HashMap<String, String>();
                pgwInfoMap.put("pgwNodeCode", currPgwInfoMap.get("currPgwNodeCode"));
                pgwInfoMap.put("pgwBranch", currPgwInfoMap.get("currPgwBranch"));
                nodeDependenceMap.put(resourceId, pgwInfoMap);
                if (pgwDependenceMap.containsKey(currPgwInfoMap.get("currPgwNodeCode"))) {
                    List<Object> nodeCodes;
                    Map<String, List<String>> currPgwNodeCode = pgwDependenceMap.get(currPgwInfoMap.get("currPgwNodeCode"));
                    if (currPgwNodeCode.containsKey(currPgwInfoMap.get("currPgwBranch"))) {
                        nodeCodes = currPgwNodeCode.get(currPgwInfoMap.get("currPgwBranch"));
                        nodeCodes.add(resourceId);
                    } else {
                        nodeCodes = new ArrayList<String>();
                        nodeCodes.add(resourceId);
                        currPgwNodeCode.put(currPgwInfoMap.get("currPgwBranch"), nodeCodes);
                    }
                } else {
                    ArrayList<String> nodeCodes = new ArrayList<String>();
                    nodeCodes.add(resourceId);
                    HashMap<String, ArrayList<String>> branchMap = new HashMap<String, ArrayList<String>>();
                    branchMap.put(currPgwInfoMap.get("currPgwBranch"), nodeCodes);
                    pgwDependenceMap.put(currPgwInfoMap.get("currPgwNodeCode"), branchMap);
                }
            } else {
                nodeDependenceMap.put(resourceId, null);
            }
            outgoings = (ArrayNode)jsonNode.get("outgoing");
            block3: for (i = 0; i < outgoings.size(); ++i) {
                outgoingId = outgoings.get(i).get("resourceId").asText();
                for (JsonNode flowNode : jsonNodes) {
                    if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                    if ("EndNoneEvent".equals(flowNode.get("stencil").get("id").asText())) continue block3;
                    flowOutgoings = (ArrayNode)flowNode.get("outgoing");
                    nextNodeResourceId = flowOutgoings.get(0).get("resourceId").asText();
                    if (resourceId.equals(nextNodeResourceId)) continue;
                    this.pgwInfoGather(jsonNodes, nextNodeResourceId, nodeDependenceMap, pgwDependenceMap, currPgwInfoMap);
                }
            }
        }
    }

    public R currNodeProperties(WorkflowDTO workflowDTO) {
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        R result = new R();
        result.putAll(this.vaWorkflowNodeConfigService.getNodeProperties(workflowDTO, false));
        return result;
    }

    private List<WorkflowProcessInfo> getForwardProcessNode(List<WorkflowProcessInfo> oldInfos, Map<String, String> rejectSkipNodeMap, List<ProcessNodeDO> unApprovalNodes, Map<String, Map<String, String>> nodeDependenceMap, ProcessDO processDO, ProcessHistoryDO processHistoryDO) {
        List<WorkflowProcessInfo> infos = this.getWorkflowProcessInfo(oldInfos, rejectSkipNodeMap);
        if (CollectionUtils.isEmpty(unApprovalNodes) && "\u6d41\u7a0b\u7ed3\u675f".equals(infos.get(infos.size() - 1).getNodeCode())) {
            return infos;
        }
        if (CollectionUtils.isEmpty(unApprovalNodes)) {
            this.setRejectStateForNotReSubmitted(rejectSkipNodeMap, processDO, processHistoryDO, infos);
        } else if (this.isMainProcessNode(unApprovalNodes)) {
            this.setRejectStateForMainProcess(rejectSkipNodeMap, unApprovalNodes, infos);
        } else {
            this.setRejectStateForPgwProcess(rejectSkipNodeMap, unApprovalNodes, nodeDependenceMap, infos);
        }
        return infos;
    }

    private void setRejectStateForNotReSubmitted(Map<String, String> rejectSkipNodeMap, ProcessDO processDO, ProcessHistoryDO processHistoryDO, List<WorkflowProcessInfo> infos) {
        if (!CollectionUtils.isEmpty(rejectSkipNodeMap)) {
            this.setRejectState(rejectSkipNodeMap, infos);
        } else if (this.processFinishedWithReject(processDO, processHistoryDO)) {
            infos.forEach(info -> info.setRejectState(true));
        }
    }

    private void setRejectStateForMainProcess(Map<String, String> rejectSkipNodeMap, List<ProcessNodeDO> unApprovalNodes, List<WorkflowProcessInfo> infos) {
        block7: {
            String unApprovalNodeCode;
            HashMap<String, Integer> nodeCodeIndexMap;
            block6: {
                nodeCodeIndexMap = new HashMap<String, Integer>();
                for (int i = 0; i < infos.size(); ++i) {
                    nodeCodeIndexMap.put(infos.get(i).getNodeCode(), i);
                }
                unApprovalNodeCode = unApprovalNodes.get(0).getNodecode();
                if (!CollectionUtils.isEmpty(rejectSkipNodeMap)) break block6;
                List approvedNodeCodeList = infos.stream().map(WorkflowProcessInfo::getNodeCode).collect(Collectors.toList());
                if (!approvedNodeCodeList.contains(unApprovalNodeCode)) break block7;
                Integer startIndex = (Integer)nodeCodeIndexMap.get(unApprovalNodeCode);
                for (int i = startIndex.intValue(); i < infos.size(); ++i) {
                    infos.get(i).setRejectState(true);
                }
                break block7;
            }
            if (rejectSkipNodeMap.containsKey(unApprovalNodeCode)) {
                this.setRejectState(rejectSkipNodeMap, infos);
            } else if (rejectSkipNodeMap.containsValue(unApprovalNodeCode)) {
                this.setRejectState(rejectSkipNodeMap, infos);
                for (Map.Entry<String, String> entry : rejectSkipNodeMap.entrySet()) {
                    if (!entry.getValue().equals(unApprovalNodeCode)) continue;
                    infos.get((Integer)nodeCodeIndexMap.get(entry.getKey())).setRejectState(false);
                }
            }
        }
    }

    private void setRejectState(Map<String, String> rejectSkipNodeMap, List<WorkflowProcessInfo> infos) {
        HashSet<String> nodeCodeSet = new HashSet<String>();
        for (Map.Entry<String, String> entry : rejectSkipNodeMap.entrySet()) {
            nodeCodeSet.add(entry.getKey());
            nodeCodeSet.add(entry.getValue());
        }
        for (WorkflowProcessInfo info : infos) {
            if (!nodeCodeSet.contains(info.getNodeCode())) continue;
            info.setRejectState(true);
        }
    }

    private boolean isMainProcessNode(List<ProcessNodeDO> unApprovalNodes) {
        Set unApprovalNodeCodes = unApprovalNodes.stream().map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
        return unApprovalNodeCodes.size() == 1 && !StringUtils.hasText(unApprovalNodes.get(0).getPgwnodeid());
    }

    private void setRejectStateForPgwProcess(Map<String, String> rejectSkipNodeMap, List<ProcessNodeDO> unApprovalNodes, Map<String, Map<String, String>> nodeDependenceMap, List<WorkflowProcessInfo> infos) {
        String pgwNodeId = unApprovalNodes.get(0).getPgwnodeid();
        String pgwNodeCode = infos.stream().filter(info -> info.getNodeId().equals(pgwNodeId)).findFirst().map(WorkflowProcessInfo::getNodeCode).orElse("");
        List approvedNodeCodeList = infos.stream().map(WorkflowProcessInfo::getNodeCode).collect(Collectors.toList());
        Map<String, List<WorkflowProcessInfo>> pgwBranchMap = this.getPgwBranchMap(infos, pgwNodeCode, pgwNodeId);
        this.removeNodeOfAfterPgw(infos, pgwNodeCode);
        Set<String> unApprovalBranchSet = this.getUnApprovalBranchSet(unApprovalNodes, nodeDependenceMap);
        for (Map.Entry<String, List<WorkflowProcessInfo>> entry : pgwBranchMap.entrySet()) {
            String key = entry.getKey();
            List<WorkflowProcessInfo> branchInfoList = entry.getValue();
            if (!unApprovalBranchSet.contains(key)) {
                infos.addAll(branchInfoList);
                continue;
            }
            HashSet<String> nodeCodeCache = new HashSet<String>();
            for (ProcessNodeDO unApprovalNode : unApprovalNodes) {
                boolean currentBranchHasRejectSkipRecord;
                String unApprovalNodeCode = unApprovalNode.getNodecode();
                String pgwBranch = nodeDependenceMap.get(unApprovalNodeCode).get("pgwBranch");
                if (nodeCodeCache.contains(unApprovalNodeCode) || !key.equals(pgwBranch)) continue;
                nodeCodeCache.add(unApprovalNodeCode);
                boolean bl = currentBranchHasRejectSkipRecord = rejectSkipNodeMap.containsKey(unApprovalNodeCode) || rejectSkipNodeMap.containsValue(unApprovalNodeCode);
                if (currentBranchHasRejectSkipRecord) {
                    this.setRejectStateForPgwRejectSkip(rejectSkipNodeMap, unApprovalNodeCode, branchInfoList, infos);
                    continue;
                }
                if (approvedNodeCodeList.contains(unApprovalNodeCode)) {
                    this.setRejectStateForPgwRejectNotSkip(branchInfoList, unApprovalNodeCode, infos);
                    continue;
                }
                infos.addAll(branchInfoList);
            }
        }
    }

    private void removeNodeOfAfterPgw(List<WorkflowProcessInfo> infos, String pgwNodeCode) {
        Iterator<WorkflowProcessInfo> iterator = infos.iterator();
        boolean removeFlag = false;
        while (iterator.hasNext()) {
            WorkflowProcessInfo next = iterator.next();
            if (removeFlag) {
                iterator.remove();
            }
            if (!pgwNodeCode.equals(next.getNodeCode())) continue;
            removeFlag = true;
        }
    }

    private void setRejectStateForPgwRejectNotSkip(List<WorkflowProcessInfo> infoList, String unApprovalNodeCode, List<WorkflowProcessInfo> pgwInfos) {
        boolean startFlag = false;
        for (WorkflowProcessInfo workflowProcessInfo : infoList) {
            if (Objects.equals(workflowProcessInfo.getNodeCode(), unApprovalNodeCode)) {
                startFlag = true;
            }
            if (startFlag) {
                workflowProcessInfo.setRejectState(true);
            }
            pgwInfos.add(workflowProcessInfo);
        }
    }

    private void setRejectStateForPgwRejectSkip(Map<String, String> rejectSkipNodeMap, String unApprovalNodeCode, List<WorkflowProcessInfo> infoList, List<WorkflowProcessInfo> pgwInfos) {
        boolean endFlag = false;
        for (WorkflowProcessInfo workflowProcessInfo : infoList) {
            if (rejectSkipNodeMap.containsKey(workflowProcessInfo.getNodeCode()) && workflowProcessInfo.getNodeCode().equals(unApprovalNodeCode)) {
                workflowProcessInfo.setRejectState(true);
            } else if (rejectSkipNodeMap.containsValue(workflowProcessInfo.getNodeCode())) {
                workflowProcessInfo.setRejectState(true);
                endFlag = true;
            }
            pgwInfos.add(workflowProcessInfo);
            if (!endFlag) continue;
            break;
        }
    }

    private Set<String> getUnApprovalBranchSet(List<ProcessNodeDO> unApprovalNodes, Map<String, Map<String, String>> nodeDependenceMap) {
        Set unApprovalNodeCodeSet = unApprovalNodes.stream().map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
        HashSet<String> unApprovalBranchSet = new HashSet<String>();
        for (String nodeCode : unApprovalNodeCodeSet) {
            unApprovalBranchSet.add(nodeDependenceMap.get(nodeCode).get("pgwBranch"));
        }
        return unApprovalBranchSet;
    }

    private Map<String, List<WorkflowProcessInfo>> getPgwBranchMap(List<WorkflowProcessInfo> infos, String pgwNodeCode, String pgwNodeId) {
        HashMap<String, List<WorkflowProcessInfo>> subMap = new HashMap<String, List<WorkflowProcessInfo>>();
        List subInfos = infos.stream().filter(info -> pgwNodeCode.equals(info.getPgwNodeCode()) && !pgwNodeId.equals(info.getNodeId())).collect(Collectors.toList());
        for (WorkflowProcessInfo subInfo : subInfos) {
            if (subMap.containsKey(subInfo.getPgwBranch())) {
                ((List)subMap.get(subInfo.getPgwBranch())).add(subInfo);
                continue;
            }
            ArrayList<WorkflowProcessInfo> list = new ArrayList<WorkflowProcessInfo>();
            list.add(subInfo);
            subMap.put(subInfo.getPgwBranch(), list);
        }
        return subMap;
    }

    private boolean processFinishedWithReject(ProcessDO processDO, ProcessHistoryDO processHistoryDO) {
        return ObjectUtils.isEmpty(processDO) && !ObjectUtils.isEmpty(processHistoryDO) && WorkflowProcessStatus.PROCESS_FINSHED_REJECT.getValue() == processHistoryDO.getEndstatus().intValue();
    }

    private List<WorkflowProcessInfo> getWorkflowProcessInfo(List<WorkflowProcessInfo> infos, Map<String, String> rejectSkipNodeMap) {
        WorkflowProcessInfo firstNode;
        int size = infos.size();
        HashMap<String, WorkflowProcessInfo> cache = new HashMap<String, WorkflowProcessInfo>(size);
        WorkflowProcessInfo newPrevNode = firstNode = new WorkflowProcessInfo();
        Integer rejectSkipFlag = null;
        for (int i = 0; i < size; ++i) {
            WorkflowProcessInfo newNode = infos.get(i);
            String newNodeCode = newNode.getNodeCode();
            if (!cache.containsKey(newNodeCode)) {
                cache.put(newNodeCode, newPrevNode);
                newPrevNode.setNext(newNode);
                if (rejectSkipFlag != null) {
                    rejectSkipFlag = null;
                }
                newPrevNode = newNode;
                continue;
            }
            WorkflowProcessInfo oldPrevNode = (WorkflowProcessInfo)cache.get(newNodeCode);
            WorkflowProcessInfo oldNode = oldPrevNode.getNext();
            WorkflowProcessInfo oldNextNode = oldNode.getNext();
            if (rejectSkipFlag == null) {
                rejectSkipFlag = this.getRejectSkipFlag(infos, i, rejectSkipNodeMap);
            }
            if (rejectSkipFlag == 0) {
                oldPrevNode.setNext(newNode);
                cache.put(newNodeCode, oldPrevNode);
                this.clearCacheForNextNodes(cache, oldNextNode);
            } else {
                newNode.setNext(oldNextNode);
                oldPrevNode.setNext(newNode);
                if (oldNextNode != null) {
                    cache.put(oldNextNode.getNodeCode(), newNode);
                }
            }
            if (this.newNodeIsRejectNode(newNode)) {
                rejectSkipFlag = null;
            }
            newPrevNode = newNode;
        }
        ArrayList<WorkflowProcessInfo> newInfos = new ArrayList<WorkflowProcessInfo>();
        while (firstNode.getNext() != null) {
            WorkflowProcessInfo next = firstNode.getNext();
            newInfos.add(next);
            firstNode = next;
        }
        return newInfos;
    }

    private int getRejectSkipFlag(List<WorkflowProcessInfo> infos, int i, Map<String, String> rejectSkipNodeMap) {
        WorkflowProcessInfo newPrevNode = infos.get(i - 1);
        ArrayList allProcessNodeDOList = new ArrayList();
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newPrevNode.getApprovaInfo()));
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newPrevNode.getPlusApprovaInfo()));
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newPrevNode.getDelegateInfo()));
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newPrevNode.getTodoTransferInfo()));
        for (ProcessNodeDO processNodeDO : allProcessNodeDOList) {
            if (processNodeDO.getRejectskipflag() == null) continue;
            return processNodeDO.getRejectskipflag().intValue();
        }
        String newPrevNodeCode = newPrevNode.getNodeCode();
        if (i == infos.size() - 1) {
            return rejectSkipNodeMap.containsValue(newPrevNodeCode) ? 1 : 0;
        }
        WorkflowProcessInfo newNextNode = infos.get(i + 1);
        return Objects.equals(newPrevNodeCode, newNextNode.getNodeCode()) ? 1 : 0;
    }

    private void clearCacheForNextNodes(Map<String, WorkflowProcessInfo> cache, WorkflowProcessInfo oldNextNode) {
        while (oldNextNode != null) {
            cache.remove(oldNextNode.getNodeCode());
            oldNextNode = oldNextNode.getNext();
        }
    }

    private boolean newNodeIsRejectNode(WorkflowProcessInfo newNode) {
        ArrayList allProcessNodeDOList = new ArrayList();
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newNode.getApprovaInfo()));
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newNode.getPlusApprovaInfo()));
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newNode.getDelegateInfo()));
        allProcessNodeDOList.addAll(VaWorkflowUtils.getList(newNode.getTodoTransferInfo()));
        for (ProcessNodeDO processNodeDO : allProcessNodeDOList) {
            if (!"\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) continue;
            return true;
        }
        return false;
    }

    private void convertParallelGateway(List<WorkflowProcessInfo> forwardNodes, List<ProcessNodeDO> processNodeDOs, Map<String, Map<String, String>> nodeDependenceMap) {
        List subNodes = processNodeDOs.stream().filter(processNode -> StringUtils.hasText(processNode.getPgwnodeid()) && !processNode.getNodeid().equals(processNode.getPgwnodeid())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(subNodes)) {
            return;
        }
        Set<String> subNodeIds = subNodes.stream().map(ProcessNodeDO::getNodeid).collect(Collectors.toSet());
        HashSet<String> pgwNodeCodeSet = new HashSet<String>();
        HashMap<String, Map<String, List<WorkflowProcessInfo>>> pGWMap = new HashMap<String, Map<String, List<WorkflowProcessInfo>>>();
        for (ProcessNodeDO subNode : subNodes) {
            List<WorkflowProcessInfo> nodes;
            int index = IntStream.range(0, forwardNodes.size()).filter(i -> ((WorkflowProcessInfo)forwardNodes.get(i)).getNodeId().equals(subNode.getNodeid())).findFirst().orElse(-1);
            if (index == -1) continue;
            WorkflowProcessInfo subInfo = forwardNodes.get(index);
            String pgwNodeCode = nodeDependenceMap.get(subInfo.getNodeCode()).get("pgwNodeCode");
            String pgwBranch = nodeDependenceMap.get(subInfo.getNodeCode()).get("pgwBranch");
            pgwNodeCodeSet.add(pgwNodeCode);
            if (pGWMap.containsKey(pgwNodeCode)) {
                Map branchMap = (Map)pGWMap.get(pgwNodeCode);
                if (branchMap.containsKey(pgwBranch)) {
                    nodes = (List)branchMap.get(pgwBranch);
                    nodes.add(subInfo);
                    continue;
                }
                nodes = new ArrayList();
                nodes.add(subInfo);
                branchMap.put(pgwBranch, nodes);
                continue;
            }
            HashMap newBranchMap = new HashMap();
            nodes = new ArrayList<WorkflowProcessInfo>();
            nodes.add(subInfo);
            newBranchMap.put(pgwBranch, nodes);
            pGWMap.put(pgwNodeCode, newBranchMap);
        }
        List<WorkflowProcessInfo> tempList = this.getRemoveNodeList(forwardNodes, pgwNodeCodeSet, pGWMap, subNodeIds);
        forwardNodes.removeAll(tempList);
    }

    private List<WorkflowProcessInfo> getRemoveNodeList(List<WorkflowProcessInfo> forwardNodes, Set<String> pgwNodeCodeSet, Map<String, Map<String, List<WorkflowProcessInfo>>> pGWMap, Set<String> subNodeIds) {
        ArrayList<WorkflowProcessInfo> tempList = new ArrayList<WorkflowProcessInfo>();
        for (WorkflowProcessInfo info : forwardNodes) {
            if (pgwNodeCodeSet.contains(info.getNodeCode()) && pGWMap.containsKey(info.getNodeCode())) {
                Map<String, List<WorkflowProcessInfo>> branchMap = pGWMap.get(info.getNodeCode());
                ArrayList<List<WorkflowProcessInfo>> pGWNodes = new ArrayList<List<WorkflowProcessInfo>>();
                for (Map.Entry<String, List<WorkflowProcessInfo>> entry : branchMap.entrySet()) {
                    pGWNodes.add(entry.getValue());
                    info.setGatewayInfo(pGWNodes);
                }
            }
            if (!subNodeIds.contains(info.getNodeId())) continue;
            tempList.add(info);
        }
        return tempList;
    }

    private void addNodeUser(ProcessNodeDO processNode, List<UserDO> nodeUsers, NodeView currNode, NodeView targetNode, boolean isChildNode) {
        String delegateUser = processNode.getDelegateuser();
        if (!StringUtils.hasText(delegateUser)) {
            ArrayList<UserDO> newNodeUsers = new ArrayList<UserDO>();
            UserDO currentUserDO = currNode.getMyNodeUser();
            ArrayList<UserDO> agentUsers = new ArrayList<UserDO>();
            for (UserDO nodeUserDO : nodeUsers) {
                if (nodeUserDO.getExtInfo("delegateUser") != null && currentUserDO.getId().equals(nodeUserDO.getExtInfo("delegateUser"))) {
                    agentUsers.add(nodeUserDO);
                    continue;
                }
                newNodeUsers.add(nodeUserDO);
            }
            if (agentUsers.size() > 0) {
                currentUserDO.addExtInfo("agentUsers", agentUsers);
            }
            newNodeUsers.add(currentUserDO);
            targetNode.setNodeUser(newNodeUsers);
        } else {
            boolean temp = false;
            for (UserDO userDO : nodeUsers) {
                ArrayList agentUsers;
                String userId = userDO.getId();
                if (!userId.equals(delegateUser)) continue;
                Object object = userDO.getExtInfo("agentUsers");
                if (object != null) {
                    agentUsers = (ArrayList)object;
                    agentUsers.addAll(currNode.getNodeUser());
                } else {
                    agentUsers = new ArrayList();
                    agentUsers.addAll(currNode.getNodeUser());
                    userDO.addExtInfo("agentUsers", agentUsers);
                }
                temp = true;
            }
            if (!temp) {
                if (isChildNode) {
                    targetNode.getNodeViews().add(currNode);
                }
                targetNode.getNodeUser().addAll(currNode.getNodeUser());
            }
        }
    }

    private NodeView getChildNode(String name, List<NodeView> nodes) {
        for (NodeView node : nodes) {
            if (!name.equals(node.getNodeName())) continue;
            return node;
        }
        return null;
    }

    private UserDO generatePlusApprovaledUser(ProcessNodeDO processNode, Map<String, UserDO> userMap) {
        UserDO user = this.getNodeUserInfo(processNode, userMap);
        return user;
    }

    private NodeView generateNode(ProcessNodeDO processNode, Map<String, UserDO> userMap, Map<String, List<String>> tempMap) {
        NodeView nodeView = new NodeView();
        nodeView.setNodeName(processNode.getProcessnodename());
        nodeView.setNodeId(processNode.getNodeid());
        nodeView.setNodeCode(processNode.getNodecode());
        nodeView.setNodeType(Integer.valueOf(processNode.getCountersignflag().intValue()));
        nodeView.setExpend(false);
        nodeView.setApprovalResult(processNode.getCompleteresult());
        nodeView.setBizCode(processNode.getBizcode());
        nodeView.setBizDefine(processNode.getBizdefine());
        nodeView.setSyscode(processNode.getSyscode());
        if (processNode.getExtInfo("plusApprovaledExplain") != null) {
            nodeView.setPlusApprovaledExplain(processNode.getExtInfo("plusApprovaledExplain").toString());
        }
        nodeView.setApprovalComment(processNode.getCompletecomment());
        if (processNode.getCommentcolor() != null) {
            nodeView.setCommentColor(Integer.valueOf(processNode.getCommentcolor().intValue()));
        }
        if (processNode.getCompletetime() != null) {
            nodeView.setNodeTime(processNode.getCompletetime());
            nodeView.setNodeHoldingTime(Long.valueOf(processNode.getCompletetime().getTime() - processNode.getReceivetime().getTime()));
        }
        UserDO nodeUserDO = this.getNodeUserInfo(processNode, userMap);
        ArrayList<UserDO> users = new ArrayList<UserDO>();
        users.add(nodeUserDO);
        nodeView.setNodeUser(users);
        nodeView.setMyNodeUser(nodeUserDO);
        nodeView.setPGWNodeId(processNode.getPgwnodeid());
        nodeView.setPGWBranch(processNode.getPgwbranch());
        nodeView.setSubProcessNodeId(processNode.getSubprocessnodeid());
        nodeView.setSubProcessBranch(processNode.getSubprocessbranch());
        nodeView.setNodeGroup(processNode.getNodegroup());
        nodeView.setForecastFlag(processNode.getExtInfo("forecastFlag") != null);
        this.dealTransferTodoInfo(nodeView, processNode, tempMap);
        if (processNode.getExtInfo("requestParam") != null) {
            nodeView.setRequestParam((Map)processNode.getExtInfo("requestParam"));
        }
        return nodeView;
    }

    private void dealTransferTodoInfo(NodeView nodeView, ProcessNodeDO processNode, Map<String, List<String>> tempMap) {
        try {
            VaTodoTransferDTO vaTodoTransferDTO = new VaTodoTransferDTO();
            vaTodoTransferDTO.setNodecode(processNode.getNodecode());
            vaTodoTransferDTO.setProcessid(processNode.getProcessid());
            vaTodoTransferDTO.setBizCode(processNode.getBizcode());
            vaTodoTransferDTO.setNodeid(processNode.getNodeid());
            vaTodoTransferDTO.setCompleteuserid(processNode.getCompleteuserid());
            R r = this.todoClient.listTransferInfo(vaTodoTransferDTO);
            List<VaTodoTransferVO> vaTodoTransferVOList = null;
            if (r.getCode() == 0) {
                vaTodoTransferVOList = VaWorkflowUtils.getList(r.get((Object)"data"), VaTodoTransferVO.class);
            }
            if (CollectionUtils.isEmpty(vaTodoTransferVOList)) {
                return;
            }
            List nodeUser = nodeView.getNodeUser();
            for (UserDO userDO : nodeUser) {
                if (processNode.getCompleteuserid() == null || !processNode.getCompleteuserid().equals(userDO.getId())) continue;
                String id = userDO.getId();
                List<String> list = tempMap.get(processNode.getNodecode());
                if (list.contains(id)) continue;
                userDO.getExtInfo().put("completeUserType", VaWorkflowConstants.TRANSFER_FLAG);
            }
        }
        catch (Exception e) {
            log.error("Error occur while deal TodoTransferInfo:", e);
        }
    }

    private UserDO getNodeUserInfo(ProcessNodeDO processNode, Map<String, UserDO> userMap) {
        UserDTO userDTO;
        UserDO userDO = null;
        if (processNode.getCompleteuserid() != null) {
            userDO = userMap.get(processNode.getCompleteuserid());
            if (userDO == null) {
                userDTO = new UserDTO();
                userDTO.setId(processNode.getCompleteuserid());
                userDO = this.authUserClient.get(userDTO);
                if (userDO != null) {
                    userMap.put(userDO.getId().toString(), userDO);
                }
            }
            if (userDO == null && processNode.getCompleteusername() != null) {
                userDO = new UserDO();
                userDO.setName(processNode.getCompleteusername());
            }
        } else if (processNode.getCompleteusercode() != null) {
            userDTO = new UserDTO();
            userDTO.setUsername(processNode.getCompleteusercode());
            userDO = this.authUserClient.get(userDTO);
            if (userDO == null && processNode.getCompleteusername() != null) {
                userDO = new UserDO();
                userDO.setName(processNode.getCompleteusername());
            }
        } else if (processNode.getCompleteusername() != null) {
            userDO = new UserDO();
            userDO.setName(processNode.getCompleteusername());
        }
        UserDO nodeUserDO = new UserDO();
        if (userDO != null) {
            nodeUserDO.setId(userDO.getId());
            nodeUserDO.setUsername(userDO.getUsername());
            nodeUserDO.setName(userDO.getName());
            nodeUserDO.setUnitcode(userDO.getUnitcode());
            nodeUserDO.setTelephone(userDO.getTelephone());
            String name = null;
            OptionItemDTO optionItemDTO = new OptionItemDTO();
            optionItemDTO.setName("WF1008");
            List itemVOS = this.workflowOptionService.list(optionItemDTO);
            if (!"0".equals(((OptionItemVO)itemVOS.get(0)).getVal())) {
                String unitcode = processNode.getCompleteunitcode();
                if (!StringUtils.hasText(unitcode)) {
                    unitcode = processNode.getCompleteuserunitcode();
                }
                if (!StringUtils.hasText(unitcode)) {
                    unitcode = userDO.getUnitcode();
                }
                if (StringUtils.hasText(unitcode)) {
                    OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
                    OrgDTO orgDTO = new OrgDTO();
                    orgDTO.setCode(unitcode);
                    orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
                    PageVO list = orgDataClient.list(orgDTO);
                    if (list.getRows() != null && list.getRows().size() > 0) {
                        name = ((OrgDO)list.getRows().get(0)).getName();
                    }
                }
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("value", unitcode);
                nodeUserDO.addExtInfo("unitcode", map);
            }
            if (processNode.getCompleteresult() != null) {
                if (processNode.getCountersignflag().intValue() == 1) {
                    nodeUserDO.addExtInfo("approvalResult", (Object)processNode.getCompleteresult());
                }
                if (new BigDecimal(1).equals(processNode.getApprovalflag())) {
                    nodeUserDO.addExtInfo("approvalComment", (Object)processNode.getCompletecomment());
                    nodeUserDO.addExtInfo("approvalResult", (Object)processNode.getCompleteresult());
                    nodeUserDO.addExtInfo("holdingTime", (Object)(processNode.getCompletetime().getTime() - processNode.getReceivetime().getTime()));
                }
            } else {
                nodeUserDO.setWechat(userDO.getWechat());
            }
            nodeUserDO.addExtInfo("completeUserType", (Object)processNode.getCompleteusertype());
        }
        nodeUserDO.addExtInfo("completetime", (Object)processNode.getCompletetime());
        nodeUserDO.addExtInfo("delegateUser", (Object)processNode.getDelegateuser());
        if (processNode.getExtInfo("plusApprovaledExplain") != null) {
            nodeUserDO.addExtInfo("plusApprovaledExplain", (Object)processNode.getExtInfo("plusApprovaledExplain").toString());
        }
        return nodeUserDO;
    }

    public List<ProcessNodeDO> queryNodeLastOpt(ProcessNodeDTO processNodeDTO) {
        if (processNodeDTO.getBizcode() == null) {
            return new ArrayList<ProcessNodeDO>();
        }
        return this.workflowProcessNodeDao.queryNodeLastOpt(processNodeDTO);
    }

    public R listRejectDesignateNodeInfo(WorkflowDTO workflowDTO) {
        String bizCode = workflowDTO.getBizCode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            processDTO.setBizcode(workflowDTO.getBizCode());
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.norecorder"));
            }
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
            processDO = new ProcessDO();
            processDO.setDefinekey(processHistoryDO.getDefinekey());
            processDO.setDefineversion(processHistoryDO.getDefineversion());
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(bizCode);
        processNodeDTO.setSort("ordernum");
        processNodeDTO.setSyscode("WORKFLOW");
        processNodeDTO.setSearchIgnore(true);
        List<ProcessNodeDO> processNodeDOList = this.listProcessNode(processNodeDTO);
        return this.workflowRejectDesignateNodeService.list(workflowDTO, processDO, processNodeDOList);
    }

    public Map<String, Map<String, Object>> getPreNodeCommAndCurrNode(List<ProcessNodeDTO> processNodeDTOs, boolean prevNodeCommentFlag, boolean currNodeFlag) {
        ConcurrentHashMap<String, Map<String, Object>> resultMap = new ConcurrentHashMap<String, Map<String, Object>>(processNodeDTOs.size());
        int processNodeSize = processNodeDTOs.size();
        List processIdList = processNodeDTOs.stream().map(ProcessNodeDO::getProcessid).collect(Collectors.toList());
        if (processIdList.isEmpty()) {
            return resultMap;
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessIdList(processIdList);
        List<ProcessNodeDO> allProcessNodeList = this.workflowProcessNodeDao.listProcessNodeByProcessIdList(processNodeDTO);
        if (allProcessNodeList.isEmpty()) {
            return resultMap;
        }
        boolean i18nFlag = Boolean.TRUE.equals(VaI18nParamUtil.getTranslationEnabled());
        HashMap processIdNodeListMap = new HashMap();
        for (ProcessNodeDO processNodeDO : allProcessNodeList) {
            String processid = processNodeDO.getProcessid();
            if (processIdNodeListMap.containsKey(processid)) {
                ((List)processIdNodeListMap.get(processid)).add(processNodeDO);
                continue;
            }
            ArrayList<ProcessNodeDO> processNodeDOList = new ArrayList<ProcessNodeDO>();
            processNodeDOList.add(processNodeDO);
            processIdNodeListMap.put(processid, processNodeDOList);
        }
        ConcurrentHashMap userMap = new ConcurrentHashMap();
        ConcurrentHashMap<String, String> i18nKeyMap = new ConcurrentHashMap<String, String>();
        ThreadPoolExecutor executor = VaWorkflowThreadUtils.getThreadPoolExecutor();
        CountDownLatch latch = new CountDownLatch(processNodeSize);
        for (ProcessNodeDTO processNode : processNodeDTOs) {
            executor.submit(() -> {
                try {
                    HashMap<String, String> processNodeMap = new HashMap<String, String>(8);
                    String todoTaskId = (String)processNode.getExtInfo("todoTaskId");
                    if (!StringUtils.hasText(todoTaskId)) {
                        return;
                    }
                    resultMap.put(todoTaskId, processNodeMap);
                    String nodeCode = processNode.getNodecode();
                    String taskId = processNode.getNodeid();
                    String processid = processNode.getProcessid();
                    List processNodeList = (List)processIdNodeListMap.get(processid);
                    if (CollectionUtils.isEmpty(processNodeList)) {
                        return;
                    }
                    if (currNodeFlag) {
                        String currNodeName = "";
                        ProcessNodeDO nodeDO = processNodeList.stream().filter(node -> Objects.equals(node.getNodecode(), nodeCode) && Objects.equals(node.getNodeid(), taskId)).findFirst().orElse(null);
                        if (nodeDO != null) {
                            currNodeName = nodeDO.getProcessnodename();
                        }
                        processNodeMap.put("currNode", currNodeName);
                        if (i18nFlag) {
                            String processDefineKey = (String)processNode.getExtInfo("PROCESSDEFINEKEY");
                            String processVersion = String.valueOf(processNode.getExtInfo("PROCESSDEFINEVERSION"));
                            i18nKeyMap.put(todoTaskId, "VA#workflow#" + processDefineKey + "&define#" + processVersion + "&workflowversion#processDesignPlugin&plugin#" + nodeCode);
                        }
                    }
                    if (prevNodeCommentFlag) {
                        List<ProcessNodeDO> previousNodes = this.getPreviousNodes(nodeCode, taskId, processNodeList);
                        if (previousNodes.isEmpty()) {
                            return;
                        }
                        ProcessNodeDO resultNode = previousNodes.size() > 1 ? (ProcessNodeDO)previousNodes.stream().filter(node -> node.getCompletetime() != null).max(Comparator.comparing(ProcessNodeDO::getCompletetime)).orElse(null) : previousNodes.get(0);
                        if (resultNode != null) {
                            String completeuserid = resultNode.getCompleteuserid();
                            String username = resultNode.getCompleteusername();
                            if (StringUtils.hasText(completeuserid)) {
                                if (userMap.containsKey(completeuserid)) {
                                    username = (String)userMap.get(completeuserid);
                                } else {
                                    UserDO userDO = VaWorkFlowDataUtils.getOneUserData(null, completeuserid);
                                    if (userDO != null) {
                                        username = userDO.getName();
                                        userMap.put(completeuserid, username);
                                    }
                                }
                            }
                            processNodeMap.put("username", username);
                            processNodeMap.put("completeComment", resultNode.getCompletecomment());
                            processNodeMap.put("completeResult", resultNode.getCompleteresult());
                        }
                    }
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        }
        catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        if (currNodeFlag && i18nFlag) {
            this.handleProcessNodeI18n(i18nKeyMap, resultMap);
        }
        return resultMap;
    }

    private void handleProcessNodeI18n(Map<String, String> i18nKeyMap, Map<String, Map<String, Object>> resultMap) {
        ArrayList<String> i18nKeys = new ArrayList<String>();
        ArrayList<String> todoIds = new ArrayList<String>();
        HashMap i18nValueMap = new HashMap();
        for (Map.Entry<String, String> entry : i18nKeyMap.entrySet()) {
            i18nKeys.add(entry.getValue());
            todoIds.add(entry.getKey());
        }
        if (i18nKeys.isEmpty()) {
            return;
        }
        VaI18nResourceDTO i18nResourceDTO = new VaI18nResourceDTO();
        i18nResourceDTO.setKey(i18nKeys);
        List i18nValues = this.vaI18nClient.queryList(i18nResourceDTO);
        if (CollectionUtils.isEmpty(i18nValues)) {
            return;
        }
        for (int i = 0; i < i18nKeys.size(); ++i) {
            i18nValueMap.put(todoIds.get(i), i18nValues.get(i));
        }
        for (Map.Entry<String, Map<String, Object>> entry : resultMap.entrySet()) {
            String value;
            String key = entry.getKey();
            Map<String, Object> nodeMap = entry.getValue();
            if (!nodeMap.containsKey("currNode") || !StringUtils.hasText(value = (String)i18nValueMap.get(key))) continue;
            nodeMap.put("currNode", value);
        }
    }

    private List<ProcessNodeDO> getPreviousNodes(String nodeCode, String taskId, List<ProcessNodeDO> processNodeList) {
        ProcessNodeDO previousNode;
        List<ProcessNodeDO> previousNodes = new ArrayList<ProcessNodeDO>();
        ProcessNodeDO processNodeDO = processNodeList.stream().filter(p -> Objects.equals(p.getNodecode(), nodeCode) && Objects.equals(p.getNodeid(), taskId)).findFirst().orElse(null);
        if (processNodeDO == null) {
            return previousNodes;
        }
        List<Object> previousAllNodeList = new ArrayList<ProcessNodeDO>();
        BigDecimal currOrdernum = processNodeDO.getOrdernum();
        for (int i = processNodeList.size() - 1; i >= 0; --i) {
            ProcessNodeDO nodeDO = processNodeList.get(i);
            BigDecimal ordernum = nodeDO.getOrdernum();
            if (Objects.equals(nodeDO.getNodecode(), nodeCode) || BigDecimal.ONE.equals(nodeDO.getHiddenflag()) || ordernum.compareTo(currOrdernum) >= 0) continue;
            previousAllNodeList.add(nodeDO);
        }
        if (previousAllNodeList.size() < 2) {
            return previousNodes;
        }
        String subProcessBranch = processNodeDO.getSubprocessbranch();
        if (StringUtils.hasText(subProcessBranch)) {
            previousAllNodeList = previousAllNodeList.stream().filter(node -> Objects.equals(subProcessBranch, node.getSubprocessbranch())).collect(Collectors.toList());
        }
        if ((previousNode = this.getPreviousNode(processNodeDO, previousAllNodeList)) == null || "\u63d0\u4ea4".equals(previousNode.getCompleteresult())) {
            return previousNodes;
        }
        previousNodes = this.getPreviousNodes(previousAllNodeList, previousNode);
        return previousNodes;
    }

    private List<ProcessNodeDO> getPreviousNodes(List<ProcessNodeDO> previousNodeList, ProcessNodeDO previousNode) {
        ArrayList<ProcessNodeDO> previousNodes = new ArrayList<ProcessNodeDO>();
        String previousNodeCode = previousNode.getNodecode();
        String previousNodeId = previousNode.getNodeid();
        String pgwnodeid = previousNode.getPgwnodeid();
        if (StringUtils.hasText(pgwnodeid)) {
            for (ProcessNodeDO nodeDO : previousNodeList) {
                if (Objects.equals(nodeDO.getNodecode(), previousNodeCode)) {
                    previousNodes.add(nodeDO);
                    continue;
                }
                if (Objects.equals(nodeDO.getPgwnodeid(), pgwnodeid)) {
                    continue;
                }
                break;
            }
        } else {
            boolean flag = false;
            for (ProcessNodeDO nodeDO : previousNodeList) {
                if (previousNodeCode == null) {
                    if (!Objects.equals(nodeDO.getNodeid(), previousNodeId)) continue;
                    previousNodes.add(nodeDO);
                    continue;
                }
                if (Objects.equals(nodeDO.getNodecode(), previousNodeCode)) {
                    previousNodes.add(nodeDO);
                    flag = true;
                    continue;
                }
                if (!flag) continue;
                break;
            }
        }
        return previousNodes;
    }

    private ProcessNodeDO getPreviousNode(ProcessNodeDO processNodeDO, List<ProcessNodeDO> previousNodeList) {
        String pgwBranch = processNodeDO.getPgwbranch();
        ProcessNodeDO previousNode = StringUtils.hasText(pgwBranch) ? this.handlePwgBranch(previousNodeList, pgwBranch) : (ProcessNodeDO)previousNodeList.stream().filter(node -> !Objects.equals(node.getNodeid(), node.getPgwnodeid()) && !Objects.equals(node.getNodeid(), node.getSubprocessnodeid()) && !BigDecimal.ONE.equals(node.getHiddenflag()) && node.getCompletetime() != null).max(Comparator.comparing(ProcessNodeDO::getCompletetime)).orElse(null);
        return previousNode;
    }

    private ProcessNodeDO handlePwgBranch(List<ProcessNodeDO> nodeList, String pgwBranch) {
        ProcessNodeDO previousNode = null;
        List currPgwBranchList = nodeList.stream().filter(node -> pgwBranch.equals(node.getPgwbranch()) && !Objects.equals(node.getNodeid(), node.getPgwnodeid())).collect(Collectors.toList());
        if (currPgwBranchList.isEmpty()) {
            Iterator<ProcessNodeDO> iterator = nodeList.iterator();
            while (iterator.hasNext()) {
                ProcessNodeDO nodeDO = iterator.next();
                if (!Objects.equals(nodeDO.getNodeid(), nodeDO.getPgwnodeid()) || !iterator.hasNext()) continue;
                previousNode = iterator.next();
                break;
            }
        } else {
            previousNode = (ProcessNodeDO)currPgwBranchList.get(0);
        }
        return previousNode;
    }

    public List<ProcessNodeDO> getLatestProcessNodes(String processId, String bizCode, String nodeCode, String subBranch) {
        if (!StringUtils.hasText(processId) && !StringUtils.hasText(bizCode)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processId);
        processNodeDTO.setBizcode(bizCode);
        List<ProcessNodeDO> processNodeList = this.workflowProcessNodeDao.listProcessNode(processNodeDTO);
        return this.getLatestProcessNodes(nodeCode, processNodeList, subBranch);
    }

    public List<ProcessNodeDO> getLatestProcessNodes(String nodeCode, List<ProcessNodeDO> processNodeList, String subBranch) {
        ProcessNodeDO oneCurProcessNode;
        ArrayList<ProcessNodeDO> resultList = new ArrayList<ProcessNodeDO>();
        if (StringUtils.hasText(subBranch)) {
            processNodeList = processNodeList.stream().filter(node -> Objects.equals(subBranch, node.getSubprocessbranch()) && !Objects.equals(node.getNodeid(), node.getSubprocessnodeid())).collect(Collectors.toList());
        }
        if ((oneCurProcessNode = (ProcessNodeDO)processNodeList.stream().filter(node -> node.getOrdernum() != null).filter(node -> Objects.equals(nodeCode, node.getNodecode())).max(Comparator.comparing(ProcessNodeDO::getOrdernum)).orElse(null)) == null) {
            return resultList;
        }
        BigDecimal curOrdernum = oneCurProcessNode.getOrdernum();
        List<Object> previousNodeList = processNodeList.stream().filter(node -> !BigDecimal.ONE.equals(node.getHiddenflag())).filter(node -> curOrdernum.compareTo(node.getOrdernum()) >= 0).sorted(Comparator.comparing(ProcessNodeDO::getOrdernum, Comparator.reverseOrder())).collect(Collectors.toList());
        if (previousNodeList.isEmpty()) {
            return resultList;
        }
        String pgwnodeid = oneCurProcessNode.getPgwnodeid();
        String pgwbranch = oneCurProcessNode.getPgwbranch();
        if (StringUtils.hasText(pgwnodeid)) {
            previousNodeList = previousNodeList.stream().filter(node -> !Objects.equals(node.getPgwnodeid(), node.getNodeid())).filter(node -> Objects.equals(node.getPgwbranch(), pgwbranch)).collect(Collectors.toList());
        }
        if (previousNodeList.size() == 1) {
            return previousNodeList;
        }
        boolean skipFlag = false;
        for (ProcessNodeDO nodeDO : previousNodeList) {
            if (Objects.equals(nodeDO.getNodecode(), nodeCode)) {
                resultList.add(nodeDO);
                skipFlag = true;
                continue;
            }
            if (!skipFlag) continue;
            break;
        }
        return resultList;
    }

    public int getApprovalCount(ApprovalCountDTO approvalCountDTO) {
        int result = 0;
        String bizCode = approvalCountDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return result;
        }
        ProcessNodeDTO selectDTO = new ProcessNodeDTO();
        selectDTO.setBizcode(bizCode);
        selectDTO.setOrder("DESC");
        List<ProcessNodeDO> processNodeDOList = this.listProcessNode(selectDTO);
        String nodeCode = approvalCountDTO.getNodeCode();
        String subProcessBranch = approvalCountDTO.getSubProcessBranch();
        ProcessNodeDO targetNode = VaWorkflowUtils.findTargetNode(nodeCode, subProcessBranch, processNodeDOList);
        VaWorkflowUtils.filterOtherBranch(processNodeDOList, targetNode);
        String lastNodeCode = null;
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            String curNodeCode = processNodeDO.getNodecode();
            if (Objects.equals(curNodeCode, nodeCode) && !Objects.equals(lastNodeCode, nodeCode)) {
                ++result;
            }
            lastNodeCode = curNodeCode;
        }
        return result;
    }

    @Deprecated
    public R listNextNode(WorkflowDTO workflowDTO) {
        return this.getWorkflowNextNodeService().listNextNode(workflowDTO);
    }

    @Deprecated
    public R getNextNodeApprover(WorkflowDTO workflowDTO) {
        return this.getWorkflowNextNodeService().getNextNodeApprover(workflowDTO);
    }

    @Deprecated
    public R getNextNodeConfig(WorkflowDTO workflowDTO) {
        return this.getWorkflowNextNodeService().getNextNodeConfig(workflowDTO);
    }
}

