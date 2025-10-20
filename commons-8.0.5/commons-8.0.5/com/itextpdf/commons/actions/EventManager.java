/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.AbstractITextConfigurationEvent;
import com.itextpdf.commons.actions.IEvent;
import com.itextpdf.commons.actions.IEventHandler;
import com.itextpdf.commons.actions.ProductEventHandler;
import com.itextpdf.commons.actions.ProductProcessorFactoryKeeper;
import com.itextpdf.commons.actions.processors.UnderAgplProductProcessorFactory;
import com.itextpdf.commons.exceptions.AggregatedException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public final class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    private final Set<IEventHandler> handlers = new LinkedHashSet<IEventHandler>();

    private EventManager() {
        this.handlers.add(ProductEventHandler.INSTANCE);
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    public static void acknowledgeAgplUsageDisableWarningMessage() {
        ProductProcessorFactoryKeeper.setProductProcessorFactory(new UnderAgplProductProcessorFactory());
    }

    public void onEvent(IEvent event) {
        ArrayList<RuntimeException> caughtExceptions = new ArrayList<RuntimeException>();
        for (IEventHandler handler : this.handlers) {
            try {
                handler.onEvent(event);
            }
            catch (RuntimeException ex) {
                caughtExceptions.add(ex);
            }
        }
        if (event instanceof AbstractITextConfigurationEvent) {
            try {
                AbstractITextConfigurationEvent itce = (AbstractITextConfigurationEvent)event;
                itce.doAction();
            }
            catch (RuntimeException ex) {
                caughtExceptions.add(ex);
            }
        }
        if (caughtExceptions.size() == 1) {
            throw (RuntimeException)caughtExceptions.get(0);
        }
        if (!caughtExceptions.isEmpty()) {
            throw new AggregatedException("Error during event processing", caughtExceptions);
        }
    }

    public void register(IEventHandler handler) {
        if (handler != null) {
            this.handlers.add(handler);
        }
    }

    public boolean isRegistered(IEventHandler handler) {
        if (handler != null) {
            return this.handlers.contains(handler);
        }
        return false;
    }

    public boolean unregister(IEventHandler handler) {
        if (handler != null) {
            return this.handlers.remove(handler);
        }
        return false;
    }
}

