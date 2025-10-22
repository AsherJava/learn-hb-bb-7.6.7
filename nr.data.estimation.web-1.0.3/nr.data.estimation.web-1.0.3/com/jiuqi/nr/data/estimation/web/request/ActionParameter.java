/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class ActionParameter {
    public static final String actionNameKey = "actionId";
    private String actionId;
    private String estimationScheme;
    private Map<String, DimensionValue> dimValueSet;

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getEstimationScheme() {
        return this.estimationScheme;
    }

    public void setEstimationScheme(String estimationScheme) {
        this.estimationScheme = estimationScheme;
    }

    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }
}

