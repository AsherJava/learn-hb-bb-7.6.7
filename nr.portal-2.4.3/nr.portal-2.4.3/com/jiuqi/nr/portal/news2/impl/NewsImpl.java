/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news2.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import java.time.LocalDateTime;

public class NewsImpl
implements IBaseInfo {
    @JsonInclude
    private String id;
    @JsonProperty(value="mid")
    @JsonInclude
    private String mid;
    @JsonProperty(value="order")
    @JsonInclude
    private Integer order;
    @JsonProperty(value="title")
    @JsonInclude
    private String title;
    @JsonProperty(value="abstract")
    @JsonInclude
    private String abstractDesc;
    @JsonProperty(value="poster")
    @JsonInclude
    private String poster;
    @JsonProperty(value="content")
    @JsonInclude
    private String content;
    @JsonProperty(value="link")
    @JsonInclude
    private String link;
    @JsonProperty(value="showLatest")
    @JsonInclude
    private Boolean showLatest;
    @JsonProperty(value="top")
    @JsonInclude
    private Boolean top;
    @JsonProperty(value="hide")
    @JsonInclude
    private Boolean hide;
    @JsonProperty(value="autoPop")
    @JsonInclude
    private Boolean autoPop;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonProperty(value="createTime")
    @JsonInclude
    private LocalDateTime createTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonInclude
    private LocalDateTime updateTime;
    @JsonInclude
    private String extention;
    @JsonInclude
    private Integer viewCount;
    @JsonInclude
    private String portalId;
    @JsonInclude
    private Boolean startAuth;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractDesc() {
        return this.abstractDesc;
    }

    public void setAbstractDesc(String abstractDesc) {
        this.abstractDesc = abstractDesc;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getShowLatest() {
        return this.showLatest;
    }

    public void setShowLatest(Boolean showLatest) {
        this.showLatest = showLatest;
    }

    public Boolean getTop() {
        return this.top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getHide() {
        return this.hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean getAutoPop() {
        return this.autoPop;
    }

    public void setAutoPop(Boolean autoPop) {
        this.autoPop = autoPop;
    }

    public String getExtention() {
        return this.extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public Integer getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getPortalId() {
        return this.portalId;
    }

    public void setPortalId(String portalId) {
        this.portalId = portalId;
    }

    @Override
    public Boolean getStartAuth() {
        return this.startAuth;
    }

    public void setStartAuth(Boolean startAuth) {
        this.startAuth = startAuth;
    }
}

