/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class MultiInstanceLoop
extends ProcessElement {
    public MultiInstanceLoop() {
        super("multiInstanceLoopCharacteristics");
    }

    @Override
    public void setProperty(String name, String value) {
        super.setProperty(name, value);
    }

    public void setExtension(String name, String value) {
        super.setProperty(name, value);
    }

    public void addExtension(ProcessElement extension) {
        Assert.notNull((Object)extension, "'extension' must not be null.");
        super.appendChild(extension);
    }
}

