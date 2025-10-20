/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractITextEvent;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.ProductEventHandler;
import com.itextpdf.commons.actions.processors.ITextProductEventProcessor;
import com.itextpdf.commons.actions.sequence.SequenceId;
import java.util.List;
import java.util.Map;

public abstract class AbstractITextConfigurationEvent
extends AbstractITextEvent {
    protected ITextProductEventProcessor addProcessor(ITextProductEventProcessor processor) {
        return ProductEventHandler.INSTANCE.addProcessor(processor);
    }

    protected ITextProductEventProcessor removeProcessor(String productName) {
        return ProductEventHandler.INSTANCE.removeProcessor(productName);
    }

    protected ITextProductEventProcessor getActiveProcessor(String productName) {
        return ProductEventHandler.INSTANCE.getActiveProcessor(productName);
    }

    protected Map<String, ITextProductEventProcessor> getProcessors() {
        return ProductEventHandler.INSTANCE.getProcessors();
    }

    protected List<AbstractProductProcessITextEvent> getEvents(SequenceId id) {
        return ProductEventHandler.INSTANCE.getEvents(id);
    }

    protected void addEvent(SequenceId id, AbstractProductProcessITextEvent event) {
        ProductEventHandler.INSTANCE.addEvent(id, event);
    }

    protected void registerInternalNamespace(String namespace) {
        AbstractITextEvent.registerNamespace(namespace);
    }

    protected abstract void doAction();
}

