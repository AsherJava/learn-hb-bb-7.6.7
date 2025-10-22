/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

import java.util.List;

public class UnitCountQueryParam {
    private String dwSchemeKey;
    private List<String> entityIds;

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }

    public List<String> getEntityIds() {
        return this.entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }
}

