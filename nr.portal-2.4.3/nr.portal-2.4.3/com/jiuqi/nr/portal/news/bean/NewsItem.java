/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news.INews;
import java.io.Serializable;
import java.util.Date;

public class NewsItem
implements INews,
Serializable {
    private static final long serialVersionUID = -1121739174868575814L;
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="MId")
    private String mId;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Link")
    private String link;
    @JsonProperty(value="Poster")
    private String poster;
    @JsonProperty(value="Abstract")
    private String abstact;
    @JsonProperty(value="Content")
    private String content;
    @JsonProperty(value="ShowLatest")
    private boolean showLatest;
    @JsonProperty(value="Stick")
    private boolean stick;
    @JsonProperty(value="Push")
    private boolean push;
    @JsonProperty(value="Hide")
    private boolean hide;
    @JsonProperty(value="NewsOrder")
    private int newsOrder;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonProperty(value="CreateTime")
    private Date createTime;
    private String portalId;

    @Override
    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String getMId() {
        return this.mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String getAbstact() {
        return this.abstact;
    }

    public void setAbstact(String abstact) {
        this.abstact = abstact;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean showLatest() {
        return this.showLatest;
    }

    public void setShowLatest(boolean showLatest) {
        this.showLatest = showLatest;
    }

    @Override
    public boolean stick() {
        return this.stick;
    }

    public void setStick(boolean stick) {
        this.stick = stick;
    }

    @Override
    public boolean push() {
        return this.push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    @Override
    public boolean hide() {
        return this.hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    @Override
    public int getNewsOrder() {
        return this.newsOrder;
    }

    public void setNewsOrder(int newsOrder) {
        this.newsOrder = newsOrder;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getPortalId() {
        return this.portalId;
    }

    public void setPortalId(String portalId) {
        this.portalId = portalId;
    }
}

