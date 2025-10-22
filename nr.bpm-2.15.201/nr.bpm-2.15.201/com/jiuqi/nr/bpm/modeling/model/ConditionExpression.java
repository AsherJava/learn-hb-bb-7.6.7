/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.modeling.model.Action;
import com.jiuqi.nr.bpm.modeling.model.Expression;
import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class ConditionExpression
extends ProcessElement {
    public ConditionExpression() {
        super("conditionExpression");
    }

    public void setExpressionType(String expresstionType) {
        Assert.notNull((Object)expresstionType, "'expresstionType' must not be null.");
        super.setProperty("xsi:type", expresstionType);
    }

    public void setExpression(Expression expression) {
        Assert.notNull((Object)expression, "'expression' must not be null.");
        super.appendChild(expression);
    }

    public static class OnActionCondition
    extends ConditionExpression {
        public OnActionCondition(FlowableProcessElement sourceTask, Action action) {
            this(sourceTask, action.getCode());
        }

        public OnActionCondition(FlowableProcessElement sourceTask, String actionCode) {
            Assert.notNull((Object)sourceTask, "'sourceTask' must not be null.");
            Assert.notNull((Object)actionCode, "'actionCode' must not be null.");
            this.setExpressionType("tFormalExpression");
            this.setExpression(new Expression(String.format("<![CDATA[${%s%s == \"%s\"}]]>", "action_", sourceTask.getId(), actionCode)));
        }

        public OnActionCondition(FlowableProcessElement sourceTask, String lineId, String actionCode, String result) {
            Assert.notNull((Object)sourceTask, "'sourceTask' must not be null.");
            Assert.notNull((Object)actionCode, "'actionCode' must not be null.");
            this.setExpressionType("tFormalExpression");
            String input = actionCode + sourceTask.getId() + lineId;
            this.setExpression(new Expression(String.format("<![CDATA[${%s == \"%s\" }]]>", ProcessBuilderUtils.MD5(input), result)));
        }

        public OnActionCondition(String lineId, String result) {
            Assert.notNull((Object)lineId, "'lineId' must not be null.");
            this.setExpressionType("tFormalExpression");
            String input = lineId;
            this.setExpression(new Expression(String.format("<![CDATA[${%s == \"%s\" }]]>", ProcessBuilderUtils.MD5(input), result)));
        }
    }
}

