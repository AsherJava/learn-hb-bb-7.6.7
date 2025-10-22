/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import java.util.List;
import java.util.Map;

public class LevelUploadObj {
    private int total;
    private String action;
    private Map<String, Map<String, List<String>>> noOperateGroupOrFormMap;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Map<String, List<String>>> getNoOperateGroupOrFormMap() {
        return this.noOperateGroupOrFormMap;
    }

    public void setNoOperateGroupOrFormMap(Map<String, Map<String, List<String>>> noOperateGroupOrFormMap) {
        this.noOperateGroupOrFormMap = noOperateGroupOrFormMap;
    }
}

