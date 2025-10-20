/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.workflow.service.WorkflowCommentService;
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
@RequestMapping(value={"/workflow"})
public class WorkflowCommentController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowCommentController.class);
    @Autowired
    private WorkflowCommentService workflowCommentService;

    @PostMapping(value={"/comments", "/comment/list"})
    public R getComments(@RequestBody(required=false) WorkflowCommentDTO workflowCommentDTO) {
        if (workflowCommentDTO == null) {
            workflowCommentDTO = new WorkflowCommentDTO();
        }
        if (!StringUtils.hasText(workflowCommentDTO.getUsername())) {
            workflowCommentDTO.setUsername(ShiroUtil.getUser().getId());
        }
        List<WorkflowCommentDO> comments = this.workflowCommentService.getCommentByUsername(workflowCommentDTO);
        R r = new R();
        r.put("comments", comments);
        return r;
    }

    @PostMapping(value={"/comment/move"})
    public R move(@RequestBody WorkflowCommentDTO workflowCommentDTO) {
        this.workflowCommentService.move(workflowCommentDTO);
        return R.ok();
    }

    @PostMapping(value={"/comment/delete"})
    public R deleteComment(@RequestBody WorkflowCommentDTO workflowCommentDTO) {
        if (!StringUtils.hasText(workflowCommentDTO.getId())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        this.workflowCommentService.deleteComment(workflowCommentDTO);
        return R.ok();
    }

    @PostMapping(value={"/comment/add"})
    public R add(@RequestBody WorkflowCommentDTO workflowCommentDTO) {
        R r;
        try {
            this.workflowCommentService.add(workflowCommentDTO);
            r = R.ok((String)"\u6dfb\u52a0\u5e38\u7528\u610f\u89c1\u6210\u529f");
        }
        catch (Exception e) {
            log.error("\u6dfb\u52a0\u5e38\u7528\u610f\u89c1\u5931\u8d25", e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }
}

