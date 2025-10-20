/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.confirmations;

import com.itextpdf.commons.actions.AbstractEventWrapper;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;

public class ConfirmedEventWrapper
extends AbstractEventWrapper {
    private final String productUsageType;
    private final String producerLine;

    public ConfirmedEventWrapper(AbstractProductProcessITextEvent event, String productUsageType, String producerLine) {
        super(event, EventConfirmationType.UNCONFIRMABLE);
        this.productUsageType = productUsageType;
        this.producerLine = producerLine;
    }

    public String getProductUsageType() {
        return this.productUsageType;
    }

    public String getProducerLine() {
        return this.producerLine;
    }
}

