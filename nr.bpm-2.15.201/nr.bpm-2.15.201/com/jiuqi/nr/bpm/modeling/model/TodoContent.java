/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;

public class TodoContent
extends ActivitiExtension {
    public TodoContent(String content) {
        super("todoContent");
        this.setProperty("value", content);
    }
}

