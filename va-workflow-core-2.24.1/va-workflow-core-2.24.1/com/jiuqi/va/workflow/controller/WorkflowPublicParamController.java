/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.workflow.service.WorkflowPublicParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/param/public"})
public class WorkflowPublicParamController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowPublicParamController.class);
    @Autowired
    private WorkflowPublicParamService workflowPublicParamService;

    @PostMapping(value={"/add"})
    public R addPublicParam(@RequestBody WorkflowPublicParamDTO publicParamDTO) {
        if (!(StringUtils.hasText(publicParamDTO.getParamname()) && StringUtils.hasText(publicParamDTO.getParamtitle()) && StringUtils.hasText(publicParamDTO.getParamtype()))) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowPublicParamService.addPublicParam(publicParamDTO);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/update"})
    public R updatePublicParam(@RequestBody WorkflowPublicParamDTO publicParamDTO) {
        UUID id = publicParamDTO.getId();
        if (!(id != null && StringUtils.hasText(id.toString()) && StringUtils.hasText(publicParamDTO.getParamname()) && StringUtils.hasText(publicParamDTO.getParamtype()))) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowPublicParamService.updatePublicParam(publicParamDTO);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/remove"})
    public R removePublicParam(@RequestBody WorkflowPublicParamDTO publicParamDTO) {
        UUID id = publicParamDTO.getId();
        if (id == null || !StringUtils.hasText(id.toString()) || !StringUtils.hasText(publicParamDTO.getParamname())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowPublicParamService.removePublicParam(publicParamDTO);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/updown"})
    public R updown(@RequestBody WorkflowPublicParamDTO publicParamDTO) {
        UUID id = publicParamDTO.getId();
        String movetype = publicParamDTO.getMovetype();
        if (!StringUtils.hasText(movetype) || id == null || !StringUtils.hasText(id.toString())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowPublicParamService.updown(publicParamDTO);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/list"})
    public R list(@RequestBody WorkflowPublicParamDTO publicParamDTO) {
        List<WorkflowPublicParamDTO> publicParamDTOS = this.workflowPublicParamService.list(publicParamDTO);
        R r = new R();
        r.put("data", publicParamDTOS);
        return r;
    }

    @PostMapping(value={"/check"})
    public R check(@RequestBody WorkflowPublicParamDTO publicParamDTO) {
        if (!StringUtils.hasText(publicParamDTO.getParamname())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            boolean checkFlag = this.workflowPublicParamService.check(publicParamDTO);
            R ok = R.ok();
            return ok.put("checkFlag", (Object)checkFlag);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

