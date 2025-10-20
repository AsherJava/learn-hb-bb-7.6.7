/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractContextBasedITextEvent;
import com.itextpdf.commons.actions.IEvent;
import com.itextpdf.commons.actions.IEventHandler;
import com.itextpdf.commons.actions.contexts.ContextManager;
import com.itextpdf.commons.actions.contexts.IContext;

public abstract class AbstractContextBasedEventHandler
implements IEventHandler {
    private final IContext defaultContext;

    protected AbstractContextBasedEventHandler(IContext onUnknownContext) {
        this.defaultContext = onUnknownContext;
    }

    @Override
    public final void onEvent(IEvent event) {
        if (!(event instanceof AbstractContextBasedITextEvent)) {
            return;
        }
        IContext context = null;
        AbstractContextBasedITextEvent iTextEvent = (AbstractContextBasedITextEvent)event;
        if (iTextEvent.getMetaInfo() != null) {
            context = ContextManager.getInstance().getContext(iTextEvent.getMetaInfo().getClass());
        }
        if (context == null) {
            context = ContextManager.getInstance().getContext(iTextEvent.getClassFromContext());
        }
        if (context == null) {
            context = this.defaultContext;
        }
        if (context.isAllowed(iTextEvent)) {
            this.onAcceptedEvent(iTextEvent);
        }
    }

    protected abstract void onAcceptedEvent(AbstractContextBasedITextEvent var1);
}

