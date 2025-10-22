/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class EntityLinkageObject {
    @JsonProperty
    private String Key;
    @JsonProperty
    private String StrKey;
    @JsonProperty
    private String Order;
    @JsonProperty
    private String Version;
    @JsonProperty
    private String OwnerLevelAndId;
    @JsonProperty
    private String Title;
    @JsonProperty
    private Date UpdateTime;
    @JsonProperty
    private String MasterEntityKey;
    @JsonProperty
    private String SlaveEntityKey;
    @JsonProperty
    private String LinkageCondition;
    @JsonProperty
    private boolean IsNew = false;
    @JsonProperty
    private boolean IsDeleted = false;
    @JsonProperty
    private boolean IsDirty = false;

    @JsonProperty
    public boolean isIsNew() {
        return this.IsNew;
    }

    @JsonProperty
    public void setIsNew(boolean isNew) {
        this.IsNew = isNew;
    }

    @JsonProperty
    public boolean isIsDeleted() {
        return this.IsDeleted;
    }

    @JsonProperty
    public void setIsDeleted(boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    @JsonProperty
    public boolean isIsDirty() {
        return this.IsDirty;
    }

    @JsonProperty
    public void setIsDirty(boolean isDirty) {
        this.IsDirty = isDirty;
    }

    @JsonProperty
    public String getKey() {
        return this.Key;
    }

    @JsonProperty
    public void setKey(String key) {
        this.Key = key;
    }

    @JsonProperty
    public String getStrKey() {
        return this.StrKey;
    }

    @JsonProperty
    public void setStrKey(String strKey) {
        this.StrKey = strKey;
    }

    @JsonProperty
    public String getOrder() {
        return this.Order;
    }

    @JsonProperty
    public void setOrder(String order) {
        this.Order = order;
    }

    @JsonProperty
    public String getVersion() {
        return this.Version;
    }

    @JsonProperty
    public void setVersion(String version) {
        this.Version = version;
    }

    @JsonProperty
    public String getOwnerLevelAndId() {
        return this.OwnerLevelAndId;
    }

    @JsonProperty
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.OwnerLevelAndId = ownerLevelAndId;
    }

    @JsonProperty
    public String getTitle() {
        return this.Title;
    }

    @JsonProperty
    public void setTitle(String title) {
        this.Title = title;
    }

    @JsonProperty
    public Date getUpdateTime() {
        return this.UpdateTime;
    }

    @JsonProperty
    public void setUpdateTime(Date updateTime) {
        this.UpdateTime = updateTime;
    }

    @JsonProperty
    public String getMasterEntityKey() {
        return this.MasterEntityKey;
    }

    @JsonProperty
    public void setMasterEntityKey(String masterEntityKey) {
        this.MasterEntityKey = masterEntityKey;
    }

    @JsonProperty
    public String getSlaveEntityKey() {
        return this.SlaveEntityKey;
    }

    @JsonProperty
    public void setSlaveEntityKey(String slaveEntityKey) {
        this.SlaveEntityKey = slaveEntityKey;
    }

    @JsonProperty
    public String getLinkageCondition() {
        return this.LinkageCondition;
    }

    @JsonProperty
    public void setLinkageCondition(String linkageCondition) {
        this.LinkageCondition = linkageCondition;
    }
}

