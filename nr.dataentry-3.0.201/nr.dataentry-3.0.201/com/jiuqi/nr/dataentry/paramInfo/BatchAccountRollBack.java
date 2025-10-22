/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.io.Serializable;

public class BatchAccountRollBack
implements Serializable {
    private static final long serialVersionUID = -4229614333921225092L;
    private String formKeys;
    private String formSchemeKey;
    private String period;
    private String dwKey;
    private boolean allChildren;
    private boolean allDw;

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

    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public String getDwKey() {
        return this.dwKey;
    }

    public void setDwKey(String dwKey) {
        this.dwKey = dwKey;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public boolean isAllDw() {
        return this.allDw;
    }

    public void setAllDw(boolean allDw) {
        this.allDw = allDw;
    }
}

