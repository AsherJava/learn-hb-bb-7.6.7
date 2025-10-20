/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.efdc.extract.impl.response.FixExpResult
 */
package com.jiuqi.gcreport.efdcdatacheck.vo;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.efdc.extract.impl.response.FixExpResult;
import java.util.List;
import java.util.Set;

public class GcFetchDataResultInfo {
    private List<FixExpResult> retEfdcValues;
    private List<FieldDefine> retEfdcZbFieldDefines;
    private Set<String> errorFormKeySet;

    public GcFetchDataResultInfo() {
    }

    public Set<String> getErrorFormKeySet() {
        return this.errorFormKeySet;
    }

    public void setErrorFormKeySet(Set<String> errorFormKeySet) {
        this.errorFormKeySet = errorFormKeySet;
    }

    public GcFetchDataResultInfo(List<FixExpResult> retEfdcValues) {
        this.retEfdcValues = retEfdcValues;
    }

    public GcFetchDataResultInfo(List<FixExpResult> retEfdcValues, List<FieldDefine> retEfdcZbFieldDefines) {
        this.retEfdcValues = retEfdcValues;
        this.retEfdcZbFieldDefines = retEfdcZbFieldDefines;
    }

    public List<FieldDefine> getRetEfdcZbFieldDefines() {
        return this.retEfdcZbFieldDefines;
    }

    public void setRetEfdcZbFieldDefines(List<FieldDefine> retEfdcZbFieldDefines) {
        this.retEfdcZbFieldDefines = retEfdcZbFieldDefines;
    }

    public List<FixExpResult> getRetEfdcValues() {
        return this.retEfdcValues;
    }

    public void setRetEfdcValues(List<FixExpResult> retEfdcValues) {
        this.retEfdcValues = retEfdcValues;
    }
}

