/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.bi.integration;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bi.integration.BIApiFactory;
import com.jiuqi.nr.bi.integration.BIIntegrationHelper;
import com.jiuqi.nr.common.log.LogModuleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bi-integration"})
public class IReportServices {
    @Autowired
    BIIntegrationHelper helper;
    @Autowired
    BIApiFactory biApi;

    @RequestMapping(value={"/query-initreporttree"}, method={RequestMethod.GET})
    public String initReportTree(String group, String defaultKey) {
        try {
            return this.helper.initTree(group, "bireport", defaultKey);
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5feb\u901f\u5206\u6790\u8868\u7ed3\u6784\u751f\u6210\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }

    @RequestMapping(value={"/query-getreportItem"}, method={RequestMethod.GET})
    public String getReport(String key) {
        try {
            return HtmlUtils.cleanUrlXSS((String)this.biApi.getItemUrl("bireport", key));
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5feb\u901f\u5206\u6790\u8868\u7ed3\u6784\u751f\u6210\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }

    @RequestMapping(value={"/query-getreportpage"}, method={RequestMethod.GET})
    public String getReportPage(String key) {
        try {
            key = HtmlUtils.cleanUrlXSS((String)key);
            return this.biApi.getPageUrl("bireport", key);
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5feb\u901f\u5206\u6790\u8868\u9875\u9762\u8def\u5f84\u751f\u6210\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }

    @RequestMapping(value={"/query-locate"}, method={RequestMethod.GET})
    public String locate(String group) {
        try {
            return this.helper.selectAndExpendTreeNode(group, "bireport");
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5feb\u901f\u5206\u6790\u8868\u52a0\u8f7d\u9009\u4e2d\u8282\u70b9\u6811", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }
}

