/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;

public abstract class LinkFilter
implements RowFilter {
    protected final String link;
    protected final String value;
    protected RegionRelation relation;

    public LinkFilter(String link, String value) {
        this.link = link;
        this.value = value;
    }

    @Override
    public String name() {
        return null;
    }

    public String getLink() {
        return this.link;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean supportFormula() {
        return true;
    }

    @Override
    public String toFormula() {
        MetaData byLink;
        if (this.link != null && (byLink = this.relation.getMetaDataByLink(this.link)) != null) {
            DataTable dataTable = this.relation.getDataTable();
            String tableCode = dataTable.getCode();
            int dataType = byLink.getDataType();
            DataField dataField = byLink.getDataField();
            if (dataField != null) {
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
        return null;
    }

    @Override
    public boolean filter(String formula, IContext context) {
        return true;
    }

    protected abstract String operator();

    public void setRelation(RegionRelation relation) {
        this.relation = relation;
    }
}

