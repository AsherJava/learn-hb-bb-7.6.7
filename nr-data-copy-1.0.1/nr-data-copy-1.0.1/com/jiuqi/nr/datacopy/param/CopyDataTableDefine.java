/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import java.util.List;

public class CopyDataTableDefine {
    private final DataTable tableDefine;
    private String formKey;
    private String regionKey;
    private final boolean isFixed;
    private String filterCondition;
    private List<DataField> copyDataFields;
    private List<DataField> publicDimFields;
    private DataField unitField;
    private DataField periodField;
    private DataField orderField;
    private DataField bizOrderField;

    public CopyDataTableDefine(DataTable tableDefine, boolean isFixed) {
        this.tableDefine = tableDefine;
        this.isFixed = isFixed;
    }

    public DataTable getTableDefine() {
        return this.tableDefine;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<DataField> getCopyDataFields() {
        return this.copyDataFields;
    }

    public void setCopyDataFields(List<DataField> copyDataFields) {
        this.copyDataFields = copyDataFields;
    }

    public boolean isFixed() {
        return this.isFixed;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public List<DataField> getPublicDimFields() {
        return this.publicDimFields;
    }

    public void setPublicDimFields(List<DataField> publicDimFields) {
        this.publicDimFields = publicDimFields;
    }

    public DataField getUnitField() {
        return this.unitField;
    }

    public void setUnitField(DataField unitField) {
        this.unitField = unitField;
    }

    public DataField getPeriodField() {
        return this.periodField;
    }

    public void setPeriodField(DataField periodField) {
        this.periodField = periodField;
    }

    public DataField getOrderField() {
        return this.orderField;
    }

    public void setOrderField(DataField orderField) {
        this.orderField = orderField;
    }

    public DataField getBizOrderField() {
        return this.bizOrderField;
    }

    public void setBizOrderField(DataField bizOrderField) {
        this.bizOrderField = bizOrderField;
    }
}

