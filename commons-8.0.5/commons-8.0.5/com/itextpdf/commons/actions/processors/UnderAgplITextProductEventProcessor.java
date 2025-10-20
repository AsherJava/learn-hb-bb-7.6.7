/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.processors;

import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.processors.AbstractITextProductEventProcessor;

public class UnderAgplITextProductEventProcessor
extends AbstractITextProductEventProcessor {
    public UnderAgplITextProductEventProcessor(String productName) {
        super(productName);
    }

    @Override
    public void onEvent(AbstractProductProcessITextEvent event) {
    }

    @Override
    public String getUsageType() {
        return "AGPL";
    }
}

