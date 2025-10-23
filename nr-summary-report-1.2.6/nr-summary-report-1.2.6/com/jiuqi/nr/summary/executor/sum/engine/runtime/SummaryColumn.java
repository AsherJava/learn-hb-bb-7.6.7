/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryCell;

public class SummaryColumn {
    private RuntimeSummaryCell cell;
    private StatUnit statUnit;

    public SummaryColumn(RuntimeSummaryCell cell) {
        this.cell = cell;
        this.statUnit = StatItem.createStatUnit((int)cell.getStatKind(), (int)cell.getResultDataType());
    }

    public void statistic(SumContext context) throws SyntaxException {
        if (context.getCurrentRegion().getJudger().getResult(this.cell.getConditions())) {
            AbstractData cellResult = AbstractData.valueOf((Object)this.cell.evaluate(context), (int)this.cell.getResultDataType());
            this.statUnit.statistic(cellResult);
        }
    }

    public int getTargetFieldIndex() {
        return this.cell.getTargetFieldIndex();
    }

    public Object getResult() {
        AbstractData result = this.statUnit.getResult();
        return result.getAsObject();
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(this.cell.getTargetExp()).append("=");
        buff.append(StatItem.STAT_KIND_MODE_NAMES[this.statUnit.getStatKind()]);
        buff.append("(").append(this.cell.getCellDefine().getExp()).append(")");
        buff.append(this.cell.getConditions().toString());
        return buff.toString();
    }
}

