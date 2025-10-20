/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.jiuqi.va.bill.service.ProcessForecastService;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill"})
public class ProcessForecastController {
    @Autowired
    private ProcessForecastService processForecastService;

    @PostMapping(value={"/process/forecast/node/info"})
    public R getForecastNodeInfo(@RequestBody TenantDO tenantDO) {
        Object bizcode = tenantDO.getExtInfo("bizcode");
        Object stencilId = tenantDO.getExtInfo("stencilId");
        return this.processForecastService.getForecastNodeInfo(bizcode.toString(), stencilId.toString());
    }
}

