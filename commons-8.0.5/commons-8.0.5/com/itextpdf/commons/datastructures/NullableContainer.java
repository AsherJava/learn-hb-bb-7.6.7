/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.datastructures;

public class NullableContainer<T> {
    private final T value;

    public NullableContainer(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}

