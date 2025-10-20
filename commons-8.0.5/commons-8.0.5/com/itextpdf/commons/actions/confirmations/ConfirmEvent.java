/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.confirmations;

import com.itextpdf.commons.actions.AbstractEventWrapper;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.sequence.SequenceId;

public class ConfirmEvent
extends AbstractEventWrapper {
    public ConfirmEvent(SequenceId updatedSequenceId, AbstractProductProcessITextEvent confirmedEvent) {
        super(updatedSequenceId, confirmedEvent, EventConfirmationType.UNCONFIRMABLE);
    }

    public ConfirmEvent(AbstractProductProcessITextEvent confirmedEvent) {
        this(confirmedEvent.getSequenceId(), confirmedEvent);
    }

    public AbstractProductProcessITextEvent getConfirmedEvent() {
        AbstractProductProcessITextEvent event = this.getEvent();
        if (event instanceof ConfirmEvent) {
            return ((ConfirmEvent)event).getConfirmedEvent();
        }
        return event;
    }
}

