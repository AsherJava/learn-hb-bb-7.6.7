/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.summary.internal.service.ISummaryConfigService;
import com.jiuqi.nr.summary.vo.SummaryConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report"})
public class SummaryConfigController {
    @Autowired
    private ISummaryConfigService summaryConfigService;

    @PostMapping(value={"/config/save"})
    public void saveConfig(@RequestBody SummaryConfigVO configVO) {
        this.summaryConfigService.saveConfig(configVO);
    }

    @GetMapping(value={"/config/get/{menuId}"})
    public SummaryConfigVO getConfig(@PathVariable String menuId) {
        return this.summaryConfigService.getConfig(menuId);
    }
}

