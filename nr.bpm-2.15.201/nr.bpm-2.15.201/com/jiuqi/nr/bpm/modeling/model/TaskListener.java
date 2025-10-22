/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import org.springframework.util.Assert;

public class TaskListener
extends ActivitiExtension {
    protected TaskListener() {
        super("taskListener");
    }

    public void setEvent(String event) {
        Assert.notNull((Object)event, "'event' must not be null.");
        super.setProperty("event", event);
    }

    public void setDelegateExpression(String delegateExpression) {
        Assert.notNull((Object)delegateExpression, "'delegateExpression' must not be null.");
        super.setProperty("delegateExpression", delegateExpression);
    }

    public static class DefaultTaskCreateListener
    extends TaskListener {
        public DefaultTaskCreateListener() {
            this.setEvent("create");
            this.setDelegateExpression(String.format("${%s}", "ActivitiTaskCreateListenerBean"));
        }
    }
}

