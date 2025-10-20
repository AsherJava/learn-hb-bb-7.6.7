/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.sequence.SequenceId;

public abstract class AbstractEventWrapper
extends AbstractProductProcessITextEvent {
    private final AbstractProductProcessITextEvent event;

    protected AbstractEventWrapper(AbstractProductProcessITextEvent event, EventConfirmationType confirmationType) {
        super(event.getSequenceId(), event.getProductData(), event.getMetaInfo(), confirmationType);
        this.event = event;
    }

    protected AbstractEventWrapper(SequenceId updatedSequenceId, AbstractProductProcessITextEvent event, EventConfirmationType confirmationType) {
        super(updatedSequenceId, event.getProductData(), event.getMetaInfo(), confirmationType);
        this.event = event;
    }

    public AbstractProductProcessITextEvent getEvent() {
        return this.event;
    }

    @Override
    public Class<?> getClassFromContext() {
        return this.getEvent().getClassFromContext();
    }

    @Override
    public String getEventType() {
        return this.getEvent().getEventType();
    }
}

