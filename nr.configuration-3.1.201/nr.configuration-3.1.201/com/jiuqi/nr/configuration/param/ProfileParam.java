/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.configuration.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.UUID;

public class ProfileParam {
    private UUID taskKey;
    private UUID userKey;
    private String code;
    private UUID formSchemeKey;
    private DimensionValueSet entityKey;
    private UUID formKey;

    public UUID getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(UUID taskKey) {
        this.taskKey = taskKey;
    }

    public UUID getUserKey() {
        return this.userKey;
    }

    public void setUserKey(UUID userKey) {
        this.userKey = userKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UUID getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(UUID formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(DimensionValueSet entityKey) {
        this.entityKey = entityKey;
    }

    public UUID getFormKey() {
        return this.formKey;
    }

    public void setFormKey(UUID formKey) {
        this.formKey = formKey;
    }
}

