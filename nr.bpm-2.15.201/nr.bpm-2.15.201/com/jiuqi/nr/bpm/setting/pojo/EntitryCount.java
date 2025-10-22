/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

public class EntitryCount {
    private String formSchemeKey;
    private String period;
    private String settingId;
    private Boolean defaultWorkflow;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getSettingId() {
        return this.settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public Boolean getDefaultWorkflow() {
        return this.defaultWorkflow;
    }

    public void setDefaultWorkflow(Boolean defaultWorkflow) {
        this.defaultWorkflow = defaultWorkflow;
    }
}

