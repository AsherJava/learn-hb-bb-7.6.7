/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.period.select.page.Page;
import java.util.List;

public class RangeData {
    private List<Page> data;
    private List<String> selectList;

    public List<Page> getData() {
        return this.data;
    }

    public void setData(List<Page> data) {
        this.data = data;
    }

    public List<String> getSelectList() {
        return this.selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }
}

