/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.context;

import com.jiuqi.nr.workflow2.instance.context.InstanceBaseContext;
import com.jiuqi.nr.workflow2.instance.entity.EntityInfo;
import com.jiuqi.nr.workflow2.instance.enumeration.InstanceOperateType;

public class InstanceOperateContext
extends InstanceBaseContext {
    private EntityInfo unitInfo;
    private EntityInfo formOrFormGroupInfo;
    private InstanceOperateType operateType;

    public EntityInfo getUnitInfo() {
        return this.unitInfo;
    }

    public void setUnitInfo(EntityInfo unitInfo) {
        this.unitInfo = unitInfo;
    }

    public EntityInfo getFormOrFormGroupInfo() {
        return this.formOrFormGroupInfo;
    }

    public void setFormOrFormGroupInfo(EntityInfo formOrFormGroupInfo) {
        this.formOrFormGroupInfo = formOrFormGroupInfo;
    }

    public InstanceOperateType getOperateType() {
        return this.operateType;
    }

    public void setOperateType(InstanceOperateType operateType) {
        this.operateType = operateType;
    }
}

