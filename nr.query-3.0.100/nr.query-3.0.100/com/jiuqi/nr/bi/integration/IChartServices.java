/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  io.netty.util.internal.StringUtil
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.bi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bi.integration.BIApiFactory;
import com.jiuqi.nr.bi.integration.BIIntegrationHelper;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.query.chart.ChartTreeNode;
import com.jiuqi.nr.query.chart.HttpUtils;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bi-integration"})
public class IChartServices {
    @Autowired
    BIIntegrationHelper helper;
    @Autowired
    BIApiFactory biApi;

    @RequestMapping(value={"/query-charttree"}, method={RequestMethod.GET})
    public String getTree(String group, String type) {
        try {
            List<ITree<ChartTreeNode>> bitree;
            List<ITree<ChartTreeNode>> reporttree;
            ObjectMapper objectMapper = new ObjectMapper();
            if (StringUtil.isNullOrEmpty((String)group) || "null".equals(group)) {
                return objectMapper.writeValueAsString(this.helper.getRootGroup("Chart"));
            }
            if (StringUtil.isNullOrEmpty((String)type)) {
                return "";
            }
            ArrayList<ITree<ChartTreeNode>> charttree = new ArrayList<ITree<ChartTreeNode>>();
            if (type.equals("report") && (reporttree = this.helper.getTree(group, QueryModelType.DASHOWNER)).size() > 0) {
                charttree.addAll(reporttree);
            }
            if (type.equals("bi") && (bitree = this.helper.getBiTree(group, type)).size() > 0) {
                charttree.addAll(bitree);
            }
            return objectMapper.writeValueAsString(charttree);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/query-initcharttree"}, method={RequestMethod.GET})
    public String initChartTree(String group, String type, String defaultKey) {
        try {
            return this.helper.initTree(group, type, defaultKey);
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u5feb\u901f\u5206\u6790\u8868\u7ed3\u6784\u751f\u6210\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }

    @RequestMapping(value={"/query-getchartkey"}, method={RequestMethod.GET})
    public String getChartKey(String key) {
        try {
            if (!StringUtil.isNullOrEmpty((String)key)) {
                return HtmlUtils.cleanUrlXSS((String)key);
            }
            return HtmlUtils.cleanUrlXSS((String)HttpUtils.getCurrentId(key));
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u56fe\u8868key\u83b7\u53d6\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return HtmlUtils.cleanUrlXSS((String)key);
        }
    }

    @RequestMapping(value={"/query-getchartpage"}, method={RequestMethod.GET})
    public String getChartPage(String key) {
        try {
            return HtmlUtils.cleanUrlXSS((String)this.biApi.getPageUrl("/ms/dashboard/chart.jsp", key));
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"\u56fe\u8868\u9875\u9762\u8def\u5f84\u751f\u6210\u5f02\u5e38", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }
}

