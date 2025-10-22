/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.itreebase.web;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.context.ITreeContextData;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeDataPage;
import com.jiuqi.nr.itreebase.web.service.ITreeDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/tree-selector/node-data"})
@Api(tags={"\u4e0b\u62c9\u6811\u8282\u70b9\u6570\u636e\u8bf7\u6c42-API"})
public class ITreeDataController {
    @Resource
    private ITreeDataService service;

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u6839\u8282\u70b9")
    @PostMapping(value={"/loading-tree-data"})
    public List<ITree<IBaseNodeData>> loadingTree(@Valid @RequestBody ITreeContextData context) {
        return this.service.getTree(context);
    }

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u5b50\u8282\u70b9")
    @PostMapping(value={"/loading-children"})
    public List<ITree<IBaseNodeData>> loadingChildren(@Valid @RequestBody ITreeContextData context) {
        return this.service.getChildren(context);
    }

    @ResponseBody
    @ApiOperation(value="\u6811\u8282\u70b9\u641c\u7d22")
    @PostMapping(value={"/node-data/searching-nodes"})
    public ISearchNodeDataPage searchingNodes(@Valid @RequestBody ITreeContextData context) {
        return this.service.searchingNodes(context);
    }
}

