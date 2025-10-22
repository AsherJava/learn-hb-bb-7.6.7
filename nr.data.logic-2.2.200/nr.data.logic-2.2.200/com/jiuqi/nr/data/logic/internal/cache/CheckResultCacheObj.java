/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.query.result.ColumnInfo
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.query.result.ColumnInfo;
import java.io.Serializable;

public class CheckResultCacheObj
implements Serializable {
    private static final long serialVersionUID = -6873356083736542945L;
    private DataSet<ColumnInfo> dataSet;
    private String actionID;

    public DataSet<ColumnInfo> getDataSet() {
        return this.dataSet;
    }

    public void setDataSet(DataSet<ColumnInfo> dataSet) {
        this.dataSet = dataSet;
    }

    public String getActionID() {
        return this.actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }
}

