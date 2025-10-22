/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.nr.batch.summary.common.StringLogger;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummaryNrDBBuilder;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumnMap;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableEntity;
import com.jiuqi.nr.batch.summary.service.table.PowerTableEntity;
import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumn;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BatchSummaryNrDBSaver {
    protected static final String BIZKEYORDER = "BIZKEYORDER";
    protected StringLogger logger;
    protected NrdbHelper nrdbHelper;
    protected ITableDBUtil tableDBUtil;
    protected SummaryScheme summaryScheme;
    protected DataModelService dataModelService;
    protected INvwaDataAccessProvider dataAccessProvider;
    protected IPowerTableEntity tempTableData;

    public BatchSummaryNrDBSaver(StringLogger logger, NrdbHelper nrdbHelper, SummaryScheme summaryScheme, ITableDBUtil tableDBUtil, DataModelService dataModelService, INvwaDataAccessProvider dataAccessProvider) {
        this.logger = logger;
        this.nrdbHelper = nrdbHelper;
        this.tableDBUtil = tableDBUtil;
        this.summaryScheme = summaryScheme;
        this.dataModelService = dataModelService;
        this.dataAccessProvider = dataAccessProvider;
    }

    public boolean isEnableNrdb() {
        return this.nrdbHelper.isEnableNrdb();
    }

    public void sumTableDataWithNrDB(Connection connection, ITableEntity tempTable, OriTableModelInfo oriTableModel, SumTableModelInfo sumTableModel, String period, double progress) throws Exception {
        this.logger.logInfo("\u6b63\u5728\u6e05\u7a7a\u5386\u53f2\u6570\u636e...");
        this.clearHistoryTableData(sumTableModel, period);
        this.logger.addProcess(progress / 3.0);
        this.logger.logInfo("\u6b63\u5728\u6267\u884c\u6570\u636e\u6c47\u603b...");
        IPowerTableEntity tempTableData = this.tempTableData(connection, tempTable, sumTableModel);
        IPowerTableEntity zbTableData = this.dataFiledTableData(oriTableModel, period);
        IPowerTableEntity sumTableData = this.summaryZBTableData(tempTable, tempTableData, oriTableModel, zbTableData, sumTableModel);
        this.logger.addProcess(progress / 3.0);
        this.saveSumTableData(sumTableModel, sumTableData);
    }

    protected void saveSumTableData(SumTableModelInfo sumTableModel, IPowerTableEntity sumTableData) throws Exception {
        if (!sumTableData.isEmpty()) {
            HashMap<ColumnModelDefine, String> columnCodeMap = new HashMap<ColumnModelDefine, String>();
            this.putGatherColumnToSumTableColumnMap(sumTableModel, columnCodeMap);
            columnCodeMap.put(sumTableModel.getDWColumn().getColumnModel(), "DM_KJ");
            columnCodeMap.put(sumTableModel.getPeriodColumn().getColumnModel(), sumTableModel.getPeriodColumn().getColumnName());
            sumTableModel.getSituationColumns().forEach(col -> columnCodeMap.put(col.getColumnModel(), col.getColumnName()));
            sumTableModel.getBizKeyColumns().forEach(col -> columnCodeMap.put(col.getColumnModel(), col.getColumnName()));
            sumTableModel.getBuildColumns().forEach(col -> columnCodeMap.put(col.getColumnModel(), col.getColumnName()));
            sumTableModel.getZBColumns().forEach(col -> columnCodeMap.put(col.getColumnModel(), col.getColumnName()));
            NvwaQueryModel queryModel = this.newNvwaQueryModel(sumTableModel.getTableModel());
            List queryColumns = queryModel.getColumns();
            this.appendGatherQueryColumn(sumTableModel, queryColumns);
            this.appendSituationColumns(sumTableModel, queryColumns);
            this.appendBizKeyColumns(sumTableModel, queryColumns);
            this.appendBuildColumns(sumTableModel, queryColumns);
            this.appendZBColumns(sumTableModel, queryColumns);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
            for (int rowIdx = 0; rowIdx < sumTableData.getRowCount(); ++rowIdx) {
                ArrayKey arrayKey = this.getArrayKey(columnCodeMap, dataUpdator.getRowKeyColumns(), sumTableData, rowIdx);
                INvwaDataRow dataRow = dataUpdator.addUpdateOrInsertRow(arrayKey);
                for (int colIdx = 0; colIdx < queryColumns.size(); ++colIdx) {
                    dataRow.setValue(colIdx, sumTableData.getCellValue(rowIdx, (String)columnCodeMap.get(((NvwaQueryColumn)queryColumns.get(colIdx)).getColumnModel())));
                }
            }
            dataUpdator.commitChanges(context);
        }
    }

    protected void putGatherColumnToSumTableColumnMap(SumTableModelInfo sumTableModel, Map<ColumnModelDefine, String> columnCodeMap) {
        columnCodeMap.put(sumTableModel.getGatherColumn().getColumnModel(), sumTableModel.getGatherColumn().getColumnName());
    }

    protected void appendGatherQueryColumn(SumTableModelInfo sumTableModel, List<NvwaQueryColumn> queryColumns) {
        String bizKeys = sumTableModel.getTableModel().getBizKeys();
        if (!bizKeys.contains(sumTableModel.getGatherColumn().getColumnModel().getID())) {
            queryColumns.add(new NvwaQueryColumn(sumTableModel.getGatherColumn().getColumnModel()));
        }
    }

    protected void appendSituationColumns(SumTableModelInfo sumTableModel, List<NvwaQueryColumn> queryColumns) {
        String bizKeys = sumTableModel.getTableModel().getBizKeys();
        sumTableModel.getSituationColumns().forEach(column -> {
            if (!bizKeys.contains(column.getColumnModel().getID())) {
                queryColumns.add(new NvwaQueryColumn(column.getColumnModel()));
            }
        });
    }

    protected void appendBizKeyColumns(SumTableModelInfo sumTableModel, List<NvwaQueryColumn> queryColumns) {
        String bizKeys = sumTableModel.getTableModel().getBizKeys();
        sumTableModel.getBizKeyColumns().forEach(column -> {
            if (!bizKeys.contains(column.getColumnModel().getID())) {
                queryColumns.add(new NvwaQueryColumn(column.getColumnModel()));
            }
        });
    }

    protected void appendBuildColumns(SumTableModelInfo sumTableModel, List<NvwaQueryColumn> queryColumns) {
        String bizKeys = sumTableModel.getTableModel().getBizKeys();
        sumTableModel.getBuildColumns().forEach(column -> {
            if (!bizKeys.contains(column.getColumnModel().getID())) {
                queryColumns.add(new NvwaQueryColumn(column.getColumnModel()));
            }
        });
    }

    protected void appendZBColumns(SumTableModelInfo sumTableModel, List<NvwaQueryColumn> queryColumns) {
        sumTableModel.getZBColumns().forEach(tableColumn -> queryColumns.add(new NvwaQueryColumn(tableColumn.getColumnModel())));
    }

    protected ArrayKey getArrayKey(Map<ColumnModelDefine, String> columnCodeMap, List<ColumnModelDefine> rowKeyColumns, IPowerTableEntity sumTableData, int rowIdx) {
        ArrayList<Object> rowKeys = new ArrayList<Object>();
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            rowKeys.add(sumTableData.getCellValue(rowIdx, columnCodeMap.get(rowKeyColumn)));
        }
        return new ArrayKey(rowKeys);
    }

    protected IPowerTableEntity tempTableData(Connection connection, ITableEntity tempTable, SumTableModelInfo sumTableModel) {
        if (this.tempTableData == null) {
            Map<String, Object> otherColumnValues = this.tempTableDataOtherColumnValues(sumTableModel);
            this.tempTableData = this.tableDBUtil.selectSQLImplement(connection, tempTable.getTableName(), tempTable.getAllColumns().stream().map(LogicField::getFieldName).collect(Collectors.toList()), otherColumnValues);
        }
        return this.tempTableData;
    }

    protected Map<String, Object> tempTableDataOtherColumnValues(SumTableModelInfo sumTableModel) {
        HashMap<String, Object> otherColumnValues = new HashMap<String, Object>();
        otherColumnValues.put(sumTableModel.getGatherColumn().getColumnName(), this.summaryScheme.getKey());
        return otherColumnValues;
    }

    protected IPowerTableEntity summaryZBTableData(ITableEntity tempTableModel, IPowerTableEntity tempTableData, OriTableModelInfo oriTableModel, IPowerTableEntity zbTableData, SumTableModelInfo sumTableModel) {
        BatchSummaryNrDBBuilder nrDBSummaryBuilder = this.getBatchSummaryNrDBBuilder();
        Set<IPowerTableColumnMap> joinColumns = nrDBSummaryBuilder.getJoinColumns(tempTableModel, oriTableModel);
        IPowerTableEntity joinTableData = tempTableData.andJoinRows(zbTableData, joinColumns);
        Set<String> groupColumns = nrDBSummaryBuilder.getGroupColumns(oriTableModel);
        Set<IPowerTableColumn> aggregateColumns = nrDBSummaryBuilder.getAggregateColumns(oriTableModel, sumTableModel, this.summaryScheme);
        return joinTableData.groupByRows(groupColumns, aggregateColumns);
    }

    protected BatchSummaryNrDBBuilder getBatchSummaryNrDBBuilder() {
        return new BatchSummaryNrDBBuilder();
    }

    protected IPowerTableEntity dataFiledTableData(OriTableModelInfo oriTableModel, String period) throws Exception {
        ArrayList<ColumnModelDefine> selectColumns = new ArrayList<ColumnModelDefine>();
        selectColumns.add(oriTableModel.getDWColumn().getColumnModel());
        selectColumns.add(oriTableModel.getPeriodColumn().getColumnModel());
        selectColumns.addAll(oriTableModel.getSituationColumns().stream().map(BSTableColumn::getColumnModel).collect(Collectors.toList()));
        selectColumns.addAll(oriTableModel.getBizKeyColumns().stream().map(BSTableColumn::getColumnModel).collect(Collectors.toList()));
        selectColumns.addAll(oriTableModel.getBuildColumns().stream().map(BSTableColumn::getColumnModel).collect(Collectors.toList()));
        selectColumns.addAll(oriTableModel.getZBColumns().stream().map(BSTableColumn::getColumnModel).collect(Collectors.toList()));
        HashMap<ColumnModelDefine, Object> columnFilters = new HashMap<ColumnModelDefine, Object>();
        columnFilters.put(oriTableModel.getPeriodColumn().getColumnModel(), period);
        return this.selectDataRows(oriTableModel.getTableModel(), selectColumns, columnFilters);
    }

    protected void clearHistoryTableData(SumTableModelInfo sumTableModel, String period) throws Exception {
        HashMap<ColumnModelDefine, Object> columnFilters = new HashMap<ColumnModelDefine, Object>();
        BSBizKeyColumn gatherColumn = sumTableModel.getGatherColumn();
        columnFilters.put(gatherColumn.getColumnModel(), this.summaryScheme.getKey());
        BSTableColumn periodColumn = sumTableModel.getPeriodColumn();
        columnFilters.put(periodColumn.getColumnModel(), period);
        this.deleteDataRows(sumTableModel.getTableModel(), columnFilters);
    }

    protected void deleteDataRows(TableModelDefine tableDefine, Map<ColumnModelDefine, Object> columnFilters) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(tableDefine);
        Set<ColumnModelDefine> columnModelDefines = columnFilters.keySet();
        columnModelDefines.forEach(c -> queryModel.getColumns().add(new NvwaQueryColumn(c)));
        columnModelDefines.forEach(c -> queryModel.getColumnFilters().put(c, columnFilters.get(c)));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        dataUpdator.deleteAll();
        dataUpdator.commitChanges(context);
    }

    protected IPowerTableEntity selectDataRows(TableModelDefine tableDefine, List<ColumnModelDefine> selectColumns, Map<ColumnModelDefine, Object> columnFilters) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(tableDefine);
        selectColumns.forEach(col -> queryModel.getColumns().add(new NvwaQueryColumn(col)));
        columnFilters.forEach((col, value) -> queryModel.getColumnFilters().put(col, value));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        PowerTableEntity tableEntityData = new PowerTableEntity((String[])selectColumns.stream().map(IModelDefineItem::getCode).toArray(String[]::new));
        MemoryDataSet resultSet = dataAccess.executeQuery(context);
        for (int rowIdx = 0; rowIdx < resultSet.size(); ++rowIdx) {
            DataRow dataRow = resultSet.get(rowIdx);
            for (int colIdx = 0; colIdx < selectColumns.size(); ++colIdx) {
                if (BIZKEYORDER.equalsIgnoreCase(selectColumns.get(colIdx).getName())) {
                    tableEntityData.setCellValue(rowIdx, selectColumns.get(colIdx).getName(), (Object)UUID.randomUUID());
                    continue;
                }
                tableEntityData.setCellValue(rowIdx, selectColumns.get(colIdx).getName(), dataRow.getValue(colIdx));
            }
        }
        return tableEntityData;
    }

    protected NvwaQueryModel newNvwaQueryModel(TableModelDefine tableDefine) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(tableDefine.getName());
        return queryModel;
    }
}

