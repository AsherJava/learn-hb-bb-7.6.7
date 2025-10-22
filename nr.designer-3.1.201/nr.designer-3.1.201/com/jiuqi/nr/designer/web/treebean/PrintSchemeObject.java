/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.treebean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintSchemeObject {
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
    @JsonProperty(value="pritnSchemeType")
    private int pritnSchemeType;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getPritnSchemeType() {
        return this.pritnSchemeType;
    }

    public void setPritnSchemeType(int pritnSchemeType) {
        this.pritnSchemeType = pritnSchemeType;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }
}

