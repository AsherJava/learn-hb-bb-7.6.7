/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.sequence;

import com.itextpdf.commons.actions.sequence.SequenceId;

public abstract class AbstractIdentifiableElement {
    private SequenceId sequenceId;

    SequenceId getSequenceId() {
        return this.sequenceId;
    }

    void setSequenceId(SequenceId sequenceId) {
        this.sequenceId = sequenceId;
    }
}

