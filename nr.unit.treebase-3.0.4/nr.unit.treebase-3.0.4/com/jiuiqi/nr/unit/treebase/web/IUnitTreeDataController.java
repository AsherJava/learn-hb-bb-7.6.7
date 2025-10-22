/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage
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
package com.jiuiqi.nr.unit.treebase.web;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeDataService;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeMenuService;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeSearchService;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage;
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
@RequestMapping(value={"/api/v2/unit-tree/tree"})
@Api(tags={"\u5355\u9009\u6811\u8282\u70b9\u6570\u636e\u8bf7\u6c42-API"})
public class IUnitTreeDataController {
    @Resource(name="unit-tree-data-service")
    private IUnitTreeDataService treeService;
    @Resource(name="unit-tree-search-data-service")
    private IUnitTreeSearchService searchService;
    @Resource
    private IUnitTreeMenuService menuService;

    @NRContextBuild
    @ResponseBody
    @RequiresPermissions(value={"nr:unitTreebase:unitTreebaseQuery"})
    @ApiOperation(value="\u521d\u59cb\u5316")
    @PostMapping(value={"/init/load-static-resource"})
    public Map<String, Object> treeModuleInit(@Valid @RequestBody UnitTreeContextData context) {
        return this.treeService.getStaticResource(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u6811\u5f62\u8282\u70b9")
    @PostMapping(value={"/node-data/load-tree"})
    public List<ITree<IBaseNodeData>> loadingTree(@Valid @RequestBody UnitTreeContextData context) {
        return this.treeService.getTree(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u5b50\u8282\u70b9")
    @PostMapping(value={"/node-data/load-children"})
    public List<ITree<IBaseNodeData>> loadingChildren(@Valid @RequestBody UnitTreeContextData context) {
        return this.treeService.getChildren(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6811\u8282\u70b9\u641c\u7d22")
    @PostMapping(value={"/node-data/searching-nodes"})
    public ISearchNodeDataPage searchingNodes(@Valid @RequestBody UnitTreeContextData context) {
        return this.searchService.searchingOnePageNodes(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u5355\u4f4d\u6811\u53f3\u952e\u83dc\u5355")
    @PostMapping(value={"/menu-loading/context-menus"})
    public List<IMenuItemObject> loadContextMenus(@Valid @RequestBody UnitTreeContextData contextData) {
        return this.menuService.getContextMenuItems(contextData);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u5b50\u8282\u70b9")
    @PostMapping(value={"/node-data/load-child-count"})
    public Map<String, Integer> loadChildCount(@Valid @RequestBody UnitTreeContextData context) {
        return this.treeService.getNodeCountMap(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u5f53\u524d\u5c55\u5f00\u8282\u70b9\u7684\u5b50\u8282\u70b9")
    @PostMapping(value={"/node-data/load-expand-child-count"})
    public Map<String, Integer> loadExpandChildCount(@Valid @RequestBody UnitTreeContextData context) {
        return this.treeService.getExpandNodeCountMap(context);
    }
}

