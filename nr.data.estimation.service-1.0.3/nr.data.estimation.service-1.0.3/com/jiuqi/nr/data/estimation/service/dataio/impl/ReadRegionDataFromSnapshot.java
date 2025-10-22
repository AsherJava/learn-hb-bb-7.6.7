/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.snapshot.input.QueryPeriodDataSourceContext
 *  com.jiuqi.nr.snapshot.input.QuerySnapshotDataSourceContext
 *  com.jiuqi.nr.snapshot.message.DataInfo
 *  com.jiuqi.nr.snapshot.message.DataRange
 *  com.jiuqi.nr.snapshot.message.DataRegionRange
 *  com.jiuqi.nr.snapshot.message.FieldData
 *  com.jiuqi.nr.snapshot.message.FixRegionData
 *  com.jiuqi.nr.snapshot.message.FloatRegionData
 *  com.jiuqi.nr.snapshot.service.DataSource
 *  com.jiuqi.nr.snapshot.service.DataSourceBuilder
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.dataio.CheckPolicy;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueReader;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionDataIOContext;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionTableData;
import com.jiuqi.nr.data.estimation.service.enumeration.DataSnapshotPeriodType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.snapshot.input.QueryPeriodDataSourceContext;
import com.jiuqi.nr.snapshot.input.QuerySnapshotDataSourceContext;
import com.jiuqi.nr.snapshot.message.DataInfo;
import com.jiuqi.nr.snapshot.message.DataRange;
import com.jiuqi.nr.snapshot.message.DataRegionRange;
import com.jiuqi.nr.snapshot.message.FieldData;
import com.jiuqi.nr.snapshot.message.FixRegionData;
import com.jiuqi.nr.snapshot.message.FloatRegionData;
import com.jiuqi.nr.snapshot.service.DataSource;
import com.jiuqi.nr.snapshot.service.DataSourceBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadRegionDataFromSnapshot
implements IDataRegionValueReader {
    private static final String DATATIME = "DATATIME";
    private String snapshotId;
    private DataSnapshotPeriodType periodType;
    private final StringLogger logger;
    private final IRegionDataIOContext ioContext;
    private final DataSourceBuilder dataSourceBuilder;
    private final Map<DimensionValueSet, DataSource> snapshotCache = new HashMap<DimensionValueSet, DataSource>();

    private ReadRegionDataFromSnapshot(StringLogger logger, IRegionDataIOContext ioContext, DataSourceBuilder dataSourceBuilder) {
        this.logger = logger;
        this.ioContext = ioContext;
        this.dataSourceBuilder = dataSourceBuilder;
    }

    public ReadRegionDataFromSnapshot(StringLogger logger, IRegionDataIOContext ioContext, DataSourceBuilder dataSourceBuilder, DataSnapshotPeriodType periodType, String snapshotId) {
        this(logger, ioContext, dataSourceBuilder);
        this.periodType = periodType;
        this.snapshotId = snapshotId;
    }

    @Override
    public IDataRegionTableData readDataRegionValue(IDataRegionInfo dataRegionInfo, DimensionCombination dimensionValues, DimensionValueSet pubColumnValueSet) throws Exception {
        boolean isFixRegion;
        DataSource dataSource = this.getSnapshotSource(dimensionValues);
        DataRegionTableData tableData = new DataRegionTableData(pubColumnValueSet);
        boolean bl = isFixRegion = dataRegionInfo.getDataRegion().getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
        if (dataSource != null) {
            DataInfo formData = dataSource.getData(dataRegionInfo.getDataRegion().getFormKey());
            if (isFixRegion) {
                this.appendFixRegionTableData(dataRegionInfo, tableData, formData);
            } else {
                this.appendFloatRegionTableData(dataRegionInfo, tableData, formData);
            }
        } else {
            this.logger.logError("\u5f53\u524d\u8868\u5355\u6ca1\u6709\u5feb\u7167\u6570\u636e\uff0c\u6570\u636e\u8fd8\u539f\u5931\u8d25\uff01\uff01");
        }
        return tableData;
    }

    private void appendFixRegionTableData(IDataRegionInfo dataRegionInfo, DataRegionTableData tableData, DataInfo formData) {
        FixRegionData fixData = formData.getFixData();
        if (fixData != null && fixData.getFixDatas() != null && !fixData.getFixDatas().isEmpty()) {
            List fileData = fixData.getFixDatas();
            List<ITableCellLinkColumn> cellLinkColumns = dataRegionInfo.getCellLinksColumns();
            tableData.appendTableData(CheckPolicy.UpdateSet, cellLinkColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
            HashMap cellLinkColumnMap = new HashMap();
            cellLinkColumns.forEach(e -> cellLinkColumnMap.put(e.getDataField().getKey(), e));
            for (FieldData zb : fileData) {
                AbstractData zbValue;
                ITableCellLinkColumn cellLinkColumn = (ITableCellLinkColumn)cellLinkColumnMap.get(zb.getFieldKey());
                if (cellLinkColumn == null || (zbValue = zb.getData()) == null) continue;
                tableData.setCellValue(0, cellLinkColumn.getColumnName(), zbValue.getAsObject(), zbValue.getAsObject());
            }
        }
    }

    private void appendFloatRegionTableData(IDataRegionInfo dataRegionInfo, DataRegionTableData tableData, DataInfo formData) {
        HashMap cellLinkColumnMap = new HashMap();
        List<ITableBizKeyColumn> bizKeyColumns = dataRegionInfo.getBizKeyColumns();
        bizKeyColumns.forEach(e -> cellLinkColumnMap.put(e.getDataField().getKey(), e));
        tableData.appendTableData(CheckPolicy.UpdateSet, bizKeyColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        List<ITableBizKeyColumn> buildColumns = dataRegionInfo.getBuildColumns();
        buildColumns.forEach(e -> cellLinkColumnMap.put(e.getDataField().getKey(), e));
        buildColumns.forEach(e -> cellLinkColumnMap.put(e.getDataField().getCode(), e));
        tableData.appendTableData(CheckPolicy.UpdateSet, buildColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        List<ITableCellLinkColumn> cellLinksColumns = dataRegionInfo.getCellLinksColumns();
        cellLinksColumns.forEach(e -> {
            ITableBizKeyColumn cfr_ignored_0 = cellLinkColumnMap.put(e.getDataField().getKey(), e);
        });
        tableData.appendTableData(CheckPolicy.UpdateSet, cellLinksColumns.stream().map(ITableColumn::getColumnName).collect(Collectors.toList()));
        FieldData floatOrderFieldData = new FieldData();
        floatOrderFieldData.setFieldCode("FLOATORDER");
        List floatRegionData = formData.getFloatDatas();
        for (FloatRegionData regionData : floatRegionData) {
            List rowData = regionData.getFloatDatass();
            for (int rowIdx = 0; rowIdx < rowData.size(); ++rowIdx) {
                List oneRow = (List)rowData.get(rowIdx);
                oneRow.add(floatOrderFieldData);
                for (FieldData zb : oneRow) {
                    AbstractData zbValue;
                    ITableBizKeyColumn cellLinkColumn;
                    if ("BIZKEYORDER".equals(zb.getFieldCode())) {
                        cellLinkColumn = (ITableBizKeyColumn)cellLinkColumnMap.get("BIZKEYORDER");
                        AbstractData bizKeyOrder = this.getBizKeyOrder(zb.getData());
                        tableData.setCellValue(rowIdx, cellLinkColumn.getColumnName(), bizKeyOrder.getAsObject(), bizKeyOrder.getAsObject());
                        continue;
                    }
                    if ("FLOATORDER".equals(zb.getFieldCode())) {
                        cellLinkColumn = (ITableBizKeyColumn)cellLinkColumnMap.get("FLOATORDER");
                        AbstractData floatOrder = this.getFloatOrder(zb.getData(), rowIdx);
                        tableData.setCellValue(rowIdx, cellLinkColumn.getColumnName(), floatOrder.getAsObject(), floatOrder.getAsObject());
                        continue;
                    }
                    cellLinkColumn = (ITableBizKeyColumn)cellLinkColumnMap.get(zb.getFieldKey());
                    if (cellLinkColumn == null || (zbValue = zb.getData()) == null) continue;
                    tableData.setCellValue(rowIdx, cellLinkColumn.getColumnName(), zbValue.getAsObject(), zbValue.getAsObject());
                }
            }
        }
    }

    private AbstractData getBizKeyOrder(AbstractData oriValue) {
        if (oriValue != null) {
            return oriValue;
        }
        return new StringData(UUID.randomUUID().toString());
    }

    private AbstractData getFloatOrder(AbstractData oriValue, int index) {
        if (oriValue != null) {
            return oriValue;
        }
        return new FloatData((double)((index + 1) * 100));
    }

    private DataSource getSnapshotSource(DimensionCombination dimensionValues) {
        DimensionValueSet dimensionValueSet = dimensionValues.toDimensionValueSet();
        DataSource dataSource = this.snapshotCache.get(dimensionValueSet);
        if (dataSource == null) {
            switch (this.periodType) {
                case NOT_PERIOD: {
                    dataSource = this.getSnapshotSourceById(dimensionValues, this.snapshotId);
                    this.snapshotCache.put(dimensionValueSet, dataSource);
                    break;
                }
                case LASTYEAR_SAMEPERIOD: {
                    dataSource = this.getSnapshotSourceByLastYearPeriod(dimensionValues, this.snapshotId);
                    this.snapshotCache.put(dimensionValueSet, dataSource);
                    break;
                }
                case LAST_PERIOD: {
                    dataSource = this.getSnapshotSourceByLastPeriod(dimensionValues, this.snapshotId);
                    this.snapshotCache.put(dimensionValueSet, dataSource);
                }
            }
        }
        return dataSource;
    }

    private DataSource getSnapshotSourceById(DimensionCombination dimensionValues, String snapshotId) {
        QuerySnapshotDataSourceContext queryContext = new QuerySnapshotDataSourceContext();
        queryContext.setFormSchemeKey(this.ioContext.getForSchemeKey());
        queryContext.setDimensionCombination(dimensionValues);
        queryContext.setSnapshotId(snapshotId);
        queryContext.setDataRange(this.getDataRange(this.ioContext));
        return this.dataSourceBuilder.querySnapshotDataSource(queryContext);
    }

    private DataSource getSnapshotSourceByLastYearPeriod(DimensionCombination dimensionValues, String snapshotId) {
        DimensionValueSet dimensionValueSet = dimensionValues.toDimensionValueSet();
        DimensionValueSet lastyearDimensionValueSet = new DimensionValueSet(dimensionValueSet);
        Object currenPeriod = dimensionValueSet.getValue(DATATIME);
        String currenPeriodCode = (String)currenPeriod;
        String currenYear = currenPeriodCode.substring(0, 4);
        int currenYearInt = Integer.parseInt(currenYear);
        int lastYearInt = currenYearInt - 1;
        String lastYearPeriod = lastYearInt + currenPeriodCode.substring(4);
        lastyearDimensionValueSet.setValue(DATATIME, (Object)lastYearPeriod);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastyearDimensionValueSet);
        DimensionCombination lastYearPeriodCombination = dimensionCombinationBuilder.getCombination();
        QueryPeriodDataSourceContext queryContext = new QueryPeriodDataSourceContext();
        queryContext.setFormSchemeKey(this.ioContext.getForSchemeKey());
        queryContext.setDimensionCombination(lastYearPeriodCombination);
        queryContext.setDataRange(this.getDataRange(this.ioContext));
        return this.dataSourceBuilder.queryPeriodDataSource(queryContext);
    }

    private DataSource getSnapshotSourceByLastPeriod(DimensionCombination dimensionValues, String snapshotId) {
        DimensionValueSet dimensionValueSet = dimensionValues.toDimensionValueSet();
        DimensionValueSet lastPeriodDimensionValueSet = new DimensionValueSet(dimensionValueSet);
        Object currenPeriod = dimensionValueSet.getValue(DATATIME);
        String lastPeriod = PeriodUtils.priorPeriod((String)((String)currenPeriod));
        lastPeriodDimensionValueSet.setValue(DATATIME, (Object)lastPeriod);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(lastPeriodDimensionValueSet);
        DimensionCombination lastPeriodCombination = dimensionCombinationBuilder.getCombination();
        QueryPeriodDataSourceContext queryContext = new QueryPeriodDataSourceContext();
        queryContext.setFormSchemeKey(this.ioContext.getForSchemeKey());
        queryContext.setDimensionCombination(lastPeriodCombination);
        queryContext.setDataRange(this.getDataRange(this.ioContext));
        return this.dataSourceBuilder.queryPeriodDataSource(queryContext);
    }

    private DataRange getDataRange(IRegionDataIOContext ioContext) {
        ArrayList<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();
        List<String> rangeFormKeys = ioContext.getRangeFormKeys();
        for (String formKey : rangeFormKeys) {
            DataRegionRange dataRegionRange = new DataRegionRange();
            dataRegionRange.setFormKey(formKey);
            formAndFields.add(dataRegionRange);
        }
        DataRange dataRange = new DataRange();
        dataRange.setFormAndFields(formAndFields);
        return dataRange;
    }
}

