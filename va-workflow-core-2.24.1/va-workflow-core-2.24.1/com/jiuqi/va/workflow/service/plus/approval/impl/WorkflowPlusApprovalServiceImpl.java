/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.todo.VaTodoTaskTypeEnum
 *  com.jiuqi.va.domain.todo.VaTodoTransferDTO
 *  com.jiuqi.va.domain.todo.VaTodoTransferVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowOperation
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.plus.approval.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.todo.VaTodoTaskTypeEnum;
import com.jiuqi.va.domain.todo.VaTodoTransferDTO;
import com.jiuqi.va.domain.todo.VaTodoTransferVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowOperation;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.dao.PlusApprovalInfoDao;
import com.jiuqi.va.workflow.dao.PlusApprovalUserDao;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import com.jiuqi.va.workflow.domain.plus.approval.PlusApprovalInfoExtendsDTO;
import com.jiuqi.va.workflow.domain.plus.approval.PlusApprovalUserExtendsDTO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowPlusApprovalHelper;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowPlusApprovalServiceImpl
implements WorkflowPlusApprovalService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowPlusApprovalServiceImpl.class);
    @Autowired
    private PlusApprovalInfoDao plusAprovalInfoDao;
    @Autowired
    private PlusApprovalUserDao plusApprovalUserDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private DelegateService delegateService;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private WorkflowPlusApprovalHelper workflowPlusApprovalHelper;
    private WorkflowSevice workflowSevice;

    public WorkflowSevice getWorkflowSevice() {
        if (this.workflowSevice == null) {
            this.workflowSevice = (WorkflowSevice)ApplicationContextRegister.getBean(WorkflowSevice.class);
        }
        return this.workflowSevice;
    }

    public List<PlusApprovalInfoDO> list(PlusApprovalInfoDTO plusApprovalInfoDTO) {
        if (new BigDecimal("1").equals(plusApprovalInfoDTO.getCountersignflag())) {
            plusApprovalInfoDTO.setNodeid(null);
        }
        return this.plusAprovalInfoDao.select((PlusApprovalInfoDO)plusApprovalInfoDTO);
    }

    public List<PlusApprovalInfoDO> listPlusApprovalInfoDto(PlusApprovalInfoDO plusApprovalInfoDO) {
        Assert.notNull((Object)plusApprovalInfoDO, "\u8bf7\u6821\u9a8c\u53c2\u6570\uff0c\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        return Optional.ofNullable(this.plusAprovalInfoDao.select(plusApprovalInfoDO)).orElse(Collections.emptyList());
    }

    public List<PlusApprovalInfoDO> selectByConditionAndNodeIds(PlusApprovalInfoDTO info) {
        return this.plusAprovalInfoDao.selectByConditionAndNodeIds(info);
    }

    @Transactional
    public void updatePlusApprovalInfoList(List<PlusApprovalInfoDO> plusApprovalInfoDoList) {
        for (PlusApprovalInfoDO plusApprovalInfoDO : plusApprovalInfoDoList) {
            this.plusAprovalInfoDao.updateByPrimaryKeySelective(plusApprovalInfoDO);
        }
    }

    public List<Map<String, Object>> getPlusApprovalComment(WorkflowDTO workflowDTO) {
        String nodeCode = workflowDTO.getNodeCode();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        if (!StringUtils.hasText(nodeCode) || !StringUtils.hasText(processInstanceId)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        ArrayList<Map<String, Object>> plusApprovalComments = new ArrayList<Map<String, Object>>();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processInstanceId);
        List processNodeDOS = Optional.ofNullable(this.workflowProcessNodeService.listProcessNode(processNodeDTO)).orElse(Collections.emptyList());
        List processNodeDOList = this.workflowProcessNodeService.getLatestProcessNodes(nodeCode, processNodeDOS, null);
        Set<String> plusUserSet = this.listCurrentUserPlusList(nodeCode, processInstanceId, processNodeDOList);
        if (plusUserSet.isEmpty()) {
            return plusApprovalComments;
        }
        if ((processNodeDOList = processNodeDOList.stream().filter(x -> Objects.nonNull(x.getCompleteuserid()) && plusUserSet.contains(x.getCompleteuserid())).collect(Collectors.toList())).isEmpty()) {
            return plusApprovalComments;
        }
        UserDTO userDTO = new UserDTO();
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            if (!new BigDecimal(1).equals(processNodeDO.getApprovalflag()) && !new BigDecimal(0).equals(processNodeDO.getApprovalflag()) || !StringUtils.hasText(processNodeDO.getCompleteresult())) continue;
            HashMap<String, Object> comment = new HashMap<String, Object>();
            String completeuserid = processNodeDO.getCompleteuserid();
            if (!StringUtils.hasText(completeuserid)) continue;
            userDTO.setId(completeuserid);
            UserDO userDO = this.authUserClient.get(userDTO);
            String completeusername = userDO == null ? processNodeDO.getCompleteusername() : userDO.getName();
            comment.put("COMPLETEUSERNAME", completeusername);
            comment.put("COMPLETECOMMENT", processNodeDO.getCompletecomment());
            comment.put("COMPLETETIME", processNodeDO.getCompletetime());
            plusApprovalComments.add(comment);
        }
        return plusApprovalComments;
    }

    private List<VaTodoTransferVO> getTransferList(String nodeCode, String processInstanceId) {
        VaTodoTransferDTO transferDTO = new VaTodoTransferDTO();
        transferDTO.setProcessid(processInstanceId);
        transferDTO.setNodecode(nodeCode);
        transferDTO.setIgnoreuser(Boolean.valueOf(true));
        R r = this.todoClient.listTransferInfo(transferDTO);
        List<Object> transferList = new ArrayList<VaTodoTransferVO>();
        if (r == null || r.getCode() != 0) {
            return transferList;
        }
        Object data = r.get((Object)"data");
        if (data == null) {
            return transferList;
        }
        transferList = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)data), VaTodoTransferVO.class);
        return transferList;
    }

    public Set<String> listCurrentUserPlusList(String nodeCode, String processInstanceId, List<ProcessNodeDO> processNodeDOList) {
        List nodeIds = processNodeDOList.stream().map(ProcessNodeDO::getNodeid).distinct().collect(Collectors.toList());
        PlusApprovalInfoDTO plusApprovalInfoDO = new PlusApprovalInfoDTO();
        plusApprovalInfoDO.setNodecode(nodeCode);
        plusApprovalInfoDO.setProcessid(processInstanceId);
        plusApprovalInfoDO.setNodeIds(nodeIds);
        List<PlusApprovalInfoDO> currentNodePlusApprovalList = this.selectByConditionAndNodeIds(plusApprovalInfoDO);
        if (currentNodePlusApprovalList.isEmpty()) {
            return Collections.emptySet();
        }
        List<VaTodoTransferVO> transferList = this.getTransferList(nodeCode, processInstanceId);
        Collections.reverse(transferList);
        boolean oldDataFlag = transferList.stream().anyMatch(transfer -> transfer.getNodeid() == null);
        if (!oldDataFlag) {
            transferList.removeIf(t -> !nodeIds.contains(t.getNodeid()));
        }
        return this.getLowLevelUsers(currentNodePlusApprovalList, transferList, processNodeDOList, oldDataFlag);
    }

    private Set<String> getLowLevelUsers(List<PlusApprovalInfoDO> plusApprovalList, List<VaTodoTransferVO> transferList, List<ProcessNodeDO> processNodeList, boolean oldDataFlag) {
        String currentUserId = ShiroUtil.getUser().getId();
        HashSet<String> userIdUniqueSet = new HashSet<String>();
        ProcessNodeDO processNodeDO = processNodeList.stream().filter(node -> Objects.equals(currentUserId, node.getCompleteuserid())).findFirst().orElse(null);
        if (processNodeDO == null) {
            return userIdUniqueSet;
        }
        Stack<String[]> stack = new Stack<String[]>();
        String[] currentUser = new String[]{currentUserId, processNodeDO.getNodeid()};
        stack.push(currentUser);
        while (!stack.isEmpty()) {
            List transferToInfos;
            String[] approvalUser = (String[])stack.pop();
            String approvalUserId = approvalUser[0];
            String nodeId = approvalUser[1];
            List lowPlusApprovalInfos = plusApprovalList.stream().filter(approval -> Objects.equals(approval.getUsername(), approvalUserId)).collect(Collectors.toList());
            if (!lowPlusApprovalInfos.isEmpty()) {
                for (Object approvalInfoDO : lowPlusApprovalInfos) {
                    String approvaluser = approvalInfoDO.getApprovaluser();
                    if (!StringUtils.hasText(approvaluser) || !userIdUniqueSet.add(approvaluser)) continue;
                    stack.push(new String[]{approvaluser, approvalInfoDO.getNodeid()});
                }
            }
            if (transferList.isEmpty()) continue;
            List transferFromInfos = oldDataFlag ? transferList.stream().filter(transfer -> Objects.equals(transfer.getTransferFrom(), approvalUserId)).collect(Collectors.toList()) : transferList.stream().filter(transfer -> Objects.equals(transfer.getTransferFrom(), approvalUserId)).filter(transfer -> Objects.equals(transfer.getNodeid(), nodeId)).collect(Collectors.toList());
            if (!transferFromInfos.isEmpty()) {
                Object approvalInfoDO;
                approvalInfoDO = transferFromInfos.iterator();
                while (approvalInfoDO.hasNext()) {
                    VaTodoTransferVO transferFromInfo = (VaTodoTransferVO)approvalInfoDO.next();
                    String transferTo = transferFromInfo.getTransferTo();
                    if (!userIdUniqueSet.add(transferTo)) continue;
                    stack.push(new String[]{transferTo, transferFromInfo.getNodeid()});
                }
            }
            if ((transferToInfos = oldDataFlag ? transferList.stream().filter(transfer -> Objects.equals(transfer.getTransferTo(), approvalUserId)).collect(Collectors.toList()) : transferList.stream().filter(transfer -> Objects.equals(transfer.getTransferTo(), approvalUserId)).filter(transfer -> Objects.equals(transfer.getNodeid(), nodeId)).collect(Collectors.toList())).isEmpty()) continue;
            for (VaTodoTransferVO transferToInfo : transferToInfos) {
                String transferFrom = transferToInfo.getTransferFrom();
                if (!userIdUniqueSet.add(transferFrom)) continue;
                stack.push(new String[]{transferFrom, transferToInfo.getNodeid()});
            }
        }
        return userIdUniqueSet;
    }

    @Transactional(rollbackFor={Exception.class})
    public void setPlusApproval(TenantDO tenantDO) {
        String processId = (String)tenantDO.getExtInfo("PROCESSID");
        String nodeCode = (String)tenantDO.getExtInfo("TASKDEFINEKEY");
        String taskId = (String)tenantDO.getExtInfo("TASKID");
        String approvalcomment = (String)tenantDO.getExtInfo("approvalcomment");
        List<PlusApprovalInfoDO> updatePlusApprovalList = Optional.ofNullable(JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)tenantDO.getExtInfo("updatePlusInfo")), PlusApprovalInfoDO.class)).orElse(new ArrayList());
        updatePlusApprovalList = updatePlusApprovalList.stream().filter(u -> StringUtils.hasText(u.getApprovaluser())).collect(Collectors.toList());
        List plusApprovalInfoList = Optional.ofNullable(JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)tenantDO.getExtInfo("plusinfos")), PlusApprovalInfoDO.class)).orElse(new ArrayList());
        plusApprovalInfoList = plusApprovalInfoList.stream().filter(p -> StringUtils.hasText(p.getApprovaluser())).collect(Collectors.toList());
        List<PlusApprovalInfoDTO> updatePlusApprovalDtoList = this.workflowPlusApprovalHelper.convertPlusApprovalDoToDtoList(updatePlusApprovalList);
        Integer counterSignFlag = (Integer)tenantDO.getExtInfo("COUNTERSIGNFLAG");
        PlusApprovalInfoDO info = new PlusApprovalInfoDO(processId, nodeCode);
        if (counterSignFlag != 1) {
            info.setNodeid(taskId);
        }
        List latestProcessNodes = this.workflowProcessNodeService.getLatestProcessNodes(processId, null, nodeCode, null);
        List nodeIds = latestProcessNodes.stream().map(ProcessNodeDO::getNodeid).distinct().collect(Collectors.toList());
        PlusApprovalInfoDTO infoDTO = new PlusApprovalInfoDTO();
        infoDTO.setProcessid(processId);
        infoDTO.setNodecode(nodeCode);
        infoDTO.setNodeIds(nodeIds);
        List<PlusApprovalInfoDO> existList = this.plusAprovalInfoDao.selectByConditionAndNodeIds(infoDTO);
        ArrayList<PlusApprovalInfoDTO> removeList = new ArrayList<PlusApprovalInfoDTO>();
        ArrayList<PlusApprovalInfoDO> onlyUpdateList = new ArrayList<PlusApprovalInfoDO>();
        this.handleOnlyUpdateComment(updatePlusApprovalDtoList, existList, removeList, onlyUpdateList, updatePlusApprovalList);
        List<PlusApprovalInfoDTO> oldPlusApprovalDtoList = this.workflowPlusApprovalHelper.getRemoveFromUpdatePlusApprovalDtoList(updatePlusApprovalDtoList);
        List<PlusApprovalInfoDTO> removeInfos = Optional.ofNullable(JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)tenantDO.getExtInfo("removeinfos")), PlusApprovalInfoDTO.class)).orElse(new ArrayList());
        if (!CollectionUtils.isEmpty(oldPlusApprovalDtoList)) {
            removeInfos.addAll(oldPlusApprovalDtoList);
        }
        this.checkIfRemove(latestProcessNodes, taskId, removeList);
        this.checkIfRemove(latestProcessNodes, taskId, removeInfos);
        onlyUpdateList.forEach(o -> this.plusAprovalInfoDao.updateByPrimaryKeySelective(o));
        ArrayList<Object> plusInfos = new ArrayList<Object>();
        for (Object temp : plusApprovalInfoList) {
            Map<String, String> usermap = this.getOneUserData(temp.getApprovaluser(), temp.getTenantName());
            if (usermap.get("stopflag") != null && usermap.get("stopflag").equals("1")) continue;
            plusInfos.add(temp);
        }
        HashMap<String, PlusApprovalInfoDO> uniqueMap = new HashMap<String, PlusApprovalInfoDO>();
        for (PlusApprovalInfoDO exist : existList) {
            uniqueMap.put(exist.getApprovaluser(), exist);
        }
        List completeUsers = latestProcessNodes.stream().filter(node -> node.getCompletetime() != null).map(ProcessNodeDO::getCompleteuserid).collect(Collectors.toList());
        ArrayList<PlusApprovalInfoDO> newPlusInfo = new ArrayList<PlusApprovalInfoDO>();
        for (PlusApprovalInfoDO plusApprovalInfoDO : plusInfos) {
            String approvaluser = plusApprovalInfoDO.getApprovaluser();
            if (!uniqueMap.containsKey(approvaluser)) {
                newPlusInfo.add(plusApprovalInfoDO);
                continue;
            }
            PlusApprovalInfoDO exist = (PlusApprovalInfoDO)uniqueMap.get(approvaluser);
            if (!Objects.equals(exist.getApprovalcomment(), plusApprovalInfoDO.getApprovalcomment()) || !Objects.equals(exist.getPlusSignApprovalFlag(), plusApprovalInfoDO.getPlusSignApprovalFlag())) {
                PlusApprovalInfoDTO target = new PlusApprovalInfoDTO();
                BeanUtils.copyProperties(exist, target);
                if (!completeUsers.contains(approvaluser)) {
                    removeInfos.add(target);
                }
                newPlusInfo.add(plusApprovalInfoDO);
                continue;
            }
            if (!completeUsers.contains(approvaluser)) continue;
            newPlusInfo.add(plusApprovalInfoDO);
        }
        removeInfos = removeInfos.stream().filter(e -> uniqueMap.containsKey(e.getApprovaluser())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(updatePlusApprovalList)) {
            newPlusInfo.addAll(updatePlusApprovalList);
        }
        if (approvalcomment != null) {
            info.setApprovalcomment(approvalcomment);
            this.plusAprovalInfoDao.update(info);
        }
        WorkflowOptionDTO workFlowOptionDto = this.workflowHelperService.getWorkFlowOptionDto();
        boolean bl = String.valueOf(1).equals(workFlowOptionDto.getPlusApprovalFlag());
        boolean reserveTodoFlag = "1".equals(workFlowOptionDto.getWf1003());
        this.handleRemoveApproval(removeInfos, latestProcessNodes, bl, tenantDO);
        this.handlePlusApproval(newPlusInfo, bl, reserveTodoFlag, tenantDO);
    }

    public void addCommonUser(List<PlusApprovalInfoDO> commonUserList) {
        if (CollectionUtils.isEmpty(commonUserList)) {
            return;
        }
        PlusApprovalUserDO user = new PlusApprovalUserDO();
        String currentUser = ShiroUtil.getUser().getId();
        user.setUsername(currentUser);
        List<PlusApprovalUserDO> exitPlusApprovalUserList = this.plusApprovalUserDao.selectByUserName(user);
        List usernames = exitPlusApprovalUserList.stream().map(PlusApprovalUserDO::getCommonuser).collect(Collectors.toList());
        usernames.add(currentUser);
        commonUserList.removeIf(c -> usernames.contains(c.getApprovaluser()));
        this.setPlusApprovalCommonUser(commonUserList);
    }

    private void handleOnlyUpdateComment(List<PlusApprovalInfoDTO> updatePlusApprovalDtoList, List<PlusApprovalInfoDO> existList, List<PlusApprovalInfoDTO> removeList, List<PlusApprovalInfoDO> onlyUpdateList, List<PlusApprovalInfoDO> updatePlusApprovalList) {
        if (CollectionUtils.isEmpty(updatePlusApprovalDtoList) || CollectionUtils.isEmpty(existList)) {
            return;
        }
        for (PlusApprovalInfoDTO dto : updatePlusApprovalDtoList) {
            PlusApprovalInfoDO plusApprovalInfoDO = existList.stream().filter(e -> Objects.equals(dto.getApprovaluser(), e.getApprovaluser()) && Objects.equals(dto.getNodeid(), e.getNodeid()) && Objects.equals(dto.getPlusSignApprovalFlag(), e.getPlusSignApprovalFlag()) && !Objects.equals(dto.getApprovalcomment(), e.getApprovalcomment())).findFirst().orElse(null);
            if (plusApprovalInfoDO == null) continue;
            plusApprovalInfoDO.setApprovalcomment(dto.getApprovalcomment());
            onlyUpdateList.add(plusApprovalInfoDO);
            removeList.add(dto);
        }
        updatePlusApprovalDtoList.removeAll(removeList);
        List ids = removeList.stream().map(PlusApprovalInfoDO::getId).collect(Collectors.toList());
        updatePlusApprovalList.removeIf(plus -> ids.contains(plus.getId()));
    }

    private void checkIfRemove(List<ProcessNodeDO> processNodeDOList, String taskId, List<PlusApprovalInfoDTO> removeInfos) {
        if (CollectionUtils.isEmpty(removeInfos)) {
            return;
        }
        String currentUserId = ShiroUtil.getUser().getId();
        ArrayList<String> approvalFinishedUserList = new ArrayList<String>();
        boolean isCurrentNode = false;
        for (ProcessNodeDO nodeDO : processNodeDOList) {
            String nodeId = nodeDO.getNodeid();
            String completeUserId = nodeDO.getCompleteuserid();
            String completeResult = nodeDO.getCompleteresult();
            if (BigDecimal.ONE.equals(nodeDO.getCompleteusertype()) && completeResult != null && nodeDO.getRejectstatus() == null) {
                approvalFinishedUserList.add(nodeId + "#" + completeUserId);
            }
            if (!nodeId.equals(taskId) || !completeUserId.equals(currentUserId)) continue;
            Assert.isNull((Object)completeResult, VaWorkFlowI18nUtils.getInfo("va.workflow.addsignatureinfo"));
            isCurrentNode = true;
        }
        Assert.isTrue(isCurrentNode, VaWorkFlowI18nUtils.getInfo("va.workflow.datachanged"));
        for (PlusApprovalInfoDTO infoDTO : removeInfos) {
            if (!approvalFinishedUserList.contains(infoDTO.getNodeid() + "#" + infoDTO.getApprovaluser())) continue;
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.user") + infoDTO.getApprovalusertitle() + VaWorkFlowI18nUtils.getInfo("va.workflow.cantdeletedelegate"));
        }
    }

    private void handlePlusApproval(List<PlusApprovalInfoDO> plusApprovalInfoDoList, boolean approvalFlag, boolean reserveTodoFlag, TenantDO tenantDO) {
        if (CollectionUtils.isEmpty(plusApprovalInfoDoList)) {
            return;
        }
        String processId = (String)tenantDO.getExtInfo("PROCESSID");
        String taskId = (String)tenantDO.getExtInfo("TASKID");
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(processId);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.stopprocessinfo"));
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("PlusInfos", plusApprovalInfoDoList);
        map.put("approvalFlag", approvalFlag);
        map.put("reserveTodoFlag", reserveTodoFlag);
        if (tenantDO.getExtInfo() != null) {
            map.putAll(tenantDO.getExtInfo());
        }
        WorkflowModel workflowModel = this.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
        WorkflowDTO workflowDTO = new WorkflowDTO();
        workflowDTO.setProcessInstanceId(processId);
        workflowDTO.setUniqueCode(processDO.getDefinekey());
        workflowDTO.setNodeCode((String)tenantDO.getExtInfo("TASKDEFINEKEY"));
        workflowDTO.setProcessDefineVersion(Long.valueOf(processDO.getDefineversion().longValue()));
        workflowDTO.setBizType(processDO.getBizmodule());
        workflowDTO.setBizDefine(processDO.getBiztype());
        workflowDTO.setBizCode(processDO.getBizcode());
        workflowDTO.setTaskId(taskId);
        workflowDTO.setExtInfo(map);
        workflowDTO.addExtInfo("COUNTERSIGNFLAG", tenantDO.getExtInfo("COUNTERSIGNFLAG"));
        workflowDTO.setTraceId(Utils.getTraceId());
        R r = workflowModel.plusApproval(workflowDTO);
        if (r != null && r.getCode() != 0) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.taskbeendealneedtorefresh"));
        }
    }

    public void afterPlusApprovalInfo(Map<String, Object> param) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map currentTask = vaWorkflowContext.getCurrentTask();
        String userId = ShiroUtil.getUser().getId();
        List<PlusApprovalInfoDO> plusApprovalInfoDoList = VaWorkflowUtils.getList(param.get("PlusInfos"));
        Map<String, String> userToIdMap = VaWorkflowUtils.getStringMap(param.get("PlusUserToId"));
        boolean reserveTodoFlag = (Boolean)param.get("reserveTodoFlag");
        boolean approvalFlag = (Boolean)param.get("approvalFlag");
        String taskDefineKey = (String)param.get("TASKDEFINEKEY");
        String processId = (String)param.get("PROCESSID");
        ArrayList<ProcessNodeDO> processNodeDOs = new ArrayList<ProcessNodeDO>();
        ArrayList todoTasks = new ArrayList();
        for (PlusApprovalInfoDO info : plusApprovalInfoDoList) {
            info.setId(UUID.randomUUID().toString());
            info.setUsername(userId);
            info.setNodeid(userToIdMap.get(info.getApprovaluser()));
            info.setCreatetime(new Date());
            info.setNodecode(taskDefineKey);
            info.setProcessid(processId);
            this.plusAprovalInfoDao.insert(info);
            HashMap<String, Object> todoTask = new HashMap<String, Object>(currentTask);
            todoTask.put("TASKID", userToIdMap.get(info.getApprovaluser()));
            todoTask.put("TASKTYPE", VaTodoTaskTypeEnum.JOIN.getValue());
            todoTask.put("PARTICIPANT", info.getApprovaluser());
            todoTask.put("DELEGATEID", null);
            todoTask.put("RECEIVETIME", new Date());
            todoTask.put("APPROVALFLAG", approvalFlag ? 0 : 1);
            ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setNodeid((String)param.get("TASKID"));
            processNodeDTO.setProcessid((String)param.get("PROCESSID"));
            processNodeDTO.setCompleteuserid(ShiroUtil.getUser().getId());
            ProcessNodeDO processNodeDO = this.workflowProcessNodeService.get(processNodeDTO);
            processNodeDO.setNodeid(userToIdMap.get(info.getApprovaluser()));
            processNodeDO.setCompleteuserid(info.getApprovaluser());
            processNodeDO.setCompleteusertype(BigDecimal.ONE);
            processNodeDO.setCompletecomment(null);
            processNodeDO.setCompleteresult(null);
            processNodeDO.setApprovalflag(approvalFlag ? BigDecimal.ZERO : BigDecimal.ONE);
            processNodeDO.setDelegateuser(null);
            processNodeDO.setReceivetime(new Date());
            List delegate = this.delegateService.getDelegate(info.getApprovaluser(), todoTask);
            if (delegate.isEmpty() || reserveTodoFlag) {
                todoTasks.add(todoTask);
                processNodeDOs.add(processNodeDO);
            }
            for (DelegateDTO dto : delegate) {
                if (dto.getAgentusers() == null) continue;
                for (String agentuser : dto.getAgentusers()) {
                    HashMap<String, Object> todoTaskTemp = new HashMap<String, Object>(todoTask);
                    todoTaskTemp.put("TASKTYPE", VaTodoTaskTypeEnum.DELEGATION.getValue());
                    todoTaskTemp.put("PARTICIPANT", agentuser);
                    todoTaskTemp.put("DELEGATEID", dto.getId());
                    todoTasks.add(todoTaskTemp);
                    ProcessNodeDO processNodeDOTemp = new ProcessNodeDO();
                    BeanUtils.copyProperties(processNodeDO, processNodeDOTemp);
                    processNodeDOTemp.setCompleteuserid(agentuser);
                    processNodeDOTemp.setCompleteusertype(BigDecimal.valueOf(3L));
                    processNodeDOTemp.setDelegateuser(info.getApprovaluser());
                    processNodeDOs.add(processNodeDOTemp);
                }
            }
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todoTasks", todoTasks);
        vaWorkflowContext.getCustomParam().put("pgwPlusApprovalFlag", true);
        this.todoClient.addBatch(tenantDO);
        this.workflowProcessNodeService.addBatch(processNodeDOs, tenantDO.getTenantName());
        vaWorkflowContext.getCustomParam().put("todoTasks", todoTasks);
    }

    private void handleRemoveApproval(List<PlusApprovalInfoDTO> removeInfos, List<ProcessNodeDO> nodes, boolean approvalFlag, TenantDO tenantDO) {
        if (CollectionUtils.isEmpty(removeInfos)) {
            return;
        }
        HashMap<String, String> userNodeIdMap = new HashMap<String, String>();
        List removeUserList = removeInfos.stream().map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
        List listProcessNode = nodes.stream().filter(e -> e.getRejectstatus() == null).sorted((o1, o2) -> {
            if (o1.getCompleteusertype() == null) {
                return -1;
            }
            if (o2.getCompleteusertype() == null) {
                return 1;
            }
            return o1.getCompleteusertype().compareTo(o2.getCompleteusertype());
        }).collect(Collectors.toList());
        for (ProcessNodeDO processNodeDO : listProcessNode) {
            BigDecimal completeusertype = processNodeDO.getCompleteusertype();
            String nodeId = processNodeDO.getNodeid();
            String delegateuser = processNodeDO.getDelegateuser();
            String completeuserid = processNodeDO.getCompleteuserid();
            if (completeusertype == null || userNodeIdMap.containsKey(completeuserid)) continue;
            if (completeusertype.equals(BigDecimal.ONE) && removeUserList.contains(completeuserid)) {
                userNodeIdMap.put(completeuserid, nodeId);
            }
            if (!completeusertype.equals(BigDecimal.valueOf(3L)) || !removeUserList.contains(delegateuser)) continue;
            userNodeIdMap.put(completeuserid, nodeId);
        }
        this.packageParamRemovePlusApprovalInfo(removeInfos, userNodeIdMap, tenantDO, approvalFlag);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void packageParamRemovePlusApprovalInfo(List<PlusApprovalInfoDTO> removeInfos, Map<String, String> userNodeIdMap, TenantDO tenantDO, boolean approvalFlag) {
        VaWorkflowContext vaWorkflowContext;
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        if (tenantDO.getExtInfo() != null) {
            paramMap.putAll(tenantDO.getExtInfo());
        }
        List approvalList = removeInfos.stream().filter(x -> Objects.nonNull(x.getPlusSignApprovalFlag()) && 0 == x.getPlusSignApprovalFlag()).collect(Collectors.toList());
        List notApprovalList = removeInfos.stream().filter(x -> Objects.nonNull(x.getPlusSignApprovalFlag()) && 1 == x.getPlusSignApprovalFlag()).collect(Collectors.toList());
        List allTempList = removeInfos.stream().filter(x -> Objects.isNull(x.getPlusSignApprovalFlag())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(approvalList)) {
            this.resetRemovePlusApprovalParamMap(paramMap);
            paramMap.put("RemoveInfos", approvalList);
            paramMap.put("RemoveUserToId", this.workflowPlusApprovalHelper.getRemoveUserNodeIdMap(approvalList, userNodeIdMap));
            try {
                vaWorkflowContext = new VaWorkflowContext();
                vaWorkflowContext.setOperation(WorkflowOperation.REMOVEAPPROVAL);
                vaWorkflowContext.setCustomParam(paramMap);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                this.getWorkflowSevice().removeApproval((String)tenantDO.getExtInfo("PROCESSID"), null);
            }
            finally {
                VaContext.removeVaWorkflowContext();
            }
        }
        if (!CollectionUtils.isEmpty(notApprovalList)) {
            this.resetRemovePlusApprovalParamMap(paramMap);
            paramMap.put("RemoveInfos", notApprovalList);
            paramMap.put("RemoveUserToId", this.workflowPlusApprovalHelper.getRemoveUserNodeIdMap(notApprovalList, userNodeIdMap));
            this.afterRemoveApprovalInfo(paramMap);
        }
        if (!CollectionUtils.isEmpty(allTempList)) {
            this.resetRemovePlusApprovalParamMap(paramMap);
            paramMap.put("RemoveInfos", allTempList);
            paramMap.put("RemoveUserToId", this.workflowPlusApprovalHelper.getRemoveUserNodeIdMap(allTempList, userNodeIdMap));
            if (approvalFlag) {
                try {
                    vaWorkflowContext = new VaWorkflowContext();
                    vaWorkflowContext.setOperation(WorkflowOperation.REMOVEAPPROVAL);
                    vaWorkflowContext.setCustomParam(paramMap);
                    VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                    this.getWorkflowSevice().removeApproval((String)tenantDO.getExtInfo("PROCESSID"), null);
                }
                finally {
                    VaContext.removeVaWorkflowContext();
                }
            } else {
                this.afterRemoveApprovalInfo(paramMap);
            }
        }
    }

    private void resetRemovePlusApprovalParamMap(Map<String, Object> paramMap) {
        paramMap.remove("RemoveInfos");
        paramMap.remove("RemoveUserToId");
    }

    public void afterRemoveApprovalInfo(Map<String, Object> param) {
        List<PlusApprovalInfoDO> infos = VaWorkflowUtils.getList(param.get("RemoveInfos"));
        Integer countersignflag = (Integer)param.get("COUNTERSIGNFLAG");
        String taskid = (String)param.get("TASKID");
        String processId = (String)param.get("PROCESSID");
        if (StringUtils.hasText(processId)) {
            for (PlusApprovalInfoDO info : infos) {
                info.setProcessid(processId);
                info.setNodecode((String)param.get("TASKDEFINEKEY"));
                if (1 != countersignflag) {
                    info.setNodeid(taskid);
                }
                this.plusAprovalInfoDao.deleteByCondition(info);
            }
        } else {
            logger.error("\u5220\u9664\u52a0\u7b7e\u4fe1\u606f\u51fa\u9519 processId \u4e3a\u7a7a\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        ArrayList todos = new ArrayList();
        Map<String, String> userToIdMap = VaWorkflowUtils.getStringMap(param.get("RemoveUserToId"));
        for (Map.Entry<String, String> entry : userToIdMap.entrySet()) {
            HashMap<String, String> todo = new HashMap<String, String>();
            todo.put("PROCESSID", processId);
            todo.put("TASKID", entry.getValue());
            todo.put("PARTICIPANT", entry.getKey());
            todos.add(todo);
            ProcessNodeDO processNodeDO = new ProcessNodeDO();
            processNodeDO.setNodeid(entry.getValue());
            processNodeDO.setCompleteuserid(entry.getKey());
            processNodeDO.setProcessid(processId);
            this.workflowProcessNodeService.deleteByNodeIdAndUserId(processNodeDO);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("todos", todos);
        tenantDO.setTraceId(Utils.getTraceId());
        this.todoClient.deleteBatch(tenantDO);
    }

    private void setPlusApprovalCommonUser(List<PlusApprovalInfoDO> plusapprovalinfos) {
        for (PlusApprovalInfoDO info : plusapprovalinfos) {
            PlusApprovalUserDO user = new PlusApprovalUserDO();
            user.setUsername(ShiroUtil.getUser().getId().toString());
            user.setCommonuser(info.getApprovaluser());
            user.setUnitcode(info.getApprovalunitcode());
            PlusApprovalUserDO existOne = (PlusApprovalUserDO)this.plusApprovalUserDao.selectByPrimaryKey(user);
            if (existOne != null) continue;
            user.setCreatetime(new Date());
            user.setStaffCode(info.getStaffCode());
            this.plusApprovalUserDao.insert(user);
        }
    }

    public List<PlusApprovalInfoDTO> getPlusApprovalUser(PlusApprovalInfoDTO info) {
        BigDecimal countersignflag = info.getCountersignflag();
        if (new BigDecimal("1").equals(countersignflag)) {
            info.setNodeid(null);
        }
        String processid = info.getProcessid();
        String nodecode = info.getNodecode();
        ProcessNodeDTO processNodeDTO1 = new ProcessNodeDTO();
        processNodeDTO1.setProcessid(processid);
        List processNodes = this.workflowProcessNodeService.listProcessNode(processNodeDTO1);
        List processNodeDOList = this.workflowProcessNodeService.getLatestProcessNodes(nodecode, processNodes, null);
        List nodeIds = processNodeDOList.stream().map(ProcessNodeDO::getNodeid).distinct().collect(Collectors.toList());
        PlusApprovalInfoDTO approvalInfoDO = new PlusApprovalInfoDTO();
        approvalInfoDO.setProcessid(processid);
        approvalInfoDO.setNodecode(nodecode);
        approvalInfoDO.setNodeIds(nodeIds);
        approvalInfoDO.setApprovaluser(info.getApprovaluser());
        List<PlusApprovalInfoDO> infolist = this.plusAprovalInfoDao.selectByConditionAndNodeIds(approvalInfoDO);
        if (info.isDistinct()) {
            LinkedHashMap<String, PlusApprovalInfoDO> plusApprovalMap = new LinkedHashMap<String, PlusApprovalInfoDO>();
            for (PlusApprovalInfoDO plusApprovalInfoDO : infolist) {
                String approvaluser = plusApprovalInfoDO.getApprovaluser();
                plusApprovalMap.put(approvaluser, plusApprovalInfoDO);
            }
            infolist = new ArrayList(plusApprovalMap.values());
        }
        HashMap commentMap = new HashMap(infolist.size());
        if (!infolist.isEmpty()) {
            infolist.forEach(plusInfo -> commentMap.put(plusInfo.getNodeid() + "#" + plusInfo.getApprovaluser(), plusInfo.getApprovalcomment()));
        }
        HashMap<String, String> plususers = new HashMap<String, String>();
        for (PlusApprovalInfoDO plusApprovalInfoDO : infolist) {
            plususers.put(plusApprovalInfoDO.getNodeid() + "#" + plusApprovalInfoDO.getApprovaluser(), null);
        }
        ArrayList<PlusApprovalInfoDTO> arrayList = new ArrayList<PlusApprovalInfoDTO>();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(info.getProcessid());
        processNodeDTO.setNodecode(info.getNodecode());
        processNodeDTO.setFilteNull(true);
        List listProcessNode = this.workflowProcessNodeService.listProcessNodeByCondition(processNodeDTO);
        if (!new BigDecimal("1").equals(countersignflag)) {
            listProcessNode = listProcessNode.stream().filter(processNodeDO -> Objects.equals(processNodeDO.getNodeid(), info.getNodeid())).collect(Collectors.toList());
        }
        ArrayList<String> approvalusers = new ArrayList<String>();
        for (Object processNodeDO2 : listProcessNode) {
            if (processNodeDO2.getCompleteusertype() != null && !processNodeDO2.getCompleteusertype().equals(BigDecimal.ZERO)) continue;
            approvalusers.add(processNodeDO2.getCompleteuserid());
        }
        HashMap<String, String> orgmap = new HashMap<String, String>();
        for (ProcessNodeDO processNodeDO3 : listProcessNode) {
            if (processNodeDO3.getCompleteusertype() != null && !processNodeDO3.getCompleteusertype().equals(BigDecimal.ZERO)) {
                if (processNodeDO3.getCompleteusertype().equals(BigDecimal.ONE)) {
                    plususers.put(processNodeDO3.getNodeid() + "#" + processNodeDO3.getCompleteuserid(), processNodeDO3.getCompleteresult() == null ? "\u672a\u5ba1\u6279" : "\u5df2\u5ba1\u6279");
                    continue;
                }
                if (processNodeDO3.getCompleteusertype().equals(BigDecimal.valueOf(3L))) {
                    plususers.put(processNodeDO3.getNodeid() + "#" + processNodeDO3.getDelegateuser(), processNodeDO3.getCompleteresult() == null ? "\u672a\u5ba1\u6279" : "\u5df2\u5ba1\u6279");
                    continue;
                }
                if (processNodeDO3.getCompleteusertype().equals(BigDecimal.valueOf(2L)) && approvalusers.contains(processNodeDO3.getDelegateuser())) continue;
            }
            PlusApprovalInfoExtendsDTO infoDTO = new PlusApprovalInfoExtendsDTO();
            infoDTO.setApprovaluser(processNodeDO3.getCompleteuserid());
            infoDTO.setApprovalunitcode(processNodeDO3.getCompleteuserunitcode());
            infoDTO.setType("\u5ba1\u6279");
            infoDTO.setApprovalStatus(processNodeDO3.getCompleteresult() == null ? "\u672a\u5ba1\u6279" : "\u5df2\u5ba1\u6279");
            String approvalcomment = (String)commentMap.get(processNodeDO3.getNodeid() + "#" + processNodeDO3.getCompleteuserid());
            infoDTO.setApprovalcomment(approvalcomment);
            if (orgmap.get(infoDTO.getApprovalunitcode()) == null) {
                this.getOneOrgData(orgmap, infoDTO.getApprovalunitcode(), info.getTenantName());
            }
            infoDTO.setApprovalunittitle((String)orgmap.get(infoDTO.getApprovalunitcode()));
            Map<String, String> usermap = this.getOneUserData(infoDTO.getApprovaluser(), infoDTO.getTenantName());
            infoDTO.setApprovalusertitle(usermap.get("title"));
            infoDTO.setApprovalusername(usermap.get("name"));
            infoDTO.setEmail(usermap.get("email"));
            infoDTO.setTelephone(usermap.get("telephone"));
            infoDTO.setUnitcode(usermap.get("unitcode"));
            infoDTO.setName(infoDTO.getApprovalusertitle());
            infoDTO.setUsername(ShiroUtil.getUser().getName());
            infoDTO.setNodeid(processNodeDO3.getNodeid());
            arrayList.add(infoDTO);
        }
        String systemApprovalFlag = this.workflowHelperService.getWorkFlowOptionDto().getPlusApprovalFlag();
        systemApprovalFlag = String.valueOf(1).equals(systemApprovalFlag) ? String.valueOf(0) : String.valueOf(1);
        boolean ifChooseStaff = this.workflowPlusApprovalHelper.getChooseStaffOption();
        for (PlusApprovalInfoDO plusdo : infolist) {
            PlusApprovalInfoExtendsDTO infoDTO = new PlusApprovalInfoExtendsDTO();
            infoDTO.setApprovaluser(plusdo.getApprovaluser());
            infoDTO.setApprovalunitcode(plusdo.getApprovalunitcode());
            infoDTO.setType(VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.countersign"));
            infoDTO.setApprovalStatus((String)plususers.get(plusdo.getNodeid() + "#" + plusdo.getApprovaluser()));
            String approvalcomment = (String)commentMap.get(plusdo.getNodeid() + "#" + plusdo.getApprovaluser());
            infoDTO.setApprovalcomment(approvalcomment);
            infoDTO.setStaffCode(plusdo.getStaffCode());
            if (orgmap.get(infoDTO.getApprovalunitcode()) == null) {
                this.getOneOrgData(orgmap, infoDTO.getApprovalunitcode(), info.getTenantName());
            }
            infoDTO.setApprovalunittitle((String)orgmap.get(infoDTO.getApprovalunitcode()));
            infoDTO.setSuperiorusername(plusdo.getUsername());
            Map<String, String> usermap = this.getOneUserData(infoDTO.getApprovaluser(), infoDTO.getTenantName());
            infoDTO.setApprovalusertitle(usermap.get("title"));
            infoDTO.setApprovalusername(usermap.get("name"));
            infoDTO.setEmail(usermap.get("email"));
            infoDTO.setTelephone(usermap.get("telephone"));
            infoDTO.setUnitcode(usermap.get("unitcode"));
            infoDTO.setName(infoDTO.getApprovalusertitle());
            infoDTO.setUsername(ShiroUtil.getUser().getName());
            infoDTO.setNodeid(plusdo.getNodeid());
            infoDTO.setCreatetime(plusdo.getCreatetime());
            Integer approvalFlag = plusdo.getPlusSignApprovalFlag();
            if (Objects.nonNull(approvalFlag)) {
                infoDTO.setApprovalflag(BigDecimal.valueOf(approvalFlag.intValue()));
            } else if (StringUtils.hasText(systemApprovalFlag)) {
                infoDTO.setApprovalflag(NumberUtils.parseNumber(systemApprovalFlag, BigDecimal.class));
            }
            this.workflowPlusApprovalHelper.handleStaffInfo(ifChooseStaff, plusdo, infoDTO);
            arrayList.add(infoDTO);
        }
        return arrayList;
    }

    public List<PlusApprovalInfoDTO> getNoUser(PlusApprovalInfoDTO info) {
        boolean searchPlusUserFlag;
        String nodecode;
        ArrayList<PlusApprovalInfoDTO> list = new ArrayList<PlusApprovalInfoDTO>();
        String processid = info.getProcessid();
        List<ProcessNodeDO> processNodeDOList = this.getLatestProcessNodeDOS(processid, nodecode = info.getNodecode());
        if (processNodeDOList.isEmpty()) {
            return list;
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(processid);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            return list;
        }
        boolean waitPlusApprovalFlag = this.isWaitPlusApprovalFlag(processDO, nodecode);
        Set<Object> approvalUserSet = new HashSet();
        if (waitPlusApprovalFlag) {
            searchPlusUserFlag = true;
        } else {
            WorkflowOptionDTO workFlowOptionDto = this.workflowHelperService.getWorkFlowOptionDto();
            String wf1013 = workFlowOptionDto.getWf1013();
            searchPlusUserFlag = "1".equals(wf1013);
        }
        if (searchPlusUserFlag || info.isPlusUserSearchForce()) {
            List nodeIds = processNodeDOList.stream().map(ProcessNodeDO::getNodeid).distinct().collect(Collectors.toList());
            PlusApprovalInfoDTO plusApprovalInfoDTO = new PlusApprovalInfoDTO();
            plusApprovalInfoDTO.setNodecode(info.getNodecode());
            plusApprovalInfoDTO.setProcessid(info.getProcessid());
            plusApprovalInfoDTO.setNodeIds(nodeIds);
            List<PlusApprovalInfoDO> currentNodePlusApprovalList = this.plusAprovalInfoDao.selectByConditionAndNodeIds(plusApprovalInfoDTO);
            if (new BigDecimal("1").equals(processNodeDOList.get(0).getCountersignflag())) {
                approvalUserSet = this.getCurrentUserLowLevelApprovalUser(currentNodePlusApprovalList);
            } else {
                String currentUserId = ShiroUtil.getUser().getId();
                approvalUserSet = currentNodePlusApprovalList.stream().map(PlusApprovalInfoDO::getApprovaluser).filter(userId -> !Objects.equals(currentUserId, userId)).collect(Collectors.toSet());
            }
        }
        if (CollectionUtils.isEmpty(approvalUserSet)) {
            return list;
        }
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            String completeUserId;
            if (processNodeDO.getCompleteusertype() == null || processNodeDO.getCompleteusertype().compareTo(BigDecimal.ONE) != 0 || processNodeDO.getCompleteresult() != null || !approvalUserSet.contains(completeUserId = processNodeDO.getCompleteuserid())) continue;
            PlusApprovalInfoDTO infoDTO = new PlusApprovalInfoDTO();
            infoDTO.setApprovaluser(processNodeDO.getCompleteuserid());
            infoDTO.setApprovalunitcode(processNodeDO.getCompleteuserunitcode());
            infoDTO.setApprovalflag(processNodeDO.getApprovalflag());
            UserDO oneUserData = VaWorkFlowDataUtils.getOneUserData(info.getTenantName(), processNodeDO.getCompleteuserid());
            if (Objects.nonNull(oneUserData)) {
                String name = oneUserData.getName();
                String username = oneUserData.getUsername();
                if (StringUtils.hasText(name)) {
                    infoDTO.setUsername(name);
                } else {
                    infoDTO.setUsername(username);
                }
            }
            list.add(infoDTO);
        }
        return list;
    }

    private boolean isWaitPlusApprovalFlag(ProcessDO processDO, String nodecode) {
        boolean waitPlusApprovalFlag = false;
        BigDecimal defineversion = processDO.getDefineversion();
        WorkflowModelDefine define = (WorkflowModelDefine)this.modelDefineService.getDefine(processDO.getDefinekey(), Long.valueOf(Long.parseLong(String.valueOf(defineversion))));
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode arrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        block0: for (JsonNode jsonNode : arrayNode) {
            String resourceId;
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if ("SequenceFlow".equals(nodeType)) continue;
            if ("SubProcess".equals(nodeType)) {
                ArrayNode childShapes = (ArrayNode)jsonNode.get("childShapes");
                for (JsonNode childShape : childShapes) {
                    String childResourceId;
                    String childNodeType = childShape.get("stencil").get("id").asText();
                    if ("SequenceFlow".equals(childNodeType) || !Objects.equals(nodecode, childResourceId = childShape.get("resourceId").asText())) continue;
                    JsonNode properties = childShape.get("properties");
                    if (!properties.hasNonNull("servicetaskclass")) break block0;
                    JsonNode servicetaskclass = properties.get("servicetaskclass");
                    JsonNode plusapprovalnode = servicetaskclass.get("plusapproval");
                    JsonNode waitplusapprovalnode = servicetaskclass.get("waitplusapprovalcompleted");
                    if (plusapprovalnode == null || waitplusapprovalnode == null || !plusapprovalnode.asBoolean()) break block0;
                    waitPlusApprovalFlag = waitplusapprovalnode.asBoolean();
                    break block0;
                }
            }
            if (!Objects.equals(nodecode, resourceId = jsonNode.get("resourceId").asText())) continue;
            JsonNode properties = jsonNode.get("properties");
            if (!properties.hasNonNull("servicetaskclass")) break;
            JsonNode servicetaskclass = properties.get("servicetaskclass");
            JsonNode plusapprovalnode = servicetaskclass.get("plusapproval");
            JsonNode waitplusapprovalnode = servicetaskclass.get("waitplusapprovalcompleted");
            if (plusapprovalnode == null || waitplusapprovalnode == null || !plusapprovalnode.asBoolean()) break;
            waitPlusApprovalFlag = waitplusapprovalnode.asBoolean();
            break;
        }
        return waitPlusApprovalFlag;
    }

    public List<ProcessNodeDO> getLatestProcessNodeDOS(String processId, String nodeCode) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processId);
        List listProcessNode = Optional.ofNullable(this.workflowProcessNodeService.listProcessNode(processNodeDTO)).orElse(Collections.emptyList());
        return this.workflowProcessNodeService.getLatestProcessNodes(nodeCode, listProcessNode, null);
    }

    public Set<String> getCurrentUserLowLevelApprovalUser(List<PlusApprovalInfoDO> currentNodePlusApprovalList) {
        HashSet<String> plusUserSet = new HashSet<String>();
        String currentUserId = ShiroUtil.getUser().getId();
        Stack<String> stack = new Stack<String>();
        stack.push(currentUserId);
        while (!stack.isEmpty()) {
            String finalApprovalUser = (String)stack.pop();
            List currentUserApprovalInfo = currentNodePlusApprovalList.stream().filter(approval -> Objects.equals(approval.getUsername(), finalApprovalUser)).collect(Collectors.toList());
            if (currentUserApprovalInfo.isEmpty()) continue;
            for (PlusApprovalInfoDO infoDO : currentUserApprovalInfo) {
                String approvaluser = infoDO.getApprovaluser();
                if (!StringUtils.hasText(approvaluser) || !plusUserSet.add(approvaluser)) continue;
                stack.push(approvaluser);
            }
        }
        return plusUserSet;
    }

    public void deletePlusApprovalUserCommonUser(PlusApprovalUserDO user) {
        user.setUsername(ShiroUtil.getUser().getId());
        this.plusApprovalUserDao.deleteByPrimaryKey(user);
    }

    public List<PlusApprovalUserDTO> getPlusApprovalUserCommonUser(PlusApprovalUserDO user) {
        HashMap<String, String> orgmap = new HashMap<String, String>();
        ArrayList<PlusApprovalUserDTO> dtolist = new ArrayList<PlusApprovalUserDTO>();
        user.setUsername(ShiroUtil.getUser().getId());
        List<PlusApprovalUserDO> list = this.plusApprovalUserDao.selectByUserName(user);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<PlusApprovalUserDTO>();
        }
        boolean ifChooseStaff = this.workflowPlusApprovalHelper.getChooseStaffOption();
        for (PlusApprovalUserDO doo : list) {
            PlusApprovalUserExtendsDTO dto = new PlusApprovalUserExtendsDTO();
            this.workflowPlusApprovalHelper.handleStaffInfo(ifChooseStaff, doo, dto);
            dto.setCommonuser(doo.getCommonuser());
            dto.setUnitcode(doo.getUnitcode());
            if (orgmap.get(dto.getUnitcode()) == null) {
                this.getOneOrgData(orgmap, dto.getUnitcode(), dto.getTenantName());
            }
            dto.setUnittitle((String)orgmap.get(dto.getUnitcode()));
            Map<String, String> oneUserData = this.getOneUserData(dto.getCommonuser(), dto.getTenantName());
            if (oneUserData.get("stopflag") != null && oneUserData.get("stopflag").equals("1")) continue;
            dto.setCommonusertitle(oneUserData.get("title"));
            if (!ifChooseStaff) {
                dto.setEmail(oneUserData.get("email"));
                dto.setTelephone(oneUserData.get("telephone"));
                dto.setUnitcode(oneUserData.get("unitcode"));
                dto.setUnitname(dto.getUnittitle());
            }
            dto.setName(dto.getCommonusertitle());
            dto.setUsername(oneUserData.get("name"));
            dtolist.add(dto);
        }
        return dtolist;
    }

    private Map<String, String> getOneUserData(String id, String tenantName) {
        HashMap<String, String> map = new HashMap<String, String>();
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(tenantName);
        userDTO.setId(id);
        UserDO userDO = this.authUserClient.get(userDTO);
        if (userDO != null) {
            map.put("name", userDO.getUsername());
            map.put("title", userDO.getName());
            map.put("stopflag", userDO.getStopflag().toString());
            map.put("telephone", userDO.getTelephone());
            map.put("email", userDO.getEmail());
            map.put("unitcode", userDO.getUnitcode());
        }
        return map;
    }

    private void getOneOrgData(Map<String, String> orgMap, String code, String tenantName) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCode(code);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setTenantName(tenantName);
        PageVO list = this.orgDataClient.list(orgDTO);
        List rows = list.getRows();
        if (rows != null && rows.size() != 0) {
            orgMap.put(code, ((OrgDO)rows.get(0)).getName());
        } else {
            orgMap.put(code, null);
        }
    }

    public void deletePlusApprovalUser(PlusApprovalInfoDO infoDO) {
        if (Objects.isNull(infoDO)) {
            logger.error("\u5220\u9664\u52a0\u7b7e\u4fe1\u606f\u53c2\u6570\u4e0d\u6b63\u786e infoDo \u4e3a\u7a7a");
            return;
        }
        if (StringUtils.hasText(infoDO.getProcessid())) {
            this.plusAprovalInfoDao.deleteByCondition(infoDO);
        } else {
            logger.error("\u5220\u9664\u52a0\u7b7e\u4fe1\u606f\u53c2\u6570\u4e0d\u6b63\u786e\uff0c\u6ca1\u6709\u4f20 processId\uff1a{}", (Object)infoDO);
        }
    }

    public List<String> getStopUser(TenantDO tenantDO) {
        ArrayList<String> list = new ArrayList<String>();
        List plusinfos = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)tenantDO.getExtInfo("plusinfos")), PlusApprovalInfoDO.class);
        for (PlusApprovalInfoDO infoDO : plusinfos) {
            Map<String, String> usermap = this.getOneUserData(infoDO.getApprovaluser(), infoDO.getTenantName());
            if (usermap.get("stopflag") == null || !usermap.get("stopflag").equals("1")) continue;
            list.add(usermap.get("title"));
        }
        return list;
    }

    private WorkflowModel getModel(String uniqueCode, Long processDefineVersion) {
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        return workflowModel;
    }

    public List<PlusApprovalInfoDO> listSpecifyNodeApprovalInfo(PlusApprovalInfoDTO infoDTO) {
        PlusApprovalInfoDO info = new PlusApprovalInfoDO();
        info.setNodecode(infoDTO.getNodecode());
        info.setNodeid(infoDTO.getNodeid());
        info.setProcessid(info.getProcessid());
        return this.plusAprovalInfoDao.select(info);
    }

    public void updateNodeId(PlusApprovalInfoDTO infoDTO) {
        String processid = infoDTO.getProcessid();
        if (!StringUtils.hasText(processid)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "processId");
        }
        this.plusAprovalInfoDao.updateNodeId(infoDTO);
    }
}

