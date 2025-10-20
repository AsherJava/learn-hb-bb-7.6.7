/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.definition.facade.print.common.parse;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParseContext {
    private Map<String, String> replacerMap = new HashMap<String, String>();
    private List<Variable> variables;
    private DimensionValueSet dimensionValueSet;
    private boolean isSplit;
    private String formSchemeKey;
    private String formKey;
    private String formulaSchemeKey;
    private ExecutorContext executorContext;
    private IExpressionEvaluator expressionEvaluator;
    private IPageNumberGenerateStrategy pagePatternParser;

    public ParseContext() {
        this.setDate(Calendar.getInstance());
    }

    public String getString(String key) {
        return this.replacerMap.get(key);
    }

    public boolean containsKey(String key) {
        return this.replacerMap.containsKey(key);
    }

    public void putString(String key, String value) {
        this.replacerMap.put(key, value);
    }

    public void setDate(Calendar calendar) {
        Calendar currCalendar = calendar;
        if (currCalendar == null) {
            currCalendar = Calendar.getInstance();
        }
        this.replacerMap.put("yyyy", String.valueOf(currCalendar.get(1)));
        this.replacerMap.put("mm", String.valueOf(currCalendar.get(2) + 1));
        this.replacerMap.put("dd", String.valueOf(currCalendar.get(5)));
        this.replacerMap.put("hh", String.valueOf(currCalendar.get(11)));
        this.replacerMap.put("nn", String.valueOf(currCalendar.get(12)));
    }

    public void setFormDefine(FormDefine formDefine) {
        if (formDefine == null) {
            return;
        }
        this.replacerMap.put("RPTMAINTITLE", formDefine.getTitle());
        this.replacerMap.put("RPTSUBTITLE", formDefine.getSubTitle());
        this.replacerMap.put("REPORTNUM", formDefine.getSerialNumber());
        this.replacerMap.put("RPTMONEYUNIT", formDefine.getMeasureUnit());
    }

    public void setPageNum(int pageNum, int pageNumInPt, int totalCount) {
        this.replacerMap.put("PageNum", String.valueOf(pageNum));
        this.replacerMap.put("PageNumber", String.valueOf(pageNumInPt));
        this.replacerMap.put("ReportPageNum", String.valueOf(pageNumInPt));
        this.replacerMap.put("ReportPageCount", String.valueOf(totalCount));
        this.replacerMap.put("TotalCount", String.valueOf(totalCount));
        this.replacerMap.put("n", String.valueOf(pageNumInPt));
        this.replacerMap.put("m", String.valueOf(totalCount));
    }

    public void setRptOrderNum(int orderNum) {
        this.replacerMap.put("ReportOrder", String.valueOf(orderNum));
    }

    public boolean isSplit() {
        return this.isSplit;
    }

    public void setSplit(boolean isSplit) {
        this.isSplit = isSplit;
    }

    public void setFQuanUnit(String fQuanUnit) {
        this.replacerMap.remove("RPTMONEYUNIT");
        this.replacerMap.put("RPTMONEYUNIT", fQuanUnit);
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
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

    public IPageNumberGenerateStrategy getPagePatternParser() {
        if (null == this.pagePatternParser) {
            this.pagePatternParser = new DefaultPageNumberGenerateStrategy();
        }
        return this.pagePatternParser;
    }

    public void setPagePatternParser(IPageNumberGenerateStrategy pagePatternParser) {
        this.pagePatternParser = pagePatternParser;
    }
}

