/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class CreateSnapshotInfo {
    private String title;
    private String describe;
    private String formSchemeKey;
    private DimensionCombination dimensionCombination;
    private List<String> formKeys;
    private boolean isAutoCreate;

    public CreateSnapshotInfo() {
    }

    public CreateSnapshotInfo(String title, String describe, String formSchemeKey, DimensionCombination dimensionCombination, List<String> formKeys, boolean isAutoCreate) {
        this.title = title;
        this.describe = describe;
        this.formSchemeKey = formSchemeKey;
        this.dimensionCombination = dimensionCombination;
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

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isAutoCreate() {
        return this.isAutoCreate;
    }

    public void setAutoCreate(boolean autoCreate) {
        this.isAutoCreate = autoCreate;
    }
}

