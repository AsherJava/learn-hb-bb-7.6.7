/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public class OpenEstimationPageFuncPara {
    private String actionId;
    private String formSchemeId;
    private List<String> formIds;
    private Map<String, DimensionValue> dimValueSet;
    private JtableContext context;

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    public List<String> getFormIds() {
        return this.formIds;
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

