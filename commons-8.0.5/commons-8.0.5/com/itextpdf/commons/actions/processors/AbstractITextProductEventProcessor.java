/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.processors;

import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.processors.ITextProductEventProcessor;

public abstract class AbstractITextProductEventProcessor
implements ITextProductEventProcessor {
    private final String productName;

    public AbstractITextProductEventProcessor(String productName) {
        if (productName == null) {
            throw new IllegalArgumentException("Product name can not be null.");
        }
        this.productName = productName;
    }

    @Override
    public abstract void onEvent(AbstractProductProcessITextEvent var1);

    @Override
    public abstract String getUsageType();

    @Override
    public String getProducer() {
        return "iText\u00ae ${usedProducts:P V (T 'version')} \u00a9${copyrightSince}-${copyrightTo} Apryse Group NV";
    }

    @Override
    public String getProductName() {
        return this.productName;
    }
}

