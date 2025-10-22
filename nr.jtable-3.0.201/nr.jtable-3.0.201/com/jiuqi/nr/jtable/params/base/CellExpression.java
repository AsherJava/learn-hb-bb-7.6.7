/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.jtable.params.base.CellStyle;

public class CellExpression {
    @JsonIgnore
    private String Order;
    private String VarName;
    private CellStyle cellStyle;

    public String getOrder() {
        return this.Order;
    }

    public CellStyle getCellStyle() {
        return this.cellStyle;
    }

    public String getVarName() {
        return this.VarName;
    }

    public void setOrder(String order) {
        this.Order = order;
    }

    public void setVarName(String varName) {
        this.VarName = varName;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}

