/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.base.intf;

import java.util.LinkedHashMap;
import java.util.Optional;

public class FloatFieldDefineValPojo {
    private int fieldDefineIdx;
    private LinkedHashMap<String, Integer> fieldSettingMap;

    public FloatFieldDefineValPojo(int fieldDefineIdx) {
        this.fieldDefineIdx = fieldDefineIdx;
        this.fieldSettingMap = new LinkedHashMap();
    }

    public int getFieldDefineIdx() {
        return this.fieldDefineIdx;
    }

    public void setFieldDefineIdx(int fieldDefineIdx) {
        this.fieldDefineIdx = fieldDefineIdx;
    }

    public LinkedHashMap<String, Integer> getFieldSettingMap() {
        return this.fieldSettingMap;
    }

    public void setFieldSettingMap(LinkedHashMap<String, Integer> fieldSettingMap) {
        this.fieldSettingMap = fieldSettingMap;
    }

    public int getFetchSettingIdx(String fetchSetting) {
        return Optional.of(this.fieldSettingMap.get(fetchSetting)).get();
    }

    public String toString() {
        return "FieldDefineValPojo [fieldDefineIdx=" + this.fieldDefineIdx + ", fieldSettingMap=" + this.fieldSettingMap + "]";
    }
}

