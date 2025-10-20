/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import com.jiuqi.bi.util.tuples.Tuple;

public class Quintet<A, B, C, D, E>
extends Tuple {
    private static final long serialVersionUID = 1L;

    public Quintet(A elem0, B elem1, C elem2, D elem3, E elem4) {
        super(elem0, elem1, elem2, elem3, elem4);
    }

    public static <A, B, C, D, E> Quintet<A, B, C, D, E> with(A elem0, B elem1, C elem2, D elem3, E elem4) {
        return new Quintet<A, B, C, D, E>(elem0, elem1, elem2, elem3, elem4);
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

    public E get_4() {
        return (E)this.get(4);
    }
}

