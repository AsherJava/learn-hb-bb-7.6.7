/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.gather.bean;

import com.jiuqi.nr.data.gather.bean.GatherParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectDataGatherParam
implements GatherParam {
    private static final long serialVersionUID = -7854437312315080855L;
    private List<String> sourceKeys = new ArrayList<String>();
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection dimensionCollection;
    private String formKeys;
    private Set<String> ignoreAccessItems = new HashSet<String>();

    @Override
    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getSourceKeys() {
        return this.sourceKeys;
    }

    public void setSourceKeys(List<String> sourceKeys) {
        this.sourceKeys = sourceKeys;
    }

    @Override
    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public Set<String> getIgnoreAccessItems() {
        return this.ignoreAccessItems;
    }

    @Override
    public GatherParam.GatherType getType() {
        return GatherParam.GatherType.SELECT_GATHER;
    }
}

