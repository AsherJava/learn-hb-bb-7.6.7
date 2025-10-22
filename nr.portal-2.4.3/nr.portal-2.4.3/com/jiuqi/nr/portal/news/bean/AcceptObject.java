/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class AcceptObject
implements Serializable {
    private static final long serialVersionUID = -9086222242088783843L;
    @JsonProperty(value="newsId")
    @JsonInclude
    private String newsId;

    public String getNews() {
        return this.newsId;
    }

    public void setNews(String news) {
        this.newsId = news;
    }
}

