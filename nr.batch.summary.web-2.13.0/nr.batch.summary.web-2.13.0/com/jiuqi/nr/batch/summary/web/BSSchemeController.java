/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.batch.summary.web;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.DateUtils;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/batch-summary/scheme"})
@Api(tags={"\u6c47\u603b\u65b9\u6848-\u901a\u7528API"})
public class BSSchemeController {
    @Resource
    private BSSchemeService service;

    @ResponseBody
    @ApiOperation(value="\u65b9\u6848\u67e5\u8be2")
    @GetMapping(value={"/find-scheme"})
    public IReturnObject<SummaryScheme> findSchemeInfo(@RequestParam(name="schemeKey", required=true) String schemeKey, @RequestParam(name="actionId", required=true) String actionId) {
        IReturnObject instance;
        SummaryScheme schemeData = null;
        try {
            schemeData = "com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction".equals(actionId) ? this.service.copyScheme(schemeKey) : this.service.findScheme(schemeKey);
            instance = IReturnObject.getSuccessInstance((Object)schemeData);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), schemeData);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u65b9\u6848\u67e5\u8be2")
    @GetMapping(value={"/find-summary-date"})
    public IReturnObject<String> findSchemeInfo(@RequestParam(name="schemeKey", required=true) String schemeKey) {
        IReturnObject instance;
        String dateStr = "";
        try {
            SummaryScheme scheme = this.service.findScheme(schemeKey);
            if (scheme != null) {
                dateStr = DateUtils.date_str_yyyy_mm_dd_HH_mm_ss((Date)scheme.getSumDataTime());
            }
            instance = IReturnObject.getSuccessInstance((Object)dateStr);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)dateStr);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u68c0\u67e5\u65b9\u6848\u540d\u79f0\u662f\u5426\u91cd\u590d")
    @GetMapping(value={"/check-report-title"})
    public IReturnObject<Boolean> checkReportTitle(@RequestParam(name="taskKey", required=true) String taskKey, @RequestParam(name="schemeTitle", required=true) String schemeTitle, @RequestParam(name="schemeKey", required=true) String schemeKey, @RequestParam(name="groupKey", required=true) String groupKey) {
        IReturnObject instance;
        Boolean isRepeat = Boolean.FALSE;
        try {
            SummaryScheme resultScheme;
            if (groupKey.isEmpty()) {
                groupKey = "00000000-0000-0000-0000-000000000000";
            }
            if ((resultScheme = this.service.findSchemeByTaskGroupAndTitle(taskKey, groupKey, schemeTitle)) != null) {
                isRepeat = !resultScheme.getKey().equals(schemeKey);
            }
            instance = IReturnObject.getSuccessInstance((Object)isRepeat);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)isRepeat);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }

    @ResponseBody
    @ApiOperation(value="\u68c0\u67e5\u65b9\u6848\u6807\u8bc6\u662f\u5426\u91cd\u590d")
    @GetMapping(value={"/check-report-code"})
    public IReturnObject<Boolean> checkReportCode(@RequestParam(name="taskKey", required=true) String taskKey, @RequestParam(name="schemeCode", required=true) String schemeCode) {
        IReturnObject instance;
        Boolean isRepeat = Boolean.FALSE;
        try {
            isRepeat = this.service.findScheme(taskKey, schemeCode) != null;
            instance = IReturnObject.getSuccessInstance((Object)isRepeat);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)isRepeat);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }
}

