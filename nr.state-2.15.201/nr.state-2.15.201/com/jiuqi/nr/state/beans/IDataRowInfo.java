/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 */
package com.jiuqi.nr.state.beans;

import com.jiuqi.np.dataengine.intf.IDataRow;

public class IDataRowInfo {
    private IDataRow dataRow;
    private int stateIndex;
    private int userIndex;

    public IDataRow getDataRow() {
        return this.dataRow;
    }

    public void setDataRow(IDataRow dataRow) {
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

