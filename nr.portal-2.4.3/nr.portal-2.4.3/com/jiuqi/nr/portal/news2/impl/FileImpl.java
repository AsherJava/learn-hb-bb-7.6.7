/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonProperty$Access
 */
package com.jiuqi.nr.portal.news2.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class FileImpl
implements IBaseInfo {
    @JsonInclude
    private String id;
    @JsonInclude
    private String mid;
    @JsonInclude
    private Integer order;
    @JsonInclude
    private String title;
    @JsonInclude
    private String contentType;
    @JsonInclude
    private Long fileSize;
    @JsonInclude
    private String description;
    @JsonInclude
    private String link;
    @JsonInclude
    private Boolean showLatest;
    @JsonInclude
    private Boolean top;
    @JsonInclude
    private Boolean hide;
    @JsonInclude
    private String extention;
    @JsonInclude
    private Integer downLoadCount;
    @JsonProperty(access=JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonInclude
    private LocalDateTime createTime;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonProperty(access=JsonProperty.Access.READ_ONLY)
    @JsonInclude
    private LocalDateTime updateTime;
    @JsonInclude
    private String portalId;
    @JsonInclude
    private String showTitle;
    @JsonInclude
    private Boolean editable = false;
    @JsonInclude
    private Boolean startAuth;
    @JsonInclude
    private Boolean read = false;

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

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExtention() {
        return this.extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public Integer getDownLoadCount() {
        return this.downLoadCount;
    }

    public void setDownLoadCount(Integer downLoadCount) {
        this.downLoadCount = downLoadCount;
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

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public Boolean getEditable() {
        return this.editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @Override
    public Boolean getStartAuth() {
        return this.startAuth;
    }

    public void setStartAuth(Boolean startAuth) {
        this.startAuth = startAuth;
    }

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}

