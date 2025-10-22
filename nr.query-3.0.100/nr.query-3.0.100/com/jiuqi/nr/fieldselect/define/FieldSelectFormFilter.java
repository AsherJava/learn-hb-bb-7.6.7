/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fieldselect.define;

import java.util.Map;

public class FieldSelectFormFilter {
    private String schemeKey;
    private String displayFMDM;
    private Map<String, String> filterCondition;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getDisplayFMDM() {
        return this.displayFMDM;
    }

    public void setDisplayFMDM(String displayFMDM) {
        this.displayFMDM = displayFMDM;
    }

    public Map<String, String> getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(Map<String, String> filterCondition) {
        this.filterCondition = filterCondition;
    }
}

