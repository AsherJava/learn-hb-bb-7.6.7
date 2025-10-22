/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.ReportEngineException
 *  com.jiuqi.bi.quickreport.engine.ReportEngineFactory
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.office.template.document.DocumentContext
 *  com.jiuqi.nvwa.office.template.document.fragment.context.WordTableContext
 *  com.jiuqi.nvwa.office.template.document.fragment.context.WordTableContext$FitType
 */
package com.jiuqi.nr.datareport.helper;

import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.ReportEngineFactory;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datareport.obj.NrDocumentParam;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.office.template.document.DocumentContext;
import com.jiuqi.nvwa.office.template.document.fragment.context.WordTableContext;

public class ReportUtil {
    private ReportUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static WordTableContext getWordTableContext() {
        WordTableContext wordTableContext = new WordTableContext();
        wordTableContext.setFitType(WordTableContext.FitType.Auto_Fit_To_Contents);
        return wordTableContext;
    }

    public static IReportEngine getQuickReportEngine(String userGuid, QuickReport report, IParameterEnv env, int options) throws ReportEngineException {
        IReportEngine engine = ReportEngineFactory.createEngine((String)userGuid, (QuickReport)report, (IParameterEnv)env);
        engine.initParamEnv();
        engine.open(options);
        return engine;
    }

    public static NrDocumentParam getNrDocumentParam(DocumentContext documentContext) {
        return (NrDocumentParam)documentContext.getProperty("nrDocumentParam");
    }

    public static String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }
}

