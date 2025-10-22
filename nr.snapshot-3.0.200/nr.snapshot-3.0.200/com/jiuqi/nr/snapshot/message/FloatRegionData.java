/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.message.FieldData;
import java.util.List;

public class FloatRegionData {
    private String regionKey;
    private String regionName;
    private List<String> naturalKeys;
    private List<String> naturalCodes;
    private boolean allowDuplicateKey = false;
    private List<List<FieldData>> floatDatass;

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

    public List<String> getNaturalKeys() {
        return this.naturalKeys;
    }

    public void setNaturalKeys(List<String> naturalKeys) {
        this.naturalKeys = naturalKeys;
    }

    public List<String> getNaturalCodes() {
        return this.naturalCodes;
    }

    public void setNaturalCodes(List<String> naturalCodes) {
        this.naturalCodes = naturalCodes;
    }

    public boolean isAllowDuplicateKey() {
        return this.allowDuplicateKey;
    }

    public void setAllowDuplicateKey(boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey;
    }

    public List<List<FieldData>> getFloatDatass() {
        return this.floatDatass;
    }

    public void setFloatDatass(List<List<FieldData>> floatDatass) {
        this.floatDatass = floatDatass;
    }
}

