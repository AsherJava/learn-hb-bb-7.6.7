/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionDataSetFactory;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.BaseFloatRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class FloatRegionDataStrategy
extends BaseFloatRegionDataStrategy<IDataQuery>
implements IRegionDataSetStrategy {
    protected RegionDataSetFactory regionDataSetFactory;
    protected RegionDataSet queryDataSet;

    public FloatRegionDataStrategy(RegionDataSetStrategyFactory factory) {
        super(factory);
        this.regionDataSetFactory = factory.getRegionDataSetFactory();
    }

    @Override
    public IRegionDataSet queryData(IQueryInfo queryInfo, RegionRelation relation) {
        IReadonlyTable table;
        PageInfo pageInfo;
        super.initDataQuery(queryInfo, relation);
        Iterator<String> selectLinkItr = queryInfo.selectLinkItr();
        List<MetaData> metaData = relation.getMetaData(selectLinkItr);
        this.queryDataSet = this.regionDataSetFactory.getRegionDataSet(metaData, relation);
        this.queryDataSet.setRegionKey(queryInfo.getRegionKey());
        DimensionCombination dimensionCombination = queryInfo.getDimensionCombination();
        this.queryDataSet.setMasterDimension(dimensionCombination);
        if (metaData.isEmpty()) {
            return this.queryDataSet;
        }
        this.bizKeyOrderIndex = this.addBizKeyOrderField();
        super.addQueryCol(metaData);
        this.fillEnum();
        this.addFloatOrderField(metaData);
        super.addFilter();
        this.addOrder();
        this.addPage();
        int begin = -1;
        int end = -1;
        if (this.isIgnoreDbPage() && (pageInfo = queryInfo.getPageInfo()) != null) {
            begin = pageInfo.getPageIndex() * pageInfo.getRowsPerPage();
            end = (pageInfo.getPageIndex() + 1) * pageInfo.getRowsPerPage();
        }
        try {
            table = ((IDataQuery)this.dataQuery).executeReader(this.context);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", e);
        }
        this.queryDataSet.setTotalCount(table.getTotalCount());
        List<IRowData> rowData = this.buildData(table, metaData, begin, end);
        this.queryDataSet.setRows(rowData);
        return this.queryDataSet;
    }

    protected void fillEnum() {
        if (this.noneFillEnum()) {
            return;
        }
        List<MetaData> enumLinks = this.relation.getFilledEnumLinks();
        ArrayList<DataFieldDTO> enumFields = new ArrayList<DataFieldDTO>();
        for (MetaData fillLink : enumLinks) {
            enumFields.add((DataFieldDTO)fillLink.getDataField());
        }
        List<List<String>> tableData = this.fillDataProvider.fillData(this.queryInfo, this.relation);
        if (!CollectionUtils.isEmpty(tableData)) {
            ((IDataQuery)this.dataQuery).setFilledEnumLinks(enumFields, tableData);
        }
    }

    protected boolean noneFillEnum() {
        if (!this.queryInfo.isEnableEnumFill()) {
            return true;
        }
        List<MetaData> enumLinks = this.relation.getFilledEnumLinks();
        return CollectionUtils.isEmpty(enumLinks);
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimensionalData(IQueryInfo queryInfo, RegionRelation relation) {
        return null;
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        builder.setValue("RECORDKEY", "RECORDKEY", (Object)bizKeyOrder);
        return this.queryDataIndex(queryInfo, relation, builder.getCombination());
    }

    private int getIndexByDim(DimensionValueSet dimensionValueSet) {
        try {
            IndexItem indexItem = ((IDataQuery)this.dataQuery).queryRowIndex(dimensionValueSet, this.context);
            if (indexItem != null) {
                return indexItem.getCurrentIndex(dimensionValueSet);
            }
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u7d22\u5f15\u5b9a\u4f4d\u9519\u8bef", e);
            throw new CrudException(4101, "\u6570\u636e\u7d22\u5f15\u5b9a\u4f4d\u9519\u8bef");
        }
        return -1;
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder, int offset) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        builder.setValue("RECORDKEY", "RECORDKEY", (Object)bizKeyOrder);
        return this.regionDataLocate(queryInfo, relation, builder.getCombination(), offset);
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim) {
        super.initDataQuery(queryInfo, relation);
        relation.getMetaData(null, true);
        super.addFilter();
        this.addOrder();
        return this.getIndexByDim(rowDim.toDimensionValueSet());
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim, int offset) {
        super.initDataQuery(queryInfo, relation);
        List<MetaData> metaData = relation.getMetaData(queryInfo.selectLinkItr());
        RegionDataSet set = new RegionDataSet(metaData, Collections.emptyList());
        set.setRegionKey(queryInfo.getRegionKey());
        DimensionCombination dimensionCombination = queryInfo.getDimensionCombination();
        set.setMasterDimension(dimensionCombination);
        if (metaData.isEmpty()) {
            return set;
        }
        this.addBizKeyOrderField();
        super.addQueryCol(metaData);
        this.addFloatOrderField(metaData);
        super.addFilter();
        this.addOrder();
        if (offset == 0) {
            for (FixedDimensionValue fixedDimensionValue : rowDim) {
                ((IDataQuery)this.dataQuery).getMasterKeys().setValue(fixedDimensionValue.getName(), fixedDimensionValue.getValue());
            }
        } else {
            int index = this.getIndexByDim(rowDim.toDimensionValueSet());
            if (index < 0) {
                return set;
            }
            if (this.calcPage(index, offset) < 0) {
                return set;
            }
        }
        List<IRowData> rowData = super.buildData(metaData);
        set.setRows(rowData);
        return set;
    }

    @Override
    protected IDataQuery getDataQuery(IQueryInfo queryInfo, RegionRelation relation) {
        IDataQuery dataQuery = this.dataEngineService.getDataQuery(relation);
        FormDefine formDefine = relation.getFormDefine();
        if (formDefine != null) {
            dataQuery.setDefaultGroupName(formDefine.getFormCode());
        }
        return dataQuery;
    }
}

