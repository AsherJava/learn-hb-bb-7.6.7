/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

import com.jiuqi.nr.period.common.rest.PeriodDataObject;
import java.util.List;

public class PeriodPage {
    List<PeriodDataObject> list;
    int page;
    int pageCount;

    public List<PeriodDataObject> getList() {
        return this.list;
    }

    public void setList(List<PeriodDataObject> list) {
        this.list = list;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}

