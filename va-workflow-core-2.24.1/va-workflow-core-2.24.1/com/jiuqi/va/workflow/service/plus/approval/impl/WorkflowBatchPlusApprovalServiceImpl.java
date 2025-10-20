/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult
 *  com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult$DetailMessage
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.plus.approval.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.dao.PlusApprovalInfoDao;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import com.jiuqi.va.workflow.domain.plus.approval.BatchPlusApprovalDTO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalDTO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalVO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowBatchPlusApprovalService;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowPlusApprovalHelper;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowThreadUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowBatchPlusApprovalServiceImpl
implements WorkflowBatchPlusApprovalService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowBatchPlusApprovalServiceImpl.class);
    public static final String PRE_KEY_PROCESS_INSTANCE_ID = "processInstanceId:";
    public static final String COLON = ":";
    @Autowired
    private PlusApprovalInfoDao plusApprovalInfoDao;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private WorkflowPlusApprovalHelper workflowPlusApprovalHelper;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;

    @Override
    public BatchOperateResult batchPlusApproval(BatchPlusApprovalDTO batchPlusApprovalDTO) {
        List<BatchOperateResult.DetailMessage> successList = Collections.synchronizedList(new ArrayList());
        List<BatchOperateResult.DetailMessage> failedList = Collections.synchronizedList(new ArrayList());
        BatchOperateResult result = new BatchOperateResult(successList, failedList);
        List<TaskDTO> taskDTOList = batchPlusApprovalDTO.getTaskDTOList();
        List<PlusApprovalInfoDO> plusApprovalInfoDOList = batchPlusApprovalDTO.getPlusApprovalInfoDOList();
        if (CollectionUtils.isEmpty(taskDTOList) || CollectionUtils.isEmpty(plusApprovalInfoDOList)) {
            return result;
        }
        Map<String, List<TaskDTO>> taskDTOMap = taskDTOList.stream().collect(Collectors.groupingBy(TaskDTO::getProcessId));
        CountDownLatch countDownLatch = new CountDownLatch(taskDTOMap.size());
        String tenantName = ShiroUtil.getTenantName();
        WorkflowOptionDTO workflowOptionDTO = this.workflowHelperService.getWorkFlowOptionDto();
        BatchPlusContext context = new BatchPlusContext(successList, failedList, countDownLatch, tenantName, workflowOptionDTO, ShiroUtil.getUser());
        for (List<TaskDTO> taskDTOS : taskDTOMap.values()) {
            List<PlusApprovalInfoDO> plusApprovalInfoDOS = this.copyPlusApprovalInfo(plusApprovalInfoDOList);
            BatchPlusApprovalTask task = new BatchPlusApprovalTask(taskDTOS, plusApprovalInfoDOS, context);
            VaWorkflowThreadUtils.executor(task);
        }
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return result;
    }

    private List<PlusApprovalInfoDO> copyPlusApprovalInfo(List<PlusApprovalInfoDO> plusApprovalInfoDOList) {
        return JSONUtil.parseArray((String)JSONUtil.toJSONString(plusApprovalInfoDOList), PlusApprovalInfoDO.class);
    }

    @Override
    public List<WithdrawPlusApprovalVO> getUnapprovedPlusApprovalInfo() {
        ArrayList<WithdrawPlusApprovalVO> result = new ArrayList<WithdrawPlusApprovalVO>();
        String currentUserId = ShiroUtil.getUser().getId();
        if (!StringUtils.hasText(currentUserId)) {
            throw new WorkflowException("va.workflow.failedtogetcurrentloginuser");
        }
        List<PlusApprovalInfoDO> allPlusApprovalInfoList = this.getAllPlusApprovalInfoOfCurrentUser(currentUserId);
        if (CollectionUtils.isEmpty(allPlusApprovalInfoList)) {
            return result;
        }
        int size = allPlusApprovalInfoList.size();
        HashMap<String, String> bizTitleCache = new HashMap<String, String>(size);
        HashMap<String, String> userNameCache = new HashMap<String, String>(size);
        HashMap<String, BaseDataDO> staffCache = new HashMap<String, BaseDataDO>(size);
        HashMap<String, String> positionCache = new HashMap<String, String>(size);
        String tenantName = ShiroUtil.getTenantName();
        boolean chooseStaffOption = this.workflowPlusApprovalHelper.getChooseStaffOption();
        Map<String, List<ProcessNodeDO>> processNodeDOListMap = this.getProcessNodeDOListMap(allPlusApprovalInfoList);
        for (PlusApprovalInfoDO plusApprovalInfoDO : allPlusApprovalInfoList) {
            ProcessNodeDO processNodeDO = this.getProcessNodeDO(processNodeDOListMap, plusApprovalInfoDO);
            if (processNodeDO == null) continue;
            WithdrawPlusApprovalVO withdrawPlusApprovalVO = new WithdrawPlusApprovalVO();
            withdrawPlusApprovalVO.setBizCode(processNodeDO.getBizcode());
            withdrawPlusApprovalVO.setPlusApprovalTime(plusApprovalInfoDO.getCreatetime());
            withdrawPlusApprovalVO.setNodeCode(plusApprovalInfoDO.getNodecode());
            withdrawPlusApprovalVO.setCounterSignFlag(processNodeDO.getCountersignflag());
            withdrawPlusApprovalVO.setProcessId(plusApprovalInfoDO.getProcessid());
            withdrawPlusApprovalVO.setNodeId(processNodeDO.getNodeid());
            withdrawPlusApprovalVO.setApprovalUserUnitCode(plusApprovalInfoDO.getApprovalunitcode());
            String userId = plusApprovalInfoDO.getApprovaluser();
            withdrawPlusApprovalVO.setPlusApprovalUserId(userId);
            withdrawPlusApprovalVO.setPlusApprovalUserName(this.getUserName(userId, tenantName, userNameCache));
            String bizDefine = processNodeDO.getBizdefine();
            withdrawPlusApprovalVO.setBizDefine(bizDefine);
            withdrawPlusApprovalVO.setBizDefineName(this.getBizDefineName(processNodeDO.getNodemodule(), bizDefine, bizTitleCache));
            if (chooseStaffOption) {
                withdrawPlusApprovalVO.setStaffName(this.getStaffName(plusApprovalInfoDO.getStaffCode(), staffCache));
                withdrawPlusApprovalVO.setPositionName(this.getPositionName(plusApprovalInfoDO.getStaffCode(), staffCache, positionCache));
            }
            result.add(withdrawPlusApprovalVO);
        }
        return result;
    }

    private List<PlusApprovalInfoDO> getAllPlusApprovalInfoOfCurrentUser(String currentUserId) {
        PlusApprovalInfoDO infoDO = new PlusApprovalInfoDO();
        infoDO.setUsername(currentUserId);
        return this.plusApprovalInfoDao.selectByUserName(infoDO);
    }

    private Map<String, List<ProcessNodeDO>> getProcessNodeDOListMap(List<PlusApprovalInfoDO> allPlusApprovalInfoList) {
        List processIdList = allPlusApprovalInfoList.stream().map(PlusApprovalInfoDO::getProcessid).collect(Collectors.toList());
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessIdList(processIdList);
        List processNodeDOList = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        return processNodeDOList.stream().collect(Collectors.groupingBy(ProcessNodeDO::getProcessid));
    }

    private ProcessNodeDO getProcessNodeDO(Map<String, List<ProcessNodeDO>> processNodeDOListMap, PlusApprovalInfoDO plusApprovalInfoDO) {
        String processId = plusApprovalInfoDO.getProcessid();
        List<ProcessNodeDO> processNodeDOS = processNodeDOListMap.get(processId);
        if (!CollectionUtils.isEmpty(processNodeDOS)) {
            String approvalUser = plusApprovalInfoDO.getApprovaluser();
            String nodeId = plusApprovalInfoDO.getNodeid();
            ProcessNodeDO processNodeDO = processNodeDOS.stream().filter(node -> Objects.equals(node.getNodeid(), nodeId) && Objects.equals(node.getCompleteuserid(), approvalUser) && node.getCompletetime() == null).findFirst().orElse(null);
            if (processNodeDO != null && BigDecimal.ONE.equals(processNodeDO.getCountersignflag())) {
                String subProcessBranch = processNodeDO.getSubprocessbranch();
                String nodeCode = plusApprovalInfoDO.getNodecode();
                String userName = plusApprovalInfoDO.getUsername();
                processNodeDO = processNodeDOS.stream().filter(node -> Objects.equals(node.getCompleteuserid(), userName) && Objects.equals(node.getNodecode(), nodeCode) && Objects.equals(node.getSubprocessbranch(), subProcessBranch)).findFirst().orElse(null);
            }
            return processNodeDO;
        }
        return null;
    }

    private String getUserName(String userId, String tenantName, Map<String, String> userNameCache) {
        if (userNameCache.containsKey(userId)) {
            return userNameCache.get(userId);
        }
        UserDO userDO = VaWorkFlowDataUtils.getOneUserData(tenantName, userId);
        String userName = userDO == null ? userId : userDO.getName();
        userNameCache.put(userId, userName);
        return userName;
    }

    private String getBizDefineName(String bizType, String bizDefine, Map<String, String> bizTitleCache) {
        if (bizTitleCache.containsKey(bizDefine)) {
            return bizTitleCache.get(bizDefine);
        }
        String bizDefineTitle = VaWorkFlowDataUtils.getBizDefineTitle(bizType, bizDefine);
        bizTitleCache.put(bizDefine, bizDefineTitle);
        return bizDefineTitle;
    }

    private String getStaffName(String staffCode, Map<String, BaseDataDO> staffCache) {
        if (staffCache.containsKey(staffCode)) {
            return (String)staffCache.get(staffCode).get((Object)"name");
        }
        BaseDataDO staff = VaWorkFlowDataUtils.getStaff(staffCode);
        if (staff == null) {
            staff = new BaseDataDO();
        }
        staffCache.put(staffCode, staff);
        return (String)staff.get((Object)"name");
    }

    private String getPositionName(String staffCode, Map<String, BaseDataDO> staffCache, Map<String, String> positionCache) {
        String positionCode = (String)staffCache.get(staffCode).get((Object)"positioncode");
        if (!StringUtils.hasText(positionCode)) {
            return null;
        }
        if (positionCache.containsKey(positionCode)) {
            return positionCache.get(positionCode);
        }
        PageVO<BaseDataDO> positionPageVO = VaWorkFlowDataUtils.getPositionByCode(positionCode);
        if (positionPageVO != null && positionPageVO.getTotal() > 0) {
            BaseDataDO positionInfo = (BaseDataDO)positionPageVO.getRows().get(0);
            String positionName = positionInfo.getName();
            positionCache.put(positionCode, positionName);
            return positionName;
        }
        return null;
    }

    @Override
    public BatchOperateResult withdrawPlusApproval(WithdrawPlusApprovalDTO withdrawPlusApprovalDTO) {
        List<BatchOperateResult.DetailMessage> successList = Collections.synchronizedList(new ArrayList());
        List<BatchOperateResult.DetailMessage> failedList = Collections.synchronizedList(new ArrayList());
        BatchOperateResult result = new BatchOperateResult(successList, failedList);
        List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList = withdrawPlusApprovalDTO.getWithdrawPlusApprovalVOList();
        if (CollectionUtils.isEmpty(withdrawPlusApprovalVOList)) {
            return result;
        }
        Map<String, List<WithdrawPlusApprovalVO>> withdrawPlusApprovalVOMap = withdrawPlusApprovalVOList.stream().collect(Collectors.groupingBy(WithdrawPlusApprovalVO::getProcessId));
        CountDownLatch countDownLatch = new CountDownLatch(withdrawPlusApprovalVOMap.size());
        String tenantName = ShiroUtil.getTenantName();
        BatchPlusContext context = new BatchPlusContext(successList, failedList, countDownLatch, tenantName, ShiroUtil.getUser());
        for (List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOS : withdrawPlusApprovalVOMap.values()) {
            String bizCode = withdrawPlusApprovalVOS.get(0).getBizCode();
            WithdrawPlusApprovalTask task = new WithdrawPlusApprovalTask(bizCode, withdrawPlusApprovalVOS, context);
            VaWorkflowThreadUtils.executor(task);
        }
        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return result;
    }

    private class WithdrawPlusApprovalTask
    implements Runnable {
        private final String bizCode;
        private final List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList;
        private final BatchPlusContext context;

        public WithdrawPlusApprovalTask(String bizCode, List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList, BatchPlusContext context) {
            this.bizCode = bizCode;
            this.withdrawPlusApprovalVOList = withdrawPlusApprovalVOList;
            this.context = context;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)this.context.loginUser);
                Map<String, List<WithdrawPlusApprovalVO>> withdrawPlusApprovalVOMap = this.withdrawPlusApprovalVOList.stream().collect(Collectors.groupingBy(WithdrawPlusApprovalVO::getNodeId));
                for (List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOS : withdrawPlusApprovalVOMap.values()) {
                    this.executeWithdrawPlusApproval(withdrawPlusApprovalVOS);
                }
            }
            catch (Exception e) {
                logger.error("\u64a4\u56de\u52a0\u7b7e\u5931\u8d25\uff0c\u4e1a\u52a1\u7f16\u53f7\uff1a{}", (Object)this.bizCode, (Object)e);
                this.context.failedList.add(new BatchOperateResult.DetailMessage(this.bizCode, e.getMessage()));
            }
            finally {
                this.context.countDownLatch.countDown();
                ShiroUtil.unbindUser();
            }
        }

        private void executeWithdrawPlusApproval(List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOS) {
            try {
                this.execute(withdrawPlusApprovalVOS, this.context.tenantName);
                this.context.successList.add(new BatchOperateResult.DetailMessage(this.bizCode, null));
            }
            catch (Exception e) {
                this.context.failedList.add(new BatchOperateResult.DetailMessage(this.bizCode, e.getMessage()));
            }
        }

        private void execute(List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList, String tenantName) {
            String lockKey;
            WithdrawPlusApprovalVO withdrawPlusApprovalVO = withdrawPlusApprovalVOList.get(0);
            Runnable task = this.getWithdrawTask(withdrawPlusApprovalVOList, withdrawPlusApprovalVO);
            R result = RedisLockUtil.execute((Runnable)task, (String)(lockKey = tenantName + WorkflowBatchPlusApprovalServiceImpl.PRE_KEY_PROCESS_INSTANCE_ID + withdrawPlusApprovalVO.getProcessId()), (long)10000L, (boolean)true);
            if (result.getCode() != 0) {
                throw new WorkflowException(result.getMsg());
            }
        }

        private Runnable getWithdrawTask(List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList, WithdrawPlusApprovalVO withdrawPlusApprovalVO) {
            return () -> {
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("PROCESSID", (Object)withdrawPlusApprovalVO.getProcessId());
                tenantDO.addExtInfo("TASKDEFINEKEY", (Object)withdrawPlusApprovalVO.getNodeCode());
                tenantDO.addExtInfo("TASKID", (Object)withdrawPlusApprovalVO.getNodeId());
                tenantDO.addExtInfo("COUNTERSIGNFLAG", (Object)withdrawPlusApprovalVO.getCounterSignFlag().intValue());
                tenantDO.addExtInfo("removeinfos", this.convertPlusApprovalDTO(withdrawPlusApprovalVOList));
                WorkflowBatchPlusApprovalServiceImpl.this.workflowPlusApprovalService.setPlusApproval(tenantDO);
            };
        }

        private List<PlusApprovalInfoDTO> convertPlusApprovalDTO(List<WithdrawPlusApprovalVO> withdrawPlusApprovalVOList) {
            ArrayList<PlusApprovalInfoDTO> result = new ArrayList<PlusApprovalInfoDTO>(withdrawPlusApprovalVOList.size());
            for (WithdrawPlusApprovalVO withdrawPlusApprovalVO : withdrawPlusApprovalVOList) {
                PlusApprovalInfoDTO plusApprovalInfoDTO = new PlusApprovalInfoDTO();
                plusApprovalInfoDTO.setProcessid(withdrawPlusApprovalVO.getProcessId());
                plusApprovalInfoDTO.setNodeid(withdrawPlusApprovalVO.getNodeId());
                plusApprovalInfoDTO.setNodecode(withdrawPlusApprovalVO.getNodeCode());
                plusApprovalInfoDTO.setApprovaluser(withdrawPlusApprovalVO.getPlusApprovalUserId());
                plusApprovalInfoDTO.setApprovalunitcode(withdrawPlusApprovalVO.getApprovalUserUnitCode());
                result.add(plusApprovalInfoDTO);
            }
            return result;
        }
    }

    private static class BatchPlusContext {
        final List<BatchOperateResult.DetailMessage> successList;
        final List<BatchOperateResult.DetailMessage> failedList;
        final CountDownLatch countDownLatch;
        final String tenantName;
        final WorkflowOptionDTO workflowOptionDTO;
        final UserLoginDTO loginUser;

        public BatchPlusContext(List<BatchOperateResult.DetailMessage> successList, List<BatchOperateResult.DetailMessage> failedList, CountDownLatch countDownLatch, String tenantName, WorkflowOptionDTO workflowOptionDTO, UserLoginDTO loginUser) {
            this.successList = successList;
            this.failedList = failedList;
            this.countDownLatch = countDownLatch;
            this.tenantName = tenantName;
            this.workflowOptionDTO = workflowOptionDTO;
            this.loginUser = loginUser;
        }

        public BatchPlusContext(List<BatchOperateResult.DetailMessage> successList, List<BatchOperateResult.DetailMessage> failedList, CountDownLatch countDownLatch, String tenantName, UserLoginDTO loginUser) {
            this.successList = successList;
            this.failedList = failedList;
            this.countDownLatch = countDownLatch;
            this.tenantName = tenantName;
            this.loginUser = loginUser;
            this.workflowOptionDTO = null;
        }
    }

    private class BatchPlusApprovalTask
    implements Runnable {
        private final List<TaskDTO> taskDTOList;
        private final List<PlusApprovalInfoDO> plusApprovalInfoDOList;
        private final BatchPlusContext context;

        public BatchPlusApprovalTask(List<TaskDTO> taskDTOList, List<PlusApprovalInfoDO> plusApprovalInfoDOList, BatchPlusContext context) {
            this.taskDTOList = taskDTOList;
            this.plusApprovalInfoDOList = plusApprovalInfoDOList;
            this.context = context;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)this.context.loginUser);
                String bizCode = this.taskDTOList.get(0).getBizCode();
                for (TaskDTO taskDTO : this.taskDTOList) {
                    try {
                        this.executePlusApproval(taskDTO, this.plusApprovalInfoDOList, this.context);
                        this.context.successList.add(new BatchOperateResult.DetailMessage(bizCode, null));
                    }
                    catch (Exception e) {
                        this.context.failedList.add(new BatchOperateResult.DetailMessage(bizCode, e.getMessage()));
                    }
                }
            }
            finally {
                this.context.countDownLatch.countDown();
                ShiroUtil.unbindUser();
            }
        }

        private void executePlusApproval(TaskDTO taskDTO, List<PlusApprovalInfoDO> plusApprovalInfoDOList, BatchPlusContext context) {
            String lockKey;
            if (this.cannotPlusApproval(taskDTO)) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.cannotplusapproval"));
            }
            if (taskDTO.getApprovalFlag() != null && taskDTO.getApprovalFlag() == 1) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notallowedtoplusapproval"));
            }
            Runnable task = this.getPlusApprovalTask(taskDTO, plusApprovalInfoDOList, context.workflowOptionDTO);
            R result = RedisLockUtil.execute((Runnable)task, (String)(lockKey = context.tenantName + WorkflowBatchPlusApprovalServiceImpl.PRE_KEY_PROCESS_INSTANCE_ID + taskDTO.getProcessId()), (long)10000L, (boolean)true);
            if (result.getCode() != 0) {
                throw new WorkflowException(result.getMsg());
            }
        }

        private boolean cannotPlusApproval(TaskDTO taskDTO) {
            WorkflowModelDefine define = (WorkflowModelDefine)WorkflowBatchPlusApprovalServiceImpl.this.modelDefineService.getDefine(taskDTO.getProcessDefineKey(), Long.valueOf(taskDTO.getProcessDefineVersion().longValue()));
            ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
            ArrayNode defineData = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
            String taskDefineKey = taskDTO.getTaskDefineKey();
            for (JsonNode jsonNode : defineData) {
                if (jsonNode.get("resourceId").asText().equals(taskDefineKey)) {
                    JsonNode plusConfig = jsonNode.get("properties").get("servicetaskclass");
                    return plusConfig == null || plusConfig.get("plusapproval") == null || !plusConfig.get("plusapproval").asBoolean();
                }
                JsonNode childShapes = jsonNode.get("childShapes");
                if (childShapes == null) continue;
                for (JsonNode childShape : childShapes) {
                    if (!childShape.get("resourceId").asText().equals(taskDefineKey)) continue;
                    JsonNode plusConfig = childShape.get("properties").get("servicetaskclass");
                    return plusConfig == null || plusConfig.get("plusapproval") == null || !plusConfig.get("plusapproval").asBoolean();
                }
            }
            return true;
        }

        private Runnable getPlusApprovalTask(TaskDTO taskDTO, List<PlusApprovalInfoDO> plusApprovalInfoDOList, WorkflowOptionDTO workflowOptionDTO) {
            return () -> {
                ProcessDTO processDTO = new ProcessDTO();
                processDTO.setId(taskDTO.getProcessId());
                ProcessDO processDO = WorkflowBatchPlusApprovalServiceImpl.this.vaWorkflowProcessService.get(processDTO);
                if (processDO == null) {
                    throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.stopprocessinfo"));
                }
                List<String> existedUserList = this.getExistedUserList(taskDTO);
                plusApprovalInfoDOList.removeIf(o -> existedUserList.contains(o.getApprovaluser()));
                String processDefineKey = taskDTO.getProcessDefineKey();
                long processDefineVersion = taskDTO.getProcessDefineVersion().longValue();
                String processId = taskDTO.getProcessId();
                WorkflowModel workflowModel = this.getModel(processDefineKey, processDefineVersion);
                WorkflowDTO workflowDTO = new WorkflowDTO();
                workflowDTO.setProcessInstanceId(processId);
                workflowDTO.setUniqueCode(processDefineKey);
                workflowDTO.setNodeCode(taskDTO.getTaskDefineKey());
                workflowDTO.setProcessDefineVersion(Long.valueOf(processDefineVersion));
                workflowDTO.setBizType((String)taskDTO.getBizType());
                workflowDTO.setBizDefine((String)taskDTO.getBizDefine());
                workflowDTO.setBizCode(taskDTO.getBizCode());
                workflowDTO.setTaskId(taskDTO.getTaskId());
                workflowDTO.setExtInfo(this.getExtInfoMap(taskDTO, plusApprovalInfoDOList, workflowOptionDTO));
                workflowDTO.setTraceId(Utils.getTraceId());
                workflowModel.plusApproval(workflowDTO);
            };
        }

        private List<String> getExistedUserList(TaskDTO taskDTO) {
            ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setBizcode(taskDTO.getBizCode());
            processNodeDTO.setNodecode(taskDTO.getTaskDefineKey());
            processNodeDTO.setSubprocessbranch(taskDTO.getSubProcessBranch());
            List processNodeDOS = WorkflowBatchPlusApprovalServiceImpl.this.workflowProcessNodeService.listProcessNode(processNodeDTO);
            return processNodeDOS.stream().map(ProcessNodeDO::getCompleteuserid).collect(Collectors.toList());
        }

        private WorkflowModel getModel(String uniqueCode, Long processDefineVersion) {
            WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)WorkflowBatchPlusApprovalServiceImpl.this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
            return (WorkflowModel)WorkflowBatchPlusApprovalServiceImpl.this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        }

        private Map<String, Object> getExtInfoMap(TaskDTO taskDTO, List<PlusApprovalInfoDO> plusApprovalInfoDOList, WorkflowOptionDTO workflowOptionDTO) {
            boolean approvalFlag = String.valueOf(1).equals(workflowOptionDTO.getPlusApprovalFlag());
            boolean reserveTodoFlag = "1".equals(workflowOptionDTO.getWf1003());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("PlusInfos", plusApprovalInfoDOList);
            map.put("approvalFlag", approvalFlag);
            map.put("reserveTodoFlag", reserveTodoFlag);
            map.put("COUNTERSIGNFLAG", taskDTO.getCounterSignFlag());
            map.put("TASKID", taskDTO.getTaskId());
            map.put("TASKDEFINEKEY", taskDTO.getTaskDefineKey());
            map.put("PROCESSID", taskDTO.getProcessId());
            return map;
        }
    }
}

