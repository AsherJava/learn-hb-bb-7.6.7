/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.office.excel.ExcelException
 *  com.jiuqi.bi.office.excel.GridIterator
 */
package com.jiuqi.bi.quickreport.engine.util;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.ExcelException;
import com.jiuqi.bi.office.excel.GridIterator;
import java.util.List;

@Deprecated
public class QuickGridIterator
implements GridIterator {
    private int currentIndex = -1;
    private List<GridData> gridDatas;
    private List<String> titles;

    public QuickGridIterator(List<GridData> gridDatas, List<String> titles) {
        this.gridDatas = gridDatas;
        this.titles = titles;
    }

    public GridData getGridData() {
        return this.gridDatas.get(this.currentIndex);
    }

    public String getTitle() {
        return this.titles.get(this.currentIndex).replaceAll("[\\[;\\];\\*;\\?;\\\\;:;/]", "");
    }

    public boolean next() throws ExcelException {
        if (null == this.gridDatas) {
            return false;
        }
        ++this.currentIndex;
        return this.currentIndex < this.gridDatas.size();
    }
}

