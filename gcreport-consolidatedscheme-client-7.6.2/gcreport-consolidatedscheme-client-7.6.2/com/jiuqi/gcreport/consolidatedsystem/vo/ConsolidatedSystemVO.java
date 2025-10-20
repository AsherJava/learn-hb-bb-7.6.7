/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo;

import java.util.Date;

public class ConsolidatedSystemVO {
    private String id;
    private Long recver;
    private String systemName;
    private String dataSchemeKey;
    private String dataSchemeName;
    private String description;
    private String dataSort;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private Boolean editFlag;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.recver = recver;
    }

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataSort() {
        return this.dataSort;
    }

    public void setDataSort(String dataSort) {
        this.dataSort = dataSort;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Boolean getEditFlag() {
        return this.editFlag;
    }

    public void setEditFlag(Boolean editFlag) {
        this.editFlag = editFlag;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataSchemeName() {
        return this.dataSchemeName;
    }

    public void setDataSchemeName(String dataSchemeName) {
        this.dataSchemeName = dataSchemeName;
    }
}

