/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;

public class SingleDimDefine
implements SingleDim {
    private String entityId;
    private String value;

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SingleDimDefine() {
    }

    public SingleDimDefine(String entityId, String value) {
        this.entityId = entityId;
        this.value = value;
    }
}

