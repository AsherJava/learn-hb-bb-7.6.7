/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.CustomPeriodData
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.SelectStructure;
import java.util.List;
import java.util.Objects;

public class AssTaskToFormSchemes {
    private String assTaskKey;
    private List<CustomPeriodData> customPeriodDatas;
    private List<SelectStructure> assFormSchemes;

    public List<CustomPeriodData> getCustomPeriodDatas() {
        return this.customPeriodDatas;
    }

    public void setCustomPeriodDatas(List<CustomPeriodData> customPeriodDatas) {
        this.customPeriodDatas = customPeriodDatas;
    }

    public String getAssTaskKey() {
        return this.assTaskKey;
    }

    public void setAssTaskKey(String assTaskKey) {
        this.assTaskKey = assTaskKey;
    }

    public List<SelectStructure> getAssFormSchemes() {
        return this.assFormSchemes;
    }

    public void setAssFormSchemes(List<SelectStructure> assFormSchemes) {
        this.assFormSchemes = assFormSchemes;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AssTaskToFormSchemes that = (AssTaskToFormSchemes)o;
        return Objects.equals(this.assTaskKey, that.assTaskKey);
    }

    public int hashCode() {
        return Objects.hash(this.assTaskKey);
    }
}

