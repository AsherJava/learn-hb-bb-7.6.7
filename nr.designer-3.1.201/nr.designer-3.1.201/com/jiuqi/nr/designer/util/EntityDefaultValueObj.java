/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;

public class EntityDefaultValueObj {
    private String fieldKey;
    private String entityId;
    private String title;
    private EntityValueType entityValueType;
    private String itemValue;

    public EntityDefaultValueObj() {
    }

    public EntityDefaultValueObj(EntityDefaultValue entityDefaultValue) {
        if (entityDefaultValue != null) {
            this.entityId = entityDefaultValue.getEntityId();
            this.fieldKey = entityDefaultValue.getFieldKey();
            this.entityValueType = entityDefaultValue.getEntityValueType();
            this.itemValue = entityDefaultValue.getItemValue();
        }
    }

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static EntityDefaultValue convert(EntityDefaultValueObj obj) {
        EntityDefaultValue entityDefaultValue = new EntityDefaultValue();
        entityDefaultValue.setFieldKey(obj.getFieldKey());
        entityDefaultValue.setEntityId(obj.getEntityId());
        entityDefaultValue.setItemValue(obj.getItemValue());
        entityDefaultValue.setEntityValueType(obj.getEntityValueType());
        return entityDefaultValue;
    }
}

