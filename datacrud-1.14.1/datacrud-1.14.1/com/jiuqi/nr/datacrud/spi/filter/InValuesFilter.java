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
import java.util.List;
import org.springframework.util.CollectionUtils;

public class InValuesFilter
extends LinkFilter {
    protected List<String> values;

    public List<String> getValues() {
        DataField dataField;
        MetaData byLink;
        if (this.link != null && !CollectionUtils.isEmpty(this.values) && (byLink = this.relation.getMetaDataByLink(this.link)) != null && (dataField = byLink.getDataField()) != null) {
            int dataType = byLink.getDataType();
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

    public InValuesFilter(String link, List<String> values) {
        super(link, null);
        this.values = values;
    }

    @Override
    protected String operator() {
        return null;
    }

    @Override
    public String toFormula() {
        DataField dataField;
        MetaData byLink;
        if (this.link != null && !CollectionUtils.isEmpty(this.values) && (byLink = this.relation.getMetaDataByLink(this.link)) != null && (dataField = byLink.getDataField()) != null) {
            DataTable dataTable = this.relation.getDataTable();
            String tableCode = dataTable.getCode();
            return tableCode + "[" + dataField.getCode() + "] IN (" + String.join((CharSequence)",", this.getValues()) + ")";
        }
        return null;
    }
}

