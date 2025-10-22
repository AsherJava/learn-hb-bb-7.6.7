/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util.datatable;

import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataTable;
import java.util.List;
import java.util.Map;

public class DataExpression {
    private DataTable table;

    public DataExpression(DataTable table) {
        this.table = table;
    }

    public Object compute(String filterString, Map<String, Object> map) {
        Object obj = new Object();
        return obj;
    }

    public Object compute2(int filterString, Map<Integer, Object> map) {
        Object obj = new Object();
        return obj;
    }

    public Object compute(String filterString, List<DataRow> map) {
        Object obj = new Object();
        return obj;
    }
}

