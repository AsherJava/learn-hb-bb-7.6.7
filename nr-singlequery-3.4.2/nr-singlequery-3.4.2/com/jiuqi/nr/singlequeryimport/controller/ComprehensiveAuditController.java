/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.singlequeryimport.intf.bean.IMultCheckItemBase
 *  com.jiuqi.nr.singlequeryimport.intf.service.IModelQueryCheckService
 *  io.swagger.annotations.Api
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuqi.nr.singlequeryimport.intf.bean.IMultCheckItemBase;
import com.jiuqi.nr.singlequeryimport.intf.service.IModelQueryCheckService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u7efc\u5408\u5ba1\u6838\u6574\u5408\u67e5\u8be2\u6a21\u677f"})
@RequestMapping(value={"/comprehensiveAudit"})
public class ComprehensiveAuditController {
    @Autowired
    IModelQueryCheckService checkService;

    @RequestMapping(value={"/getCheckModel"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    IMultCheckItemBase getCheckModel(@RequestParam String taskKey, @RequestParam String formSchemeKey) throws Exception {
        IMultCheckItemBase checkModel = this.checkService.getCheckModel(taskKey, formSchemeKey);
        return checkModel;
    }
}

