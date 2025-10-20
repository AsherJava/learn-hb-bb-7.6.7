/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;

public class Triplet<A, B, C>
extends Tuple {
    private static final long serialVersionUID = 1L;

    public Triplet(A elem0, B elem1, C elem2) {
        super(elem0, elem1, elem2);
    }

    public static <A, B, C> Triplet<A, B, C> with(A elem0, B elem1, C elem2) {
        return new Triplet<A, B, C>(elem0, elem1, elem2);
    }

    public A get_0() {
        return (A)this.get(0);
    }

    public B get_1() {
        return (B)this.get(1);
    }

    public C get_2() {
        return (C)this.get(2);
    }
}

