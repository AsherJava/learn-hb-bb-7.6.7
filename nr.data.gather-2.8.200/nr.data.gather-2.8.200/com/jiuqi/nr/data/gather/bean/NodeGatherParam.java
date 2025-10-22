/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.gather.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.gather.bean.GatherParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.HashSet;
import java.util.Set;

public class NodeGatherParam
implements GatherParam {
    private static final long serialVersionUID = -4874796740063852387L;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection dimensionCollection;
    private String formKeys;
    private boolean recursive;
    private boolean difference;
    private Set<String> ignoreAccessItems = new HashSet<String>();
    private boolean BZHZBGather = false;
    private DimensionValueSet sourceDimension;
    private String gatherDimName;

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

    public boolean isRecursive() {
        return this.recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isDifference() {
        return this.difference;
    }

    public void setDifference(boolean difference) {
        this.difference = difference;
    }

    @Override
    public Set<String> getIgnoreAccessItems() {
        return this.ignoreAccessItems;
    }

    public void setIgnoreAccessItems(Set<String> ignoreAccessItems) {
        this.ignoreAccessItems = ignoreAccessItems;
    }

    public boolean isBZHZBGather() {
        return this.BZHZBGather;
    }

    public void setBZHZBGather(boolean BZHZBGather) {
        this.BZHZBGather = BZHZBGather;
    }

    @Override
    public GatherParam.GatherType getType() {
        return GatherParam.GatherType.DATA_GATHER;
    }

    public DimensionValueSet getSourceDimension() {
        return this.sourceDimension;
    }

    public void setSourceDimension(DimensionValueSet sourceDimension) {
        this.sourceDimension = sourceDimension;
    }

    public String getGatherDimName() {
        return this.gatherDimName;
    }

    public void setGatherDimName(String gatherDimName) {
        this.gatherDimName = gatherDimName;
    }
}

