/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.datacrud.PageInfo
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.datacrud.PageInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QueryICRContext
implements INRContext,
Serializable {
    private static final long serialVersionUID = -7352592294983864510L;
    private String batchId;
    private String taskKey;
    private Map<String, DimensionValue> dimensionSet;
    private String formSchemeKey;
    private List<String> formKeys;
    private String contextEntityId;
    private PageInfo pageInfo;
    private boolean diffTable;
    private boolean zeroTable;

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

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
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

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return null;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean isDiffTable() {
        return this.diffTable;
    }

    public void setDiffTable(boolean diffTable) {
        this.diffTable = diffTable;
    }

    public boolean isZeroTable() {
        return this.zeroTable;
    }

    public void setZeroTable(boolean zeroTable) {
        this.zeroTable = zeroTable;
    }
}

