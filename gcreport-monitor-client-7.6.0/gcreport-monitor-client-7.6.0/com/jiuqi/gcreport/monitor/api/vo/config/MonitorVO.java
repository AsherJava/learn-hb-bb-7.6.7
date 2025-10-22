/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import java.util.Date;

public class MonitorVO {
    private String recid;
    private String groupId;
    private String code;
    private String label;
    private String title;
    private String creatorId;
    private Date createTime;
    private String modifierId;
    private Date modifyTime;
    private Integer showNode;
    private String key;

    public Integer getShowNode() {
        return this.showNode;
    }

    public void setShowNode(Integer showNode) {
        this.showNode = showNode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifierId() {
        return this.modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

