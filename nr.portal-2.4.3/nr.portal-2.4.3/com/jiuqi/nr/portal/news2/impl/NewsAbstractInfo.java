/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news2.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import java.time.LocalDateTime;

public class NewsAbstractInfo
implements IBaseInfo {
    private String id;
    private Integer order;
    private String title;
    private String link;
    private String poster;
    @JsonProperty(value="abstract")
    private String abstractDesc;
    @JsonProperty(value="date")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Boolean editable = false;
    private Boolean top;
    private Boolean hide;
    private Boolean autoPop;
    private Boolean showLatest;
    private Boolean startAuth;
    private Integer viewCount;
    private String extention;
    private Boolean read = false;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getPortalId() {
        return null;
    }

    @Override
    public String getMid() {
        return null;
    }

    public Boolean getEditable() {
        return this.editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
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

    public Boolean getShowLatest() {
        return this.showLatest;
    }

    public void setShowLatest(Boolean showLatest) {
        this.showLatest = showLatest;
    }

    @Override
    public Boolean getStartAuth() {
        return this.startAuth;
    }

    public void setStartAuth(Boolean startAuth) {
        this.startAuth = startAuth;
    }

    public Integer getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getExtention() {
        return this.extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}

