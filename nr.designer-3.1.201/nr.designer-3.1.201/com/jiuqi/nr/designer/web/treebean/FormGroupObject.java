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

public class FormGroupObject {
    @JsonProperty(value="IsNew")
    private boolean isNew;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted;
    @JsonProperty(value="IsDirty")
    private boolean isDirty;
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="TaskId")
    private String taskId;
    @JsonProperty(value="ParentGroupID")
    private String parentGroupID;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="FormSchemeKey")
    private String formSchemeKey;
    @JsonProperty(value="Condition")
    private String condition;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="MeasureUnitIsExtend")
    private boolean measureUnitIsExtend = true;

    @JsonIgnore
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @JsonIgnore
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.code = code;
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
    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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
    public String getID() {
        return this.id;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.id = iD;
    }

    @JsonIgnore
    public String getTaskId() {
        return this.taskId;
    }

    @JsonIgnore
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonIgnore
    public String getParentGroupID() {
        return this.parentGroupID;
    }

    @JsonIgnore
    public void setParentGroupID(String parentGroupID) {
        this.parentGroupID = parentGroupID;
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
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getCondition() {
        return this.condition;
    }

    @JsonIgnore
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @JsonIgnore
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @JsonIgnore
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @JsonIgnore
    public boolean getMeasureUnitIsExtend() {
        return this.measureUnitIsExtend;
    }

    @JsonIgnore
    public void setMeasureUnitIsExtend(boolean measureUnitIsExtend) {
        this.measureUnitIsExtend = measureUnitIsExtend;
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

