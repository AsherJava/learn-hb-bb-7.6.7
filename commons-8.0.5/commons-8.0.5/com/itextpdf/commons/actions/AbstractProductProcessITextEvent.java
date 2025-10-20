/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractContextBasedITextEvent;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.contexts.IMetaInfo;
import com.itextpdf.commons.actions.data.ProductData;
import com.itextpdf.commons.actions.sequence.SequenceId;
import java.lang.ref.WeakReference;

public abstract class AbstractProductProcessITextEvent
extends AbstractContextBasedITextEvent {
    private final WeakReference<SequenceId> sequenceId;
    private final EventConfirmationType confirmationType;

    protected AbstractProductProcessITextEvent(SequenceId sequenceId, ProductData productData, IMetaInfo metaInfo, EventConfirmationType confirmationType) {
        super(productData, metaInfo);
        this.sequenceId = new WeakReference<SequenceId>(sequenceId);
        this.confirmationType = confirmationType;
    }

    protected AbstractProductProcessITextEvent(ProductData productData, IMetaInfo metaInfo, EventConfirmationType confirmationType) {
        this(null, productData, metaInfo, confirmationType);
    }

    public SequenceId getSequenceId() {
        return (SequenceId)this.sequenceId.get();
    }

    public abstract String getEventType();

    public EventConfirmationType getConfirmationType() {
        return this.confirmationType;
    }
}

