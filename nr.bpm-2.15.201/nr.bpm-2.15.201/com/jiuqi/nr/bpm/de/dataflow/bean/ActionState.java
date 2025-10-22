/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;

public class ActionState {
    private ActionStateBean unitState;
    private ActionStateBean formState;
    private ActionStateBean groupState;

    public ActionStateBean getUnitState() {
        return this.unitState;
    }

    public void setUnitState(ActionStateBean unitState) {
        this.unitState = unitState;
    }

    public ActionStateBean getFormState() {
        return this.formState;
    }

    public void setFormState(ActionStateBean formState) {
        this.formState = formState;
    }

    public ActionStateBean getGroupState() {
        return this.groupState;
    }

    public void setGroupState(ActionStateBean groupState) {
        this.groupState = groupState;
    }
}

