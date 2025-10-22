/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IRowData
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.datacrud.IRowData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAndReplaceDataSet {
    private List<List<Object>> data = new ArrayList<List<Object>>();
    private List<List<Object>> dataFormat = new ArrayList<List<Object>>();
    private Map<String, List<String>> cells = new HashMap<String, List<String>>();
    private List<IRowData> iRowDatas;

    public List<List<Object>> getData() {
        return this.data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public List<List<Object>> getDataFormat() {
        return this.dataFormat;
    }

    public void setDataFormat(List<List<Object>> dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Map<String, List<String>> getCells() {
        return this.cells;
    }

    public void setCells(Map<String, List<String>> cells) {
        this.cells = cells;
    }

    public List<IRowData> getiRowDatas() {
        return this.iRowDatas;
    }

    public void setiRowDatas(List<IRowData> iRowDatas) {
        this.iRowDatas = iRowDatas;
    }
}

