/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.dataset.stat;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.StatConfig;
import com.jiuqi.bi.dataset.stat.device.BigDecimalAvgStatDevice;
import com.jiuqi.bi.dataset.stat.device.BigDecimalSumStatDevice;
import com.jiuqi.bi.dataset.stat.device.CountStatDevice;
import com.jiuqi.bi.dataset.stat.device.DimKeyFieldStatDevice;
import com.jiuqi.bi.dataset.stat.device.DimNameFieldStatDevice;
import com.jiuqi.bi.dataset.stat.device.DoubleAvgStatDevice;
import com.jiuqi.bi.dataset.stat.device.DoubleSumStatDevice;
import com.jiuqi.bi.dataset.stat.device.DummyStatDevice;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;
import com.jiuqi.bi.dataset.stat.device.IntAvgStatDevice;
import com.jiuqi.bi.dataset.stat.device.IntSumStatDevice;
import com.jiuqi.bi.dataset.stat.device.MaxStatDevice;
import com.jiuqi.bi.dataset.stat.device.MinStatDevice;
import com.jiuqi.bi.dataset.stat.device.TimeOpenblanceStatDevice;
import com.jiuqi.bi.dataset.stat.device.TimePointStatDevice;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.dataset.stat.info.StatInfoHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class StatProcessor {
    private BIDataSetImpl dataset;
    private StatConfig statConfig;
    private List<FieldStatDevice> devices;
    private RecordBuffer activeBuffer;
    private Map<RecordBuffer, RecordBuffer> records;
    private StatInfo statInfo;
    private Map<DataKey, Integer> dimkeyFirstAppearRowMap;
    private List<Integer> destKeyExceptParent;

    public StatProcessor(BIDataSetImpl dataset, StatConfig statConfig) {
        this.dataset = dataset;
        this.statConfig = statConfig;
    }

    public BIDataSetImpl stat(StatInfo statInfo) throws BIDataSetException {
        this.statInfo = statInfo;
        this.destKeyExceptParent = new ArrayList<Integer>(statInfo.destKeyColIdx);
        this.destKeyExceptParent.removeAll(statInfo.reserveParentColIdx);
        this.initDevice();
        this.activeBuffer = new RecordBuffer(this.devices.size(), this.destKeyExceptParent);
        this.records = new TreeMap<RecordBuffer, RecordBuffer>();
        this.dimkeyFirstAppearRowMap = new TreeMap<DataKey, Integer>();
        ArrayList<int[]> compoundColIdxes = new ArrayList<int[]>();
        int[] srcCols = this.convert(statInfo.srcKeyColIdx);
        if (statInfo.parentKeyColIdx != null) {
            for (Integer parentKeyCol : statInfo.parentKeyColIdx) {
                int[] compoundCols = srcCols;
                if (!statInfo.srcKeyColIdx.contains(parentKeyCol)) {
                    compoundCols = new int[srcCols.length + 1];
                    System.arraycopy(srcCols, 0, compoundCols, 0, srcCols.length);
                    compoundCols[srcCols.length] = parentKeyCol;
                }
                if (compoundCols.length <= 1) continue;
                this.dataset.getDSIndex().buildCompoundIndex(compoundCols);
                compoundColIdxes.add(compoundCols);
            }
        }
        int size = this.dataset.getRecordCount();
        for (int s = 0; s < size; ++s) {
            this.process(s);
        }
        for (int[] cols : compoundColIdxes) {
            this.dataset.getDSIndex().deleteCompoundIndex(cols);
        }
        return this.createDataset();
    }

    public BIDataSetImpl stat(List<Integer> reserveDimColList, List<AggrMeasureItem> reserveMsList) throws BIDataSetException {
        return this.stat(this.createStatInfo(reserveDimColList, reserveMsList));
    }

    public StatInfo createStatInfo(List<Integer> reserveDimColList, List<AggrMeasureItem> reserveMsList) {
        StatInfoHelper helper = new StatInfoHelper(this.dataset, this.statConfig);
        return helper.createStatInfo(reserveDimColList, reserveMsList);
    }

    private void process(int rowIdx) throws BIDataSetException {
        BIDataRow dataRow = this.dataset.get(rowIdx);
        this.statKeys(dataRow, this.activeBuffer);
        RecordBuffer recBuffer = this.records.get(this.activeBuffer);
        if (recBuffer == null) {
            recBuffer = new RecordBuffer(this.devices.size(), this.destKeyExceptParent);
            for (int i = 0; i < this.devices.size(); ++i) {
                this.devices.get(i).init(recBuffer.getBuffer());
            }
            this.statKeys(dataRow, recBuffer);
            if (this.statConfig.isNeedSort) {
                int[] firstRowInDs = this.getRecordFirstAppearRow(dataRow);
                recBuffer.setFirstRowInDs(firstRowInDs);
            }
            this.records.put(recBuffer, recBuffer);
        } else {
            this.statKeys(dataRow, recBuffer);
        }
        this.statMs(dataRow, recBuffer);
        this.updateNewestTimekey(dataRow, recBuffer);
    }

    private BIDataSetImpl createDataset() throws BIDataSetException {
        MemoryDataSet newMemoryDataset = new MemoryDataSet(BIDataSetFieldInfo.class, this.statInfo.metadata.clone());
        Collection<RecordBuffer> recordList = this.records.values();
        if (this.statConfig.isNeedSort) {
            recordList = new ArrayList<RecordBuffer>(recordList);
            this.doSort((List)recordList);
        }
        try {
            for (RecordBuffer rdBuf : recordList) {
                Object[] data = new Object[this.statInfo.metadata.size()];
                for (int i = 0; i < data.length; ++i) {
                    data[i] = this.devices.get(i).toValue(rdBuf);
                }
                newMemoryDataset.add(data);
            }
        }
        catch (DataSetException e) {
            throw new BIDataSetException("\u590d\u5236\u6570\u636e\u96c6\u8bb0\u5f55\u65f6\u51fa\u9519", e);
        }
        if (this.statInfo.isTimeWasAggr) {
            TimeKeyBuilder.buildTimeKey((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        }
        BIDataSetImpl newdataset = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        newdataset.setParameterEnv(this.dataset.getEnhancedParameterEnv());
        newdataset.setLogger(this.dataset.getLogger());
        return newdataset;
    }

    private void statKeys(BIDataRow dataRow, RecordBuffer recordBuf) {
        for (int i : this.statInfo.destDimColIdx) {
            this.devices.get(i).stat(dataRow, recordBuf);
        }
    }

    private void statMs(BIDataRow dataRow, RecordBuffer recordBuf) {
        for (int i : this.statInfo.destMsColIdx) {
            this.devices.get(i).stat(dataRow, recordBuf);
        }
    }

    private void updateNewestTimekey(BIDataRow dataRow, RecordBuffer recordBuf) {
        String curTimekey = null;
        if (this.statInfo.sys_timekey_index >= 0) {
            curTimekey = dataRow.getString(this.statInfo.sys_timekey_index);
        }
        if (curTimekey == null) {
            return;
        }
        String min = recordBuf.getMin_timekey();
        String max = recordBuf.getMax_timekey();
        if (max == null || max.compareTo(curTimekey) < 0) {
            recordBuf.setMax_timekey(curTimekey);
        }
        if (min == null || min.compareTo(curTimekey) > 0) {
            recordBuf.setMin_timekey(curTimekey);
        }
    }

    private int[] getRecordFirstAppearRow(BIDataRow dataRow) {
        int dimkeyNum = this.destKeyExceptParent.size();
        int[] firstRowInDs = new int[dimkeyNum];
        if (dimkeyNum > 0) {
            if (dimkeyNum >= 1) {
                firstRowInDs[dimkeyNum - 1] = dataRow.getRowNum();
            }
            ArrayList<Object> keyData = new ArrayList<Object>();
            for (int i = 0; i < dimkeyNum - 1; ++i) {
                Integer destCol = this.destKeyExceptParent.get(i);
                Object val = dataRow.getValue(this.statInfo.dest2srcColMap.get(destCol));
                keyData.add(val);
                DataKey datakey = new DataKey(new ArrayList<Object>(keyData));
                Integer firstRow = this.dimkeyFirstAppearRowMap.get(datakey);
                if (firstRow == null) {
                    firstRow = dataRow.getRowNum();
                    this.dimkeyFirstAppearRowMap.put(datakey, firstRow);
                }
                firstRowInDs[i] = firstRow;
            }
        }
        return firstRowInDs;
    }

    private void doSort(List<RecordBuffer> rdBuf) {
        Collections.sort(rdBuf, new Comparator<RecordBuffer>(){

            @Override
            public int compare(RecordBuffer o1, RecordBuffer o2) {
                int[] frow1 = o1.getFirstRowInDs();
                int[] frow2 = o2.getFirstRowInDs();
                for (int i = 0; i < frow1.length; ++i) {
                    int v = frow1[i] - frow2[i];
                    if (v == 0) continue;
                    return v;
                }
                return 0;
            }
        });
    }

    private void initDevice() {
        this.devices = new ArrayList<FieldStatDevice>();
        List columns = this.statInfo.metadata.getColumns();
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        for (int i = 0; i < columns.size(); ++i) {
            FieldType fType;
            Column column = (Column)columns.get(i);
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            Integer srcIdx = this.statInfo.dest2srcColMap.get(i);
            if (srcIdx == null) {
                srcIdx = this.statInfo.sys_timekey_index;
            }
            if ((fType = info.getFieldType()) == FieldType.MEASURE) {
                TimeGranularity dsTimegranularity;
                FieldStatDevice device = this.createMsDevice(info.getAggregation(), srcIdx, column.getIndex(), (Column<BIDataSetFieldInfo>)metadata.getColumn(srcIdx.intValue()));
                BIDataSetFieldInfo orgInfo = (BIDataSetFieldInfo)metadata.find(info.getName()).getInfo();
                if (orgInfo.hasRefDim()) {
                    Iterator<String> itor = orgInfo.refDimIterator();
                    ArrayList<Integer> refDimKeyColIdx = new ArrayList<Integer>();
                    while (itor.hasNext()) {
                        String colName = itor.next();
                        if (colName.equals("*")) {
                            refDimKeyColIdx.add(-1);
                            continue;
                        }
                        Column col = metadata.find(colName);
                        if (col == null || !((BIDataSetFieldInfo)col.getInfo()).isKeyField()) continue;
                        Integer v = col.getIndex();
                        refDimKeyColIdx.add(v);
                    }
                    device.setRefDimKeyColIdx(refDimKeyColIdx);
                }
                if (this.statInfo.isTimeWasAggr && info.getTimegranularity() != null && (dsTimegranularity = ((BIDataSetFieldInfo)metadata.getColumn(this.statInfo.sys_timekey_index).getInfo()).getTimegranularity()).value() > info.getTimegranularity().value()) {
                    device.setMsTimeGranularity(info.getTimegranularity());
                }
                this.devices.add(device);
                continue;
            }
            if (fType == FieldType.DESCRIPTION) continue;
            boolean isKeyDim = this.destKeyExceptParent.contains(column.getIndex());
            FieldStatDevice device = this.createDimDevice(isKeyDim, srcIdx, column.getIndex());
            this.devices.add(device);
        }
    }

    private FieldStatDevice createDimDevice(boolean isKeyDim, int srcIndex, int destIndex) {
        FieldStatDevice device = isKeyDim ? new DimKeyFieldStatDevice(srcIndex, destIndex) : new DimNameFieldStatDevice(srcIndex, destIndex, this.statInfo.sys_timekey_index);
        device.setDataSet(this.dataset);
        return device;
    }

    private FieldStatDevice createMsDevice(AggregationType aggrType, int srcIndex, int destIndex, Column<BIDataSetFieldInfo> column) {
        FieldStatDevice device;
        ApplyType applyType = ((BIDataSetFieldInfo)column.getInfo()).getApplyType();
        if (this.statInfo.isTimeWasAggr) {
            if (applyType == ApplyType.PERIOD_OPENNING_BLANCE) {
                return new TimeOpenblanceStatDevice(srcIndex, destIndex, this.statInfo.sys_timekey_index);
            }
            if (applyType == ApplyType.TIMEPOINT || applyType == ApplyType.TOTAL || applyType == ApplyType.PERIOD_CLOSING_BLANCE) {
                return new TimePointStatDevice(srcIndex, destIndex, this.statInfo.sys_timekey_index);
            }
        }
        int dataType = column.getDataType();
        int[] dimColIdx = this.convert(this.statInfo.parentKeyColIdx);
        int[] parentColIdx = this.convert(this.statInfo.parentColIdx);
        switch (aggrType) {
            case AVG: {
                if (dataType == DataType.INTEGER.value()) {
                    device = new IntAvgStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                    break;
                }
                if (dataType == DataType.DOUBLE.value()) {
                    device = new DoubleAvgStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                    break;
                }
                if (dataType == 10) {
                    device = new BigDecimalAvgStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                    break;
                }
                device = new DummyStatDevice();
                break;
            }
            case COUNT: {
                device = new CountStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                break;
            }
            case MAX: {
                device = new MaxStatDevice(srcIndex, destIndex, dataType, dimColIdx, parentColIdx);
                break;
            }
            case MIN: {
                device = new MinStatDevice(srcIndex, destIndex, dataType, dimColIdx, parentColIdx);
                break;
            }
            case SUM: {
                if (dataType == DataType.INTEGER.value()) {
                    device = new IntSumStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                    break;
                }
                if (dataType == DataType.DOUBLE.value()) {
                    device = new DoubleSumStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                    break;
                }
                if (dataType == 10) {
                    device = new BigDecimalSumStatDevice(srcIndex, destIndex, dimColIdx, parentColIdx);
                    break;
                }
                device = new DummyStatDevice();
                break;
            }
            default: {
                device = new DummyStatDevice();
            }
        }
        device.setSrcKeyColIdx(this.convert(this.statInfo.srcKeyColIdx));
        device.setDataSet(this.dataset);
        return device;
    }

    private int[] convert(List<Integer> list) {
        if (list == null) {
            return null;
        }
        int[] rs = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            rs[i] = list.get(i);
        }
        return rs;
    }

    private final class DataKey
    implements Comparable<DataKey> {
        private List<Object> data;
        private int hash;

        public DataKey(List<Object> data) {
            this.data = data;
            for (Object d : data) {
                this.hash = this.hash * 31 + (d == null ? 1 : d.hashCode());
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof DataKey) {
                DataKey d2 = (DataKey)obj;
                int c = this.compareTo(d2);
                return c == 0;
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }

        @Override
        public int compareTo(DataKey o) {
            int c = this.data.size() - o.data.size();
            if (c != 0) {
                return c;
            }
            c = this.hash - o.hash;
            if (c != 0) {
                return c;
            }
            for (int i = 0; i < this.data.size(); ++i) {
                Object v2;
                Object v1 = this.data.get(i);
                c = com.jiuqi.bi.syntax.DataType.compareObject((Object)v1, (Object)(v2 = o.data.get(i)));
                if (c == 0) continue;
                return c;
            }
            return 0;
        }
    }
}

