/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ConditionExpression;
import com.jiuqi.nr.bpm.modeling.model.IdentitiableProcessElement;
import org.springframework.util.Assert;

public class SequenceFlow
extends IdentitiableProcessElement {
    public SequenceFlow(String id, IdentitiableProcessElement sourceRef, IdentitiableProcessElement targetRef) {
        super("sequenceFlow", id);
        Assert.notNull((Object)sourceRef, "'sourceRef' must not be null.");
        Assert.notNull((Object)targetRef, "'targetRef' must not be null.");
        super.setProperty("sourceRef", sourceRef.getId());
        super.setProperty("targetRef", targetRef.getId());
    }

    public void setCondition(ConditionExpression condition) {
        Assert.notNull((Object)condition, "'condition' must not be null.");
        super.appendChild(condition);
    }
}

