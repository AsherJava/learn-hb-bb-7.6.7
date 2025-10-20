/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO
 *  org.apache.shiro.util.StringUtils
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO;
import com.jiuqi.va.workflow.service.WorkflowBusinessSchemeService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/business/scheme"})
public class WorkflowBusinessSchemeController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowBusinessSchemeController.class);
    @Autowired
    private WorkflowBusinessSchemeService workflowBusinessSchemeService;

    @PostMapping(value={"/list"})
    public R list(@RequestBody WorkflowBusinessSchemeDTO schemeDTO) {
        List<WorkflowBusinessSchemeDTO> list = this.workflowBusinessSchemeService.list(schemeDTO);
        return R.ok().put("data", list);
    }

    @PostMapping(value={"/get"})
    public R get(@RequestBody WorkflowBusinessSchemeDTO schemeDTO) {
        if (!StringUtils.hasText((String)schemeDTO.getId())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return R.ok().put("data", this.workflowBusinessSchemeService.get(schemeDTO));
    }

    @PostMapping(value={"/add"})
    public R add(@RequestBody WorkflowBusinessSchemeDTO schemeDTO) {
        if (!(StringUtils.hasText((String)schemeDTO.getSchemetype()) && StringUtils.hasText((String)schemeDTO.getName()) && StringUtils.hasText((String)schemeDTO.getTitle()))) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowBusinessSchemeService.add(schemeDTO);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/update"})
    public R update(@RequestBody WorkflowBusinessSchemeDTO schemeDTO) {
        if (!StringUtils.hasText((String)schemeDTO.getId())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowBusinessSchemeService.update(schemeDTO);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/delete"})
    public R delete(@RequestBody WorkflowBusinessSchemeDTO schemeDTO) {
        if (!StringUtils.hasText((String)schemeDTO.getId())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        try {
            this.workflowBusinessSchemeService.delete(schemeDTO);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

