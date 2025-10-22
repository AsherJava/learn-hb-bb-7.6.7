/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.StepByStepCheckItem;
import java.io.Serializable;
import java.util.List;

public class StepByStepCheckResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean child;
    private String actionStateTitle;
    private String directActionStateTitle;
    private List<StepByStepCheckItem> items;

    public List<StepByStepCheckItem> getItems() {
        return this.items;
    }

    public void setItems(List<StepByStepCheckItem> items) {
        this.items = items;
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

