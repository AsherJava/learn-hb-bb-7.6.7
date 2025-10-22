/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.io.Serializable;

public class DataColumn
implements Serializable {
    private static final long serialVersionUID = 4664241113970040972L;
    public static final int COLUMN_TYPE_FIELD = 0;
    public static final int COLUMN_TYPE_DIM_FIELD = 1;
    public static final int COLUMN_TYPE_EXPRESSION = 2;
    public static final int COLUMN_TYPE_LOOKUP = 3;
    protected int type;
    protected ColumnModelDefine columnModel;
    protected PeriodModifier periodModifier;
    protected DimensionValueSet dimensionRestriction;
    protected String expression;

    public DataColumn(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
        this.type = 0;
    }

    public DataColumn(ColumnModelDefine columnModel, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        this.columnModel = columnModel;
        this.periodModifier = periodModifier;
        this.dimensionRestriction = dimensionRestriction;
        this.type = 1;
    }

    public DataColumn(String expression) {
        this.expression = expression;
        this.type = 2;
    }

    public int getType() {
        return this.type;
    }

    public PeriodModifier getPeriodModifier() {
        return this.periodModifier;
    }

    public DimensionValueSet getDimensionRestriction() {
        return this.dimensionRestriction;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPeriodModifier(PeriodModifier periodModifier) {
        this.periodModifier = periodModifier;
    }

    public void setDimensionRestriction(DimensionValueSet dimensionRestriction) {
        this.dimensionRestriction = dimensionRestriction;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    public int hashCode() {
        int hashCode = 0;
        if (this.columnModel != null) {
            hashCode = this.columnModel.getID().hashCode();
            if (this.periodModifier != null) {
                hashCode = hashCode * 31 + this.periodModifier.hashCode();
            }
            if (this.dimensionRestriction != null) {
                hashCode = hashCode * 31 + this.dimensionRestriction.hashCode();
            }
        } else if (this.expression != null) {
            hashCode = this.expression.hashCode();
        }
        return hashCode;
    }

    public boolean equals(Object obj) {
        return this.hashCode() - obj.hashCode() == 0;
    }

    public String toString() {
        if (this.expression != null) {
            return this.expression;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("[").append(this.columnModel.getCode());
        if (this.periodModifier != null) {
            buffer.append(",").append(this.periodModifier.toString());
        }
        if (this.dimensionRestriction != null) {
            for (int i = 0; i < this.dimensionRestriction.size(); ++i) {
                Object valueObject = this.dimensionRestriction.getValue(i);
                if (valueObject == null) continue;
                buffer.append(",").append(this.dimensionRestriction.getName(i)).append("=").append(valueObject);
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}

