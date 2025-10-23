/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.data.dto;

import java.util.List;

public class IConfirmParamVO {
    private String dwSchemeKey;
    private List<String> unitKeys;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }

    public List<String> getUnitKeys() {
        return this.unitKeys;
    }

    public void setUnitKeys(List<String> unitKeys) {
        this.unitKeys = unitKeys;
    }
}

