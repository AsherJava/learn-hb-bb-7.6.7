/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionDTO;
import com.jiuqi.va.workflow.domain.detection.WorkflowDetectionVO;
import com.jiuqi.va.workflow.service.WorkflowDetectionService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/process/detection"})
public class WorkflowDetectionController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowDetectionController.class);
    @Autowired
    private WorkflowDetectionService workflowDetectionService;

    @PostMapping(value={"/param/extract"})
    public R detectionParamExtract(@RequestBody WorkflowDetectionDTO workflowDetectionDTO) {
        try {
            WorkflowDetectionVO workflowDetectionVO = this.workflowDetectionService.detectionParamExtract(workflowDetectionDTO);
            R r = new R();
            r.put("orgFlag", (Object)workflowDetectionVO.isOrgFlag());
            r.put("paramInfo", workflowDetectionVO.getParamInfoList());
            r.put("orgCodes", workflowDetectionVO.getOrgCodes());
            return r;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/execute"})
    public R detectionExecute(@RequestBody WorkflowDetectionDTO detectionDTO) {
        if (!StringUtils.hasText(detectionDTO.getWorkflowdefinekey()) || !StringUtils.hasText(detectionDTO.getBizdefine())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            WorkflowDetectionVO workflowDetectionVO = this.workflowDetectionService.detectionExecute(detectionDTO);
            R ok = R.ok();
            ok.put("detectionResult", workflowDetectionVO.getDetectionResult());
            return ok;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/data"})
    public R detectionData(@RequestBody WorkflowDetectionDTO detectionDTO) {
        if (!StringUtils.hasText(detectionDTO.getWorkflowdefinekey()) || !StringUtils.hasText(detectionDTO.getBizdefine())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            WorkflowDetectionVO detectionVO = this.workflowDetectionService.getDetectionData(detectionDTO);
            R ok = R.ok();
            ok.put("detectionData", detectionVO.getDetectionData());
            ok.put("detectionResult", detectionVO.getDetectionResult());
            return ok;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

