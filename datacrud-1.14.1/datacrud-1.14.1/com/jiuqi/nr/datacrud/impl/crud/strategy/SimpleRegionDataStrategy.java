/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.MultiDimDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.BaseRegionDataStrategy;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SimpleRegionDataStrategy
extends BaseRegionDataStrategy<IDataQuery>
implements IRegionDataSetStrategy {
    protected static Logger logger = LoggerFactory.getLogger(SimpleRegionDataStrategy.class);
    protected final IEntityMetaService entityMetaService;
    private Boolean batchMode;
    private Set<DimensionCombination> dims;

    public SimpleRegionDataStrategy(RegionDataSetStrategyFactory factory) {
        super(factory);
        this.entityMetaService = factory.getEntityMetaService();
    }

    @Override
    protected IDataQuery getDataQuery(IQueryInfo queryInfo, RegionRelation relation) {
        return this.dataEngineService.getDataQuery(relation);
    }

    @Override
    public IRegionDataSet queryData(IQueryInfo queryInfo, RegionRelation relation) {
        return this.queryMultiDimensionalData(queryInfo, relation).getRegionDataSet(queryInfo.getDimensionCombination());
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder, int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim) {
        return 0;
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim, int offset) {
        return null;
    }

    @Override
    protected void addFilter() {
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimensionalData(IQueryInfo queryInfo, RegionRelation relation) {
        super.initDataQuery(queryInfo, relation);
        DimensionCombination dimensionCombination = queryInfo.getDimensionCombination();
        DimensionCollection dimensionCollection = queryInfo.getDimensionCollection();
        this.batchMode = dimensionCombination == null && dimensionCollection != null;
        if (this.batchMode.booleanValue()) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection);
            ((IDataQuery)this.dataQuery).setMasterKeys(dimensionValueSet);
            this.context = this.executorContextFactory.getExecutorContext((ParamRelation)relation, dimensionValueSet, queryInfo.variableItr());
        }
        List<MetaData> metaData = relation.getMetaData(queryInfo.selectLinkItr());
        MultiDimDataSet set = new MultiDimDataSet(metaData, Collections.emptyList());
        set.setRegionRelation(relation);
        set.setDimensionCollection(dimensionCollection);
        set.setRegionKey(queryInfo.getRegionKey());
        if (metaData.isEmpty()) {
            List dimensionCombinations = queryInfo.getDimensionCollection().getDimensionCombinations();
            this.dims = new HashSet<DimensionCombination>(dimensionCombinations);
            set.setDims(this.dims);
            return set;
        }
        this.addQueryCol(metaData);
        if (this.batchMode.booleanValue()) {
            List dimensionCombinations = queryInfo.getDimensionCollection().getDimensionCombinations();
            this.dims = new LinkedHashSet<DimensionCombination>(dimensionCombinations);
            set.setDims(this.dims);
        }
        List<IRowData> rowData = super.buildData(metaData);
        set.setRows(rowData);
        return set;
    }

    @Override
    protected void addExpressionColumn(MetaData metaData) {
        String linkExpression = metaData.getDataLinkDefine().getLinkExpression();
        if (StringUtils.hasLength(linkExpression)) {
            int index = ((IDataQuery)this.dataQuery).addExpressionColumn(linkExpression);
            metaData.setIndex(index);
            ((IDataQuery)this.dataQuery).setStatic(true);
        }
    }

    @Override
    protected void metaRowTransfer(IDataRow row, RowData rowData) {
        DimensionCombination masterDimension = rowData.getMasterDimension();
        if (masterDimension == null) {
            DimensionCombination dimension = rowData.getDimension();
            if (dimension == null) {
                DimensionCombinationBuilder builder = new DimensionCombinationBuilder(row.getMasterKeys());
                dimension = builder.getCombination();
            }
            rowData.setMasterDimension(dimension);
        }
        super.metaRowTransfer(row, rowData);
    }

    @Override
    protected void addQueryCol(List<MetaData> metaDatas) {
        if (this.batchMode.booleanValue()) {
            String dw = this.relation.getTaskDefine().getDw();
            String entityCode = this.entityMetaService.getEntityCode(dw);
            ((IDataQuery)this.dataQuery).addExpressionColumn(entityCode + "[CODE]");
        }
        super.addQueryCol(metaDatas);
    }

    @Override
    protected List<IRowData> buildAllRow(IReadonlyTable table, List<MetaData> metaData, int begin, int end) {
        if (!Boolean.TRUE.equals(this.batchMode)) {
            return super.buildAllRow(table, metaData, begin, end);
        }
        ArrayList<IRowData> rows = new ArrayList<IRowData>();
        for (DimensionCombination combination : this.dims) {
            DimensionValueSet masterKey = combination.toDimensionValueSet();
            IDataRow row = table.findRow(masterKey);
            if (row == null) {
                String dwDimName = this.relation.getDwDimName();
                DimensionValueSet dw = new DimensionValueSet();
                dw.setValue(dwDimName, combination.getValue(dwDimName));
                row = table.findRow(dw);
                if (row == null) {
                    if (!logger.isDebugEnabled()) continue;
                    logger.debug("\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u540d {} \u5c1d\u8bd5\u4f7f\u7528\u6570\u636e\u6620\u5c04\u952e {} \u83b7\u53d6\u5931\u8d25", (Object)dwDimName, (Object)dw);
                    continue;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u540d {} \u5c1d\u8bd5\u4f7f\u7528\u6570\u636e\u6620\u5c04\u952e {} \u83b7\u53d6\u6210\u529f", (Object)dwDimName, (Object)dw);
                }
            }
            RowData rowData = new RowData();
            ArrayList<IDataValue> values = new ArrayList<IDataValue>(metaData.size() + 1);
            rowData.setDataValues(values);
            rowData.setDimension(combination);
            rowData.setMasterDimension(combination);
            for (MetaData meta : metaData) {
                AbstractData abstractData = this.metaDataTransfer(meta, row, rowData);
                DataValue value = new DataValue(meta, abstractData);
                value.setRowData(rowData);
                values.add(value);
            }
            this.metaRowTransfer(row, rowData);
            rows.add(rowData);
        }
        return rows;
    }
}

