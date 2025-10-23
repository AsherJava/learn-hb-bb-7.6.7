/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.nrdx.param.task.controller;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.nr.nrdx.param.task.service.ITransParamService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/nrdx/paramio/"})
@Api(tags={"NRDX\u53c2\u6570\u5bfc\u5165\u5bfc\u51faAPI"})
public class ParamController {
    @Autowired
    ITransParamService transParamService;

    @PostMapping(value={"release"})
    public List<ResItem> exportData(@RequestBody List<ResItem> resItems) throws Exception {
        return this.transParamService.getRelatedBusiness(resItems);
    }
}

