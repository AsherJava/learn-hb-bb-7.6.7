/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;

public class Range<T>
extends Tuple {
    private static final long serialVersionUID = 1L;

    public Range(T min, T max) {
        super(min, max);
    }

    public static <T> Range<T> with(T min, T max) {
        return new Range<T>(min, max);
    }

    public T getMin() {
        return (T)this.get(0);
    }

    public T getMax() {
        return (T)this.get(1);
    }
}

