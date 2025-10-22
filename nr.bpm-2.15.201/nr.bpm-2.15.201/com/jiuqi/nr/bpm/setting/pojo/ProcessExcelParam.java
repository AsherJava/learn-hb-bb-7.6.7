/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class ProcessExcelParam {
    private String formSchemeKey;
    private Map<String, DimensionValue> dimensionSetMap;
    private List<ProcessTrackExcelInfo> list;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSetMap() {
        return this.dimensionSetMap;
    }

    public void setDimensionSetMap(Map<String, DimensionValue> dimensionSetMap) {
        this.dimensionSetMap = dimensionSetMap;
    }

    public List<ProcessTrackExcelInfo> getList() {
        return this.list;
    }

    public void setList(List<ProcessTrackExcelInfo> list) {
        this.list = list;
    }
}

