/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.gather.bean.event;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class GatherCompleteSource
implements Serializable {
    private static final long serialVersionUID = 3650362864681361318L;
    private String formSchemeKey;
    private List<String> formKeys;
    private DimensionCollection dimensionCollection;
    private List<String> sourceKeys;
    private boolean recursive;

    public GatherCompleteSource(String formSchemeKey, List<String> formKeys, DimensionCollection dimensionCollection, List<String> sourceKeys, boolean recursive) {
        this.formSchemeKey = formSchemeKey;
        this.formKeys = formKeys;
        this.dimensionCollection = dimensionCollection;
        this.sourceKeys = sourceKeys;
        this.recursive = recursive;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getSourceKeys() {
        return this.sourceKeys;
    }

    public void setSourceKeys(List<String> sourceKeys) {
        this.sourceKeys = sourceKeys;
    }

    public boolean isRecursive() {
        return this.recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }
}

