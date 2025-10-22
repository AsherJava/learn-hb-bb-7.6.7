/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.datacrud.impl.param;

import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableDTO {
    private DataTable dataTable;
    private DataField bizKeyOrderField;
    private DataField floatOrderField;
    private DataField periodField;
    private List<DataField> dimFields;
    private List<MetaData> metaFields;
    private Map<String, MetaData> link2Meta;

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public DataField getBizKeyOrderField() {
        return this.bizKeyOrderField;
    }

    public void setBizKeyOrderField(DataField bizKeyOrderField) {
        this.bizKeyOrderField = bizKeyOrderField;
    }

    public DataField getFloatOrderField() {
        return this.floatOrderField;
    }

    public void setFloatOrderField(DataField floatOrderField) {
        this.floatOrderField = floatOrderField;
    }

    public DataField getPeriodField() {
        return this.periodField;
    }

    public void setPeriodField(DataField periodField) {
        this.periodField = periodField;
    }

    public List<DataField> getDimFields() {
        return this.dimFields;
    }

    public void setDimFields(List<DataField> dimFields) {
        this.dimFields = dimFields;
    }

    public List<MetaData> getMetaFields() {
        return this.metaFields;
    }

    public void setMetaFields(List<MetaData> metaFields) {
        this.metaFields = metaFields;
    }

    public MetaData getMetaField(String linkKey) {
        if (this.link2Meta == null) {
            this.link2Meta = new HashMap<String, MetaData>();
            for (MetaData metaField : this.metaFields) {
                this.link2Meta.put(metaField.getLinkKey(), metaField);
            }
        }
        return this.link2Meta.get(linkKey);
    }
}

