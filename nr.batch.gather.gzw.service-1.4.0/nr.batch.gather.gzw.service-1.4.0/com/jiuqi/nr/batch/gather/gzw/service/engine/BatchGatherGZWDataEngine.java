/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.common.StringLogger
 *  com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil
 *  com.jiuqi.nr.batch.summary.service.dbutil.ITableDeleteSqlBuilder
 *  com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummaryDataEngine
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBSaver
 *  com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder
 *  com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider
 *  com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo
 *  com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo
 *  com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.gather.gzw.service.engine;

import com.jiuqi.nr.batch.gather.gzw.service.engine.BatchGatherGZWSqlBuilder;
import com.jiuqi.nr.batch.summary.common.StringLogger;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDeleteSqlBuilder;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryDataEngine;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBSaver;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder;
import com.jiuqi.nr.batch.summary.service.targetdim.TargetDimProvider;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.util.List;

public class BatchGatherGZWDataEngine
extends BatchSummaryDataEngine {
    public BatchGatherGZWDataEngine(SummaryScheme summaryScheme, TargetFromProvider targetFromProvider, TargetDimProvider targetDimProvider, ITableDBUtil tableDBUtil, BatchSummaryNrDBSaver nrDBSaver, StringLogger logger) {
        super(summaryScheme, targetFromProvider, targetDimProvider, tableDBUtil, nrDBSaver, logger);
    }

    protected BatchSummarySqlBuilder makeSummarySQL(ITableEntity tempTable, OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, String period) {
        BatchGatherGZWSqlBuilder sumSqlBuilder = new BatchGatherGZWSqlBuilder(this.summaryScheme);
        sumSqlBuilder.appendDWColumn(oriTableModel.getDWColumn(), tempTable.getTableName());
        sumSqlBuilder.appendPeriodColumn(oriTableModel.getPeriodColumn(), oriTableModel.getTableName());
        sumSqlBuilder.appendSituationColumns(oriTableModel.getSituationColumns(), oriTableModel.getTableName());
        sumSqlBuilder.appendBizKeyColumns(oriTableModel.getBizKeyColumns(), oriTableModel.getTableName());
        sumSqlBuilder.appendBuildColumns(oriTableModel.getBuildColumns(), oriTableModel.getTableName());
        sumSqlBuilder.appendZBColumns(oriTableModel.getZBColumns(), oriTableModel.getTableName());
        sumSqlBuilder.fromTable(oriTableModel.getTableName());
        sumSqlBuilder.rightJoinTable(tempTable.getTableName());
        sumSqlBuilder.joinOnCondition(tempTable, oriTableModel);
        sumSqlBuilder.whereCondition(oriTableModel, period);
        sumSqlBuilder.groupCondition(tempTable, oriTableModel);
        sumSqlBuilder.end();
        return sumSqlBuilder;
    }

    protected ITableDeleteSqlBuilder makeClearTableDataSql(SumTableModelInfo sumTableModel, String period) {
        String sumTableName = sumTableModel.getTableModel().getCode();
        String dwColumnCode = sumTableModel.getDWColumn().getColumnModel().getCode();
        String periodColumnCode = sumTableModel.getPeriodColumn().getColumnModel().getCode();
        List situationColumns = sumTableModel.getSituationColumns();
        ITableDeleteSqlBuilder sqlBuilder = new ITableDeleteSqlBuilder(sumTableName);
        sqlBuilder.whereInCondition(dwColumnCode, this.targetDimProvider.getTargetDims(period));
        sqlBuilder.andWhereCondition(periodColumnCode, period);
        situationColumns.forEach(column -> {
            if (column.isCorporate()) {
                Object defaultValue = column.getDefaultValue();
                if (defaultValue == null) {
                    throw new IllegalArgumentException("\u6c47\u603b\u65b9\u6848\uff1a" + this.summaryScheme.getCode() + "\uff0c\u7eac\u5ea6\uff1a" + column.getColumnModel().getCode() + "\uff0c\u9ed8\u8ba4\u503c\u4e0d\u80fd\u4e3a\u7a7a");
                }
                sqlBuilder.andWhereCondition(column.getColumnModel().getCode(), defaultValue.toString());
            }
        });
        sqlBuilder.end();
        return sqlBuilder;
    }
}

