/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoBuilder;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoSub;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueWriter;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableDataSet;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EstimationWriteTemplate
implements IDataRegionValueWriter {
    protected StringLogger logger;
    protected DataModelService dataModelService;
    protected IEstimationScheme estimationScheme;
    protected IDataRegionInfoBuilder regionInfoBuilder;
    protected INvwaDataAccessProvider dataAccessProvider;
    private final IDataSchemeSubDatabase dataSchemeSubDatabase;

    protected EstimationWriteTemplate(IEstimationScheme estimationScheme, StringLogger logger) {
        this.logger = logger;
        this.estimationScheme = estimationScheme;
        this.dataSchemeSubDatabase = this.getSubDatabase(estimationScheme);
        this.dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        this.regionInfoBuilder = (IDataRegionInfoBuilder)SpringBeanUtils.getBean(IDataRegionInfoBuilder.class);
        this.dataAccessProvider = (INvwaDataAccessProvider)SpringBeanUtils.getBean(INvwaDataAccessProvider.class);
    }

    private IDataSchemeSubDatabase getSubDatabase(IEstimationScheme estimationScheme) {
        IEstimationSubDatabaseHelper subDatabaseHelper = (IEstimationSubDatabaseHelper)SpringBeanUtils.getBean(IEstimationSubDatabaseHelper.class);
        return subDatabaseHelper.getSubDatabaseDefine(estimationScheme.getFormSchemeDefine());
    }

    @Override
    public void writeDataRegionValue(IDataRegionInfo oriDataRegionInfo, DimensionCombination dimensionValues, IDataRegionTableData regionTableData) throws Exception {
        IDataRegionInfoSub dataRegionInfo = this.regionInfoBuilder.buildDataRegionInfo(oriDataRegionInfo, this.dataSchemeSubDatabase);
        DimensionValueSet pubColumnValueSet = new DimensionValueSet(regionTableData.getPubColumnValueSet());
        pubColumnValueSet.setValue("ESTIMATION_SCHEME", (Object)this.estimationScheme.getKey());
        this.logger.logInfo("\u6b63\u5728\u5f80\u6d4b\u7b97\u5206\u5e93\u4e2d\u4fdd\u5b58\u6570\u636e...");
        this.logger.logInfo("\u603b\u5171\u9700\u8981\u5199\u5165[" + dataRegionInfo.getRegionTableModels().size() + "]\u5f20\u8868");
        boolean isFixRegion = dataRegionInfo.getDataRegion().getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
        for (IRegionTableModelSub regionTableModel : dataRegionInfo.getRegionTableModels()) {
            this.logger.logInfo("\u6b63\u5728\u5904\u7406[" + regionTableModel.getTableModelDefine().getName() + "]\u8868\u7684\u6570\u636e...");
            if (isFixRegion) {
                this.dealFixRegionValues(regionTableModel, regionTableData, pubColumnValueSet);
                continue;
            }
            this.dealFloatRegionValues(regionTableModel, regionTableData, pubColumnValueSet);
        }
    }

    protected abstract void dealFixRegionValues(IRegionTableModelSub var1, IDataRegionTableData var2, DimensionValueSet var3) throws Exception;

    protected abstract void dealFloatRegionValues(IRegionTableModelSub var1, IDataRegionTableData var2, DimensionValueSet var3) throws Exception;

    protected void addOrUpdateTableDataRows(IRegionTableModelSub regionTableModel, ITableDataSet tableData, DimensionValueSet pubColumnValueSet) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
        List<ITableBizKeyColumn> buildColumns = this.getBuildColumns(regionTableModel);
        buildColumns.forEach(buildColumn -> queryModel.getColumns().add(new NvwaQueryColumn(buildColumn.getColumnModel())));
        List<ITableCellLinkColumn> cellLinksColumns = this.getCellLinksColumns(regionTableModel, tableData);
        cellLinksColumns.forEach(cellLinksColumn -> queryModel.getColumns().add(new NvwaQueryColumn(cellLinksColumn.getColumnModel())));
        INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        for (int rowIdx = 0; rowIdx < tableData.getRowCount(); ++rowIdx) {
            ArrayKey arrayKey = this.getArrayKey(regionTableModel, dataUpdator.getRowKeyColumns(), pubColumnValueSet, tableData, rowIdx);
            INvwaDataRow dataRow = dataUpdator.addUpdateOrInsertRow(arrayKey);
            int colIdx = 0;
            int i = 0;
            while (i < buildColumns.size()) {
                dataRow.setValue(colIdx, tableData.getCellValue(rowIdx, buildColumns.get(i).getColumnName()).getNewValue());
                ++i;
                ++colIdx;
            }
            i = 0;
            while (i < cellLinksColumns.size()) {
                dataRow.setValue(colIdx, tableData.getCellValue(rowIdx, cellLinksColumns.get(i).getColumnName()).getNewValue());
                ++i;
                ++colIdx;
            }
        }
        dataUpdator.commitChanges(context);
    }

    protected void updateTableDataRows(IRegionTableModelSub regionTableModel, ITableDataSet tableData, DimensionValueSet pubColumnValueSet) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
        List<ITableCellLinkColumn> cellLinksColumns = this.getCellLinksColumns(regionTableModel, tableData);
        cellLinksColumns.forEach(cellLinksColumn -> queryModel.getColumns().add(new NvwaQueryColumn(cellLinksColumn.getColumnModel())));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        for (int rowIdx = 0; rowIdx < tableData.getRowCount(); ++rowIdx) {
            ArrayKey arrayKey = this.getArrayKey(regionTableModel, dataUpdator.getRowKeyColumns(), pubColumnValueSet, tableData, rowIdx);
            INvwaDataRow dataRow = dataUpdator.addUpdateRow(arrayKey);
            for (int colIdx = 0; colIdx < cellLinksColumns.size(); ++colIdx) {
                dataRow.setValue(colIdx, tableData.getCellValue(rowIdx, cellLinksColumns.get(colIdx).getColumnName()).getNewValue());
            }
        }
        dataUpdator.commitChanges(context);
    }

    protected void deleteTableDataRows(IRegionTableModelSub regionTableModel, ITableDataSet tableData, DimensionValueSet pubColumnValueSet) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
        regionTableModel.getDimensionColumns().forEach(dimensionColumn -> queryModel.getColumns().add(new NvwaQueryColumn(dimensionColumn.getColumnModel())));
        regionTableModel.getBizKeyColumns().forEach(bizKeyColumn -> queryModel.getColumns().add(new NvwaQueryColumn(bizKeyColumn.getColumnModel())));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        for (int rowIdx = 0; rowIdx < tableData.getRowCount(); ++rowIdx) {
            ArrayKey arrayKey = this.getArrayKey(regionTableModel, dataUpdator.getRowKeyColumns(), pubColumnValueSet, tableData, rowIdx);
            dataUpdator.addDeleteRow(arrayKey);
        }
        dataUpdator.commitChanges(context);
    }

    protected void clearTableData(IRegionTableModelSub regionTableModel, DimensionValueSet pubColumnValueSet) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
        List<ITableBizKeyColumn> dimensionColumns = regionTableModel.getDimensionColumns();
        dimensionColumns.forEach(c -> queryModel.getColumns().add(new NvwaQueryColumn(c.getColumnModel())));
        dimensionColumns.forEach(c -> queryModel.getColumnFilters().put(c.getColumnModel(), pubColumnValueSet.getValue(c.getColumnName())));
        List<ITableBizKeyColumn> otherKeyColumns = regionTableModel.getOtherKeyColumns();
        otherKeyColumns.forEach(c -> queryModel.getColumns().add(new NvwaQueryColumn(c.getColumnModel())));
        otherKeyColumns.forEach(c -> queryModel.getColumnFilters().put(c.getColumnModel(), pubColumnValueSet.getValue(c.getColumnName())));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        dataUpdator.deleteAll();
        dataUpdator.commitChanges(context);
    }

    protected List<ITableBizKeyColumn> getBuildColumns(IRegionTableModelSub regionTableModel) {
        return regionTableModel.getBuildColumns().stream().filter(c -> c.getColumnType() == IColumnType.normal_column).collect(Collectors.toList());
    }

    protected List<ITableCellLinkColumn> getCellLinksColumns(IRegionTableModelSub regionTableModel, ITableDataSet tableData) {
        return regionTableModel.getCellLinkColumns().stream().filter(cellLinksColumn -> cellLinksColumn.getColumnType() == IColumnType.normal_column && Arrays.stream(tableData.getColumns()).anyMatch(colName -> colName.equals(cellLinksColumn.getColumnName()))).collect(Collectors.toList());
    }

    protected NvwaQueryModel newNvwaQueryModel(IRegionTableModelSub regionTableModel) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(regionTableModel.getTableModelDefine().getName());
        return queryModel;
    }

    protected ArrayKey getArrayKey(IRegionTableModelSub regionTableModel, List<ColumnModelDefine> rowKeyColumns, DimensionValueSet pubColumnValueSet, ITableDataSet tableData, int rowIdx) {
        ArrayList<Object> rowKeys = new ArrayList<Object>();
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            Object keyValue;
            ITableBizKeyColumn dimensionColumn = regionTableModel.findDimensionColumn(rowKeyColumn);
            if (dimensionColumn != null) {
                keyValue = pubColumnValueSet.getValue(dimensionColumn.getColumnName());
                if (keyValue == null) {
                    throw new RuntimeException("\u4e3b\u952e\u5217\uff1a[" + rowKeyColumn.getCode() + "]\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff1a" + null);
                }
                rowKeys.add(keyValue);
                continue;
            }
            ITableBizKeyColumn bizKeyColumn = regionTableModel.findBizKeyColumn(rowKeyColumn);
            if (bizKeyColumn != null) {
                keyValue = tableData.getCellValue(rowIdx, bizKeyColumn.getColumnName()).getNewValue();
                if (keyValue == null) {
                    throw new RuntimeException("\u4e3b\u952e\u5217\uff1a[" + rowKeyColumn.getCode() + "]\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff1a" + null);
                }
                rowKeys.add(keyValue);
                continue;
            }
            throw new RuntimeException("\u672a\u5b9a\u4e49\u7684\u4e3b\u952e\u5217\uff1a[" + rowKeyColumn.getCode() + "]");
        }
        return new ArrayKey(rowKeys);
    }
}

