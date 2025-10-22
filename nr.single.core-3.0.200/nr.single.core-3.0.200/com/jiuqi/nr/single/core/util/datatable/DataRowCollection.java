/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util.datatable;

import com.jiuqi.nr.single.core.util.datatable.DataColumnCollection;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataTable;
import java.util.ArrayList;

public class DataRowCollection
extends ArrayList<DataRow> {
    private static final long serialVersionUID = 1L;
    private transient DataTable table;
    private DataColumnCollection columns;

    public DataRowCollection(DataTable table) {
        this.table = table;
    }

    public DataRowCollection() {
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setColumns(DataColumnCollection value) {
        this.columns = value;
    }

    public DataColumnCollection getColumns() {
        return this.columns;
    }
}

