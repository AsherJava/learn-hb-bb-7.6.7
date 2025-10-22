/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.jiuqi.gcreport.intermediatelibrary.entity.ILSyncCondition;
import java.util.List;

public class ILSetupCondition {
    private String libraryDataSource;
    private String tablePrefix;
    private List<ILSyncCondition> iLSyncConditionList;

    public String getTablePrefix() {
        return this.tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public List<ILSyncCondition> getILSyncConditionList() {
        return this.iLSyncConditionList;
    }

    public void setILSyncConditionList(List<ILSyncCondition> iLSyncConditionList) {
        this.iLSyncConditionList = iLSyncConditionList;
    }

    public String getLibraryDataSource() {
        return this.libraryDataSource;
    }

    public void setLibraryDataSource(String libraryDataSource) {
        this.libraryDataSource = libraryDataSource;
    }
}

