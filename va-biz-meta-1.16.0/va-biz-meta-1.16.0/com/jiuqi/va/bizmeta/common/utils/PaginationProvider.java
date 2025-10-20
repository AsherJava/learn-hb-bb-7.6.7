/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.common.utils;

import java.util.ArrayList;
import java.util.List;

public class PaginationProvider<T> {
    private int offSet;
    private int limit;
    private List<T> allDatas;

    public PaginationProvider(int offSet, int limit, List<T> allDatas) {
        this.offSet = offSet;
        this.limit = limit;
        this.allDatas = allDatas;
    }

    public List<T> getPaginationDatas() {
        int endRows = this.allDatas.size();
        if (this.offSet + this.limit <= this.allDatas.size()) {
            endRows = this.offSet + this.limit;
        }
        ArrayList<T> paginationList = new ArrayList<T>();
        for (int curRow = this.offSet; curRow < endRows; ++curRow) {
            paginationList.add(this.allDatas.get(curRow));
        }
        return paginationList;
    }
}

