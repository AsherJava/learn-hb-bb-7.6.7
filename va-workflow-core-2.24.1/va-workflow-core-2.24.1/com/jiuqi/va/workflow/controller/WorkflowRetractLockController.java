/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/retract/lock"})
public class WorkflowRetractLockController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowRetractLockController.class);
    @Autowired
    private WorkflowRetractLockService workflowRetractLockService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;

    @PostMapping(value={"/list"})
    public R list(@RequestBody WorkflowRetractLockDTO dto) {
        try {
            List list = this.workflowRetractLockService.list(dto);
            return R.ok().put("data", (Object)list);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/add"})
    public R lockRetract(@RequestBody WorkflowRetractLockDTO retractLockDTO) {
        String bizCode = retractLockDTO.getBizcode();
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        String lockKey = processDO == null ? retractLockDTO.getTenantName() + ":SUBMIT:" + bizCode : retractLockDTO.getTenantName() + "processInstanceId:" + processDO.getId();
        R r = R.ok();
        Runnable lockRetractRunnable = () -> {
            try {
                this.workflowRetractLockService.add(retractLockDTO);
            }
            catch (Exception e) {
                logger.error("{}\u6dfb\u52a0\u53d6\u56de\u9501\u5b9a\u5931\u8d25\uff1a{}", bizCode, e.getMessage(), e);
                r.setMsg(1, e.getMessage());
            }
        };
        R redisResult = R.ok();
        RedisLockUtil.execute((Runnable)lockRetractRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() == 0) {
            return r;
        }
        return redisResult;
    }

    @PostMapping(value={"/delete"})
    public R delete(@RequestBody WorkflowRetractLockDTO dto) {
        try {
            this.workflowRetractLockService.delete(dto);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

