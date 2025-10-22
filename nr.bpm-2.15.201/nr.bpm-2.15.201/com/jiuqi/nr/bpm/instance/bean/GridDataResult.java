/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.instance.bean;

import com.jiuqi.nr.bpm.instance.bean.GridDataItem;
import java.util.List;

public class GridDataResult {
    private List<GridDataItem> gridDatas;
    private int count;

    public List<GridDataItem> getGridDatas() {
        return this.gridDatas;
    }

    public void setGridDatas(List<GridDataItem> gridDatas) {
        this.gridDatas = gridDatas;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

