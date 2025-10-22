/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.common.SplitUtil;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.EnumFillNode;
import com.jiuqi.nr.datacrud.impl.EnumLinkDTO;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.FloatRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.nested.NestedSortTable;
import com.jiuqi.nr.datacrud.spi.IEnumFillNode;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class NestedFloatRegionStrategy
extends FloatRegionDataStrategy
implements IRegionDataSetStrategy {
    protected RegionDataSetStrategyFactory regionDataSetStrategyFactory;

    public NestedFloatRegionStrategy(RegionDataSetStrategyFactory factory) {
        super(factory);
        this.regionDataSetStrategyFactory = factory;
    }

    @Override
    protected List<IRowData> buildData(IReadonlyTable table, List<MetaData> metaData, int begin, int end) {
        List<IRowData> rows = super.buildData(table, metaData, -1, -1);
        this.fillEnumRows(rows);
        String displayLevel = this.relation.getRegionDefine().getDisplayLevel();
        List<String> links = SplitUtil.split(displayLevel);
        if (!CollectionUtils.isEmpty(links) && links.size() > 1) {
            links.remove(links.size() - 1);
            Iterator<LinkSort> sortItr = this.queryInfo.linkSortItr();
            ArrayList<LinkSort> sorts = new ArrayList<LinkSort>();
            boolean appendFloatOrder = false;
            while (sortItr.hasNext()) {
                LinkSort sort = sortItr.next();
                sorts.add(sort);
                if (appendFloatOrder || !"FLOATORDER".equals(sort.getLinkKey())) continue;
                appendFloatOrder = true;
            }
            if (!appendFloatOrder) {
                sorts.add(new LinkSort("FLOATORDER", SortMode.ASC));
            }
            NestedSortTable sortTable = new NestedSortTable(links, sorts);
            for (IRowData row : rows) {
                sortTable.addRow(row);
            }
            rows = sortTable.sort();
        }
        this.queryDataSet.setTotalCount(rows.size());
        ArrayList<IRowData> pages = new ArrayList<IRowData>();
        if (begin < 0) {
            begin = 0;
        }
        if (end < 0 || end > rows.size()) {
            end = rows.size();
        }
        for (int index = begin; index < rows.size() && index < end; ++index) {
            pages.add(rows.get(index));
        }
        return pages;
    }

    @Override
    protected void fillEnum() {
        if (this.noneFillEnum()) {
            return;
        }
        List<MetaData> enumLinks = this.relation.getFilledEnumLinks();
        boolean firstPreAutoFill = this.isFirstPreAutoFill(enumLinks);
        if (!firstPreAutoFill) {
            super.fillEnum();
        }
    }

    private boolean isFirstPreAutoFill(List<MetaData> enumLinks) {
        MetaData metaData = enumLinks.get(0);
        EnumLinkDTO enumLink = metaData.getEnumLink();
        if (enumLink == null) {
            return false;
        }
        List<String> preLinks = enumLink.getPreLinks();
        if (CollectionUtils.isEmpty(preLinks)) {
            return false;
        }
        int parentSize = preLinks.size();
        for (String preLink : preLinks) {
            for (MetaData link : enumLinks) {
                if (!preLink.equals(link.getLinkKey())) continue;
                --parentSize;
            }
        }
        return parentSize > 0;
    }

    protected void fillEnumRows(List<IRowData> rows) {
        if (this.noneFillEnum()) {
            return;
        }
        List<MetaData> filledEnumMetas = this.relation.getFilledEnumLinks();
        if (this.isFirstPreAutoFill(filledEnumMetas)) {
            HashSet dbEnumData = new HashSet();
            LinkedList<String> enumLinks = new LinkedList<String>();
            MetaData metaData = filledEnumMetas.get(0);
            List<String> preLinks = metaData.getEnumLink().getPreLinks();
            for (int i = preLinks.size() - 1; i >= 0; --i) {
                enumLinks.add(preLinks.get(i));
            }
            enumLinks.addAll(filledEnumMetas.stream().map(MetaData::getLinkKey).collect(Collectors.toList()));
            ArrayList<IEnumFillNode> filterNodes = new ArrayList<IEnumFillNode>();
            HashSet<String> contains = new HashSet<String>();
            BigDecimal maxOrder = new BigDecimal(0);
            for (IRowData row : rows) {
                BigDecimal order;
                IDataValue orderValue = row.getDataValueByLink("FLOATORDER");
                if (orderValue != null && maxOrder.compareTo(order = orderValue.getAsCurrency()) < 0) {
                    maxOrder = order;
                }
                ArrayList<String> dbEnum = new ArrayList<String>(enumLinks.size());
                IDataValue value = row.getDataValueByLink(metaData.getLinkKey());
                dbEnum.add(value.getAsString());
                EnumFillNode root = null;
                for (String string : preLinks) {
                    IDataValue valueByLink = row.getDataValueByLink(string);
                    dbEnum.add(valueByLink.getAsString());
                    if (valueByLink.getAsNull()) continue;
                    MetaData metaDataByLink = this.relation.getMetaDataByLink(string);
                    DataField dataField = metaDataByLink.getDataField();
                    String entityKey = dataField.getRefDataEntityKey();
                    EnumFillNode preNode = new EnumFillNode(entityKey, valueByLink.getAsString());
                    if (root != null) {
                        preNode.setLeftNode(root);
                    } else if (!contains.contains(valueByLink.getAsString())) {
                        contains.add(valueByLink.getAsString());
                        filterNodes.add(preNode);
                    }
                    root = preNode;
                }
                Collections.reverse(dbEnum);
                dbEnumData.add(dbEnum);
            }
            DimensionCombination rowComb = this.queryInfo.getDimensionCombination();
            List<List<String>> enumValues = this.fillDataProvider.fillData(this.queryInfo, this.relation, filterNodes);
            if (CollectionUtils.isEmpty(enumValues)) {
                return;
            }
            enumValues.removeIf(dbEnumData::contains);
            DimensionValueSet rowKeys = rowComb.toDimensionValueSet();
            HashMap enumLinkIndex = new HashMap();
            for (int i = 0; i < enumLinks.size(); ++i) {
                enumLinkIndex.put(enumLinks.get(i), i);
            }
            List<MetaData> metas = this.relation.getMetaData();
            ArrayList<RowData> fillData = new ArrayList<RowData>();
            for (List list : enumValues) {
                RowData rowData = new RowData(this.queryDataSet);
                rowData.setFilledRow(true);
                ArrayList<IDataValue> values = new ArrayList<IDataValue>(metas.size() + 1);
                rowData.setDataValues(values);
                for (MetaData metaDatum : metas) {
                    AbstractData data;
                    Integer index = (Integer)enumLinkIndex.get(metaDatum.getLinkKey());
                    if (index == null) {
                        data = this.evaluateDefaultValue(this.context, this.relation, rowKeys, metaData);
                        if (data == null && "FLOATORDER".equals(metaDatum.getLinkKey())) {
                            maxOrder = maxOrder.add(BigDecimal.TEN);
                            data = AbstractData.valueOf((Object)maxOrder, (int)10);
                        }
                    } else {
                        String eData = (String)list.get(index);
                        data = AbstractData.valueOf((Object)eData, (int)metaDatum.getDataType());
                    }
                    if (data == null) {
                        data = AbstractData.valueOf(null, (int)metaDatum.getDataType());
                    }
                    DataValue dataValue = new DataValue(metaDatum, data);
                    values.add(dataValue);
                }
                fillData.add(rowData);
            }
            rows.addAll(fillData);
        }
    }

    @Override
    protected boolean isIgnoreDbPage() {
        return true;
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder) {
        if (bizKeyOrder == null) {
            return -1;
        }
        List<IRowData> rowData = this.getNestedTable(queryInfo, relation).getRowData();
        for (int i = 0; i < rowData.size(); ++i) {
            if (!bizKeyOrder.equals(rowData.get(i).getRecKey())) continue;
            return i;
        }
        return -1;
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder, int offset) {
        IRegionDataSet dataSet = this.getNestedTable(queryInfo, relation);
        List<IRowData> rowData = dataSet.getRowData();
        if (CollectionUtils.isEmpty(rowData)) {
            return dataSet;
        }
        RegionDataSet set = this.regionDataSetStrategyFactory.getRegionDataSetFactory().getRegionDataSet(dataSet.getMetaData(), relation);
        for (int i = 0; i < rowData.size(); ++i) {
            IRowData row = rowData.get(i);
            if (!bizKeyOrder.equals(row.getRecKey())) continue;
            if (offset != 0) {
                int index = offset + i;
                if (index < rowData.size() && index >= 0) {
                    row = rowData.get(index);
                } else {
                    return set;
                }
            }
            set.setRows(Collections.singletonList(row));
            set.setTotalCount(1);
            return set;
        }
        return set;
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim) {
        if (rowDim == null) {
            return -1;
        }
        List<IRowData> rowData = this.getNestedTable(queryInfo, relation).getRowData();
        DimensionValueSet rowSet = rowDim.toDimensionValueSet();
        for (int i = 0; i < rowData.size(); ++i) {
            if (!rowSet.equals((Object)rowData.get(i).getDimension().toDimensionValueSet())) continue;
            return i;
        }
        return -1;
    }

    private IRegionDataSet getNestedTable(IQueryInfo queryInfo, RegionRelation relation) {
        PageInfo pageInfo = queryInfo.getPageInfo();
        if (pageInfo != null) {
            throw new IllegalArgumentException("queryDataIndex method does not allow setting pagination");
        }
        return this.queryData(queryInfo, relation);
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim, int offset) {
        IRegionDataSet dataSet = this.getNestedTable(queryInfo, relation);
        List<IRowData> rowData = dataSet.getRowData();
        if (CollectionUtils.isEmpty(rowData)) {
            return dataSet;
        }
        RegionDataSet set = this.regionDataSetStrategyFactory.getRegionDataSetFactory().getRegionDataSet(dataSet.getMetaData(), relation);
        DimensionValueSet searchRowDim = rowDim.toDimensionValueSet();
        for (int i = 0; i < rowData.size(); ++i) {
            IRowData row = rowData.get(i);
            if (!searchRowDim.equals((Object)row.getDimension().toDimensionValueSet())) continue;
            if (offset != 0) {
                int index = offset + i;
                if (index < rowData.size() && index >= 0) {
                    row = rowData.get(index);
                } else {
                    return set;
                }
            }
            set.setRows(Collections.singletonList(row));
            set.setTotalCount(1);
            return set;
        }
        return set;
    }
}

