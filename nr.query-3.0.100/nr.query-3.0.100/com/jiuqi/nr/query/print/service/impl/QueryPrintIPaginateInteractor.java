/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.interactor.GridDataContentStream
 *  com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase
 *  com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse
 *  com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.xg.process.IContentStream
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 */
package com.jiuqi.nr.query.print.service.impl;

import com.jiuqi.grid.GridData;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.interactor.GridDataContentStream;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.xg.process.IContentStream;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryPrintIPaginateInteractor
extends PaginateInteractorBase {
    private static final Logger logger = LoggerFactory.getLogger(QueryPrintIPaginateInteractor.class);
    private String reportGuid;
    private Map<String, String> patternAndValue = new HashMap<String, String>();
    private Map<String, IContentStream> reportElementContentCache;
    private IPrintParamBase param;
    private IPageNumberGenerateStrategy pageNumberGenerateStrategy;
    private Map<String, String> replaceMap;
    private boolean printSumCover;
    private ExecutorContext executorContext;
    private IExpressionEvaluator expressionEvaluator;

    public QueryPrintIPaginateInteractor(IPrintParamBase param) {
        this.param = param;
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.expressionEvaluator = dataAccessProvider.newExpressionEvaluator();
        ExecutorContext executorContext = new ExecutorContext(runtimeController);
        executorContext.setJQReportModel(true);
        this.executorContext = executorContext;
    }

    public boolean adjustment(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex) {
        AdjustmentResponse res = new AdjustmentResponse();
        FilterChain filterChain = FilterChain.getInstance();
        filterChain.doFilter(templateObj, drawObj, pageIndex, (PaginateInteractorBase)this, res);
        logger.debug(res.isAdjustment() + "==" + res.getMsg());
        return res.isAdjustment();
    }

    public boolean isWithinDrawScope(ITemplateObject templateObj, int pageIndex) {
        if (templateObj.getDrawScope() == 8 && pageIndex == 1) {
            return true;
        }
        return super.isWithinDrawScope(templateObj, pageIndex);
    }

    public <TDataSource> TDataSource getDataSource(ITemplateElement<TDataSource> tmplElement) {
        String kind = tmplElement.getKind();
        if ("element_report".equals(kind)) {
            ReportTemplateObject reportObj = (ReportTemplateObject)tmplElement;
            if (null == this.reportElementContentCache) {
                this.reportElementContentCache = new HashMap<String, IContentStream>();
            }
            if (this.reportElementContentCache.containsKey(reportObj.getReportGuid())) {
                IContentStream oldContent = this.reportElementContentCache.get(reportObj.getReportGuid());
                if (oldContent.available() == 0) {
                    oldContent.reset(null);
                }
                return (TDataSource)oldContent;
            }
            GridDataContentStream gridDataContentStream = null;
            GridData reportGridData = reportObj.getGridData();
            gridDataContentStream = new GridDataContentStream(reportGridData);
            this.reportElementContentCache.put(reportObj.getReportGuid(), (IContentStream)gridDataContentStream);
            return (TDataSource)gridDataContentStream;
        }
        return null;
    }

    public String getReportGuid() {
        return this.reportGuid;
    }

    public void setReportGuid(String reportGuid) {
        this.reportGuid = reportGuid;
    }

    public IPrintParamBase getParam() {
        return this.param;
    }

    public void setParam(IPrintParamBase param) {
        this.param = param;
    }

    public Map<String, String> getPatternAndValue() {
        return this.patternAndValue;
    }

    public void setPatternAndValue(Map<String, String> patternAndValue) {
        this.patternAndValue = patternAndValue;
    }

    public Map<String, IContentStream> getReportElementContentCache() {
        return this.reportElementContentCache;
    }

    public void setReportElementContentCache(Map<String, IContentStream> reportElementContentCache) {
        this.reportElementContentCache = reportElementContentCache;
    }

    public IPageNumberGenerateStrategy getPageNumberGenerateStrategy() {
        return this.pageNumberGenerateStrategy;
    }

    public void setPageNumberGenerateStrategy(IPageNumberGenerateStrategy pageNumberGenerateStrategy) {
        this.pageNumberGenerateStrategy = pageNumberGenerateStrategy;
    }

    public Map<String, String> getReplaceMap() {
        return this.replaceMap;
    }

    public void setReplaceMap(Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public IExpressionEvaluator getExpressionEvaluator() {
        return this.expressionEvaluator;
    }

    public void setExpressionEvaluator(IExpressionEvaluator expressionEvaluator) {
        this.expressionEvaluator = expressionEvaluator;
    }

    public boolean isPrintSumCover() {
        return this.printSumCover;
    }

    public void setPrintSumCover(boolean printSumCover) {
        this.printSumCover = printSumCover;
    }
}

