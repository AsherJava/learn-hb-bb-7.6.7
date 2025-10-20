/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class FormCopyAttSchemeVO {
    private String schemeKey;
    private String srcSchemeKey;
    private String schemeTitle;
    private String srcSchemeTitle;

    public FormCopyAttSchemeVO(String schemeKey, String srcSchemeKey, String schemeTitle, String srcSchemeTitle) {
        this.schemeKey = schemeKey;
        this.srcSchemeKey = srcSchemeKey;
        this.schemeTitle = schemeTitle;
        this.srcSchemeTitle = srcSchemeTitle;
    }

    public FormCopyAttSchemeVO(String schemeKey, String srcSchemeKey) {
        this.schemeKey = schemeKey;
        this.srcSchemeKey = srcSchemeKey;
    }

    public String getKeys() {
        return this.srcSchemeKey + ";" + this.schemeKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getSrcSchemeKey() {
        return this.srcSchemeKey;
    }

    public void setSrcSchemeKey(String srcSchemeKey) {
        this.srcSchemeKey = srcSchemeKey;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public String getSrcSchemeTitle() {
        return this.srcSchemeTitle;
    }

    public void setSrcSchemeTitle(String srcSchemeTitle) {
        this.srcSchemeTitle = srcSchemeTitle;
    }
}

