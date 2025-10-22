/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news2.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveReturn {
    @JsonProperty(value="NewsId")
    private String newsId;

    public SaveReturn() {
    }

    public SaveReturn(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}

