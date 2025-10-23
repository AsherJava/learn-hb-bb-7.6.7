/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.param;

import java.util.Set;

public class FindGroupPathPM {
    private String dataSchemeKey;
    private Set<String> parentKeys;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public Set<String> getParentKeys() {
        return this.parentKeys;
    }

    public void setParentKeys(Set<String> parentKeys) {
        this.parentKeys = parentKeys;
    }
}

