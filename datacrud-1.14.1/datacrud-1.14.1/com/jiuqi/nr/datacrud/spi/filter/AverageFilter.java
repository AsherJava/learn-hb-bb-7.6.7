/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.spi.filter.LinkFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;

public abstract class AverageFilter
extends LinkFilter {
    public AverageFilter(String link, String value) {
        super(link, value);
    }

    @Override
    public String toFormula() {
        MetaData byLink;
        if (this.link != null && (byLink = this.relation.getMetaDataByLink(this.link)) != null) {
            DataTable dataTable = this.relation.getDataTable();
            String tableCode = dataTable.getCode();
            int dataType = byLink.getDataType();
            DataField dataField = byLink.getDataField();
            if (dataField != null && (dataType == 4 || dataType == 10 || dataType == 3)) {
                return tableCode + "[" + dataField.getCode() + "]" + this.operator() + tableCode + "[" + dataField.getCode() + ",avg]";
            }
        }
        return null;
    }
}

