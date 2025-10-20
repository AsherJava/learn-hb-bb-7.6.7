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

public class RegionTabSettingObj {
    @JsonProperty(value="ID")
    private String ID;
    @JsonProperty(value="Title")
    private String Title;
    @JsonProperty(value="Order")
    private String Order;
    @JsonProperty(value="DisplayCondition")
    private String DisplayCondition;
    @JsonProperty(value="FilterCondition")
    private String FilterCondition;
    @JsonProperty(value="BindingExpression")
    private String BindingExpression;
    @JsonProperty(value="IsNew")
    private boolean IsNew = false;
    @JsonProperty(value="IsDeleted")
    private boolean IsDeleted = false;
    @JsonProperty(value="IsDirty")
    private boolean IsDirty = false;

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
    public String getDisplayCondition() {
        return this.DisplayCondition;
    }

    @JsonIgnore
    public void setDisplayCondition(String displayCondition) {
        this.DisplayCondition = displayCondition;
    }

    @JsonIgnore
    public String getFilterCondition() {
        return this.FilterCondition;
    }

    @JsonIgnore
    public void setFilterCondition(String filterCondition) {
        this.FilterCondition = filterCondition;
    }

    @JsonIgnore
    public String getBindingExpression() {
        return this.BindingExpression;
    }

    @JsonIgnore
    public void setBindingExpression(String bindingExpression) {
        this.BindingExpression = bindingExpression;
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

