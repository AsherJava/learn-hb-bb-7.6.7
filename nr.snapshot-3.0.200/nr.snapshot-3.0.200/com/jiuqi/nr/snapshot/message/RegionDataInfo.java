/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import java.util.ArrayList;
import java.util.List;

public class RegionDataInfo {
    private String regionKey;
    private List<IMetaData> metaDatas = new ArrayList<IMetaData>();
    private List<IRowData> rowDatas = new ArrayList<IRowData>();

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<IMetaData> getMetaDatas() {
        return this.metaDatas;
    }

    public void setMetaDatas(List<IMetaData> metaDatas) {
        this.metaDatas = metaDatas;
    }

    public List<IRowData> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<IRowData> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

