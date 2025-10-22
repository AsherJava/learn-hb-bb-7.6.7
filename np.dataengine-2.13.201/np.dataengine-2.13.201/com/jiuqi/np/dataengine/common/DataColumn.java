/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import java.io.Serializable;

public class DataColumn
implements Serializable {
    private static final long serialVersionUID = 4664241113970040972L;
    public static final int COLUMN_TYPE_FIELD = 0;
    public static final int COLUMN_TYPE_DIM_FIELD = 1;
    public static final int COLUMN_TYPE_EXPRESSION = 2;
    public static final int COLUMN_TYPE_LOOKUP = 3;
    protected int type;
    protected FieldDefine field;
    protected PeriodModifier periodModifier;
    protected DimensionValueSet dimensionRestriction;
    protected String expression;
    protected FieldDefine valueField;
    protected EntityViewDefine entityViewDefine;

    public DataColumn(FieldDefine field) {
        this.field = field;
        this.type = 0;
    }

    public DataColumn(FieldDefine field, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        this.field = field;
        this.periodModifier = periodModifier;
        this.dimensionRestriction = dimensionRestriction;
        this.type = 1;
    }

    public DataColumn(String expression) {
        this.expression = expression;
        this.type = 2;
    }

    public DataColumn(FieldDefine keyField, FieldDefine valueField) {
        this.field = keyField;
        this.valueField = valueField;
        this.type = 3;
    }

    public DataColumn(FieldDefine keyField, EntityViewDefine entityViewDefine) {
        this.field = keyField;
        this.entityViewDefine = entityViewDefine;
        this.type = 3;
    }

    public int getType() {
        return this.type;
    }

    public FieldDefine getField() {
        return this.field;
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

    public FieldDefine getValueField() {
        return this.valueField;
    }

    public EntityViewDefine getEntityView() {
        return this.entityViewDefine;
    }

    public EntityViewDefine getEntityViewDefine() {
        return this.entityViewDefine;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setField(FieldDefine field) {
        this.field = field;
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

    public void setValueField(FieldDefine valueField) {
        this.valueField = valueField;
    }

    public void setEntityViewDefine(EntityViewDefine entityViewDefine) {
        this.entityViewDefine = entityViewDefine;
    }

    public int hashCode() {
        int hashCode = 0;
        if (this.field != null) {
            hashCode = this.field.getKey().hashCode();
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
        buffer.append(this.field.getOwnerTableKey()).append("[").append(this.field.getCode());
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

