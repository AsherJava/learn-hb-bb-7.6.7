/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO;
import com.jiuqi.va.workflow.service.WorkflowProcessReviewService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/process/review"})
public class WorkflowProcessReviewController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowProcessReviewController.class);
    @Autowired
    private WorkflowProcessReviewService workflowProcessReviewService;

    @PostMapping(value={"/getRejectInfos"})
    R getRejectInfos(@RequestBody WorkflowProcessReviewDTO workflowProcessReviewDTO) {
        if (workflowProcessReviewDTO.getBizcode() == null) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":bizcode"));
        }
        List<WorkflowProcessReviewDTO> reviewDTOList = this.workflowProcessReviewService.getRejectInfos(workflowProcessReviewDTO);
        return R.ok().put("rejectInfos", reviewDTOList);
    }

    @PostMapping(value={"/syncReviews"})
    R syncReviews(@RequestBody List<WorkflowProcessReviewDTO> reviewDTOS) {
        return this.workflowProcessReviewService.syncReviews(reviewDTOS);
    }

    @PostMapping(value={"/listReviews"})
    R listReviews(@RequestBody WorkflowProcessReviewDTO workflowProcessReviewDTO) {
        if (workflowProcessReviewDTO.getBizcode() == null) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":bizcode"));
        }
        List<WorkflowProcessReviewDTO> reviewDTOS = this.workflowProcessReviewService.listReviews(workflowProcessReviewDTO);
        return R.ok().put("reviews", reviewDTOS);
    }

    @PostMapping(value={"/listRejects"})
    R listRejects(@RequestBody WorkflowProcessReviewDTO workflowProcessReviewDTO) {
        if (workflowProcessReviewDTO.getBizcode() == null) {
            return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ":bizcode"));
        }
        List<WorkflowProcessReviewDTO> reviewDTOS = this.workflowProcessReviewService.listRejects(workflowProcessReviewDTO);
        return R.ok().put("listRejects", reviewDTOS);
    }
}

