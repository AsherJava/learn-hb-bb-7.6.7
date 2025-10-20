/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.dc.base.common.data;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.enums.TableCheckTypeEnum;
import java.util.List;
import java.util.Set;

public class TableCheckResult {
    private String tableName;
    private TableCheckTypeEnum checkType;
    private Boolean isPresent;
    private List<String> missingColumnList;
    private List<String> missingIndexList;
    private Set<String> extraColumns;

    public TableCheckResult(String tableName, TableCheckTypeEnum checkType, Boolean isPresent) {
        this.tableName = tableName;
        this.checkType = checkType;
        this.isPresent = isPresent;
        this.missingColumnList = CollectionUtils.newArrayList();
        this.missingIndexList = CollectionUtils.newArrayList();
        this.extraColumns = CollectionUtils.newHashSet();
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableCheckTypeEnum getCheckType() {
        return this.checkType;
    }

    public void setCheckType(TableCheckTypeEnum checkType) {
        this.checkType = checkType;
    }

    public Boolean getPresent() {
        return this.isPresent;
    }

    public void setPresent(Boolean present) {
        this.isPresent = present;
    }

    public List<String> getMissingColumnList() {
        return this.missingColumnList;
    }

    public void setMissingColumnList(List<String> missingColumnList) {
        this.missingColumnList = missingColumnList;
    }

    public List<String> getMissingIndexList() {
        return this.missingIndexList;
    }

    public void setMissingIndexList(List<String> missingIndexList) {
        this.missingIndexList = missingIndexList;
    }

    public Set<String> getExtraColumns() {
        return this.extraColumns;
    }

    public void setExtraColumns(Set<String> extraColumns) {
        this.extraColumns = extraColumns;
    }

    public boolean checkPass() {
        return this.isPresent != false && CollectionUtils.isEmpty(this.missingColumnList) && CollectionUtils.isEmpty(this.missingIndexList) && CollectionUtils.isEmpty(this.extraColumns);
    }
}

