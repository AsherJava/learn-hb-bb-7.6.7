/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldGroupObj {
    @JsonProperty
    private String ParentGroupID;
    @JsonProperty
    private String ID;
    @JsonProperty
    private String Title;
    @JsonProperty
    private String Order;
    @JsonProperty
    private boolean IsNew = false;
    @JsonProperty
    private boolean IsDeleted = false;
    @JsonProperty
    private boolean IsDirty = false;

    @JsonIgnore
    public String getParentGroupID() {
        return this.ParentGroupID;
    }

    @JsonIgnore
    public void setParentGroupID(String parentGroupID) {
        this.ParentGroupID = parentGroupID;
    }

    @JsonIgnore
    public String getID() {
        return this.ID;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.ID = iD;
    }

    @JsonIgnore
    public String getTitle() {
        return this.Title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.Title = title;
    }

    @JsonIgnore
    public String getOrder() {
        return this.Order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.Order = order;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.IsNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.IsNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.IsDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.IsDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.IsDirty = isDirty;
    }
}

