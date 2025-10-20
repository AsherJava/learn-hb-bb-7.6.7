/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.treebean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormulaSchemeObject {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="IsNew")
    private boolean isNew;
    @JsonProperty(value="IsDirty")
    private boolean isDirty;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="FormSchemeKey")
    private String formSchemeKey;
    @JsonProperty(value="IsDefault")
    private boolean isDefault;
    @JsonProperty(value="FormulaSchemeType")
    private int formulaSchemeType;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;

    @JsonIgnore
    public String getID() {
        return this.id;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.id = iD;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.isNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.isDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @JsonIgnore
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @JsonIgnore
    public boolean getIsDefault() {
        return this.isDefault;
    }

    @JsonIgnore
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @JsonIgnore
    public int getFormulaSchemeType() {
        return this.formulaSchemeType;
    }

    @JsonIgnore
    public void setFormulaSchemeType(int formulaSchemeType) {
        this.formulaSchemeType = formulaSchemeType;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @JsonIgnore
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }
}

