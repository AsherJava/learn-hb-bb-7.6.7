/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;

public class Pair<A, B>
extends Tuple {
    private static final long serialVersionUID = 1L;

    public Pair(A elem0, B elem1) {
        super(elem0, elem1);
    }

    public static <A, B> Pair<A, B> with(A elem0, B elem1) {
        return new Pair<A, B>(elem0, elem1);
    }

    public A get_0() {
        return (A)this.get(0);
    }

    public B get_1() {
        return (B)this.get(1);
    }
}

