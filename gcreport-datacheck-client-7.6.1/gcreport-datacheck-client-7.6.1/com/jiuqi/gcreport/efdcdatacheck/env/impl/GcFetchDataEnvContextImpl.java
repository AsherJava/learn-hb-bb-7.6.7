/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.efdcdatacheck.env.impl;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GcFetchDataEnvContextImpl
implements GcFetchDataEnvContext {
    private OrgDO org;
    private FormDefine formDefine;
    private Set<String> errorFormKeySet;
    private ExpressionListing cwFmlListing;
    private FormulaSchemeDefine formulaScheme;
    private GcFormOperationInfo formOperationInfo;
    private Map<String, Set<String>> reportId2ZbGuidMap;
    private ConcurrentHashMap<String, Object> dimensionValueMap;

    public GcFetchDataEnvContextImpl(OrgDO org, FormDefine formDefine, FormulaSchemeDefine formulaScheme, Set<String> errorFormKeySet, Map<String, Set<String>> reportId2ZbGuidMap, ExpressionListing cwFmlListing, GcFormOperationInfo formOperationInfo, ConcurrentHashMap<String, Object> dimensionValueMap) {
        this.org = org;
        this.formDefine = formDefine;
        this.formulaScheme = formulaScheme;
        this.errorFormKeySet = errorFormKeySet;
        this.cwFmlListing = cwFmlListing;
        this.formOperationInfo = formOperationInfo;
        this.dimensionValueMap = dimensionValueMap;
        this.reportId2ZbGuidMap = reportId2ZbGuidMap;
    }

    @Override
    public ExpressionListing getCwFmlListing() {
        return this.cwFmlListing;
    }

    public void setCwFmlListing(ExpressionListing cwFmlListing) {
        this.cwFmlListing = cwFmlListing;
    }

    @Override
    public Map<String, Set<String>> getReportId2ZbGuidMap() {
        return this.reportId2ZbGuidMap;
    }

    public void setReportId2ZbGuidMap(Map<String, Set<String>> reportId2ZbGuidMap) {
        this.reportId2ZbGuidMap = reportId2ZbGuidMap;
    }

    @Override
    public Set<String> getErrorFormKeySet() {
        return this.errorFormKeySet;
    }

    public void setErrorFormKeySet(Set<String> errorFormKeySet) {
        this.errorFormKeySet = errorFormKeySet;
    }

    @Override
    public ConcurrentHashMap<String, Object> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(ConcurrentHashMap<String, Object> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public GcFetchDataEnvContextImpl(GcFormOperationInfo formOperationInfo) {
        this.formOperationInfo = formOperationInfo;
    }

    @Override
    public OrgDO getOrg() {
        return this.org;
    }

    public void setOrg(OrgDO org) {
        this.org = org;
    }

    @Override
    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    @Override
    public FormulaSchemeDefine getFormulaSchemeDefine() {
        return this.formulaScheme;
    }

    public void setFormulaSchemeDefine(FormulaSchemeDefine formulaScheme) {
        this.formulaScheme = formulaScheme;
    }

    public void setFormOperationInfo(GcFormOperationInfo formOperationInfo) {
        this.formOperationInfo = formOperationInfo;
    }

    @Override
    public GcFormOperationInfo getFormOperationInfo() {
        return this.formOperationInfo;
    }
}

