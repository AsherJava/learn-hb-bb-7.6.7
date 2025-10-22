/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.jtable.params.base.FormulaData;

public class SearchFormulaData
extends FormulaData {
    private String beforeText;
    private String highlightText;
    private String afterText;
    private String afterDivideText;

    public String getBeforeText() {
        return this.beforeText;
    }

    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    public String getHighlightText() {
        return this.highlightText;
    }

    public void setHighlightText(String highlightText) {
        this.highlightText = highlightText;
    }

    public String getAfterText() {
        return this.afterText;
    }

    public void setAfterText(String afterText) {
        this.afterText = afterText;
    }

    public String getAfterDivideText() {
        return this.afterDivideText;
    }

    public void setAfterDivideText(String afterDivideText) {
        this.afterDivideText = afterDivideText;
    }
}

