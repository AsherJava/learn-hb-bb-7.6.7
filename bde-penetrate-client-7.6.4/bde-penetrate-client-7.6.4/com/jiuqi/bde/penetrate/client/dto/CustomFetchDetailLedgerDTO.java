/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.client.dto;

import java.util.Map;

public class CustomFetchDetailLedgerDTO {
    private String queryId;
    private String queryCode;
    private String queryTitle;
    private Map<String, Object> queryCondi;

    public String getQueryId() {
        return this.queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getQueryCode() {
        return this.queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public String getQueryTitle() {
        return this.queryTitle;
    }

    public void setQueryTitle(String queryTitle) {
        this.queryTitle = queryTitle;
    }

    public Map<String, Object> getQueryCondi() {
        return this.queryCondi;
    }

    public void setQueryCondi(Map<String, Object> queryCondi) {
        this.queryCondi = queryCondi;
    }

    public String toString() {
        return "CustomFetchDetailLedgerDTO{queryId='" + this.queryId + '\'' + ", queryCode='" + this.queryCode + '\'' + ", queryTitle='" + this.queryTitle + '\'' + ", queryCondi=" + this.queryCondi + '}';
    }
}

