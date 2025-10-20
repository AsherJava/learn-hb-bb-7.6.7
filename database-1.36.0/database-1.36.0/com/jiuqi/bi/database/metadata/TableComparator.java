/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.metadata.LogicTable;
import java.util.Comparator;

public class TableComparator
implements Comparator<LogicTable> {
    @Override
    public int compare(LogicTable table1, LogicTable table2) {
        return table1.getName().compareTo(table2.getName());
    }
}

