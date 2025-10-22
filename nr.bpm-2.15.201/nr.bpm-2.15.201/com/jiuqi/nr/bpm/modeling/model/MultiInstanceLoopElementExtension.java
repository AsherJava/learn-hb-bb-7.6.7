/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import org.springframework.util.Assert;

public class MultiInstanceLoopElementExtension
extends ActivitiExtension {
    public MultiInstanceLoopElementExtension() {
        super("activiti:elementVariable");
    }

    public void setDelegateExpression(String value) {
        Assert.notNull((Object)value, "'value' must not be null.");
        super.setProperty("value", value);
    }

    public void setParameterText(String paramText) {
        super.setProperty("param", paramText);
    }

    public static class DefaultMultiInstanceLoopElement
    extends MultiInstanceLoopElementExtension {
        public DefaultMultiInstanceLoopElement() {
            this.setDelegateExpression(String.format("${%s}", "assignee"));
        }
    }
}

