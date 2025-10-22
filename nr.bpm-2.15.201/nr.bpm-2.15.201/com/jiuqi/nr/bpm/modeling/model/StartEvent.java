/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;

public class StartEvent
extends FlowableProcessElement {
    public StartEvent(String id) {
        super("startEvent", id);
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }
}

