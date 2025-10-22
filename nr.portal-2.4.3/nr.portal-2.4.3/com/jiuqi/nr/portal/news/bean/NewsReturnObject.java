/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news.bean.NewsItem;
import java.io.Serializable;

public class NewsReturnObject
implements Serializable {
    private static final long serialVersionUID = -4218035634408086580L;
    @JsonProperty(value="Success")
    private boolean success;
    @JsonProperty(value="Message")
    private String message;
    @JsonProperty(value="NewsDefine")
    private NewsItem newsItem;
    @JsonProperty(value="NewsList")
    private NewsItem[] newsList;
    @JsonProperty(value="NewsId")
    private String newsId;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NewsItem getNewsItem() {
        return this.newsItem;
    }

    public void setNewsItem(NewsItem newsItem) {
        this.newsItem = newsItem;
    }

    public NewsItem[] getNewsList() {
        return this.newsList;
    }

    public void setNewsList(NewsItem[] newsList) {
        this.newsList = newsList;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}

