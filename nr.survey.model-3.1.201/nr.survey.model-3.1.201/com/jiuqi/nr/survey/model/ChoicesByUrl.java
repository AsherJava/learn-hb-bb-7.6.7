/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

public class ChoicesByUrl {
    private String url;
    private String path;
    private String valueName;
    private String titleName;
    private boolean allowEmptyResponse;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValueName() {
        return this.valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getTitleName() {
        return this.titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public boolean isAllowEmptyResponse() {
        return this.allowEmptyResponse;
    }

    public void setAllowEmptyResponse(boolean allowEmptyResponse) {
        this.allowEmptyResponse = allowEmptyResponse;
    }
}

