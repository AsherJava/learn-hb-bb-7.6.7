/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.DataSetPageReportEngine;
import com.jiuqi.bi.quickreport.engine.GridPageReportEngine;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.quickreport.model.QuickReport;

public class ReportEngineFactory {
    private ReportEngineFactory() {
    }

    @Deprecated
    public static IReportEngine createEngine(String userID, QuickReport report, IParameterEnv paramEnv) throws ReportEngineException {
        EnhancedParameterEnvAdapter env = new EnhancedParameterEnvAdapter(paramEnv);
        return ReportEngineFactory.createEngine(userID, report, (com.jiuqi.nvwa.framework.parameter.IParameterEnv)env);
    }

    public static IReportEngine createEngine(String userID, QuickReport report, com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) throws ReportEngineException {
        if (report.getPageInfo().getPageMode() == PageMode.DATASET) {
            return new DataSetPageReportEngine(userID, report, paramEnv);
        }
        return new GridPageReportEngine(userID, report, paramEnv);
    }
}

