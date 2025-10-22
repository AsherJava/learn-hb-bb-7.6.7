/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.Expression;
import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class MultiInstanceLoopCompleteCondition
extends ProcessElement {
    public MultiInstanceLoopCompleteCondition() {
        super("completionCondition");
    }

    public void setExpression(Expression expression) {
        Assert.notNull((Object)expression, "'expression' must not be null.");
        super.appendChild(expression);
    }

    public static class DefaultMultiInstanceLoopExtension
    extends MultiInstanceLoopCompleteCondition {
        public DefaultMultiInstanceLoopExtension() {
            this.setExpression(new Expression(String.format("${%s}", "ActivitiMulitiInstanceListenerBean.completeTask(execution)")));
        }
    }
}

