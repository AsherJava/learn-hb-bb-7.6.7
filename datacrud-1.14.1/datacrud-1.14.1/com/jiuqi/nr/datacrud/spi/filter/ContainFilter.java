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

public class ContainFilter
extends LinkFilter {
    public ContainFilter(String link, String value) {
        super(link, value);
    }

    @Override
    protected String operator() {
        return null;
    }

    @Override
    public String toFormula() {
        MetaData byLink;
        if (this.link != null && (byLink = this.relation.getMetaDataByLink(this.link)) != null) {
            DataTable dataTable = this.relation.getDataTable();
            String tableCode = dataTable.getCode();
            int dataType = byLink.getDataType();
            DataField dataField = byLink.getDataField();
            if (dataField != null && dataType == 6) {
                return " Position('" + this.value + "', " + tableCode + "[" + dataField.getCode() + "])  > 0  ";
            }
        }
        return null;
    }
}

