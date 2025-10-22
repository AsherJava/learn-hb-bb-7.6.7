/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public abstract class IdentitiableProcessElement
extends ProcessElement {
    private final String id;

    protected IdentitiableProcessElement(String tag, String id) {
        super(tag);
        Assert.notNull((Object)id, "'id' must not be null.");
        super.setProperty("id", id);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

