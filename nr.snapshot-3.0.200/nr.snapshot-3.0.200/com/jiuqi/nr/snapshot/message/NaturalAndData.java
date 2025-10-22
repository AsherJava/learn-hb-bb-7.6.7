/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.snapshot.message.FieldData;
import java.util.List;

public class NaturalAndData {
    private List<AbstractData> naturalDatas;
    private List<FieldData> floatDatas;

    public List<AbstractData> getNaturalDatas() {
        return this.naturalDatas;
    }

    public void setNaturalDatas(List<AbstractData> naturalDatas) {
        this.naturalDatas = naturalDatas;
    }

    public List<FieldData> getFloatDatas() {
        return this.floatDatas;
    }

    public void setFloatDatas(List<FieldData> floatDatas) {
        this.floatDatas = floatDatas;
    }
}

