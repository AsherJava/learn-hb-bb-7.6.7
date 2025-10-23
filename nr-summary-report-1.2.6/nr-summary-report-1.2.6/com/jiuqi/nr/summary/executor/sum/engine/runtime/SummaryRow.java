/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryCell;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryColumn;
import java.util.ArrayList;
import java.util.List;

public class SummaryRow {
    private DimensionValueSet rowKey;
    private List<SummaryColumn> columns = new ArrayList<SummaryColumn>();

    public SummaryRow(DimensionValueSet rowKey, List<RuntimeSummaryCell> cells) {
        this.rowKey = rowKey;
        for (RuntimeSummaryCell cell : cells) {
            SummaryColumn column = new SummaryColumn(cell);
            this.columns.add(column);
        }
    }

    public DimensionValueSet getRowKey() {
        return this.rowKey;
    }

    public void statistic(SumContext context) throws SyntaxException {
        for (SummaryColumn column : this.columns) {
            column.statistic(context);
        }
    }

    public void save(SumContext context, IDataUpdator updator, int floatOrderFieldIndex, double floatOrder) throws IncorrectQueryException {
        IDataRow targetRow = updator.addInsertedRow(this.rowKey);
        if (floatOrderFieldIndex >= 0) {
            targetRow.setValue(floatOrderFieldIndex, (Object)floatOrder);
        }
        for (SummaryColumn column : this.columns) {
            targetRow.setValue(column.getTargetFieldIndex(), column.getResult());
        }
    }

    public void save(SumContext context, IDataUpdator updator) throws IncorrectQueryException {
        this.save(context, updator, -1, -1.0);
    }

    public String toString() {
        return "SummaryRow [rowKey=" + this.rowKey + ", columns=" + this.columns + "]";
    }
}

