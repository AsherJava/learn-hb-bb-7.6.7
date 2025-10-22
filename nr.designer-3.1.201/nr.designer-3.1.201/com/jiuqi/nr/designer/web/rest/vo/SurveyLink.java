/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class SurveyLink {
    private String id;
    private String expression;
    private String tableCode;
    private String fieldCode;
    private String fieldTitle;

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

