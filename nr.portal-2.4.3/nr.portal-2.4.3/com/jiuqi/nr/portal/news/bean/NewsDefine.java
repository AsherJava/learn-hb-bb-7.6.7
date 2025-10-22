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
import com.jiuqi.nr.portal.news.bean.NewsItem;
import java.io.Serializable;

public class NewsDefine
implements Serializable {
    private static final long serialVersionUID = -171312142031459183L;
    @JsonProperty(value="news")
    @JsonInclude
    private NewsItem newsItem;

    public NewsItem getNewsItem() {
        return this.newsItem;
    }

    public void setNewsItem(NewsItem newsItem) {
        this.newsItem = newsItem;
    }
}

