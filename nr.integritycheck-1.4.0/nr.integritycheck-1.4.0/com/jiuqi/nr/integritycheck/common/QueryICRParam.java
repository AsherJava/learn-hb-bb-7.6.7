/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class QueryICRParam {
    private String batchId;
    private String taskKey;
    private DimensionCollection dimensionCollection;
    private String formSchemeKey;
    private List<String> formKeys;
    private PageInfo pageInfo;
    private boolean needDiffTable;
    private boolean needZeroTable;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
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

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean isNeedDiffTable() {
        return this.needDiffTable;
    }

    public void setNeedDiffTable(boolean needDiffTable) {
        this.needDiffTable = needDiffTable;
    }

    public boolean isNeedZeroTable() {
        return this.needZeroTable;
    }

    public void setNeedZeroTable(boolean needZeroTable) {
        this.needZeroTable = needZeroTable;
    }
}

