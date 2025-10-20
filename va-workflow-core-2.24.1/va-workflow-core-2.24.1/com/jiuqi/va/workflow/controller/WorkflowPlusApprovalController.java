/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.batch.operate.BatchOperateResult;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalUserDO;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.domain.plus.approval.BatchPlusApprovalDTO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalDTO;
import com.jiuqi.va.workflow.domain.plus.approval.WithdrawPlusApprovalVO;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowBatchPlusApprovalService;
import com.jiuqi.va.workflow.utils.LogUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/plusapproval"})
public class WorkflowPlusApprovalController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowPlusApprovalController.class);
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowBatchPlusApprovalService workflowBatchPlusApprovalService;
    private final String PRE_KEY = "processInstanceId:";
    @Value(value="${jiuqi.va.staff-basedata.enableposts:false}")
    private boolean staffPartTimeJobFlag;

    @PostMapping(value={"/nouser"})
    public R getNoUser(@RequestBody PlusApprovalInfoDTO info) {
        List list;
        try {
            list = this.workflowPlusApprovalService.getNoUser(info);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        R r = new R();
        r.put("data", (Object)list);
        return r;
    }

    @PostMapping(value={"/stopuser"})
    public List<String> getStopUser(@RequestBody TenantDO tenantDO) {
        return this.workflowPlusApprovalService.getStopUser(tenantDO);
    }

    @PostMapping(value={"/batch/nouser"})
    public List<String> batchGetNoUser(@RequestBody List<Map<String, Object>> params) {
        String userid = ShiroUtil.getUser().getId();
        ArrayList<String> nodeList = new ArrayList<String>();
        HashMap uniqueNoUserMap = new HashMap();
        HashMap<String, Boolean> uniquePlusMap = new HashMap<String, Boolean>();
        for (Map<String, Object> param : params) {
            String processid = param.get("PROCESSID").toString();
            String nodeId = param.get("TASKDEFINEKEY").toString();
            Boolean isAuditer = (Boolean)uniquePlusMap.get(processid + nodeId);
            if (isAuditer == null) {
                PlusApprovalInfoDO infoDO = new PlusApprovalInfoDO();
                infoDO.setProcessid(processid);
                infoDO.setNodecode(nodeId);
                ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                processNodeDTO.setNodecode(nodeId);
                processNodeDTO.setProcessid(processid);
                List listProcessNode = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
                boolean flag = true;
                for (ProcessNodeDO process : listProcessNode) {
                    if (!userid.equals(process.getCompleteuserid()) || process.getCompleteusertype() == null || !process.getCompleteusertype().equals(BigDecimal.ONE)) continue;
                    flag = false;
                    uniquePlusMap.put(processid + nodeId, flag);
                    break;
                }
                isAuditer = flag;
            }
            if (!isAuditer.booleanValue()) continue;
            Boolean noUser = (Boolean)uniqueNoUserMap.get(processid + nodeId);
            if (noUser == null) {
                PlusApprovalInfoDTO infoDO = new PlusApprovalInfoDTO();
                infoDO.setProcessid(processid);
                infoDO.setNodecode(nodeId);
                List noUserList = this.workflowPlusApprovalService.getNoUser(infoDO);
                noUser = !noUserList.isEmpty();
            }
            if (!noUser.booleanValue()) continue;
            nodeList.add((String)param.get("BIZCODE"));
        }
        return nodeList;
    }

    @GetMapping(value={"/getStaffPartTimeJobFlag"})
    public R getStaffPartTimeJobFlag() {
        return R.ok().put("staffPartTimeJobFlag", (Object)this.staffPartTimeJobFlag);
    }

    @PostMapping(value={"/user/set"})
    public R setPlusApprovalUser(@RequestBody TenantDO tenantDO) {
        String traceId = GUID.newGUID();
        Utils.setTraceId((String)traceId);
        tenantDO.setTraceId(traceId);
        R r = R.ok();
        Runnable setPlusApprovalRunnable = () -> {
            try {
                this.workflowPlusApprovalService.setPlusApproval(tenantDO);
            }
            catch (IllegalArgumentException e) {
                r.setMsg(1, e.getMessage());
            }
            catch (WorkflowException e) {
                r.setMsg(1, VaWorkFlowI18nUtils.convertError(e.getMessage()));
            }
            catch (Exception e) {
                logger.error("\u52a0\u7b7e\u5f02\u5e38", e);
                r.setMsg(1, R.error().getMsg());
            }
            finally {
                VaContext.removeVaWorkflowContext();
            }
        };
        R redisResult = R.ok();
        String processInstanceId = (String)tenantDO.getExtInfo("PROCESSID");
        String lockKey = tenantDO.getTenantName() + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)setPlusApprovalRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    @PostMapping(value={"/commonuser/add"})
    public R addCommonUser(@RequestBody List<PlusApprovalInfoDO> commonUserList) {
        try {
            this.workflowPlusApprovalService.addCommonUser(commonUserList);
        }
        catch (Exception e) {
            logger.error("\u5e38\u7528\u52a0\u7b7e\u4eba\u6dfb\u52a0\u5931\u8d25\uff1a", e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/user/get"})
    public R getPlusApprovalUser(@RequestBody PlusApprovalInfoDTO info) {
        List list;
        String processId = info.getProcessid();
        if (!StringUtils.hasText(processId)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            list = this.workflowPlusApprovalService.getPlusApprovalUser(info);
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
        R r = new R();
        r.put("data", (Object)list);
        return r;
    }

    @PostMapping(value={"/commonuser/delete"})
    public R deletePlusApprovalUserCommonUser(@RequestBody PlusApprovalUserDO user) {
        try {
            this.workflowPlusApprovalService.deletePlusApprovalUserCommonUser(user);
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/commonuser/get"})
    public R getPlusApprovalUserCommonUser(@RequestBody PlusApprovalUserDO user) {
        List list = null;
        try {
            list = this.workflowPlusApprovalService.getPlusApprovalUserCommonUser(user);
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
        R r = new R();
        r.put("data", (Object)list);
        return r;
    }

    @PostMapping(value={"/list"})
    public List<PlusApprovalInfoDO> listPlusApprovalInfoDto(@RequestBody PlusApprovalInfoDO plusApprovalInfoDO) {
        return this.workflowPlusApprovalService.listPlusApprovalInfoDto(plusApprovalInfoDO);
    }

    @PostMapping(value={"/selectByCondition"})
    public List<PlusApprovalInfoDO> selectByConditionAndNodeIds(@RequestBody PlusApprovalInfoDTO info) {
        return this.workflowPlusApprovalService.selectByConditionAndNodeIds(info);
    }

    @PostMapping(value={"/getPlusApprovalComment"})
    public R getPlusApprovalComment(@RequestBody WorkflowDTO workflowDTO) {
        List approvalComment = this.workflowPlusApprovalService.getPlusApprovalComment(workflowDTO);
        R r = new R();
        r.put("plusApprovalComments", (Object)approvalComment);
        return r;
    }

    @PostMapping(value={"/specify-node/list"})
    public R listSpecifyNodeApprovalInfo(@RequestBody PlusApprovalInfoDTO infoDTO) {
        if (!StringUtils.hasText(infoDTO.getNodecode()) || !StringUtils.hasText(infoDTO.getNodeid())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            List approvalInfos = this.workflowPlusApprovalService.listSpecifyNodeApprovalInfo(infoDTO);
            return R.ok().put("approvalInfos", (Object)approvalInfos);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/batch-update"})
    public R batchUpdateByIds(@RequestBody PlusApprovalInfoDTO plusApprovalInfoDTO) {
        List infoDOs = plusApprovalInfoDTO.getPlusApprovalInfoDOs();
        if (CollectionUtils.isEmpty(infoDOs)) {
            return R.ok();
        }
        try {
            this.workflowPlusApprovalService.updatePlusApprovalInfoList(infoDOs);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/user/batch/set"})
    public R batchSetPlusApprovalUser(@RequestBody BatchPlusApprovalDTO batchPlusApprovalDTO) {
        try {
            VaWorkflowUtils.setTractId(batchPlusApprovalDTO);
            LogUtils.addLog("\u5de5\u4f5c\u6d41", "\u6279\u91cf\u52a0\u7b7e", null, null, null, ShiroUtil.getTenantName());
            BatchOperateResult result = this.workflowBatchPlusApprovalService.batchPlusApproval(batchPlusApprovalDTO);
            return R.ok().put("data", (Object)result);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @GetMapping(value={"/get-unapproved-info"})
    public R getUnapprovedPlusApprovalInfo() {
        try {
            List<WithdrawPlusApprovalVO> plusApprovalInfoDOS = this.workflowBatchPlusApprovalService.getUnapprovedPlusApprovalInfo();
            return R.ok().put("data", plusApprovalInfoDOS);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/withdraw"})
    public R withdrawPlusApproval(@RequestBody WithdrawPlusApprovalDTO withdrawPlusApprovalDTO) {
        try {
            VaWorkflowUtils.setTractId(withdrawPlusApprovalDTO);
            LogUtils.addLog("\u5de5\u4f5c\u6d41", "\u64a4\u56de\u52a0\u7b7e", null, null, null, ShiroUtil.getTenantName());
            BatchOperateResult result = this.workflowBatchPlusApprovalService.withdrawPlusApproval(withdrawPlusApprovalDTO);
            return R.ok().put("data", (Object)result);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

