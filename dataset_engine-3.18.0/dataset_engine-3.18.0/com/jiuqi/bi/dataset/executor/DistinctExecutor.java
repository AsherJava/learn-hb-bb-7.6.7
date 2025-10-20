/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.executor;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class DistinctExecutor {
    private BIDataSetImpl dataset;

    public DistinctExecutor(BIDataSetImpl dataset) {
        this.dataset = dataset;
    }

    public BIDataSetImpl distinct(List<Integer> returnFields, boolean appendCounterColumn) throws BIDataSetException {
        ArrayList<Integer> returnFieldList = new ArrayList<Integer>();
        returnFieldList.addAll(returnFields);
        this.appendKeyFieldToReturnFieldList(returnFieldList, returnFields);
        this.appendParentFieldToReturnFieldList(returnFieldList);
        ArrayList<Integer> nameFieldList = new ArrayList<Integer>();
        ArrayList<Integer> keyFieldList = new ArrayList<Integer>();
        this.classifyFieldList(returnFieldList, keyFieldList, nameFieldList);
        try {
            return this.distinct(returnFieldList, keyFieldList, nameFieldList, appendCounterColumn);
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
    }

    public int distinctCount(List<Integer> returnFields) throws BIDataSetException {
        ArrayList<Integer> returnFieldList = new ArrayList<Integer>();
        returnFieldList.addAll(returnFields);
        this.appendKeyFieldToReturnFieldList(returnFieldList, returnFields);
        this.appendParentFieldToReturnFieldList(returnFieldList);
        ArrayList<Integer> nameFieldList = new ArrayList<Integer>();
        ArrayList<Integer> keyFieldList = new ArrayList<Integer>();
        this.classifyFieldList(returnFieldList, keyFieldList, nameFieldList);
        try {
            Set<DataRowInfo> rowInfoSet = this.getDistinctDataRowInfoSet(returnFieldList, keyFieldList, nameFieldList);
            return rowInfoSet.size();
        }
        catch (SyntaxException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
    }

    private BIDataSetImpl distinct(List<Integer> returnFieldList, List<Integer> keyFieldList, List<Integer> nameFieldList, boolean appendCounterColumn) throws Exception {
        Metadata<BIDataSetFieldInfo> newMetadata = this.createMetadata(returnFieldList);
        if (appendCounterColumn) {
            BIDataSetFieldInfo info = new BIDataSetFieldInfo("F_TOTAL", 5, "\u603b\u6570");
            info.setAggregation(AggregationType.SUM);
            info.setFieldType(FieldType.MEASURE);
            newMetadata.addColumn(new Column(info.getName(), info.getValType(), info.getTitle(), (Object)info));
        }
        MemoryDataSet newMemoryDataset = new MemoryDataSet(BIDataSetFieldInfo.class, newMetadata);
        Set<DataRowInfo> rowInfoSet = this.getDistinctDataRowInfoSet(returnFieldList, keyFieldList, nameFieldList);
        DataRowInfo[] array = rowInfoSet.toArray(new DataRowInfo[rowInfoSet.size()]);
        Arrays.sort(array, new Comparator<DataRowInfo>(){

            @Override
            public int compare(DataRowInfo v1, DataRowInfo v2) {
                return v1.rownum - v2.rownum;
            }
        });
        if (appendCounterColumn) {
            for (DataRowInfo info : array) {
                Object[] copyed = new Object[info.data.length + 1];
                System.arraycopy(info.data, 0, copyed, 0, info.data.length);
                copyed[copyed.length - 1] = info.counter;
                newMemoryDataset.add(copyed);
            }
        } else {
            for (DataRowInfo info : array) {
                newMemoryDataset.add(info.data);
            }
        }
        BIDataSetImpl newds = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        newds.setParameterEnv(this.dataset.getEnhancedParameterEnv());
        newds.setLogger(this.dataset.getLogger());
        return newds;
    }

    private void appendKeyFieldToReturnFieldList(List<Integer> returnFieldList, List<Integer> returnFields) throws BIDataSetException {
        for (Integer colIdx : returnFields) {
            Integer keyCol;
            Column column = this.dataset.getMetadata().getColumn(colIdx.intValue());
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            String keyField = info.getKeyField();
            if (!StringUtils.isNotEmpty((String)keyField) || (keyCol = Integer.valueOf(this.dataset.getMetadata().indexOf(keyField))) == -1 || returnFieldList.contains(keyCol)) continue;
            returnFieldList.add(keyCol);
        }
    }

    private void appendParentFieldToReturnFieldList(List<Integer> returnFieldList) throws BIDataSetException {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        Map props = metadata.getProperties();
        List hiers = (List)props.get("HIERARCHY");
        if (hiers == null) {
            return;
        }
        for (DSHierarchy hier : hiers) {
            int parentIdx;
            Integer dimIdx;
            if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY || !returnFieldList.contains(dimIdx = Integer.valueOf(metadata.indexOf(hier.getLevels().get(0)))) || (parentIdx = metadata.indexOf(hier.getParentFieldName())) == -1 || returnFieldList.contains(parentIdx)) continue;
            returnFieldList.add(parentIdx);
        }
    }

    private Object[] getSepcifiedColValue(List<Integer> colIdx, int row) {
        Object[] data = this.dataset.getRowData(row);
        Object[] subdata = new Object[colIdx.size()];
        for (int i = 0; i < colIdx.size(); ++i) {
            subdata[i] = data[colIdx.get(i)];
        }
        return subdata;
    }

    private Set<DataRowInfo> getDistinctDataRowInfoSet(List<Integer> returnFieldList, List<Integer> keyFieldList, List<Integer> nameFieldList) throws SyntaxException {
        int sys_timekeyIdx = this.dataset.getMetadata().indexOf("SYS_TIMEKEY");
        int[] keyIdx = new int[keyFieldList.size()];
        for (int i = 0; i < keyFieldList.size(); ++i) {
            keyIdx[i] = returnFieldList.indexOf(keyFieldList.get(i));
        }
        int count = this.dataset.getRecordCount();
        HashMap<DataRowInfo, DataRowInfo> rowInfoMap = new HashMap<DataRowInfo, DataRowInfo>();
        for (int r = 0; r < count; ++r) {
            Object[] rowDatas;
            DataRowInfo rowInfo;
            DataRowInfo last;
            Object[] data = this.dataset.getRowData(r);
            String timekey = null;
            if (sys_timekeyIdx != -1 && data[sys_timekeyIdx] != null) {
                timekey = data[sys_timekeyIdx].toString();
            }
            if ((last = (DataRowInfo)rowInfoMap.get(rowInfo = new DataRowInfo(rowDatas = this.getSepcifiedColValue(returnFieldList, r), keyIdx, timekey, r))) == null) {
                ++rowInfo.counter;
                rowInfoMap.put(rowInfo, rowInfo);
                continue;
            }
            ++last.counter;
            int compareTo = DataType.compare((int)6, (Object)rowInfo.timekey, (Object)last.timekey);
            if (compareTo <= 0) continue;
            rowInfo.rownum = last.rownum;
            rowInfoMap.put(rowInfo, rowInfo);
            rowInfo.counter = last.counter;
        }
        return new HashSet<DataRowInfo>(rowInfoMap.values());
    }

    private void classifyFieldList(List<Integer> returnFieldList, List<Integer> keyFieldList, List<Integer> nameFieldList) {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        for (Integer colIdx : returnFieldList) {
            Column column = metadata.getColumn(colIdx.intValue());
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (info.getName().equalsIgnoreCase(info.getKeyField())) {
                keyFieldList.add(colIdx);
                continue;
            }
            nameFieldList.add(colIdx);
        }
        Map props = metadata.getProperties();
        List hiers = (List)props.get("HIERARCHY");
        if (hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                Integer parentIdx = metadata.indexOf(hier.getParentFieldName());
                keyFieldList.remove(parentIdx);
                nameFieldList.add(parentIdx);
            }
        }
    }

    private Metadata<BIDataSetFieldInfo> createMetadata(List<Integer> returnFieldList) {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        Metadata newMetadata = new Metadata(null);
        for (Integer colIdx : returnFieldList) {
            Column column = metadata.getColumn(colIdx.intValue());
            Column newColumn = column.clone();
            newColumn.setInfo((Object)((BIDataSetFieldInfo)column.getInfo()).clone());
            newMetadata.getColumns().add(newColumn);
        }
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.putAll(metadata.getProperties());
        for (Map.Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            map.remove(key);
            newMetadata.getProperties().put(entry.getKey(), entry.getValue());
        }
        return newMetadata;
    }

    private final class DataRowInfo {
        public final Object[] data;
        public final int[] keyIdx;
        public final String timekey;
        public int rownum;
        public int counter = 0;

        public DataRowInfo(Object[] data, int[] keyIdx, String timekey, int rownum) {
            this.data = data;
            this.keyIdx = keyIdx;
            this.rownum = rownum;
            this.timekey = timekey;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof DataRowInfo)) {
                return false;
            }
            DataRowInfo other = (DataRowInfo)obj;
            if (this.keyIdx.length != other.keyIdx.length) {
                return false;
            }
            for (int i = 0; i < this.keyIdx.length; ++i) {
                int idx = this.keyIdx[i];
                Object o1 = this.data[idx];
                int otherIdx = other.keyIdx[i];
                Object o2 = other.data[otherIdx];
                int compareTo = DataType.compareObject((Object)o1, (Object)o2);
                if (compareTo == 0) continue;
                return false;
            }
            if (this.keyIdx == null || this.keyIdx.length == 0) {
                for (int idx = 0; idx < this.data.length; ++idx) {
                    Object o1 = this.data[idx];
                    Object o2 = other.data[idx];
                    int compareTo = DataType.compareObject((Object)o1, (Object)o2);
                    if (compareTo == 0) continue;
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int result = 1;
            for (int key : this.keyIdx) {
                Object element = this.data[key];
                result = 31 * result + (element == null ? 0 : element.hashCode());
            }
            return result;
        }

        public String toString() {
            return Arrays.toString(this.data);
        }
    }
}

