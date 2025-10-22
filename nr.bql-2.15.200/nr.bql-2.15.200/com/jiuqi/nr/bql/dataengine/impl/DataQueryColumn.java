/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.bql.dataengine.impl.DataColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class DataQueryColumn
extends DataColumn {
    private static final long serialVersionUID = -7805662486144543751L;
    private boolean isOrderBy;
    private boolean descending;
    private int index;
    private boolean specified = false;
    private IASTNode parsedNode;

    public DataQueryColumn(ColumnModelDefine columnModel) {
        super(columnModel);
    }

    public DataQueryColumn(ColumnModelDefine columnModel, PeriodModifier periodModifier, DimensionValueSet dimensionRestriction) {
        super(columnModel, periodModifier, dimensionRestriction);
    }

    public DataQueryColumn(String expression) {
        super(expression);
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

    @Override
    public String toString() {
        if (StringUtils.isNotEmpty((String)this.expression)) {
            return this.expression;
        }
        return this.columnModel.getCode();
    }
}

