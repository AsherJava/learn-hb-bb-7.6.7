/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.fielddatacrud.spi.filter;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.spi.filter.FieldFilter;
import java.util.Collections;
import java.util.List;

public class ContainFilter
extends FieldFilter {
    public ContainFilter(String fieldKey, String value) {
        super(fieldKey, value);
    }

    @Override
    protected String operator() {
        return null;
    }

    @Override
    public String toFormula() {
        IMetaData metaData;
        if (this.fieldKey != null && (metaData = this.relation.getMetaDataByField(this.fieldKey)) != null) {
            DataTable dataTable;
            DataField dataField = metaData.getDataField();
            List<TableDimSet> tableDim = this.relation.getTableDim(Collections.singletonList(metaData));
            if (tableDim != null && !tableDim.isEmpty() && (dataTable = tableDim.get(0).getDataTable()) != null) {
                int dataType;
                String tableCode = dataTable.getCode();
                if (dataField != null && (dataType = metaData.getDataType()) == 6) {
                    return " Position('" + this.value + "', " + tableCode + "[" + dataField.getCode() + "])  > 0  ";
                }
            }
        }
        return null;
    }
}

