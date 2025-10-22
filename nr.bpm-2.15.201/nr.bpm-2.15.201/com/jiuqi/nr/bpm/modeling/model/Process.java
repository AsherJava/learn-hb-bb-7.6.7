/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.IdentitiableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class Process
extends IdentitiableProcessElement {
    public Process(String id) {
        super("process", id);
        this.setName(id);
        this.setExecutable(true);
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }

    public void setExecutable(Boolean executable) {
        super.setProperty("isExecutable", executable.toString());
    }

    public void addElement(ProcessElement processElement) {
        Assert.notNull((Object)processElement, "'processElement' must not be null.");
        super.appendChild(processElement);
    }
}

