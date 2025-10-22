/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.DataRow;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QueryRowComparator
implements Comparator<DataRow> {
    private List<Integer> orderIndexes = new ArrayList<Integer>();

    @Override
    public int compare(DataRow o1, DataRow o2) {
        int result = 0;
        for (int index : this.orderIndexes) {
            Object value2;
            Object value1 = o1.getValue(index);
            if (value1 == null) {
                value1 = "";
            }
            if ((value2 = o2.getValue(index)) == null) {
                value2 = "";
            }
            if ((result = ((Comparable)value1).compareTo(value2)) == 0) continue;
            return result;
        }
        return result;
    }

    public List<Integer> getOrderIndexes() {
        return this.orderIndexes;
    }
}

