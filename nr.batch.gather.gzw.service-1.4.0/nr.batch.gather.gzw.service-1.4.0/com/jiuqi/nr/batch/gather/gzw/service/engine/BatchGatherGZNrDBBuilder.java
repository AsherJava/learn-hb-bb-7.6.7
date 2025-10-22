/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBBuilder
 *  com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn
 *  com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn
 *  com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo
 *  com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.gather.gzw.service.engine;

import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBBuilder;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.util.Set;

public class BatchGatherGZNrDBBuilder
extends BatchSummaryNrDBBuilder {
    public Set<IPowerTableColumn> getAggregateColumns(OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, SummaryScheme summaryScheme) {
        return super.getAggregateColumns(oriTableModel, sumTableModel, summaryScheme);
    }

    protected void appendGatherColumn(BSBizKeyColumn gatherColumn, Set<IPowerTableColumn> aggregateColumns, SummaryScheme summaryScheme) {
    }
}

