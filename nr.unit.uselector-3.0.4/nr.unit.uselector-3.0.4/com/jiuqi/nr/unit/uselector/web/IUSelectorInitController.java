/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuqi.nr.context.annotation.NRContextBuild
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

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.unit.uselector.web.response.ContextInfo;
import com.jiuqi.nr.unit.uselector.web.response.IMenuItem;
import com.jiuqi.nr.unit.uselector.web.response.QuickMenuInfo;
import com.jiuqi.nr.unit.uselector.web.service.IUSelectorInitService;
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
@RequestMapping(value={"/api/v2/unit-selector/init"})
@Api(tags={"\u5355\u4f4d\u9009\u62e9\u5668-\u521d\u59cb\u5316API"})
public class IUSelectorInitController {
    @Resource
    private IUSelectorInitService service;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d-\u4e0a\u4e0b\u6587\u73af\u5883")
    @PostMapping(value={"/load-context"})
    public ContextInfo loadContext(@Valid @RequestBody UnitTreeContextData ctx) {
        return this.service.loadContext(ctx);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d-\u5feb\u901f\u7b5b\u9009\u83dc\u5355\u5217\u8868")
    @GetMapping(value={"/load-quick-selection-menus"})
    public QuickMenuInfo loadQuickSelectionMenus(@RequestParam(name="selector") String selector) {
        return this.service.getQuickSelectionMenus(selector);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u9ad8\u7ea7\u7b5b\u9009-\u83dc\u5355\u5217\u8868")
    @GetMapping(value={"/load-scheme-menus"})
    public List<IMenuItem> loadFilterSchemeMenus(@RequestParam(name="selector") String selector) {
        return this.service.loadFilterSchemeMenus(selector);
    }

    @ResponseBody
    @ApiOperation(value="\u5355\u4f4d\u9009\u62e9\u5668\u9500\u6bc1\u65f6-\u8bf7\u6c42\u9500\u6bc1\u540e\u53f0\u7684\u6570\u636e\u6a21\u578b")
    @GetMapping(value={"/destroy-unit-selector"})
    public String destroyUnitSelector(@RequestParam(name="selector", required=true) String selector) {
        return "";
    }
}

