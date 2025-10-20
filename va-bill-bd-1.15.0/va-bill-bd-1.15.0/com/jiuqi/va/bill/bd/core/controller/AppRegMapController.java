/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.va.bill.bd.core.controller;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.service.ApplyRegMapService;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/bill/applyReg/define"})
public class AppRegMapController {
    @Autowired
    private ApplyRegMapService MapService;

    @RequestMapping(value={"/updateMap"})
    @ResponseBody
    public R updateMap(@RequestBody ApplyRegMapDO billMdMpDTO) {
        this.MapService.updateMap(billMdMpDTO);
        return R.ok();
    }

    @RequestMapping(value={"/createMap"})
    @ResponseBody
    public R createMap(@RequestBody ApplyRegMapDO billMdMpDTO) {
        return this.MapService.createMap(billMdMpDTO);
    }

    @RequestMapping(value={"/deleteMap"})
    @ResponseBody
    public R deleteMap(@RequestBody ApplyRegMapDO billMdMpDTO) {
        return this.MapService.deleteMap(billMdMpDTO);
    }
}

