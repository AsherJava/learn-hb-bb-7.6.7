/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;

public class FormEditable
extends ActivitiExtension {
    public FormEditable(Boolean editable) {
        super("formEditable");
        this.setProperty("value", editable.toString());
    }
}

