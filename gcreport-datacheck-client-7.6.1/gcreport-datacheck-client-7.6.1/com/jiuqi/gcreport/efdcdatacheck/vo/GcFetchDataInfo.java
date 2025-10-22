/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing
 */
package com.jiuqi.gcreport.efdcdatacheck.vo;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GcFetchDataInfo {
    private ExpressionListing expressionListing;
    private FormulaSchemeDefine soluctionFormulaScheme;
    private List<FieldDefine> retEfdcZbFieldDefines;
    private ConcurrentHashMap<String, Object> dimensionValueMap;

    public ConcurrentHashMap<String, Object> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(ConcurrentHashMap<String, Object> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public ExpressionListing getExpressionListing() {
        return this.expressionListing;
    }

    public void setExpressionListing(ExpressionListing expressionListing) {
        this.expressionListing = expressionListing;
    }

    public FormulaSchemeDefine getSoluctionFormulaScheme() {
        return this.soluctionFormulaScheme;
    }

    public void setSoluctionFormulaScheme(FormulaSchemeDefine soluctionFormulaScheme) {
        this.soluctionFormulaScheme = soluctionFormulaScheme;
    }

    public List<FieldDefine> getRetEfdcZbFieldDefines() {
        return this.retEfdcZbFieldDefines;
    }

    public void setRetEfdcZbFieldDefines(List<FieldDefine> retEfdcZbFieldDefines) {
        this.retEfdcZbFieldDefines = retEfdcZbFieldDefines;
    }
}

