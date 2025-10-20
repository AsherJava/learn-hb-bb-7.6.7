/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

import com.itextpdf.commons.utils.DIContainerConfigurations;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DIContainer {
    private static final ConcurrentHashMap<Class<?>, Supplier<Object>> instances = new ConcurrentHashMap();
    private final ConcurrentHashMap<Class<?>, Object> localInstances = new ConcurrentHashMap();

    public static void registerDefault(Class<?> clazz, Supplier<Object> supplier) {
        instances.put(clazz, supplier);
    }

    public void register(Class<?> clazz, Object inst) {
        this.localInstances.put(clazz, inst);
    }

    public <T> T getInstance(Class<T> clazz) {
        Object supplier = this.localInstances.get(clazz);
        if (supplier == null) {
            supplier = instances.get(clazz).get();
        }
        if (supplier == null) {
            throw new RuntimeException("No instance registered for class " + clazz.getName());
        }
        return (T)supplier;
    }

    public boolean isRegistered(Class<?> clazz) {
        return this.localInstances.containsKey(clazz) || instances.containsKey(clazz);
    }

    static {
        DIContainerConfigurations.loadDefaultConfigurations();
    }
}

