/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.dafafill.model.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataFillExpressionCell
extends DataFillBaseCell {
    private static final long serialVersionUID = 1L;
    private String expression;

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

