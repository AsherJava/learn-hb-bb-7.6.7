/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.checkquery.vo;

import java.util.List;

public class FinancialCheckQueryColumnVO {
    private String value;
    private String title;
    private List<FinancialCheckQueryColumnVO> children;
    private String width;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FinancialCheckQueryColumnVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<FinancialCheckQueryColumnVO> children) {
        this.children = children;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}

