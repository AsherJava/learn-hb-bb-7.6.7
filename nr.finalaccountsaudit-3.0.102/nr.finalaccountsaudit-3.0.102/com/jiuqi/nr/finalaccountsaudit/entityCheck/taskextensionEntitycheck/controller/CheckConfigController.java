/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigItemStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigRequsetParam;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.MatchTypeStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.SelectStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.service.ICheckConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"\u6838\u5bf9\u914d\u7f6e"})
@RequestMapping(value={"/EntityCheck/CheckConfig"})
public class CheckConfigController {
    @Autowired
    private ICheckConfigService checkConfigService;

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @RequestMapping(value={"/tasks"}, method={RequestMethod.POST})
    public List<SelectStructure> getTasks() {
        return this.checkConfigService.getTasks();
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u65b9\u6848")
    @RequestMapping(value={"/formschemes"}, method={RequestMethod.POST})
    public List<SelectStructure> getFormSchemes(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.getFormSchemes(param);
    }

    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f\u7c7b\u578b")
    @RequestMapping(value={"/periodType"}, method={RequestMethod.POST})
    public int getPeriodType(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.getPeriodType(param);
    }

    @ApiOperation(value="\u83b7\u53d6\u5339\u914d\u4f9d\u636e")
    @RequestMapping(value={"/matching"}, method={RequestMethod.POST})
    public List<MatchTypeStructure> getMatching() {
        return this.checkConfigService.getMatching();
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848key\u83b7\u53d6\u6307\u6807\u5217\u8868")
    @RequestMapping(value={"/config-items"}, method={RequestMethod.POST})
    public List<ConfigItemStructure> getConfigItemsByType(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.getConfigItemsByType(param);
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u7c7b\u578b\u6307\u6807\u5173\u8054\u7684\u679a\u4e3e\u6570\u636e")
    @RequestMapping(value={"/getBblxEnumData"}, method={RequestMethod.POST})
    public List<EnumStructure> getBBLXEnumData(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.getBBLXEnumData(param);
    }

    @ApiOperation(value="\u83b7\u53d6\u6838\u5bf9\u914d\u7f6e")
    @RequestMapping(value={"/queryConfigData"}, method={RequestMethod.POST})
    public ExtensionBasicModel<?> queryConfigData(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.queryConfigData(param);
    }

    @ApiOperation(value="\u79fb\u9664\u5df2\u4fdd\u5b58\u4efb\u52a1\u7684\u4efb\u52a1\u5217\u8868")
    @RequestMapping(value={"/removeSavedTasks"}, method={RequestMethod.POST})
    public List<SelectStructure> removeSavedTasks(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.removeSavedTasks(param);
    }

    @ApiOperation(value="\u79fb\u9664\u5df2\u4fdd\u5b58\u65b9\u6848\u7684\u65b9\u6848\u5217\u8868")
    @RequestMapping(value={"/removeSavedFormSchemesByTask"}, method={RequestMethod.POST})
    public List<SelectStructure> removeSavedFormSchemesByTask(@Valid @RequestBody ConfigRequsetParam param) {
        return this.checkConfigService.removeSavedFormSchemesByTask(param);
    }
}

