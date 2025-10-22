/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.queryparam;

import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import java.util.ArrayList;
import java.util.List;

public class GroupTreeRow {
    private DataRowImpl currentRow;
    private GroupTreeRow parentRow;
    private List<GroupTreeRow> childRows;

    public GroupTreeRow(DataRowImpl currentRow, GroupTreeRow parentRow) {
        this.currentRow = currentRow;
        this.parentRow = parentRow;
        this.childRows = new ArrayList<GroupTreeRow>();
        if (parentRow != null) {
            parentRow.childRows.add(this);
        }
    }

    public void setAnotherParentRow(GroupTreeRow parentRow) {
        int lastIndex;
        if (this.parentRow != null && (lastIndex = this.parentRow.childRows.lastIndexOf(this)) >= 0) {
            this.parentRow.childRows.remove(lastIndex);
        }
        this.parentRow = parentRow;
        if (parentRow != null) {
            this.parentRow.childRows.add(this);
        }
    }

    public DataRowImpl getCurrentRow() {
        return this.currentRow;
    }

    public void setCurrentRow(DataRowImpl currentRow) {
        this.currentRow = currentRow;
    }

    public GroupTreeRow getParentRow() {
        return this.parentRow;
    }

    public void setParentRow(GroupTreeRow parentRow) {
        this.parentRow = parentRow;
    }

    public List<GroupTreeRow> getChildRows() {
        return this.childRows;
    }

    public void setChildRows(List<GroupTreeRow> childRows) {
        this.childRows = childRows;
    }
}

