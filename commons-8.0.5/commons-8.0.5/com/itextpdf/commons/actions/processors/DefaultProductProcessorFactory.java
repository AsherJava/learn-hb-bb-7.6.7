/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.processors;

import com.itextpdf.commons.actions.processors.DefaultITextProductEventProcessor;
import com.itextpdf.commons.actions.processors.IProductProcessorFactory;
import com.itextpdf.commons.actions.processors.ITextProductEventProcessor;

public class DefaultProductProcessorFactory
implements IProductProcessorFactory {
    @Override
    public ITextProductEventProcessor createProcessor(String productName) {
        return new DefaultITextProductEventProcessor(productName);
    }
}

