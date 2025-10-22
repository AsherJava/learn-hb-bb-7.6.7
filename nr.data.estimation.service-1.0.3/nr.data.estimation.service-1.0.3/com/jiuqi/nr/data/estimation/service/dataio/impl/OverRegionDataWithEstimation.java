/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.impl.EstimationWriteTemplate;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import java.util.List;

public class OverRegionDataWithEstimation
extends EstimationWriteTemplate {
    public OverRegionDataWithEstimation(IEstimationScheme estimationScheme, StringLogger logger) {
        super(estimationScheme, logger);
    }

    @Override
    protected void dealFixRegionValues(IRegionTableModelSub regionTableModel, IDataRegionTableData dataRegionValueSet, DimensionValueSet pubColumnValueSet) throws Exception {
        if (!dataRegionValueSet.isEmpty()) {
            List<ITableCellLinkColumn> cellLinksColumns = this.getCellLinksColumns(regionTableModel, dataRegionValueSet);
            NvwaQueryModel queryModel = this.newNvwaQueryModel(regionTableModel);
            cellLinksColumns.forEach(linkColumn -> queryModel.getColumns().add(new NvwaQueryColumn(linkColumn.getColumnModel())));
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            INvwaUpdatableDataAccess dataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator dataUpdator = dataAccess.openForUpdate(context);
            for (int rowIdx = 0; rowIdx < dataRegionValueSet.getRowCount(); ++rowIdx) {
                ArrayKey arrayKey = this.getArrayKey(regionTableModel, dataUpdator.getRowKeyColumns(), pubColumnValueSet, dataRegionValueSet, rowIdx);
                INvwaDataRow dataRow = dataUpdator.addUpdateOrInsertRow(arrayKey);
                for (int colIdx = 0; colIdx < cellLinksColumns.size(); ++colIdx) {
                    ITableCellLinkColumn linkColumn2 = cellLinksColumns.get(colIdx);
                    dataRow.setValue(colIdx, dataRegionValueSet.getCellValue(rowIdx, linkColumn2.getColumnName()).getNewValue());
                }
            }
            dataUpdator.commitChanges(context);
        } else {
            this.logger.logInfo("\u533a\u57df\u4e0b\u6ca1\u6709\u6307\u6807\u6570\u636e...");
        }
    }

    @Override
    protected void dealFloatRegionValues(IRegionTableModelSub regionTableModel, IDataRegionTableData dataRegionValueSet, DimensionValueSet pubColumnValueSet) throws Exception {
        this.logger.logInfo("\u6b63\u5728\u6e05\u7a7a\u6d6e\u52a8\u8868\u7684\u5386\u53f2\u6570\u636e...");
        this.clearTableData(regionTableModel, pubColumnValueSet);
        if (!dataRegionValueSet.isEmpty()) {
            this.addOrUpdateTableDataRows(regionTableModel, dataRegionValueSet, pubColumnValueSet);
        } else {
            this.logger.logInfo("\u533a\u57df\u4e0b\u6ca1\u6709\u6307\u6807\u6570\u636e...");
        }
    }
}

