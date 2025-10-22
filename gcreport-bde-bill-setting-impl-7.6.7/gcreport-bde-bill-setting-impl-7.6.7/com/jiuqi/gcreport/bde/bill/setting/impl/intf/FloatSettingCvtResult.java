/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.intf;

import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
import java.util.ArrayList;
import java.util.List;

public class FloatSettingCvtResult {
    private List<FetchFloatSettingDesEO> floatSettings = new ArrayList<FetchFloatSettingDesEO>();
    private List<List<Object>> delFixSettingValues = new ArrayList<List<Object>>();

    public List<FetchFloatSettingDesEO> getFloatSettings() {
        return this.floatSettings;
    }

    public void addFloatSettings(FetchFloatSettingDesEO floatSetting) {
        this.floatSettings.add(floatSetting);
    }

    public List<List<Object>> getDelFixSettingValues() {
        return this.delFixSettingValues;
    }

    public void addDelFixSettingValues(List<Object> delFixSettingValue) {
        this.delFixSettingValues.add(delFixSettingValue);
    }

    public void addResult(FloatSettingCvtResult res) {
        this.floatSettings.addAll(res.getFloatSettings());
        this.delFixSettingValues.addAll(res.getDelFixSettingValues());
    }

    public String toString() {
        return "FloatSettingCvtResult [floatSettings=" + this.floatSettings + ", delFixSettingValues=" + this.delFixSettingValues + "]";
    }
}

