/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.invest.investworkpaper.investworkpaperfactory;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.invest.investworkpaper.service.WorkPaperQueryTaskService;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.GcDirectInvestWorkPaperQueryTask;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.GcFairAdjInvestWorkPaperQueryTask;
import com.jiuqi.gcreport.invest.investworkpaper.service.impl.GcIndirectInvestWorkPaperQueryTask;

public class GcInvestWorkPaperQueryFactory {
    public static WorkPaperQueryTaskService getInstance(String showType) {
        WorkPaperQueryTaskService queryTask = null;
        switch (showType) {
            case "PUBLIC_VALUE_ADJUSTMENT": {
                queryTask = (WorkPaperQueryTaskService)SpringContextUtils.getBean(GcFairAdjInvestWorkPaperQueryTask.class);
                break;
            }
            case "DIRECT_INVESTMENT": {
                queryTask = (WorkPaperQueryTaskService)SpringContextUtils.getBean(GcDirectInvestWorkPaperQueryTask.class);
                break;
            }
            case "INDIRECT_INVESTMENT": {
                queryTask = (WorkPaperQueryTaskService)SpringContextUtils.getBean(GcIndirectInvestWorkPaperQueryTask.class);
                break;
            }
        }
        return queryTask;
    }

    public static String getShowTypeTitle(String showType) {
        String showTypeTitle = "";
        switch (showType) {
            case "PUBLIC_VALUE_ADJUSTMENT": {
                showTypeTitle = "\u516c\u5141\u4ef7\u503c\u8c03\u6574";
                break;
            }
            case "DIRECT_INVESTMENT": {
                showTypeTitle = "\u76f4\u63a5\u6295\u8d44";
                break;
            }
            case "INDIRECT_INVESTMENT": {
                showTypeTitle = "\u95f4\u63a5\u6295\u8d44";
                break;
            }
        }
        return showTypeTitle;
    }
}

