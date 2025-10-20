/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 */
package com.jiuqi.dc.base.common.storage.intf.impl;

import com.jiuqi.dc.base.common.storage.intf.SyncTableDefine;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import java.util.List;

public class SyncTableDefineImpl
implements SyncTableDefine {
    private String tableName;
    private List<DataModelIndex> idxModels;

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public List<DataModelIndex> getIdxModels() {
        return this.idxModels;
    }

    public void setIdxModels(List<DataModelIndex> idxModels) {
        this.idxModels = idxModels;
    }
}

