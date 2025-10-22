/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.model.QueryField;

public class DatafillformulaCellContext {
    private int row;
    private int col;
    private int hideNum;
    private DimensionValueSet rowColDimenson;
    private QueryField expressionField;

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public DimensionValueSet getRowColDimenson() {
        return this.rowColDimenson;
    }

    public void setRowColDimenson(DimensionValueSet rowColDimenson) {
        this.rowColDimenson = rowColDimenson;
    }

    public QueryField getExpressionField() {
        return this.expressionField;
    }

    public void setExpressionField(QueryField expressionField) {
        this.expressionField = expressionField;
    }

    public int getHideNum() {
        return this.hideNum;
    }

    public void setHideNum(int hideNum) {
        this.hideNum = hideNum;
    }

    public String getFullCode() {
        return this.expressionField.getFullCode();
    }
}

