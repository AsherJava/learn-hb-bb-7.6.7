/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.dataio.CheckPolicy;
import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueReader;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionTableData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadRegionDataFromDataEntry
implements IDataRegionValueReader {
    public static final String BIZKEYORDER = "BIZKEYORDER";
    protected final StringLogger logger;
    protected DataModelService dataModelService;
    protected INvwaDataAccessProvider dataAccessProvider;

    public ReadRegionDataFromDataEntry(StringLogger logger) {
        this.logger = logger;
        this.dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        this.dataAccessProvider = (INvwaDataAccessProvider)SpringBeanUtils.getBean(INvwaDataAccessProvider.class);
    }

    @Override
    public IDataRegionTableData readDataRegionValue(IDataRegionInfo dataRegionInfo, DimensionCombination dimensionValues, DimensionValueSet pubColumnValueSet) throws Exception {
        DataRegionTableData tableData = new DataRegionTableData(pubColumnValueSet);
        List<IRegionTableModel> regionTableModels = dataRegionInfo.getRegionTableModels();
        for (IRegionTableModel regionTableModel : regionTableModels) {
            List<ITableBizKeyColumn> dimensionColumns = regionTableModel.getDimensionColumns();
            List<ITableBizKeyColumn> bizKeyColumns = regionTableModel.getBizKeyColumns();
            List<ITableBizKeyColumn> buildColumns = regionTableModel.getBuildColumns();
            List<ITableCellLinkColumn> cellLinkColumns = regionTableModel.getCellLinkColumns().stream().filter(col -> col.getColumnType() == IColumnType.normal_column).collect(Collectors.toList());
            bizKeyColumns.addAll(buildColumns);
            this.setTableData(dimensionColumns, bizKeyColumns, cellLinkColumns, tableData);
        }
        return tableData;
    }

    protected void setTableData(List<ITableBizKeyColumn> dimensionColumns, List<ITableBizKeyColumn> bizKeyColumns, List<ITableCellLinkColumn> cellLinkColumns, DataRegionTableData tableData) throws Exception {
        tableData.appendTableData(CheckPolicy.NewSet, bizKeyColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        tableData.appendTableData(CheckPolicy.NewSet, cellLinkColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        NvwaQueryModel queryModel = new NvwaQueryModel();
        dimensionColumns.forEach(col -> queryModel.getColumnFilters().put(col.getColumnModel(), tableData.getPubColumnValueSet().getValue(col.getColumnName())));
        bizKeyColumns.forEach(col -> queryModel.getColumns().add(new NvwaQueryColumn(col.getColumnModel())));
        cellLinkColumns.forEach(col -> queryModel.getColumns().add(new NvwaQueryColumn(col.getColumnModel())));
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet resultSet = dataAccess.executeQuery(context);
        for (int rowIdx = 0; rowIdx < resultSet.size(); ++rowIdx) {
            int colIdx;
            DataRow dataRow = resultSet.get(rowIdx);
            for (colIdx = 0; colIdx < bizKeyColumns.size(); ++colIdx) {
                if (BIZKEYORDER.equalsIgnoreCase(bizKeyColumns.get(colIdx).getColumnName())) {
                    tableData.setCellValue(rowIdx, bizKeyColumns.get(colIdx).getColumnName(), UUID.randomUUID(), dataRow.getValue(colIdx));
                    continue;
                }
                tableData.setCellValue(rowIdx, bizKeyColumns.get(colIdx).getColumnName(), dataRow.getValue(colIdx));
            }
            int idx = 0;
            while (idx < cellLinkColumns.size()) {
                tableData.setCellValue(rowIdx, cellLinkColumns.get(idx).getColumnName(), dataRow.getValue(colIdx));
                ++idx;
                ++colIdx;
            }
        }
    }
}

