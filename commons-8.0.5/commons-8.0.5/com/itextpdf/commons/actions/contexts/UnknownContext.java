/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.contexts;

import com.itextpdf.commons.actions.AbstractContextBasedITextEvent;
import com.itextpdf.commons.actions.contexts.IContext;

public class UnknownContext
implements IContext {
    public static final IContext RESTRICTIVE = new UnknownContext(false);
    public static final IContext PERMISSIVE = new UnknownContext(true);
    private final boolean allowEvents;

    public UnknownContext(boolean allowEvents) {
        this.allowEvents = allowEvents;
    }

    @Override
    public boolean isAllowed(AbstractContextBasedITextEvent event) {
        return this.allowEvents;
    }
}

