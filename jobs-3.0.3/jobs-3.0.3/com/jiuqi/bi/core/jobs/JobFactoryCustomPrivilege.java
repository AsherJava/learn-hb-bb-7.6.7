/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import java.io.Serializable;

public class JobFactoryCustomPrivilege
implements Serializable {
    private boolean canAdd = true;
    private boolean canEdit = true;
    private boolean canExec = true;

    public JobFactoryCustomPrivilege() {
    }

    public JobFactoryCustomPrivilege(boolean canAdd, boolean canEdit, boolean canExec) {
        this.canAdd = canAdd;
        this.canEdit = canEdit;
        this.canExec = canExec;
    }

    public boolean isCanAdd() {
        return this.canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanExec() {
        return this.canExec;
    }

    public void setCanExec(boolean canExec) {
        this.canExec = canExec;
    }
}

