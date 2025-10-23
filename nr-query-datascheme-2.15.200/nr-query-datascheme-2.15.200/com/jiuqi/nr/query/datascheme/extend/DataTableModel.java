/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import java.util.ArrayList;
import java.util.List;

public class DataTableModel {
    private DesignDataTable dataTable;
    private List<DesignDataField> fields = new ArrayList<DesignDataField>();

    public DataTableModel(DesignDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public DesignDataTable getDataTable() {
        return this.dataTable;
    }

    public List<DesignDataField> getFields() {
        return this.fields;
    }
}

