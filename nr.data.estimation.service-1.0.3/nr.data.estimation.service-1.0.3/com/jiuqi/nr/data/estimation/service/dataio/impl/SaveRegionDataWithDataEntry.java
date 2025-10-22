/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
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
import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueWriter;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableDataSet;
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

public class SaveRegionDataWithDataEntry
implements IDataRegionValueWriter {
    protected StringLogger logger;
    protected DataModelService dataModelService;
    protected INvwaDataAccessProvider dataAccessProvider;

    public SaveRegionDataWithDataEntry(StringLogger logger) {
        this.logger = logger;
        this.dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        this.dataAccessProvider = (INvwaDataAccessProvider)SpringBeanUtils.getBean(INvwaDataAccessProvider.class);
    }

    @Override
    public void writeDataRegionValue(IDataRegionInfo dataRegionInfo, DimensionCombination dimensionValues, IDataRegionTableData regionTableData) throws Exception {
        this.logger.logInfo("\u6b63\u5728\u56de\u5199\u533a\u57df\u6570\u636e...");
        this.logger.logInfo("\u603b\u5171\u9700\u8981\u5199\u5165[" + dataRegionInfo.getRegionTableModels().size() + "]\u5f20\u8868");
        DimensionValueSet pubColumnValueSet = regionTableData.getPubColumnValueSet();
        for (IRegionTableModel regionTableModel : dataRegionInfo.getRegionTableModels()) {
            this.logger.logInfo("\u6b63\u5728\u5904\u7406[" + regionTableModel.getTableModelDefine().getName() + "]\u8868\u7684\u6570\u636e...");
            if (dataRegionInfo.getDataRegion().getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                this.dealFixRegionValues(regionTableModel, regionTableData, pubColumnValueSet);
                continue;
            }
            this.dealFloatRegionValues(regionTableModel, regionTableData, pubColumnValueSet);
        }
    }

    protected void dealFixRegionValues(IRegionTableModel regionTableModel, IDataRegionTableData regionTableData, DimensionValueSet pubColumnValueSet) throws Exception {
        if (!regionTableData.isEmpty()) {
            List<ITableCellLinkColumn> cellLinksColumns = this.getCellLinksColumns(regionTableModel, regionTableData);
            NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
            cellLinksColumns.forEach(linkColumn -> queryModel.getColumns().add(new NvwaQueryColumn(linkColumn.getColumnModel())));
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
            for (int rowIdx = 0; rowIdx < regionTableData.getRowCount(); ++rowIdx) {
                ArrayKey arrayKey = this.getArrayKey(regionTableModel, dataUpdator.getRowKeyColumns(), pubColumnValueSet, regionTableData, rowIdx);
                INvwaDataRow dataRow = dataUpdator.addUpdateOrInsertRow(arrayKey);
                for (int colIdx = 0; colIdx < cellLinksColumns.size(); ++colIdx) {
                    ITableCellLinkColumn linkColumn2 = cellLinksColumns.get(colIdx);
                    dataRow.setValue(colIdx, regionTableData.getCellValue(rowIdx, linkColumn2.getColumnName()).getNewValue());
                }
            }
            dataUpdator.commitChanges(context);
        }
    }

    protected void dealFloatRegionValues(IRegionTableModel regionTableModel, IDataRegionTableData regionTableData, DimensionValueSet pubColumnValueSet) throws Exception {
        if (!regionTableData.isEmpty()) {
            this.clearTableData(regionTableModel, pubColumnValueSet);
            this.addOrUpdateTableDataRows(regionTableModel, regionTableData, pubColumnValueSet);
        }
    }

    protected void addOrUpdateTableDataRows(IRegionTableModel regionTableModel, ITableDataSet tableData, DimensionValueSet pubColumnValueSet) throws Exception {
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

    protected void clearTableData(IRegionTableModel regionTableModel, DimensionValueSet pubColumnValueSet) throws Exception {
        NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
        List<ITableBizKeyColumn> dimensionColumns = regionTableModel.getDimensionColumns();
        dimensionColumns.forEach(c -> queryModel.getColumns().add(new NvwaQueryColumn(c.getColumnModel())));
        dimensionColumns.forEach(c -> queryModel.getColumnFilters().put(c.getColumnModel(), pubColumnValueSet.getValue(c.getColumnName())));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
        dataUpdator.deleteAll();
        dataUpdator.commitChanges(context);
    }

    protected List<ITableBizKeyColumn> getBuildColumns(IRegionTableModel regionTableModel) {
        return regionTableModel.getBuildColumns().stream().filter(c -> c.getColumnType() == IColumnType.normal_column).collect(Collectors.toList());
    }

    protected List<ITableCellLinkColumn> getCellLinksColumns(IRegionTableModel regionTableModel, ITableDataSet tableData) {
        return regionTableModel.getCellLinkColumns().stream().filter(cellLinksColumn -> cellLinksColumn.getColumnType() == IColumnType.normal_column && Arrays.stream(tableData.getColumns()).anyMatch(colName -> colName.equals(cellLinksColumn.getColumnName()))).collect(Collectors.toList());
    }

    protected NvwaQueryModel newNvwaQueryModel(IRegionTableModel regionTableModel) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        queryModel.setMainTableName(regionTableModel.getTableModelDefine().getName());
        return queryModel;
    }

    protected ArrayKey getArrayKey(IRegionTableModel regionTableModel, List<ColumnModelDefine> rowKeyColumns, DimensionValueSet pubColumnValueSet, ITableDataSet tableData, int rowIdx) {
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

