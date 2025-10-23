/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.singlequeryimport.bean.QueryGridCells;
import com.jiuqi.nr.singlequeryimport.bean.QueryGridHeadInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryMergeCells;

public class GridData {
    private QueryGridHeadInfo QueryGridHeadInfo;
    private QueryMergeCells QueryMergeCells;
    private QueryGridCells QueryGridCells;

    public QueryGridHeadInfo getQueryGridHeadInfo() {
        return this.QueryGridHeadInfo;
    }

    public void setQueryGridHeadInfo(QueryGridHeadInfo QueryGridHeadInfo2) {
        this.QueryGridHeadInfo = QueryGridHeadInfo2;
    }

    public QueryMergeCells getQueryMergeCells() {
        return this.QueryMergeCells;
    }

    public void setQueryMergeCells(QueryMergeCells QueryMergeCells2) {
        this.QueryMergeCells = QueryMergeCells2;
    }

    public QueryGridCells getQueryGridCells() {
        return this.QueryGridCells;
    }

    public void setQueryGridCells(QueryGridCells QueryGridCells2) {
        this.QueryGridCells = QueryGridCells2;
    }
}

