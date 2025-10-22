/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class FormulaNode {
    private String dataLinkCode;
    private String dataLinkFormKey;
    private ColumnModelDefine columnModel;

    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    public String getDataLinkCode() {
        return this.dataLinkCode;
    }

    public void setDataLinkCode(String dataLinkCode) {
        this.dataLinkCode = dataLinkCode;
    }

    public String getDataLinkFormKey() {
        return this.dataLinkFormKey;
    }

    public void setDataLinkFormKey(String dataLinkFormKey) {
        this.dataLinkFormKey = dataLinkFormKey;
    }
}

