/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.utils;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    public T get() throws Exception;
}

