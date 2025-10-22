/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder
 *  com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.gather.gzw.service.engine;

import com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.util.List;

public class BatchGatherGZWSqlBuilder
extends BatchSummarySqlBuilder {
    public BatchGatherGZWSqlBuilder(SummaryScheme summaryScheme) {
        super(summaryScheme);
    }

    public void appendSituationColumns(List<BSBizKeyColumn> situationColumns, String alias) {
        situationColumns.forEach(col -> {
            if (col.isCorporate()) {
                this.selectSql.addSelectColumnMinValue(col.getColumnName(), col.getDefaultValue().toString(), col.getSQLGroupFunc());
            } else {
                this.selectSql.addSelectColumn(col.getColumnName(), alias, col.getSQLGroupFunc());
            }
            this.queryColumns.add(col.getColumnName());
        });
    }
}

