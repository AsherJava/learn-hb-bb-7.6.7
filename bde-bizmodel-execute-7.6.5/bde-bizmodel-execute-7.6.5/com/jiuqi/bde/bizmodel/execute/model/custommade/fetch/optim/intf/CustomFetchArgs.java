/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchFieldMeta;
import java.util.List;

public class CustomFetchArgs {
    private String sql;
    private CustomFetchFieldMeta fieldMeta;
    private List<String> condiList;

    public CustomFetchArgs(String sql, CustomFetchFieldMeta fieldMeta, List<String> condiList) {
        this.sql = sql;
        this.fieldMeta = fieldMeta;
        this.condiList = condiList;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public CustomFetchFieldMeta getFieldMeta() {
        return this.fieldMeta;
    }

    public void setFieldMeta(CustomFetchFieldMeta fieldMeta) {
        this.fieldMeta = fieldMeta;
    }

    public List<String> getCondiList() {
        return this.condiList;
    }

    public String toString() {
        return "CustomFetchArgs [sql=" + this.sql + ", fieldMeta=" + this.fieldMeta + ", condiList=" + this.condiList + "]";
    }
}

