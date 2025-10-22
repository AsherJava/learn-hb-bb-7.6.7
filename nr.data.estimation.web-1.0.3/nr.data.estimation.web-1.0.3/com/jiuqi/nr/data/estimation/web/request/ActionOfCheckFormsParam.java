/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.data.estimation.web.request.ActionParameter;
import java.util.ArrayList;
import java.util.List;

public class ActionOfCheckFormsParam
extends ActionParameter {
    private List<String> formIds;

    public List<String> getFormIds() {
        return this.formIds != null ? this.formIds : new ArrayList<String>();
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }
}

