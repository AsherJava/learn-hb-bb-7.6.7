/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.util.EntityValueType;

public class EntityDefaultValue {
    private String fieldKey;
    private String entityId;
    private EntityValueType entityValueType;
    private String itemValue;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public EntityValueType getEntityValueType() {
        return this.entityValueType;
    }

    public void setEntityValueType(EntityValueType entityValueType) {
        this.entityValueType = entityValueType;
    }

    public String getItemValue() {
        return this.itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityDefaultValue)) {
            return false;
        }
        EntityDefaultValue that = (EntityDefaultValue)o;
        if (!this.getFieldKey().equals(that.getFieldKey())) {
            return false;
        }
        if (!this.getEntityId().equals(that.getEntityId())) {
            return false;
        }
        if (this.getEntityValueType() != that.getEntityValueType()) {
            return false;
        }
        return this.getItemValue().equals(that.getItemValue());
    }

    public int hashCode() {
        int result = this.getFieldKey().hashCode();
        result = 31 * result + this.getEntityId().hashCode();
        result = 31 * result + this.getEntityValueType().hashCode();
        result = 31 * result + this.getItemValue().hashCode();
        return result;
    }
}

