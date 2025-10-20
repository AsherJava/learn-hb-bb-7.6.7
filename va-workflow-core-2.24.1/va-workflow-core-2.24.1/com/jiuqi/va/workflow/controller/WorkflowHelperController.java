/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow"})
public class WorkflowHelperController {
    @Autowired
    private WorkflowHelperService workflowHelperService;

    @PostMapping(value={"/listNextApproverInfo"})
    public R listNextApproverInfo(@RequestBody TenantDO tenantDO) {
        R ok = R.ok();
        Map<String, Map<String, String>> data = this.workflowHelperService.listNextApproveInfo(tenantDO);
        ok.put("data", data);
        return ok;
    }
}

