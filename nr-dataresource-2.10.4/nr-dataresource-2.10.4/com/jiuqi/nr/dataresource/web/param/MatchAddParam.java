/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.param;

public class MatchAddParam {
    private String matchValue;
    private boolean exactMatch;
    private String dimKey;
    private String period;
    private String group;
    private String dataSchemeKey;

    public String getMatchValue() {
        return this.matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public boolean isExactMatch() {
        return this.exactMatch;
    }

    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

