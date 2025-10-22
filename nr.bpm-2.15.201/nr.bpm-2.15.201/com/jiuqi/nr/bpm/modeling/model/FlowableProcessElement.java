/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.IdentitiableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.SequenceFlow;
import org.springframework.util.Assert;

public abstract class FlowableProcessElement
extends IdentitiableProcessElement {
    protected FlowableProcessElement(String tag, String id) {
        super(tag, id);
    }

    public void setDefaultFlow(SequenceFlow defaultFlow) {
        Assert.notNull((Object)defaultFlow, "'defaultFlow' must not be null.");
        super.setProperty("default", defaultFlow.getId());
    }
}

