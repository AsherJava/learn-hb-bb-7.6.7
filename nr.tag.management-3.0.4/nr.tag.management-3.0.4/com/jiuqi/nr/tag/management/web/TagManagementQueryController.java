/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
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
package com.jiuqi.nr.tag.management.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.tag.management.environment.TagCountContextData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagCountDataSet;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.service.ITagManagementQueryService;
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
@RequestMapping(value={"/nr/api/v1/tag-manager/query"})
@Api(tags={"\u6807\u8bb0\u7ba1\u7406-\u901a\u7528\u67e5\u8be2\u63a5\u53e3"})
public class TagManagementQueryController {
    @Resource
    private ITagManagementQueryService service;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u73af\u5883\u4e0b\u6240\u6709\u6807\u8bb0\u7684\u8be6\u7ec6\u4fe1\u606f")
    @PostMapping(value={"/loading-all-tags"})
    public List<ITagFacade> loadingAllTags(@Valid @RequestBody TagQueryContextData context) {
        return this.service.queryAllTags(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u7edf\u8ba1\u6807\u8bb0\u7684\u6240\u5c5e\u5355\u4f4d\u4fe1\u606f")
    @PostMapping(value={"/loading-tag-units"})
    public ITagCountDataSet loadingTagUnits(@Valid @RequestBody TagCountContextData context) {
        return this.service.tagCountUnits(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u7edf\u8ba1\u5355\u4f4d\u7684\u6240\u5c5e\u6807\u8bb0\u4fe1\u606f")
    @PostMapping(value={"/loading-unit-tags"})
    public ITagCountDataSet loadingUnitTags(@Valid @RequestBody TagCountContextData context) {
        return this.service.unitCountTags(context);
    }
}

