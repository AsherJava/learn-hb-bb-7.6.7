/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.processors;

import com.itextpdf.commons.actions.processors.IProductProcessorFactory;
import com.itextpdf.commons.actions.processors.ITextProductEventProcessor;
import com.itextpdf.commons.actions.processors.UnderAgplITextProductEventProcessor;

public class UnderAgplProductProcessorFactory
implements IProductProcessorFactory {
    @Override
    public ITextProductEventProcessor createProcessor(String productName) {
        return new UnderAgplITextProductEventProcessor(productName);
    }
}

