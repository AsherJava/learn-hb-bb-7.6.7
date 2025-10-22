/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.context.infc.impl.NRContext;

public class SubordinateDWContext
extends NRContext {
    private int type;
    private String entityId;
    private String entityKeyData;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityKeyData() {
        return this.entityKeyData;
    }

    public void setEntityKeyData(String entityKeyData) {
        this.entityKeyData = entityKeyData;
    }
}

