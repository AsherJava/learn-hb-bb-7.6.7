/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.datastructures;

public class Tuple2<T1, T2> {
    private final T1 first;
    private final T2 second;

    public Tuple2(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return this.first;
    }

    public T2 getSecond() {
        return this.second;
    }

    public String toString() {
        return "Tuple2{first=" + this.first + ", second=" + this.second + '}';
    }
}

