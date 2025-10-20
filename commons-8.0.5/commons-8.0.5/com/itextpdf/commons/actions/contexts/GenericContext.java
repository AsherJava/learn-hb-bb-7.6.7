/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.contexts;

import com.itextpdf.commons.actions.AbstractContextBasedITextEvent;
import com.itextpdf.commons.actions.contexts.IContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GenericContext
implements IContext {
    private final Set<String> supportedProducts = new HashSet<String>();

    public GenericContext(Collection<String> supportedProducts) {
        this.supportedProducts.addAll(supportedProducts);
    }

    @Override
    public boolean isAllowed(AbstractContextBasedITextEvent event) {
        return this.supportedProducts.contains(event.getProductName());
    }
}

