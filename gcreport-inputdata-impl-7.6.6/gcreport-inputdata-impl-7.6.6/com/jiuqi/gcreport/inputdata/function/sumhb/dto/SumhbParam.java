/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dto;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class SumhbParam {
    private String formCode;
    private String reginId;
    private String filter;
    private String compiledFilter;
    private String regionFilter;
    private ColumnModelDefine fieldDefine;

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getReginId() {
        return this.reginId;
    }

    public void setReginId(String reginId) {
        this.reginId = reginId;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getCompiledFilter() {
        return this.compiledFilter;
    }

    public void setCompiledFilter(String compiledFilter) {
        this.compiledFilter = compiledFilter;
    }

    public String getRegionFilter() {
        return this.regionFilter;
    }

    public void setRegionFilter(String regionFilter) {
        this.regionFilter = regionFilter;
    }

    public ColumnModelDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(ColumnModelDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public String toString() {
        return "SumhbParam{formCode='" + this.formCode + '\'' + ", reginId='" + this.reginId + '\'' + ", filter='" + this.filter + '\'' + ", compiledFilter='" + this.compiledFilter + '\'' + ", regionFilter='" + this.regionFilter + '\'' + ", fieldDefine=" + this.fieldDefine + '}';
    }
}

