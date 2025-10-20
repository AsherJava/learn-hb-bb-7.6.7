/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions;

import com.itextpdf.commons.actions.IEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractITextEvent
implements IEvent {
    private static final String ONLY_FOR_INTERNAL_USE = "AbstractITextEvent is only for internal usage.";
    private static final Map<String, Object> INTERNAL_PACKAGES = new ConcurrentHashMap<String, Object>();

    protected AbstractITextEvent() {
        boolean isUnknown = true;
        for (String namespace : INTERNAL_PACKAGES.keySet()) {
            if (!this.getClass().getName().startsWith(namespace)) continue;
            isUnknown = false;
            break;
        }
        if (isUnknown) {
            throw new UnsupportedOperationException(ONLY_FOR_INTERNAL_USE);
        }
    }

    static void registerNamespace(String namespace) {
        INTERNAL_PACKAGES.put(namespace + ".", new Object());
    }

    static {
        AbstractITextEvent.registerNamespace("com.itextpdf");
    }
}

