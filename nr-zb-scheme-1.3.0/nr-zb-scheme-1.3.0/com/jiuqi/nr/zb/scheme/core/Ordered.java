/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.zb.scheme.core;

import org.jetbrains.annotations.NotNull;

public interface Ordered
extends Comparable<Ordered> {
    public String getOrder();

    public void setOrder(String var1);

    @Override
    default public int compareTo(@NotNull Ordered o) {
        if (this.getOrder() == null) {
            return -1;
        }
        if (o.getOrder() == null) {
            return 1;
        }
        return this.getOrder().compareTo(o.getOrder());
    }
}

