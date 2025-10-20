/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelHelper;
import com.jiuqi.va.workflow.service.WorkflowCommentService;
import com.jiuqi.va.workflow.service.WorkflowProcessInstanceService;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.LogUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow"})
public class WorkflowController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowController.class);
    public static final String PRE_KEY_PROCESSINSTANCEID = "processInstanceId:";
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowProcessInstanceService workflowProcessInstanceService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowCommentService workflowCommentService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;
    @Autowired
    private WorkflowModelHelper workflowModelHelper;
    @Autowired
    private WorkflowProcessNodeExtendService nodeExtendService;
    @Autowired
    private WorkflowNextNodeService workflowNextNodeService;

    @PostMapping(value={"/process/start"})
    public R startProcess(@RequestBody WorkflowDTO workflowDTO) {
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.businesscodecheck"));
        }
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        R r = R.ok();
        Runnable startProcessRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(workflowDTO.getUniqueCode(), null);
                R rs = workflowModel.startProcess(workflowDTO);
                r.putAll((Map)rs);
            }
            catch (WorkflowException e) {
                log.error("{}\u63d0\u4ea4\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.convertError(e.getMessage()));
            }
            catch (Exception e) {
                log.error("{}\u63d0\u4ea4\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.getInfo("va.workflow.submitfailed"));
            }
        };
        String lockKey = workflowDTO.getTenantName() + ":SUBMIT:" + bizCode;
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)startProcessRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    @PostMapping(value={"/task/complete"})
    public R completeTask(@RequestBody WorkflowDTO workflowDTO) {
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String lockKey = workflowDTO.getTenantName() + PRE_KEY_PROCESSINSTANCEID + processInstanceId;
        R r = R.ok();
        Runnable completeTaskRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
                R rs = workflowModel.completeTask(workflowDTO);
                r.putAll((Map)rs);
                if (workflowDTO.isCommonComment() && StringUtils.hasText(workflowDTO.getApprovalComment())) {
                    try {
                        WorkflowCommentDTO workflowCommentDTO = new WorkflowCommentDTO();
                        workflowCommentDTO.setUsername(ShiroUtil.getUser().getId());
                        workflowCommentDTO.setComment(workflowDTO.getApprovalComment());
                        this.workflowCommentService.add(workflowCommentDTO);
                    }
                    catch (Exception e) {
                        log.error("\u5e38\u7528\u610f\u89c1\u6dfb\u52a0\u5931\u8d25", e);
                    }
                }
            }
            catch (WorkflowException e) {
                log.error("{}\u5ba1\u6279\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.convertError(e.getMessage()));
            }
            catch (Exception e) {
                log.error("{}\u5ba1\u6279\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.getInfo("va.workflow.completefailed"));
            }
        };
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)completeTaskRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    @PostMapping(value={"/process/retract"})
    public R retractProcess(@RequestBody WorkflowDTO workflowDTO) {
        LogUtils.addLog("\u5de5\u4f5c\u6d41", "\u53d6\u56de", workflowDTO.getBizDefine() == null ? null : workflowDTO.getBizDefine(), workflowDTO.getBizCode() == null ? null : workflowDTO.getBizCode(), null, ShiroUtil.getTenantName());
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.businesscodecheck"));
        }
        if (this.workflowModelHelper.isRetractReject(workflowDTO)) {
            return this.retractReject(workflowDTO);
        }
        return this.retract(workflowDTO);
    }

    private R retract(WorkflowDTO workflowDTO) {
        String bizCode = workflowDTO.getBizCode();
        ProcessDO processDO = (ProcessDO)workflowDTO.getExtInfo("processDO");
        if (processDO == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
        }
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        String processInstanceId = processDO.getId();
        R r = R.ok();
        Runnable retractProcessRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
                workflowDTO.setProcessInstanceId(processInstanceId);
                R rs = workflowModel.retractProcess(workflowDTO);
                r.putAll((Map)rs);
            }
            catch (WorkflowException e) {
                log.error("{}\u53d6\u56de\u5931\u8d25", (Object)bizCode, (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.convertError(e.getMessage()));
            }
        };
        R redisResult = R.ok();
        String lockKey = workflowDTO.getTenantName() + PRE_KEY_PROCESSINSTANCEID + processInstanceId;
        RedisLockUtil.execute((Runnable)retractProcessRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    private R retractReject(WorkflowDTO workflowDTO) {
        String lockKey;
        String processInstanceId;
        long processDefineVersion;
        String defineKey;
        String bizCode = workflowDTO.getBizCode();
        ProcessDO processDO = (ProcessDO)workflowDTO.getExtInfo("processDO");
        if (processDO == null) {
            ProcessHistoryDO historyDO = (ProcessHistoryDO)workflowDTO.getExtInfo("processHistoryDO");
            defineKey = historyDO.getDefinekey();
            processDefineVersion = historyDO.getDefineversion().longValue();
            processInstanceId = historyDO.getId();
            lockKey = workflowDTO.getTenantName() + ":SUBMIT:" + bizCode;
        } else {
            processInstanceId = processDO.getId();
            processDefineVersion = processDO.getDefineversion().longValue();
            defineKey = processDO.getDefinekey();
            lockKey = workflowDTO.getTenantName() + PRE_KEY_PROCESSINSTANCEID + processInstanceId;
        }
        Assert.hasText(defineKey, VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "workflowDefineKey");
        Assert.hasText(processInstanceId, VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "processInstanceId");
        workflowDTO.setUniqueCode(defineKey);
        workflowDTO.setProcessInstanceId(processInstanceId);
        workflowDTO.setProcessDefineVersion(Long.valueOf(processDefineVersion));
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        R r = R.ok();
        Runnable retractProcessRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(defineKey, processDefineVersion);
                R rs = workflowModel.retractRejectProcess(workflowDTO);
                r.putAll((Map)rs);
            }
            catch (WorkflowException e) {
                log.error("{}\u53d6\u56de\u5931\u8d25", (Object)bizCode, (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.convertError(e.getMessage()));
            }
        };
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)retractProcessRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    @PostMapping(value={"/process/reopen/lastnode"})
    public R reopenLastNode(@RequestBody WorkflowDTO workflowDTO) {
        String lockKey;
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        R r = R.ok();
        String bizCode = workflowDTO.getBizCode();
        String subProcessBranch = workflowDTO.getSubProcessBranch();
        String PRE_KEY_BIZCODE = "bizCode:";
        if (StringUtils.hasText(subProcessBranch)) {
            String PRE_KEY_SUBPROCESSBRANCH = "subProcessBranch:";
            lockKey = workflowDTO.getTenantName() + PRE_KEY_BIZCODE + bizCode + PRE_KEY_SUBPROCESSBRANCH + subProcessBranch;
        } else {
            lockKey = workflowDTO.getTenantName() + PRE_KEY_BIZCODE + bizCode;
        }
        Runnable reopenLastNodeRunnable = () -> {
            try {
                WorkflowModel workflowModel = this.workflowParamService.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
                R rs = workflowModel.reopenLastNode(workflowDTO);
                r.putAll((Map)rs);
            }
            catch (WorkflowException e) {
                log.error("{}\u5ba1\u6279\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.convertError(e.getMessage()));
            }
            catch (Exception e) {
                log.error("{}\u5ba1\u6279\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                r.setMsg(1, VaWorkFlowI18nUtils.getInfo("va.workflow.completefailed"));
            }
        };
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)reopenLastNodeRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    @PostMapping(value={"/process/retract/check"})
    public R checkRetract(@RequestBody WorkflowDTO workflowDTO) {
        log.info("\u7528\u6237\uff1a{}\uff0c\u6267\u884c\u53d6\u56de\u524d\u6821\u9a8c\uff0c\u4e1a\u52a1\u6807\u8bc6\uff1a{}", (Object)ShiroUtil.getUser().getId(), (Object)workflowDTO.getBizCode());
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.businesscodecheck"));
        }
        VaWorkflowUtils.setTractId((TenantDO)workflowDTO);
        if (this.workflowModelHelper.isRetractReject(workflowDTO)) {
            try {
                this.workflowModelHelper.checkRetractReject(workflowDTO, null);
            }
            catch (Exception e) {
                return R.error((String)e.getMessage());
            }
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
        }
        String processInstanceId = processDO.getId();
        try {
            WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
            workflowDTO.setProcessInstanceId(processInstanceId);
            workflowModel.checkRetract(workflowDTO);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/final/approver/get"})
    public R getFinalApprover(@RequestBody TenantDO tenantDO) {
        if (tenantDO.getExtInfo("bizCode") == null || "".equals(tenantDO.getExtInfo("bizCode"))) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String bizCode = tenantDO.getExtInfo("bizCode").toString();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO != null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.businessunderapproval"));
        }
        List processHistoryDOList = this.vaWorkflowProcessService.listHistory(processDTO);
        if (processHistoryDOList.isEmpty()) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.norecorder"));
        }
        ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOList.get(processHistoryDOList.size() - 1);
        if (new BigDecimal(WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue()).compareTo(processHistoryDO.getEndstatus()) != 0) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.notpass"));
        }
        String processId = processHistoryDO.getId();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processId);
        processNodeDTO.setSyscode("WORKFLOW");
        processNodeDTO.setSort("completetime");
        processNodeDTO.setOrder("asc");
        List processNodeDOs = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        String completeUserId = null;
        for (int i = processNodeDOs.size() - 1; i >= 0 && !StringUtils.hasText(completeUserId = ((ProcessNodeDO)processNodeDOs.get(i)).getCompleteuserid()); --i) {
        }
        R r = R.ok();
        r.put("completeUserId", (Object)completeUserId);
        return r;
    }

    @PostMapping(value={"/process/forecast"})
    public R processForecast(@RequestBody WorkflowDTO workflowDTO) {
        return this.workflowProcessInstanceService.processForecast(workflowDTO);
    }

    @PostMapping(value={"/process/history"})
    public R processHistory(@RequestBody ProcessDTO processDTO) {
        if (!StringUtils.hasText(processDTO.getBizcode()) && !StringUtils.hasText(processDTO.getId())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processList = this.vaWorkflowProcessService.listHistory(processDTO);
        R r = new R();
        r.put("processList", (Object)processList);
        return r;
    }

    @PostMapping(value={"/nextnode/list"})
    public R listNextNode(@RequestBody WorkflowDTO workflowDTO) {
        return this.workflowNextNodeService.listNextNode(workflowDTO);
    }

    @PostMapping(value={"/nextnode/approver"})
    public R nextnodeApprover(@RequestBody WorkflowDTO workflowDTO) {
        return this.workflowNextNodeService.getNextNodeApprover(workflowDTO);
    }

    @PostMapping(value={"/process/billInfo"})
    public R billProcessInfo(@RequestBody TenantDO tenantDO) {
        return this.workflowProcessInstanceService.billProcessInfo(tenantDO);
    }

    @PostMapping(value={"/processInfo/get"})
    public R getProcessInfo(@RequestBody TenantDO tenantDO) {
        return this.workflowProcessInstanceService.getProcessInfo(tenantDO);
    }

    @PostMapping(value={"/process/check/reject/submitter"})
    public R checkRejectToSubmitter(@RequestBody WorkflowDTO workflowDTO) {
        try {
            boolean result = this.workflowProcessRejectNodeService.checkRejectToSubmitter(workflowDTO);
            R r = new R();
            r.put("rejectToSubmitter", (Object)result);
            return r;
        }
        catch (Exception e) {
            log.error("\u6821\u9a8c\u662f\u5426\u9a73\u56de\u5230\u63d0\u4ea4\u4eba\u5931\u8d25", e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/retract/node/list"})
    public R retractNodeList(@RequestBody WorkflowDTO workflowDTO) {
        if (!StringUtils.hasText(workflowDTO.getBizCode())) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        }
        if (!StringUtils.hasText(workflowDTO.getProcessInstanceId())) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "processId"));
        }
        if (!StringUtils.hasText(workflowDTO.getTaskId())) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "taskId"));
        }
        log.info("\u6267\u884c\u83b7\u53d6\u8fdb\u884c\u53d6\u56de\u64cd\u4f5c\u7684\u8282\u70b9\uff0cbizCode\uff1a{}\uff0ctaskId\uff1a{}", (Object)workflowDTO.getBizCode(), (Object)workflowDTO.getTaskId());
        try {
            List retractNodeList = this.nodeExtendService.retractNodeList(workflowDTO);
            return R.ok().put("nodes", (Object)retractNodeList);
        }
        catch (WorkflowException e) {
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error();
        }
    }
}

