/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

public class ExtractCellInfo {
    private String linkKey;
    private boolean readOnly = true;
    private String backgroundColor;
    private boolean systemOptionEnable = true;

    public ExtractCellInfo() {
    }

    public ExtractCellInfo(String linkKey, boolean readOnly) {
        this.linkKey = linkKey;
        this.readOnly = readOnly;
    }

    public ExtractCellInfo(String linkKey, boolean readOnly, String backgroundColor, boolean systemOptionEnable) {
        this.linkKey = linkKey;
        this.readOnly = readOnly;
        this.backgroundColor = backgroundColor;
        this.systemOptionEnable = systemOptionEnable;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isSystemOptionEnable() {
        return this.systemOptionEnable;
    }

    public void setSystemOptionEnable(boolean systemOptionEnable) {
        this.systemOptionEnable = systemOptionEnable;
    }
}

