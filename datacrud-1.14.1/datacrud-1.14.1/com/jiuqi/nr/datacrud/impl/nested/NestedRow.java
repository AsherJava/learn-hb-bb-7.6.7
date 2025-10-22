/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.datacrud.impl.nested;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.impl.nested.DataRowWrapper;
import com.jiuqi.nr.datacrud.impl.nested.LinkNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NestedRow
implements Comparable<NestedRow> {
    protected List<DataRowWrapper> rows;
    protected LinkNode head;
    protected Map<String, NestedRow> rootMap;
    protected List<LinkSort> sorts;
    protected List<AbstractData> sortValues;

    public NestedRow(List<LinkSort> sorts) {
        this.sorts = sorts;
        this.sortValues = new ArrayList<AbstractData>();
        for (LinkSort sort : this.sorts) {
            this.sortValues.add(null);
        }
    }

    public NestedRow(LinkNode head, List<LinkSort> sorts) {
        this.head = head;
        this.sorts = sorts;
        this.sortValues = new ArrayList<AbstractData>();
        for (LinkSort sort : this.sorts) {
            this.sortValues.add(null);
        }
    }

    public List<AbstractData> getSortValues() {
        return this.sortValues;
    }

    @Override
    public int compareTo(NestedRow o) {
        List<AbstractData> oSortValues = o.getSortValues();
        for (int i = 0; i < this.sortValues.size(); ++i) {
            AbstractData oSortData;
            AbstractData sortData = this.sortValues.get(i);
            if (sortData == (oSortData = oSortValues.get(i)) || sortData == null && oSortData.isNull || oSortData == null && sortData.isNull || sortData != null && sortData.isNull && oSortData.isNull) continue;
            LinkSort linkSort = this.sorts.get(i);
            SortMode mode = linkSort.getMode();
            if (sortData == null || sortData.isNull) {
                if (mode == SortMode.ASC) {
                    return -1;
                }
                return 1;
            }
            int compared = sortData.compareTo(oSortData);
            if (compared == 0) continue;
            if (mode == SortMode.ASC) {
                return compared;
            }
            return -compared;
        }
        return 0;
    }

    public void addRow(IRowData row) {
        this.reSetOrder(row);
        LinkNode next = this.head.getNext();
        if (next == null) {
            if (this.rows == null) {
                this.rows = new ArrayList<DataRowWrapper>();
            }
            DataRowWrapper dataRowWrapper = new DataRowWrapper(this.sorts);
            dataRowWrapper.setRowData(row);
            dataRowWrapper.reSetOrder(row);
            this.rows.add(dataRowWrapper);
        } else {
            String link = next.getValue();
            IDataValue dataValue = row.getDataValueByLink(link);
            String value = dataValue.toString();
            if (this.rootMap == null) {
                this.rootMap = new HashMap<String, NestedRow>();
            }
            NestedRow groupRow = this.rootMap.computeIfAbsent(value, k -> new NestedRow(next, this.sorts));
            groupRow.addRow(row);
        }
    }

    protected void reSetOrder(IRowData row) {
        for (int i = 0; i < this.sorts.size(); ++i) {
            LinkSort sort = this.sorts.get(i);
            IDataValue value = row.getDataValueByLink(sort.getLinkKey());
            AbstractData newSortData = value.getAbstractData();
            AbstractData sortData = this.sortValues.get(i);
            SortMode mode = sort.getMode();
            if (sortData == null) {
                this.sortValues.set(i, newSortData);
                continue;
            }
            if (mode == SortMode.ASC) {
                if (sortData.compareTo(newSortData) <= 0) continue;
                this.sortValues.set(i, newSortData);
                continue;
            }
            if (sortData.compareTo(newSortData) >= 0) continue;
            this.sortValues.set(i, newSortData);
        }
    }

    public List<NestedRow> getChildren() {
        if (this.rootMap != null) {
            return new ArrayList<NestedRow>(this.rootMap.values());
        }
        return null;
    }

    public List<IRowData> getRows() {
        if (this.rows == null) {
            return null;
        }
        return this.rows.stream().sorted().map(DataRowWrapper::getRowData).collect(Collectors.toList());
    }
}

