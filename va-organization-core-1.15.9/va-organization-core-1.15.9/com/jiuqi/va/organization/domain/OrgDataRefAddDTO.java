/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.mapper.domain.TenantDO;

public class OrgDataRefAddDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private OrgDTO target;
    private OrgDTO source;
    private boolean autoMatchParent = true;
    private boolean syncExtField = false;

    public OrgDTO getTarget() {
        return this.target;
    }

    public void setTarget(OrgDTO target) {
        this.target = target;
    }

    public OrgDTO getSource() {
        return this.source;
    }

    public void setSource(OrgDTO source) {
        this.source = source;
    }

    public boolean isAutoMatchParent() {
        return this.autoMatchParent;
    }

    public void setAutoMatchParent(boolean autoMatchParent) {
        this.autoMatchParent = autoMatchParent;
    }

    public boolean isSyncExtField() {
        return this.syncExtField;
    }

    public void setSyncExtField(boolean syncExtField) {
        this.syncExtField = syncExtField;
    }
}

