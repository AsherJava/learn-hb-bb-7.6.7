/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.trigger;

import com.jiuqi.nr.survey.model.trigger.Trigger;

public class TriggerSkip
extends Trigger {
    private String expression;
    private String gotoName;

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getGotoName() {
        return this.gotoName;
    }

    public void setGotoName(String gotoName) {
        this.gotoName = gotoName;
    }
}

