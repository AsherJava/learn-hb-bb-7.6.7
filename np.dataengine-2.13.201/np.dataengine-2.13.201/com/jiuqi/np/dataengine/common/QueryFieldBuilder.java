/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.IQueryField;
import com.jiuqi.np.dataengine.common.SpecialValue;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.period.PeriodModifier;
import java.io.Serializable;

public class QueryFieldBuilder
implements IQueryField,
Serializable {
    private static final long serialVersionUID = 8148044009902836838L;
    private PeriodModifier modifier;
    private DimensionValueSet restriction;
    private DimensionSet openDimensions;
    private String tableName;
    private String fieldName;
    private int dataType;
    private boolean isLj;
    private int fractionDigits;
    private int fieldSize;

    public QueryFieldBuilder(String tableName, String fieldName, int dataType, int fractionDigits, DimensionSet tableDimensions) {
        this.restriction = new DimensionValueSet(tableDimensions);
        this.setTableName(tableName);
        this.setFieldName(fieldName);
        this.setDataType(dataType);
        this.setFractionDigits(fractionDigits);
    }

    public QueryFieldBuilder(String tableName, String fieldName, int dataType, int fractionDigits, DimensionValueSet restriction) {
        this.restriction = restriction;
        this.setTableName(tableName);
        this.setFieldName(fieldName);
        this.setDataType(dataType);
        this.setFractionDigits(fractionDigits);
    }

    @Override
    public final String getTableName() {
        return this.tableName;
    }

    private void setTableName(String value) {
        this.tableName = value;
    }

    @Override
    public final String getFieldName() {
        return this.fieldName;
    }

    private void setFieldName(String value) {
        this.fieldName = value;
    }

    @Override
    public final int getDataType() {
        return this.dataType;
    }

    private void setDataType(int value) {
        this.dataType = value;
    }

    @Override
    public final boolean getIsLj() {
        return this.isLj;
    }

    public final void setIsLj(boolean value) {
        this.isLj = value;
    }

    @Override
    public final int getFractionDigits() {
        return this.fractionDigits;
    }

    private void setFractionDigits(int value) {
        this.fractionDigits = value;
    }

    @Override
    public int getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(int value) {
        this.fieldSize = value;
    }

    @Override
    public final DimensionSet getTableDimensions() {
        return this.restriction.getDimensionSet();
    }

    @Override
    public final PeriodModifier getPeriodModifier() {
        return this.modifier == null || this.modifier.isEmpty() ? null : this.modifier;
    }

    public final void combinePeriodModifier(ExecutorContext executorContext, PeriodModifier value, boolean supportVersion) throws ExpressionException {
        int index;
        if (value == null || value.isEmpty()) {
            return;
        }
        if (!supportVersion && (index = this.restriction.getDimensionSet().indexOf("DATATIME")) < 0) {
            throw new ExpressionException("\u4e0d\u5b58\u5728\u65f6\u671f\u7ef4\u5ea6\uff0c\u4e0d\u80fd\u8bbe\u7f6e\u65f6\u671f\u504f\u79fb");
        }
        if (this.modifier == null) {
            this.modifier = value;
        }
        this.openDimensions = null;
    }

    @Override
    public final DimensionValueSet getDimensionRestriction() {
        return this.restriction.isAllNull() ? null : this.restriction;
    }

    public final String addRestriction(String dimension, Object value, int dataType) {
        int index = this.restriction.getDimensionSet().indexOf(dimension);
        if (index < 0) {
            return String.format("\u4e0d\u5b58\u5728\u7ef4\u5ea6%1$s\uff0c\u9650\u5b9a\u503c\u8bbe\u7f6e\u65e0\u6548", dimension);
        }
        if (this.restriction.getValue(index) != null) {
            return String.format("\u7ef4\u5ea6%1$s\u5df2\u7ecf\u8bbe\u7f6e\u9650\u5b9a\u503c", dimension);
        }
        this.openDimensions = null;
        this.restriction.setValue(dimension, value);
        return null;
    }

    public final void combineRestriction(DimensionValueSet another) {
        if (this.restriction == null) {
            return;
        }
        this.openDimensions = null;
        this.restriction.combine(another);
    }

    public final String setOpenDimension(String dimension) {
        int index = this.restriction.getDimensionSet().indexOf(dimension);
        if (index < 0) {
            return String.format("\u4e0d\u5b58\u5728\u7ef4\u5ea6%1$s\uff0c\u9650\u5b9a\u503c\u8bbe\u7f6e\u65e0\u6548", dimension);
        }
        if (this.restriction.getValue(index) != null) {
            return String.format("\u7ef4\u5ea6%1$s\u5df2\u7ecf\u8bbe\u7f6e\u9650\u5b9a\u503c", dimension);
        }
        this.openDimensions = null;
        this.restriction.setValue(dimension, DimensionValueSet.NoStrictDimValue);
        return null;
    }

    public final boolean hasSumMerge() {
        return this.restriction.hasSumMerge();
    }

    public final String setMergeDimension(String dimension) {
        int index = this.restriction.getDimensionSet().indexOf(dimension);
        if (index < 0) {
            return String.format("\u4e0d\u5b58\u5728\u7ef4\u5ea6%1$s\uff0c\u9650\u5b9a\u503c\u8bbe\u7f6e\u65e0\u6548", dimension);
        }
        if (this.restriction.getValue(index) != null) {
            return String.format("\u7ef4\u5ea6%1$s\u5df2\u7ecf\u8bbe\u7f6e\u9650\u5b9a\u503c", dimension);
        }
        if (this.getDataType() >= 0) {
            return "\u6570\u636e\u7c7b\u578b\u4e0d\u652f\u6301\u5408\u5e76";
        }
        this.openDimensions = null;
        this.restriction.setValue(dimension, DimensionValueSet.SumMergeDimValue);
        return null;
    }

    public final boolean hasDimensionTranslate() {
        int c = this.restriction.getDimensionSet().size();
        for (int i = 0; i < c; ++i) {
            Object rv = this.restriction.getValue(i);
            if (!(rv instanceof SpecialValue) || rv == DimensionValueSet.SumMergeDimValue || rv == DimensionValueSet.NoStrictDimValue) continue;
            return true;
        }
        return false;
    }

    public final String setDimensionTranslate(String dimension, String tranalateField) {
        int index = this.restriction.getDimensionSet().indexOf(dimension);
        if (index < 0) {
            return String.format("\u4e0d\u5b58\u5728\u7ef4\u5ea6%1$s\uff0c\u9650\u5b9a\u503c\u8bbe\u7f6e\u65e0\u6548", dimension);
        }
        if (this.restriction.getValue(index) != null) {
            return String.format("\u7ef4\u5ea6%1$s\u5df2\u7ecf\u8bbe\u7f6e\u9650\u5b9a\u503c", dimension);
        }
        this.openDimensions = null;
        this.restriction.setValue(dimension, new SpecialValue(tranalateField));
        return null;
    }

    public final boolean isDimensionOpen(String dimension) {
        Object rvalue = this.restriction.getValue(dimension);
        return rvalue == null || rvalue == DimensionValueSet.NoStrictDimValue;
    }

    public final DimensionSet getOpenDimensions() {
        if (this.openDimensions == null) {
            if (this.getPeriodModifier() != null && this.modifier.getPeriodType() < 7 && !this.modifier.isRelative()) {
                this.restriction.setValue("DATATIME", this.modifier.modify("1900N0001"));
                this.modifier = null;
            }
            if (this.restriction.isAllNull()) {
                this.openDimensions = this.restriction.getDimensionSet();
                return this.openDimensions;
            }
            ENameSet nameSet = new ENameSet();
            int c = this.restriction.getDimensionSet().size();
            for (int i = 0; i < c; ++i) {
                Object rv = this.restriction.getValue(i);
                if (rv != null && (!(rv instanceof SpecialValue) || rv == DimensionValueSet.SumMergeDimValue)) continue;
                nameSet.add(this.restriction.getDimensionSet().get(i));
            }
            this.openDimensions = new DimensionSet(nameSet);
        }
        return this.openDimensions;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof QueryFieldBuilder)) {
            return false;
        }
        QueryFieldBuilder queryFieldBuilder = (QueryFieldBuilder)obj;
        if (!this.getTableName().equals(queryFieldBuilder.getTableName())) {
            return false;
        }
        if (this.restriction.isAllNull() && queryFieldBuilder.restriction.isAllNull()) {
            return true;
        }
        return this.restriction.equals(queryFieldBuilder.restriction);
    }

    public int hashCode() {
        int resultValue = this.tableName.hashCode();
        if (!this.restriction.isAllNull()) {
            resultValue = resultValue * 31 + this.restriction.hashCode();
        }
        if (this.modifier != null) {
            resultValue = resultValue * 31 + this.modifier.hashCode();
        }
        return resultValue;
    }
}

