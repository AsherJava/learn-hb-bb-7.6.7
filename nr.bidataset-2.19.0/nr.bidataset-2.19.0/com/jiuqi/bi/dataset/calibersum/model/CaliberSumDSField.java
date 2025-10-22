/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.bi.dataset.calibersum.model;

import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSFieldType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class CaliberSumDSField
extends DSField {
    private ColumnModelDefine columnModel;
    private String adjustExpression;
    private CaliberSumDSFieldType type = CaliberSumDSFieldType.FIELD;
    private int index;
    private String tableModelCode;
    private String evalExpression;
    private boolean isDestEval = false;

    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    public String getAdjustExpression() {
        return this.adjustExpression;
    }

    public void setAdjustExpression(String adjustExpression) {
        this.adjustExpression = adjustExpression;
    }

    public CaliberSumDSFieldType getType() {
        return this.type;
    }

    public void setType(CaliberSumDSFieldType type) {
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTableModelCode() {
        return this.tableModelCode;
    }

    public void setTableModelCode(String tableModelCode) {
        this.tableModelCode = tableModelCode;
    }

    public String getEvalExpression() {
        return this.evalExpression;
    }

    public void setEvalExpression(String evalExpression) {
        this.evalExpression = evalExpression;
    }

    public boolean isDestEval() {
        return this.isDestEval;
    }

    public void setDestEval(boolean isDestEval) {
        this.isDestEval = isDestEval;
    }
}

