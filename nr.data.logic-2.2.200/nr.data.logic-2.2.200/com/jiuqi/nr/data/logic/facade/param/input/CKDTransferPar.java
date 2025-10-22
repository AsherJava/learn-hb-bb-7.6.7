/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.CKDTransferMap;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class CKDTransferPar {
    private String formSchemeKey;
    private DimensionCollection srcDimensionCollection;
    private List<String> formKeys;
    private CKDTransferMap transferMap;
    private List<String> ignDimNames;

    public List<String> getIgnDimNames() {
        return this.ignDimNames;
    }

    public void setIgnDimNames(List<String> ignDimNames) {
        this.ignDimNames = ignDimNames;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getSrcDimensionCollection() {
        return this.srcDimensionCollection;
    }

    public void setSrcDimensionCollection(DimensionCollection srcDimensionCollection) {
        this.srcDimensionCollection = srcDimensionCollection;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public CKDTransferMap getTransferMap() {
        return this.transferMap;
    }

    public void setTransferMap(CKDTransferMap transferMap) {
        this.transferMap = transferMap;
    }
}

