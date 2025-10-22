/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;

public class EndEvent
extends FlowableProcessElement {
    public EndEvent(String id) {
        super("endEvent", id);
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }
}

