/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;

public class Unit<A>
extends Tuple {
    private static final long serialVersionUID = 1L;

    public Unit(A elem) {
        super(elem);
    }

    public static <A> Unit<A> with(A elem) {
        return new Unit<A>(elem);
    }

    public A get_0() {
        return (A)this.get(0);
    }
}

