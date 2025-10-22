/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.trigger;

import com.jiuqi.nr.survey.model.trigger.Trigger;

public class TriggerSetValue
extends Trigger {
    private String expression;
    private String setToName;
    private String setValue;

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getSetToName() {
        return this.setToName;
    }

    public void setSetToName(String setToName) {
        this.setToName = setToName;
    }

    public String getSetValue() {
        return this.setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }
}

