/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.unit.uselector.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetRemoveParam;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetSortParam;
import com.jiuqi.nr.unit.uselector.web.request.FilterSetTagParam;
import com.jiuqi.nr.unit.uselector.web.service.IUSelectorResultSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/unit-selector/result-set"})
@Api(tags={"\u5355\u4f4d\u9009\u62e9\u5668-\u7b5b\u9009\u7ed3\u679c\u96c6API"})
public class IUSelectorResultSetController {
    @Resource
    private IUSelectorResultSetService service;
    @Resource
    private USelectorResultSet rsSet;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u53f3\u4fa7\u5217\u8868-\u5206\u9875\u52a0\u8f7d")
    @GetMapping(value={"/load-page-list"})
    public List<IBaseNodeData> loadPageList(@RequestParam(name="selector") String selector, @RequestParam(name="fromIndex") Integer fromIndex, @RequestParam(name="pagesize") Integer pagesize) {
        return this.service.getPageFilterSet(selector, fromIndex, pagesize);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u53f3\u4fa7\u5217\u8868-\u9009\u4e2d\u96c6\u5408\u4e0a\u79fb\u4e0b\u79fb\u6392\u5e8f")
    @PostMapping(value={"/sort-filter-set"})
    public String sortFilterSet(@Valid @RequestBody FilterSetSortParam sortParam) {
        return this.service.sortFilterSet(sortParam);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u53f3\u4fa7\u5217\u8868-\u79fb\u9664\u9009\u4e2d\u96c6\u5408")
    @PostMapping(value={"/remove-list"})
    public Integer removeListFromFilterSet(@Valid @RequestBody FilterSetRemoveParam removeParam) {
        return this.service.removeListFromFilterSet(removeParam);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u53f3\u4fa7\u5217\u8868-\u6807\u8bb0\u5df2\u9009\u96c6\u5408")
    @PostMapping(value={"/tag-filter-set"})
    public Integer tagFilterSet(@Valid @RequestBody FilterSetTagParam tagParam) {
        return this.service.tagFilterSet(tagParam);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7b5b\u9009\u7ed3\u679c\u96c6-\uff08\u7f13\u51b2\u63a5\u53e3\u4e0d\u5efa\u8bae\u4f7f\u7528\uff09")
    @GetMapping(value={"/filter-result-set"})
    public List<String> getFilterResultSet(@RequestParam(name="selector", required=true) String selector) {
        return this.rsSet.getFilterSet(selector);
    }
}

