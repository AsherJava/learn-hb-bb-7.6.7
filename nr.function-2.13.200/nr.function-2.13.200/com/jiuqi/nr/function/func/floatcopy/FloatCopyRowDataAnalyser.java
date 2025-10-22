/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyRowData;
import com.jiuqi.nr.function.func.floatcopy.FloatCopySumedRowData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FloatCopyRowDataAnalyser {
    private Map<FloatCopyRowData, FloatCopySumedRowData> sumedDataMap;
    private Map<FloatCopyRowData, FloatCopyRowData> updatedDataMap;
    private Map<FloatCopyRowData, List<FloatCopyRowData>> updatedDataMapNV1;
    private List<FloatCopyRowData> unMatchedDestDatas = new ArrayList<FloatCopyRowData>();
    private List<FloatCopyRowData> unMatchedSrcDatas;
    private String copyMode;

    public List<FloatCopyRowData> getUnMatchedSrcDatas() {
        return this.unMatchedSrcDatas;
    }

    public FloatCopyRowDataAnalyser(List<FloatCopyRowData> srcDatas, List<FloatCopyRowData> destDatas, String copyMode) {
        this.unMatchedDestDatas.addAll(destDatas);
        this.unMatchedSrcDatas = new ArrayList<FloatCopyRowData>();
        this.copyMode = copyMode;
        Comparator<FloatCopyRowData> comparator = new Comparator<FloatCopyRowData>(){

            @Override
            public int compare(FloatCopyRowData arg0, FloatCopyRowData arg1) {
                return arg0.getRowKey().compareTo(arg1.getRowKey());
            }
        };
        this.sumedDataMap = new TreeMap<FloatCopyRowData, FloatCopySumedRowData>(comparator);
        this.updatedDataMap = new TreeMap<FloatCopyRowData, FloatCopyRowData>(comparator);
        this.updatedDataMapNV1 = new HashMap<FloatCopyRowData, List<FloatCopyRowData>>();
        if (copyMode.equals("5")) {
            this.analyseNV1(srcDatas, destDatas);
        } else {
            this.analyse(srcDatas, destDatas);
        }
    }

    public void analyse(List<FloatCopyRowData> srcDatas, List<FloatCopyRowData> destDatas) {
        for (FloatCopyRowData sData : srcDatas) {
            FloatCopyRowData matchedData = this.getSameKeyDestRow(sData, destDatas);
            if (matchedData != null) {
                this.unMatchedDestDatas.remove(matchedData);
                this.updatedDataMap.put(matchedData, sData);
                List<FloatCopyRowData> matchedDatas = this.updatedDataMapNV1.get(sData);
                if (matchedDatas == null) {
                    matchedDatas = new ArrayList<FloatCopyRowData>();
                    this.updatedDataMapNV1.put(sData, matchedDatas);
                }
                matchedDatas.add(matchedData);
                this.createSummedMap(sData, matchedData);
                continue;
            }
            this.unMatchedSrcDatas.add(sData);
        }
    }

    public void analyseNV1(List<FloatCopyRowData> srcDatas, List<FloatCopyRowData> destDatas) {
        for (FloatCopyRowData sData : srcDatas) {
            List<FloatCopyRowData> matchedDatas = this.getSameKeyDestRows(sData, destDatas);
            if (matchedDatas.size() > 0) {
                for (FloatCopyRowData matchedData : matchedDatas) {
                    this.unMatchedDestDatas.remove(matchedData);
                    this.updatedDataMapNV1.put(sData, matchedDatas);
                }
                continue;
            }
            this.unMatchedSrcDatas.add(sData);
        }
    }

    public void createSummedMap(FloatCopyRowData sData, FloatCopyRowData matchedData) {
        if (this.sumedDataMap.containsKey(matchedData)) {
            FloatCopyRowData value = this.sumedDataMap.get(matchedData);
            for (int i = 0; i < value.size(); ++i) {
                value.setValue(i, matchedData.getValue(i));
            }
        } else {
            this.sumedDataMap.put(matchedData, new FloatCopySumedRowData(sData));
        }
    }

    public FloatCopyRowData getSameKeyDestRow(FloatCopyRowData toCheck, List<FloatCopyRowData> datas) {
        if (datas == null || datas.size() <= 0) {
            return null;
        }
        for (FloatCopyRowData rowData : datas) {
            if (!toCheck.getKeyColumnValue().equals(rowData.getKeyColumnValue())) continue;
            for (int i = 0; i < toCheck.size(); ++i) {
                AbstractData toCheckData = toCheck.getValue(i);
                AbstractData tempData = rowData.getValue(i);
                if (toCheckData.dataType != tempData.dataType) continue;
                return rowData;
            }
        }
        return null;
    }

    private List<FloatCopyRowData> getSameKeyDestRows(FloatCopyRowData toCheck, List<FloatCopyRowData> datas) {
        if (datas == null || datas.size() <= 0) {
            return null;
        }
        ArrayList<FloatCopyRowData> matched = new ArrayList<FloatCopyRowData>();
        for (FloatCopyRowData rowData : datas) {
            if (!toCheck.getKeyColumnValue().equals(rowData.getKeyColumnValue())) continue;
            for (int i = 0; i < toCheck.size(); ++i) {
                AbstractData toCheckData = toCheck.getValue(i);
                AbstractData tempData = rowData.getValue(i);
                if (toCheckData.dataType != tempData.dataType) continue;
                matched.add(rowData);
            }
        }
        return matched;
    }

    public List<FloatCopyRowData> getUnMatchedDestDatas() {
        return this.unMatchedDestDatas;
    }

    public Map<FloatCopyRowData, FloatCopySumedRowData> getSumedDataMap() {
        return this.sumedDataMap;
    }

    public Map<FloatCopyRowData, FloatCopyRowData> getUpdatedDataMap() {
        return this.updatedDataMap;
    }

    public Map<FloatCopyRowData, List<FloatCopyRowData>> getUpdatedDataMapNV1() {
        return this.updatedDataMapNV1;
    }
}

