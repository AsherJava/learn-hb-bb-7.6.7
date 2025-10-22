/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.internal.model.impl;

import com.jiuqi.nr.entity.model.IEntityRefer;

public class EntityReferImpl
implements IEntityRefer {
    private String ownField;
    private String ownEntityId;
    private String referEntityId;
    private String referEntityField;

    @Override
    public String getOwnField() {
        return this.ownField;
    }

    public void setOwnField(String ownField) {
        this.ownField = ownField;
    }

    @Override
    public String getOwnEntityId() {
        return this.ownEntityId;
    }

    public void setOwnEntityId(String ownEntityId) {
        this.ownEntityId = ownEntityId;
    }

    @Override
    public String getReferEntityId() {
        return this.referEntityId;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    @Override
    public String getReferEntityField() {
        return this.referEntityField;
    }

    public void setReferEntityField(String referEntityField) {
        this.referEntityField = referEntityField;
    }

    public String toString() {
        return "EntityReferImpl{ownField='" + this.ownField + '\'' + ", ownEntityId='" + this.ownEntityId + '\'' + ", referEntityId='" + this.referEntityId + '\'' + ", referEntityField='" + this.referEntityField + '\'' + '}';
    }
}

