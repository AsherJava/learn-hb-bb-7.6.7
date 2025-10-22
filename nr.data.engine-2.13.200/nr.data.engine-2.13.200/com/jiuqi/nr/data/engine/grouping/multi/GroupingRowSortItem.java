/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;

@Deprecated
public class GroupingRowSortItem
implements Comparable<GroupingRowSortItem> {
    private DataRowImpl dataRow;
    private String sortValue;

    public GroupingRowSortItem(DataRowImpl dataRow, String sortValue) {
        this.dataRow = dataRow;
        this.sortValue = sortValue;
    }

    public DataRowImpl getDataRow() {
        return this.dataRow;
    }

    public void setDataRow(DataRowImpl dataRow) {
        this.dataRow = dataRow;
    }

    public String getSortValue() {
        return this.sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public int compareTo(GroupingRowSortItem o) {
        int result = this.sortValue.compareTo(o.sortValue);
        if (result != 0) {
            return result;
        }
        int r = this.dataRow.getGroupingFlag() - o.getDataRow().getGroupingFlag();
        if (r > 0) {
            return -1;
        }
        if (r < 0) {
            return 1;
        }
        return result;
    }
}

