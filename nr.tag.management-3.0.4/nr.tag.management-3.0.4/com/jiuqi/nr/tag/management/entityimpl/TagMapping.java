/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.entityimpl;

import com.jiuqi.nr.tag.management.entity.ITagMapping;

public class TagMapping
implements ITagMapping {
    private String tagKey;
    private String entityData;
    private String order;

    public TagMapping(String tagKey, String entityData) {
        this.tagKey = tagKey;
        this.entityData = entityData;
    }

    public TagMapping(String tagKey, String entityData, String order) {
        this.tagKey = tagKey;
        this.entityData = entityData;
        this.order = order;
    }

    @Override
    public String getTagKey() {
        return this.tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    @Override
    public String getEntityData() {
        return this.entityData;
    }

    public void setEntityData(String entityData) {
        this.entityData = entityData;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

