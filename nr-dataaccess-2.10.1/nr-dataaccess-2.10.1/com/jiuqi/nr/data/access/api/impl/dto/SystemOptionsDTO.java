/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.api.impl.dto;

import com.jiuqi.nr.data.access.common.FormLockAuthType;

public class SystemOptionsDTO {
    private String userId;
    private boolean isSystem = false;
    private boolean multiUserLockOption = false;
    private boolean forceUnLockOption = false;
    private FormLockAuthType formLockType = FormLockAuthType.DISABLED;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSystem() {
        return this.isSystem;
    }

    public void setSystem(boolean system) {
        this.isSystem = system;
    }

    public boolean isMultiUserLockOption() {
        return this.multiUserLockOption;
    }

    public void setMultiUserLockOption(boolean multiUserLockOption) {
        this.multiUserLockOption = multiUserLockOption;
    }

    public boolean isForceUnLockOption() {
        return this.forceUnLockOption;
    }

    public void setForceUnLockOption(boolean forceUnLockOption) {
        this.forceUnLockOption = forceUnLockOption;
    }

    public FormLockAuthType getFormLockType() {
        return this.formLockType;
    }

    public void setFormLockType(FormLockAuthType formLockType) {
        this.formLockType = formLockType;
    }
}

