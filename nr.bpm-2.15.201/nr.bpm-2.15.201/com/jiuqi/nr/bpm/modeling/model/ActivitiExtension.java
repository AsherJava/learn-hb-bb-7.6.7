/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ProcessElement;

public abstract class ActivitiExtension
extends ProcessElement {
    protected ActivitiExtension(String extensionTag) {
        super("activiti:" + extensionTag);
    }
}

