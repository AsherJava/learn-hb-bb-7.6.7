/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryRow;
import java.util.ArrayList;
import java.util.List;

public class SummaryDataSet {
    private int floatOrderFieldIndex;
    private List<SummaryRow> rows = new ArrayList<SummaryRow>();
    private IDataUpdator updator;

    public SummaryDataSet(IDataUpdator updator) {
        this.updator = updator;
    }

    public SummaryDataSet(IDataUpdator updator, int floatOrderFieldIndex) {
        this.updator = updator;
        this.floatOrderFieldIndex = floatOrderFieldIndex;
    }

    public void statistic(SumContext context) throws SyntaxException {
        for (SummaryRow row : this.rows) {
            row.statistic(context);
        }
    }

    public void save(SumContext context) throws Exception {
        if (this.floatOrderFieldIndex >= 0) {
            double floatOrder = 1.0;
            for (SummaryRow row : this.rows) {
                row.save(context, this.updator, this.floatOrderFieldIndex, floatOrder);
                floatOrder += 1.0;
            }
        } else {
            for (SummaryRow row : this.rows) {
                row.save(context, this.updator);
            }
        }
        this.updator.commitChanges(true);
    }

    public List<SummaryRow> getRows() {
        return this.rows;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        int i = 1;
        for (SummaryRow row : this.rows) {
            buff.append(row).append("\n");
            if (++i <= 10) continue;
            buff.append("......").append("\n");
            break;
        }
        return buff.toString();
    }
}

