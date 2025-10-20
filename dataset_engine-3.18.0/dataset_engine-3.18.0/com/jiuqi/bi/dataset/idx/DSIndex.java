/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.dataset.idx;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.idx.DSBooleanColumnIndex;
import com.jiuqi.bi.dataset.idx.DSColumnIndex;
import com.jiuqi.bi.dataset.idx.DSCompoundIndex;
import com.jiuqi.bi.dataset.idx.DSIndexEntry;
import com.jiuqi.bi.dataset.idx.DSIndexException;
import com.jiuqi.bi.dataset.idx.IdxFilterInfo;
import com.jiuqi.bi.dataset.idx.SysRowNumColumnIndex;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.syntax.SyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public final class DSIndex {
    private BIDataSetImpl dataset;
    private DSIndexEntry[] dsIdxes;
    private List<DSCompoundIndex> compoundIdxes;

    public DSIndex(BIDataSetImpl dataset) {
        this.dataset = dataset;
        this.dsIdxes = new DSIndexEntry[dataset.getMetadata().getColumnCount()];
        this.compoundIdxes = new ArrayList<DSCompoundIndex>();
    }

    public synchronized void build(int[] cols) throws DSIndexException {
        if (cols == null) {
            cols = this.getAvailableCols();
        }
        ArrayList<DSIndexEntry> colIdxList = new ArrayList<DSIndexEntry>();
        for (int col : cols) {
            if (this.dsIdxes[col] != null) continue;
            this.check(col);
            this.dsIdxes[col] = this.newColumnIndex(this.dataset, col);
            colIdxList.add(this.dsIdxes[col]);
        }
        int count = this.dataset.getRecordCount();
        for (int i = 0; i < count; ++i) {
            for (DSIndexEntry idx : colIdxList) {
                idx.process(i, this.dataset.getRowData(i));
            }
        }
        for (DSIndexEntry idx : colIdxList) {
            idx.commit();
        }
    }

    public boolean containCompoundIndex(int[] cols) {
        DSCompoundIndex idx = new DSCompoundIndex(this.dataset, cols);
        return this.compoundIdxes.contains(idx);
    }

    public synchronized void buildCompoundIndex(int[] cols) throws DSIndexException {
        if (cols == null || cols.length < 2) {
            throw new DSIndexException("\u4f20\u5165\u7684\u5217\u4fe1\u606f\u4e0d\u5bf9\uff0c\u81f3\u5c11\u5e94\u8be5\u662f2\u5217");
        }
        DSCompoundIndex idx = new DSCompoundIndex(this.dataset, cols);
        this.compoundIdxes.add(idx);
        int count = this.dataset.getRecordCount();
        for (int i = 0; i < count; ++i) {
            idx.process(i, this.dataset.getRowData(i));
        }
        idx.commit();
        if (this.compoundIdxes.size() > 1) {
            Collections.sort(this.compoundIdxes, new Comparator<DSCompoundIndex>(){

                @Override
                public int compare(DSCompoundIndex o1, DSCompoundIndex o2) {
                    return o1.getColumns().length - o2.getColumns().length;
                }
            });
        }
    }

    public synchronized void deleteCompoundIndex(int[] cols) throws DSIndexException {
        for (int i = 0; i < this.compoundIdxes.size(); ++i) {
            if (!Arrays.equals(cols, this.compoundIdxes.get(i).getColumns())) continue;
            this.compoundIdxes.remove(i);
            return;
        }
    }

    public List<Integer> search(int col, Object data) {
        int count = this.dataset.getRecordCount();
        if (count == 0) {
            return Collections.emptyList();
        }
        if (count == 1) {
            return com.jiuqi.bi.syntax.DataType.compareObject((Object)this.dataset.getRowData(0)[col], (Object)data) == 0 ? Collections.singletonList(0) : Collections.emptyList();
        }
        if (this.isIndexSupport(col)) {
            DSIndexEntry dsColIdx = this.tryBuildColumnIdx(col);
            return dsColIdx.search(data);
        }
        ArrayList<Integer> rows = new ArrayList<Integer>();
        Column column = this.dataset.getMetadata().getColumn(col);
        int dataType = ((BIDataSetFieldInfo)column.getInfo()).getValType();
        if (dataType == 5 && !(data instanceof Integer)) {
            dataType = 3;
        }
        for (int row = 0; row < count; ++row) {
            Object cur = this.dataset.getRowData(row)[col];
            if (!this.isEqual(dataType, cur, data)) continue;
            rows.add(row);
        }
        return rows;
    }

    public List<Integer> search(int col, List<Object> dataList) {
        if (dataList.size() == 0) {
            return Collections.emptyList();
        }
        if (dataList.size() == 1) {
            return this.search(col, dataList.get(0));
        }
        if (this.isIndexSupport(col)) {
            DSIndexEntry dsColIdx = this.tryBuildColumnIdx(col);
            ArrayList<List<Integer>> list = new ArrayList<List<Integer>>();
            for (Object data : dataList) {
                List<Integer> rows = dsColIdx.search(data);
                if (rows.size() <= 0) continue;
                list.add(rows);
            }
            return this.union(list);
        }
        ArrayList<Integer> rows = new ArrayList<Integer>();
        Column column = this.dataset.getMetadata().getColumn(col);
        int dataType = ((BIDataSetFieldInfo)column.getInfo()).getValType();
        int count = this.dataset.getRecordCount();
        for (int row = 0; row < count; ++row) {
            Object cur = this.dataset.getRowData(row)[col];
            if (!this.isInList(dataType, cur, dataList)) continue;
            rows.add(row);
        }
        rows.trimToSize();
        return rows;
    }

    public List<Integer> search(int[] cols, Object[] data) {
        if (cols.length != data.length) {
            throw new RuntimeException("\u4f20\u5165\u53c2\u6570\u6709\u8bef\uff0c\u5217\u4fe1\u606f\u4e0e\u503c\u5217\u8868\u957f\u5ea6\u4e0d\u76f8\u7b49");
        }
        int count = this.dataset.getRecordCount();
        if (count == 0) {
            return Collections.emptyList();
        }
        if (count == 1) {
            Object[] rowData = this.dataset.getRowData(0);
            for (int c = 0; c < cols.length; ++c) {
                if (com.jiuqi.bi.syntax.DataType.compareObject((Object)rowData[cols[c]], (Object)data[c]) == 0) continue;
                return Collections.emptyList();
            }
            return Collections.singletonList(0);
        }
        if (cols.length == 1) {
            return this.search(cols[0], data[0]);
        }
        ArrayList<List<Integer>> rowList = new ArrayList<List<Integer>>();
        int[] flag = new int[cols.length];
        if (!this.compoundIdxes.isEmpty()) {
            for (DSCompoundIndex idx : this.compoundIdxes) {
                int[] idxRefCols = idx.getColumns();
                if (!DSIndex.isSubset(cols, idxRefCols)) continue;
                ArrayList<Object> key = new ArrayList<Object>();
                for (int i = 0; i < idxRefCols.length; ++i) {
                    flag[i] = 1;
                    int pos = -1;
                    for (int p = 0; p < cols.length; ++p) {
                        if (cols[p] != idxRefCols[i]) continue;
                        pos = p;
                        break;
                    }
                    key.add(data[pos]);
                }
                List<Integer> rs = idx.search(key);
                if (cols.length == idxRefCols.length) {
                    return rs;
                }
                rowList.add(rs);
            }
        }
        for (int i = 0; i < cols.length; ++i) {
            if (flag[i] == 1) continue;
            List<Integer> rows = this.search(cols[i], data[i]);
            rowList.add(rows);
        }
        return this.intersect(rowList);
    }

    public Integer searchFirstRow(int[] cols, Object[] data) {
        if (cols.length != data.length) {
            throw new RuntimeException("\u4f20\u5165\u53c2\u6570\u6709\u8bef\uff0c\u5217\u4fe1\u606f\u4e0e\u503c\u5217\u8868\u957f\u5ea6\u4e0d\u76f8\u7b49");
        }
        ArrayList<List<Integer>> rowList = new ArrayList<List<Integer>>(cols.length);
        for (int i = 0; i < cols.length; ++i) {
            List<Integer> rows = this.search(cols[i], data[i]);
            rowList.add(rows);
        }
        return this.intersectFirstRow(rowList);
    }

    public boolean isIndexSupport(int col) {
        Column column = this.dataset.getMetadata().getColumn(col);
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
        if (info.getFieldType() == FieldType.MEASURE) {
            return false;
        }
        return info.getValType() == DataType.STRING.value() || info.getValType() == DataType.INTEGER.value() || info.getValType() == DataType.BOOLEAN.value();
    }

    public List<Integer> doFilter(List<IdxFilterInfo> filters) {
        if (filters.size() == 1) {
            return this.search(filters.get((int)0).colIdx, filters.get((int)0).value);
        }
        ArrayList<List<Integer>> list = new ArrayList<List<Integer>>();
        for (IdxFilterInfo fi : filters) {
            List<Integer> rs = this.search(fi.colIdx, fi.value);
            list.add(rs);
        }
        List<Integer> rs = this.intersect(list);
        return rs;
    }

    public List<Integer> _filter(List<FilterItem> filters) {
        if (filters == null || filters.size() == 0) {
            throw new RuntimeException("\u8fc7\u6ee4\u5217\u8868\u4e3a\u7a7a");
        }
        ArrayList<List<Integer>> list = new ArrayList<List<Integer>>();
        for (FilterItem fi : filters) {
            int col;
            if (fi.getKeyList() == null || fi.getKeyList().size() == 0 || (col = this.dataset.getMetadata().indexOf(fi.getFieldName())) == -1) continue;
            List<Integer> rs = this.search(col, fi.getKeyList());
            list.add(rs);
        }
        if (list.size() == 1) {
            return (List)list.get(0);
        }
        List<Integer> rs = this.intersect(list);
        return rs;
    }

    public List<Integer> intersect(List<List<Integer>> rowList) {
        int len = rowList.size();
        for (int i = 0; i < len; ++i) {
            int size = rowList.get(i).size();
            if (size != 0) continue;
            return new ArrayList<Integer>(0);
        }
        int[] counter = new int[this.dataset.getRecordCount()];
        int min = 0;
        int max = this.dataset.getRecordCount() - 1;
        for (int i = 0; i < len; ++i) {
            List<Integer> allRows = rowList.get(i);
            int size = allRows.size();
            int start = 0;
            int end = size - 1;
            int v = allRows.get(0);
            if (v >= min) {
                min = v;
            } else {
                start = DSIndex.binarySearch(allRows, min);
            }
            v = allRows.get(size - 1);
            if (v <= max) {
                max = v;
            } else {
                end = DSIndex.binarySearch(allRows, max);
            }
            if (min > max) {
                return new ArrayList<Integer>(0);
            }
            for (int s = start; s <= end; ++s) {
                int pos;
                int n = pos = allRows.get(s).intValue();
                counter[n] = counter[n] + 1;
            }
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = min; i <= max; ++i) {
            if (counter[i] != len) continue;
            result.add(i);
        }
        return result;
    }

    public static boolean hasIntersection(List<Integer> list1, List<Integer> list2) {
        if (list1.size() > list2.size()) {
            List<Integer> temp = list1;
            list1 = list2;
            list2 = temp;
        }
        HashSet<Integer> set = new HashSet<Integer>(list1);
        for (Integer num : list2) {
            if (!set.contains(num)) continue;
            return true;
        }
        return false;
    }

    private static int binarySearch(List<Integer> list, int key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = list.get(mid);
            if (midVal < key) {
                low = mid + 1;
                continue;
            }
            if (midVal > key) {
                high = mid - 1;
                continue;
            }
            return mid;
        }
        return low;
    }

    private Integer intersectFirstRow(List<List<Integer>> rowList) {
        int size = rowList.size();
        int[] cursor = new int[size];
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            List<Integer> rs = rowList.get(0);
            if (rs.size() > 0) {
                return rs.get(0);
            }
            return null;
        }
        boolean finish = false;
        Integer curRowMax = 0;
        int count = 0;
        while (!finish) {
            boolean isEmpty = false;
            for (int i = 0; i < size; ++i) {
                List<Integer> colIdx = rowList.get(i);
                if (cursor[i] == colIdx.size()) {
                    isEmpty = true;
                }
                while (cursor[i] < colIdx.size()) {
                    Integer row = colIdx.get(cursor[i]);
                    int compareTo = row - curRowMax;
                    if (compareTo > 0) {
                        curRowMax = row;
                        count = 1;
                    } else if (compareTo == 0) {
                        ++count;
                    }
                    int n = i;
                    cursor[n] = cursor[n] + 1;
                    if (compareTo < 0) continue;
                    break;
                }
                if (count != size) continue;
                return curRowMax;
            }
            if (!isEmpty) continue;
            finish = true;
            break;
        }
        return null;
    }

    private List<Integer> union(List<List<Integer>> rowList) {
        int len = rowList.size();
        if (len == 0) {
            return new ArrayList<Integer>(0);
        }
        if (len == 1) {
            return rowList.get(0);
        }
        int size = this.dataset.getRecordCount();
        if (size == 0) {
            return new ArrayList<Integer>(0);
        }
        int min = size - 1;
        int max = 0;
        boolean FLAG = true;
        byte[] label = new byte[size];
        for (List<Integer> rows : rowList) {
            int start = rows.get(0);
            int end = rows.get(rows.size() - 1);
            if (min > start) {
                min = start;
            }
            if (max < end) {
                max = end;
            }
            for (Integer row : rows) {
                label[row.intValue()] = 1;
            }
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = min; i <= max; ++i) {
            if (label[i] != 1) continue;
            result.add(i);
        }
        return result;
    }

    private int[] getAvailableCols() {
        ArrayList<Integer> colIdxes = new ArrayList<Integer>();
        int count = this.dataset.getMetadata().getColumnCount();
        for (int i = 0; i < count; ++i) {
            if (!this.isIndexSupport(i)) continue;
            colIdxes.add(i);
        }
        int[] cols = new int[colIdxes.size()];
        for (int i = 0; i < colIdxes.size(); ++i) {
            cols[i] = (Integer)colIdxes.get(i);
        }
        return cols;
    }

    private void check(int col) throws DSIndexException {
        Column column = this.dataset.getMetadata().getColumn(col);
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
        if (info.getFieldType() == FieldType.MEASURE) {
            throw new DSIndexException("\u5b57\u6bb5" + info.getName() + "\u4e3a\u5ea6\u91cf\u5b57\u6bb5\uff0c\u4e0d\u80fd\u6784\u5efa\u7d22\u5f15");
        }
        if (info.getValType() != DataType.STRING.value() && info.getValType() != DataType.INTEGER.value() && info.getValType() != DataType.BOOLEAN.value()) {
            throw new DSIndexException("\u5b57\u6bb5" + info.getName() + "\u7684\u6570\u636e\u7c7b\u578b\u4e3a" + DataType.valueOf(info.getValType()).name() + "\uff0c\u4e0d\u80fd\u6784\u5efa\u7d22\u5f15");
        }
    }

    private boolean isEqual(int dataType, Object o1, Object o2) {
        try {
            int compare = com.jiuqi.bi.syntax.DataType.compare((int)dataType, (Object)o1, (Object)o2);
            return compare == 0;
        }
        catch (SyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isInList(int dataType, Object o1, List<Object> list) {
        for (Object obj : list) {
            if (!this.isEqual(dataType, o1, obj)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DSIndexEntry tryBuildColumnIdx(int col) {
        if (this.dsIdxes[col] != null) return this.dsIdxes[col];
        DSIndexEntry[] dSIndexEntryArray = this.dsIdxes;
        synchronized (this.dsIdxes) {
            this.dsIdxes[col] = this.newColumnIndex(this.dataset, col);
            this.dsIdxes[col].build();
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return this.dsIdxes[col];
        }
    }

    private DSIndexEntry newColumnIndex(BIDataSetImpl dataset, int col) {
        Column column = dataset.getMetadata().getColumn(col);
        int valType = ((BIDataSetFieldInfo)column.getInfo()).getValType();
        if (valType == DataType.BOOLEAN.value()) {
            return new DSBooleanColumnIndex(dataset, col);
        }
        if (valType == DataType.STRING.value()) {
            return new DSColumnIndex(dataset, col);
        }
        if (!dataset.isReferencedDs() && column.getName().equals("SYS_ROWNUM")) {
            return new SysRowNumColumnIndex(dataset, col);
        }
        return new DSColumnIndex(dataset, col);
    }

    private static boolean isSubset(int[] arr1, int[] arr2) {
        if (arr2.length > arr1.length) {
            return false;
        }
        for (int p = 0; p < arr2.length; ++p) {
            if (arr2[p] == arr1[p]) continue;
            return false;
        }
        return true;
    }
}

