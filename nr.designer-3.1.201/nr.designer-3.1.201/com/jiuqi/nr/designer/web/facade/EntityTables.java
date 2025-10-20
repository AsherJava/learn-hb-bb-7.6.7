/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.definition.common.TableKind
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.nr.designer.web.facade.EntityLinkageObject;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EntityTables {
    @JsonProperty
    private String ID;
    @JsonProperty
    private String Title;
    @JsonProperty
    private String Code;
    @JsonProperty
    private TableKind Kind;
    @JsonProperty
    private boolean IsExtend = false;
    @JsonProperty
    private boolean IsNew = false;
    @JsonProperty
    private boolean IsDeleted = false;
    @JsonProperty
    private boolean IsDirty = false;
    @JsonProperty
    private EntityLinkageObject EntityLinkageObject;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;

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

    @JsonIgnore
    public boolean isIsExtend() {
        return this.IsExtend;
    }

    @JsonIgnore
    public void setIsExtend(boolean isExtend) {
        this.IsExtend = isExtend;
    }

    @JsonIgnore
    public EntityLinkageObject getEntityLinkageObject() {
        return this.EntityLinkageObject;
    }

    @JsonIgnore
    public void setEntityLinkageObject(EntityLinkageObject entityLinkageObject) {
        this.EntityLinkageObject = entityLinkageObject;
    }

    @JsonIgnore
    public TableKind getKind() {
        return this.Kind;
    }

    @JsonIgnore
    public void setKind(TableKind kind) {
        this.Kind = kind;
    }

    @JsonIgnore
    public String getCode() {
        return this.Code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.Code = code;
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

