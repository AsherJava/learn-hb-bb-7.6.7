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
import org.springframework.util.CollectionUtils;

public class InValuesFilter
extends FieldFilter {
    protected List<String> values;

    public InValuesFilter(String link, List<String> values) {
        super(link, null);
        this.values = values;
    }

    public List<String> getValues() {
        DataField dataField;
        IMetaData metaData;
        if (this.fieldKey != null && !CollectionUtils.isEmpty(this.values) && (metaData = this.relation.getMetaDataByField(this.fieldKey)) != null && (dataField = metaData.getDataField()) != null) {
            int dataType = metaData.getDataType();
            switch (dataType) {
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 8: 
                case 10: {
                    return this.values;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    protected String operator() {
        return null;
    }

    @Override
    public String toFormula() {
        DataTable dataTable;
        List<TableDimSet> tableDim;
        DataField dataField;
        IMetaData metaData;
        if (this.fieldKey != null && !CollectionUtils.isEmpty(this.values) && (metaData = this.relation.getMetaDataByField(this.fieldKey)) != null && (dataField = metaData.getDataField()) != null && (tableDim = this.relation.getTableDim(Collections.singletonList(metaData))) != null && !tableDim.isEmpty() && (dataTable = tableDim.get(0).getDataTable()) != null) {
            String tableCode = dataTable.getCode();
            return tableCode + "[" + dataField.getCode() + "] IN (" + String.join((CharSequence)",", this.getValues()) + ")";
        }
        return null;
    }
}

