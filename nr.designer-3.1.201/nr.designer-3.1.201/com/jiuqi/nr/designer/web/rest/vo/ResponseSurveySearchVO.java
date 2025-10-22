/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class ResponseSurveySearchVO {
    private String key;
    private String title;
    private String code;
    private String zbCode;
    private String zbTitle;
    private boolean disabled;

    public ResponseSurveySearchVO(String key, String title, String code, String zbCode, String zbTitle, boolean disabled) {
        this.key = key;
        this.title = title;
        this.code = code;
        this.zbCode = zbCode;
        this.zbTitle = zbTitle;
        this.disabled = disabled;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}

