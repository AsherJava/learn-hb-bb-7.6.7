/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nvwa.dataengine.INvwaDataRow;

public class IDataRowInfo {
    private INvwaDataRow dataRow;
    private int stateIndex;
    private int userIndex;

    public INvwaDataRow getDataRow() {
        return this.dataRow;
    }

    public void setDataRow(INvwaDataRow dataRow) {
        this.dataRow = dataRow;
    }

    public int getStateIndex() {
        return this.stateIndex;
    }

    public void setStateIndex(int stateIndex) {
        this.stateIndex = stateIndex;
    }

    public int getUserIndex() {
        return this.userIndex;
    }

    public void setUserIndex(int userIndex) {
        this.userIndex = userIndex;
    }
}

