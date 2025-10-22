/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;

public class Retrivable
extends ActivitiExtension {
    public Retrivable(Boolean retrivable) {
        super("retrivable");
        super.setProperty("value", retrivable.toString());
    }
}

