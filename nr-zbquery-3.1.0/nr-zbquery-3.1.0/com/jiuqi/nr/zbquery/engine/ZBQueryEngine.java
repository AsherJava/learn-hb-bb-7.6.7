/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.builder.ReportBuilder
 *  com.jiuqi.bi.quickreport.builder.define.GridDefine
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.IReportListener
 *  com.jiuqi.bi.quickreport.engine.ReportEngineFactory
 *  com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv
 *  com.jiuqi.bi.quickreport.hyperlink.IHyperlinkEnv
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.bql.sdk.IBQLFactory
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.NullParameterEnv
 */
package com.jiuqi.nr.zbquery.engine;

import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.builder.ReportBuilder;
import com.jiuqi.bi.quickreport.builder.define.GridDefine;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineFactory;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkEnv;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.zbquery.ZBQueryException;
import com.jiuqi.nr.zbquery.engine.ZBQueryResult;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModel;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.engine.grid.GridDataProcessor;
import com.jiuqi.nr.zbquery.engine.report.GridDefineBuilder;
import com.jiuqi.nr.zbquery.engine.report.ReportListener;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nvwa.bql.sdk.IBQLFactory;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.NullParameterEnv;
import java.util.Map;

public class ZBQueryEngine {
    private String cacheId;
    private ZBQueryModel zbQueryModel;
    private IBQLFactory bqlFactory = (IBQLFactory)SpringBeanUtils.getBean(IBQLFactory.class);

    public ZBQueryEngine(String cacheId, ZBQueryModel zbQueryModel) {
        this.cacheId = cacheId;
        this.zbQueryModel = zbQueryModel;
    }

    public ZBQueryResult query(ConditionValues conditionValues) throws ZBQueryException {
        return this.query(conditionValues, null);
    }

    public ZBQueryResult query(ConditionValues conditionValues, PageInfo pageInfo) throws ZBQueryException {
        if (StringUtils.isNotEmpty((String)this.cacheId)) {
            try {
                this.bqlFactory.closeQuery(this.cacheId);
            }
            catch (AdHocEngineException e) {
                throw new ZBQueryException(e);
            }
        }
        return this.fetch(conditionValues, pageInfo);
    }

    public ZBQueryResult fetch(ConditionValues conditionValues, PageInfo pageInfo) throws ZBQueryException {
        ZBQueryResult queryResult = new ZBQueryResult();
        try {
            QueryDSModelBuilder dsModelBuilder = new QueryDSModelBuilder(this.zbQueryModel, conditionValues);
            dsModelBuilder.build();
            QueryDSModel dsModel = dsModelBuilder.getDSModel();
            GridDefineBuilder gridDefineBuilder = new GridDefineBuilder(this.zbQueryModel, dsModelBuilder);
            gridDefineBuilder.build();
            GridDefine gridDefine = gridDefineBuilder.getGridDefine();
            ReportBuilder reportBuilder = new ReportBuilder((DSModel)dsModel);
            QuickReport quickReport = reportBuilder.build(gridDefine);
            IReportEngine reportEngine = ReportEngineFactory.createEngine(null, (QuickReport)quickReport, (IParameterEnv)new NullParameterEnv(null));
            reportEngine.setLanguage(NpContextHolder.getContext().getLocale().getLanguage());
            reportEngine.setListener((IReportListener)new ReportListener(this.cacheId, dsModelBuilder, conditionValues, pageInfo));
            reportEngine.initParamEnv();
            reportEngine.open(128);
            IHyperlinkEnv hyperlinkEnv = reportEngine.getHyperlinkEnv();
            if (hyperlinkEnv instanceof HyperlinkEnv) {
                queryResult.setHyperlinkEnv((HyperlinkEnv)hyperlinkEnv);
            }
            GridData gridData = reportEngine.getPrimarySheet().getGridData();
            new GridDataProcessor(gridData, dsModelBuilder.getQueryModelBuilder().getModelFinder(), pageInfo).process();
            queryResult.setData(gridData);
            queryResult.setPageInfo(pageInfo);
            String[] colNames = new String[gridData.getColCount() - 1];
            Map<String, String> aliasFullNameMapper = dsModelBuilder.getQueryModelBuilder().getAliasFullNameMapper();
            for (int i = 0; i < gridData.getColCount() - 1; ++i) {
                colNames[i] = gridData.getCellScript(i + 1, 0);
                if (!aliasFullNameMapper.containsKey(colNames[i])) continue;
                colNames[i] = aliasFullNameMapper.get(colNames[i]);
            }
            queryResult.setAliasFullNameMapper(aliasFullNameMapper);
            queryResult.setColNames(colNames);
        }
        catch (Exception e) {
            throw new ZBQueryException(e.getMessage(), e);
        }
        return queryResult;
    }
}

