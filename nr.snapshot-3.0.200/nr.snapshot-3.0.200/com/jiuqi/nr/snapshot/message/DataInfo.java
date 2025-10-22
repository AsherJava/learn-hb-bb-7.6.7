/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.message.FixRegionData;
import com.jiuqi.nr.snapshot.message.FloatRegionData;
import java.util.ArrayList;
import java.util.List;

public class DataInfo {
    private FixRegionData fixData;
    private List<FloatRegionData> floatDatas = new ArrayList<FloatRegionData>();

    public FixRegionData getFixData() {
        return this.fixData;
    }

    public void setFixData(FixRegionData fixData) {
        this.fixData = fixData;
    }

    public List<FloatRegionData> getFloatDatas() {
        return this.floatDatas;
    }

    public void setFloatDatas(List<FloatRegionData> floatDatas) {
        this.floatDatas = floatDatas;
    }
}

