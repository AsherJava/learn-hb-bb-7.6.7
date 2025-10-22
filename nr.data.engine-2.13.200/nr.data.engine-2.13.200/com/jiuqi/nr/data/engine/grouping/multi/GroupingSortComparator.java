/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataQueryColumn
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.data.engine.grouping.multi;

import com.jiuqi.np.dataengine.common.DataQueryColumn;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.grouping.multi.GroupRowTreeNode;
import java.util.Comparator;
import java.util.List;

public class GroupingSortComparator
implements Comparator<GroupRowTreeNode> {
    private final List<DataQueryColumn> orderColumns;

    public GroupingSortComparator(List<DataQueryColumn> orderColumns) {
        this.orderColumns = orderColumns;
    }

    @Override
    public int compare(GroupRowTreeNode row1, GroupRowTreeNode row2) {
        for (DataQueryColumn orderColumn : this.orderColumns) {
            if (!orderColumn.isOrderBy()) continue;
            FieldDefine field = orderColumn.getField();
            AbstractData value = row1.getDataRow().getValue(field);
            AbstractData value1 = row2.getDataRow().getValue(field);
            int compareResult = orderColumn.isDescending() ? value1.compareTo(value) : value.compareTo(value1);
            if (compareResult == 0) continue;
            return compareResult;
        }
        return 0;
    }
}

