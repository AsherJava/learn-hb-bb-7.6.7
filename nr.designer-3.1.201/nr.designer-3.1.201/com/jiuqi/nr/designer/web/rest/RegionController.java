/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u533a\u57dfREST\u63a5\u53e3"})
public class RegionController {
    @Autowired
    private IEntityMetaService entityMetaService;

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u57fa\u7840\u6570\u636e\u9694\u79bb\u5c5e\u6027")
    @GetMapping(path={"region/isolation/{entityId}"})
    public Integer getIsolation(@PathVariable String entityId) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        if (entityDefine != null) {
            return entityDefine.getIsolation();
        }
        return -1;
    }
}

