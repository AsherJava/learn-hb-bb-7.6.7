/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.unit.treeimpl.web;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.unit.treeimpl.web.service.IDataEntryTreeInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/data-entry-unit-tree/init"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u5355\u4f4d\u6811\u521d\u59cb\u5316\u73af\u5883-API"})
public class DataEntryUnitTreeInitController {
    @Resource
    private IDataEntryTreeInitService service;

    @NRContextBuild
    @ResponseBody
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @ApiOperation(value="\u521d\u59cb\u5316\u73af\u5883\u53c2\u6570")
    @PostMapping(value={"/loading-env"})
    public Map<String, Object> treeModuleInit(@Valid @RequestBody UnitTreeContextData context) {
        return this.service.getEnvironment(context);
    }

    @NRContextBuild
    @ResponseBody
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @ApiOperation(value="\u52a0\u8f7d\u5355\u4f4d\u6811\u914d\u7f6e\u83dc\u5355")
    @PostMapping(value={"/loading-filter-menus"})
    public List<IMenuItemObject> loadFilterMenus(@Valid @RequestBody UnitTreeContextData contextData) {
        return this.service.getFilterMenusItems(contextData);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u7684\u5b50\u8282\u70b9\u6570\u91cf")
    @PostMapping(value={"/obtain-node-all-children-count"})
    public int obtainNodeAllChildrenCount(@Valid @RequestBody UnitTreeContextData contextData) {
        return this.service.getNodeAllChildrenCount(contextData);
    }
}

