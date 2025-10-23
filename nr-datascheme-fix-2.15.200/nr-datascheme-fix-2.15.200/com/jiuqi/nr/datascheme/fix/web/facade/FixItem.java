/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

public class FixItem {
    protected String dataSchemeKey;
    protected String dataTableKey;
    protected String dataTableCode;
    protected String dataTableTitle;
    protected String dataSchemeCode;
    protected String dataSchemeTitle;
    protected boolean isScheme;

    public boolean isScheme() {
        return this.isScheme;
    }

    public void setScheme(boolean scheme) {
        this.isScheme = scheme;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }

    public String getDataTableTitle() {
        return this.dataTableTitle;
    }

    public void setDataTableTitle(String dataTableTitle) {
        this.dataTableTitle = dataTableTitle;
    }
}

