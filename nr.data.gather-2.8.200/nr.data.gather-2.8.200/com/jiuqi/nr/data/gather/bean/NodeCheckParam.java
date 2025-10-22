/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.gather.bean;

import com.jiuqi.nr.data.gather.bean.GatherParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class NodeCheckParam
implements GatherParam {
    private static final long serialVersionUID = 4916650393838007059L;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection dimensionCollection;
    private boolean recursive;
    private BigDecimal errorRange;
    private String formKeys;
    private Set<String> ignoreAccessItems = new HashSet<String>();

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    @Override
    public String getFormKeys() {
        return this.formKeys;
    }

    @Override
    public Set<String> getIgnoreAccessItems() {
        return this.ignoreAccessItems;
    }

    @Override
    public GatherParam.GatherType getType() {
        return GatherParam.GatherType.NODE_CHECK;
    }

    public boolean isRecursive() {
        return this.recursive;
    }

    public BigDecimal getErrorRange() {
        return this.errorRange;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public void setErrorRange(BigDecimal errorRange) {
        this.errorRange = errorRange;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public void setIgnoreAccessItems(Set<String> ignoreAccessItems) {
        this.ignoreAccessItems = ignoreAccessItems;
    }
}

