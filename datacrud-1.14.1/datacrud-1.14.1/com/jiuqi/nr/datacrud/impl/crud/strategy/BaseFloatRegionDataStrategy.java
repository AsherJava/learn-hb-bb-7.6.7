/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.BaseRegionDataStrategy;
import com.jiuqi.nr.datacrud.spi.FillDataProvider;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class BaseFloatRegionDataStrategy<Q extends ICommonQuery>
extends BaseRegionDataStrategy<Q> {
    protected int bizKeyOrderIndex = -1;
    private Boolean ignoreDbOrder;
    private Boolean ignoreDbPage;
    protected final FillDataProvider fillDataProvider;

    public BaseFloatRegionDataStrategy(RegionDataSetStrategyFactory factory) {
        super(factory);
        this.fillDataProvider = factory.getFillDataProvider();
    }

    protected void addFloatOrderField(List<MetaData> metaData) {
        if (this.queryInfo.selectLinkItr() != null) {
            return;
        }
        MetaData floatOrder = new MetaData("FLOATORDER", this.relation.getFloatOrderField());
        metaData.add(floatOrder);
        int floatIndex = this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)this.relation.getFloatOrderField()));
        floatOrder.setIndex(floatIndex);
    }

    protected void addPage() {
        if (this.isIgnoreDbPage()) {
            return;
        }
        PageInfo pageInfo = this.queryInfo.getPageInfo();
        if (pageInfo != null) {
            this.dataQuery.setPagingInfo(pageInfo.getRowsPerPage(), pageInfo.getPageIndex());
        }
    }

    protected int addBizKeyOrderField() {
        return this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)this.relation.getBizKeyOrderField()));
    }

    protected int calcPage(int index, int offset) {
        int page = index + offset - 1;
        if (page < 0) {
            return page;
        }
        this.dataQuery.setPagingInfo(1, page);
        return page;
    }

    protected boolean isIgnoreDbOrder() {
        if (this.ignoreDbOrder == null) {
            this.ignoreDbOrder = CollectionUtils.isEmpty(this.relation.getFilledEnumLinks());
        }
        return false;
    }

    protected boolean isIgnoreDbPage() {
        if (this.ignoreDbPage == null) {
            this.ignoreDbPage = !CollectionUtils.isEmpty(this.relation.getFilledEnumLinks());
        }
        return this.ignoreDbPage;
    }

    protected List<IRowData> mergeData(List<IRowData> dbData, List<IRowData> fillData) {
        if (fillData == null || fillData.isEmpty()) {
            return dbData;
        }
        ArrayList<IRowData> existValueRows = new ArrayList<IRowData>();
        ArrayList<IRowData> fillRows = new ArrayList<IRowData>();
        Map<String, List<RowItem>> rowMap = this.getRowMap(dbData, this.relation.getFilledEnumLinks());
        Boolean[] existFlag = new Boolean[dbData.size()];
        for (IRowData fillDatum : fillData) {
            String bizKeyStr = this.getEntityBizKeyStr(fillDatum, this.relation.getFilledEnumLinks());
            if (!StringUtils.hasLength(bizKeyStr)) continue;
            if (rowMap.isEmpty()) {
                fillRows.add(fillDatum);
                continue;
            }
            List<RowItem> existRows = rowMap.get(bizKeyStr);
            if (existRows != null) {
                for (RowItem existRow : existRows) {
                    existValueRows.add(existRow.rowData);
                    existFlag[((RowItem)existRow).rowIndex] = true;
                }
                continue;
            }
            fillRows.add(fillDatum);
        }
        List<IRowData> resData = this.doSort(existValueRows, fillRows);
        for (int i = 0; i < existFlag.length; ++i) {
            if (existFlag[i] != null) continue;
            resData.add(dbData.get(i));
        }
        resData = this.doPage(resData);
        return resData;
    }

    private List<IRowData> doPage(List<IRowData> resData) {
        int toIndex;
        int rowsPerPage;
        PageInfo pageInfo = this.queryInfo.getPageInfo();
        if (pageInfo == null) {
            return resData;
        }
        int pageIndex = pageInfo.getPageIndex();
        int fromIndex = Math.min(pageIndex * (rowsPerPage = pageInfo.getRowsPerPage()), resData.size());
        if (fromIndex == (toIndex = Math.min((pageIndex + 1) * rowsPerPage, resData.size()))) {
            return new ArrayList<IRowData>();
        }
        return new ArrayList<IRowData>(resData.subList(fromIndex, toIndex));
    }

    private boolean emptyRow(IRowData rowData) {
        List<MetaData> metaData = this.relation.getMetaData(this.queryInfo.selectLinkItr());
        List<MetaData> filledEnumLinks = this.relation.getFilledEnumLinks();
        Set enumLinkSet = filledEnumLinks.stream().map(MetaData::getLinkKey).collect(Collectors.toSet());
        for (MetaData metaDatum : metaData) {
            AbstractData data;
            IDataValue value;
            if (enumLinkSet.contains(metaDatum.getLinkKey()) || (value = rowData.getDataValueByLink(metaDatum.getLinkKey())) == null || (data = value.getAbstractData()) == null || data.isNull) continue;
            return false;
        }
        return true;
    }

    private List<IRowData> doSort(List<IRowData> existValueRows, List<IRowData> fillRows) {
        IRowData rowData;
        boolean emptyRow;
        Iterator<LinkSort> sortItr = this.queryInfo.linkSortItr();
        ArrayList<LinkSort> sortList = new ArrayList<LinkSort>();
        while (sortItr != null && sortItr.hasNext()) {
            sortList.add(sortItr.next());
        }
        if (sortList.isEmpty() || CollectionUtils.isEmpty(existValueRows)) {
            if (!CollectionUtils.isEmpty(fillRows)) {
                existValueRows.addAll(fillRows);
            }
            return existValueRows;
        }
        IRowData fillRow = null;
        if (!CollectionUtils.isEmpty(fillRows) && !(emptyRow = this.emptyRow(rowData = fillRows.get(0)))) {
            fillRow = rowData;
            existValueRows.add(fillRow);
        }
        existValueRows.sort(BaseFloatRegionDataStrategy.getiRowDataComparator(sortList));
        if (fillRow == null) {
            return existValueRows;
        }
        ArrayList<IRowData> res = new ArrayList<IRowData>();
        for (IRowData existValueRow : existValueRows) {
            if (existValueRow == fillRow) {
                res.addAll(fillRows);
                continue;
            }
            res.add(existValueRow);
        }
        return res;
    }

    private static Comparator<IRowData> getiRowDataComparator(List<LinkSort> sortList) {
        return (o1, o2) -> {
            for (LinkSort linkSort : sortList) {
                boolean asc = linkSort.getMode() == SortMode.ASC;
                IDataValue o1Value = o1.getDataValueByLink(linkSort.getLinkKey());
                IDataValue o2Value = o2.getDataValueByLink(linkSort.getLinkKey());
                if (o1Value == null && o2Value == null) continue;
                if (o1Value == null) {
                    return asc ? 1 : -1;
                }
                AbstractData o1Data = o1Value.getAbstractData();
                AbstractData o2Data = o2Value.getAbstractData();
                if (o1Data == null && o2Data == null) continue;
                if (o1Data == null) {
                    return asc ? 1 : -1;
                }
                int compare = o1Data.compareTo(o2Data);
                if (compare == 0) continue;
                return asc ? compare : -compare;
            }
            return 0;
        };
    }

    private Map<String, List<RowItem>> getRowMap(List<IRowData> dbData, List<MetaData> filledEnumLinks) {
        HashMap<String, List<RowItem>> rowMap = new HashMap<String, List<RowItem>>();
        for (int rowIndex = 0; rowIndex < dbData.size(); ++rowIndex) {
            IRowData rowData = dbData.get(rowIndex);
            String bizKey = this.getEntityBizKeyStr(rowData, filledEnumLinks);
            List rowList = rowMap.computeIfAbsent(bizKey, k -> new ArrayList());
            RowItem rowItem = new RowItem(rowData, rowIndex);
            rowList.add(rowItem);
        }
        return rowMap;
    }

    private String getEntityBizKeyStr(IRowData rowData, List<MetaData> filledEnumLinks) {
        ArrayList<String> bizKeys = new ArrayList<String>(filledEnumLinks.size());
        for (MetaData filledEnumLink : filledEnumLinks) {
            IDataValue dataValueByLink = rowData.getDataValueByLink(filledEnumLink.getLinkKey());
            if (dataValueByLink == null) continue;
            bizKeys.add(dataValueByLink.getAsString());
        }
        return String.join((CharSequence)",", bizKeys);
    }

    @Override
    protected List<IRowData> buildData(List<MetaData> metaData) {
        List<IRowData> dbRowData = super.buildData(metaData);
        boolean empty = CollectionUtils.isEmpty(this.relation.getFilledEnumLinks());
        if (!empty) {
            return this.mergeData(dbRowData, null);
        }
        return dbRowData;
    }

    @Override
    protected void metaRowTransfer(IDataRow row, RowData rowData) {
        if (this.bizKeyOrderIndex >= 0) {
            AbstractData value = row.getValue(this.bizKeyOrderIndex);
            rowData.setRecKey(value.getAsString());
        }
        super.metaRowTransfer(row, rowData);
    }

    private static class RowItem {
        private final IRowData rowData;
        private final int rowIndex;

        public RowItem(IRowData rowData, int rowIndex) {
            this.rowData = rowData;
            this.rowIndex = rowIndex;
        }
    }
}

