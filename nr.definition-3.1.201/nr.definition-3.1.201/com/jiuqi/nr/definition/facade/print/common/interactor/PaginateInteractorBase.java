/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.xg.process.IContentStream
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.xg.process.IContentStream;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import java.util.HashMap;
import java.util.Map;

public class PaginateInteractorBase
implements IPaginateInteractor {
    private String reportGuid;
    private Map<String, String> patternAndValue = new HashMap<String, String>();
    private Map<String, IContentStream> reportElementContentCache;
    private IPrintParamBase param;
    private IPageNumberGenerateStrategy pageNumberGenerateStrategy;
    private Map<String, String> replaceMap;
    private ExecutorContext executorContext;
    private IExpressionEvaluator expressionEvaluator;

    public boolean adjustment(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex) {
        return true;
    }

    public boolean isWithinDrawScope(ITemplateObject templateObj, int pageIndex) {
        int scope = templateObj.getDrawScope();
        boolean isWithin = false;
        switch (scope) {
            case 0: {
                isWithin = true;
                break;
            }
            case 1: {
                if (pageIndex % 2 != 0) break;
                isWithin = true;
                break;
            }
            case 2: {
                if (pageIndex % 2 != 1) break;
                isWithin = true;
                break;
            }
            case 6: {
                if (pageIndex != 0) break;
                isWithin = true;
                break;
            }
            case 3: {
                break;
            }
        }
        return isWithin;
    }

    public boolean isWithinDrawScope(ITemplateObject templateObj, int pageIndex, int maxIndex) {
        int scope = templateObj.getDrawScope();
        boolean isWithin = false;
        switch (scope) {
            case 0: {
                isWithin = true;
                break;
            }
            case 1: {
                if (pageIndex % 2 != 0) break;
                isWithin = true;
                break;
            }
            case 2: {
                if (pageIndex % 2 != 1) break;
                isWithin = true;
                break;
            }
            case 6: {
                if (pageIndex != 0) break;
                isWithin = true;
                break;
            }
            case 7: {
                if (pageIndex != maxIndex) break;
                isWithin = true;
                break;
            }
        }
        return isWithin;
    }

    public <TDataSource> TDataSource getDataSource(ITemplateElement<TDataSource> tmplElement) {
        return null;
    }

    public String getReportGuid() {
        return this.reportGuid;
    }

    public void setReportGuid(String reportGuid) {
        this.reportGuid = reportGuid;
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

    public IPrintParamBase getParam() {
        return this.param;
    }

    public void setParam(IPrintParamBase param) {
        this.param = param;
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
}

