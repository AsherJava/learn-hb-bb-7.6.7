/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableSelectSqlBuilder;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import java.util.ArrayList;
import java.util.List;

public class BatchSummarySqlBuilder {
    public static String bizkeyOrder = "BIZKEYORDER";
    protected final SummaryScheme summaryScheme;
    protected final ITableSelectSqlBuilder selectSql = new ITableSelectSqlBuilder();
    protected final List<String> queryColumns = new ArrayList<String>();

    public BatchSummarySqlBuilder(SummaryScheme summaryScheme) {
        this.summaryScheme = summaryScheme;
    }

    public void appendGatherColumn(BSBizKeyColumn gatherColumn) {
        this.queryColumns.add(gatherColumn.getColumnName());
        this.selectSql.addSelectColumnMinValue(gatherColumn.getColumnName(), this.summaryScheme.getKey(), gatherColumn.getSQLGroupFunc());
    }

    public void appendDWColumn(BSTableColumn dwColumn, String alias) {
        this.queryColumns.add(dwColumn.getColumnName());
        this.selectSql.addSelectColumn("DM_KJ", alias);
    }

    public void appendPeriodColumn(BSTableColumn periodColumn, String alias) {
        this.queryColumns.add(periodColumn.getColumnName());
        this.selectSql.addSelectColumn(periodColumn.getColumnName(), alias, periodColumn.getSQLGroupFunc());
    }

    public void appendSituationColumns(List<BSBizKeyColumn> situationColumns, String alias) {
        situationColumns.forEach(col -> {
            if (col.isCorporate() && col.getDefaultValue() != null) {
                this.selectSql.addSelectColumnMinValue(col.getColumnName(), col.getDefaultValue().toString(), col.getSQLGroupFunc());
            } else {
                this.selectSql.addSelectColumn(col.getColumnName(), alias, col.getSQLGroupFunc());
            }
            this.queryColumns.add(col.getColumnName());
        });
    }

    public void appendBizKeyColumns(List<BSBizKeyColumn> bizKeyColumns, String alias) {
        bizKeyColumns.forEach(col -> {
            this.selectSql.addSelectColumn(col.getColumnName(), alias, col.getSQLGroupFunc());
            this.queryColumns.add(col.getColumnName());
        });
    }

    public void appendBuildColumns(List<BSBizKeyColumn> buildColumns, String alias) {
        buildColumns.forEach(col -> {
            this.selectSql.addSelectColumn(col.getColumnName(), alias, col.getSQLGroupFunc());
            this.queryColumns.add(col.getColumnName());
        });
    }

    public void appendZBColumns(List<BSTableColumn> zbColumns, String alias) {
        zbColumns.forEach(col -> {
            this.selectSql.addSelectColumn(col.getColumnName(), alias, col.getSQLGroupFunc());
            this.queryColumns.add(col.getColumnName());
        });
    }

    public void fromTable(String tableName) {
        this.selectSql.from(tableName);
    }

    public void rightJoinTable(String tableName) {
        this.selectSql.rightJoin(tableName);
    }

    public void joinOnCondition(ITableEntity tempTable, OriTableModelInfo oriTableModel) {
        BSTableColumn dwColumn = oriTableModel.getDWColumn();
        this.selectSql.joinOnCondition(tempTable.getTableName(), dwColumn.getColumnName(), oriTableModel.getTableName(), dwColumn.getColumnName());
        List<BSBizKeyColumn> situationColumns = oriTableModel.getSituationColumns();
        situationColumns.forEach(col -> {
            LogicField tempColumn = tempTable.findColumn(col.getColumnName());
            if (tempColumn != null) {
                this.selectSql.joinAndCondition(tempTable.getTableName(), tempColumn.getFieldName(), oriTableModel.getTableName(), col.getColumnName());
            }
        });
    }

    public void whereCondition(OriTableModelInfo oriTableModel, String period) {
        BSTableColumn periodColumn = oriTableModel.getPeriodColumn();
        this.selectSql.where(oriTableModel.getTableName(), periodColumn.getColumnName(), period);
    }

    public void groupCondition(ITableEntity tempTable, OriTableModelInfo oriTableModel) {
        List<BSBizKeyColumn> situationColumns = oriTableModel.getSituationColumns();
        this.selectSql.groupBy(tempTable.getTableName(), "DM_KJ");
        situationColumns.forEach(col -> {
            if (!col.isCorporate()) {
                this.selectSql.addGroupByColumn(oriTableModel.getTableName(), col.getColumnName());
            }
        });
        List<BSBizKeyColumn> bizKeyColumns = oriTableModel.getBizKeyColumns();
        bizKeyColumns.forEach(col -> this.selectSql.addGroupByColumn(oriTableModel.getTableName(), col.getColumnName()));
    }

    public void end() {
        this.selectSql.end();
    }

    public ITableSelectSqlBuilder getSelectSummaryBuilder() {
        return this.selectSql;
    }

    public List<String> getQuerySelectColumns() {
        return this.queryColumns;
    }

    public String toString() {
        return this.selectSql.toString();
    }
}

