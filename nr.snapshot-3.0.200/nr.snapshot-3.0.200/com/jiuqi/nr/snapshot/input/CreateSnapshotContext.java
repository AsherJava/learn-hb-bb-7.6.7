/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class CreateSnapshotContext {
    private String title;
    private String describe;
    private String formSchemeKey;
    private DimensionCollection dimensionCollection;
    private List<String> formKeys;
    private boolean isAutoCreate;

    public CreateSnapshotContext() {
    }

    public CreateSnapshotContext(String title, String describe, String formSchemeKey, DimensionCollection dimensionCollection, List<String> formKeys, boolean isAutoCreate) {
        this.title = title;
        this.describe = describe;
        this.formSchemeKey = formSchemeKey;
        this.dimensionCollection = dimensionCollection;
        this.formKeys = formKeys;
        this.isAutoCreate = isAutoCreate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean getIsAutoCreate() {
        return this.isAutoCreate;
    }

    public void setIsAutoCreate(boolean isAutoCreate) {
        this.isAutoCreate = isAutoCreate;
    }
}

