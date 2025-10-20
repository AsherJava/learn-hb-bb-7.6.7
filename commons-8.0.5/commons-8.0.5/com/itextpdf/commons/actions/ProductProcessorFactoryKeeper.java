/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.processors.DefaultProductProcessorFactory;
import com.itextpdf.commons.actions.processors.IProductProcessorFactory;

final class ProductProcessorFactoryKeeper {
    private static final IProductProcessorFactory DEFAULT_FACTORY;
    private static IProductProcessorFactory productProcessorFactory;

    private ProductProcessorFactoryKeeper() {
    }

    static void setProductProcessorFactory(IProductProcessorFactory productProcessorFactory) {
        ProductProcessorFactoryKeeper.productProcessorFactory = productProcessorFactory;
    }

    static void restoreDefaultProductProcessorFactory() {
        productProcessorFactory = DEFAULT_FACTORY;
    }

    static IProductProcessorFactory getProductProcessorFactory() {
        return productProcessorFactory;
    }

    static {
        productProcessorFactory = DEFAULT_FACTORY = new DefaultProductProcessorFactory();
    }
}

