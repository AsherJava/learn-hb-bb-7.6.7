/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;

public class Quartet<A, B, C, D>
extends Tuple {
    private static final long serialVersionUID = 1L;

    public Quartet(A elem0, B elem1, C elem2, D elem3) {
        super(elem0, elem1, elem2, elem3);
    }

    public static <A, B, C, D> Quartet<A, B, C, D> with(A elem0, B elem1, C elem2, D elem3) {
        return new Quartet<A, B, C, D>(elem0, elem1, elem2, elem3);
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

    public D get_3() {
        return (D)this.get(3);
    }
}

