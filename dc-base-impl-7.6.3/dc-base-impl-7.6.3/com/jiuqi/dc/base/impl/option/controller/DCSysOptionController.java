/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.option.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.impl.option.service.DCSysOptionService;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DCSysOptionController {
    public final String DC_SYS_OPTION_PATH = "/api/datacenter/v1/dcSysOption";
    @Autowired
    private DCSysOptionService dcSysOptionService;

    @PostMapping(value={"/api/datacenter/v1/dcSysOption/save"})
    public BusinessResponseEntity<ResultObject> saveBalanceQueryOption(@RequestBody SystemOptionSave optionSave) {
        return BusinessResponseEntity.ok((Object)this.dcSysOptionService.saveOption(optionSave));
    }
}

