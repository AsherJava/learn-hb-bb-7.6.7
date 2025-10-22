/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import com.jiuqi.nr.datacheck.fmlcheck.FmlCheckConfig;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CheckResultContext
implements Serializable {
    private static final long serialVersionUID = -3064113657927100912L;
    private FmlCheckConfig fmlCheckConfig;
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private List<String> orgCode;
    private Map<String, String> dimSet;

    public FmlCheckConfig getFmlCheckConfig() {
        return this.fmlCheckConfig;
    }

    public void setFmlCheckConfig(FmlCheckConfig fmlCheckConfig) {
        this.fmlCheckConfig = fmlCheckConfig;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(List<String> orgCode) {
        this.orgCode = orgCode;
    }

    public Map<String, String> getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(Map<String, String> dimSet) {
        this.dimSet = dimSet;
    }
}

