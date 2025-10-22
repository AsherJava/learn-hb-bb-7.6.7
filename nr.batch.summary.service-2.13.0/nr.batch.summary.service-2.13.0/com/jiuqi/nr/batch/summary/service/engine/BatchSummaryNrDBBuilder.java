/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumnMap;
import com.jiuqi.nr.batch.summary.service.table.PowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.PowerTableColumnMap;
import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregateType;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BatchSummaryNrDBBuilder {
    public Set<IPowerTableColumnMap> getJoinColumns(ITableEntity tempTableModel, OriTableModelInfo oriTableModel) {
        BSTableColumn dwColumn = oriTableModel.getDWColumn();
        List<BSBizKeyColumn> situationColumns = oriTableModel.getSituationColumns();
        LinkedHashSet<IPowerTableColumnMap> joinColumns = new LinkedHashSet<IPowerTableColumnMap>();
        joinColumns.add(new PowerTableColumnMap(dwColumn.getColumnName(), dwColumn.getColumnName()));
        situationColumns.forEach(col -> {
            LogicField tempColumn = tempTableModel.findColumn(col.getColumnName());
            if (tempColumn != null) {
                joinColumns.add(new PowerTableColumnMap(tempColumn.getFieldName(), col.getColumnName()));
            }
        });
        return joinColumns;
    }

    public Set<String> getGroupColumns(OriTableModelInfo oriTableModel) {
        LinkedHashSet<String> groupColumns = new LinkedHashSet<String>();
        groupColumns.add("DM_KJ");
        List<BSBizKeyColumn> situationColumns = oriTableModel.getSituationColumns();
        situationColumns.forEach(col -> {
            if (!col.isCorporate()) {
                groupColumns.add(col.getColumnName());
            }
        });
        List<BSBizKeyColumn> bizKeyColumns = oriTableModel.getBizKeyColumns();
        bizKeyColumns.forEach(col -> groupColumns.add(col.getColumnName()));
        return groupColumns;
    }

    public Set<IPowerTableColumn> getAggregateColumns(OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, SummaryScheme summaryScheme) {
        LinkedHashSet<IPowerTableColumn> aggregateColumns = new LinkedHashSet<IPowerTableColumn>();
        this.appendGatherColumn(sumTableModel.getGatherColumn(), aggregateColumns, summaryScheme);
        this.appendDWColumn(oriTableModel.getDWColumn(), aggregateColumns);
        this.appendPeriodColumn(oriTableModel.getPeriodColumn(), aggregateColumns);
        this.appendSituationColumns(oriTableModel.getSituationColumns(), aggregateColumns);
        this.appendBizKeyColumns(oriTableModel.getBizKeyColumns(), aggregateColumns);
        this.appendBuildColumns(oriTableModel.getBuildColumns(), aggregateColumns);
        this.appendZBColumns(oriTableModel.getZBColumns(), aggregateColumns);
        return aggregateColumns;
    }

    protected void appendGatherColumn(BSBizKeyColumn gatherColumn, Set<IPowerTableColumn> aggregateColumns, SummaryScheme summaryScheme) {
        aggregateColumns.add(new PowerTableColumn(gatherColumn.getColumnName(), IAggregateType.NO_AGGREGATION, summaryScheme.getKey()));
    }

    protected void appendDWColumn(BSTableColumn dwColumn, Set<IPowerTableColumn> aggregateColumns) {
        aggregateColumns.add(new PowerTableColumn("DM_KJ", IAggregateType.MIN, ColumnModelType.STRING));
    }

    private void appendPeriodColumn(BSTableColumn periodColumn, Set<IPowerTableColumn> aggregateColumns) {
        aggregateColumns.add(new PowerTableColumn(periodColumn.getColumnName(), IAggregateType.getAggregateType(periodColumn.getSQLGroupFunc()), periodColumn.getColumnModel().getColumnType()));
    }

    protected void appendSituationColumns(List<BSBizKeyColumn> situationColumns, Set<IPowerTableColumn> aggregateColumns) {
        situationColumns.forEach(col -> {
            if (col.isCorporate()) {
                aggregateColumns.add(new PowerTableColumn(col.getColumnName(), IAggregateType.NO_AGGREGATION, col.getDefaultValue()));
            } else {
                aggregateColumns.add(new PowerTableColumn(col.getColumnName(), IAggregateType.getAggregateType(col.getSQLGroupFunc()), col.getColumnModel().getColumnType()));
            }
        });
    }

    protected void appendBizKeyColumns(List<BSBizKeyColumn> bizKeyColumns, Set<IPowerTableColumn> aggregateColumns) {
        bizKeyColumns.forEach(col -> aggregateColumns.add(new PowerTableColumn(col.getColumnName(), IAggregateType.getAggregateType(col.getSQLGroupFunc()), col.getColumnModel().getColumnType())));
    }

    protected void appendBuildColumns(List<BSBizKeyColumn> buildColumns, Set<IPowerTableColumn> aggregateColumns) {
        buildColumns.forEach(col -> aggregateColumns.add(new PowerTableColumn(col.getColumnName(), IAggregateType.getAggregateType(col.getSQLGroupFunc()), col.getColumnModel().getColumnType())));
    }

    protected void appendZBColumns(List<BSTableColumn> zbColumns, Set<IPowerTableColumn> aggregateColumns) {
        zbColumns.forEach(col -> aggregateColumns.add(new PowerTableColumn(col.getColumnName(), IAggregateType.getAggregateType(col.getSQLGroupFunc()), col.getColumnModel().getColumnType())));
    }
}

