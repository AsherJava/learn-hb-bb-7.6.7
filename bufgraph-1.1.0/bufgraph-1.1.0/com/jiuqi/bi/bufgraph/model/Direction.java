/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph.model;

public enum Direction {
    OUTGOING,
    INCOMING;


    public Direction reverse() {
        switch (this) {
            case OUTGOING: {
                return INCOMING;
            }
            case INCOMING: {
                return OUTGOING;
            }
        }
        throw new IllegalStateException("Unknown Direction enum: " + (Object)((Object)this));
    }
}

