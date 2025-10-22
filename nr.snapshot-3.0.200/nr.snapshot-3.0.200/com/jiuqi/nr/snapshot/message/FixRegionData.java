/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.message.FieldData;
import java.util.List;

public class FixRegionData {
    private String regionKey;
    private String regionName;
    private List<FieldData> fieldData;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<FieldData> getFixDatas() {
        return this.fieldData;
    }

    public void setFixDatas(List<FieldData> fieldData) {
        this.fieldData = fieldData;
    }
}

