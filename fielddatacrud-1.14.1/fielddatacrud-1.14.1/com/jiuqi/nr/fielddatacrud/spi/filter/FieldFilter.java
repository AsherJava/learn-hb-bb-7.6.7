/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.fielddatacrud.spi.filter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import java.util.Collections;
import java.util.List;

public abstract class FieldFilter
implements RowFilter {
    protected final String fieldKey;
    protected final String value;
    protected FieldRelation relation;

    public FieldFilter(String fieldKey, String value) {
        this.fieldKey = fieldKey;
        this.value = value;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public String getValue() {
        return this.value;
    }

    public void setRelation(FieldRelation relation) {
        this.relation = relation;
    }

    public String name() {
        return "\u6307\u6807\u6570\u636e\u8fc7\u6ee4";
    }

    public boolean supportFormula() {
        return true;
    }

    public String toFormula() {
        IMetaData metaData;
        if (this.fieldKey != null && (metaData = this.relation.getMetaDataByField(this.fieldKey)) != null) {
            DataTable dataTable;
            DataField dataField = metaData.getDataField();
            List<TableDimSet> tableDim = this.relation.getTableDim(Collections.singletonList(metaData));
            if (tableDim != null && !tableDim.isEmpty() && (dataTable = tableDim.get(0).getDataTable()) != null) {
                String tableCode = dataTable.getCode();
                if (dataField != null) {
                    int dataType = metaData.getDataType();
                    if (dataType == 6 || dataType == 5 || dataType == 8 || dataType == 2) {
                        return tableCode + "[" + dataField.getCode() + "]" + this.operator() + "'" + this.getValue() + "'";
                    }
                    if (dataType == 4 || dataType == 10 || dataType == 3) {
                        return tableCode + "[" + dataField.getCode() + "]" + this.operator() + this.getValue();
                    }
                    if (dataType == 1) {
                        return tableCode + "[" + dataField.getCode() + "]" + this.operator() + this.getValue();
                    }
                }
            }
        }
        return null;
    }

    public boolean filter(String formula, IContext context) throws SyntaxException {
        return false;
    }

    protected abstract String operator();
}

