/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver
 *  com.fasterxml.jackson.databind.module.SimpleModule
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nr.definition.facade.AnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.analysis.CaliberInfo;
import com.jiuqi.nr.definition.facade.analysis.CellPosition;
import com.jiuqi.nr.definition.facade.analysis.ColAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;
import com.jiuqi.nr.definition.internal.impl.anslysis.CaliberInfoImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.CellPositionImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.ColAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.FloatListConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.LineCaliberImpl;
import java.util.List;

public class AnalysisFormParamDefineImpl
implements DesignAnalysisFormParamDefine {
    private boolean autoAnalysis = false;
    private String functionName = "\u5206\u6790";
    private String functionCondition;
    private int hostCount;
    private int guestCount;
    private int numEnable;
    private boolean firstDimensionFloat;
    private boolean showAllChild;
    private String conditionFormula;
    private List<LineCaliber> lineCalibers;
    private List<ColAttribute> colAttribute;
    private DimensionConfig dimensionConfig;
    private List<FloatListConfig> floatListSettings;
    private List<String> noColCbrLinks;
    private List<String> noRowCbrLinks;

    @Override
    public List<FloatListConfig> getFloatListSettings() {
        return this.floatListSettings;
    }

    @Override
    public void setFloatListSettings(List<FloatListConfig> floatListSettings) {
        this.floatListSettings = floatListSettings;
    }

    @Override
    public int getHostCount() {
        return this.hostCount;
    }

    @Override
    public void setHostCount(int hostCount) {
        this.hostCount = hostCount;
    }

    @Override
    public int getGuestCount() {
        return this.guestCount;
    }

    @Override
    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    @Override
    public int getNumEnable() {
        return this.numEnable;
    }

    @Override
    public void setNumEnable(int numEnable) {
        this.numEnable = numEnable;
    }

    @Override
    public boolean getFirstDimensionFloat() {
        return this.firstDimensionFloat;
    }

    @Override
    public void setFirstDimensionFloat(boolean firstDimensionFloat) {
        this.firstDimensionFloat = firstDimensionFloat;
    }

    @Override
    public boolean getShowAllChild() {
        return this.showAllChild;
    }

    @Override
    public void setShowAllChild(boolean showAllChild) {
        this.showAllChild = showAllChild;
    }

    @Override
    public String getConditionFormula() {
        return this.conditionFormula;
    }

    @Override
    public void setConditionFormula(String conditionFormula) {
        this.conditionFormula = conditionFormula;
    }

    @Override
    public List<LineCaliber> getLineCalibers() {
        return this.lineCalibers;
    }

    @Override
    public void setLineCalibers(List<LineCaliber> lineCalibers) {
        this.lineCalibers = lineCalibers;
    }

    @Override
    public List<ColAttribute> getColAttribute() {
        return this.colAttribute;
    }

    @Override
    public void setColAttribute(List<ColAttribute> colAttribute) {
        this.colAttribute = colAttribute;
    }

    @Override
    public DimensionConfig getDimensionConfig() {
        return this.dimensionConfig;
    }

    @Override
    public void setDimensionConfig(DimensionConfig dimensionConfig) {
        this.dimensionConfig = dimensionConfig;
    }

    @Override
    public boolean isAutoAnalysis() {
        return this.autoAnalysis;
    }

    @Override
    public void setAutoAnalysis(boolean autoAnalysis) {
        this.autoAnalysis = autoAnalysis;
    }

    @Override
    public String getFunctionName() {
        return this.functionName;
    }

    @Override
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String getFunctionCondition() {
        return this.functionCondition;
    }

    @Override
    public void setFunctionCondition(String functionCondition) {
        this.functionCondition = functionCondition;
    }

    @Override
    public List<String> getNoColCbrLinks() {
        return this.noColCbrLinks;
    }

    @Override
    public void setNoColCbrLinks(List<String> noColCbrLinks) {
        this.noColCbrLinks = noColCbrLinks;
    }

    @Override
    public List<String> getNoRowCbrLinks() {
        return this.noRowCbrLinks;
    }

    @Override
    public void setNoRowCbrLinks(List<String> noRowCbrLinks) {
        this.noRowCbrLinks = noRowCbrLinks;
    }

    public static Module getDefaultModule() {
        SimpleModule module = new SimpleModule();
        module.setAbstractTypes(AnalysisFormParamDefineImpl.getDefaultResolver());
        return module;
    }

    public static SimpleAbstractTypeResolver getDefaultResolver() {
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(AnalysisFormParamDefine.class, AnalysisFormParamDefineImpl.class);
        resolver.addMapping(DesignAnalysisFormParamDefine.class, AnalysisFormParamDefineImpl.class);
        resolver.addMapping(CaliberInfo.class, CaliberInfoImpl.class);
        resolver.addMapping(ColAttribute.class, ColAttributeImpl.class);
        resolver.addMapping(FloatListConfig.class, FloatListConfigImpl.class);
        resolver.addMapping(LineCaliber.class, LineCaliberImpl.class);
        resolver.addMapping(DimensionConfig.class, DimensionConfigImpl.class);
        resolver.addMapping(DimensionInfo.class, DimensionInfoImpl.class);
        resolver.addMapping(DimensionAttribute.class, DimensionAttributeImpl.class);
        resolver.addMapping(CellPosition.class, CellPositionImpl.class);
        return resolver;
    }
}

