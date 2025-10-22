/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DataColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;

public class DataQueryColumn
extends DataColumn {
    private static final long serialVersionUID = -7805662486144543751L;
    private boolean isOrderBy;
    private boolean descending = false;
    private boolean specified = false;
    private int index;
    private IASTNode parsedNode;

    public DataQueryColumn(FieldDefine field) {
        super(field);
    }

    public DataQueryColumn(FieldDefine field, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        super(field, periodModifier, dimensionRestriction);
    }

    public DataQueryColumn(String expression) {
        super(expression);
    }

    public DataQueryColumn(FieldDefine keyField, FieldDefine valueField) {
        super(keyField, valueField);
    }

    public DataQueryColumn(FieldDefine keyField, EntityViewDefine entityViewDefine) {
        super(keyField, entityViewDefine);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isOrderBy() {
        return this.isOrderBy;
    }

    public void setOrderBy(boolean isOrderBy) {
        this.isOrderBy = isOrderBy;
    }

    public boolean isDescending() {
        return this.descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }

    public IASTNode getParsedNode() {
        return this.parsedNode;
    }

    public void setParsedNode(IASTNode parsedNode) {
        this.parsedNode = parsedNode;
    }

    public boolean isSpecified() {
        return this.specified;
    }

    public void setSpecified(boolean specified) {
        this.specified = specified;
    }
}

