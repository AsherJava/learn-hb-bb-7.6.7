/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO;
import com.jiuqi.va.workflow.service.WorkflowCommonUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/common/user"})
public class WorkflowCommonUserController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowCommonUserController.class);
    @Autowired
    private WorkflowCommonUserService workflowSelectApproverService;

    @RequestMapping(value={"/add"})
    public R add(@RequestBody WorkflowCommonUserDTO workflowCommonUserDTO) {
        try {
            this.workflowSelectApproverService.add(workflowCommonUserDTO);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @RequestMapping(value={"/delete"})
    public R delete(@RequestBody WorkflowCommonUserDTO workflowCommonUserDTO) {
        try {
            this.workflowSelectApproverService.delete(workflowCommonUserDTO);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

