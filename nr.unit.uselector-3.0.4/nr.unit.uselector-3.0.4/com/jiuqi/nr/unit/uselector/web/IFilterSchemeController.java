/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
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

import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeInfo;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeTableData;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterTemplateInfo;
import com.jiuqi.nr.unit.uselector.filter.scheme.IFilterConditionMenuItem;
import com.jiuqi.nr.unit.uselector.web.service.IFilterSchemeService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
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
@RequestMapping(value={"/api/v2/unit-selector/filter-scheme"})
@Api(tags={"\u5355\u4f4d\u9009\u62e9\u5668-\u9ad8\u7ea7\u7b5b\u9009API"})
public class IFilterSchemeController {
    @Resource
    private IFilterSchemeService service;

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u7b5b\u9009\u65b9\u6848")
    @GetMapping(value={"/load-schemes"})
    public FilterSchemeTableData loadFilterSchemes(@RequestParam(name="selector") String selector) {
        return this.service.loadFilterSchemes(selector);
    }

    @ResponseBody
    @ApiOperation(value="\u7b5b\u9009\u65b9\u6848\u65b0\u589e/\u66f4\u65b0")
    @PostMapping(value={"/save-scheme"})
    public FilterSchemeTableData saveFilterScheme(@RequestParam(name="selector") String selector, @Valid @RequestBody FilterSchemeInfo scheme) {
        return this.service.saveFilterScheme(selector, scheme);
    }

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u7b5b\u9009\u6a21\u677f")
    @GetMapping(value={"/load-template"})
    public FilterTemplateInfo loadFilterTemplate(@RequestParam(name="selector") String selector, @RequestParam(name="schemeKey") String schemeKey) {
        return this.service.loadFilterTemplate(selector, schemeKey);
    }

    @ResponseBody
    @ApiOperation(value="\u7b5b\u9009\u6a21\u677f\u65b0\u589e/\u66f4\u65b0")
    @PostMapping(value={"/save-template"})
    public FilterSchemeTableData saveFilterTemplate(@RequestParam(name="selector") String selector, @Valid @RequestBody @SFDecrypt FilterTemplateInfo template) {
        return this.service.saveFilterTemplate(selector, template);
    }

    @ResponseBody
    @ApiOperation(value="\u5220\u9664\u7b5b\u9009\u65b9\u6848")
    @GetMapping(value={"/remove-scheme"})
    public FilterSchemeTableData removeFilterScheme(@RequestParam(name="selector") String selector, @RequestParam(name="schemeKey") String schemeKey) {
        return this.service.removeFilterScheme(selector, schemeKey);
    }

    @ResponseBody
    @ApiOperation(value="\u590d\u5236\u7b5b\u9009\u65b9\u6848")
    @GetMapping(value={"/copy-scheme"})
    public FilterSchemeTableData copyFilterScheme(@RequestParam(name="selector") String selector, @RequestParam(name="schemeKey") String schemeKey) {
        return this.service.copyFilterScheme(selector, schemeKey);
    }

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u9ad8\u7ea7\u7b5b\u9009-\u6761\u4ef6\u5217\u8868")
    @GetMapping(value={"/load-filter-condition-item"})
    public List<IFilterConditionMenuItem> loadFilterConditionItem(@RequestParam(name="selector", required=true) String selector) {
        return this.service.loadFilterConditionItem(selector);
    }
}

