/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.BPMNDocumentElement;
import org.springframework.util.Assert;

public class Expression
extends BPMNDocumentElement {
    private final String exp;

    public Expression(String exp) {
        Assert.notNull((Object)exp, "'exp' must not be null.");
        this.exp = exp;
    }

    @Override
    public void buildBPMNDocument(StringBuilder builder) {
        builder.append(this.exp);
    }
}

