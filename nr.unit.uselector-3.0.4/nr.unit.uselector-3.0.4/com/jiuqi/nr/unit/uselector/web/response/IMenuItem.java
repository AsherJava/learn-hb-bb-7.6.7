/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.web.response;

import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;

public class IMenuItem {
    private String keyword;
    private String showText;
    private IFilterCheckValues checkValues;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getShowText() {
        return this.showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public IFilterCheckValues getCheckValues() {
        return this.checkValues;
    }

    public void setCheckValues(IFilterCheckValues checkValues) {
        this.checkValues = checkValues;
    }
}

