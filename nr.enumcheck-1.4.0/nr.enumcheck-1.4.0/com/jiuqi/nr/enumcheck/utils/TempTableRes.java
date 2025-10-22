/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.temptable.ITempTable
 */
package com.jiuqi.nr.enumcheck.utils;

import com.jiuqi.nr.common.temptable.ITempTable;
import java.util.List;

public class TempTableRes {
    private List<String> colName;
    private ITempTable tempTable;

    public TempTableRes(List<String> colName, ITempTable tempTable) {
        this.colName = colName;
        this.tempTable = tempTable;
    }

    public List<String> getColName() {
        return this.colName;
    }

    public void setColName(List<String> colName) {
        this.colName = colName;
    }

    public ITempTable getTempTable() {
        return this.tempTable;
    }

    public void setTempTable(ITempTable tempTable) {
        this.tempTable = tempTable;
    }
}

