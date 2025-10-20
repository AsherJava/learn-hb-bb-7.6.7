/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.analysis.CaliberInfo
 *  com.jiuqi.nr.definition.facade.analysis.DimensionConfig
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.facade.analysis.FloatListConfig
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.analysis.CaliberInfo;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisFormParamObj {
    private boolean dirty;
    private String functionName = "\u5206\u6790";
    private String functionCondition;
    private boolean autoAnalysis = false;
    private String conditionFormula;
    private Map<Integer, List<CaliberInfo>> colCalibers;
    private Map<Integer, List<CaliberInfo>> rowCalibers;
    private List<FloatListConfig> floatListSettings;
    private List<DimensionInfo> srcDims;
    private List<String> noColCbrLinks;
    private List<String> noRowCbrLinks;

    public List<String> getNoColCbrLinks() {
        return this.noColCbrLinks;
    }

    public void setNoColCbrLinks(List<String> noColCbrLinks) {
        this.noColCbrLinks = noColCbrLinks;
    }

    public List<String> getNoRowCbrLinks() {
        return this.noRowCbrLinks;
    }

    public void setNoRowCbrLinks(List<String> noRowCbrLinks) {
        this.noRowCbrLinks = noRowCbrLinks;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean isAutoAnalysis() {
        return this.autoAnalysis;
    }

    public void setAutoAnalysis(boolean autoAnalysis) {
        this.autoAnalysis = autoAnalysis;
    }

    public String getConditionFormula() {
        return this.conditionFormula;
    }

    public void setConditionFormula(String conditionFormula) {
        this.conditionFormula = conditionFormula;
    }

    public Map<Integer, List<CaliberInfo>> getColCalibers() {
        return this.colCalibers;
    }

    public void setColCalibers(Map<Integer, List<CaliberInfo>> colCalibers) {
        this.colCalibers = colCalibers;
    }

    public Map<Integer, List<CaliberInfo>> getRowCalibers() {
        return this.rowCalibers;
    }

    public void setRowCalibers(Map<Integer, List<CaliberInfo>> rowCalibers) {
        this.rowCalibers = rowCalibers;
    }

    public List<FloatListConfig> getFloatListSettings() {
        return this.floatListSettings;
    }

    public void setFloatListSettings(List<FloatListConfig> floatListSettings) {
        this.floatListSettings = floatListSettings;
    }

    public List<DimensionInfo> getSrcDims() {
        return this.srcDims;
    }

    public void setSrcDims(List<DimensionInfo> srcDims) {
        this.srcDims = srcDims;
    }

    public String getFunctionCondition() {
        return this.functionCondition;
    }

    public void setFunctionCondition(String functionCondition) {
        this.functionCondition = functionCondition;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public static void toObj(AnalysisFormParamObj obj, DesignAnalysisFormParamDefine define) {
        obj.setAutoAnalysis(define.isAutoAnalysis());
        obj.setFunctionName(define.getFunctionName());
        obj.setFunctionCondition(define.getFunctionCondition());
        obj.setConditionFormula(define.getConditionFormula());
        List lineCalibers = define.getLineCalibers();
        HashMap<Integer, List<CaliberInfo>> colCalibers = new HashMap<Integer, List<CaliberInfo>>();
        HashMap<Integer, List<CaliberInfo>> rowCalibers = new HashMap<Integer, List<CaliberInfo>>();
        obj.setColCalibers(colCalibers);
        obj.setRowCalibers(rowCalibers);
        obj.setFloatListSettings(define.getFloatListSettings());
        obj.setSrcDims(define.getDimensionConfig().getSrcDims());
        obj.setNoColCbrLinks(define.getNoColCbrLinks());
        obj.setNoRowCbrLinks(define.getNoRowCbrLinks());
    }

    public static void toDefine(AnalysisFormParamObj obj, DesignAnalysisFormParamDefine define) {
        define.setFunctionName(obj.getFunctionName());
        define.setFunctionCondition(obj.getFunctionCondition());
        define.setAutoAnalysis(obj.isAutoAnalysis());
        define.setConditionFormula(obj.getConditionFormula());
        ArrayList lineCalibers = new ArrayList();
        define.setFloatListSettings(obj.getFloatListSettings());
        DimensionConfigImpl dimConfig = new DimensionConfigImpl();
        dimConfig.setSrcDims(obj.getSrcDims());
        define.setDimensionConfig((DimensionConfig)dimConfig);
        define.setNoColCbrLinks(obj.getNoColCbrLinks());
        define.setNoRowCbrLinks(obj.getNoRowCbrLinks());
    }
}

