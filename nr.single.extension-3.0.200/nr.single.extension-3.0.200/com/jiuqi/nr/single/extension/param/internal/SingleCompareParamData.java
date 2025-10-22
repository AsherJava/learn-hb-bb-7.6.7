/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.extension.param.internal;

import com.jiuqi.nr.single.extension.param.internal.CompareMapFieldData;
import java.util.ArrayList;
import java.util.List;

public class SingleCompareParamData {
    List<CompareMapFieldData> mapFields;

    public List<CompareMapFieldData> getMapFields() {
        if (this.mapFields == null) {
            this.mapFields = new ArrayList<CompareMapFieldData>();
        }
        return this.mapFields;
    }

    public void setMapFields(List<CompareMapFieldData> mapFields) {
        this.mapFields = mapFields;
    }
}

