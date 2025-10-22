/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class AnalysisSchemeParamObj {
    private String srcTaskKey;
    private String srcFormSchemeKey;
    private List<DimensionInfo> srcDims;
    private String condition;
    private boolean autoAnalysis = false;
    private String functionName = "\u5206\u6790";
    private String functionCondition;

    public String getSrcTaskKey() {
        return this.srcTaskKey;
    }

    public void setSrcTaskKey(String srcTaskKey) {
        this.srcTaskKey = srcTaskKey;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public List<DimensionInfo> getSrcDims() {
        return this.srcDims;
    }

    public void setSrcDims(List<DimensionInfo> srcDims) {
        this.srcDims = srcDims;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isAutoAnalysis() {
        return this.autoAnalysis;
    }

    public void setAutoAnalysis(boolean autoAnalysis) {
        this.autoAnalysis = autoAnalysis;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCondition() {
        return this.functionCondition;
    }

    public void setFunctionCondition(String functionCondition) {
        this.functionCondition = functionCondition;
    }

    public static AnalysisSchemeParamObj toObj(String params) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(AnalysisSchemeParamDefineImpl.getDefaultModule());
        return (AnalysisSchemeParamObj)mapper.readValue(params, AnalysisSchemeParamObj.class);
    }

    public static DesignAnalysisSchemeParamDefine toDefine(String params) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(AnalysisSchemeParamDefineImpl.getDefaultModule());
        return (DesignAnalysisSchemeParamDefine)mapper.readValue(params, DesignAnalysisSchemeParamDefine.class);
    }

    public static AnalysisSchemeParamObj toObj(DesignAnalysisSchemeParamDefine define) {
        AnalysisSchemeParamObj obj = new AnalysisSchemeParamObj();
        BeanUtils.copyProperties(define, obj);
        return obj;
    }

    public static DesignAnalysisSchemeParamDefine toDefine(AnalysisSchemeParamObj obj) {
        AnalysisSchemeParamDefineImpl define = new AnalysisSchemeParamDefineImpl();
        BeanUtils.copyProperties(obj, define);
        return define;
    }
}

