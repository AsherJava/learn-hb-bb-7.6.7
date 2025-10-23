/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.param;

import java.util.List;

public class ShowFieldPM {
    private String orgDimKey;
    private List<String> dimKeys;

    public String getOrgDimKey() {
        return this.orgDimKey;
    }

    public void setOrgDimKey(String orgDimKey) {
        this.orgDimKey = orgDimKey;
    }

    public List<String> getDimKeys() {
        return this.dimKeys;
    }

    public void setDimKeys(List<String> dimKeys) {
        this.dimKeys = dimKeys;
    }
}

