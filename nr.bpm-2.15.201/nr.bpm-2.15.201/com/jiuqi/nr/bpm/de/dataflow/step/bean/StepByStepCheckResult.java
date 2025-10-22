/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StepByStepCheckResult
implements Serializable {
    private static final long serialVersionUID = 6433018323583196668L;
    private List<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
    private String actionState;
    private boolean child;
    private String actionStateTitle;
    private String directActionStateTitle;

    public List<StepByStepCheckItem> getItems() {
        return this.items;
    }

    public void setItems(List<StepByStepCheckItem> items) {
        this.items = items;
    }

    public String getActionState() {
        return this.actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    public boolean isChild() {
        return this.child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public String getActionStateTitle() {
        return this.actionStateTitle;
    }

    public void setActionStateTitle(String actionStateTitle) {
        this.actionStateTitle = actionStateTitle;
    }

    public String getDirectActionStateTitle() {
        return this.directActionStateTitle;
    }

    public void setDirectActionStateTitle(String directActionStateTitle) {
        this.directActionStateTitle = directActionStateTitle;
    }
}

