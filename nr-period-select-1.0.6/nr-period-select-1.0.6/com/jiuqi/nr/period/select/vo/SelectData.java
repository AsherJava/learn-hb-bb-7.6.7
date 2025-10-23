/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.period.select.vo.SelectAdjustData;
import com.jiuqi.nr.period.select.vo.SelectPeriodData;
import java.util.ArrayList;
import java.util.List;

public class SelectData {
    private List<SelectPeriodData> periodDataList;
    private SelectAdjustData adjustData;

    public void addPeriodData(SelectPeriodData selectPeriodData) {
        if (null == this.periodDataList) {
            this.periodDataList = new ArrayList<SelectPeriodData>();
        }
        this.periodDataList.add(selectPeriodData);
    }

    public List<SelectPeriodData> getPeriodDataList() {
        return this.periodDataList;
    }

    public void setPeriodDataList(List<SelectPeriodData> periodDataList) {
        this.periodDataList = periodDataList;
    }

    public SelectAdjustData getAdjustData() {
        return this.adjustData;
    }

    public void setAdjustData(SelectAdjustData adjustData) {
        this.adjustData = adjustData;
    }
}

