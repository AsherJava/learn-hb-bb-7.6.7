/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.processors;

import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;

public interface ITextProductEventProcessor {
    public void onEvent(AbstractProductProcessITextEvent var1);

    public String getProductName();

    public String getUsageType();

    public String getProducer();
}

