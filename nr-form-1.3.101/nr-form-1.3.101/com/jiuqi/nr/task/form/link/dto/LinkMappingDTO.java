/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.link.dto;

import com.jiuqi.nr.task.form.dto.AbstractState;

public class LinkMappingDTO
extends AbstractState {
    private String id;
    private String leftLinkKey;
    private String rightLinkKey;
    private String entityId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftLinkKey() {
        return this.leftLinkKey;
    }

    public void setLeftLinkKey(String leftLinkKey) {
        this.leftLinkKey = leftLinkKey;
    }

    public String getRightLinkKey() {
        return this.rightLinkKey;
    }

    public void setRightLinkKey(String rightLinkKey) {
        this.rightLinkKey = rightLinkKey;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

