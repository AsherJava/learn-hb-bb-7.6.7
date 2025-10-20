/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.workflow.WorkflowOptionDO
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.workflow.WorkflowOptionDO;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/option"})
public class WorkflowOptionController {
    @Autowired
    private WorkflowOptionService optionFoService;

    @PostMapping(value={"/list"})
    List<OptionItemVO> list(@RequestBody OptionItemDTO param) {
        return this.optionFoService.list(param);
    }

    @PostMapping(value={"/update"})
    R update(@RequestBody WorkflowOptionDO option) {
        this.optionFoService.update(option);
        return R.ok();
    }
}

