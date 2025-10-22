/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.result.SheetData
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 */
package com.jiuqi.nr.finalaccountsaudit.dataanalysischeck.service.impl;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.finalaccountsaudit.dataanalysischeck.service.IDataAnalysisCheckService;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.DataAnalysisItem;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.common.ZBQueryCheckUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataAnalysisCheckService
implements IDataAnalysisCheckService {
    private static final Logger logger = LoggerFactory.getLogger(DataAnalysisCheckService.class);
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    ZBQueryCheckUtil zbQueryCheckUtil;

    @Override
    public DataAnalysisItem hasQueryData(String modelId, JtableContext context) {
        DataAnalysisItem analysisItem = new DataAnalysisItem();
        try {
            Option option = this.getOption(modelId, context);
            QuickReport report = this.quickReportModelService.getQuickReportByGuidOrId(modelId);
            analysisItem.setModelId(modelId);
            analysisItem.setType("dataAnalysis");
            analysisItem.setName(report.getTitle());
            analysisItem.setResult(true);
            Class<?> clazz = Class.forName("com.jiuqi.nvwa.quickreport.web.query.QueryUtil");
            Method method = clazz.getDeclaredMethod("query", QuickReport.class, Option.class, Integer.TYPE);
            method.setAccessible(true);
            IReportEngine regine = (IReportEngine)method.invoke(null, report, option, 1);
            if (regine != null) {
                List sheetDatas = regine.getPagedAllSheets(-1);
                for (SheetData sheetData : sheetDatas) {
                    GridData gridData = sheetData.getGridData();
                    if (gridData.getDataRowCount() <= 0) continue;
                    analysisItem.setResult(false);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            analysisItem.setResult(false);
        }
        return analysisItem;
    }

    private Option getOption(String modelId, JtableContext context) {
        if (context == null) {
            return new Option();
        }
        Map<String, DimensionValue> dimensionValueSet = this.zbQueryCheckUtil.getDimensionValueSet(context);
        Option option = this.zbQueryCheckUtil.dimensionToQuickReportOption(modelId, dimensionValueSet);
        return option;
    }
}

