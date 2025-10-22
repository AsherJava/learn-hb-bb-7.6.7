/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.Map;
import java.util.UUID;

public class BatchQueryUpload
extends NRContext {
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private Map<String, DimensionValue> dimensionSet;
    private String filter;
    private int summaryScope;
    private boolean leafEntity;
    private boolean filterDiffUnit;
    private boolean onlyDirectChild;
    private UUID currNode;
    private int pageIndex = 1;
    private int size = 14;
    private boolean forceUpoload;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public int getSummaryScope() {
        return this.summaryScope;
    }

    public void setSummaryScope(int summaryScope) {
        this.summaryScope = summaryScope;
    }

    public UUID getCurrNode() {
        return this.currNode;
    }

    public void setCurrNode(UUID currNode) {
        this.currNode = currNode;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isForceUpoload() {
        return this.forceUpoload;
    }

    public void setForceUpoload(boolean forceUpoload) {
        this.forceUpoload = forceUpoload;
    }

    public boolean isLeafEntity() {
        return this.leafEntity;
    }

    public void setLeafEntity(boolean leafEntity) {
        this.leafEntity = leafEntity;
    }

    public boolean isFilterDiffUnit() {
        return this.filterDiffUnit;
    }

    public void setFilterDiffUnit(boolean filterDiffUnit) {
        this.filterDiffUnit = filterDiffUnit;
    }

    public boolean isOnlyDirectChild() {
        return this.onlyDirectChild;
    }

    public void setOnlyDirectChild(boolean onlyDirectChild) {
        this.onlyDirectChild = onlyDirectChild;
    }
}

