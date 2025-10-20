/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.base.intf;

import com.jiuqi.bde.base.intf.FloatFieldDefineValPojo;
import java.util.LinkedHashMap;
import java.util.Optional;

public class FloatColDataPojo {
    private Integer fieldDefineLength;
    private Integer zbSettingLength;
    private LinkedHashMap<String, FloatFieldDefineValPojo> colInfoMap = new LinkedHashMap();

    public Integer getFieldDefineLength() {
        return this.fieldDefineLength;
    }

    public void setFieldDefineLength(Integer fieldDefineLength) {
        this.fieldDefineLength = fieldDefineLength;
    }

    public Integer getZbSettingLength() {
        return this.zbSettingLength;
    }

    public void setZbSettingLength(Integer zbSettingLength) {
        this.zbSettingLength = zbSettingLength;
    }

    public LinkedHashMap<String, FloatFieldDefineValPojo> getColInfoMap() {
        return this.colInfoMap;
    }

    public boolean containsfieldDefine(String fieldDefine) {
        return this.colInfoMap.containsKey(fieldDefine);
    }

    public void putColInfo(String fieldDefine, int fieldDefineIdx, String fetchSetting, int fetchSettingIdx) {
        if (!this.containsfieldDefine(fieldDefine)) {
            this.colInfoMap.put(fieldDefine, new FloatFieldDefineValPojo(fieldDefineIdx));
        }
        this.colInfoMap.get(fieldDefine).getFieldSettingMap().put(fetchSetting, fetchSettingIdx);
    }

    public FloatFieldDefineValPojo getFieldDefineVal(String fieldDefine) {
        return Optional.of(this.colInfoMap.get(fieldDefine)).get();
    }

    public int getFieldDefineIdx(String fieldDefine) {
        return this.getFieldDefineVal(fieldDefine).getFieldDefineIdx();
    }

    public int getFetchSettingIdx(String fieldDefine, String fetchSetting) {
        return this.getFieldDefineVal(fieldDefine).getFetchSettingIdx(fetchSetting);
    }

    public boolean isEmpty() {
        return this.colInfoMap == null || this.colInfoMap.isEmpty();
    }

    public String toString() {
        return "FloatColDataPojo [fieldDefineLength=" + this.fieldDefineLength + ", zbSettingLength=" + this.zbSettingLength + ", colInfoMap=" + this.colInfoMap + "]";
    }
}

