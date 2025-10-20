/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.gcreport.basedata.impl.va.impl;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.ArrayList;
import java.util.List;

public class GcBaseDataFieldDefineDTO {
    private List<DataModelColumn> columns;

    public List<DataModelColumn> getColumns() {
        if (this.columns == null) {
            this.columns = new ArrayList<DataModelColumn>();
        }
        return this.columns;
    }

    public void setColumns(List<DataModelColumn> columns) {
        this.columns = columns;
    }
}

