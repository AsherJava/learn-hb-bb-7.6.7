/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param;

import java.util.List;

public class RealTimeCheckOrOffsetParam {
    private List<String> items;
    private String dataTime;

    public RealTimeCheckOrOffsetParam(List<String> items, String dataTime) {
        this.items = items;
        this.dataTime = dataTime;
    }

    public RealTimeCheckOrOffsetParam() {
    }

    public List<String> getItems() {
        return this.items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}

