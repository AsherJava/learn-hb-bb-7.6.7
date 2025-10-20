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
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl;
import java.util.List;

public class AnalysisSchemeParamDefineImpl
implements DesignAnalysisSchemeParamDefine {
    private String srcTaskKey;
    private String srcFormSchemeKey;
    private List<DimensionInfo> srcDims;
    private String condition;
    private boolean autoAnalysis = false;
    private String functionName = "\u5206\u6790";
    private String functionCondition;

    @Override
    public String getSrcTaskKey() {
        return this.srcTaskKey;
    }

    @Override
    public void setSrcTaskKey(String srcTaskKey) {
        this.srcTaskKey = srcTaskKey;
    }

    @Override
    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    @Override
    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    @Override
    public List<DimensionInfo> getSrcDims() {
        return this.srcDims;
    }

    @Override
    public void setSrcDims(List<DimensionInfo> srcDims) {
        this.srcDims = srcDims;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(String condition) {
        this.condition = condition;
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

    public static Module getDefaultModule() {
        SimpleModule module = new SimpleModule();
        module.setAbstractTypes(AnalysisSchemeParamDefineImpl.getDefaultResolver());
        return module;
    }

    private static SimpleAbstractTypeResolver getDefaultResolver() {
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(AnalysisSchemeParamDefine.class, AnalysisSchemeParamDefineImpl.class);
        resolver.addMapping(DesignAnalysisSchemeParamDefine.class, AnalysisSchemeParamDefineImpl.class);
        resolver.addMapping(DimensionConfig.class, DimensionConfigImpl.class);
        resolver.addMapping(DimensionInfo.class, DimensionInfoImpl.class);
        resolver.addMapping(DimensionAttribute.class, DimensionAttributeImpl.class);
        return resolver;
    }
}

