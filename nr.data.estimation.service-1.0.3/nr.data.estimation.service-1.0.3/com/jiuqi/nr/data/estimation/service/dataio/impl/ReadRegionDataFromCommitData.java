/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.jtable.params.input.RegionDataCommitSet
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.dataio.CheckPolicy;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueReader;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionTableData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadRegionDataFromCommitData
implements IDataRegionValueReader {
    protected final StringLogger logger;
    private final Map<String, RegionDataCommitSet> commitDataSet;

    public ReadRegionDataFromCommitData(Map<String, RegionDataCommitSet> commitDataSet, StringLogger logger) {
        this.logger = logger;
        this.commitDataSet = commitDataSet;
    }

    @Override
    public IDataRegionTableData readDataRegionValue(IDataRegionInfo dataRegionInfo, DimensionCombination dimensionValues, DimensionValueSet pubColumnValueSet) {
        boolean isFixRegion;
        DataRegionTableData tableData = new DataRegionTableData(pubColumnValueSet);
        String dataRegionKey = dataRegionInfo.getDataRegion().getKey();
        boolean bl = isFixRegion = dataRegionInfo.getDataRegion().getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
        if (this.commitDataSet != null && this.commitDataSet.containsKey(dataRegionKey)) {
            RegionDataCommitSet dataCommitSet = this.commitDataSet.get(dataRegionInfo.getDataRegion().getKey());
            List dataLinkIds = (List)dataCommitSet.getCells().get(dataRegionInfo.getDataRegion().getKey());
            List<ITableCellLinkColumn> cellLinkColumns = this.sortCellColumnByCommitSet(dataRegionInfo, dataLinkIds);
            if (isFixRegion) {
                this.appendFixRegionTableData(cellLinkColumns, dataCommitSet, tableData);
            } else {
                List<ITableBizKeyColumn> bizKeyColumns = this.sortBizKeyColumnByCommitSet(dataRegionInfo, dataLinkIds);
                List<ITableBizKeyColumn> buildKeyColumns = this.sortBuildColumnByCommitSet(dataRegionInfo, dataLinkIds);
                this.appendFloatRegionNewTableData(bizKeyColumns, buildKeyColumns, cellLinkColumns, dataCommitSet, tableData);
                this.appendFloatRegionUpdateTableData(bizKeyColumns, buildKeyColumns, cellLinkColumns, dataCommitSet, tableData);
                this.appendFloatRegionDeleteTableData(bizKeyColumns, buildKeyColumns, cellLinkColumns, dataCommitSet, tableData);
            }
        }
        return tableData;
    }

    private void appendFloatRegionNewTableData(List<ITableBizKeyColumn> bizKeyColumns, List<ITableBizKeyColumn> buildKeyColumns, List<ITableCellLinkColumn> cellLinkColumns, RegionDataCommitSet dataCommitSet, DataRegionTableData tableData) {
        List commitData = dataCommitSet.getNewdata();
        if (commitData != null && !commitData.isEmpty()) {
            this.appendBizKeyColumnValues(CheckPolicy.NewSet, bizKeyColumns, commitData, tableData);
            this.appendBuildKeyColumnValues(CheckPolicy.NewSet, buildKeyColumns, commitData, tableData);
            this.appendCellLinkColumnValues(CheckPolicy.NewSet, cellLinkColumns, commitData, tableData);
        }
    }

    private void appendFloatRegionUpdateTableData(List<ITableBizKeyColumn> bizKeyColumns, List<ITableBizKeyColumn> buildKeyColumns, List<ITableCellLinkColumn> cellLinkColumns, RegionDataCommitSet dataCommitSet, DataRegionTableData tableData) {
        List commitData = dataCommitSet.getModifydata();
        if (commitData != null && !commitData.isEmpty()) {
            this.appendBizKeyColumnValues(CheckPolicy.UpdateSet, bizKeyColumns, commitData, tableData);
            this.appendBuildKeyColumnValues(CheckPolicy.UpdateSet, buildKeyColumns, commitData, tableData);
            this.appendCellLinkColumnValues(CheckPolicy.UpdateSet, cellLinkColumns, commitData, tableData);
        }
    }

    private void appendFloatRegionDeleteTableData(List<ITableBizKeyColumn> bizKeyColumns, List<ITableBizKeyColumn> buildKeyColumns, List<ITableCellLinkColumn> cellLinkColumns, RegionDataCommitSet dataCommitSet, DataRegionTableData tableData) {
        List deleteData = dataCommitSet.getDeletedata();
        if (deleteData != null && !deleteData.isEmpty()) {
            tableData.appendTableData(CheckPolicy.DeleteSet, bizKeyColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
            for (int rowIdx = 0; rowIdx < deleteData.size(); ++rowIdx) {
                String[] bizKeyValues = ((String)deleteData.get(rowIdx)).split("\\#\\^\\$");
                for (int colIdx = bizKeyColumns.size() - bizKeyValues.length; colIdx < bizKeyColumns.size(); ++colIdx) {
                    tableData.setCellValue(rowIdx, bizKeyColumns.get(colIdx).getColumnName(), bizKeyValues[colIdx]);
                }
            }
        }
    }

    private void appendBizKeyColumnValues(CheckPolicy policy, List<ITableBizKeyColumn> bizKeyColumns, List<List<List<Object>>> commitData, DataRegionTableData tableData) {
        tableData.appendTableData(policy, bizKeyColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        for (int rowIdx = 0; rowIdx < commitData.size(); ++rowIdx) {
            List<List<Object>> dataRow = commitData.get(rowIdx);
            String[] bizKeyValues = dataRow.get(0).get(1).toString().split("\\#\\^\\$");
            int idx = 0;
            for (int colIdx = bizKeyColumns.size() - bizKeyValues.length; colIdx < bizKeyColumns.size(); ++colIdx) {
                tableData.setCellValue(rowIdx, bizKeyColumns.get(colIdx).getColumnName(), bizKeyValues[idx++]);
            }
        }
    }

    private void appendBuildKeyColumnValues(CheckPolicy policy, List<ITableBizKeyColumn> buildKeyColumns, List<List<List<Object>>> commitData, DataRegionTableData tableData) {
        tableData.appendTableData(policy, buildKeyColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        for (int rowIdx = 0; rowIdx < commitData.size(); ++rowIdx) {
            List<List<Object>> dataRow = commitData.get(rowIdx);
            List<Object> floatOrderValue = dataRow.get(1);
            tableData.setCellValue(rowIdx, buildKeyColumns.get(0).getColumnName(), floatOrderValue.get(1), floatOrderValue.get(0));
        }
    }

    private void appendCellLinkColumnValues(CheckPolicy policy, List<ITableCellLinkColumn> cellLinkColumns, List<List<List<Object>>> commitData, DataRegionTableData tableData) {
        tableData.appendTableData(policy, cellLinkColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        for (int rowIdx = 0; rowIdx < commitData.size(); ++rowIdx) {
            List<List<Object>> dataRow = commitData.get(rowIdx);
            int idx = 0;
            for (int colIdx = dataRow.size() - cellLinkColumns.size(); colIdx < dataRow.size(); ++colIdx) {
                List<Object> cellLinkValue = dataRow.get(colIdx);
                tableData.setCellValue(rowIdx, cellLinkColumns.get(idx++).getColumnName(), cellLinkValue.get(1), cellLinkValue.get(0));
            }
        }
    }

    private void appendFixRegionTableData(List<ITableCellLinkColumn> cellLinkColumns, RegionDataCommitSet dataCommitSet, DataRegionTableData tableData) {
        List commitData = dataCommitSet.getData();
        if (commitData != null && !commitData.isEmpty()) {
            tableData.appendTableData(CheckPolicy.UpdateSet, cellLinkColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
            for (int rowIdx = 0; rowIdx < commitData.size(); ++rowIdx) {
                List dataRow = (List)commitData.get(rowIdx);
                for (int colIdx = 0; colIdx < cellLinkColumns.size(); ++colIdx) {
                    List cellValue = (List)dataRow.get(colIdx);
                    tableData.setCellValue(rowIdx, cellLinkColumns.get(colIdx).getColumnName(), cellValue.get(1), cellValue.get(0));
                }
            }
        }
    }

    private List<ITableBizKeyColumn> sortBizKeyColumnByCommitSet(IDataRegionInfo dataRegionInfo, List<String> dataLinkIds) {
        ArrayList<ITableBizKeyColumn> bizKeyColumns = new ArrayList<ITableBizKeyColumn>();
        if (dataRegionInfo.getDataRegion().getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && dataLinkIds.size() >= 1) {
            String[] dataFiledBizKeys = dataRegionInfo.getRegionTableModels().get(0).getDataTable().getBizKeys();
            String subBizKey = dataLinkIds.get(0);
            if ("ID".equals(subBizKey)) {
                for (String dataFieldKey : dataFiledBizKeys) {
                    dataRegionInfo.getBizKeyColumns().stream().filter(c -> c.getDataField().getKey().equals(dataFieldKey)).findFirst().ifPresent(bizKeyColumns::add);
                    dataRegionInfo.getBuildColumns().stream().filter(c -> c.getDataField().getKey().equals(dataFieldKey)).findFirst().ifPresent(bizKeyColumns::add);
                }
            }
        }
        return bizKeyColumns;
    }

    private List<ITableBizKeyColumn> sortBuildColumnByCommitSet(IDataRegionInfo dataRegionInfo, List<String> dataLinkIds) {
        String subBizKey;
        ArrayList<ITableBizKeyColumn> buildKeyColumns = new ArrayList<ITableBizKeyColumn>();
        if (dataRegionInfo.getDataRegion().getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && dataLinkIds.size() >= 2 && "FLOATORDER".equals(subBizKey = dataLinkIds.get(1))) {
            ITableBizKeyColumn floatOrderCol = dataRegionInfo.getBuildColumns().stream().filter(c -> c.getColumnName().equalsIgnoreCase("FLOATORDER")).findFirst().orElse(null);
            buildKeyColumns.add(floatOrderCol);
        }
        return buildKeyColumns;
    }

    private List<ITableCellLinkColumn> sortCellColumnByCommitSet(IDataRegionInfo dataRegionInfo, List<String> dataLinkIds) {
        ArrayList<ITableCellLinkColumn> cellLinkColumns = new ArrayList<ITableCellLinkColumn>();
        if (dataRegionInfo.getDataRegion().getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            dataLinkIds = dataLinkIds.subList(2, dataLinkIds.size());
        }
        for (String linkId : dataLinkIds) {
            ITableCellLinkColumn cellLinkColumn = dataRegionInfo.getCellLinksColumns().stream().filter(c -> c.getLinkDefine().getKey().equals(linkId)).findFirst().orElse(null);
            cellLinkColumns.add(cellLinkColumn);
        }
        return cellLinkColumns;
    }
}

