/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.env.formulaconversion;

import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo;
import java.util.HashMap;
import java.util.Map;

public class ConversionFormInfo {
    private Map<String, ConversionFieldInfo> fieldInfoMap = new HashMap<String, ConversionFieldInfo>();

    public Map<String, ConversionFieldInfo> getFieldInfoMap() {
        return this.fieldInfoMap;
    }

    public void setFieldInfoMap(Map<String, ConversionFieldInfo> fieldInfoMap) {
        this.fieldInfoMap = fieldInfoMap;
    }
}

