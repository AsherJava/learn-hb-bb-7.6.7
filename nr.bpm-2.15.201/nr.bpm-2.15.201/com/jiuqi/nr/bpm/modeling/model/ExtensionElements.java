/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class ExtensionElements
extends ProcessElement {
    public ExtensionElements() {
        super("extensionElements");
    }

    public void addExtension(ActivitiExtension extension) {
        Assert.notNull((Object)extension, "'extension' must not be null.");
        super.appendChild(extension);
    }
}

