/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.service.dataio.IColumnType;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoBuilder;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoSub;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromDataEntry;
import com.jiuqi.nr.data.estimation.service.dataio.model.DataRegionTableData;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;
import java.util.stream.Collectors;

public class ReadRegionDataFromEstimation
extends ReadRegionDataFromDataEntry {
    protected IEstimationScheme estimationScheme;
    protected IDataRegionInfoBuilder regionInfoBuilder;
    private final IDataSchemeSubDatabase dataSchemeSubDatabase;

    public ReadRegionDataFromEstimation(IEstimationScheme estimationScheme, StringLogger logger) {
        super(logger);
        this.estimationScheme = estimationScheme;
        this.dataSchemeSubDatabase = this.getSubDatabase(estimationScheme);
        this.regionInfoBuilder = (IDataRegionInfoBuilder)SpringBeanUtils.getBean(IDataRegionInfoBuilder.class);
    }

    @Override
    public IDataRegionTableData readDataRegionValue(IDataRegionInfo oriDataRegionInfo, DimensionCombination dimensionValues, DimensionValueSet oriPubColumnValueSet) throws Exception {
        IDataRegionInfoSub dataRegionInfo = this.regionInfoBuilder.buildDataRegionInfo(oriDataRegionInfo, this.dataSchemeSubDatabase);
        DimensionValueSet pubColumnValueSet = new DimensionValueSet(oriPubColumnValueSet);
        pubColumnValueSet.setValue("ESTIMATION_SCHEME", (Object)this.estimationScheme.getKey());
        DataRegionTableData tableData = new DataRegionTableData(pubColumnValueSet);
        for (IRegionTableModelSub regionTableModel : dataRegionInfo.getRegionTableModels()) {
            List<ITableBizKeyColumn> otherKeyColumns = regionTableModel.getOtherKeyColumns();
            List<ITableBizKeyColumn> dimensionColumns = regionTableModel.getDimensionColumns();
            List<ITableBizKeyColumn> bizKeyColumns = regionTableModel.getBizKeyColumns();
            List<ITableBizKeyColumn> buildColumns = regionTableModel.getBuildColumns();
            List<ITableCellLinkColumn> cellLinkColumns = regionTableModel.getCellLinkColumns().stream().filter(col -> col.getColumnType() == IColumnType.normal_column).collect(Collectors.toList());
            bizKeyColumns.addAll(buildColumns);
            otherKeyColumns.addAll(dimensionColumns);
            this.setTableData(otherKeyColumns, bizKeyColumns, cellLinkColumns, tableData);
        }
        return tableData;
    }

    private IDataSchemeSubDatabase getSubDatabase(IEstimationScheme estimationScheme) {
        IEstimationSubDatabaseHelper subDatabaseHelper = (IEstimationSubDatabaseHelper)SpringBeanUtils.getBean(IEstimationSubDatabaseHelper.class);
        return subDatabaseHelper.getSubDatabaseDefine(estimationScheme.getFormSchemeDefine());
    }
}

