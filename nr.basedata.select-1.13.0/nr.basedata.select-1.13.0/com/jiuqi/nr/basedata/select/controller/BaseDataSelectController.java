/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.basedata.select.controller;

import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataOpenResponse;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfoExtend;
import com.jiuqi.nr.basedata.select.param.BaseDataResponse;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectService;
import com.jiuqi.nr.common.itree.ITree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/nr-basedata-select"})
@Api(tags={"\u57fa\u7840\u6570\u636e\u67e5\u8be2"})
public class BaseDataSelectController {
    @Autowired
    private IBaseDataSelectService iBaseDataSelectService;

    @ApiOperation(value="\u57fa\u7840\u6570\u636e\u9009\u62e9\u6253\u5f00\u53c2\u6570")
    @PostMapping(value={"/basedata-open"})
    public BaseDataOpenResponse getBaseDataOpenParam(@Valid @RequestBody BaseDataQueryInfo baseDataQueryInfo) {
        return this.iBaseDataSelectService.getBaseDataOpenParam(baseDataQueryInfo);
    }

    @ApiOperation(value="\u57fa\u7840\u6570\u636e\u6811\u5f62\u67e5\u8be2\uff1a \u67e5\u8be2\u76f4\u63a5\u4e0b\u7ea7\uff0c\u6240\u6709\u4e0b\u7ea7\uff0c\u5b9a\u4f4d\uff0c\u641c\u7d22")
    @PostMapping(value={"/basedata"})
    public BaseDataResponse queryBaseDataTree(@Valid @RequestBody BaseDataQueryInfo baseDataQueryInfo) {
        return this.iBaseDataSelectService.getBaseDataTree(baseDataQueryInfo);
    }

    @ApiOperation(value="\u83b7\u53d6\u57fa\u7840\u6570\u636e\u6761\u76ee\u5b57\u6bb5\u7684code\u53ca\u5bf9\u5e94\u7684\u503c")
    @PostMapping(value={"/format"})
    public Map<String, Map<String, String>> queryBaseDataAttributeVale(@Valid @RequestBody BaseDataQueryInfoExtend baseDataQueryInfoExtend) {
        return this.iBaseDataSelectService.getBaseDataAttributeVale(baseDataQueryInfoExtend);
    }

    @ApiOperation(value="\u83b7\u53d6\u57fa\u7840\u6570\u636e\u6761\u76ee\u96c6\u5408")
    @PostMapping(value={"/basedata-entry"})
    public List<ITree<BaseDataInfo>> queryBaseDataEntry(@Valid @RequestBody BaseDataQueryInfoExtend baseDataQueryInfoExtend) {
        return this.iBaseDataSelectService.getBaseDataEntry(baseDataQueryInfoExtend);
    }

    @ApiOperation(value="\u83b7\u53d6\u57fa\u7840\u6570\u636e\u6761\u76ee\u5c5e\u6027\u7684\u5168\u8def\u5f84\u5c5e\u6027\u503c")
    @PostMapping(value={"/basedata-full-path"})
    public Map<String, List<String>> queryFullPathAttributeVale(@Valid @RequestBody BaseDataQueryInfoExtend baseDataQueryInfoExtend) {
        return this.iBaseDataSelectService.getFullPathAttributeVale(baseDataQueryInfoExtend);
    }
}

