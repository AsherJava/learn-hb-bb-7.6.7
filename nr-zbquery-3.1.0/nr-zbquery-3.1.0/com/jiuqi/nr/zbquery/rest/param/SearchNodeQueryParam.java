/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.param;

import java.util.List;

public class SearchNodeQueryParam {
    private String filter;
    private List<String> defineKeys;

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<String> getDefineKeys() {
        return this.defineKeys;
    }

    public void setDefineKeys(List<String> defineKeys) {
        this.defineKeys = defineKeys;
    }
}

