/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;

public class MultiInstanceLoopUserId
extends ActivitiExtension {
    public MultiInstanceLoopUserId() {
        super("multiInstanceLoopUserId");
    }

    public void setUserId(String userId) {
        super.setProperty("value", userId);
    }
}

