/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import java.util.List;

public class ResponseSurveyTreeLocateVO {
    private List<String> paths;
    private String zbKey;
    private String zbCode;
    private String zbTitle;

    public List<String> getPaths() {
        return this.paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String getZbKey() {
        return this.zbKey;
    }

    public void setZbKey(String zbKey) {
        this.zbKey = zbKey;
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
}

